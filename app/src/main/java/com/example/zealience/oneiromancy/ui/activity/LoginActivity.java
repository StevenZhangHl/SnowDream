package com.example.zealience.oneiromancy.ui.activity;


import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.mvp.contract.LoginContract;
import com.example.zealience.oneiromancy.mvp.model.LoginModel;
import com.example.zealience.oneiromancy.mvp.presenter.LoginPresenter;
import com.example.zealience.oneiromancy.util.ShakeUtils;
import com.jaeger.library.StatusBarUtil;
import com.steven.base.app.BaseApp;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.Typefaces;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View, OnClickListener, ShakeUtils.OnShakeListener {

    private LinearLayout ll_content;
    private Button bt_login;
    private TextView tv_cancel;
    private EditText et_phone;
    private EditText et_password;
    private ShakeUtils shakeUtils;
    private LinearLayout ll_show_love;
    private TextView tv_show_love_one;
    private TextView tv_show_love_two;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        StatusBarUtil.setTranslucentForImageView(this, 100, null);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        bt_login = (Button) findViewById(R.id.bt_login);
        ll_show_love = (LinearLayout) findViewById(R.id.ll_show_love);
        tv_show_love_one = (TextView) findViewById(R.id.tv_show_love_one);
        tv_show_love_two = (TextView) findViewById(R.id.tv_show_love_two);
        bt_login.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        mPresenter.observerInput(et_phone, et_password);
        shakeUtils = new ShakeUtils(this);
        shakeUtils.setmOnShakeListener(this);
        startAnim();
    }

    private void startAnim() {
        ll_content.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -180);
        animation.setInterpolator(new AnticipateOvershootInterpolator());
        animation.setFillAfter(true);
        animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int top = ll_content.getTop() - 320;
                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.topMargin = top;
                ll_content.setLayoutParams(ll);
                ll_content.clearAnimation();
                ll_show_love.setVisibility(View.VISIBLE);
                Typeface typeface = Typefaces.get(LoginActivity.this, "showlove.ttf");
                tv_show_love_one.setTypeface(typeface);
                tv_show_love_two.setTypeface(typeface);
                YoYo.with(Techniques.FadeIn)
                        .interpolate(new LinearInterpolator())
                        .duration(5000)
                        .playOn(ll_show_love);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ll_content.startAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shakeUtils.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shakeUtils.onPause();
    }

    @Override
    public void inputSuccess(boolean isSuccess) {
        if (isSuccess) {
            bt_login.setBackgroundResource(R.drawable.bg_radio_333_2);
        } else {
            bt_login.setBackgroundResource(R.drawable.bg_radio_666_2);
        }
    }

    @Override
    public void loginSuccess() {
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void loginFailure() {

    }

    @Override
    public void onClick(View v) {
        if (v == tv_cancel) {
            finish();
        }
        if (v == bt_login) {
            String phone = et_phone.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            mPresenter.doLogin(phone, password);
        }
    }

    @Override
    protected void setFitsSystemWindows() {
    }

    @Override
    public void onShake() {
        et_phone.setText("520");
        et_password.setText("1314");
    }
}

