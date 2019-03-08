package com.example.zealience.oneiromancy.mvp.contract;

import com.example.zealience.oneiromancy.entity.BaseNewsEntity;
import com.example.zealience.oneiromancy.entity.NewsEntity;
import com.steven.base.mvp.BaseModel;
import com.steven.base.mvp.BasePresenter;
import com.steven.base.mvp.BaseView;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @user steven
 * @createDate 2019/3/7 14:03
 * @description 自定义
 */
public interface NewsContract {
    public interface View extends BaseView {
        void setNewsList(List<NewsEntity> newsEntities);
    }

    interface Model extends BaseModel {
        Observable<BaseNewsEntity> getNewsList(Map<String, Object> map);
    }

    public abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getNewsList(String type);
        public abstract String getNewsTypeByName(String name);
    }
}
