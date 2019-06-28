package com.horen.maplib;

/**
 * @author :ChenYangYi
 * @date :2018/08/01/12:48
 * @description :地图的各种监听
 * @github :https://github.com/chenyy0708
 */
public interface MapListener {
    interface MapClickListener {
        /**
         * 地图点击监听
         *
         * @param lat 经度
         * @param lng 纬度
         */
        void onMapClickListener(double lat, double lng);
    }

    interface MarkerClickListener {
        /**
         * Marker点击监听
         *
         * @param currentPosition  当前点击Marker的索引位置
         * @param previousPosition 上一个点击Marker的索引位置
         */
        void onMarkerClickListener(int currentPosition, int previousPosition);
    }

    interface OnLocationListener {
        /**
         * 定位位置
         *
         * @param lat 经度
         * @param lng 纬度
         */
        void location(double lat, double lng);
    }
}
