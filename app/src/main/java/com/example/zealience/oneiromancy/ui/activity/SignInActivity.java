package com.example.zealience.oneiromancy.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.AnimationUtils;

public class SignInActivity extends BaseActivity {
    private TextView tv_jifen;
    private RecyclerView recyclerview_sign_note;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        showTitle("签到");
        tv_jifen = (TextView) findViewById(R.id.tv_jifen);
        AnimationUtils.raiseNumberByInt(0, 8888, 2000, tv_jifen);
        recyclerview_sign_note = (RecyclerView)findViewById(R.id.recyclerview_sign_note);
        recyclerview_sign_note.setLayoutManager(new LinearLayoutManager(this));

    }
}
