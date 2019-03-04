package com.steven.base.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.steven.base.R;
import com.steven.base.util.SPUtils;
import com.steven.base.util.ToastUitl;

/**
 * @user steven
 * @createDate 2019/2/28 11:04
 * @description 自定义
 */
public class FloatingDraggedView extends FrameLayout {
    private ViewDragHelper dragHelper;
    private Button floatingBtn;
    public static final String KEY_FLOATING_X = "KEY_FLOATING_X";
    public static final String KEY_FLOATING_Y = "KEY_FLOATING_Y";
    private Context mContext;

    public FloatingDraggedView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public FloatingDraggedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public FloatingDraggedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, 1f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View view, int i) {
                return view == floatingBtn;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                if (left > getWidth() - child.getMeasuredWidth()) {
                    left = getWidth() - child.getMeasuredWidth();
                } else if (left < 0) {
                    left = 0;
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                if (top > getHeight() - child.getMeasuredHeight()) {
                    top = getHeight() - child.getMeasuredHeight();
                } else if (top < 0) {
                    top = 0;
                }
                return top;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                savePosition();
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
                if (state == ViewDragHelper.STATE_SETTLING) { // 拖拽结束，通知观察者
                    FloatingDragger.getObservable().update();
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == floatingBtn) {
                    float x = floatingBtn.getX();
                    float y = floatingBtn.getY();
                    if (x < (getMeasuredWidth() / 2f - releasedChild.getMeasuredWidth() / 2f)) { // 0-x/2
                        if (x < releasedChild.getMeasuredWidth() / 3f) {
                            x = 0;
                        } else if (y < (releasedChild.getMeasuredHeight() * 3)) { // 0-y/3
                            y = 0;
                        } else if (y > (getMeasuredHeight() - releasedChild.getMeasuredHeight() * 3)) { // 0-(y-y/3)
                            y = getMeasuredHeight() - releasedChild.getMeasuredHeight();
                        } else {
                            x = 0;
                        }
                    } else { // x/2-x
                        if (x > getMeasuredWidth() - releasedChild.getMeasuredWidth() / 3f - releasedChild.getMeasuredWidth()) {
                            x = getMeasuredWidth() - releasedChild.getMeasuredWidth();
                        } else if (y < (releasedChild.getMeasuredHeight() * 3)) { // 0-y/3
                            y = 0;
                        } else if (y > (getMeasuredHeight() - releasedChild.getMeasuredHeight() * 3)) { // 0-(y-y/3)
                            y = getMeasuredHeight() - releasedChild.getMeasuredHeight();
                        } else {
                            x = getMeasuredWidth() - releasedChild.getMeasuredWidth();
                        }
                    }
                    // 移动到指定位置
                    dragHelper.smoothSlideViewTo(releasedChild, (int) x, (int) y);
                    invalidate();
                }
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        restorePosition();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        savePosition();
        FloatingDragger.getObservable().deleteObservers();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        floatingBtn = (Button) findViewById(R.id.floatingBtn);
        floatingBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUitl.showShort("点击了我");
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    /**
     * 更新位置
     */
    public void restorePosition() {
        // 读取保存的位置
        float x = SPUtils.getSharedFloatData(mContext, KEY_FLOATING_X);
        float y = SPUtils.getSharedFloatData(mContext, KEY_FLOATING_Y);
        if (x == -1 && y == -1) { // 初始位置
            x = getMeasuredWidth() - floatingBtn.getMeasuredWidth();
            y = getMeasuredHeight() * 2 / 3;
        }
        floatingBtn.layout((int) x, (int) y,
                (int) x + floatingBtn.getMeasuredWidth(), (int) y + floatingBtn.getMeasuredHeight());
    }

    /**
     * 保存数据
     */
    private void savePosition() {
        float x = floatingBtn.getX();
        float y = floatingBtn.getY();
        SPUtils.setSharedFloatData(mContext, KEY_FLOATING_X, x);
        SPUtils.setSharedFloatData(mContext, KEY_FLOATING_Y, y);
    }
}
