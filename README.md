# FallingView
An interface can be added to the falling objects View

You can import it into your project


Add it in your root build.gradle at the end of repositories:

Step 1. Add the statement


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


Step 2. Add the dependency

	dependencies {
	        compile 'com.github.hyc875224980:FallingView:v1.0.0'
	}


How to Use ?

    <com.falling.view.FallingView
        //You can import your favorite small icon
        app:falling="@drawable/snow_flake"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>


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
