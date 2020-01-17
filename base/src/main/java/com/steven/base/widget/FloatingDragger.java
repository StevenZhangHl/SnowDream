package com.steven.base.widget;

import android.content.Context;
import androidx.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.steven.base.R;
import com.steven.base.util.DisplayUtil;
import com.steven.base.util.PositionObservable;

import java.util.Observable;
import java.util.Observer;

/**
 * @user steven
 * @createDate 2019/2/28 13:17
 * @description 自定义
 */
public class FloatingDragger implements Observer {
    public static PositionObservable observable = PositionObservable.getInstance();
    private FloatingDraggedView floatingDraggedView;

    public FloatingDragger(Context context, @LayoutRes int layoutResID ) {
        // 用户布局
        View contentView = LayoutInflater.from(context).inflate(layoutResID, null);
        // 悬浮球按钮
        View floatingView = LayoutInflater.from(context).inflate(R.layout.layout_floating_dragged, null);

        // ViewDragHelper的ViewGroup容器
        floatingDraggedView = new FloatingDraggedView(context);
        floatingDraggedView.addView(contentView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        floatingDraggedView.addView(floatingView, new FrameLayout.LayoutParams(DisplayUtil.dip2px(context, 45), DisplayUtil.dip2px(context, 40)));

        // 添加观察者
        observable.addObserver(this);
    }

    public View getView() {
        return floatingDraggedView;
    }

    public static PositionObservable getObservable() {
        return observable;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (floatingDraggedView != null) {
            // 更新位置
            floatingDraggedView.restorePosition();
        }
    }

    public void deleteObsever() {
        observable.deleteObserver(this);
    }
}
