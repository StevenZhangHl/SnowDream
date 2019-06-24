package com.example.zealience.oneiromancy.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.example.zealience.oneiromancy.R;
import com.steven.base.util.UserHelper;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.ProviderUtil;
import com.steven.base.util.ToastUitl;
import com.steven.base.widget.CustomDialog;
import com.steven.base.widget.CustomLayoutGroup;

public class SetingActivity extends BaseActivity implements View.OnClickListener {

    private SwitchCompat switch_notification;
    private CustomLayoutGroup rl_set_sound;
    private CustomLayoutGroup rl_feedback;
    private CustomLayoutGroup rl_about;
    /**
     * 退出登录按钮
     */
    private Button bt_login_out;
    private CustomDialog customDialog;

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
        switch_notification = (SwitchCompat) findViewById(R.id.switch_notification);
        rl_set_sound = (CustomLayoutGroup) findViewById(R.id.rl_set_sound);
        rl_feedback = (CustomLayoutGroup) findViewById(R.id.rl_feedback);
        rl_about = (CustomLayoutGroup) findViewById(R.id.rl_about);
        bt_login_out = (Button) findViewById(R.id.bt_login_out);
        initListenter();
    }

    private void initListenter() {
        switch_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ToastUitl.showShort("打开通知");
                } else {
                    ToastUitl.showShort("关闭通知");
                }
            }
        });
        rl_set_sound.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        bt_login_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == rl_set_sound) {
            ProviderUtil.goToSetNotification(this);
        }
        if (v == bt_login_out) {
            showDailog();
        }
        if (v == rl_about) {
            startActivity(AboutActivity.class);
        }
    }

    private void showDailog() {
        customDialog = new CustomDialog(this);
        customDialog.showTitle("退出提示")
                .setButtonTexts("取消", "确定")
                .showContent("确定退出吗？")
                .setOnClickListene(new CustomDialog.OnClickListener() {
                    @Override
                    public void onLeftClick() {
                        customDialog.dismiss();
                    }

                    @Override
                    public void onRightClick() {
                        customDialog.dismiss();
                        UserHelper.clearUseInfo(SetingActivity.this);
                        AppManager.getAppManager().finishAllActivity();
                        startActivity(LoginActivity.class);
                    }
                })
                .show();
    }
}
