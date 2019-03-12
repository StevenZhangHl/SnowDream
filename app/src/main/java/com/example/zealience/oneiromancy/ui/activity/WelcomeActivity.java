package com.example.zealience.oneiromancy.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.util.UserHelper;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;
import com.steven.base.rx.RxHelper;
import com.steven.base.rx.RxManager;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class WelcomeActivity extends BaseActivity {
    private ImageView iv_splash;
    private int[] images = {R.mipmap.lanucher_three};
    private RxManager rxManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        iv_splash = (ImageView) findViewById(R.id.iv_splash);
        final int indenx = (int) (Math.random() * images.length);
        iv_splash.setImageResource(images[indenx]);
        rxManager = new RxManager();
        rxManager.add(Observable.timer(2, TimeUnit.SECONDS)
                .compose(RxHelper.<Long>applySchedulers())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (UserHelper.isLogin(WelcomeActivity.this)) {
                            startActivity(MainActivity.class);
                        } else {
                            startActivity(LoginActivity.class);
                        }
                        finish();
                    }
                }));
    }
}
