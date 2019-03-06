package com.steven.base.app;

import android.app.Activity;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;

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
