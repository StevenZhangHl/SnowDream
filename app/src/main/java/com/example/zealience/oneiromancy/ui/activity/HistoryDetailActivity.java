package com.example.zealience.oneiromancy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.api.ApiManager;
import com.example.zealience.oneiromancy.api.ApiRequest;
import com.example.zealience.oneiromancy.constant.KeyConstant;
import com.example.zealience.oneiromancy.entity.HistoryDetailEntity;
import com.example.zealience.oneiromancy.entity.HistoryEntity;
import com.example.zealience.oneiromancy.ui.HistoryDetailPhotoAdapter;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.steven.base.base.BaseActivity;
import com.steven.base.rx.BaseObserver;
import com.steven.base.rx.RxHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetailActivity extends BaseActivity {
    private TitleBar mToolbar;
    private CollapsingToolbarLayout mToolbarLayout;
    private AppBarLayout mAppBar;
    /**  */
    private TextView mTvHistoryDetailContent;
    private RecyclerView mRecyclerviewHistoryDetailPhoto;
    private FloatingActionButton mFab;
    private HistoryDetailPhotoAdapter photoAdapter;

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, HistoryDetailActivity.class);
        intent.putExtra(KeyConstant.HISITORY_BUNDLE_KEY, bundle);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_history_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolbar = (TitleBar) findViewById(R.id.toolbar);
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mAppBar = (AppBarLayout) findViewById(R.id.app_bar);
        mTvHistoryDetailContent = (TextView) findViewById(R.id.tv_history_detail_content);
        mRecyclerviewHistoryDetailPhoto = (RecyclerView) findViewById(R.id.recyclerview_history_detail_photo);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        HistoryEntity entity = (HistoryEntity) getIntent().getBundleExtra(KeyConstant.HISITORY_BUNDLE_KEY).getSerializable(KeyConstant.HISITORY_ENTITY_KEY);
        mToolbarLayout.setTitle(entity.getTitle());
        mToolbar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
        initRecyclerView();
        getDetailData(entity.getE_id());
    }

    private void initRecyclerView() {
        photoAdapter = new HistoryDetailPhotoAdapter(R.layout.item_history_detail_photo, new ArrayList<>());
        mRecyclerviewHistoryDetailPhoto.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerviewHistoryDetailPhoto.setAdapter(photoAdapter);
    }

    private void getDetailData(String e_id) {
        mRxManager.add(ApiManager.getInstance().getHistoryById(ApiRequest.getHistoryListById(e_id)).compose(RxHelper.getResult()).subscribeWith(new BaseObserver<List<HistoryDetailEntity>>(this, false) {
            @Override
            protected void onSuccess(List<HistoryDetailEntity> historyDetailEntities) {
                if (historyDetailEntities != null) {
                    HistoryDetailEntity entity = historyDetailEntities.get(0);
                    mTvHistoryDetailContent.setText(entity.getContent());
                    photoAdapter.setNewData(entity.getPicUrl());
                }
            }

            @Override
            protected void onError(String message) {

            }
        }));
    }
}
