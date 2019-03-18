package com.example.zealience.oneiromancy.ui;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;
import com.steven.base.app.GlideApp;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @user steven
 * @createDate 2019/3/18 17:56
 * @description 自定义
 */
public class DreamHotAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public DreamHotAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        CircleImageView ivUserImg = helper.getView(R.id.iv_user_img);
        TextView tvUserName = helper.getView(R.id.tv_user_name);
        ImageView ivHotPic = helper.getView(R.id.iv_hot_pic);
        TextView tvContent = helper.getView(R.id.tv_content);
        GlideApp.with(mContext)
                .load(R.mipmap.icon_user)
                .into(ivUserImg);
        tvContent.setText("我爱你雪儿");
        tvUserName.setText("Setven");
        ivHotPic.setImageResource(R.mipmap.bg_banner_three);
    }
}
