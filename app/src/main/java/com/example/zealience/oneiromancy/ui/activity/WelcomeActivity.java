package com.example.zealience.oneiromancy.ui.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.util.UserHelper;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;
import com.steven.base.rx.RxHelper;
import com.steven.base.rx.RxManager;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;


import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class WelcomeActivity extends BaseActivity {
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
        rxManager = new RxManager();
        rxManager.add(Observable.timer(1, TimeUnit.SECONDS)
                .compose(RxHelper.<Long>applySchedulers())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (Build.VERSION.SDK_INT >= 23) {
                            postPermission();
                        } else {
                            doMain();
                        }
                    }
                }));
    }

    /**
     * 动态请求读取权限
     */
    private void postPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION})
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        doMain();
                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                if (AndPermission.hasAlwaysDeniedPermission(WelcomeActivity.this, data)) {
                    // 打开权限设置页
                    AndPermission.permissionSetting(WelcomeActivity.this).execute();
                    return;
                } else {
                    finish();
                }
            }
        }).start();
    }

    private void doMain() {
        if (UserHelper.isLogin(WelcomeActivity.this)) {
            startActivity(MainActivity.class);
        } else {
            startActivity(LoginActivity.class);
        }
        finish();
    }
}
