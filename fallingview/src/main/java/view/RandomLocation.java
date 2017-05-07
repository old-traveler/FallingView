package view;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyc on 2017/5/6 15:53
 */

public class RandomLocation {

    /**
     * 计算飘落物的位置
     * @param width         屏幕的宽度
     * @param densityRatio  密度系数（多少个单位宽度产生一个飘落物）
     * @param fallingSize   飘落物的宽度
     * @return
     */
    public static List<Integer> countRandomLocation(int width,int densityRatio,int fallingSize){
        int maxCount = width / fallingSize;
        int countLine= maxCount / densityRatio;
        float randomWith=  width*1.0f/countLine*1.0f;
        List<Integer> lists=new ArrayList<>();
        for (int i = 0; i <countLine; i++) {
            int x= (int) ((int) (Math.random()  * randomWith)+i*randomWith);
            lists.add(x);
        }
        return lists;
    }

    /**
     * 缩放bitmap
     * @param zoomBitmap
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap zoomImage(Bitmap zoomBitmap, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = zoomBitmap.getWidth();
        float height = zoomBitmap.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(zoomBitmap, 0, 0, (int) width,(int) height, matrix, true);
        return bitmap;
    }
}
