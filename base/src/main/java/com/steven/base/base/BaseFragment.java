package com.steven.base.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.steven.base.mvp.BaseModel;
import com.steven.base.mvp.BasePresenter;
import com.steven.base.mvp.BaseView;
import com.steven.base.rx.RxManager;
import com.steven.base.util.TUtil;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @user steven
 * @createDate 2019/1/24 10:04
 * @description 自定义
 */
public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends SupportFragment implements BaseView, Callback.OnReloadListener {
    protected View rootView;
    public T mPresenter;
    public E mModel;
    public RxManager mRxManager;
    protected LoadService mBaseLoadService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        // 注册对象
//        EventBus.getDefault().register(this);
        mRxManager = new RxManager();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        // 多状态管理类
        if (!isCustomLoadingLayout()) {
            mBaseLoadService = LoadSir.getDefault().register(rootView, this);
        }
        return mBaseLoadService != null ? mBaseLoadService.getLoadLayout() : rootView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initPresenter();
        initView(savedInstanceState);
    }

    /**
     * 获取布局文件
     *
     * @return 布局Id
     */
    @LayoutRes
    public abstract int getLayoutId();

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public abstract void initPresenter();

    /**
     * 初始化view
     *
     * @param savedInstanceState 保存数据
     */
    public abstract void initView(Bundle savedInstanceState);

    @Override
    public void onReload(View view) {

    }

    /**
     * 适配4.4系统toolbar显示不全
     */
    protected void setFitsSystemWindows() {
        rootView.setFitsSystemWindows(true);
    }

    protected boolean isCustomLoadingLayout() {
        return false;
    }
}
