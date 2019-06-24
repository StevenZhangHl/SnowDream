package com.example.zealience.oneiromancy.api;

import com.example.zealience.oneiromancy.entity.BaseNewsEntity;
import com.example.zealience.oneiromancy.entity.DreamEntity;
import com.example.zealience.oneiromancy.entity.DreamTypeEntity;
import com.example.zealience.oneiromancy.entity.HistoryDetailEntity;
import com.example.zealience.oneiromancy.entity.HistoryEntity;
import com.example.zealience.oneiromancy.entity.LuckEntity;
import com.steven.base.bean.BaseEntity;
import com.steven.base.net.Url;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * @user steven
 * @createDate 2019/2/20 13:37
 * @description 自定义
 */
public interface Api {
    /**
     * 得到解梦类型列表
     *
     * @param map
     * @return
     */
    @GET("dream/category")
    Observable<BaseEntity<List<DreamTypeEntity>>> getDreamType(@QueryMap Map<String, Object> map);

    /**
     * 搜索解梦详情
     *
     * @param map
     * @return
     */
    @POST("dream/query")
    Observable<BaseEntity<List<DreamEntity>>> getSearchDream(@QueryMap Map<String, Object> map);

    /**
     * 得到新闻列表
     *
     * @param map
     * @return
     */
    @POST("toutiao/index")
    Observable<BaseEntity<BaseNewsEntity>> getNewsList(@QueryMap Map<String, Object> map);

    /**
     * 根据日期得到历史上的今天数据列表
     *
     * @param map
     * @return
     */
    @POST("todayOnhistory/queryEvent.php")
    Observable<BaseEntity<List<HistoryEntity>>> getHistoryListBydate(@QueryMap Map<String, Object> map);

    /**
     * 根据ID得到历史上的今天数据详情
     *
     * @param map
     * @return
     */
    @POST("todayOnhistory/queryDetail.php")
    Observable<BaseEntity<List<HistoryDetailEntity>>> getHistoryById(@QueryMap Map<String, Object> map);

    /**
     * 查询运势
     *
     * @param map
     * @return
     */
    @Headers({DOMAIN_NAME_HEADER + Url.BASE_JUHE_DEV_NAME})
    @POST("constellation/getAll")
    Observable<LuckEntity> getUserLuckData(@QueryMap Map<String, Object> map);
}
