package com.example.zealience.oneiromancy.ui;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.DreamTypeEntity;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/2/20 16:56
 * @description 自定义
 */
public class DreamTypeAdapter extends BaseQuickAdapter<DreamTypeEntity, BaseViewHolder> {
    public DreamTypeAdapter(int layoutResId, @Nullable List<DreamTypeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DreamTypeEntity item) {
        TextView tv_dream_type = helper.getView(R.id.tv_dream_type);
        tv_dream_type.setText(item.getName());
    }
}
