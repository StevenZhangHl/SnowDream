package com.steven.base.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.steven.base.R;
import com.steven.base.util.CircularAnim;
import com.steven.base.util.DisplayUtil;
import com.youth.banner.WeakHandler;

/**
 * @user steven
 * @createDate 2019/3/29 10:11
 * @description 自定义
 */
public class RippleButton extends RelativeLayout {
    public static final int GRAY = 0;
    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int LOADING = 3;

    private Context context;
    private String grayText;
    private String redText;
    private String greenText;
    /**
     * 默认加载中
     */
    private String loadingText;
    private SuperButton stbGray;
    private SuperButton stbRed;
    private SuperButton stbGreen;
    /**
     * 按钮当前颜色，默认灰色
     */
    private int currentButton = 0;
    /**
     * 显示红色之前的按钮状态，用于红色按钮显示完毕恢复按钮状态
     */
    private int lastButtonStatus = 0;

    private RelativeLayout llLoading;
    private TextView tvLoading;
    private ImageView ivLoading;
    /**
     * 按钮自动消失的时间
     */
    private int duration = 2000;

    /**
     * 显示错误红色按钮时，定时回到原始按钮状态
     */
    private WeakHandler weakHandler;
    private float radius;
    private SuperButton sbtLoading;

    public RippleButton(Context context) {
        this(context, null);
        this.context = context;
    }

    public RippleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View.inflate(context, R.layout.custom_ripple_button, this);
        setUp(attrs);
        weakHandler = new WeakHandler();
    }

    /**
     * 设置属性
     * 按钮层级：灰色 ---> 红色 --- > 绿色 -- > 加载
     *
     * @param attrs
     */
    private void setUp(AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.RippleButton, 0, 0);
        try {
            grayText = a.getString(R.styleable.RippleButton_gray_text);
            redText = a.getString(R.styleable.RippleButton_red_text);
            greenText = a.getString(R.styleable.RippleButton_green_text);
            loadingText = a.getString(R.styleable.RippleButton_loading_text);
            radius = a.getDimension(R.styleable.RippleButton_rbt_radius,
                    DisplayUtil.dip2px(context, 4));
        } finally {
            a.recycle();
        }
        stbGray = findViewById(R.id.stb_gray);
        stbRed = findViewById(R.id.stb_red);
        stbGreen = findViewById(R.id.stb_green);
        sbtLoading = findViewById(R.id.sbt_loading);
        // 设置圆角 dp转成sp，控件内部处理
        stbGray.setShapeCornersRadius(DisplayUtil.px2dip(context, radius))
                .setUseShape();
        stbRed.setShapeCornersRadius(DisplayUtil.px2dip(context, radius))
                .setUseShape();
        stbGreen.setShapeCornersRadius(DisplayUtil.px2dip(context, radius))
                .setUseShape();
        sbtLoading.setShapeCornersRadius(DisplayUtil.px2dip(context, radius))
                .setUseShape();

        llLoading = findViewById(R.id.ll_loading);
        tvLoading = findViewById(R.id.tv_loading);
        ivLoading = findViewById(R.id.iv_loading);

        stbGray.setText(grayText);
        stbRed.setText(redText);
        // 默认显示灰色按钮的字
        stbGreen.setText(TextUtils.isEmpty(greenText) ? grayText : greenText);
        tvLoading.setText(TextUtils.isEmpty(loadingText) ? "加载中" : loadingText);

        stbGray.setTextColor(getResources().getColor(R.color.color_999));
        stbRed.setTextColor(getResources().getColor(R.color.white));
        stbGreen.setTextColor(getResources().getColor(R.color.white));

        if (isInEditMode()) {
            showGreenButton();
        }
    }

    /**
     * 显示灰色按钮
     */
    public void showGrayButton() {
        if (currentButton == RED) { // 当前颜色为红色，只需要隐藏红色按钮即可
            CircularAnim.hide(stbRed).go();
        } else if (currentButton == GREEN) { // 绿色，需要先隐藏红色按钮，然后执行涟漪动画隐藏绿色按钮
            stbRed.setVisibility(INVISIBLE);
            CircularAnim.hide(stbGreen).go();
        } else if (currentButton == LOADING) { // 加载按钮， 隐藏绿色、红色按钮，涟漪动画隐藏加载按钮
            stbGreen.setVisibility(INVISIBLE);
            stbRed.setVisibility(INVISIBLE);
            hideLoadingButton();
        }
        currentButton = GRAY; // 更新按钮颜色
        weakHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 显示红色按钮
     */
    public void showRedButton() {
        if (currentButton == GRAY) { // 当前颜色为灰色，只需要显示红色按钮即可
            CircularAnim.show(stbRed).go();
        } else if (currentButton == GREEN) { // 绿色，涟漪动画隐藏绿色按钮,显示红色按钮
            stbRed.setVisibility(VISIBLE);
            CircularAnim.hide(stbGreen).go();
        } else if (currentButton == LOADING) { // 加载布局， 隐藏绿色按钮，涟漪动画隐藏加载按钮
            stbRed.setVisibility(VISIBLE);
            stbGreen.setVisibility(INVISIBLE);
            hideLoadingButton();
        }
        // 纪录上一次按钮状态，用于恢复按钮
        if (currentButton != RED)
            lastButtonStatus = currentButton;
        // 两秒后回到原始按钮
        currentButton = RED; // 更新按钮颜色
        weakHandler.removeCallbacksAndMessages(null);
        weakHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (lastButtonStatus) { // 返回上一次按钮
                    case GREEN:
                        showGreenButton();
                        break;
                    case GRAY:
                        showGrayButton();
                        break;
                    default:
                        showGreenButton();
                        break;
                }
            }
        }, duration);
    }


    /**
     * 显示红色按钮
     */
    public void showRedButton(String redText) {
        stbRed.setText(redText);
        showRedButton();
    }

    /**
     * 显示绿色按钮
     */
    public void showGreenButton() {
        if (currentButton == GRAY) { // 当前颜色为灰色，涟漪动画显示绿色按钮即可
            CircularAnim.show(stbGreen).go();
        } else if (currentButton == RED) { // 红色，涟漪动画显示绿色按钮
            CircularAnim.show(stbGreen).go();
        } else if (currentButton == LOADING) { // 加载布局，涟漪动画隐藏 加载按钮即可
            stbGreen.setVisibility(VISIBLE);
            hideLoadingButton();
        }
        currentButton = GREEN; // 更新按钮颜色
    }


    /**
     * 显示绿色按钮
     */
    public void showGreenButton(String greenText) {
        stbGreen.setText(greenText);
        showGreenButton();
    }

    /**
     * 隐藏加载按钮，停止iv动画
     */
    private void hideLoadingButton() {
        // 停止旋转动画
        ivLoading.clearAnimation();
        CircularAnim.hide(llLoading).go();
    }

    /**
     * 显示加载按钮
     */
    public void showLoadingButton() {
        // 开始旋转动画
        rotationView(ivLoading, 0f, 359f, 500, -1);
        if (currentButton == GRAY) {
            CircularAnim.show(llLoading).go();
        } else if (currentButton == RED) {
            CircularAnim.show(llLoading).go();
        } else if (currentButton == GREEN) {
            CircularAnim.show(llLoading).go();
        }
        currentButton = LOADING; // 更新按钮颜色
    }

    /**
     * 绿色按钮点击事件
     *
     * @param listener
     */
    public RippleButton setOnGreenBTClickListener(OnClickListener listener) {
        stbGreen.setOnClickListener(listener);
        return this;
    }

    /**
     * 旋转动画
     *
     * @param view        view
     * @param from
     * @param to
     * @param duration    时间
     * @param repeatCount 选择周数
     */
    public void rotationView(View view, float from, float to, int duration, int repeatCount) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", from, to);
        animator.setRepeatCount(repeatCount);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(duration);
        animator.start();
    }

}
