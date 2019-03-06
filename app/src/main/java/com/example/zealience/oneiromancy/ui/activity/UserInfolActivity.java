package com.example.zealience.oneiromancy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.ui.widget.ScrollPaintView;
import com.example.zealience.oneiromancy.util.UserHelper;
import com.steven.base.app.BaseApp;
import com.steven.base.base.BaseActivity;

public class UserInfolActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 需要显示的IV
     */
    private ImageView iv_paint;
    /**
     * 画轴
     */
    private Button bt_login_out;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, UserInfolActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_photo_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        BaseApp.getInstance().addActivity(this);
        showTitle("个人信息");
        setWhiteStatusBar(R.color.white);
        bt_login_out = (Button) findViewById(R.id.bt_login_out);
        bt_login_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        UserHelper.clearUseInfo(this);
        BaseApp.getInstance().finishAllActivity();
        startActivity(LoginActivity.class);
    }
}
