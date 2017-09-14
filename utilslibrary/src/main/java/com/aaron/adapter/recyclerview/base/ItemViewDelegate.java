package com.aaron.adapter.recyclerview.base;


/**
 * 多样式列表项代理接口
 */
public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(MyViewHolder holder, T t, int position);
}
