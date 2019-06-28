package com.snow.weevideo.module.video.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.snow.weevideo.module.video.mvp.contract.VideoDetailContract;
import com.snow.weevideo.module.video.widget.CommentBottomDialog;

import java.io.IOException;


import cn.jzvd.JZDataSource;

/**
 * @user steven
 * @createDate 2019/6/28 09:35
 * @description 自定义
 */
public class VideoDetailPresenter extends VideoDetailContract.Prensener{
    private int duration = 500;

    @Override
    public void getVideoData(Context context) {
        JZDataSource jzDataSource = null;
        try {
            jzDataSource = new JZDataSource(context.getAssets().openFd("mywife.mp4"));
            jzDataSource.title = "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        mView.setVideoData(jzDataSource);
    }

    @Override
    public void showMenuDialog(Context context) {

    }

    @Override
    public void showShareDialog(Context context) {

    }

    @Override
    public void showCommentDialog(Activity activity) {
        CommentBottomDialog commentBottomDialog = new CommentBottomDialog(activity, null);
        commentBottomDialog.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        commentBottomDialog.show();
    }

    @Override
    public void startPariseAnim(Context context, ImageView imageView) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(duration);
        scaleAnimation.setFillAfter(false);
        scaleAnimation.setInterpolator(new DecelerateInterpolator());
        imageView.startAnimation(scaleAnimation);
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnim = new ScaleAnimation(1f, 2.0f, 1f, 2.0f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(duration);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.1f);
        alphaAnimation.setDuration(duration);
        TranslateAnimation translateAnim = new TranslateAnimation(1.0f, 1.0f, 1.0f, -200f);
        translateAnim.setDuration(duration);
        translateAnim.setFillAfter(true);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(translateAnim);
        animationSet.addAnimation(alphaAnimation);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animationSet);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mView.praiseAnimEnd(imageView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
