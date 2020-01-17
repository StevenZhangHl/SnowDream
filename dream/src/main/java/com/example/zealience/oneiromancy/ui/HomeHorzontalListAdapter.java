package com.example.zealience.oneiromancy.ui;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.HomeNormalEntity;
import com.steven.base.app.GlideApp;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/3/20 16:35
 * @description 自定义
 */
public class HomeHorzontalListAdapter extends BaseQuickAdapter<HomeNormalEntity, BaseViewHolder> {
    public HomeHorzontalListAdapter(int layoutResId, @Nullable List<HomeNormalEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeNormalEntity item) {
        ImageView ivHotPic = helper.getView(R.id.iv_horzontal);
        ViewCompat.setTransitionName(ivHotPic, item.getPhotoUrl());
        TextView tvContent = helper.getView(R.id.tv_content);
        tvContent.setText(item.getContent());
        GlideApp.with(mContext)
                .load(item.getPhotoUrl())
                .into(ivHotPic);
    }

}