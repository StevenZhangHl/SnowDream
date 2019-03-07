package com.example.zealience.oneiromancy.ui.fragment;

import android.os.Bundle;

import com.example.zealience.oneiromancy.R;
import com.steven.base.base.BaseFragment;

/**
 * @user steven
 * @createDate 2019/3/7 13:17
 * @description 新闻页
 */
public class NewsFragment extends BaseFragment {
    public static NewsFragment getInstance(String title) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onError(String msg) {

    }
}
