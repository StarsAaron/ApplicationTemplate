package com.aaron.applicationtemplate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aaron.applicationtemplate.R;

import java.util.List;


/**
 *
 * RecyclerView.Adapter 使用例子
 * Created by Aaron on 2017/9/22.
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyBaseViewHolder>{
    private List<String> stringList;
    private Context context;

    public MyRecycleViewAdapter(Context context,List<String> stringList){
        this.stringList = stringList;
        this.context = context;
    }

    @Override
    public MyBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview,null);
        return new MyBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyBaseViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    class MyBaseViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_item;

        public MyBaseViewHolder(View itemView) {
            super(itemView);
            tv_item = (TextView) itemView.findViewById(R.id.tv_item);
        }

        public void setData(int position){
            tv_item.setText(stringList.get(position));
        }
    }
}
