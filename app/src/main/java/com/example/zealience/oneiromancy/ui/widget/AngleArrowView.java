package com.example.zealience.oneiromancy.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * @user steven
 * @createDate 2019/4/25 11:00
 * @description 自定义
 */
public class AngleArrowView extends View {
    private Paint mArrowMainPaint;
    private Paint mArrowWingPaint;
    private int width;
    private int height;

    public AngleArrowView(Context context) {
        super(context);
        init(context);
    }

    public AngleArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AngleArrowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mArrowMainPaint = new Paint();
        mArrowMainPaint.setColor(Color.BLACK);
        mArrowMainPaint.setAntiAlias(true);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(-15,0,height-30);
        Path path = new Path();
        path.moveTo(0,height-30);
        path.lineTo(70,height-60);
        path.lineTo(70,height-40);
        path.lineTo(width,height-40);
        path.lineTo(width,height-20);
        path.lineTo(70,height-20);
        path.lineTo(70,height);
        path.close();
        canvas.drawPath(path,mArrowMainPaint);
    }
}
