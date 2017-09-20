package com.aaron.base;

import android.app.Activity;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 名称：ActivityManager.java
 *
 * 描述：用于处理退出程序时可以退出所有的activity，而编写的通用类
 * 单例模式中获取唯一的AbActivityManager实例
 */
public enum ActivityManager {
    Instance();
    ActivityManager() {//构造函数
    }
    private static List<Activity> activityList = new LinkedList<Activity>();

    /**
     * 添加Activity到容器中.
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 移除Activity从容器中.
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 遍历所有Activity并finish.
     */
    public void exitApp() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }

        // 方式1：android.os.Process.killProcess()
//        android.os.Process.killProcess(android.os.Process.myPid());

        // 方式2：System.exit()
        // System.exit() = Java中结束进程的方法：关闭当前JVM虚拟机
        System.exit(0);

        // System.exit(0)和System.exit(1)的区别
        // 1. System.exit(0)：正常退出；
        // 2. System.exit(1)：非正常退出，通常这种退出方式应该放在catch块中。
    }
}