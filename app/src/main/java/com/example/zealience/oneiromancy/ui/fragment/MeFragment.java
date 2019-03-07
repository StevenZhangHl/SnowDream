package com.example.zealience.oneiromancy.ui.fragment;

import android.os.Bundle;

import com.example.zealience.oneiromancy.R;
import com.steven.base.base.BaseFragment;

/**
 * @user steven
 * @createDate 2019/3/6 18:02
 * @description 我的页面
 */
public class MeFragment extends BaseFragment {
    public static MeFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        MeFragment meFragment = new MeFragment();
        meFragment.setArguments(bundle);
        return meFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
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
