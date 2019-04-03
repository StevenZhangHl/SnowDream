package com.steven.base.util.RecyclerView;

/**
 * @user steven
 * @createDate 2019/4/3 10:08
 * @description ViewHolder 被选中 以及 拖拽释放 触发监听器
 */
public interface OnDragVHListener {
    /**
     * Item被选中时触发
     */
    void onItemSelected();


    /**
     * Item在拖拽结束/滑动结束后触发
     */
    void onItemFinish();
}
