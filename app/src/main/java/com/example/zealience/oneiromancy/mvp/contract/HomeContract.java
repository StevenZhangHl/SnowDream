package com.example.zealience.oneiromancy.mvp.contract;

import com.example.zealience.oneiromancy.entity.DreamTypeEntity;
import com.example.zealience.oneiromancy.entity.HomeRecommendEntity;
import com.steven.base.mvp.BaseModel;
import com.steven.base.mvp.BasePresenter;
import com.steven.base.mvp.BaseView;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @user steven
 * @createDate 2019/2/20 11:15
 * @description 自定义
 */
public interface HomeContract {
    interface View extends BaseView {
        void setDreamTypeData(List<DreamTypeEntity> dreamTypeEntityList);

        void setBannerDdata(List<Integer> images);

        void setHotSearchData(String[] hotSearchData);

        void setRecommendData(List<HomeRecommendEntity> homeRecommendEntities);
    }

    interface Model extends BaseModel {
        Observable<List<DreamTypeEntity>> getDreamType(Map<String, Object> objectMap);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getHomeDreamTypeData();

        public abstract void getHomeBannerData();

        public abstract void getHotSearchData();

        public abstract void getHomeRecommendData();
    }
}
