package com.example.zealience.oneiromancy.ui;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.HomeRecommendEntity;
import com.example.zealience.oneiromancy.util.BlurTransformation;
import com.steven.base.app.GlideApp;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @user steven
 * @createDate 2019/3/18 17:56
 * @description 自定义
 */
public class DreamHotAdapter extends BaseMultiItemQuickAdapter<HomeRecommendEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DreamHotAdapter(List<HomeRecommendEntity> data) {
        super(data);
        addItemType(0, R.layout.item_horizontal_list);
        addItemType(1, R.layout.item_dream_hot);
        addItemType(2, R.layout.item_home_video);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeRecommendEntity item) {

        switch (helper.getItemViewType()) {
            case 0:
                RecyclerView recyclerView = helper.getView(R.id.recyclerview_horizontal);
                DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
                itemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.divider_horizontal_list));
                recyclerView.addItemDecoration(itemDecoration);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                HomeHorzontalListAdapter adapter = new HomeHorzontalListAdapter(R.layout.item_home_horzontal_list, item.getHorizontalEntity());
                adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                recyclerView.setAdapter(adapter);
                break;
            case 1:
                CircleImageView ivUserImg = helper.getView(R.id.iv_user_img);
                TextView tvUserName = helper.getView(R.id.tv_user_name);
                ImageView ivHotPic = helper.getView(R.id.iv_hot_pic);
                TextView tvContent = helper.getView(R.id.tv_content);
                GlideApp.with(mContext)
                        .load(R.mipmap.icon_user)
                        .into(ivUserImg);
                tvContent.setText(item.getPortraitEntity().getContent());
                tvUserName.setText(item.getPortraitEntity().getUserName());
                GlideApp.with(mContext)
                        .load(item.getPortraitEntity().getPhotoUrl())
                        .into(ivHotPic);
                break;
            case 2:
                JzvdStd jz_video = helper.getView(R.id.jz_video);
                jz_video.setUp(item.getVideoEntity().getVideoUrl(), item.getVideoEntity().getTitle(), Jzvd.SCREEN_WINDOW_NORMAL);
                GlideApp.with(mContext)
                        .load(item.getVideoEntity().getVideoThumb())
                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(mContext, 10)))
                        .into(jz_video.thumbImageView);
                break;
        }
    }
}
