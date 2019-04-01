package com.example.zealience.oneiromancy.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.steven.base.app.BaseApp;

/**
 * @user steven
 * @createDate 2019/4/1 17:51
 * @description 自定义
 */
public class AppUtil {
    /**
     * AndroidId
     */
    public static String getDeviceId() {
        String id = Settings.Secure.getString(BaseApp.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(id)) {
            id = Build.SERIAL + "35" + "/" +
                    Build.BOARD.length() % 10 + "/" +
                    Build.BRAND.length() % 10 + "/" +
                    Build.CPU_ABI.length() % 10 + "/" +
                    Build.DEVICE.length() % 10 + "/" +
                    Build.DISPLAY.length() % 10 + "/" +
                    Build.HOST.length() % 10 + "/" +
                    Build.ID.length() % 10 + "/" +
                    Build.MANUFACTURER.length() % 10 + "/" +
                    Build.MODEL.length() % 10 + "/" +
                    Build.PRODUCT.length() % 10 + "/" +
                    Build.TAGS.length() % 10 + "/" +
                    Build.TYPE.length() % 10 + "/" +
                    Build.USER.length() % 10 + "/";
        }
        return id;
    }

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }
}
