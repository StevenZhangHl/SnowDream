package com.example.zealience.oneiromancy.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.zealience.oneiromancy.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.steven.base.util.DisplayUtil;

/**
 * @user steven
 * @createDate 2019/3/15 13:40
 * @description 自定义
 */
@SuppressLint("RestrictedApi")
public class SnowRefershHeader extends RelativeLayout implements RefreshHeader {
    private SnowLoadingView snowLoadingView;
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
        snowLoadingView = new SnowLoadingView(context);
        snowLoadingView.setmAutoAnim(false);
        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundColor(ContextCompat.getColor(context, R.color.color_F8C400));
        layout.setGravity(Gravity.CENTER);
        LayoutParams layoutParams = new LayoutParams(DisplayUtil.dip2px(80), DisplayUtil.dip2px(80));
        layoutParams.addRule(CENTER_IN_PARENT);
        layout.addView(snowLoadingView);
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
        snowLoadingView.start();
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
        snowLoadingView.stop();
        snowLoadingView.setSize(DisplayUtil.dip2px(40));
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
