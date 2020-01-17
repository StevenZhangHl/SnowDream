package com.example.zealience.oneiromancy.ui;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.VRPhotoEntity;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/2/27 13:32
 * @description 自定义
 */
public class VRPhotoAdapter extends BaseQuickAdapter<VRPhotoEntity, BaseViewHolder> {
    public VRPhotoAdapter(int layoutResId, @Nullable List<VRPhotoEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VRPhotoEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_desc, item.getDesc());
        helper.setImageResource(R.id.imageView, item.getResourceName());
    }
}
