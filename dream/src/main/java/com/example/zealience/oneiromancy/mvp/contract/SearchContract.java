package com.example.zealience.oneiromancy.mvp.contract;

import com.example.zealience.oneiromancy.entity.DreamEntity;
import com.steven.base.mvp.BaseModel;
import com.steven.base.mvp.BasePresenter;
import com.steven.base.mvp.BaseView;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @user steven
 * @createDate 2019/2/21 10:27
 * @description 自定义
 */
public interface SearchContract {
    interface Model extends BaseModel {
        Observable<List<DreamEntity>> searchDreamData(Map<String, Object> map);
    }

    interface View extends BaseView {
        void setDreamData(List<DreamEntity> dreamEntities);
    }

    public abstract class Presenter extends BasePresenter<View, Model> {
       public abstract void searchDreamData(String content,int typeId);
    }
}
