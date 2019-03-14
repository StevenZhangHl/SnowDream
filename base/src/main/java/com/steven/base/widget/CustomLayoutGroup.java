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

    public CustomLayoutGroup(Context context) {
        super(context);
        init(context);
    }

    public CustomLayoutGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomLayoutGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initStyle(attrs);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_group, null);
        tv_left = (TextView) view.findViewById(R.id.tv_left_title);
        tv_right = (TextView) view.findViewById(R.id.tv_right_title);
        iv_right = (ImageView) view.findViewById(R.id.iv_right);
        tv_left.setCompoundDrawablePadding(DisplayUtil.dip2px(10));
        addView(view);
    }

    private void initStyle(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomLayoutGroup);
        if (typedArray.hasValue(R.styleable.CustomLayoutGroup_left_title)) {
            leftTitle = typedArray.getString(R.styleable.CustomLayoutGroup_left_title);
        }
        if (typedArray.hasValue(R.styleable.CustomLayoutGroup_left_drawable)) {
            leftDrawable = typedArray.getInt(R.styleable.CustomLayoutGroup_left_drawable, -1);
        }
        if (typedArray.hasValue(R.styleable.CustomLayoutGroup_right_drawable)) {
            rightDrawable = typedArray.getInt(R.styleable.CustomLayoutGroup_right_drawable, -1);
        }
        if (typedArray.hasValue(R.styleable.CustomLayoutGroup_right_title)) {
            rightTitle = typedArray.getString(R.styleable.CustomLayoutGroup_right_title);
        }
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
        tv_left.setCompoundDrawablesWithIntrinsicBounds(getDrawable(leftDrawable), null, null, null);
        invalidate();
    }

    public void isShowRightDrawable(boolean isShowRightDrawable) {
        if (!isShowRightDrawable) {
            iv_right.setVisibility(GONE);
        }
    }

    public void setRightDrawable(int rightDrawable) {
        this.rightDrawable = rightDrawable;
        iv_right.setImageResource(rightDrawable);
        invalidate();
    }

    public Drawable getDrawable(int drawalbeResource) {
        return getResources().getDrawable(drawalbeResource);
    }
}
