package com.example.zealience.oneiromancy.mvp.presenter;

import com.example.zealience.oneiromancy.api.ApiRequest;
import com.example.zealience.oneiromancy.entity.BaseNewsEntity;
import com.example.zealience.oneiromancy.entity.NewsEntity;
import com.example.zealience.oneiromancy.mvp.contract.NewsContract;
import com.steven.base.rx.BaseObserver;

/**
 * @user steven
 * @createDate 2019/3/7 14:04
 * @description 自定义
 */
public class NewsPresenter extends NewsContract.Presenter {
    @Override
    public void getNewsList(String type) {
        mRxManager.add(mModel.getNewsList(ApiRequest.getNewsList(type)).subscribeWith(new BaseObserver<BaseNewsEntity>(mContext, false) {
            @Override
            protected void onSuccess(BaseNewsEntity baseNewsEntity) {
                if (baseNewsEntity.getData().size() != 0) {
                    mView.setNewsList(baseNewsEntity.getData());
                }
            }

            @Override
            protected void onError(String message) {
                mView.onError(message);
            }
        }));
    }

    @Override
    public String getNewsTypeByName(String name) {
        if ("推荐".equals(name)) {
            return "top";
        }
        if ("社会".equals(name)) {
            return "shehui";
        }
        if ("国内".equals(name)) {
            return "guonei";
        }
        if ("国际".equals(name)) {
            return "guoji";
        }
        if ("娱乐".equals(name)) {
            return "yule";
        }
        if ("体育".equals(name)) {
            return "tiyu";
        }
        if ("军事".equals(name)) {
            return "junshi";
        }
        if ("科技".equals(name)) {
            return "keji";
        }
        if ("财经".equals(name)) {
            return "caijing";
        }
        if ("时尚".equals(name)) {
            return "shishang";
        }
        return "top";
    }
}
