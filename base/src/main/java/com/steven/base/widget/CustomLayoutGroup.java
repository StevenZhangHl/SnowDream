package com.steven.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.steven.base.R;
import com.steven.base.util.DisplayUtil;

/**
 * @user steven
 * @createDate 2019/3/11 11:16
 * @description 左边和右边有标题和图标的通用布局
 */
public class CustomLayoutGroup extends RelativeLayout {
    private TextView tv_left;
    private ImageView iv_right;
    private TextView tv_right;
    private int leftDrawable;
    private int rightDrawable;
    private String leftTitle;
    private String rightTitle;
    private View view_line;
    private boolean isHidenLine;
    private boolean isHidenRightDrawable;

    public CustomLayoutGroup(Context context) {
        super(context);
        init(context);
    }

    public CustomLayoutGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initStyle(attrs, 0);
    }

    public CustomLayoutGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initStyle(attrs, defStyleAttr);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_group, null);
        tv_left = (TextView) view.findViewById(R.id.tv_left_title);
        tv_right = (TextView) view.findViewById(R.id.tv_right_title);
        iv_right = (ImageView) view.findViewById(R.id.iv_right);
        view_line = (View) view.findViewById(R.id.view_line);
        tv_left.setCompoundDrawablePadding(DisplayUtil.dip2px(10));
        addView(view);
    }

    private void initStyle(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomLayoutGroup, defStyleAttr, 0);
        if (typedArray.hasValue(R.styleable.CustomLayoutGroup_left_title)) {
            leftTitle = typedArray.getString(R.styleable.CustomLayoutGroup_left_title);
        }
        if (typedArray.hasValue(R.styleable.CustomLayoutGroup_left_drawable)) {
            leftDrawable = typedArray.getResourceId(R.styleable.CustomLayoutGroup_left_drawable, -1);
        }
        if (typedArray.hasValue(R.styleable.CustomLayoutGroup_right_drawable)) {
            rightDrawable = typedArray.getResourceId(R.styleable.CustomLayoutGroup_right_drawable, -1);
        }
        if (typedArray.hasValue(R.styleable.CustomLayoutGroup_right_title)) {
            rightTitle = typedArray.getString(R.styleable.CustomLayoutGroup_right_title);
        }
        if (typedArray.hasValue(R.styleable.CustomLayoutGroup_hide_line)) {
            isHidenLine = typedArray.getBoolean(R.styleable.CustomLayoutGroup_hide_line, false);
        }
        if (typedArray.hasValue(R.styleable.CustomLayoutGroup_hide_right_drawable)) {
            isHidenRightDrawable = typedArray.getBoolean(R.styleable.CustomLayoutGroup_hide_right_drawable, false);
        }
        setTv_left(leftTitle);
        setTv_right(rightTitle);
        setLeftDrawable(leftDrawable);
        setRightDrawable(rightDrawable);
        hidenLine(isHidenLine);
        hideRightDrawable(isHidenRightDrawable);
        typedArray.recycle();
    }

    public void setTv_left(String leftTitle) {
        this.leftTitle = leftTitle;
        tv_left.setText(leftTitle);
        invalidate();
    }

    public void setTv_right(String rightTitle) {
        this.rightTitle = rightTitle;
        tv_right.setText(rightTitle);
        invalidate();
    }

    public void setLeftDrawable(int leftDrawable) {
        this.leftDrawable = leftDrawable;
        if (leftDrawable != 0) {
            tv_left.setCompoundDrawablesWithIntrinsicBounds(getDrawable(leftDrawable), null, null, null);
            invalidate();
        }
    }

    public void hideRightDrawable(boolean isHidenRightDrawable) {
        iv_right.setVisibility(isHidenRightDrawable ? GONE : VISIBLE);
    }

    public void setRightDrawable(int rightDrawable) {
        this.rightDrawable = rightDrawable;
        if (rightDrawable != 0) {
            iv_right.setImageResource(rightDrawable);
            invalidate();
        }
    }

    public Drawable getDrawable(int drawalbeResource) {
        return getResources().getDrawable(drawalbeResource);
    }

    /**
     * 隐藏底部分界线
     */
    public void hidenLine(boolean isHidenLine) {
        view_line.setVisibility(isHidenLine ? GONE : VISIBLE);
    }
}
