package com.example.zealience.oneiromancy.mvp.presenter;

import com.example.zealience.oneiromancy.api.ApiRequest;
import com.example.zealience.oneiromancy.entity.HistoryDetailEntity;
import com.example.zealience.oneiromancy.entity.HistoryEntity;
import com.example.zealience.oneiromancy.mvp.contract.HistoryContract;
import com.steven.base.rx.BaseObserver;
import com.steven.base.util.DateTimeHelper;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/3/8 13:19
 * @description 自定义
 */
public class HistoryPresenter extends HistoryContract.Presenter {
    @Override
    public void getHistoryBydate(String date) {
        mRxManager.add(mModel.getHistoryByDate(ApiRequest.getHistoryListBydate(date)).subscribeWith(new BaseObserver<List<HistoryEntity>>(mContext, false) {
            @Override
            protected void onSuccess(List<HistoryEntity> historyEntities) {
                if (historyEntities.size() != 0) {
                    mView.setHistoryEntities(historyEntities);
                } else {
                    mView.historyEmpty();
                }
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
            }
        }));
    }

    @Override
    public String getCurrentDay() {
        String formitDay = DateTimeHelper.getCurrentMonthDay();
        String[] args = formitDay.split("/");
        formitDay = Integer.parseInt(args[0]) + "/" + Integer.parseInt(args[1]);
        return formitDay;
    }
}
