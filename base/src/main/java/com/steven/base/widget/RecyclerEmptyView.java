package com.steven.base.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.steven.base.R;
import com.steven.base.app.BaseApp;

/**
 * @user steven
 * @createDate 2019/3/28 15:42
 * @description 处理列表数据为空时的显示
 */
public class RecyclerEmptyView {
    private static View view;
    private static TextView tv_title;
    private static ImageView iv_empty;
    private static BaseQuickAdapter quickAdapter;
    private static RecyclerView mRecyclerView;

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

    /**
     * 自定义空数据提示的文字
     *
     * @param adapter
     * @param content
     */
    public static void setEmptyView(BaseQuickAdapter adapter, String content) {
        view = LayoutInflater.from(BaseApp.getInstance()).inflate(R.layout.include_data_empty, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_empty_type);
        textView.setText(content);
        adapter.setEmptyView(view);
    }

    /**
     * 自定义空数据提示的图片
     *
     * @param adapter
     * @param imageId
     */
    public static void setEmptyView(BaseQuickAdapter adapter, int imageId) {
        view = LayoutInflater.from(BaseApp.getInstance()).inflate(R.layout.include_data_empty, null);
        ImageView iv_data_empty = (ImageView) view.findViewById(R.id.iv_data_empty);
        iv_data_empty.setImageResource(imageId);
        adapter.setEmptyView(view);
    }

    /**
     * 完全自定义空数据提示
     *
     * @param adapter
     * @param content
     * @param imageId
     */
    public static void setEmptyView(BaseQuickAdapter adapter, String content, int imageId) {
        view = LayoutInflater.from(BaseApp.getInstance()).inflate(R.layout.include_data_empty, null);
        ImageView iv_data_empty = (ImageView) view.findViewById(R.id.iv_data_empty);
        TextView textView = (TextView) view.findViewById(R.id.tv_empty_type);
        textView.setText(content);
        iv_data_empty.setImageResource(imageId);
        adapter.setEmptyView(view);
    }

    public static View getView() {
        return view;
    }

    /**
     * 自定义布局的时候调用这个
     *
     * @param layoutId
     */
    public static void createView(int layoutId) {
        view = LayoutInflater.from(BaseApp.getInstance()).inflate(layoutId, null);
    }

    /**
     * 喜欢链式调用的用这个
     */
    public static class Builder {
        public Builder with(Context context) {
            view = LayoutInflater.from(context).inflate(R.layout.include_data_empty, null);
            iv_empty = (ImageView) view.findViewById(R.id.iv_data_empty);
            tv_title = (TextView) view.findViewById(R.id.tv_empty_type);
            return this;
        }

        public Builder adapter(BaseQuickAdapter adapter) {
            quickAdapter = adapter;
            return this;
        }

        public Builder setTitle(String title) {
            tv_title.setText(title);
            return this;
        }

        public Builder setIv_empty(int drawableId) {
            iv_empty.setImageResource(drawableId);
            return this;
        }

        public Builder setListener(View.OnClickListener onClickListener) {
            view.setOnClickListener(onClickListener);
            return this;
        }

        public void create() {
            quickAdapter.setEmptyView(view);
        }
    }
}
