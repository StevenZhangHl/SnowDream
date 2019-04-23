package com.example.zealience.oneiromancy.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.UserInfo;
import com.example.zealience.oneiromancy.util.UserHelper;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.ToastUitl;
import com.steven.base.widget.RippleButton;
import com.youth.banner.WeakHandler;

public class SelectGenderActivity extends BaseActivity {

    private RadioGroup radio_group_gender;
    private RippleButton bt_save_gender;
    private RadioButton rb_man;
    private RadioButton rb_woman;
    private int gender = 1;
    private WeakHandler handler = new WeakHandler();
    private int delayTime = 1000;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ToastUitl.showShort("修改成功");
            UserInfo userInfo = UserHelper.getUserInfo(SelectGenderActivity.this);
            userInfo.setGender(gender);
            UserHelper.saveUserInfo(SelectGenderActivity.this, userInfo);
            setResult(RESULT_OK);
            finish();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_gender;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showTitle("设置性别");
        radio_group_gender = (RadioGroup) findViewById(R.id.radio_group_gender);
        rb_woman = (RadioButton) findViewById(R.id.rb_woman);
        rb_man = (RadioButton) findViewById(R.id.rb_man);
        bt_save_gender = (RippleButton) findViewById(R.id.bt_save_gender);
        radio_group_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_man) {
                    gender = 1;
                } else if (checkedId == R.id.rb_woman) {
                    gender = 2;
                }
            }
        });
        bt_save_gender.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_save_gender.showLoadingButton();
                handler.postDelayed(runnable, delayTime);
            }
        });
        bt_save_gender.showGreenButton();
        setDefaultData();
    }

    private void setDefaultData() {
        UserInfo userInfo = UserHelper.getUserInfo(SelectGenderActivity.this);
        if (userInfo.getGender() == 1) {
            rb_man.setChecked(true);
        } else if (userInfo.getGender() == 2) {
            rb_woman.setChecked(true);
        }
    }

}
