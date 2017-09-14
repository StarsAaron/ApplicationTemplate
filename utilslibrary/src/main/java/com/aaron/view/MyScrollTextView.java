package com.aaron.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * 名称：AbScrollTextView.java
 * 描述：跑马灯一直跑
 */
public class MyScrollTextView extends TextView {

    /**
     * Instantiates a new ab scroll text view.
     *
     * @param context the context
     */
    public MyScrollTextView(Context context) {
        super(context);
    }

    /**
     * Instantiates a new ab scroll text view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public MyScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Instantiates a new ab scroll text view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public MyScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 描述：设置为焦点，能一直滚动.
     */
    @Override
    public boolean isFocused() {
        return true;
    }

}
