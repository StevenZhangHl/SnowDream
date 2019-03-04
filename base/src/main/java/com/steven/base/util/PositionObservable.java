package com.steven.base.util;

import java.util.Observable;

/**
 * @user steven
 * @createDate 2019/2/28 13:12
 * @description 自定义
 */
public class PositionObservable extends Observable {
    public static PositionObservable sInstance;

    public static PositionObservable getInstance() {
        if (sInstance == null) {
            sInstance = new PositionObservable();
        }
        return sInstance;
    }

    /**
     * 通知观察者FloatingDragger
     */
    public void update() {
        setChanged();
        notifyObservers();
    }
}
