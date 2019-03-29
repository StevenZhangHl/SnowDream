package com.example.zealience.oneiromancy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.UserInfo;
import com.example.zealience.oneiromancy.util.UserHelper;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.KeyboardUtil;
import com.steven.base.util.TimeCountUtil;
import com.steven.base.util.ToastUitl;
import com.steven.base.widget.ClearEditText;
import com.steven.base.widget.RippleButton;
import com.youth.banner.WeakHandler;

public class ChangePhoneActivity extends BaseActivity implements View.OnClickListener, TimeCountUtil.OnTimeOutListener {

    /**
     * 请输入原手机号
     */
    private ClearEditText et_phone;
    /**
     * 请输入验证码
     */
    private ClearEditText et_code;
    /**
     * 获取验证码
     */
    private TextView tv_get_code;
    private RippleButton bt_next;

    private TimeCountUtil timeCountUtil;
    private String phone = "";
    private String code = "";
    private WeakHandler handler = new WeakHandler();
    private int delayTime = 1500;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ToastUitl.showTopToast(ChangePhoneActivity.this, "修改成功");
            UserInfo userInfo = UserHelper.getUserInfo(ChangePhoneActivity.this);
            userInfo.setPhone(phone);
            UserHelper.saveUserInfo(ChangePhoneActivity.this, userInfo);
            setResult(RESULT_OK);
            finish();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showTitle("修改手机号");
        et_phone = (ClearEditText) findViewById(R.id.et_phone);
        et_code = (ClearEditText) findViewById(R.id.et_code);
        tv_get_code = (TextView) findViewById(R.id.tv_get_code);
        bt_next = (RippleButton) findViewById(R.id.bt_next);
        initListener();
        timeCountUtil = new TimeCountUtil(tv_get_code, 60 * 1000, 1000);
        timeCountUtil.setOnTimeOutListener(this);
    }

    private void initListener() {
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isButtonClick();
                phone = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isButtonClick();
                code = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bt_next.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.closeAllKeybord(ChangePhoneActivity.this);
                bt_next.showLoadingButton();
                handler.postDelayed(runnable, delayTime);
            }
        });
        tv_get_code.setOnClickListener(this);
    }

    private boolean isButtonClick() {
        String phone = et_phone.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        if (phone.length() != 11) {
            bt_next.showGrayButton();
            return false;
        }
        if (code.length() != 6) {
            bt_next.showGrayButton();
            return false;
        }
        bt_next.showGreenButton();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == tv_get_code) {
            if (phone.length() != 11) {
                ToastUitl.showTopToast(ChangePhoneActivity.this, "请输入正确的手机号");
                return;
            }
            timeCountUtil.start();
        }
    }

    @Override
    public void timeOut() {
        tv_get_code.setText("重新发送");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timeCountUtil != null) {
            timeCountUtil.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
