package com.steven.base.util;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.steven.base.R;

/**
 * @user steven
 * @createDate 2019/3/28 15:42
 * @description 自定义
 */
public class AddRecyclerEmptyView {

    /**
     * 统一设置空数据View
     *
     * @param adapter      adapter
     * @param recyclerView 列表
     */
    public static void setEmptyView(BaseQuickAdapter adapter, RecyclerView recyclerView) {
        adapter.setNewData(null);
        adapter.setEmptyView(R.layout.include_data_empty, (ViewGroup) recyclerView.getParent());
    }

    /**
     * 统一设置空数据View
     *
     * @param adapter      adapter
     * @param recyclerView 列表
     * @param emptyViewId  空数据id
     */
    public static void setEmptyView(BaseQuickAdapter adapter, RecyclerView recyclerView, @LayoutRes int emptyViewId) {
        adapter.setNewData(null);
        adapter.setEmptyView(emptyViewId, (ViewGroup) recyclerView.getParent());
    }
}
