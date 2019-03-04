package com.steven.base.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.steven.base.R;
import com.steven.base.app.BaseApp;

/**
 * @user steven
 * @createDate 2019/1/25 13:49
 * @description 自定义
 */
public class ToastUitl {
    private static Toast toast;
    private static Toast toast2;
    private static TextView tv;

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
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        initToast(message, Toast.LENGTH_SHORT).show();
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
