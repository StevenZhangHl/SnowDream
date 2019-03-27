package com.steven.base.util;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.steven.base.R;
import com.steven.base.app.BaseApp;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @user steven
 * @createDate 2019/1/25 13:49
 * @description 自定义
 */
public class ToastUitl {
    private static Toast toast;
    private static Toast selfToast;
    private static TextView tv;
    private static WindowManager.LayoutParams mParams;
    private static boolean mIsShow;
    private static Timer mTimer;
    private static WindowManager mWdm;
    private static View mToastView;

    private static Toast initToast(CharSequence message, int duration) {
        if (toast == null) {
            toast = new Toast(BaseApp.getInstance());
            View view = LayoutInflater.from(BaseApp.getInstance()).inflate(R.layout.toast_tv, null);
            tv = (TextView) view.findViewById(R.id.toast);
            toast.setView(view);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        tv.setText(TextUtils.isEmpty(message) ? "" : message);
        return toast;
    }

    /**
     * 初始化在顶部弹出的toast
     *
     * @param message
     * @param duration
     * @return
     */
    private static Toast initSelfToast(CharSequence message, int duration) {
        if (selfToast == null) {
            selfToast = new Toast(BaseApp.getInstance());
            View view = LayoutInflater.from(BaseApp.getInstance()).inflate(R.layout.self_toast_layout, null);
            tv = (TextView) view.findViewById(R.id.tv_self_toast);
            selfToast.setView(view);
            selfToast.setDuration(duration);
            selfToast.setGravity(Gravity.TOP, 0, 150);
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 200, 0);
            animator.setDuration(200);
            animator.start();
        }
        tv.setText(TextUtils.isEmpty(message) ? "" : message);
        return selfToast;
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        initToast(message, Toast.LENGTH_SHORT).show();
    }

    public static void showTopToast(Context context, CharSequence message) {
        mIsShow = false;//记录当前Toast的内容是否已经在显示
        mWdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //通过Toast实例获取当前android系统的默认Toast的View布局
        mToastView = LayoutInflater.from(BaseApp.getInstance()).inflate(R.layout.self_toast_layout, null);
        tv = (TextView) mToastView.findViewById(R.id.tv_self_toast);
        selfToast = new Toast(BaseApp.getInstance());
        selfToast.setView(mToastView);
        selfToast.setDuration(Toast.LENGTH_LONG);
        tv.setText(message);
        mTimer = new Timer();
        //设置布局参数
        setParams(context);
        show();
    }

    private static void show() {
        if (!mIsShow) {//如果Toast没有显示，则开始加载显示
            mIsShow = true;
            mWdm.addView(mToastView, mParams);//将其加载到windowManager上
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mWdm.removeView(mToastView);
                    mIsShow = false;
                }
            }, (long) (mIsShow ? 2000 : 1500));
        }
    }

    private static void setParams(Context context) {
        mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.windowAnimations = R.style.anim_view;//设置进入退出动画效果
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mParams.gravity = Gravity.CENTER_HORIZONTAL;
        mParams.y = 250;
    }

    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int strResId) {
//		Toast.makeText(context, strResId, Toast.LENGTH_SHORT).show();
        initToast(BaseApp.getInstance().getResources().getText(strResId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        initToast(message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param strResId
     */
    public static void showLong(int strResId) {
        initToast(BaseApp.getInstance().getResources().getText(strResId), Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        initToast(message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param strResId
     * @param duration
     */
    public static void show(Context context, int strResId, int duration) {
        initToast(context.getResources().getText(strResId), duration).show();
    }
}
