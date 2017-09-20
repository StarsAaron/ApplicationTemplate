package com.aaron.adapter.normalListview.base;

import com.aaron.adapter.normalListview.ViewHolder;

public interface ItemViewDelegate<T> {

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(ViewHolder holder, T t, int position);


}
