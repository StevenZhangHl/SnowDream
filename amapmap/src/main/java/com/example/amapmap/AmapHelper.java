package com.example.amapmap;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.steven.base.app.BaseApp;

/**
 * @user steven
 * @createDate 2019/3/13 13:54
 * @description 高德地图工具类
 */
public class AmapHelper {
    private Context mContext;
    private AMap mAmap;
    private MapView mMapView;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private AmapLocationChangeListener locationChangeListener;
    private OnCameraChangeListener mOnCameraChangeListener;
    private OnPoiSearchListener mOnPoiSearchListerer;
    private UiSettings mUiSettings;
    private PoiSearch mPoiSearch;
    /**
     * 地图默认的比例尺是否显示
     */
    private boolean isScaleControlsEnabled = false;
    /**
     * 地图默认的缩放按钮是否显示
     */
    private boolean isZoomControlsEnabled = false;
    /**
     * 地图默认的指南针是否显示
     */
    private boolean isCompassEnabled = false;
    /**
     * 地图默认的定位按钮是否显示
     */
    private boolean isMyLocationButtonEnabled = false;
    /**
     * 地图的缩放级别，范围3-19，数字越大缩放级别越高
     */
    private int defaultZoomLevel = 16;

    public AmapHelper(Context context, MapView mMapView) {
        this.mContext = context;
        this.mMapView = mMapView;
        initMap();
    }

    public AMap getmAmap() {
        return mAmap;
    }

    private void initMap() {
        mAmap = mMapView.getMap();
        mUiSettings = mAmap.getUiSettings();
        mAmap.setMyLocationEnabled(true);
        changeMapZoomLevel(defaultZoomLevel);
        mAmap.setOnCameraChangeListener(onCameraChangeListener);
        initStyle();
        initUiSetting();
        //初始化定位
        initLocation();
    }

    private void initStyle() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(100000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAmap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
    }

    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(mContext);
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    private void initUiSetting() {
        mUiSettings.setScaleControlsEnabled(isScaleControlsEnabled);
        mUiSettings.setCompassEnabled(isCompassEnabled);
        mUiSettings.setZoomControlsEnabled(isZoomControlsEnabled);
        mUiSettings.setMyLocationButtonEnabled(isMyLocationButtonEnabled);
        mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);
    }

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                locationChangeListener.onLocationChanged(location);
                if (location.getErrorCode() == 0) {
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");

                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                sb.append("***定位质量报告***").append("\n");
                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
                sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
                sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
                sb.append("****************").append("\n");
                //解析定位结果，
                String result = sb.toString();
            } else {
                Log.e("定位失败", "location is null");
            }
        }
    };

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }

    /**
     * 镜头移动监听
     */
    AMap.OnCameraChangeListener onCameraChangeListener = new AMap.OnCameraChangeListener() {
        @Override
        public void onCameraChange(CameraPosition cameraPosition) {

        }

        @Override
        public void onCameraChangeFinish(CameraPosition cameraPosition) {
            mOnCameraChangeListener.onCameraChangeFinish(cameraPosition);
        }
    };

    PoiSearch.OnPoiSearchListener poiSearchListener = new PoiSearch.OnPoiSearchListener() {
        @Override
        public void onPoiSearched(PoiResult poiResult, int i) {
            mOnPoiSearchListerer.onPoiSearched(poiResult);
        }

        @Override
        public void onPoiItemSearched(PoiItem poiItem, int i) {

        }
    };

    /**
     * 设置周边搜索的中心点以及半径
     *
     * @param latLng
     */
    public void setPoiSearchCenter(LatLng latLng, int currentPage) {
        PoiSearch.Query query = new PoiSearch.Query("", "");
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);//设置查询页码
        mPoiSearch = new PoiSearch(mContext, query);
        mPoiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latLng.latitude,
                latLng.longitude), 2000));
        mPoiSearch.setOnPoiSearchListener(poiSearchListener);
        mPoiSearch.searchPOIAsyn();
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void destroyLocation() {
        if (null != locationClient) {
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    /**
     * 设置地图默认的比例尺是否显示
     */
    public void setScaleControlsEnabled(boolean scaleControlsEnabled) {
        this.isScaleControlsEnabled = scaleControlsEnabled;
        mUiSettings.setScaleControlsEnabled(isScaleControlsEnabled);
    }

    /**
     * 设置地图默认的缩放按钮是否显示
     */
    public void setZoomControlsEnabled(boolean zoomControlsEnabled) {
        this.isZoomControlsEnabled = zoomControlsEnabled;
    }

    /**
     * 设置地图默认的指南针是否显示
     *
     * @param compassEnabled
     */
    public void setCompassEnabled(boolean compassEnabled) {
        this.isCompassEnabled = compassEnabled;
    }

    /**
     * 设置地图默认的定位按钮是否显示
     *
     * @param myLocationButtonEnabled
     */

    public void setMyLocationButtonEnabled(boolean myLocationButtonEnabled) {
        isMyLocationButtonEnabled = myLocationButtonEnabled;
    }

    public void setLocationChangeListener(AmapLocationChangeListener locationChangeListener) {
        this.locationChangeListener = locationChangeListener;
    }

    public void setOnCameraChangeListener(OnCameraChangeListener onCameraChangeListener) {
        this.mOnCameraChangeListener = onCameraChangeListener;
    }

    public void setPoiSearchListener(OnPoiSearchListener poiSearchListener) {
        this.mOnPoiSearchListerer = poiSearchListener;
    }

    /**
     * 设置logo位置
     *
     * @param position
     */
    public void setLogoPosition(int position) {
        switch (position) {
            case 0:
                mUiSettings.setZoomPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
                break;
            case 1:
                mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
                break;
            case 2:
                mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);
                break;
            default:
                break;
        }
    }

    /**
     * 改变地图的默认位置
     *
     * @param latLng
     */
    public void changeMapDefaltPosition(LatLng latLng) {
        LatLng centerBJPoint = new LatLng(latLng.latitude, latLng.longitude);
        AMapOptions mapOptions = new AMapOptions();
        // 设置了一个可视范围的初始化位置
        // CameraPosition 第一个参数： 目标位置的屏幕中心点经纬度坐标。
        // CameraPosition 第二个参数： 目标可视区域的缩放级别
        // CameraPosition 第三个参数： 目标可视区域的倾斜度，以角度为单位。
        // CameraPosition 第四个参数： 可视区域指向的方向，以角度为单位，从正北向顺时针方向计算，从0度到360度
        mapOptions.camera(new CameraPosition(centerBJPoint, 10f, 0, 0));
        // 定义一个 MapView 对象，构造方法中传入 mapOptions 参数类
        mMapView = new MapView(mContext, mapOptions);
    }

    /**
     * 设置地图的缩放级别
     *
     * @param level
     */
    public void changeMapZoomLevel(int level) {
        //设置希望展示的地图缩放级别
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(level);
        mAmap.animateCamera(mCameraUpdate);
    }

    /**
     * 调起高德地图导航功能，如果没安装高德地图，会进入异常，可以在异常中处理，调起高德地图app的下载页面
     */
    public void startAMapNavi(Marker marker) {
        // 构造导航参数
        NaviPara naviPara = new NaviPara();
        // 设置终点位置
        naviPara.setTargetPoint(marker.getPosition());
        // 设置导航策略，这里是避免拥堵
        naviPara.setNaviStyle(NaviPara.DRIVING_AVOID_CONGESTION);

        // 调起高德地图导航
        try {
            AMapUtils.openAMapNavi(naviPara, BaseApp.getInstance());
        } catch (com.amap.api.maps.AMapException e) {

            // 如果没安装会进入异常，调起下载页面
            AMapUtils.getLatestAMapApp(BaseApp.getInstance());

        }

    }
}
