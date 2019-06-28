package com.snow.weevideo.module.video.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.snow.weevideo.R;
import com.snow.weevideo.module.video.adapter.VideoMainAdapter;
import com.snow.weevideo.module.video.activity.VideoDetailActivity;
import com.snow.weevideo.module.video.entity.VideoEntity;
import com.steven.base.util.RecyclerView.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @user steven
 * @createDate 2019/6/24 16:52
 * @description 视频主界面
 */
public class VideoMainFragment extends SupportFragment implements OnRefreshListener, OnRefreshLoadMoreListener {
    private RecyclerView recyclerviewVideo;
    private SmartRefreshLayout refreshVideo;
    private VideoMainAdapter videoMainAdapter;
    private StaggeredGridLayoutManager layoutManager;
    private int column = 2;
    private int videoheight;
    private int currentPage = 1;
    private List<VideoEntity> mData = new ArrayList<>();

    public static VideoMainFragment getInstance(String title, int height) {
        VideoMainFragment videoMainFragment = new VideoMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("height", height);
        videoMainFragment.setArguments(bundle);
        return videoMainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_video_main, null);
        refreshVideo = (SmartRefreshLayout) view.findViewById(R.id.refresh_video_main);
        recyclerviewVideo = (RecyclerView) view.findViewById(R.id.recyclerview_video_main);
        return view;
    }

    @Override
    public void onLazyInitView(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        videoheight = getArguments().getInt("height");
        layoutManager = new StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);
        SpaceItemDecoration itemDecoration = new SpaceItemDecoration(5, column);
        recyclerviewVideo.addItemDecoration(itemDecoration);
        recyclerviewVideo.setLayoutManager(layoutManager);
        videoMainAdapter = new VideoMainAdapter(R.layout.item_video_main, new ArrayList<>(), videoheight / 2);
        recyclerviewVideo.setAdapter(videoMainAdapter);
        refreshVideo.setOnRefreshLoadMoreListener(this);
        refreshVideo.setRefreshHeader(new MaterialHeader(_mActivity));
        refreshVideo.autoRefresh();
        recyclerviewVideo.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                startActivity(new Intent(_mActivity, VideoDetailActivity.class));
            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refreshVideo.finishRefresh(500);
        currentPage = 1;
        refreshData();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        loadMoreData();
    }

    private void refreshData() {
        mData.clear();
        getData();
        videoMainAdapter.setNewData(mData);
    }

    private void loadMoreData() {
        currentPage++;
        if (currentPage == 5) {
            refreshVideo.finishLoadMore(0, true, true);
            return;
        }
        getData();
        videoMainAdapter.setNewData(mData);
        refreshVideo.finishLoadMore(300);
    }

    private void getData() {
        for (int i = 0; i < 20; i++) {
            VideoEntity info = new VideoEntity();
            info.setId(i);
            info.setPraiseNum(i * 250);
            info.setUserHeadUrl("");
            info.setPreviewUrl("");
            mData.add(info);
        }
    }
}
