package com.example.zealience.oneiromancy.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.DreamTypeEntity;
import com.example.zealience.oneiromancy.ui.ChannelAdapter;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.RecyclerView.ItemDragHelperCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页频道管理页
 */
public class ChannelManagerActivity extends BaseActivity {
    private RecyclerView recyclerview_channel;
    private ChannelAdapter channelAdapter;
    public static final String mMyChannelKey = "currentType";
    public static final String mOhterChannelKey = "otherType";

    @Override
    public int getLayoutId() {
        return R.layout.activity_channel_manager;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        showTitle("我的频道");
        recyclerview_channel = (RecyclerView) findViewById(R.id.recyclerview_channel);
        initRecyclerView();
    }

    private void initRecyclerView() {
        List<DreamTypeEntity> mMyChannelItems = new ArrayList<>();
        List<DreamTypeEntity> mOtherChannelItems = new ArrayList<>();
        mMyChannelItems = getIntent().getExtras().getParcelableArrayList(mMyChannelKey);
        mOtherChannelItems = getIntent().getExtras().getParcelableArrayList(mOhterChannelKey);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerview_channel.setLayoutManager(manager);
        ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerview_channel);
        channelAdapter = new ChannelAdapter(this, itemTouchHelper, mMyChannelItems, mOtherChannelItems);
        recyclerview_channel.setAdapter(channelAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = channelAdapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        channelAdapter.setmChannelItemClickListener(new ChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });
    }
}
