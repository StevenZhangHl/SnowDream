package com.snow.weevideo.module.video.activity;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jaeger.library.StatusBarUtil;
import com.snow.weevideo.R;
import com.snow.weevideo.module.video.mvp.contract.VideoDetailContract;
import com.snow.weevideo.module.video.mvp.model.VideoDetailModel;
import com.snow.weevideo.module.video.mvp.presenter.VideoDetailPresenter;
import com.snow.weevideo.module.video.widget.CommentBottomDialog;
import com.snow.weevideo.module.video.widget.MyJz;
import com.steven.base.app.GlideApp;
import com.steven.base.base.BaseActivity;
import com.steven.base.impl.OnMultiTouchListener;
import com.steven.base.impl.OnVideoCompleteLinstener;
import com.steven.base.util.CustomMediaPlayerAssertFolder;
import com.steven.base.util.DisplayUtil;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class VideoDetailActivity extends BaseActivity<VideoDetailPresenter, VideoDetailModel> implements OnMultiTouchListener, OnVideoCompleteLinstener, VideoDetailContract.View, View.OnClickListener {
    private MyJz video_detail;
    private RelativeLayout rl_video_detail_root;
    private TextView tv_comment;
    private LinearLayout ll_reply_hide;
    private LinearLayout ll_comment_root;
    private ImageView iv_close_dialog;
    private BottomSheetBehavior behavior;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setStatusBarFullTransparent();
        iv_close_dialog = (ImageView) findViewById(R.id.iv_close_dialog);
        ll_comment_root = (LinearLayout) findViewById(R.id.ll_comment_root);
        rl_video_detail_root = (RelativeLayout) findViewById(R.id.rl_video_detail_root);
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        video_detail = (MyJz) findViewById(R.id.video_detail);
        ll_reply_hide = (LinearLayout) findViewById(R.id.ll_reply_hide);

        video_detail.startButton.setVisibility(View.GONE);
        video_detail.backButton.setVisibility(View.GONE);
        video_detail.mRetryLayout.setVisibility(View.GONE);
        tv_comment.setOnClickListener(this);
        iv_close_dialog.setOnClickListener(this);
        video_detail.setOnMultiTouchListener(this);
        video_detail.setOnVideoCompleteLinstener(this);
        GlideApp.with(mContext)
                .load(R.mipmap.bg_preview)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(mContext, 10)))
                .into(video_detail.thumbImageView);
        Jzvd.setMediaInterface(new CustomMediaPlayerAssertFolder());//进入此页面修改MediaInterface，让此页面的jzvd正常工作
        mPresenter.getVideoData(this);
        behavior = BottomSheetBehavior.from(ll_reply_hide);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    setStatusBarFullTransparent();
                } else {
                    StatusBarUtil.setLightMode(VideoDetailActivity.this);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
        behavior.setPeekHeight(0);
        initCommentView();
    }

    private int commentViewHeight;

    private void initCommentView() {
        ll_comment_root.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = ll_comment_root.getLayoutParams();
                layoutParams.height = DisplayUtil.getScreenHeight(VideoDetailActivity.this) * 2 / 3;
                ll_comment_root.setLayoutParams(layoutParams);
                commentViewHeight = layoutParams.height;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        if (ll_comment_root.getVisibility() == View.VISIBLE) {
            YoYo.with(Techniques.SlideOutDown)
                    .duration(500)
                    .onEnd(new YoYo.AnimatorCallback() {
                        @Override
                        public void call(Animator animator) {
                            ll_comment_root.setVisibility(View.INVISIBLE);
                        }
                    })
                    .interpolate(new DecelerateInterpolator())
                    .playOn(ll_comment_root);
            return;
        }
        Jzvd.releaseAllVideos();
        super.onBackPressed();
    }

    @Override
    protected void setFitsSystemWindows() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        JzvdStd.releaseAllVideos();
    }

    private boolean isAnimFinish = true;

    @Override
    public void onMultiTouch(float x, float y) {
        if (!isAnimFinish) {
            return;
        }
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.icon_praise_anim_large);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = (int) x - 50;
        layoutParams.topMargin = (int) y - 180;
        rl_video_detail_root.addView(imageView, layoutParams);
        isAnimFinish = false;
        mPresenter.startPariseAnim(this, imageView);
    }

    @Override
    public void onComplete() {
        video_detail.startVideo();
    }

    @Override
    public void setVideoData(JZDataSource jzDataSource) {
        video_detail.setUp(jzDataSource, Jzvd.NORMAL_ORIENTATION);
        video_detail.startVideo();
    }

    @Override
    public void praiseAnimEnd(ImageView imageView) {
        isAnimFinish = true;
        Runnable runnable = () -> rl_video_detail_root.removeView(imageView);
        runnable.run();
    }

    public void goToReplyView(long id) {
        ll_reply_hide.setVisibility(View.VISIBLE);
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == tv_comment) {
//            mPresenter.showCommentDialog(this);
            ll_comment_root.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInUp)
                    .duration(500)
                    .interpolate(new DecelerateInterpolator())
                    .playOn(ll_comment_root);
        }
        if (v == iv_close_dialog) {
            goToReplyView(0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float y = event.getY();
                if (ll_comment_root.getVisibility() == View.VISIBLE) {
                    if (y > 0 && y < DisplayUtil.getScreenHeight(this) -commentViewHeight) {
                        YoYo.with(Techniques.SlideOutDown)
                                .duration(500)
                                .onEnd(new YoYo.AnimatorCallback() {
                                    @Override
                                    public void call(Animator animator) {
                                        ll_comment_root.setVisibility(View.INVISIBLE);
                                    }
                                })
                                .interpolate(new DecelerateInterpolator())
                                .playOn(ll_comment_root);
                    }
                }

                Log.i("y------:", y + "---");
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
