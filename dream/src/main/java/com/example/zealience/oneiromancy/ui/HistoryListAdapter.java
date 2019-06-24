package com.example.zealience.oneiromancy.ui;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.HistoryEntity;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/3/8 13:28
 * @description 自定义
 */
public class HistoryListAdapter extends BaseQuickAdapter<HistoryEntity, BaseViewHolder> {
    public HistoryListAdapter(int layoutResId, @Nullable List<HistoryEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryEntity item) {
        TextView tvHistoryDate = helper.getView(R.id.tv_history_date);
        TextView tvHistoryEvent = helper.getView(R.id.tv_history_event);
        tvHistoryDate.setText(item.getDate());
        tvHistoryEvent.setText(item.getTitle());
    }
}
