package com.steven.base.mvp;

import android.content.Context;

import com.steven.base.rx.RxManager;


/**
 * @author ChenYangYi
 * @date 2018/7/31
 * Presenter基类
 */
public abstract class BasePresenter<T extends BaseView,E extends BaseModel>{
    public Context mContext;
    public E mModel;
    public T mView;
    public RxManager mRxManager = new RxManager();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }
    public void onStart(){
    }
    public void onDestroy() {
        mRxManager.clear();
    }
}
