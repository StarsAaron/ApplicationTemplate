package com.aaron.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.aaron.utils.encrypt.AesUtils;

import java.io.File;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/23
 *     desc  : 意图相关工具类
 * </pre>
 */
public final class IntentUtils {

    private IntentUtils() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 获取安装App（支持7.0）的意图
     *
     * @param filePath  文件路径
     * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     *                  <br>参看https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @return intent
     */
    public static Intent getInstallAppIntent(final String filePath, final String authority) {
        return getInstallAppIntent(FileUtils.getFileByPath(filePath), authority);
    }

    /**
     * 获取安装App(支持7.0)的意图
     *
     * @param file      文件
     * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     *                  <br>参看https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @return intent
     */
    public static Intent getInstallAppIntent(final File file, final String authority) {
        if (file == null) return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            data = FileProvider.getUriForFile(Utils.getApp(), authority, file);
        }
        intent.setDataAndType(data, type);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取卸载App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getUninstallAppIntent(final String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取打开App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getLaunchAppIntent(final String packageName) {
        return Utils.getApp().getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * 获取App具体设置的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getAppDetailsSettingsIntent(final String packageName) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取分享文本的意图
     *
     * @param content 分享文本
     * @return intent
     */
    public static Intent getShareTextIntent(final String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取分享图片的意图
     *
     * @param content   文本
     * @param imagePath 图片文件路径
     * @return intent
     */
    public static Intent getShareImageIntent(final String content, final String imagePath) {
        return getShareImageIntent(content, FileUtils.getFileByPath(imagePath));
    }

    /**
     * 获取分享图片的意图
     *
     * @param content 文本
     * @param image   图片文件
     * @return intent
     */
    public static Intent getShareImageIntent(final String content, final File image) {
        if (!FileUtils.isFileExists(image)) return null;
        return getShareImageIntent(content, Uri.fromFile(image));
    }

    /**
     * 获取分享图片的意图
     *
     * @param content 分享文本
     * @param uri     图片uri
     * @return intent
     */
    public static Intent getShareImageIntent(final String content, final Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @return intent
     */
    public static Intent getComponentIntent(final String packageName, final String className) {
        return getComponentIntent(packageName, className, null);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     * @return intent
     */
    public static Intent getComponentIntent(final String packageName, final String className, final Bundle bundle) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (bundle != null) intent.putExtras(bundle);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取关机的意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.SHUTDOWN"/>}</p>
     *
     * @return intent
     */
    public static Intent getShutdownIntent() {
        Intent intent = new Intent(Intent.ACTION_SHUTDOWN);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳至拨号界面意图
     *
     * @param phoneNumber 电话号码
     */
    public static Intent getDialIntent(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取拨打电话意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
     *
     * @param phoneNumber 电话号码
     */
    public static Intent getCallIntent(final String phoneNumber) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳至发送短信界面的意图
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    public static Intent getSendSmsIntent(final String phoneNumber, final String content) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }


    /**
     * 获取拍照的意图
     *
     * @param outUri 输出的uri
     * @return 拍照的意图
     */
    public static Intent getCaptureIntent(final Uri outUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        return intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取启动web浏览器意图
     *
     * @param url
     * @return
     */
    public static Intent getWebIntent(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
//        intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");//指定浏览器
        return intent;
    }

    /**
     * 检查是否有对应的处理程序
     * @return
     */
    public static boolean checkResolveInfo(Context context,Intent intent){
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (null == resolveInfo) {
            Log.e(AesUtils.TAG, "No activity to handle this intent");
            return false;
        }
        return true;
    }

    /**
     * 添加桌面快捷方式
     *
     * @param context
     * @param resId    资源ID 如：R.drawable.ic_launcher
     * @param activity 目标
     */
    public static void addLauncherIcon(Context context, int resId, Class activity) {
        if (isLauncherIconExit(context)) {
            Toast.makeText(context, "快捷方式已经存在", Toast.LENGTH_SHORT).show();
            return;
        }
        // 取得图标资源
        Parcelable icon = Intent.ShortcutIconResource.fromContext(
                context, resId);
        Intent intent = new Intent();
        // 设置意图的动作
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        // 设置图标的名称
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "LOL");
        // 设置图标
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        // 设置意图的动作和类别
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_MAIN);
        intent2.addCategory(Intent.CATEGORY_LAUNCHER);
        intent2.setComponent(new ComponentName(context, activity));
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent2);
        context.sendBroadcast(intent);
    }

    /**
     * 删除桌面快捷方式
     *
     * @param context
     * @param resId    资源ID 如：R.drawable.ic_launcher
     * @param activity 目标
     */
    public static void delLauncherIcon(Context context, int resId, Class activity) {
        Parcelable icon = Intent.ShortcutIconResource.fromContext(
                context, resId);
        Intent intent = new Intent();
        // 设置意图的动作
        intent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        // 设置图标的名称
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "LOL");
        // 设置图标
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        // 设置意图的动作和类别
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_MAIN);
        intent2.addCategory(Intent.CATEGORY_LAUNCHER);
        intent2.setComponent(new ComponentName(context, activity));
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent2);
        context.sendBroadcast(intent);
    }

    /**
     * 判断桌面是否已经存在快捷方式
     *
     * @param context
     * @return
     */
    private static boolean isLauncherIconExit(Context context) {
        Uri uri = null;
        // 不同版本中的Uri不同
        if (getSdkVersion() < 8) {
            uri = Uri
                    .parse("content://com.android.launcher.settings/favorites");
        } else {
            uri = Uri
                    .parse("content://com.android.launcher2.settings/favorites");
        }
        String selection = "title=?";
        String[] selectionArgs = new String[]{"LOL"};
        Cursor cursor = context.getContentResolver().query(uri, null, selection,
                selectionArgs, null);
        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    /**
     * 获得手机SDK的版本
     *
     * @return
     */
    private static int getSdkVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 创建带消息条数提示的桌面快捷方式
     * @param context
     * @param resId 图标资源
     * @param activity 目标
     * @param num 显示的数字
     */
    public static void addLauncherIconWithMsgNum(Context context,int resId, Class activity,int num) {
        final Intent addIntent = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        final Parcelable icon = getCountIcon(context,
                (BitmapFactory.decodeResource(context.getResources(),resId)),num); // 获取快捷键的图标
        // final Parcelable icon = Intent.ShortcutIconResource.fromContext(
        // context, R.drawable.ic_launcher); // 获取快捷键的图标
        addIntent.putExtra("duplicate", false);
        final Intent myIntent = new Intent(context, activity);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.setAction("android.intent.action.MAIN"); // 绑定快捷方式和应用程序
        myIntent.addCategory("android.intent.category.LAUNCHER");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                context.getString(R.string.app_name));// 快捷方式的标题
        // addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);//
        // 快捷方式的图标
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);// 快捷方式的图标
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);// 快捷方式的动作
        context.sendBroadcast(addIntent);
    }

    /**
     * 生成带数字的图标
     * @param context
     * @param icon
     * @param num 显示在图标上的数字
     * @return
     */
    private static Bitmap getCountIcon(Context context, Bitmap icon,int num) {
        // 初始化画布
        int iconSize = (int) context.getResources().getDimension(
                android.R.dimen.app_icon_size);
        Bitmap contactIcon = Bitmap.createBitmap(iconSize, iconSize,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(contactIcon);

        // 拷贝图片
        Paint iconPaint = new Paint();
        iconPaint.setDither(true);// 防抖动
        iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
        Rect src = new Rect(0, 0, icon.getWidth(), icon.getHeight());
        Rect dst = new Rect(0, 0, iconSize, iconSize);
        canvas.drawBitmap(icon, src, dst, iconPaint);

        // 在图片上创建一个覆盖的数字
        int contacyCount = 11;
        // 启用抗锯齿和使用设备的文本字距
        Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.DEV_KERN_TEXT_FLAG);
        countPaint.setColor(Color.RED);
        countPaint.setTextSize(20f);
        countPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(String.valueOf(contacyCount), iconSize - 18, 25,
                countPaint);
        return contactIcon;
    }

//    /**
//     * 获取选择照片的Intent
//     *
//     * @return
//     */
//    public static Intent getPickIntentWithGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        return intent.setType("image*//*");
//    }
//
//    /**
//     * 获取从文件中选择照片的Intent
//     *
//     * @return
//     */
//    public static Intent getPickIntentWithDocuments() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        return intent.setType("image*//*");
//    }
//
//
//    public static Intent buildImageGetIntent(final Uri saveTo, final int outputX, final int outputY, final boolean returnData) {
//        return buildImageGetIntent(saveTo, 1, 1, outputX, outputY, returnData);
//    }
//
//    public static Intent buildImageGetIntent(Uri saveTo, int aspectX, int aspectY,
//                                             int outputX, int outputY, boolean returnData) {
//        Intent intent = new Intent();
//        if (Build.VERSION.SDK_INT < 19) {
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//        } else {
//            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//        }
//        intent.setType("image*//*");
//        intent.putExtra("output", saveTo);
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", returnData);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//        return intent;
//    }
//
//    public static Intent buildImageCropIntent(final Uri uriFrom, final Uri uriTo, final int outputX, final int outputY, final boolean returnData) {
//        return buildImageCropIntent(uriFrom, uriTo, 1, 1, outputX, outputY, returnData);
//    }
//
//    public static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY,
//                                              int outputX, int outputY, boolean returnData) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uriFrom, "image*//*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("output", uriTo);
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", returnData);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//        return intent;
//    }
//
//    public static Intent buildImageCaptureIntent(final Uri uri) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        return intent;
//    }
}
