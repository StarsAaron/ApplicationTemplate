package com.aaron.applicationtemplate.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.aaron.applicationtemplate.BaseContract;
import com.aaron.applicationtemplate.R;
import com.aaron.applicationtemplate.RxPresenter;
import com.aaron.base.ActivityManager;
import com.aaron.utils.StatusBarColorUtils;
import com.aaron.utils.klog.KLog;
import com.customdialoglibrary.CustomDialog;
import com.customdialoglibrary.Effectstype;

import butterknife.Unbinder;

/**
 * Activity 基类，带ToolBar
 */
public abstract class BaseActivity <V extends BaseContract.BaseView, T extends RxPresenter<V>>
        extends AppCompatActivity {
    private String TAG = "BaseActivity";
    private Toolbar toolbar;//标题栏
    private ViewGroup viewGroup;//内容窗口
    private T mPresenter;//处理业务逻辑的Presenter
    protected Unbinder unbinder;//用于注解解绑

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        // 设置布局文件
        getDelegate().setContentView(R.layout.activity_base);
        // 配置工具栏
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setBackBar(true);

        viewGroup = (ViewGroup) findViewById(R.id.content);

        // 创建Presenter并绑定该Activity
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
        // 设置状态栏样式
        StatusBarColorUtils.compat(this, ContextCompat.getColor(this, R.color.colorPrimary));
        // 保存当前Activity到列表中
        ActivityManager.Instance.addActivity(this);
        // 初始化视图(加载数据，控件显示样式)
        onActivityCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        KLog.i(TAG, "--onResume");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        KLog.i(TAG, "--onRestoreInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        KLog.i(TAG, "--onStop");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        KLog.i(TAG, "--onSaveInstanceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KLog.i(TAG, "--onDestroy");
        //解除注解绑定
        if (unbinder != null) {
            unbinder.unbind();
        }
        //从列表中移除activity
        ActivityManager.Instance.removeActivity(this);
        //解除Presenter绑定
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 在setContentView之前设置，例如全屏
     */
    protected void beforeSetContentView() {
        KLog.i(TAG, "--beforeSetContentView");
        //由子类选择设置
        // 无标题栏：
        // 隐藏系统title注意的两点：
        // 继承AppCompatActivity时使用：
        // supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 继承activity时使用：
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏：
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 创建Presenter
     *
     * @return
     */
    protected T createPresenter(){return null;}

    /**
     * 跳转到Activity
     *
     * @param activityClass
     */
    public void goActivity(Class activityClass) {
        goActivity(activityClass, null);
    }

    /**
     * 跳转到Activity
     *
     * @param activityClass
     */
    public void goActivity(Class activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        if (bundle != null) {
            intent.putExtra(TAG, bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
//        finish();
    }

    /**
     * 调用 onCreate 初始化时调用
     *
     * @param savedInstanceState
     */
    protected abstract void onActivityCreate(Bundle savedInstanceState);

    /**
     * 获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T findView(int viewId) {
        return (T) viewGroup.findViewById(viewId);
    }

    /**
     * 获取Toolbar 控件
     *
     * @return
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    @Override
    public void setTitle(CharSequence title) {
        toolbar.setSubtitle(title);
    }

    /**
     * 设置标题
     *
     * @param titleId 字符串资源
     */
    @Override
    public void setTitle(int titleId) {
        toolbar.setTitle(titleId);
    }

    public void setSubtitle(CharSequence title) {
        toolbar.setSubtitle(title);
    }

    public void setSubtitle(int titleId) {
        toolbar.setSubtitle(titleId);
    }

    public void setSubtitleTextColor(int color) {
        toolbar.setSubtitleTextColor(color);
    }

    public void setTitleTextColor(int color) {
        toolbar.setTitleTextColor(color);
    }

    /**
     * 设置是否显示返回按钮
     *
     * @param isShow
     */
    public void setBackBar(boolean isShow) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
    }

    /**
     * 设置背景颜色
     *
     * @param color
     */
    public void setContentBackground(int color) {
        viewGroup.setBackgroundResource(color);
    }

    @Override
    public void setContentView(int layoutResID) {
        viewGroup.removeAllViews();
        getLayoutInflater().inflate(layoutResID, viewGroup, true);
    }

    @Override
    public void setContentView(View view) {
        viewGroup.removeAllViews();
        viewGroup.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        viewGroup.addView(view, params);
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return onOptionsItemSelectedCompat(item);
    }

    /**
     * 子类可重写该方法监听Menu的点击事件
     *
     * @param item
     * @return
     */
    protected boolean onOptionsItemSelectedCompat(MenuItem item) {
        return false;
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(int title, int message) {
        showMessageDialog(getText(title).toString(), getText(message).toString());
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(int title, String message) {
        showMessageDialog(getText(title).toString(), message);
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(String title, int message) {
        showMessageDialog(title, getText(message).toString());
    }

    /**
     * Show message dialog.
     *
     * @param title
     * @param message
     */
    public void showMessageDialog(final String title, final String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.setPositiveButton(R.string.know, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.show();
//        AlertDialog alertDialog = builder.show();
//        alertDialog.setCanceledOnTouchOutside(true);
        CustomDialog.init(CustomDialog.DialogType.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutCancel(true)
                .setAnimation(Effectstype.Flipv)
                .show(getSupportFragmentManager());
    }
}
