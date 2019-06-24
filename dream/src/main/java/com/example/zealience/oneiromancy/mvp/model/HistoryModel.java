package com.example.zealience.oneiromancy.mvp.model;

import com.example.zealience.oneiromancy.api.Api;
import com.example.zealience.oneiromancy.api.ApiManager;
import com.example.zealience.oneiromancy.entity.HistoryDetailEntity;
import com.example.zealience.oneiromancy.entity.HistoryEntity;
import com.example.zealience.oneiromancy.mvp.contract.HistoryContract;
import com.steven.base.rx.RxHelper;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @user steven
 * @createDate 2019/3/8 13:20
 * @description 自定义
 */
public class HistoryModel implements HistoryContract.Model {
    @Override
    public Observable<List<HistoryEntity>> getHistoryByDate(Map<String, Object> objectMap) {
        return ApiManager.getInstance().getHistoryListBydate(objectMap).compose(RxHelper.getResult());
    }

}
