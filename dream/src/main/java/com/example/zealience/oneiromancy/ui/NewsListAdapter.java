package com.example.zealience.oneiromancy.ui;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.NewsEntity;
import com.steven.base.app.GlideApp;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/3/7 14:26
 * @description 自定义
 */
public class NewsListAdapter extends BaseMultiItemQuickAdapter<NewsEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewsListAdapter(List<NewsEntity> data) {
        super(data);
        addItemType(3, R.layout.item_news_three_img);
        addItemType(1, R.layout.item_news_one_img);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsEntity item) {
        switch (helper.getItemViewType()) {
            case 1:
                ImageView iv_only_one = helper.getView(R.id.iv_only_one);
                GlideApp.with(mContext)
                        .load(item.getThumbnail_pic_s())
                        .into(iv_only_one);
                break;
            case 3:
                ImageView ivOne = helper.getView(R.id.iv_one);
                ImageView ivTwo = helper.getView(R.id.iv_two);
                ImageView ivThree = helper.getView(R.id.iv_three);
                GlideApp.with(mContext)
                        .load(item.getThumbnail_pic_s())
                        .into(ivOne);
                GlideApp.with(mContext)
                        .load(item.getThumbnail_pic_s02())
                        .into(ivTwo);
                GlideApp.with(mContext)
                        .load(item.getThumbnail_pic_s03())
                        .into(ivThree);
                break;
        }
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tvAuthor = helper.getView(R.id.tv_author);
        TextView tvDate = helper.getView(R.id.tv_date);
        tvAuthor.setText(item.getAuthor_name());
        tvDate.setText(item.getDate());
        tv_title.setText(item.getTitle());
    }
}
