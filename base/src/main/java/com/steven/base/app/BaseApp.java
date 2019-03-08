package com.steven.base.app;

import android.app.Activity;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLogCommon;
import com.umeng.commonsdk.debug.UMLogUtils;
import com.umeng.socialize.PlatformConfig;

import java.util.LinkedList;
import java.util.List;

/**
 * @user steven
 * @createDate 2019/1/23 10:39
 * @description 自定义
 */
public class BaseApp extends MultiDexApplication {
    public static BaseApp instance;
    private static List<Activity> activities = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openDebug();
        ARouter.openLog();
        ARouter.init(this);
        instance = this;
        initLoadingLayout();
        UMConfigure.setLogEnabled(true);//友盟日志调试开关
        UMConfigure.init(this,"5c81d3f23fc1955bd7000998"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
//        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
//        //豆瓣RENREN平台目前只能在服务器端配置
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
//        PlatformConfig.setAlipay("2015111700822536");
//        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
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
     * 将一个activity加入到栈中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }

    /**
     * 关闭栈中所有activity
     */
    public void finishAllActivity() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

}
