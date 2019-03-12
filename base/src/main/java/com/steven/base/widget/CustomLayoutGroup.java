package com.steven.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.steven.base.R;

/**
 * @user steven
 * @createDate 2019/3/11 11:16
 * @description 自定义
 */
public class CustomLayoutGroup extends RelativeLayout {
    private TextView tv_left;
    private ImageView iv_right;
    private int leftDrawable;
    private int rightDrawable;

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

    }

    private void initStyle(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomLayoutGroup);
        if (typedArray.hasValue(R.styleable.CustomLayoutGroup_left_title)) {
            setTv_left(typedArray.getString(R.styleable.CustomLayoutGroup_left_title));
        }
        if (typedArray.hasValue(R.styleable.CustomLayoutGroup_left_drawable)){

        }
    }

    public void setTv_left(String leftTitle) {
        tv_left.setText(leftTitle);
    }

    public void setLeftDrawable(int leftDrawable) {
        this.leftDrawable = leftDrawable;
    }

    public void setRightDrawable(int rightDrawable) {
        this.rightDrawable = rightDrawable;
    }
}
