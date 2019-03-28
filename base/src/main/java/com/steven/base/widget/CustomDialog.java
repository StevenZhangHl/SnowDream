package com.steven.base.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.flyco.dialog.widget.base.BaseDialog;
import com.steven.base.R;

/**
 * @user steven
 * @createDate 2019/3/20 13:56
 * @description 普通的对话框
 */
public class CustomDialog extends BaseDialog<CustomDialog> {
    /**
     * 监听
     */
    private OnClickListener onClickListener;
    private ExceptionListener onExceptionListener;
    /**
     * 左右按钮
     */
    private SuperButton sbLeft;
    private SuperButton sbRight;
    /**
     * 输入框
     */
    private EditText et_input_content;
    /**
     * 标题内容显示
     */
    boolean isShowTitle = true;
    boolean isShowContent = true;
    boolean isSingleButton = false;
    private boolean isShowEditText = false;

    boolean isCancle = true;
    /**
     * 标题控件
     */
    private TextView mTitle;
    private TextView mContent;
    /**
     * 标题和内容显示文字
     */
    private String content;
    private String title;

    private int contentTextSize;

    private int contentTextColor;
    private boolean isException = false;
    /**
     * 按钮颜色
     */
    private Integer[] mButtonColors;

    /**
     * 按钮文字
     */
    private String[] mButtonTexts;

    /**
     * 按钮文字颜色
     */
    private Integer[] mButtonTextColors;

    /**
     * 点击左边按钮是否关闭dialog
     * 默认关闭
     */
    private boolean isLeftDismiss = true;

    /**
     * 点击左边按钮是否关闭dialog
     * 默认关闭
     */
    private boolean isRightDismiss = true;
    private TextView tvException;
    private LinearLayout llException;

    public CustomDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f); // 屏幕宽度

        View inflate = View.inflate(mContext, R.layout.dialog_message, null);

        mTitle = (TextView) inflate.findViewById(R.id.tv_title);
        mContent = (TextView) inflate.findViewById(R.id.tv_content);
        sbLeft = (SuperButton) inflate.findViewById(R.id.stb_left);
        sbRight = (SuperButton) inflate.findViewById(R.id.stb_right);
        tvException = (TextView) inflate.findViewById(R.id.tv_exception);
        llException = (LinearLayout) inflate.findViewById(R.id.ll_exception);
        et_input_content = (EditText) inflate.findViewById(R.id.et_input_content);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        setCanceledOnTouchOutside(isCancle);
        // 标题内容按钮是否显示
        mTitle.setVisibility(isShowTitle ? View.VISIBLE : View.GONE);
        mContent.setVisibility(isShowContent ? View.VISIBLE : View.GONE);
        sbLeft.setVisibility(isSingleButton ? View.GONE : View.VISIBLE);
        et_input_content.setVisibility(isShowEditText ? View.VISIBLE : View.GONE);
        mTitle.setText(title);
        if (isException) {
            sbLeft.setVisibility(View.GONE);
            sbRight.setVisibility(View.GONE);
            llException.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onExceptionListener != null) onExceptionListener.onExceptionClickListener();
                    dismiss();
                }
            });
            llException.setVisibility(View.VISIBLE);
            Drawable drawable = mContent.getResources().getDrawable(R.mipmap.icon_delete);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTitle.setCompoundDrawables(drawable, null, null, null);
        }
        if (contentTextSize != 0) {
            mContent.setTextSize(contentTextSize);
        }
        if (contentTextColor != 0) {
            mContent.setTextSize(contentTextColor);
        }
        mContent.setText(content);
        // 按钮颜色
        if (mButtonColors != null && mButtonColors.length > 0) {
            sbLeft.setShapeSelectorNormalColor(mButtonColors[0])
                    .setShapeSelectorPressedColor(mButtonColors[1])
                    .setUseShape();
            sbRight.setShapeSelectorNormalColor(mButtonColors[0])
                    .setShapeSelectorPressedColor(mButtonColors[1])
                    .setUseShape();
        }
        // 按钮文字
        if (mButtonTexts != null && mButtonTexts.length > 0) {
            sbLeft.setText(mButtonTexts[0]);
            sbRight.setText(mButtonTexts[1]);
        }
        // 按钮文字颜色
        if (mButtonTextColors != null && mButtonTextColors.length > 0) {
            sbLeft.setTextColor(mButtonTextColors[0]);
            sbRight.setTextColor(mButtonTextColors[1]);
        }
        sbLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLeftDismiss) dismiss();
                if (onClickListener != null) onClickListener.onLeftClick();
            }
        });
        sbRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRightDismiss) dismiss();
                if (onClickListener != null) onClickListener.onRightClick();
            }
        });
    }

    /**
     * 显示标题
     *
     * @param isShowTitle
     */
    public CustomDialog isShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
        return this;
    }

    /**
     * 显示一个按钮
     *
     * @param isSingleButton
     */
    public CustomDialog isSingleButton(boolean isSingleButton, String button) {
        mButtonTexts = new String[2];
        mButtonTexts[1] = button;
        this.isSingleButton = isSingleButton;
        return this;
    }

    /**
     * 显示内容
     *
     * @param isShowContent
     */
    public CustomDialog isShowContent(boolean isShowContent) {
        this.isShowContent = isShowContent;
        return this;
    }

    /**
     * 内容文字
     *
     * @param content
     */
    public CustomDialog showContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * 是否可以点击外面取消
     *
     * @param isCancle
     */
    public CustomDialog isCancle(boolean isCancle) {
        this.isCancle = isCancle;
        return this;
    }

    /**
     * 标题文字
     *
     * @param title
     */
    public CustomDialog showTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 显示异常信息，带有红色标题
     *
     * @param title
     */
    public CustomDialog showExceptionTitle(String title) {
        this.title = title;
        this.isException = true;
        return this;
    }

    public CustomDialog isShowEditText(boolean isShowEditText) {
        this.isShowEditText = isShowEditText;
        return this;
    }

    /**
     * 按钮颜色
     *
     * @param normalColor
     * @param selectColor
     * @return
     */
    public CustomDialog setButtonColos(int normalColor, int selectColor) {
        mButtonColors = new Integer[2];
        mButtonColors[0] = normalColor;
        mButtonColors[1] = selectColor;
        return this;
    }

    public CustomDialog setContentTextSize(int textSize) {
        this.contentTextSize = textSize;
        return this;
    }

    public CustomDialog setContentTextColor(int textColor) {
        this.contentTextColor = textColor;
        return this;
    }

    /**
     * 按钮文字
     *
     * @param leftText
     * @param rightText
     * @return
     */
    public CustomDialog setButtonTexts(String leftText, String rightText) {
        mButtonTexts = new String[2];
        mButtonTexts[0] = leftText;
        mButtonTexts[1] = rightText;
        return this;
    }

    /**
     * 按钮文字颜色
     *
     * @param leftTextColor
     * @param rightTextColor
     * @return
     */
    public CustomDialog setButtonTextsColors(int leftTextColor, int rightTextColor) {
        mButtonTextColors = new Integer[2];
        mButtonTextColors[0] = leftTextColor;
        mButtonTextColors[1] = rightTextColor;
        return this;
    }

    /**
     * 按钮监听
     *
     * @param onAddBtClickLinstener
     * @return
     */
    public CustomDialog setOnClickListene(OnClickListener onAddBtClickLinstener) {
        this.onClickListener = onAddBtClickLinstener;
        return this;
    }

    /**
     * 异常状态按钮点击
     */
    public CustomDialog setOnExceptionListener(ExceptionListener onExceptionListener) {
        this.onExceptionListener = onExceptionListener;
        return this;
    }


    public void setLeftDismiss(boolean leftDismiss) {
        isLeftDismiss = leftDismiss;
    }

    public void setRightDismiss(boolean rightDismiss) {
        isRightDismiss = rightDismiss;
    }

    public interface OnClickListener {
        void onLeftClick();

        void onRightClick();
    }

    public interface ExceptionListener {
        void onExceptionClickListener();
    }
}
