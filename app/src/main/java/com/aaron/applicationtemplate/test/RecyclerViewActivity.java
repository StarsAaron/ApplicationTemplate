package com.aaron.applicationtemplate.test;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.adapter.recyclerview.wrapper.CommonAdapter;
import com.aaron.adapter.recyclerview.base.MyViewHolder;
import com.aaron.adapter.recyclerview.decoration.DividerGridItemDecoration;
import com.aaron.adapter.recyclerview.event.ItemTouchHelperCallback;
import com.aaron.adapter.recyclerview.event.OnRecyclerItemClickListener;
import com.aaron.applicationtemplate.R;
import com.aaron.applicationtemplate.ui.BaseActivity;

import java.util.Arrays;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity {
    private List<String> sStringList = Arrays.asList("香蕉", "苹果", "草莓", "橙子",
            "柠檬", "梨", "樱桃", "哈密瓜", "猕猴桃", "葡萄", "苹果", "草莓", "橙子",
            "柠檬", "梨", "樱桃", "哈密瓜", "猕猴桃", "葡萄");
    private RecyclerView mRecyclerView;
    private CommonAdapter mRecyAdapter;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = (RecyclerView) findViewById(R.id.recy);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new DividerListItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration());
        mRecyclerView.setHasFixedSize(true);

        mRecyAdapter = new CommonAdapter(this,R.layout.item_listview,sStringList){
            @Override
            protected void convert(MyViewHolder holder, Object o, int position) {
                ((TextView)holder.getView(R.id.tv_item)).setText(sStringList.get(position));
            }
        };
        mRecyclerView.setAdapter(mRecyAdapter);
        //拖动
        ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback(mRecyAdapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        //点击
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(MyViewHolder viewHolder) {
                String tvString = ((TextView)viewHolder.getView(R.id.tv_item)).getText().toString();
                Toast.makeText(RecyclerViewActivity.this, "逗逗~" + tvString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(MyViewHolder viewHolder) {
                Toast.makeText(RecyclerViewActivity.this, "" + "讨厌，不要老是摸人家啦...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
