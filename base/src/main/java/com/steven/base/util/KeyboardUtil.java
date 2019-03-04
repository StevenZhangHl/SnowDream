package com.steven.base.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @user steven
 * @createDate 2019/1/25 15:04
 * @description 自定义
 */
public class KeyboardUtil {
    /**
     * 打开软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 关闭软键盘
     */
    public static void closeAllKeybord(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && context.getCurrentFocus() != null) {
            if (context.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 判断当前软键盘是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputShow(Activity activity) {

        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
//       inputmanger.hideSoftInputFromWindow(view.getWindowToken(),0);

            return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }

    /**
     * 实现自动滚动视图到可见位置
     *
     * @param activity
     * @param lyRootID
     * @param vID
     * @param svID
     */
    public static void pullKeywordTop(final Activity activity, final int lyRootID, final int vID, final int svID) {
        ViewGroup ly = (ViewGroup) activity.findViewById(lyRootID);
        //获取屏幕高度，根据经验，输入法弹出高度一般在屏幕1/3到1/2之间
        final int defaultHeight = ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        final int mKeyHeight = defaultHeight / 4;
        ly.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right,
                                       int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //获取根布局前后高度差
                int height = oldBottom - bottom;
                NestedScrollView sv = (NestedScrollView) activity.findViewById(svID);
                if (height > mKeyHeight) {//当高度差大于屏幕1/4，认为是输入法弹出变动，可能会有特殊机型会失败
                    final int lybottom = bottom;
                    sv.post(new Runnable() {//用post防止有时输入法会自动滚动覆盖我们手动滚动
                        @Override
                        public void run() {
                            NestedScrollView runSv = (NestedScrollView) activity.findViewById(svID);
                            //获取要滚动至的控件到屏幕顶部高度
                            View v = (View) activity.findViewById(vID);
                            int[] loca = new int[2];
                            v.getLocationOnScreen(loca);
                            //这种通知栏高度获取方法必须在布局构建完毕后才能生效，否则获取为0
                            Rect frame = new Rect();
                            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                            int statusBarHeight = frame.top;
                            // 要滚动的距离=控件距屏幕顶部距离+控件高度-输入法弹出后的activity高度-通知栏高度
                            int scrollHeight = loca[1] + v.getHeight() - lybottom - statusBarHeight;
                            if (scrollHeight > 0) {
                                runSv.scrollBy(0, scrollHeight);
                            }

                        }
                    });
                } else if (-height > mKeyHeight) {//当输入法收起，回滚回顶部
                    sv.scrollTo(0, 0);
                }
            }
        });


    }

}
