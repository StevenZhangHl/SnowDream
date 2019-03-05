package com.example.zealience.oneiromancy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.ui.widget.ScrollPaintView;
import com.steven.base.base.BaseActivity;

public class UserPhotoDetailActivity extends BaseActivity {
    /**
     * 需要显示的IV
     */
    private ImageView iv_paint;
    /**
     * 画轴
     */
    private ScrollPaintView mScrollPaintView;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, UserPhotoDetailActivity.class);
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
        showTitle("个人信息");
        setWhiteStatusBar(R.color.white);
        iv_paint = (ImageView) findViewById(R.id.iv_paint);
        mScrollPaintView = (ScrollPaintView) findViewById(R.id.mScrollPaintView);
        mScrollPaintView.setPaintView(iv_paint);
        mScrollPaintView.setScrollPaintCompleteListener(new ScrollPaintView.ScrollPaintCompleteListener() {
            @Override
            public void onScrollTouch(TextView tv) {
                iv_paint.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScrollTop(TextView tv) {
                if (View.VISIBLE == iv_paint.getVisibility()) {
                    iv_paint.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrollBottom(TextView tv) {
                // 延迟800毫秒重置位置
                new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message message) {
                        mScrollPaintView.replaceScroll();
                        return false;
                    }
                }).sendEmptyMessageDelayed(0, 600);
            }

            @Override
            public void onScrollMove(TextView tv) {

            }
        });
    }
}
