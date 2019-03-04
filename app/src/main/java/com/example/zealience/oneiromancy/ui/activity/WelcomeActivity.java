package com.example.zealience.oneiromancy.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.zealience.oneiromancy.MainActivity;
import com.example.zealience.oneiromancy.R;
import com.steven.base.rx.RxHelper;
import com.steven.base.rx.RxManager;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class WelcomeActivity extends AppCompatActivity {
    private ImageView iv_splash;
    private int[] images = {R.mipmap.lanucher_one, R.mipmap.lanucher_two};
    private RxManager rxManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        iv_splash = (ImageView) findViewById(R.id.iv_splash);
        final int indenx = (int) (Math.random() * images.length);
        iv_splash.setImageResource(images[indenx]);
        rxManager = new RxManager();
        rxManager.add(Observable.timer(2, TimeUnit.SECONDS)
                .compose(RxHelper.<Long>applySchedulers())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Intent intent = new Intent();
                        intent.setClass(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }));
    }
}
