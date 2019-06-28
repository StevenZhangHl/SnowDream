package com.snow.weevideo.module.video.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.steven.base.impl.OnMultiTouchListener;
import com.steven.base.impl.OnVideoCompleteLinstener;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JzvdStd;

/**
 * @user steven
 * @createDate 2019/6/25 18:10
 * @description 自定义
 */
public class MyJz extends JzvdStd {
    private OnMultiTouchListener onMultiTouchListener;
    private long clickTime;

    private OnVideoCompleteLinstener onVideoCompleteLinstener;

    public MyJz(Context context) {
        super(context);
    }

    public MyJz(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onVideoSizeChanged() {
        if (JZMediaManager.textureView != null) {
            JZMediaManager.textureView.setVideoSize(textureViewContainer.getWidth(), textureViewContainer.getHeight());//视频大小与控件大小一致
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        if (id == cn.jzvd.R.id.surface_container) {
            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onDown(MotionEvent e) {
                    if (System.currentTimeMillis() - clickTime < 400) {
                        onMultiTouchListener.onMultiTouch(e.getX(),e.getY());
                    }
                    clickTime = System.currentTimeMillis();
                    return super.onDown(e);
                }
            });
            gestureDetector.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
        onVideoCompleteLinstener.onComplete();
    }

    public void setOnMultiTouchListener(OnMultiTouchListener onMultiTouchListener) {
        this.onMultiTouchListener = onMultiTouchListener;
    }

    public void setOnVideoCompleteLinstener(OnVideoCompleteLinstener onVideoCompleteLinstener) {
        this.onVideoCompleteLinstener = onVideoCompleteLinstener;
    }
}
