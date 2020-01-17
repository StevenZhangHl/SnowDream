package com.steven.base.util.RecyclerView;

import android.graphics.Rect;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * @user steven
 * @createDate 2019/6/25 10:57
 * @description gird间距
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int column;

    public SpaceItemDecoration(int space, int column) {
        this.space = space;
        this.column = column;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.bottom = space;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int position = parent.getChildLayoutPosition(view);
        if (layoutManager instanceof LinearLayoutManager) {
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (position % column == 0) {
                outRect.left = 0;
            }
        }
    }
}
