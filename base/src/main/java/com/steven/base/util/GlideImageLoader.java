package com.steven.base.util;

import android.content.Context;
import android.widget.ImageView;

import com.steven.base.app.GlideApp;
import com.youth.banner.loader.ImageLoader;

/**
 * @user steven
 * @createDate 2019/3/5 13:26
 * @description banner的图片加载器
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideApp.with(context)
                .load(path)
                .into(imageView);
    }
}
