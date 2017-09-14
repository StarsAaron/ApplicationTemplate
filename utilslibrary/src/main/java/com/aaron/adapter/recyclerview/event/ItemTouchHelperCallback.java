package com.aaron.adapter.recyclerview.event;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


import com.aaron.adapter.recyclerview.wrapper.MultiItemTypeAdapter;

import java.util.Collections;

/**
 * ItemTouchHelper 辅助列表项触摸监听
 * <p>
 * 使用方法：
 * ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback(mRecyAdapter);
 * ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
 * itemTouchHelper.attachToRecyclerView(mRecyclerView);
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    RecyclerView.Adapter mAdapter;
    boolean isSwipeEnable;
    boolean isFirstDragUnable;

    public ItemTouchHelperCallback(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        isSwipeEnable = true;
        isFirstDragUnable = false;
    }

    public ItemTouchHelperCallback(RecyclerView.Adapter adapter, boolean isSwipeEnable, boolean isFirstDragUnable) {
        mAdapter = adapter;
        this.isSwipeEnable = isSwipeEnable;
        this.isFirstDragUnable = isFirstDragUnable;
    }

    /**
     * 用于设置是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向，有以下两种情况：
     * <p>
     * 如果是列表类型的 RecyclerView，拖拽只有 UP、DOWN 两个方向
     * <p>
     * 如果是网格类型的则有 UP、DOWN、LEFT、RIGHT 四个方向
     * <p>
     * dragFlags 是拖拽标志，
     * swipeFlags 是滑动标志，
     * swipeFlags 都设置为0，暂时不考虑滑动相关操作。
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    /**
     * 如果设置了相关的 dragFlags，那么当长按 item 的时候就会进入拖拽并在拖拽过程中不断回调 onMove() 方法
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (isFirstDragUnable && toPosition == 0) {
            return false;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(((MultiItemTypeAdapter) mAdapter).getDataList(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(((MultiItemTypeAdapter) mAdapter).getDataList(), i, i - 1);
            }
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    /**
     * 滑动删除
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int adapterPosition = viewHolder.getAdapterPosition();
        mAdapter.notifyItemRemoved(adapterPosition);
        ((MultiItemTypeAdapter) mAdapter).getDataList().remove(adapterPosition);
    }

    /**
     * 实现被拖曳的 item 有背景颜色加深起到强调的视觉效果
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
    }

    /**
     * 是否长按不可以拖动
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return !isFirstDragUnable;
    }

    /**
     * 是否不能滑动
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipeEnable;
    }
}
