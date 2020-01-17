package com.example.zealience.oneiromancy.ui;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.DreamEntity;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/2/21 10:54
 * @description 自定义
 */
public class SearchDreamAdapter extends BaseQuickAdapter<DreamEntity, BaseViewHolder> {
    public SearchDreamAdapter(int layoutResId, @Nullable List<DreamEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DreamEntity item) {
        helper.setText(R.id.tv_dream_title, item.getTitle());
        helper.setText(R.id.tv_dream_des, item.getDes());
    }
}
