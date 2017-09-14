package com.aaron.base;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

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
    public void clearAllActivity() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
    }
}