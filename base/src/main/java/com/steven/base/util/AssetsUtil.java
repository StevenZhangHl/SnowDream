package com.steven.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @user steven
 * @createDate 2019/2/27 13:52
 * @description 自定义
 */
public class AssetsUtil {
    public static Bitmap getBitmapFromAssets(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)) return null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
