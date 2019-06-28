package com.horen.chart.hrlinechart;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.PointF;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/08:36
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class LineEntry implements Comparable<LineEntry>, Cloneable {
    static long DURATION = 800;
    private ValueAnimator VALUEANIMATOR = ValueAnimator.ofFloat(0, 1);

    /**
     * 当前点的值
     */
    private float value;
    // 当前点的额外信息（可选，x轴）
    private String extX;
    /**
     * 当前点的坐标信息,都是相对的canvas而不是linesArea
     */
    private PointF xy;

    /**
     * 当前点的动画进度，
     * 默认为1表示无动画
     */
    private float percent = 1f;

    public LineEntry(float value) {
        this.value = value;
    }

    public LineEntry(float value, String extX) {
        this.value = value;
        this.extX = extX;
    }


    public float getValue() {
        return value;
    }

    void setXY(PointF xy) {
        this.xy = xy;
    }

    PointF getXY() {
        return xy;
    }

    void setPercent(float percent) {
        this.percent = percent;
    }

    float getPercent() {
        return percent;
    }

    public void setExtX(String extX) {
        this.extX = extX;
    }

    String getExtX() {
        return extX;
    }


    void cancelToEndAnim() {
        if (VALUEANIMATOR.isRunning()) {
            VALUEANIMATOR.cancel();
        }
        percent = 1f;
    }

    void startAnim(TimeInterpolator value) {
        if (percent > 0 || VALUEANIMATOR.isRunning()) {
            return;
        }
        // 如果value小于一定阈值就不开启动画
        if (Math.abs((int) this.value) < 0.1) {
            percent = 1;
            return;
        }
        VALUEANIMATOR.setFloatValues(0, 1);
        VALUEANIMATOR.setDuration(DURATION);
        VALUEANIMATOR.setInterpolator(value);
        VALUEANIMATOR.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = (float) animation.getAnimatedValue();
            }
        });
        VALUEANIMATOR.start();
    }


    @Override
    public int compareTo(LineEntry o) {
        if (value == o.value) {
            return 0;
        } else if (value > o.value) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LineEntry)) return false;
        LineEntry unit = (LineEntry) obj;
        return value == unit.value
                && (extX == unit.extX) || (extX != null && extX.equals(unit.extX));
    }

    @Override
    public String toString() {
        return "Unit{" +
                "xy=" + xy +
                '}';
    }

    @Override
    protected LineEntry clone() {// 转化为深度拷贝，防止在集合中排序时的引用问题
        try {
            return (LineEntry) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
