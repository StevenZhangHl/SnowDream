package com.steven.base.widget;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steven.base.R;

/**
 * @user steven
 * @createDate 2019/1/24 13:53
 * @description 自定义
 */
public class LoadingDialog {
    /**
     * 加载数据对话框
     */
    private Dialog mLoadingDialog;
    private ImageView iv_animation;

    /**
     * 显示加载对话框
     *
     * @param context    上下文
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public Dialog showDialogForLoading(Activity context, String msg, boolean cancelable) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);

        iv_animation = (ImageView) view.findViewById(R.id.iv_animation);
        Animation rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_anim);
        LinearInterpolator lir = new LinearInterpolator();
        rotate.setInterpolator(lir);
        iv_animation.startAnimation(rotate);
        loadingText.setText(msg);

        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


        //这些是设置对话框大小，位置，下面activity的亮度
        WindowManager.LayoutParams params = mLoadingDialog.getWindow()
                .getAttributes();
        // 这个是设置activity的亮度的dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的 ，1.0f时候，背景全部变黑暗。
        params.dimAmount = 0.50f;
        // 设置对话框的布局参数为居中
        mLoadingDialog.getWindow().setAttributes(params);
        mLoadingDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        mLoadingDialog.show();
        return mLoadingDialog;
    }

    public Dialog showDialogForLoading(Activity context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
        iv_animation = (ImageView) view.findViewById(R.id.iv_animation);
        Animation rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_anim);
        LinearInterpolator lir = new LinearInterpolator();
        rotate.setInterpolator(lir);
        iv_animation.startAnimation(rotate);

        loadingText.setText("加载中");

        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        //这些是设置对话框大小，位置，下面activity的亮度
        WindowManager.LayoutParams params = mLoadingDialog.getWindow()
                .getAttributes();
        // 这个是设置activity的亮度的dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的 ，1.0f时候，背景全部变黑暗。
        params.dimAmount = 0.50f;
        // 设置对话框的布局参数为居中
        mLoadingDialog.getWindow().setAttributes(params);
        mLoadingDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        mLoadingDialog.show();
        return mLoadingDialog;
    }

    /**
     * 关闭加载对话框
     */
    public void cancelDialogForLoading() {
        if (mLoadingDialog != null) {
            iv_animation.clearAnimation();
            mLoadingDialog.cancel();
        }
    }
}
