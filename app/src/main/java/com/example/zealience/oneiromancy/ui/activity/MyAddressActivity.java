package com.example.zealience.oneiromancy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amap.api.services.core.PoiItem;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.constant.SharePConstant;
import com.hjq.bar.OnTitleBarListener;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.GsonUtil;
import com.steven.base.util.SPUtils;
import com.steven.base.widget.CustomLayoutGroup;

public class MyAddressActivity extends BaseActivity implements View.OnClickListener, OnTitleBarListener {

    private CustomLayoutGroup mViewProvince;
    private CustomLayoutGroup mViewCity;
    private CustomLayoutGroup mViewDistrict;
    private CustomLayoutGroup mViewStreet;
    private ImageView iv_add_address;
    private LinearLayout ll_address_container;
    private LinearLayout ll_empty_view;
    private PoiItem mPoiItem;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_address;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showTitle("我的地址");
        mViewProvince = (CustomLayoutGroup) findViewById(R.id.view_province);
        mViewCity = (CustomLayoutGroup) findViewById(R.id.view_city);
        mViewDistrict = (CustomLayoutGroup) findViewById(R.id.view_district);
        mViewStreet = (CustomLayoutGroup) findViewById(R.id.view_street);
        iv_add_address = (ImageView) findViewById(R.id.iv_add_address);
        ll_address_container = (LinearLayout) findViewById(R.id.ll_address_container);
        ll_empty_view = (LinearLayout) findViewById(R.id.ll_empty_view);
        iv_add_address.setOnClickListener(this);
        mViewProvince.setTv_left("省");
        mViewCity.setTv_left("市");
        mViewDistrict.setTv_left("区");
        mViewStreet.setTv_left("街道");
        mViewProvince.isShowRightDrawable(false);
        mViewCity.isShowRightDrawable(false);
        mViewDistrict.isShowRightDrawable(false);
        mViewStreet.isShowRightDrawable(false);
        getTitlebar().setOnTitleBarListener(this);
        fillData();
    }

    /**
     * 填充数据
     */
    private void fillData() {
        mPoiItem = GsonUtil.GsonToBean(SPUtils.getSharedStringData(this, SharePConstant.KEY_USER_ADDRESS_DATA), PoiItem.class);
        if (mPoiItem != null) {
            getTitlebar().setRightTitle("修改");
            ll_empty_view.setVisibility(View.GONE);
            ll_address_container.setVisibility(View.VISIBLE);
            mViewProvince.setTv_right(mPoiItem.getProvinceName());
            mViewCity.setTv_right(mPoiItem.getCityName());
            mViewDistrict.setTv_right(mPoiItem.getAdName());
            mViewStreet.setTv_right(mPoiItem.getSnippet());

        } else {
            getTitlebar().setRightTitle("");
            ll_empty_view.setVisibility(View.VISIBLE);
            ll_address_container.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        startActivityForResult(ShowAmapActivity.class, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (data != null) {
                    mPoiItem = data.getParcelableExtra("selectAddressInfo");
                    SPUtils.setSharedStringData(MyAddressActivity.this, SharePConstant.KEY_USER_ADDRESS_DATA, GsonUtil.GsonString(mPoiItem));
                    fillData();
                }
            }
        }
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
        startActivityForResult(ShowAmapActivity.class, 1);
    }
}
