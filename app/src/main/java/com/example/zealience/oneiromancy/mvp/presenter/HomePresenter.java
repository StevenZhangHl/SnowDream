package com.example.zealience.oneiromancy.mvp.presenter;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.api.ApiRequest;
import com.example.zealience.oneiromancy.constant.SharePConstant;
import com.example.zealience.oneiromancy.entity.DreamTypeEntity;
import com.example.zealience.oneiromancy.mvp.contract.HomeContract;
import com.steven.base.rx.BaseObserver;
import com.steven.base.util.GsonUtil;
import com.steven.base.util.SPUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @user steven
 * @createDate 2019/2/20 11:14
 * @description 自定义
 */
public class HomePresenter extends HomeContract.Presenter {
    private Integer[] images = {R.mipmap.bg_banner_one, R.mipmap.bg_banner_two, R.mipmap.bg_banner_three};

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

    @Override
    public void getHomeBannerData() {
        mView.setBannerDdata(Arrays.asList(images));
    }

    @Override
    public void getHotSearchData() {
        String[] hots = {"被人打", "捡到钱", "梦见回到小学", "梦到吃好吃的", "被人追杀"};
        mView.setHotSearchData(hots);
    }
}
