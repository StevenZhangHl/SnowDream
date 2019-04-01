package com.steven.base.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.steven.base.net.Url;
import com.steven.base.util.DisplayUtil;
import com.steven.base.widget.CustomFooter;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLogCommon;
import com.umeng.commonsdk.debug.UMLogUtils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageService;
import com.umeng.message.inapp.InAppMessageManager;
import com.umeng.socialize.PlatformConfig;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.LinkedList;
import java.util.List;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * @user steven
 * @createDate 2019/1/23 10:39
 * @description 自定义
 */
public class BaseApp extends MultiDexApplication {
    public static BaseApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openDebug();
        ARouter.openLog();
        ARouter.init(this);
        ZXingLibrary.initDisplayOpinion(this);
        instance = this;
        initLoadingLayout();
        initRefreshLayout();
        RetrofitUrlManager.getInstance().putDomain(Url.BASE_JUHE_DEV_NAME,Url.BASE_JUHE_DEV);
        UMConfigure.setLogEnabled(false);//友盟日志调试开关
        UMConfigure.init(this, "5c81d3f23fc1955bd7000998"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "1717496e0ac0e6b0c67c1f69cc0c6286");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.i("register_deviceToken", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i("deviceToken", s + s1);
            }
        });
    }

    public static BaseApp getInstance() {
        return instance;
    }

    public static Resources getAppResources() {
        return instance.getResources();
    }

    /**
     * 多状态布局初始化
     */
    private void initLoadingLayout() {
        LoadSir.beginBuilder()
                .setDefaultCallback(SuccessCallback.class)//设置默认状态页
                .commit();
    }

    /**
     * 设置默认的刷新头部和脚部
     */
    private void initRefreshLayout() {
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
//            @NonNull
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                // 自动上啦加载
//                layout.setEnableAutoLoadMore(false);
//                // 上啦加载完成滚动到内容
//                layout.setEnableScrollContentWhenLoaded(false);
//                CortpHeader cortpHeader = new CortpHeader(context);
//                return cortpHeader;
//            }
//        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                // 自动上啦加载
                layout.setEnableAutoLoadMore(true);
                // 上啦加载完成滚动到内容
                layout.setEnableScrollContentWhenLoaded(true);
                layout.setFooterHeight(50);
                return new CustomFooter(context);
            }
        });
    }
}
