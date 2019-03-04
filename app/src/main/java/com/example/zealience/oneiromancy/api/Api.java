package com.example.zealience.oneiromancy.api;

import com.example.zealience.oneiromancy.entity.DreamEntity;
import com.example.zealience.oneiromancy.entity.DreamTypeEntity;
import com.steven.base.bean.BaseEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @user steven
 * @createDate 2019/2/20 13:37
 * @description 自定义
 */
public interface Api {
    @GET("dream/category")
    Observable<BaseEntity<List<DreamTypeEntity>>> getDreamType(@QueryMap Map<String, Object> map);

    @POST("dream/query")
    Observable<BaseEntity<List<DreamEntity>>> getSearchDream(@QueryMap Map<String, Object> map);
}
