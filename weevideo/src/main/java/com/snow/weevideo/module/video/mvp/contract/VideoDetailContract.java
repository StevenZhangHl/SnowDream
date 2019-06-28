package com.snow.weevideo.module.video.mvp.contract;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.steven.base.mvp.BaseModel;
import com.steven.base.mvp.BasePresenter;
import com.steven.base.mvp.BaseView;

import cn.jzvd.JZDataSource;

/**
 * @user steven
 * @createDate 2019/6/28 09:37
 * @description 自定义
 */
public interface VideoDetailContract {
    interface View extends BaseView {
        void setVideoData(JZDataSource jzDataSource);

        void praiseAnimEnd(ImageView imageView);
    }

    interface Model extends BaseModel {

    }

    abstract class Prensener extends BasePresenter<View, Model> {
        public abstract void getVideoData(Context context);

        public abstract void showMenuDialog(Context context);

        public abstract void showShareDialog(Context context);

        public abstract void showCommentDialog(Activity context);

        public abstract void startPariseAnim(Context context, ImageView imageView);
    }
}
