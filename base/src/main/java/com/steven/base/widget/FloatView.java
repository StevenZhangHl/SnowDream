package com.steven.base.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.steven.base.util.DisplayUtil;

/**
 * @user steven
 * @createDate 2019/2/27 15:29
 * @description 自定义
 */
public class FloatView extends RelativeLayout {
    private View mDragView;
    private ViewDragHelper viewDragHelper;
    private boolean isHideSelfHalf = false;

    public FloatView(Context context) {
        super(context);
        init();
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mDragView.layout(0, getHeight() - mDragView.getHeight() - DisplayUtil.dip2px(50), mDragView.getWidth(), getHeight() - DisplayUtil.dip2px(50));
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View view, int i) {
                return view == mDragView;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                if (left < 0) {
                    left = 0;
                } else if (left > getMeasuredWidth() - child.getMeasuredWidth()) {
                    left = getMeasuredWidth() - child.getMeasuredWidth();
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                if (top < 0) {
                    top = 0;
                } else if (top > getMeasuredHeight() - child.getMeasuredHeight()) {
                    top = getMeasuredHeight() - child.getMeasuredHeight();
                }
                return top;
            }

            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild == mDragView) {
                    if ((releasedChild.getLeft() + releasedChild.getMeasuredWidth() / 2) > getMeasuredWidth() / 2) {
                        if (isHideSelfHalf) {
                            viewDragHelper.smoothSlideViewTo(releasedChild, getMeasuredWidth() - releasedChild.getMeasuredWidth() / 2, releasedChild.getTop());
                        } else {
                            viewDragHelper.smoothSlideViewTo(releasedChild, getMeasuredWidth() - releasedChild.getMeasuredWidth(), releasedChild.getTop());
                        }
                    } else {
                        if (isHideSelfHalf) {
                            viewDragHelper.smoothSlideViewTo(releasedChild, -releasedChild.getMeasuredWidth() / 2, releasedChild.getTop());
                        }
                        viewDragHelper.smoothSlideViewTo(releasedChild, 0, releasedChild.getTop());
                    }
                    ViewCompat.postInvalidateOnAnimation(FloatView.this);
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(FloatView.this);
        }
    }

    public void setHideSelfHalf(boolean isHideSelfHalf) {
        this.isHideSelfHalf = isHideSelfHalf;
    }

    public void setmDragView(View mDragView) {
        this.mDragView = mDragView;
    }

}
