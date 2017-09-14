package com.aaron.applicationtemplate.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aaron.applicationtemplate.R;
import com.library.FancyButton;

public class DiyButtonActivity extends Activity {
    private LinearLayout ll_fancy_container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_button);
        ll_fancy_container = (LinearLayout) findViewById(R.id.ll_fancy_container);
        initDiyButton();
    }

    /**
     * DiyButton 显示事例
     */
    private void initDiyButton() {
        //代码生成
        FancyButton facebookLoginBtn = FancyButton.init(this)
                .setText("Login with Facebook")
                .setDefaultBackgroundColor(Color.parseColor("#3b5998"))
                .setFocusBackgroundColor(Color.parseColor("#5474b8"))
                .setTextSize(17)
                .setRadius(5)
                .setIconResource("\uf082")
                .setIconPosition(FancyButton.POSITION_LEFT)
                .setFontIconSize(30);

        FancyButton foursquareBtn = FancyButton.init(this)
                .setText("Check in")
                .setDefaultBackgroundColor(Color.parseColor("#0072b1"))
                .setFocusBackgroundColor(Color.parseColor("#228fcb"))
                .setTextSize(17)
                .setRadius(5)
                .setIconResource("\uf180")
                .setIconPosition(FancyButton.POSITION_TOP)
                .setFontIconSize(30);

        FancyButton installBtn = FancyButton.init(this)
                .setText("Google play install")
                .setDefaultBackgroundColor(Color.parseColor("#a4c639"))
                .setFocusBackgroundColor(Color.parseColor("#bfe156"))
                .setTextSize(17)
                .setRadius(5)
                .setIconPadding(0, 30, 0, 0);

        FancyButton signupBtn = FancyButton.init(this)
                .setText("Repost the song")
                .setIconResource(R.drawable.soundcloud)
                .setDefaultBackgroundColor(Color.parseColor("#ff8800"))
                .setFocusBackgroundColor(Color.parseColor("#ffa43c"))
                .setTextSize(20)
                .setCustomTextFont("robotothin.ttf")
                .setIconPadding(10, 0, 10, 0);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 10);

        ll_fancy_container.addView(facebookLoginBtn, layoutParams);
        ll_fancy_container.addView(foursquareBtn, layoutParams);
        ll_fancy_container.addView(installBtn, layoutParams);
        ll_fancy_container.addView(signupBtn, layoutParams);
    }
}
