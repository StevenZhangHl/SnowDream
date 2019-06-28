package com.horen.chart.hrlinechart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.horen.chart.R;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/08:39
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class HRChart extends FrameLayout {
    private Context mContext;
    private SuitLines suitlines;
    private TextView tvMarker;
    private View diver;
    private TextView mXBgView;
    /**
     * 第一次触摸偏移量
     */
    private boolean isFirstInit = true;
    private int lineChartColor = Color.parseColor("#6FBA2C");

    public HRChart(@NonNull Context context) {
        this(context, null);
    }

    public HRChart(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HRChart(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs, R.styleable.HRChart, 0, 0);
        try {
            lineChartColor = a.getColor(R.styleable.HRChart_defaultColor, lineChartColor);
        } finally {
            a.recycle();
        }
        View.inflate(mContext, R.layout.view_hrchart, this);
        suitlines = findViewById(R.id.suitlines);
        tvMarker = findViewById(R.id.tv_marker);
        // 折线图颜色
        suitlines.setDefaultOneLineColor(lineChartColor);
        // 图表触摸事件
        suitlines.setOnChartTouchListener(new SuitLines.onChartTouchListener() {
            @Override
            public void OnChartTouchDown(float downX, float downY) {
                if (touchListener != null) touchListener.OnTouch();
                tvMarker.setVisibility(VISIBLE);
                diver.setVisibility(VISIBLE);
                int minX = suitlines.getmXTextPoint().get(0).x;
                int maxX = suitlines.getmXTextPoint().get(suitlines.getDatas().get(0).size() - 1).x;
                if (downX <= minX) {
                    tvMarker.setX(minX - (tvMarker.getWidth() / 2));
                    diver.setX(minX - (diver.getWidth() / 2));
                } else if (downX >= maxX) {
                    tvMarker.setX(maxX - (tvMarker.getWidth() / 2));
                    diver.setX(maxX - (diver.getWidth() / 2));
                } else {
                    tvMarker.setX(downX - (tvMarker.getWidth() / 2));
                    diver.setX(downX - (diver.getWidth() / 2));
                }
                onTap(downX);
            }

            @Override
            public void OnChartTouchMove(float moveX, float moveY) {
                int minX = suitlines.getmXTextPoint().get(0).x;
                int maxX = suitlines.getmXTextPoint().get(suitlines.getDatas().get(0).size() - 1).x;
                if (moveX <= minX) {
                    tvMarker.setTranslationX(minX - (tvMarker.getWidth() / 2));
                    diver.setTranslationX(minX - (diver.getWidth() / 2));
                } else if (moveX >= maxX) {
                    tvMarker.setTranslationX(maxX - (tvMarker.getWidth() / 2));
                    diver.setTranslationX(maxX - (diver.getWidth() / 2));
                } else {
                    tvMarker.setTranslationX(moveX - (tvMarker.getWidth() / 2));
                    diver.setTranslationX(moveX - (diver.getWidth() / 2));
                }
                onTap(moveX);
            }

            @Override
            public void OnChartTouchUp(float upX, float upY) {
                if (touchListener != null) touchListener.onUnTouch();
                mXBgView.setVisibility(INVISIBLE);
                tvMarker.setVisibility(INVISIBLE);
                diver.setVisibility(INVISIBLE);
            }
        });
        suitlines.setOnChartInitListener(new SuitLines.onChartInitListener() {
            @Override
            public void onChartInit(RectF linesArea) {
                // 添加一个1dp的竖直线
                if (diver == null && (linesArea.bottom - linesArea.top > 0)) {
                    diver = new View(mContext);
                    float height = linesArea.height();
                    FrameLayout.LayoutParams lpDriver = new FrameLayout.LayoutParams(Util.dip2px(1), ViewGroup.LayoutParams.MATCH_PARENT);
                    lpDriver.gravity = Gravity.TOP;
                    lpDriver.setMargins(0, Util.dip2px(35) + Util.dip2px(10), 0, (int) (height));
                    addView(diver, lpDriver);
                    diver.setVisibility(INVISIBLE);
                }
                // 添加底部X轴文字选中背景
                if (mXBgView == null) {
                    mXBgView = new TextView(mContext);
                    mXBgView.setTextSize(12);
                    mXBgView.setTextColor(Color.WHITE);
                    mXBgView.setGravity(Gravity.CENTER);
                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(Util.dip2px(80), Util.dip2px(25));
                    addView(mXBgView, lp);
                    mXBgView.setVisibility(INVISIBLE);
                }
                // 设置提示文字的颜色
                if (lineChartColor == Color.parseColor("#6FBA2C")) { // 绿色
                    tvMarker.setBackgroundResource(R.drawable.shape_marker_green);
                    mXBgView.setBackgroundResource(R.drawable.shape_marker_green);
                    diver.setBackgroundColor(lineChartColor);
                } else if (lineChartColor == Color.parseColor("#F15B02")) { // 橙色
                    diver.setBackgroundColor(lineChartColor);
                    tvMarker.setBackgroundResource(R.drawable.shape_marker_orange);
                    mXBgView.setBackgroundResource(R.drawable.shape_marker_orange);
                }
            }
        });

    }

    public void setDefaultColor(@ColorRes int defaultColor) {
        this.lineChartColor = ContextCompat.getColor(mContext, defaultColor);
        // 折线图颜色
        suitlines.setDefaultOneLineColor(lineChartColor);
    }

    private void onTap(float upX) {
        // 第一次触摸偏移量
        int offstX = isFirstInit ? Util.dip2px(11.8f) : 0;
        if (suitlines.getDatas().isEmpty()) {
            return;
        }
        float index = (upX - suitlines.getLinesArea().left) / suitlines.getRealBetween();
        int realIndex = -1;
        if ((index - (int) index) > 0.6f) {
            realIndex = (int) index + 1;
        } else if ((index - (int) index) < 0.4f) {
            realIndex = (int) index;
        }
        if (realIndex != -1 && realIndex < suitlines.getDatas().get(0).size()) {
            mXBgView.setVisibility(VISIBLE);
            // 测量文字的宽高
            int width = (int) Util.getTextWidth(suitlines.getXyPaint(), suitlines.getDatas().get(0).get(realIndex).getExtX());
            int heigth = (int) Util.getTextHeight(suitlines.getXyPaint());
            FrameLayout.LayoutParams lp = (LayoutParams) mXBgView.getLayoutParams();
            lp.width = width + Util.dip2px(6) * 2;
            lp.height = heigth + Util.dip2px(5) * 2;
            mXBgView.setLayoutParams(lp);
            mXBgView.setText(suitlines.getDatas().get(0).get(realIndex).getExtX());
            if (realIndex == 0) {
                mXBgView.setX(suitlines.getmXTextPoint().get(realIndex).x - (mXBgView.getWidth() / 2) + width / 2 + offstX);
            } else if (realIndex == suitlines.getDatas().get(0).size() - 1) {
                mXBgView.setX(suitlines.getmXTextPoint().get(realIndex).x - (mXBgView.getWidth() / 2) - width / 2 + offstX);
            } else {
                mXBgView.setX(suitlines.getmXTextPoint().get(realIndex).x - (mXBgView.getWidth() / 2) + offstX);
            }
            tvMarker.setText(String.valueOf((int) suitlines.getDatas().get(0).get(realIndex).getValue()));
//            mXBgView.setX(suitlines.getmXTextPoint().get(realIndex).x - (mXBgView.getWidth() / 2));
            mXBgView.setY(suitlines.getmXTextPoint().get(realIndex).y + suitlines.getBasePadding() + Util.dip2px(2) + Util.dip2px(10));
            isFirstInit = false;
        }
    }

    public SuitLines getSuitlines() {
        return suitlines;
    }

    public TextView getTvMarker() {
        return tvMarker;
    }

    private onChartTouchListener touchListener;

    public void setTouchListener(onChartTouchListener touchListener) {
        this.touchListener = touchListener;
    }

    /**
     * 监听图表是否被触摸
     */
    public interface onChartTouchListener {
        void OnTouch();

        void onUnTouch();
    }
}
