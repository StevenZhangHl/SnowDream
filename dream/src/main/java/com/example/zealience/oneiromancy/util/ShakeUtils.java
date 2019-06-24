package com.example.zealience.oneiromancy.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.VibrationEffect;
import android.os.Vibrator;

/**
 * @user steven
 * @createDate 2019/3/6 15:16
 * @description 自定义
 */
public class ShakeUtils implements SensorEventListener {
    private SensorManager mSensorManager = null;
    private Vibrator vibrator;
    private OnShakeListener mOnShakeListener = null;
    private static final int SENSOR_VALUE = 20;

    public ShakeUtils(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            //这里可以调节摇一摇的灵敏度
            if ((Math.abs(values[0]) > SENSOR_VALUE || Math.abs(values[1]) > SENSOR_VALUE || Math.abs(values[2]) > SENSOR_VALUE)) {
                System.out.println("sensor value == " + " " + values[0] + " " + values[1] + " " + values[2]);
                if (!isFastDoubleClick()) {
                    if (null != mOnShakeListener) {
                        if (vibrator.hasVibrator()) {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                VibrationEffect vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
                                vibrator.vibrate(vibrationEffect);
                            } else {
                                vibrator.vibrate(500);
                            }
                        }
                        mOnShakeListener.onShake();
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onResume() {
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        mSensorManager.unregisterListener(this);
    }

    public interface OnShakeListener {
        void onShake();
    }

    public void setmOnShakeListener(OnShakeListener mOnShakeListener) {
        this.mOnShakeListener = mOnShakeListener;
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 2000) {
            lastClickTime = time;
            return true;
        } else {
            return false;
        }
    }
}
