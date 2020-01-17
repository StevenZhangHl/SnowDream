package com.snow.weevideo.module.samecity.adapter;

import androidx.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.snow.weevideo.R;
import com.snow.weevideo.module.samecity.entity.SameCityEntity;
import com.steven.base.app.GlideApp;
import com.steven.base.util.NumberUtil;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/6/25 13:51
 * @description 自定义
 */
public class SameCityAdapter extends BaseQuickAdapter<SameCityEntity, BaseViewHolder> {
    private int height;
    private int colors[] = new int[]{R.color.color_FBC02C, R.color.color_F2D49A, R.color.color_7A8A80, R.color.color_B4C39F, R.color.color_FF3B30, R.color.color_FB675A, R.color.color_F49E9A};

    public SameCityAdapter(int layoutResId, @Nullable List<SameCityEntity> data, int height) {
        super(layoutResId, data);
        this.height = height;
    }

    @Override
    protected void convert(BaseViewHolder holder, SameCityEntity sameCityEntity) {
        RelativeLayout rl_item_video_root = holder.getView(R.id.rl_item_video_root);
        ImageView iv_video_preview = holder.getView(R.id.iv_video_preview);
        ImageView iv_video_user_head = holder.getView(R.id.iv_video_user_head);
        TextView tv_first_title = holder.getView(R.id.tv_first_title);
        TextView tv_second_title = holder.getView(R.id.tv_second_title);
        ViewGroup.LayoutParams layoutParams = rl_item_video_root.getLayoutParams();
        layoutParams.height =  height / 2 + (int) (Math.random() * (height / 2));
        rl_item_video_root.setLayoutParams(layoutParams);
        int location[] = new int[2];
        rl_item_video_root.getLocationOnScreen(location);
        Log.i("itemX:",location[0]+"");
        TextView tv_distance = holder.getView(R.id.tv_distance);
        double distance = sameCityEntity.getDistance();
        if (distance >= 1000) {
            distance = NumberUtil.formitSaveOnePoint(distance / 1000);
            tv_distance.setText(distance + "公里");
        } else {
            tv_distance.setText((int) distance + "米");
        }
        tv_first_title.setText(sameCityEntity.getFirstTitle());
        tv_second_title.setText(sameCityEntity.getSencondTitle());
        int bgColor = colors[(int) (Math.random() * (colors.length - 1))];
        GlideApp.with(mContext)
                .load(sameCityEntity.getPreviewUrl())
                .apply(new RequestOptions().placeholder(bgColor))
                .into(iv_video_preview);
        GlideApp.with(mContext)
                .load(sameCityEntity.getUserHeadUrl())
                .placeholder(R.mipmap.icon_user)
                .into(iv_video_user_head);
    }

}
