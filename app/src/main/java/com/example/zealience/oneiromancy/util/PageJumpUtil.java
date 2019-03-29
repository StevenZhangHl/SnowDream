package com.example.zealience.oneiromancy.util;

import android.content.Context;
import android.os.Bundle;

import com.example.zealience.oneiromancy.constant.KeyConstant;
import com.example.zealience.oneiromancy.constant.UrlConstant;
import com.example.zealience.oneiromancy.ui.activity.WebViewActivity;

/**
 * @user steven
 * @createDate 2019/3/6 18:06
 * @description 页面跳转配置文件
 */
public class PageJumpUtil {
    public static void jumpToAppointpage(Context context, String functionUrl,
                                         String pageTitle) {
        jumpToAppointpage(context, functionUrl, pageTitle, null);
    }

    /**
     * @param context
     * @param functionUrl
     * @param pageTitle
     */
    public static void jumpToAppointpage(final Context context, String functionUrl,
                                         String pageTitle, Bundle bundle) {
        if (functionUrl == null || functionUrl.length() <= 0) {
            return;
        }
        if (functionUrl.startsWith("http")) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(KeyConstant.URL_KEY, functionUrl);
            WebViewActivity.startActivity(context, bundle);
        } else if (functionUrl.startsWith("app")) {

        }
    }
}
