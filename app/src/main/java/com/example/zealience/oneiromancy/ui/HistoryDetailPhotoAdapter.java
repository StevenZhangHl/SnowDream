package com.example.zealience.oneiromancy.ui;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.HistoryDetailEntity;
import com.steven.base.app.GlideApp;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/3/8 16:02
 * @description 自定义
 */
public class HistoryDetailPhotoAdapter extends BaseQuickAdapter<HistoryDetailEntity.PicUrlBean, BaseViewHolder> {
    public HistoryDetailPhotoAdapter(int layoutResId, @Nullable List<HistoryDetailEntity.PicUrlBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryDetailEntity.PicUrlBean item) {
        TextView tv_photo_des = helper.getView(R.id.tv_photo_des);
        ImageView ivHisitoryDetail = helper.getView(R.id.iv_hisitory_detail);
        tv_photo_des.setText(item.getPic_title());
        GlideApp.with(mContext)
                .load(item.getUrl())
                .into(ivHisitoryDetail);
    }
}
