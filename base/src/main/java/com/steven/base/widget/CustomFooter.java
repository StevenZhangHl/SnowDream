package com.steven.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.steven.base.R;
import com.steven.base.util.DisplayUtil;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @user steven
 * @createDate 2019/3/14 09:52
 * @description 自定义
 */
public class CustomFooter extends RelativeLayout implements RefreshFooter {
    public static String REFRESH_FOOTER_LOADING;
    public static String REFRESH_FOOTER_REFRESHING;
    public static String REFRESH_FOOTER_FINISH;
    public static String REFRESH_FOOTER_FAILED;
    public static String REFRESH_FOOTER_ALLLOADED;

    protected TextView mTitleText;
    protected ImageView mProgressView;
    protected Drawable mArrowDrawable;
    protected ProgressDrawable mProgressDrawable;
    protected SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;
    protected RefreshKernel mRefreshKernel;
    protected Integer mAccentColor;
    protected Integer mPrimaryColor;
    protected int mBackgroundColor;
    protected int mFinishDuration = 500;
    protected int mPaddingTop = 20;
    protected int mPaddingBottom = 20;
    protected boolean mNoMoreData = false;

    public CustomFooter(Context context) {
        super(context);
        this.initView(context, null, 0);
    }

    public CustomFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context, attrs, 0);
    }

    public CustomFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        REFRESH_FOOTER_LOADING = context.getString(R.string.loading_footer);
        REFRESH_FOOTER_REFRESHING = context.getString(R.string.refresh_footer);
        REFRESH_FOOTER_FINISH = "";
        REFRESH_FOOTER_FAILED = context.getString(R.string.loading_fail);
        REFRESH_FOOTER_ALLLOADED = context.getString(R.string.loading_finish_footer);

        DensityUtil density = new DensityUtil();

        LinearLayout layout = new LinearLayout(context);
        layout.setId(android.R.id.widget_frame);
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams lpProgress = new LayoutParams(density.dip2px(20), density.dip2px(20));
        lpProgress.addRule(CENTER_VERTICAL);
        lpProgress.addRule(RIGHT_OF, android.R.id.widget_frame);
        mProgressView = new ImageView(context);
        layout.addView(mProgressView, lpProgress);
        mTitleText = new TextView(context);
        mTitleText.setWidth(DisplayUtil.dip2px(120));
        mTitleText.setGravity(Gravity.CENTER);
        mTitleText.setTextColor(ContextCompat.getColor(context, R.color.color_333));

        LinearLayout.LayoutParams lpHeaderText = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layout.addView(mTitleText, lpHeaderText);
        mTitleText.setVisibility(GONE);
        LayoutParams lpHeaderLayout = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpHeaderLayout.addRule(CENTER_IN_PARENT);
        addView(layout, lpHeaderLayout);

        if (!isInEditMode()) {
            mProgressView.setVisibility(GONE);
        }

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClassicsFooter);

        lpProgress.rightMargin = ta.getDimensionPixelSize(R.styleable.ClassicsFooter_srlDrawableMarginRight, density.dip2px(20));

        lpProgress.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableProgressSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableProgressSize, lpProgress.height);

        lpProgress.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, lpProgress.height);

        mFinishDuration = ta.getInt(R.styleable.ClassicsFooter_srlFinishDuration, mFinishDuration);
        mSpinnerStyle = SpinnerStyle.values()[ta.getInt(R.styleable.ClassicsFooter_srlClassicsSpinnerStyle, mSpinnerStyle.ordinal())];

        if (ta.hasValue(R.styleable.ClassicsFooter_srlDrawableProgress)) {
            mProgressView.setImageDrawable(ta.getDrawable(R.styleable.ClassicsFooter_srlDrawableProgress));
        } else {
            mProgressDrawable = new ProgressDrawable();
            mProgressDrawable.setColor(0xff666666);
            mProgressView.setImageDrawable(mProgressDrawable);
        }

        if (ta.hasValue(R.styleable.ClassicsFooter_srlTextSizeTitle)) {
            mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimensionPixelSize(R.styleable.ClassicsFooter_srlTextSizeTitle, DensityUtil.dp2px(16)));
        } else {
            mTitleText.setTextSize(16);
        }

        if (ta.hasValue(R.styleable.ClassicsFooter_srlPrimaryColor)) {
            setPrimaryColor(ta.getColor(R.styleable.ClassicsFooter_srlPrimaryColor, 0));
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_srlAccentColor)) {
            setAccentColor(ta.getColor(R.styleable.ClassicsFooter_srlAccentColor, 0));
        }

        ta.recycle();

        if (getPaddingTop() == 0) {
            if (getPaddingBottom() == 0) {
                setPadding(getPaddingLeft(), mPaddingTop = density.dip2px(20), getPaddingRight(), mPaddingBottom = density.dip2px(20));
            } else {
                setPadding(getPaddingLeft(), mPaddingTop = density.dip2px(20), getPaddingRight(), mPaddingBottom = getPaddingBottom());
            }
        } else {
            if (getPaddingBottom() == 0) {
                setPadding(getPaddingLeft(), mPaddingTop = getPaddingTop(), getPaddingRight(), mPaddingBottom = density.dip2px(20));
            } else {
                mPaddingTop = getPaddingTop();
                mPaddingBottom = getPaddingBottom();
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
        } else {
            setPadding(getPaddingLeft(), mPaddingTop, getPaddingRight(), mPaddingBottom);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //</editor-fold>

    //<editor-fold desc="RefreshFooter">

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {
        mRefreshKernel = kernel;
        mRefreshKernel.requestDrawBackgroundForFooter(mBackgroundColor);
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void onPulling(float percent, int offset, int footerHeight, int extendHeight) {
        if (!mNoMoreData) {
            mProgressView.setVisibility(VISIBLE);
            if (mProgressDrawable != null) {
                mProgressDrawable.start();
            } else {
                mProgressView.animate().rotation(36000).setDuration(100000);
            }
        }
    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout layout, int footerHeight, int extendHeight) {
        if (!mNoMoreData) {
            mProgressView.setVisibility(VISIBLE);
            if (mProgressDrawable != null) {
                mProgressDrawable.start();
            } else {
                mProgressView.animate().rotation(36000).setDuration(100000);
            }
        }
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout layout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (!mNoMoreData) {
            if (mProgressDrawable != null) {
                mProgressDrawable.stop();
            } else {
                mProgressView.animate().rotation(0).setDuration(300);
            }
            mProgressView.setVisibility(GONE);
            if (!success) {
                mTitleText.setText(REFRESH_FOOTER_FAILED);
            }
            return mFinishDuration;
        }
        return 0;
    }

    /**
     * ClassicsFooter 在(SpinnerStyle.FixedBehind)时才有主题色
     */
    @Override
    @Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (mSpinnerStyle == SpinnerStyle.FixedBehind) {
            if (colors.length > 0) {
                if (!(getBackground() instanceof BitmapDrawable)) {
                    setPrimaryColor(colors[0]);
                }
                if (colors.length > 1) {
                    setAccentColor(colors[1]);
                } else {
                    setAccentColor(colors[0] == 0xffffffff ? 0xff666666 : 0xffffffff);
                }
            }
        }
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
            if (noMoreData) {
                mTitleText.setVisibility(VISIBLE);
                mTitleText.setText(REFRESH_FOOTER_ALLLOADED);
            }
            if (mProgressDrawable != null) {
                mProgressDrawable.stop();
            } else {
                mProgressView.animate().rotation(0).setDuration(300);
            }
            mProgressView.setVisibility(GONE);
        }
        return true;
    }

    @Override
    @NonNull
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        if (!mNoMoreData) {
            switch (newState) {
                case None:
//                    restoreRefreshLayoutBackground();
                    mProgressView.setVisibility(GONE);
                case LoadReleased:
                    mProgressView.setVisibility(VISIBLE);
                    break;
                case LoadFinish:
                    mProgressView.setVisibility(GONE);
                    break;
                case ReleaseToLoad:
                    mProgressView.setVisibility(GONE);
//                    replaceRefreshLayoutBackground(refreshLayout);
                    break;
                case Refreshing:
                    mProgressView.setVisibility(GONE);
                    break;
                default:
                    break;
            }
        }
    }

    public CustomFooter setPrimaryColor(@ColorInt int primaryColor) {
        mBackgroundColor = mPrimaryColor = primaryColor;
        if (mRefreshKernel != null) {
            mRefreshKernel.requestDrawBackgroundForFooter(mPrimaryColor);
        }
        return this;
    }

    public CustomFooter setAccentColor(@ColorInt int accentColor) {
        mAccentColor = accentColor;
        mTitleText.setTextColor(accentColor);
        if (mProgressDrawable != null) {
            mProgressDrawable.setColor(accentColor);
        }
        if (mArrowDrawable != null) {
        }
        return this;
    }
}
