package com.example.zealience.oneiromancy.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.HomeNormalEntity;
import com.example.zealience.oneiromancy.entity.HomeRecommendEntity;
import com.example.zealience.oneiromancy.util.BlurTransformation;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.steven.base.ShowBigImageActivity;
import com.steven.base.app.GlideApp;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @user steven
 * @createDate 2019/3/18 17:56
 * @description 自定义
 */
public class DreamHotAdapter extends BaseMultiItemQuickAdapter<HomeRecommendEntity, BaseViewHolder> implements IShareElements {
    private AppCompatActivity activity;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DreamHotAdapter(AppCompatActivity activity, List<HomeRecommendEntity> data) {
        super(data);
        this.activity = activity;
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
                List<String> photos = new ArrayList<>();
                for (HomeNormalEntity photo : item.getHorizontalEntity()) {
                    photos.add(photo.getPhotoUrl());
                }
                recyclerView.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                        ShowBigImageActivity.startImagePagerActivity(mContext, photos, position);
                    }
                });
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

    @Override
    public ShareElementInfo[] getShareElements() {
        return new ShareElementInfo[0];
    }
}
