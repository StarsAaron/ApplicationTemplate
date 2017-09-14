package com.aaron.applicationtemplate.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aaron.applicationtemplate.R;
import com.aaron.applicationtemplate.example.RecyclerViewActivity;
import com.aaron.applicationtemplate.dialog.CustomDialogTestActivity;
import com.aaron.applicationtemplate.example.VolleyTestActivity;
import com.aaron.applicationtemplate.example.filechoose.FileChooserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.btn_volley)
    Button btn_volley;
    @BindView(R.id.btn_guide)
    Button btn_guide;
    @BindView(R.id.btn_filechoose)
    Button btn_filechoose;
    @BindView(R.id.btn_diybutton)
    Button btn_diybutton;
    @BindView(R.id.btn_listview)
    Button btn_listview;
    @BindView(R.id.btn_nicedialog)
    Button btnNicedialog;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
    }

    int pressBackNum = 0;

    @Override
    public void onBackPressed() {
        pressBackNum++;
        if (1 == pressBackNum) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //两秒后归零
                    pressBackNum = 0;
                }
            }, 2000);
        } else if (2 == pressBackNum) {
            finish();
        }
    }

    /**
     * 系统设置发生改变时执行
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        /*
        < activity android:name="MyActivity"
         可以固定屏幕方向：
                   android:screenOrientation="landscape"
                   参数说明:
                            landscape = 横向
                            portrait = 纵向

         可以设置屏幕或键盘发生改变时不执行重置，就是不会执行onConfigurationChanged 方法：
                android:configChanges="orientation|keyboardHidden">
         */

        super.onConfigurationChanged(newConfig);
        //横竖屏切换
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //加入横屏要处理的代码
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //加入竖屏要处理的代码
        }
    }

    @OnClick({R.id.btn_volley, R.id.btn_guide, R.id.btn_filechoose
            , R.id.btn_diybutton,R.id.btn_listview,R.id.btn_nicedialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_volley:
                Intent intent = new Intent(MainActivity.this, VolleyTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_guide:
                Intent intent2 = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_filechoose:
                Intent intent4 = new Intent(MainActivity.this, FileChooserActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_diybutton:
                Intent intent5 = new Intent(MainActivity.this, DiyButtonActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_listview:
                Intent intent6 = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_nicedialog:
                Intent intent8 = new Intent(MainActivity.this, CustomDialogTestActivity.class);
                startActivity(intent8);
                break;
        }
    }

}
