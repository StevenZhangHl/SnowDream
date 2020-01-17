package com.example.zealience.oneiromancy.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.steven.base.util.DisplayUtil;

/**
 * @user steven
 * @createDate 2019/4/24 10:26
 * @description 自定义
 */
public class CustomView extends View {
    private Paint mRectPaint;
    private Paint mTextPaint;
    private Paint mOvalPaint;
    private int screenW;
    private int screenH;
    private Context mContext;
    private int radius = 200;
    private Paint heartPaint;
    private RectF rectFTopLeft;
    private RectF rectFTopRight;
    private RectF rectFBottom;
    private int width;
    private int height;
    private Paint whitePaint1;
    private Paint whitePaint2;
    private RectF rectFLeft;
    private RectF rectFRight;

    public CustomView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenW = DisplayUtil.getScreenWidth(mContext);
        screenH = DisplayUtil.getScreenHeight(mContext);
        width = 3 * getMeasuredWidth() / 4;//取宽的3/4来绘制，让旋转图形过后，心形能够完全展示
        height = 3 * getMeasuredHeight() / 4;//取高的3/4来绘制，让旋转图形过后，心形能够完全展示
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void init() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(DisplayUtil.dip2px(16));

        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setColor(Color.RED);

        mOvalPaint = new Paint();
        mOvalPaint.setAntiAlias(true);
        mOvalPaint.setColor(Color.RED);
        heartPaint = new Paint();//画心的红色画笔
        heartPaint.setAntiAlias(true);//抗锯齿
        heartPaint.setColor(Color.RED);

        whitePaint1 = new Paint();//给心形描边的白色画笔
        whitePaint1.setAntiAlias(true);
        whitePaint1.setColor(Color.WHITE);
        whitePaint1.setStrokeWidth(4.0f);//画笔的宽度
        whitePaint1.setStyle(Paint.Style.STROKE);//FILL:实心     STROKE:空心

        whitePaint2 = new Paint();//给心形描边的白色画笔
        whitePaint2.setAntiAlias(true);
        whitePaint2.setStrokeWidth(4.0f);
        whitePaint2.setColor(Color.WHITE);

        rectFTopLeft = new RectF();//心左上角位置
        rectFTopRight = new RectF();//心右上角位置
        rectFBottom = new RectF();//心下部位置
        rectFLeft = new RectF();//心下部左边描边位置
        rectFRight = new RectF();//心下部右边描边位置
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.rotate(45, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        rectFTopLeft.set(0, height / 3, width * 2 / 3, height);
        rectFLeft.set(0, height / 3, width * 2 / 3, height);
        canvas.drawArc(rectFTopLeft, 90, 180, true, mRectPaint);
        canvas.drawArc(rectFLeft, 90, 180, false, whitePaint1);
        rectFTopRight.set(width / 3, 0, width, height * 2 / 3);
        rectFRight.set(width / 3, 0, width, height * 2 / 3);
        canvas.drawArc(rectFTopRight, 180, 180, true, mRectPaint);
        canvas.drawArc(rectFRight, 180, 180, false, whitePaint1);
        rectFBottom.set(width / 3, height / 3, width, height);
        canvas.drawRect(rectFBottom, mRectPaint);
        canvas.drawLine(width / 3, height, width, height, whitePaint2);
        canvas.drawLine(width, height, width, height / 3, whitePaint2);
    }

    /**
     * 角度转弧度公式
     *
     * @param degree
     * @return
     */
    private float degree2Radian(int degree) {
        return (float) (Math.PI * degree / 180);
    }
}
