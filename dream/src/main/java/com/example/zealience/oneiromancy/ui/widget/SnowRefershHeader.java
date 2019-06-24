package com.example.zealience.oneiromancy.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.zealience.oneiromancy.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.steven.base.app.GlideApp;
import com.steven.base.util.DisplayUtil;

/**
 * @user steven
 * @createDate 2019/3/15 13:40
 * @description 自定义
 */
public class SnowRefershHeader extends RelativeLayout implements RefreshHeader {
    protected ImageView mProgressView;
    protected RefreshKernel mRefreshKernel;
    protected SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;
    protected int mFinishDuration = 100;

    public SnowRefershHeader(Context context) {
        super(context);
        initView(context);
    }

    public SnowRefershHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SnowRefershHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mProgressView = new ImageView(context);
        mProgressView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideApp.with(context)
                .asGif()
                .load(R.drawable.gif_refersh_down)
                .into(mProgressView);
        LinearLayout layout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(DisplayUtil.dip2px(100), DisplayUtil.dip2px(80));
        layoutParams.addRule(CENTER_HORIZONTAL);
        layout.addView(mProgressView);
        addView(layout, layoutParams);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {
        mRefreshKernel = kernel;
    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return mFinishDuration;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }
}
