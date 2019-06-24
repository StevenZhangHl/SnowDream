package com.example.zealience.oneiromancy.ui;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/2/25 09:39
 * @description 自定义
 */
public class DreamDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public DreamDetailAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_dream_detail, item);
    }
}
