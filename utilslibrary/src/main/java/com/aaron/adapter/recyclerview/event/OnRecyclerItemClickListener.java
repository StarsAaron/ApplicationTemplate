package com.aaron.adapter.recyclerview.event;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.aaron.adapter.recyclerview.base.MyViewHolder;

/**
 * 列表项点击监听封装
 * <p>
 * 使用方法
 * mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
 *
 * @Override public void onItemClick(RecyclerView.MyViewHolder viewHolder) {
 * RecyAdapter.MyViewHolder viewHolder1 = (RecyAdapter.MyViewHolder) viewHolder;
 * String tvString = viewHolder1.mTextView.getText().toString();
 * Toast.makeText(ListViewActivity.this, "逗逗~" + tvString, Toast.LENGTH_SHORT).show();
 * }
 * @Override public void onLongClick(RecyclerView.MyViewHolder viewHolder) {
 * Toast.makeText(ListViewActivity.this, "" + "讨厌，不要老是摸人家啦...", Toast.LENGTH_SHORT).show();
 * }
 * });
 */
public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetectorCompat;
    private RecyclerView mRecyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mGestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(),
                new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public abstract void onItemClick(MyViewHolder viewHolder);

    public abstract void onLongClick(MyViewHolder viewHolder);

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                MyViewHolder childViewHolder = (MyViewHolder)mRecyclerView.getChildViewHolder(childViewUnder);
                onItemClick(childViewHolder);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                MyViewHolder childViewHolder = (MyViewHolder)mRecyclerView.getChildViewHolder(childViewUnder);
                onLongClick(childViewHolder);
            }
        }
    }
}
