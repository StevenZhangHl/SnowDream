package com.example.amapmap;

import com.amap.api.maps.model.CameraPosition;

/**
 * @user steven
 * @createDate 2019/3/13 16:09
 * @description 高德地图镜头移动后的回调接口
 */
public interface OnCameraChangeListener {
    void onCameraChangeFinish(CameraPosition cameraPosition);
}
