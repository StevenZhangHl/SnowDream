package com.example.zealience.oneiromancy.mvp.contract;

import com.example.zealience.oneiromancy.entity.VRPhotoEntity;
import com.steven.base.mvp.BaseModel;
import com.steven.base.mvp.BasePresenter;
import com.steven.base.mvp.BaseView;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/2/27 13:37
 * @description 自定义
 */
public interface VRcontract {
    interface Model extends BaseModel {

    }

    interface View extends BaseView {
        void setVRPhotoData(List<VRPhotoEntity> vrPhotoDatas);
    }

    public abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getVRPhotoData();
    }
}
