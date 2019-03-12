package com.example.zealience.oneiromancy.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.example.zealience.oneiromancy.R;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.bar.style.TitleBarLightStyle;
import com.steven.base.app.BaseApp;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;

public class SetingActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_seting;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        showTitle("设置");
        setWhiteStatusBar(R.color.white);
    }
}
