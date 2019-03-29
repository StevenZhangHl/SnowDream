package com.steven.base.util;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @user steven
 * @createDate 2019/3/29 10:01
 * @description 倒计时
 */
public class TimeCountUtil extends CountDownTimer {
    private TextView mButton;
    private OnTimeOutListener listener;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimeCountUtil(TextView button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mButton = button;
    }

    public void setOnTimeOutListener(OnTimeOutListener listener) {
        this.listener = listener;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // 按钮不可用
        mButton.setEnabled(false);
        String showText = millisUntilFinished / 1000 + "秒后可重新发送";
        mButton.setText(showText);
    }

    @Override
    public void onFinish() {
        // 按钮设置可用
        mButton.setEnabled(true);
        mButton.setText("获取验证码");
        if (listener != null) {
            listener.timeOut();
        }
    }

    public interface OnTimeOutListener {
        void timeOut();
    }
}
