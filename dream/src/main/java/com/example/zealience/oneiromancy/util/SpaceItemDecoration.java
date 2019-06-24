package com.example.zealience.oneiromancy.util;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @user steven
 * @createDate 2019/2/21 09:35
 * @description 表格布局间隔工具
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int column = 0;

    public SpaceItemDecoration(int space,int column) {
        this.space = space;
        this.column = column;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = space;
        outRect.bottom = space;
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) %column==0) {
            outRect.left = 0;
        }
    }
}
