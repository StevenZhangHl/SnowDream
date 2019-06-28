package com.horen.maplib;

import com.baidu.mapapi.model.LatLng;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/01/12:48
 * @description :地图契约接口类，扩展
 * @github :https://github.com/chenyy0708
 */
public interface IMapView {

    /**
     * 根据点集合，显示缩放比例
     *
     * @param list 点集合
     */
    void setZoomWithLatLngs(List<LatLng> list);

    /**
     * 移动地图到指定经纬度
     *
     * @param lat        经度
     * @param lng        纬度
     * @param latLngZoom 缩放比率
     */
    void moveTo(double lat, double lng, float latLngZoom);

    /**
     * 移动中心点到Marker位置
     *
     * @param lat 经度
     * @param lng 纬度
     */
    void moveTo(double lat, double lng);

    /**
     * 清除MapView，Marker点和Listener
     */
    void clearMap();

    /**
     * 添加Marker标记
     *
     * @param latitude   经度
     * @param longitude  纬度
     * @param drawableId 资源id
     * @param index      marker在集合中位置
     * @param zIndex     层级
     */
    void addMarker(double latitude, double longitude, int drawableId, int index, int zIndex);

    /**
     * 修改Marker标记Icon
     *
     * @param position   Marker位置
     * @param drawableId 资源id
     * @param zIndex     层级
     */
    void changeMarkerIcon(int position, int drawableId, int zIndex);

    /**
     * 地图点击事件
     *
     * @param onMapClickListener 点击监听
     */
    void setOnMapClickListener(final MapListener.MapClickListener onMapClickListener);

    /**
     * Maker点击事件
     *
     * @param onMarkerClickListenertener 点击监听
     */
    void setOnMarkerClickListener(final MapListener.MarkerClickListener onMarkerClickListenertener);

    /**
     * 高亮某一城市区域
     *
     * @param cityName    城市名
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     * @param fillColor   填充颜色
     */
    void setHighlightCity(String cityName, int strokeWidth, int strokeColor, int fillColor);
}