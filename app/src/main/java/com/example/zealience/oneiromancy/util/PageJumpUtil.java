package com.example.zealience.oneiromancy.util;

import android.content.Context;
import android.os.Bundle;

import com.example.zealience.oneiromancy.ui.activity.WebViewActivity;

/**
 * @user steven
 * @createDate 2019/3/6 18:06
 * @description 页面调整配置文件
 */
public class PageJumpUtil {
    public static void jumpPage(Context context, String fuctionUrl, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (fuctionUrl.contains("http:")) {
            WebViewActivity.startActivity(context, bundle);
        }
        if (fuctionUrl.contains("app:")) {
            int pageCode = Integer.parseInt(fuctionUrl.split(":")[1]);
            switch (pageCode) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }
    }
}
