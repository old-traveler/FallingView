package com.falling.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import com.falling.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyc on 2017/5/6 15:30
 */

public class FallingView  extends View{
    /**
     * 用户屏幕的宽度
     */
    private int screenWidth = 0;
    /**
     * 用户屏幕的高度
     */
    private int screenHeight = 0;
    /**
     * 画笔
     */
    private Paint paint=null;
    /**
     * 是否正在下落
     */
    private boolean isFalling=false;
    /**
     * 飘落物的坐标
     */
    private List<List<Point>> mPoints;
    /**
     * 飘落物的大小
     */
    private int fallingSize=60;
    /**
     * 一个飘落物所占的单位长度，数值越小飘落物越多
     */
    private int densityRatio = 4;
    /**
     * 飘落物的bitmap
     */
    private Bitmap fallingBitmap = null;

    /**
     * 飘落物下落的时间  默认30
     */
    private int dropTime=30;
    /**
     * 产生飘落物的间隔
     */
    private long produceInterval=1000L;


    public FallingView(Context context) {
        super(context,null);
    }

    /**
     * 获取飘落物图标，初始化bitmap
     * @param context
     * @param attrs
     */
    public FallingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        TypedArray ta= context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.FallingView,0,0);
        fallingBitmap = BitmapFactory.decodeResource(getResources(),ta.getResourceId(R.styleable.FallingView_falling, R.drawable.snow_flake));
        fallingBitmap=RandomLocation.zoomImage(fallingBitmap,fallingSize,fallingSize);

    }

    public FallingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取屏幕宽度和高度
     */
    private void getScreenWidthAndHeight(){
        WindowManager manager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        screenHeight = outMetrics.heightPixels;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (screenHeight == 0 || screenWidth == 0){
            getScreenWidthAndHeight();
        }
        if (paint == null){
            paint=new Paint();
            paint.setAntiAlias(true);
        }

        if (!isFalling){
            startTime();
            startAnimation();
        }
        drawFalling(canvas);

    }

    /**
     * 开始飘落物下落的动画
     */
    private void startAnimation(){
        isFalling=true;
        final List<Point> point=produceFallingView();
        if (mPoints==null){
            mPoints=new ArrayList<>();
        }
        mPoints.add(point);
        final ValueAnimator anim = ObjectAnimator.ofFloat(0.0F,screenHeight).setDuration((long) (1000*dropTime));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drop(point,(Float) animation.getAnimatedValue(),anim);
                invalidate();

            }
        });
        anim.start();
    }

    /**
     * 计时器，每隔1秒增加一批飘落物
     */
    private void startTime(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
                startTime();
            }
        }, produceInterval);
    }

    /**
     * 产生飘落物
     * @return
     */
    private List<Point> produceFallingView(){
        List<Point> points = new ArrayList<>();
        List<Integer> lists = RandomLocation
                .countRandomLocation(screenWidth,densityRatio,fallingSize);
        for (int i:lists){
            Point point=new Point();
            point.set(i, 0);
            points.add(point);
        }
        return points;
    }

    /**
     * 飘落物的下降
     * @param points
     * @param distance
     * @param anim
     */
    private void drop(List<Point> points,float distance,ValueAnimator anim){
        for (Point point : points){
            point.y = (int) (distance)+point.x%2*fallingSize-20;
        }
        //删除落出屏幕的下落物，并取消动画
        if (distance>(screenHeight-20)){
            mPoints.remove(points);
            anim.cancel();
        }
    }

    /**
     * 绘制飘落物
     * @param canvas
     */
    private void drawFalling(Canvas canvas){
        for (List<Point> points:mPoints){
            for (Point point : points){
                float left = point.x ;
                float top  = point.y-fallingSize;
                canvas.drawBitmap(fallingBitmap,left,top,paint);
            }
        }

    }


    /**
     * 设置产生飘落物的时间间隔
     * @param produceInterval
     */
    public void setProduceInterval(long produceInterval) {
        this.produceInterval = produceInterval;
    }

    /**
     * 设置飘落物的大小
     * @param fallingSize
     */
    public void setFallingSize(int fallingSize) {
        this.fallingSize = fallingSize;
    }

    /**
     * 设置所占的单位宽度  ，即飘落物密度 越小飘落物越密
     * @param densityRatio
     */
    public void setDensityRatio(int densityRatio) {
        this.densityRatio = densityRatio;
    }

    /**
     * 设置飘落物从下落到消失的时间
     * @param dropTime
     */
    public void setDropTime(int dropTime) {
        this.dropTime = dropTime;
    }

}
