package com.example.zealience.oneiromancy.ui.activity;

import android.os.Bundle;

import com.example.zealience.oneiromancy.R;
import com.steven.base.base.BaseActivity;

public class CustomViewActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_view;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
       showTitle("展示自定义");
       setWhiteStatusBar(R.color.white);
    }
}
