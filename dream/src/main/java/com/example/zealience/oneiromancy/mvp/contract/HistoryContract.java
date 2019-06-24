package com.example.zealience.oneiromancy.mvp.contract;

import com.example.zealience.oneiromancy.entity.DreamTypeEntity;
import com.example.zealience.oneiromancy.entity.HistoryDetailEntity;
import com.example.zealience.oneiromancy.entity.HistoryEntity;
import com.steven.base.mvp.BaseModel;
import com.steven.base.mvp.BasePresenter;
import com.steven.base.mvp.BaseView;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @user steven
 * @createDate 2019/3/8 13:12
 * @description 自定义
 */
public interface HistoryContract {
    interface View extends BaseView {
        void setHistoryEntities(List<HistoryEntity> historyEntities);
        void historyEmpty();
    }

    interface Model extends BaseModel {
        Observable<List<HistoryEntity>> getHistoryByDate(Map<String, Object> objectMap);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getHistoryBydate(String date);
        public abstract String getCurrentDay();
    }
}
