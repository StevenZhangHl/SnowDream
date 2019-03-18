package com.example.zealience.oneiromancy.ui.activity;

import android.os.Bundle;

import com.example.zealience.oneiromancy.R;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;

/**
 * 首页频道管理页
 */
public class ChannelManagerActivity extends BaseActivity {

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
    }
}
