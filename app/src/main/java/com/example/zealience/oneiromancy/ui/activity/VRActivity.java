package com.example.zealience.oneiromancy.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.VRPhotoEntity;
import com.example.zealience.oneiromancy.mvp.contract.VRcontract;
import com.example.zealience.oneiromancy.mvp.model.VRModel;
import com.example.zealience.oneiromancy.mvp.presenter.VRPresenter;
import com.example.zealience.oneiromancy.ui.VRPhotoAdapter;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.steven.base.app.BaseApp;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.AssetsUtil;
import com.steven.base.widget.FloatView;

import java.util.ArrayList;
import java.util.List;

public class VRActivity extends BaseActivity<VRPresenter, VRModel> implements VRcontract.View, AppBarLayout.OnOffsetChangedListener, View.OnClickListener {
    private VrPanoramaView vrView;
    private RecyclerView recyclerviewVr;
    private AppBarLayout appbar_layout;
    private ImageView vr_small_view;
    private TitleBar toolbar;
    private NestedScrollView nested_scrollview;
    private VRPhotoAdapter vrPhotoAdapter;
    private List<VRPhotoEntity> vrPhotoEntities = new ArrayList<>();
    private int currentPosition = 0;
    private FloatView floatView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vr;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        vrView = (VrPanoramaView) findViewById(R.id.vr_view);
        recyclerviewVr = (RecyclerView) findViewById(R.id.recyclerview_vr);
        appbar_layout = (AppBarLayout) findViewById(R.id.appbar_layout);
        vr_small_view = (ImageView) findViewById(R.id.vr_small_view);
        nested_scrollview = (NestedScrollView) findViewById(R.id.nested_scrollview);
        floatView = (FloatView) findViewById(R.id.floatView);
        toolbar = (TitleBar) findViewById(R.id.toolbar);
        vrView.setTouchTrackingEnabled(true);
        vrView.setFullscreenButtonEnabled(true);
        vrView.setInfoButtonEnabled(false);
        vrView.setStereoModeButtonEnabled(false);
        recyclerviewVr.setLayoutManager(new LinearLayoutManager(this));
        vrPhotoAdapter = new VRPhotoAdapter(R.layout.item_vr_photo, new ArrayList<>());
        recyclerviewVr.setAdapter(vrPhotoAdapter);
        mPresenter.getVRPhotoData();
        recyclerviewVr.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (currentPosition == position) {
                    return;
                }
                VRPhotoEntity vrPhotoEntity = vrPhotoEntities.get(position);
                currentPosition = position;
                loadPanoramaImage(vrPhotoEntity);
            }
        });
        toolbar.setOnTitleBarListener(new OnTitleBarListener() {
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
        vr_small_view.setOnClickListener(this);
        appbar_layout.addOnOffsetChangedListener(this);
    }

    /**
     * 根据名字获取VR图片
     *
     * @param model
     */
    private void loadPanoramaImage(VRPhotoEntity model) {
        loadPanoramaImage(AssetsUtil.getBitmapFromAssets(VRActivity.this, model.getAssetName()));
    }

    private void loadPanoramaImage(Bitmap bitmap) {
        if (bitmap == null) return;
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        options.inputType = VrPanoramaView.Options.TYPE_MONO;
        vrView.loadImageFromBitmap(bitmap, options);
        vr_small_view.setImageResource(vrPhotoEntities.get(currentPosition).getResourceName());
    }

    @Override
    public void setVRPhotoData(List<VRPhotoEntity> vrPhotoDatas) {
        vrPhotoAdapter.setNewData(vrPhotoDatas);
        vrPhotoEntities.clear();
        vrPhotoEntities.addAll(vrPhotoDatas);
        loadPanoramaImage(vrPhotoDatas.get(currentPosition));
        floatView.setmDragView(vr_small_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        vrView.resumeRendering();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vrView.pauseRendering();
    }

    @Override
    protected void onDestroy() {
        vrView.shutdown();
        super.onDestroy();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            //完全展开状态
            vr_small_view.setVisibility(View.GONE);
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            //折叠状态
            vr_small_view.setVisibility(View.VISIBLE);
        } else {
            //中间状态
            vr_small_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == vr_small_view) {
        }
    }
}
