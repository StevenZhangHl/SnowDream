package com.steven.base.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @user steven
 * @createDate 2019/2/28 17:55
 * @description 自定义
 */
public class FloatBallView extends RelativeLayout {
    private View mdragView;
    private int downX;
    private int downY;

    public FloatBallView(Context context) {
        super(context);
    }

    public FloatBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatBallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        mdragView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getX();
                        downY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int distanceX = (int) (event.getX() - downX);
                        int distanceY = (int) (event.getY() - downY);
                        int l = 0, t = 0, r = 0, b = 0;
                        l = v.getLeft() + distanceX;
                        t = v.getTop() + distanceY;
                        r = v.getRight() + distanceX;
                        b = v.getBottom() + distanceY;
                        if (l < 0) {
                            l = 0;
                            r = v.getWidth();
                        }
                        if (r > getWidth()) {
                            r = getWidth();
                            l = r - v.getWidth();
                        }
                        if (t < 0) {
                            t = 0;
                            b = v.getHeight();
                        }
                        if (b > getHeight()) {
                            b = getHeight();
                            t = b - v.getHeight();
                        }
                        mdragView.layout(l, t, r, b);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (event.getX() > getWidth() / 2) {
                            mdragView.layout(getWidth() - v.getWidth(), v.getTop(), getWidth(), v.getBottom());
                        } else {
                            mdragView.layout(0, v.getTop(), v.getWidth(), v.getBottom());
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void setMdragView(View mdragView) {
        this.mdragView = mdragView;
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mdragView.layout(getWidth() - mdragView.getWidth(), 0, getWidth(), mdragView.getHeight());
    }
}
