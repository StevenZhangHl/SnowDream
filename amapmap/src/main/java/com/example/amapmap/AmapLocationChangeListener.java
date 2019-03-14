package com.example.amapmap;

import com.amap.api.location.AMapLocation;

/**
 * @user steven
 * @createDate 2019/3/13 15:26
 * @description 高德地图定位回调接口
 */
public interface AmapLocationChangeListener {
    void onLocationChanged(AMapLocation location);
}
