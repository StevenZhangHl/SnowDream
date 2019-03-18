package com.example.zealience.oneiromancy.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.amapmap.AmapHelper;
import com.example.amapmap.AmapLocationChangeListener;
import com.example.amapmap.OnCameraChangeListener;
import com.example.amapmap.OnPoiSearchListener;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.ui.NearPoiAddressAdapter;
import com.hjq.bar.OnTitleBarListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.steven.base.base.BaseActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.List;

public class ShowAmapActivity extends BaseActivity implements AmapLocationChangeListener, OnCameraChangeListener, OnPoiSearchListener, OnTitleBarListener {

    private MapView amapView;
    private RecyclerView recyclerviewNearAddress;
    private ImageView iv_location_marker;
    private SmartRefreshLayout refresh_address;
    //初始化地图控制器对象
    private AMap aMap;
    private AmapHelper amapHelper;
    private NearPoiAddressAdapter poiAddressAdapter;
    private int currentPage = 0;
    /**
     * 当前中心店的经纬度
     */
    private LatLng currentLatLng;
    /**
     * 当前选择的地址信息
     */
    private PoiItem selectAddressInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_amap;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showTitle("选择地址");
        getTitlebar().setRightTitle("确定");
        getTitlebar().setOnTitleBarListener(this);
        amapView = (MapView) findViewById(R.id.amap_view);
        recyclerviewNearAddress = (RecyclerView) findViewById(R.id.recyclerview_near_address);
        iv_location_marker = (ImageView) findViewById(R.id.iv_location_marker);
        refresh_address = (SmartRefreshLayout) findViewById(R.id.refresh_address);
        initRecyclerView();
        if (Build.VERSION.SDK_INT >= 23) {
            AndPermission.with(this)
                    .runtime()
                    .permission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION})
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            finish();
                        }
                    }).onGranted(new Action<List<String>>() {
                @Override
                public void onAction(List<String> data) {
                    initAMap(savedInstanceState);
                }
            }).start();
        } else {
            initAMap(savedInstanceState);
        }

    }

    private void initAMap(Bundle savedInstanceState) {
        amapHelper = new AmapHelper(ShowAmapActivity.this, amapView);
        amapHelper.setLocationChangeListener(ShowAmapActivity.this);
        amapHelper.setPoiSearchListener(ShowAmapActivity.this);
        amapView.onCreate(savedInstanceState);
        amapHelper.startLocation();
    }

    private void initRecyclerView() {
        recyclerviewNearAddress.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.list_divider_1dp));
        recyclerviewNearAddress.addItemDecoration(itemDecoration);
        poiAddressAdapter = new NearPoiAddressAdapter(R.layout.item_select_address, new ArrayList<>());
        recyclerviewNearAddress.setAdapter(poiAddressAdapter);
        recyclerviewNearAddress.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                poiAddressAdapter.clickPosition(position);
                selectAddressInfo = mPoiItemLists.get(position);
            }
        });
        refresh_address.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                amapHelper.setPoiSearchCenter(new LatLng(currentLatLng.latitude, currentLatLng.longitude), currentPage);
                refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        amapView.onDestroy();
        if (amapHelper != null) {
            amapHelper.destroyLocation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        amapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        amapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        amapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        iv_location_marker.setVisibility(View.VISIBLE);
        amapHelper.setOnCameraChangeListener(ShowAmapActivity.this);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        currentPage = 0;
        currentLatLng = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
        amapHelper.setPoiSearchCenter(new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude), currentPage);
    }

    private List<PoiItem> mPoiItemLists = new ArrayList<>();
    private int totalPage;

    @Override
    public void onPoiSearched(PoiResult poiResult) {
        if (currentPage == 0) {
            mPoiItemLists.clear();
            poiAddressAdapter.setNewData(poiResult.getPois());
            int i = poiResult.getPageCount() % 10;
            if (i != 0) {
                totalPage = poiResult.getPageCount() / 10 + 1;
            } else {
                totalPage = poiResult.getPageCount();
            }
        } else {
            poiAddressAdapter.addData(poiResult.getPois());
        }
        if (currentPage == totalPage - 1) {
            refresh_address.finishLoadMore(1000, true, true);
        } else {
            currentPage++;
        }
        mPoiItemLists.addAll(poiResult.getPois());

    }

    @Override
    public void onLeftClick(View v) {
        finish();
    }

    @Override
    public void onTitleClick(View v) {

    }

    @Override
    public void onRightClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("selectAddressInfo", selectAddressInfo);
        setResult(RESULT_OK, intent);
        finish();
    }
}
