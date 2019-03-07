package com.example.zealience.oneiromancy.ui.fragment;

import android.os.Bundle;

import com.example.zealience.oneiromancy.R;
import com.steven.base.base.BaseFragment;

/**
 * @user steven
 * @createDate 2019/3/7 13:22
 * @description 历史上的今天
 */
public class HistoryTodayFragment extends BaseFragment {
    public static HistoryTodayFragment getInstance(String title) {
        HistoryTodayFragment historyTodayFragment = new HistoryTodayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        historyTodayFragment.setArguments(bundle);
        return historyTodayFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history_today;
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
