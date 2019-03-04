package com.example.zealience.oneiromancy.mvp.model;

import com.example.zealience.oneiromancy.api.ApiManager;
import com.example.zealience.oneiromancy.entity.DreamEntity;
import com.example.zealience.oneiromancy.mvp.contract.SearchContract;
import com.steven.base.rx.RxHelper;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @user steven
 * @createDate 2019/2/21 10:27
 * @description 自定义
 */
public class SearchModel implements SearchContract.Model {
    @Override
    public Observable<List<DreamEntity>> searchDreamData(Map<String, Object> map) {
        return ApiManager.getInstance().getSearchDream(map).compose(RxHelper.getResult());
    }
}
