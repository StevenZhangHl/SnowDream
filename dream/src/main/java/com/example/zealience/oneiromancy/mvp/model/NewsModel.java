package com.example.zealience.oneiromancy.mvp.model;

import com.example.zealience.oneiromancy.api.ApiManager;
import com.example.zealience.oneiromancy.entity.BaseNewsEntity;
import com.example.zealience.oneiromancy.mvp.contract.NewsContract;
import com.steven.base.rx.RxHelper;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @user steven
 * @createDate 2019/3/7 14:04
 * @description 自定义
 */
public class NewsModel implements NewsContract.Model {
    @Override
    public Observable<BaseNewsEntity> getNewsList(Map<String, Object> map) {
        return ApiManager.getInstance().getNewsList(map).compose(RxHelper.getResult());
    }
}
