package com.steven.base.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @user steven
 * @createDate 2019/3/14 14:33
 * @description 内容提供者工具类
 */
public class ProviderUtil {
    /**
     * 本地分享：分享文本
     * @param context
     * @param content
     */
    public static void startLocalShareText(Context context, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "来自析梦的分享"));
    }
    /**
     * 本地分享：分享图片
     * @param context
     * @param content
     */
    public static void startLocalShareUri(Context context, Uri content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "来自析梦的分享"));
    }
}
