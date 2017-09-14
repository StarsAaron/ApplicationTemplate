package com.aaron.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 名称：AbOuterListView.java
 * 描述：这个ListView不会与它里面有可滑动view的事件产生冲突
 */
public class MyOuterListView extends ListView {

    /**
     * The m gesture detector.
     */
    private GestureDetector mGestureDetector;

    /**
     * Instantiates a new ab outer list view.
     *
     * @param context the context
     */
    public MyOuterListView(Context context) {

        super(context);
        mGestureDetector = new GestureDetector(new YScrollDetector());
        setFadingEdgeLength(0);
    }

    /**
     * Instantiates a new ab outer list view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public MyOuterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(new YScrollDetector());
        setFadingEdgeLength(0);
    }

    /**
     * Instantiates a new ab outer list view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public MyOuterListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /* (non-Javadoc)
     * @see android.widget.AbsListView#onInterceptTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev);
    }

    /**
     * The Class YScrollDetector.
     */
    class YScrollDetector extends SimpleOnGestureListener {

        /* (non-Javadoc)
         * @see android.view.GestureDetector.SimpleOnGestureListener#onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float)
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }
}
