package com.example.zealience.oneiromancy.ui;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/2/22 09:32
 * @description 自定义
 */
public class ShowShiAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ShowShiAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_shi, item);
    }
}
