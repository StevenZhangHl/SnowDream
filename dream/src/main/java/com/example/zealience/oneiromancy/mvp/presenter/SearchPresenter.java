package com.example.zealience.oneiromancy.mvp.presenter;

import com.example.zealience.oneiromancy.api.ApiRequest;
import com.example.zealience.oneiromancy.entity.DreamEntity;
import com.example.zealience.oneiromancy.mvp.contract.SearchContract;
import com.steven.base.rx.BaseObserver;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/2/21 10:28
 * @description 自定义
 */
public class SearchPresenter extends SearchContract.Presenter {

    @Override
    public void searchDreamData(String content, int typeId) {
        mRxManager.add(mModel.searchDreamData(ApiRequest.searchDreamData(content, typeId)).subscribeWith(new BaseObserver<List<DreamEntity>>(mContext, false) {
            @Override
            protected void onSuccess(List<DreamEntity> dreamEntities) {
                if (dreamEntities != null && dreamEntities.size() != 0) {
                    mView.setDreamData(dreamEntities);
                }
            }

            @Override
            protected void onError(String message) {

            }
        }));
    }
}
