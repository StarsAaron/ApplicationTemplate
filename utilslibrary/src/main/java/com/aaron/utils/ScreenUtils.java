package com.aaron.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.aaron.utils.encrypt.AesUtils;

import static android.content.ContentValues.TAG;
import static android.content.Context.POWER_SERVICE;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/02
 *     desc  : 屏幕相关工具类
 * </pre>
 */
public final class ScreenUtils {

    private ScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽
     */
    public static int getScreenWidth() {
        return Utils.getApp().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高
     */
    public static int getScreenHeight() {
        return Utils.getApp().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 设置屏幕为全屏
     * <p>需在 {@code setContentView} 之前调用</p>
     *
     * @param activity activity
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置屏幕为横屏
     * <p>还有一种就是在Activity中加属性android:screenOrientation="landscape"</p>
     * <p>不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次</p>
     * <p>设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次</p>
     * <p>设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法</p>
     *
     * @param activity activity
     */
    public static void setLandscape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否横屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLandscape() {
        return Utils.getApp().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPortrait() {
        return Utils.getApp().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            default:
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
    }

    /**
     * 截屏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        return screenShot(activity, true);
    }

    /**
     * 截屏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bmp = decorView.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight);
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    /**
     * 判断是否锁屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isScreenLock() {
        KeyguardManager km = (KeyguardManager) Utils.getApp().getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }


    /**
     * 设置进入休眠时长
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration 时长
     */
    public static void setSleepDuration(final int duration) {
        Settings.System.putInt(Utils.getApp().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, duration);
    }

    /**
     * 获取进入休眠时长
     *
     * @return 进入休眠时长，报错返回-123
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(Utils.getApp().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

    /**
     * flags方式保持屏幕常亮1
     * 在Activity的oncreate方法里面setContentView（）之前调用
     * @param activity
     */
    public static void keepScreenOn(Activity activity){
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                , WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 清除flags方式设置的屏幕常亮
     * @param activity
     */
    public static void clearKeepScreenOn(Activity activity){
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 保持屏幕常亮2
     *
     * 添加权限：<uses-permission android:name="android.permission.WAKE_LOCK" />
     *
     * 注意：
     * 在onResume 中启用
     * 在onPause 的时候使用mWakeLock.release();释放掉PowerManager
     * @param context
     */
    public static void keepScreenOn2(Context context,String Tag){
        PowerManager pManager = ((PowerManager) context.getSystemService(POWER_SERVICE));
        PowerManager.WakeLock mWakeLock = pManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                | PowerManager.ON_AFTER_RELEASE, Tag);
        mWakeLock.acquire();
//        mWakeLock.release();//在onPause 的时候要释放掉
    }

    /**
     * 保持屏幕常亮3
     *
     * 注意：
     * <uses-permission android:name="android.permission.WRITE_SETTINGS" />
     * 还要特别注意的是要加入 android:sharedUserId="android.uid.system"，但有一个问题，
     * 如果加入了sharedUserId后就不能使用eclipse编译了，一定要手动通过 mm -B进行编译，
     * 然后把apk install到模拟器或设备中
     * @param context
     * @param enabled 是否开启常亮
     */
    public static void keepScreenOn3(Context context,boolean enabled){
        android.provider.Settings.System.putInt(context.getContentResolver()
                ,android.provider.Settings.System.LOCK_PATTERN_ENABLED
                , enabled ? 1 : 0);
    }

    /**
     * 判断是否是平板
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isTablet() {
        return (Utils.getApp().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @param useDeviceSize 是否包含状态栏高度
     * @return
     */
    public static int[] getScreenSize(Context context, boolean useDeviceSize) {
        int[] size = new int[2];
        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        //不包含状态栏高度
        if (!useDeviceSize) {
            size[0] = widthPixels;
            size[1] = heightPixels - getStatusBarHeight2(context);
            return size;
        }

        // 包含状态栏高度
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        size[0] = widthPixels;
        size[1] = heightPixels;
        return size;
    }

    /**
     * 获取状态栏的高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeigh(Activity activity) {
        // decorView是window中的最顶层view，可以从window中获取到decorView，
        // 然后decorView有个getWindowVisibleDisplayFrame方法可以获取到程序显示的区域，
        // 包括标题栏，但不包括状态栏。于是，我们就可以算出状态栏的高度了。
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * 获取状态栏的高度2
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight2(Context context) {
        int result = 0;
        try {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取标题栏的高度
     *
     * @param activity
     * @return
     */
    public static int getTitleBarHeigh(Activity activity) {
        // getWindow().findViewById(Window.ID_ANDROID_CONTENT)这个方法获取到的view
        // 就是程序不包括标题栏的部分，然后就可以知道标题栏的高度了。
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop - getStatusBarHeigh(activity);
    }

    /**
     * 获取屏幕密度DPI
     *
     * @param context
     * @return
     */
    public static int getDensity(Context context) {
        // l：m：h：xh：xxh=3：4：6：8：12 所有L是120
        float density = context.getResources().getDisplayMetrics().density; // 屏幕密度(0.75-1.0-1.5)
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi; // 屏幕密度DPI(120-160-240)
        System.out.println("density=" + density + "- densityDpi=" + densityDpi);
        return densityDpi;
    }
}
