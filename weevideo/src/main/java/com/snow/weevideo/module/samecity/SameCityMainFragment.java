package com.snow.weevideo.module.samecity;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.snow.weevideo.R;
import com.snow.weevideo.adapter.SameCityAdapter;
import com.snow.weevideo.module.samecity.entity.SameCityEntity;
import com.steven.base.util.RecyclerView.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @user steven
 * @createDate 2019/6/24 16:52
 * @description 同城主界面
 */
public class SameCityMainFragment extends SupportFragment implements OnRefreshLoadMoreListener {
    private RecyclerView recyclerview_same_city_main;
    private SmartRefreshLayout refresh_same_city_main;
    private SameCityAdapter sameCityAdapter;
    private StaggeredGridLayoutManager layoutManager;
    private int column = 2;
    private int videoheight;
    private int currentPage = 1;
    private List<SameCityEntity> mData = new ArrayList<>();

    public static SameCityMainFragment getInstance(String title, int height) {
        SameCityMainFragment sameCityMainFragment = new SameCityMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("height", height);
        sameCityMainFragment.setArguments(bundle);
        return sameCityMainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_samecity_main, null);
        refresh_same_city_main = (SmartRefreshLayout) view.findViewById(R.id.refresh_same_city_main);
        recyclerview_same_city_main = (RecyclerView) view.findViewById(R.id.recyclerview_same_city_main);
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
        recyclerview_same_city_main.addItemDecoration(itemDecoration);
        recyclerview_same_city_main.setLayoutManager(layoutManager);
        sameCityAdapter = new SameCityAdapter(R.layout.item_same_city_main, new ArrayList<>(), videoheight / 2);
        recyclerview_same_city_main.setAdapter(sameCityAdapter);
        refresh_same_city_main.setOnRefreshLoadMoreListener(this);
        refresh_same_city_main.setRefreshHeader(new MaterialHeader(_mActivity));
        refresh_same_city_main.autoRefresh();
        recyclerview_same_city_main.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
            }
        });
        recyclerview_same_city_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();//防止滑到顶端时出现空白
            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refresh_same_city_main.finishRefresh(500);
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
        sameCityAdapter.setNewData(mData);
    }

    private void loadMoreData() {
        currentPage++;
        if (currentPage == 5) {
            refresh_same_city_main.finishLoadMore(0, true, true);
            return;
        }
        getData();
        sameCityAdapter.setNewData(mData);
        refresh_same_city_main.finishLoadMore(300);
    }

    private void getData() {
        for (int i = 0; i < 20; i++) {
            SameCityEntity info = new SameCityEntity();
            info.setId(i);
            info.setDistance(i * 250);
            info.setUserHeadUrl("");
            info.setPreviewUrl("");
            if (i % 3 == 0) {
                info.setFirstTitle("我要飞");
                info.setSencondTitle("快来带我飞");
            } else if (i % 5 == 0) {
                info.setFirstTitle("");
                info.setSencondTitle("看我七十二变");
            } else {
                info.setFirstTitle("");
                info.setSencondTitle("");
            }
            mData.add(info);
        }
    }
}
