package com.customdialoglibrary;

import android.support.v4.app.FragmentManager;
import android.view.WindowManager;

/**
 * Created by Aaron on 2017/9/18.
 */

public class CustomDialogWrapper {
    /**
     * 显示简单对话框
     */
    public static void showSimpleDialog(String title,String msg,FragmentManager sfm){
        CustomDialog.init()
                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutCancel(true)
                .setTitleText(title)
                .setContentText(msg)
                .show(sfm);
    }

    /**
     * 显示错误对话框
     */
    public static void showEror(String title,String msg,FragmentManager sfm){
        CustomDialog.init(CustomDialog.DialogType.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutCancel(true)
                .show(sfm);
    }

    /**
     * 显示警告对话框
     */
    public static void showWarn(String title,String msg,FragmentManager sfm){
        CustomDialog.init(CustomDialog.DialogType.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutCancel(true)
                .show(sfm);
    }

    /**
     * 显示成功对话框
     */
    public static void showSuccess(String title,String msg,FragmentManager sfm){
        CustomDialog.init(CustomDialog.DialogType.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutCancel(true)
                .show(sfm);
    }

}


