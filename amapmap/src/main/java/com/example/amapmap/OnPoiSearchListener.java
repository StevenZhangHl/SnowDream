package com.example.amapmap;

import com.amap.api.services.poisearch.PoiResult;

/**
 * @user steven
 * @createDate 2019/3/13 16:49
 * @description 周边搜索的回调接口
 */
public interface OnPoiSearchListener {
    void onPoiSearched(PoiResult poiResult);
}
