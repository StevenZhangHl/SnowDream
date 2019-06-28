package com.horen.maplib;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * @author :ChenYangYi
 * @date :2018/08/01/12:48
 * @description :定位帮助类
 * @github :https://github.com/chenyy0708
 */
public class LocationHelper {
    private static LocationClient mLocationClient;

    private LocationHelper() {
    }

    public static void getLocation(final Context context, final MapListener.OnLocationListener listener) {

        /**
         * LocationHelper.getSingleLocation
         *
         * 只定位一次，判断国内国外，回调当前经纬度给调用者
         *
         * */

        if (mLocationClient == null) {
            mLocationClient = new LocationClient(context);
        }
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setScanSpan(0);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setOpenGps(false);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setLocationNotify(false);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setIgnoreKillProcess(true);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation != null) {
                    //定位成功回调信息，设置相关消息
                    //判断国内还是国外
                    //bdLocation.getLocationWhere() == BDLocation.LOCATION_WHERE_IN_CN
                    //当获取到了经纬度之后，关闭定位
                    if (listener != null) {
                        listener.location(bdLocation.getLatitude(), bdLocation.getLongitude());
                    }
                    mLocationClient.stop();
                    mLocationClient = null;
                }
            }
        });
        mLocationClient.start();
    }
}