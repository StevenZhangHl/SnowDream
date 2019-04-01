package com.example.zealience.oneiromancy.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.util.AppUtil;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.ProviderUtil;
import com.steven.base.util.ToastUitl;
import com.steven.base.widget.CustomLayoutGroup;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 版本号：反反复复
     */
    private TextView tv_version;
    private CustomLayoutGroup rl_check_update;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showTitle("关于");
        tv_version = (TextView) findViewById(R.id.tv_version);
        rl_check_update = (CustomLayoutGroup) findViewById(R.id.rl_check_update);
        tv_version.setText(AppUtil.getVersionName(this));
        rl_check_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == rl_check_update) {
            ToastUitl.showTopToast(this, "已是最新版本!");
        }
    }
}
