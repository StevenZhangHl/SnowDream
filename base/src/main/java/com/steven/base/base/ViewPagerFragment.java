package com.steven.base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

/**
 * @user steven
 * @createDate 2019/1/24 13:30
 * @description 自定义
 */
public abstract class ViewPagerFragment<T extends BasePresenter, E extends BaseModel> extends Fragment implements BaseView, Callback.OnReloadListener {
    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;

    /**
     * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
     */
    protected View rootView;
    public T mPresenter;
    public E mModel;
    public RxManager mRxManager;
    protected LoadService mBaseLoadService;
    protected FragmentActivity _mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _mActivity = getActivity();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }

    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {

        }
    }

    //新建类ViewPagerFragment，将上面代码复制粘贴进去，添加需要的import语句 -> 新建你需要的Fragment类，继承ViewPagerFragment，在onCreateView()里对rootView进行初始化 -> 重写onFragmentVisibleChange()，在这里进行你需要的操作，比如数据加载，控制显示等。

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
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
        initPresenter();
        initView(savedInstanceState);
        return mBaseLoadService != null ? mBaseLoadService.getLoadLayout() : rootView;
    }

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

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public void onReload(View view) {

    }

    @Override
    public void onError(String msg) {

    }

    protected boolean isCustomLoadingLayout() {
        return false;
    }
}
