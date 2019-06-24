package com.example.zealience.oneiromancy.mvp.model;

import com.example.zealience.oneiromancy.api.ApiManager;
import com.example.zealience.oneiromancy.entity.DreamTypeEntity;
import com.example.zealience.oneiromancy.mvp.contract.HomeContract;
import com.steven.base.rx.RxHelper;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @user steven
 * @createDate 2019/2/20 11:14
 * @description 自定义
 */
public class HomeModel implements HomeContract.Model {
    @Override
    public Observable<List<DreamTypeEntity>> getDreamType(Map<String,Object> map) {
        return ApiManager.getInstance().getDreamType(map).compose(RxHelper.getResult());
    }
}
