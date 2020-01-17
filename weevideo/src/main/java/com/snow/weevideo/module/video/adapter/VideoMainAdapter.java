package com.snow.weevideo.module.video.adapter;

import androidx.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.snow.weevideo.R;
import com.snow.weevideo.module.video.entity.VideoEntity;
import com.steven.base.app.GlideApp;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/6/24 18:05
 * @description 自定义
 */
public class VideoMainAdapter extends BaseQuickAdapter<VideoEntity, BaseViewHolder> {
    private int height;
    private int colors[] = new int[]{R.color.color_FBC02C,R.color.color_F2D49A,R.color.color_7A8A80,R.color.color_B4C39F,R.color.color_FF3B30,R.color.color_FB675A,R.color.color_F49E9A};

    public VideoMainAdapter(int layoutResId, @Nullable List<VideoEntity> data, int height) {
        super(layoutResId, data);
        this.height = height;
    }

    @Override
    protected void convert(BaseViewHolder holder, VideoEntity videoEntity) {
        RelativeLayout rl_item_video_root = holder.getView(R.id.rl_item_video_root);
        ImageView iv_video_preview = holder.getView(R.id.iv_video_preview);
        ImageView iv_video_user_head = holder.getView(R.id.iv_video_user_head);
        ViewGroup.LayoutParams layoutParams = rl_item_video_root.getLayoutParams();
        layoutParams.height = height;
        rl_item_video_root.setLayoutParams(layoutParams);
        TextView tv_praise = holder.getView(R.id.tv_praise);
        int praiseNum = videoEntity.getPraiseNum();
        if (praiseNum >= 10000) {
            praiseNum = praiseNum / 10000;
            tv_praise.setText(praiseNum + "万");
        } else {
            tv_praise.setText(praiseNum + "");
        }
        int bgColor = colors[(int) (Math.random()*(colors.length-1))];
        GlideApp.with(mContext)
                .load(videoEntity.getPreviewUrl())
                .apply(new RequestOptions().placeholder(bgColor))
                .into(iv_video_preview);
        GlideApp.with(mContext)
                .load(videoEntity.getUserHeadUrl())
                .placeholder(R.mipmap.icon_user)
                .into(iv_video_user_head);
    }
}
