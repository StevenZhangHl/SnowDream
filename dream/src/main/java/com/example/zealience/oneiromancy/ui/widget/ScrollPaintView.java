package com.example.zealience.oneiromancy.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zealience.oneiromancy.R;

/**
 * @user steven
 * @createDate 2019/2/21 14:55
 * @description 自定义
 */
public class ScrollPaintView extends RelativeLayout {
    /**
     * TAG
     */
    private static final String TAG = "ScrollPaintView";
    /**
     * 默认滚轴高度
     */
    private final int DEFAULT_PAINT_SCROLL_HEIGHT = 25;
    /**
     * 默认滚动的速度
     */
    private final int DEFAULT_SCROLL_SPEED = 1000;
    /**
     * 默认分割点高度
     */
    private final int DEFAULT_PARTITION_NODE = 200;
    /**
     * 默认画轴文字大小
     */
    private final int DEFAULT_PAINT_SCROLL_TXT_SIZE = 16;
    /**
     * Scroller
     */
    private Scroller mScroller;
    /**
     * 滚轴Iv
     */
    private ImageView mPaintScrollImageView;
    /**
     * 滚轴Tv
     */
    private TextView mPaintScrollTextView;
    /**
     * 图画Iv
     */
    private ImageView mPaintView;
    /**
     * 画轴图
     */
    private Bitmap mPaintScrollBp;
    /**
     * 画轴高度
     */
    private int mPaintIvHeight;
    /**
     * 画轴文字
     */
    private String mPaintScrollTxt;
    /**
     * 画轴文字大小
     */
    private float mPaintScrollTxtSize;
    /**
     * 画轴文字颜色
     */
    private int mPaintScrollTxtColor;
    /**
     * 图画开始时的高度
     */
    private int mPaintStartHeight;
    /**
     * 上一次获取的Y
     */
    private int mLastY;
    /**
     * 滚动速度
     */
    private int mScrollSpeed;
    /**
     * 分隔节点
     */
    private int partitionNode;
    /**
     * 是否是向上滚动
     */
    private boolean isScrllerTop = false;
    /**
     * 是否正在点击
     */
    private boolean isClick = false;
    /**
     * 布局参数
     */
    private LayoutParams lpp;
    /**
     * 屏幕高度
     */
    private int screenHeight;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * 回调监听
     */
    private ScrollPaintCompleteListener listener;
    /**
     * 上一次滚动的Y值
     */
    private int lastScrollY;

    /**
     * 回调接口
     */
    public interface ScrollPaintCompleteListener {
        /**
         * 点击时的回调
         */
        public void onScrollTouch(TextView tv);

        /**
         * 收缩时的回调
         */
        public void onScrollTop(TextView tv);

        /**
         * 展开时的回调
         */
        public void onScrollBottom(TextView tv);

        /**
         * 滚动中的回调
         */
        public void onScrollMove(TextView tv);

    }


    public ScrollPaintView(Context context) {
        this(context, null);
    }

    public ScrollPaintView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollPaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollPaintView);
        mPaintIvHeight = (int) ta.getDimension(R.styleable.ScrollPaintView_paintScrollHeight, TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, DEFAULT_PAINT_SCROLL_HEIGHT, getResources().getDisplayMetrics()));
        mScrollSpeed = ta.getInteger(R.styleable.ScrollPaintView_scrollSpeed, DEFAULT_SCROLL_SPEED);
        partitionNode = ta.getInteger(R.styleable.ScrollPaintView_scrollPartitionNode, DEFAULT_PARTITION_NODE);
        mPaintScrollBp = drawableToBitamp(ta.getDrawable(R.styleable.ScrollPaintView_paintScrollSrc));
        mPaintScrollTxt = ta.getString(R.styleable.ScrollPaintView_paintScrollTxt);
        mPaintScrollTxtColor = ta.getColor(R.styleable.ScrollPaintView_paintScrollTxtColor, Color.BLACK);
        mPaintScrollTxtSize = px2sp(ta.getDimensionPixelSize(R.styleable.ScrollPaintView_paintScrollTxtSize, DEFAULT_PAINT_SCROLL_TXT_SIZE));
        ta.recycle();
        init();
        makePaintScroll();
        handleView();
    }

    /**
     * 设置paintView
     *
     * @param paintView
     */
    public void setPaintView(ImageView paintView) {
        if (null == paintView) {
            Log.e(TAG, "设置的View为空");
            return;
        }
        // 处理图片,对图片按照屏幕宽高比进行缩放
        Bitmap bp = drawableToBitamp(paintView.getDrawable());
        paintView.setImageBitmap(scaleBitmal(bp));
        // 设置缩放形式
        paintView.setScaleType(ImageView.ScaleType.MATRIX);
        mPaintView = paintView;
    }

    /**
     * 设置回调
     */
    public void setScrollPaintCompleteListener(ScrollPaintCompleteListener listener) {
        if (null != listener) {
            this.listener = listener;
        }
    }

    /**
     * 初始化
     */
    private void init() {
        mScroller = new Scroller(getContext());
        lpp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        // 获取屏幕信息
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((AppCompatActivity) getContext()).getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        // 屏幕高度
        screenHeight = displayMetrics.heightPixels;
        // 屏幕宽度
        screenWidth = displayMetrics.widthPixels;
    }


    /**
     * 创建滚轴
     */
    private void makePaintScroll() {
        // 如果已经存在,则不再创建
        if (null != mPaintScrollImageView || null != mPaintScrollTextView) {
            return;
        }
        // 创建滚轴
        mPaintScrollImageView = new ImageView(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.height = mPaintIvHeight;
        mPaintScrollImageView.setLayoutParams(lp);
        mPaintScrollImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mPaintScrollImageView.setImageBitmap(null == mPaintScrollBp ? makeDefaultScroll() : mPaintScrollBp);
        addView(mPaintScrollImageView);
        // 创建文字
        mPaintScrollTextView = new TextView(getContext());
        LayoutParams lpt = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lpt.height = mPaintIvHeight;
        mPaintScrollTextView.setLayoutParams(lpt);
        mPaintScrollTextView.setText(null == mPaintScrollTxt ? "" : mPaintScrollTxt);
        mPaintScrollTextView.setTextSize(mPaintScrollTxtSize);
        mPaintScrollTextView.setTextColor(mPaintScrollTxtColor);
        mPaintScrollTextView.setGravity(Gravity.CENTER);
        addView(mPaintScrollTextView);
    }


    /**
     * 测量方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (null != mPaintView && getTop() + mPaintIvHeight != mPaintView.getHeight()) {
            // 重新设置图画高度
            mPaintStartHeight = getTop() + mPaintIvHeight / 2;
            lpp.height = mPaintStartHeight;
            mPaintView.setLayoutParams(lpp);
        }
        // 测量状态栏高度
        Rect frame = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        // 高度为屏幕高度减去状态栏高度和top的高度
        setMeasuredDimension(screenWidth, screenHeight - getTop() - statusBarHeight);
    }

    /**
     * 处理View
     */
    private void handleView() {
        mPaintScrollImageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (null == mPaintView) {
                    Log.e(TAG, "设置的View为空");
                    return true;
                }
                // 获取点击的XY坐标
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        // 请求处理点击事件
                        requestDisallowInterceptTouchEvent(true);
                        isClick = true;
                        mLastY = y;
                        if (!mScroller.isFinished()) { // 如果上次的调用没有执行完就取消。
                            mScroller.abortAnimation();
                        }
                        if (null != listener) {
                            listener.onScrollTouch(mPaintScrollTextView);
                        }
                        return true;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        // 移动的距离
                        int dy = y - mLastY;
                        mLastY = y;
                        // 滑动
                        scrollBy(0, -dy);
                        //  如果是向上滑动并且是在初始位置,则不去做处理
                        if (getScrollY() >= 0 && dy <= 0) {
                            lpp.height = mPaintStartHeight;
                            mPaintView.setLayoutParams(lpp);
                            scrollTo(0, 0);
                            return true;
                        }
                        // 如果是向下滑动并且超过屏幕高度,则不去处理
                        if (Math.abs(getScrollY()) >= getHeight() - mPaintIvHeight && dy >= 0) {
                            lpp.height = mPaintStartHeight + getHeight() - mPaintIvHeight;
                            mPaintView.setLayoutParams(lpp);
                            scrollTo(0, -(getHeight() - mPaintIvHeight));
                            return true;
                        }
                        // 滚动回调
                        if (null != listener) {
                            listener.onScrollMove(mPaintScrollTextView);
                        }
                        // 重新设置显示的控件高度
                        if (Math.abs(getScrollY()) > 0) {
                            lpp.height = mPaintStartHeight + Math.abs(getScrollY());
                            mPaintView.setLayoutParams(lpp);
                        }
                        return true;
                    }
                    case MotionEvent.ACTION_UP:
                        // 恢复事件处理
                        requestDisallowInterceptTouchEvent(false);
                        isClick = false;
                        // 没有发生移动
                        if (getScrollY() >= 0) {
                            if (null != listener) {
                                listener.onScrollTop(mPaintScrollTextView);
                            }
                            return true;
                        }
                        if (-getScrollY() < partitionNode) {   // 如果小于临界值,则返回起始坐标
                            // XY都从滑动的距离回去，最后一个参数是多少毫秒内执行完这个动作。
                            isScrllerTop = true;
                            mScroller.startScroll(getScrollX(), getScrollY(), -getScrollX(), -getScrollY(), mScrollSpeed);
                        } else {    // 如果大于临界值,则展开
                            isScrllerTop = false;
                            mScroller.startScroll(getScrollX(), getScrollY(), -getScrollX(), -(getHeight() - (-getScrollY()) - mPaintIvHeight), mScrollSpeed);
                        }
                        invalidate();
                        return true;
                }
                return false;
            }
        });
    }


    /**
     * 滑动处理
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {  // 计算新位置，并判断上一个滚动是否完成。
            // 请求处理点击事件,防止父控件滑动
            requestDisallowInterceptTouchEvent(true);
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 重新设置显示的控件高度
            if (0 < Math.abs(mScroller.getCurrY())) {
                if (!isScrllerTop) {
                    lpp.height = mPaintStartHeight + Math.abs(mScroller.getCurrY()) + mPaintIvHeight / 2;
                } else {
                    lpp.height = mPaintStartHeight + Math.abs(mScroller.getCurrY()) - mPaintIvHeight / 2;
                }
            } else {
                lpp.height = mPaintStartHeight;
            }
            mPaintView.setLayoutParams(lpp);
            invalidate();
        } else {
            // 重新设置画图高度,防止高度异常
            if (mPaintView.getHeight() > mPaintStartHeight + Math.abs(mScroller.getCurrY()) && !isScrllerTop && mScroller.getStartY() > 0) {
                lpp.height = mPaintStartHeight + Math.abs(mScroller.getCurrY());
                mPaintView.setLayoutParams(lpp);
            }
        }
        // 防止多次调用
        if (lastScrollY != mScroller.getCurrY()) {
            // 收缩完成
            if (mScroller.getCurrY() >= 0 && !isClick) {
                if (null != listener) {
                    listener.onScrollTop(mPaintScrollTextView);
                }
            }
            // 展开完成
            if (-mScroller.getCurrY() >= getHeight() - mPaintIvHeight && !isClick) {
                if (null != listener) {
                    listener.onScrollBottom(mPaintScrollTextView);
                }
            }
            lastScrollY = mScroller.getCurrY();
        }
    }

    /**
     * 重置滚动
     */
    public void replaceScroll() {
        // 重置信息
        scrollTo(0, 0);
        mScroller.setFinalY(0);
        lastScrollY = 0;
        lpp.height = mPaintStartHeight;
        mPaintView.setLayoutParams(lpp);
        if (null != listener) {
            listener.onScrollTop(mPaintScrollTextView);
        }
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitamp(Drawable drawable) {
        if (null == drawable) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 按照屏幕宽高缩放图片
     *
     * @param bp
     * @return
     */
    private Bitmap scaleBitmal(Bitmap bp) {
        // 宽度比例
        float scaleW = (float) screenWidth / (float) bp.getWidth();
        // 高度比例
        float scaleH = (float) screenHeight / (float) bp.getHeight();
        // 矩阵,用于缩放图片
        Matrix matrix = new Matrix();
        matrix.postScale(scaleW, scaleH);
        // 缩放后的图片
        Bitmap scaleBp = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), matrix, true);
        return scaleBp;
    }

    /**
     * 设置默认的滚轴
     *
     * @return
     */
    private Bitmap makeDefaultScroll() {
        Bitmap defaultBp = Bitmap.createBitmap(screenWidth, mPaintIvHeight,
                Bitmap.Config.ARGB_8888);
        //填充颜色
        defaultBp.eraseColor(Color.parseColor("#FF0000"));
        return defaultBp;

    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public int px2sp(float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
