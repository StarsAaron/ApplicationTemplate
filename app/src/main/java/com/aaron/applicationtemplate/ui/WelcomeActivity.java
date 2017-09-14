package com.aaron.applicationtemplate.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.aaron.applicationtemplate.R;


/**
 * 初次登陆引导页面
 */
public class WelcomeActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private GestureDetector gestureDetector = null;
    private ViewFlipper viewFlipper = null;
    private ImageView[] imageViews = null;
    private Button btn_enter = null;
    private TextView btn_jump = null;
    private LinearLayout ll_point_bar = null;
    private int pageAcount = 0;//页面数量
    private int i = 0;//当前页面位置

    private int[] imagesId = {R.mipmap.png1o, R.mipmap.png2o, R.mipmap.png3o};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ll_point_bar = (LinearLayout) findViewById(R.id.ll_point_bar);
        //初始化页面数量
        pageAcount = imagesId.length;
        //跳出按钮
        btn_jump = (TextView) findViewById(R.id.btn_jump);
        btn_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //进入程序按钮
        btn_enter = (Button) findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //添加点
        imageViews = new ImageView[pageAcount];
        for (int i = 0; i < pageAcount; i++) {
            ImageView img = new ImageView(getApplicationContext());
            if (0 == i) {
                img.setImageResource(R.mipmap.ic_point_big);
            } else {
                img.setImageResource(R.mipmap.ic_point_small);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 5, 5, 5);
            img.setLayoutParams(layoutParams);
            imageViews[i] = img;
            ll_point_bar.addView(img);
        }
        //添加页面
        gestureDetector = new GestureDetector(getApplicationContext(), this);
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper1);
        addPageView();
    }

    private View addImageView(int id) {
        ImageView iv = new ImageView(this);
        iv.setImageResource(id);
        return iv;
    }

    /**
     * 添加页面
     */
    private void addPageView() {
        for (int i = 0; i < pageAcount; i++) {
            viewFlipper.addView(addImageView(imagesId[i]));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event); // 将点击事件传给GestureDetector
    }

    //------- GestureDetector 重写的方法 ----------------------------------------
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        System.out.println("in------------>>>>>>>");
        if (motionEvent.getX() - motionEvent1.getX() > 120) {
            if (i < pageAcount - 1) {
                i++;
                setImage(i);
                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.animation_right_in));
                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.animation_left_out));
                viewFlipper.showNext();
                if (i == pageAcount - 1) {
                    btn_enter.setVisibility(View.VISIBLE);
                } else {
                    btn_enter.setVisibility(View.GONE);
                }
            }
            return true;
        } else if (motionEvent.getX() - motionEvent1.getX() < -120) {
            if (i > 0) {
                i--;
                setImage(i);
                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.animation_left_in));
                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.animation_right_out));
                viewFlipper.showPrevious();
                if (i == pageAcount - 1) {
                    btn_enter.setVisibility(View.VISIBLE);
                } else {
                    btn_enter.setVisibility(View.GONE);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置点位置
     *
     * @param i
     */
    private void setImage(int i) {
        for (int j = 0; j < 3; j++) {
            if (j != i) {
                imageViews[j].setImageResource(R.mipmap.ic_point_small);
            } else {
                imageViews[j].setImageResource(R.mipmap.ic_point_big);
            }
        }
    }
}
