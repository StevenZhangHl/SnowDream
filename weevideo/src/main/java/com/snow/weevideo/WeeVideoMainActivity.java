package com.snow.weevideo;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.steven.base.ARouterPath;
import com.steven.base.base.BaseActivity;

@Route(path = ARouterPath.WEE_VIDEO_MAIN_ACTIVITY)
public class WeeVideoMainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_wee_video_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}
