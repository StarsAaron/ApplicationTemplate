package com.aaron.base;

import android.app.Application;

import com.aaron.utils.Utils;

/**
 * 自定义Application
 */
public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    private static BaseApplication sInstance;

    public static BaseApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Utils.init(this);
    }


}
