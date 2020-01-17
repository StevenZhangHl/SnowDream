package com.example.zealience.oneiromancy.ui;

import androidx.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.UserDynamicEntity;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/3/28 13:37
 * @description 自定义
 */
public class UserDynamicAdapter extends BaseQuickAdapter<UserDynamicEntity, BaseViewHolder> {
    public UserDynamicAdapter(int layoutResId, @Nullable List<UserDynamicEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserDynamicEntity item) {
        TextView tvDynamicDate = helper.getView(R.id.tv_dynamic_date);
        TextView tvDynamicContent = helper.getView(R.id.tv_dynamic_content);
        tvDynamicDate.setText(item.getDate());
        tvDynamicContent.setText(item.getContent());
    }
}
