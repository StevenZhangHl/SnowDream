package com.example.zealience.oneiromancy.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.constant.KeyConstant;
import com.example.zealience.oneiromancy.entity.NewsEntity;
import com.example.zealience.oneiromancy.mvp.contract.NewsContract;
import com.example.zealience.oneiromancy.mvp.model.NewsModel;
import com.example.zealience.oneiromancy.mvp.presenter.NewsPresenter;
import com.example.zealience.oneiromancy.ui.NewsListAdapter;
import com.example.zealience.oneiromancy.ui.activity.WebViewActivity;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @user steven
 * @createDate 2019/3/7 15:39
 * @description 自定义
 */
public class NewsChildFragment extends SupportFragment implements NewsContract.View, OnRefreshListener {

    private NewsPresenter mPresenter;
    private NewsModel mModel;
    private ShimmerRecyclerView recyclerviewNewsList;
    private SmartRefreshLayout refreshNews;
    private NewsListAdapter newsListAdapter;
    private List<NewsEntity> mNewsList = new ArrayList<>();
    private String title = "top";

    public static NewsChildFragment getInstance(String title) {
        NewsChildFragment childFragment = new NewsChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        childFragment.setArguments(bundle);
        return childFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_child, null);
        refreshNews = (SmartRefreshLayout) view.findViewById(R.id.refresh__news);
        recyclerviewNewsList = (ShimmerRecyclerView) view.findViewById(R.id.recyclerview_news_list);
        mPresenter = new NewsPresenter();
        mModel = new NewsModel();
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        initPresenter();
        initView(savedInstanceState);
    }

    private void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    private void initView(Bundle savedInstanceState) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(_mActivity.getResources().getDrawable(R.drawable.list_divider_1dp));
        recyclerviewNewsList.addItemDecoration(dividerItemDecoration);
        recyclerviewNewsList.setLayoutManager(new LinearLayoutManager(_mActivity));
        newsListAdapter = new NewsListAdapter(new ArrayList<>());
        recyclerviewNewsList.setAdapter(newsListAdapter);
        recyclerviewNewsList.showShimmerAdapter();
        refreshNews.setOnRefreshListener(this);
        refreshNews.setRefreshHeader(new MaterialHeader(_mActivity));
        refreshNews.autoRefresh();
        title = getArguments().getString("title");
    }

    @Override
    public void setNewsList(List<NewsEntity> newsEntities) {
        recyclerviewNewsList.hideShimmerAdapter();
        recyclerviewNewsList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(KeyConstant.URL_KEY, mNewsList.get(position).getUrl());
                bundle.putBoolean(KeyConstant.ISSHOW_SHARE_KEY, true);
                WebViewActivity.startActivity(_mActivity, bundle);
            }
        });
        refreshNews.finishRefresh();
        newsListAdapter.setNewData(newsEntities);
        mNewsList.clear();
        mNewsList.addAll(newsEntities);
    }

    @Override
    public void onError(String msg) {
        refreshNews.finishRefresh();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.getNewsList(mPresenter.getNewsTypeByName(title));
    }
}
