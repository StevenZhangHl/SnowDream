package com.example.zealience.oneiromancy.mvp.presenter;

import com.example.zealience.oneiromancy.api.ApiRequest;
import com.example.zealience.oneiromancy.constant.SharePConstant;
import com.example.zealience.oneiromancy.entity.DreamTypeEntity;
import com.example.zealience.oneiromancy.mvp.contract.HomeContract;
import com.steven.base.rx.BaseObserver;
import com.steven.base.util.GsonUtil;
import com.steven.base.util.SPUtils;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/2/20 11:14
 * @description 自定义
 */
public class HomePresenter extends HomeContract.Presenter {
    @Override
    public void getHomeDreamTypeData() {
        mRxManager.add(mModel.getDreamType(ApiRequest.getHomeDreamType()).subscribeWith(new BaseObserver<List<DreamTypeEntity>>(mContext, false) {
            @Override
            protected void onSuccess(List<DreamTypeEntity> dreamTypeEntities) {
                mView.setDreamTypeData(dreamTypeEntities);
                SPUtils.setSharedStringData(mContext, SharePConstant.KEY_DREAM_DATA, GsonUtil.getGson().toJson(dreamTypeEntities));
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
            }
        }));
    }
}
