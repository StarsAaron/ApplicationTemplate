package com.aaron.applicationtemplate.global;

import com.aaron.applicationtemplate.BuildConfig;
import com.aaron.base.BaseApplication;
import com.aaron.utils.klog.KLog;

/**
 * Created by Aaron on 2017/2/20.
 */
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化日志工具,
        // 参数一：
        // BuildConfig.LOG_DEBUG是自动生成的，对应 build.gragle 的
        // buildConfigField "boolean", "LOG_DEBUG", "false" 值
        // 参数二:
        // tag标签
        KLog.init(BuildConfig.LOG_DEBUG, "KLog");
    }
}
