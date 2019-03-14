package com.example.zealience.oneiromancy.ui;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/3/13 16:40
 * @description 自定义
 */
public class NearPoiAddressAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {
    private int currentPosition = -1;

    public NearPoiAddressAdapter(int layoutResId, @Nullable List<PoiItem> data) {
        super(layoutResId, data);
    }

    /**
     * 传递当前点击的位置
     *
     * @param position
     */
    public void clickPosition(int position) {
        this.currentPosition = position;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        TextView tvAddress = helper.getView(R.id.tv_address);
        ImageView ivSelectAddress = helper.getView(R.id.iv_select_address);
        tvAddress.setText(item.getSnippet());
        if (currentPosition == helper.getAdapterPosition()) {
            helper.getConvertView().setBackgroundColor(mContext.getResources().getColor(R.color.DCDCDC));
            ivSelectAddress.setVisibility(View.VISIBLE);
        } else {
            helper.getConvertView().setBackgroundColor(mContext.getResources().getColor(R.color.white));
            ivSelectAddress.setVisibility(View.INVISIBLE);
        }
    }
}
