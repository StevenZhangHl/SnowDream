package com.steven.base.rx;

import android.app.Activity;
import android.content.Context;

import com.steven.base.R;
import com.steven.base.app.BaseApp;
import com.steven.base.util.NetWorkUtils;
import com.steven.base.widget.LoadingDialog;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @author :ChenYangYi
 * @time :2018/4/17
 * @desc :订阅封装
 */
public abstract class BaseObserver<T> extends DisposableObserver<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog = true;
    private LoadingDialog loadingDialog;

    public BaseObserver(Context context, String msg, boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
    }

    public BaseObserver() {
        this(null, false);
    }

    public BaseObserver(Context context) {
        this(context, BaseApp.getInstance().getString(R.string.loading), true);
    }

    public BaseObserver(Context context, boolean showDialog) {
        this(context, BaseApp.getInstance().getString(R.string.loading), showDialog);
    }

    public BaseObserver(Context context, String msg) {
        this(context, msg, true);
    }

    @Override
    public void onComplete() {
        if (showDialog) {
            loadingDialog.cancelDialogForLoading();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                loadingDialog = new LoadingDialog();
                loadingDialog.showDialogForLoading((Activity) mContext, msg, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (showDialog) {
            loadingDialog.cancelDialogForLoading();
        }
        e.printStackTrace();
        //网络
        if (!NetWorkUtils.isNetConnected(BaseApp.getInstance())) {
            onError(BaseApp.getInstance().getString(R.string.error_please_check_network));
        }
        //服务器
        else if (e instanceof HRException) {
            HRException err = (HRException) e;
            onError(err.getMessage());
        } else if (e instanceof HttpException) {
//            if (((HttpException) e).code() == 404) {
//                onError("404");
//            } else {
            onError(BaseApp.getAppResources().getString(R.string.connection_network_error));
//            }
        }
        //其它(请求超时)
        else {
            onError(BaseApp.getAppResources().getString(R.string.connection_network_error));
        }
    }

    /**
     * 加载成功
     *
     * @param t 实体类
     */
    protected abstract void onSuccess(T t);

    /**
     * 加载失败
     *
     * @param message 失败错误message
     */
    protected abstract void onError(String message);
}