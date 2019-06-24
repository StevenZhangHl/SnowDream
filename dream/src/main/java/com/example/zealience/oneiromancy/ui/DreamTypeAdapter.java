package com.example.zealience.oneiromancy.ui;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.DreamTypeEntity;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/2/20 16:56
 * @description 自定义
 */
public class DreamTypeAdapter extends BaseQuickAdapter<DreamTypeEntity, BaseViewHolder> {
    private Integer[] icons = {R.mipmap.icon_figure, R.mipmap.icon_animal, R.mipmap.icon_recreation, R.mipmap.icon_religion, R.mipmap.icon_landscape, R.mipmap.icon_build, R.mipmap.icon_terror, R.mipmap.icon_emotion, R.mipmap.icon_plant, R.mipmap.icon_more};

    public DreamTypeAdapter(int layoutResId, @Nullable List<DreamTypeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    protected void convert(BaseViewHolder helper, DreamTypeEntity item) {
        TextView tv_dream_type = helper.getView(R.id.tv_dream_type);
        ImageView iv_dream_type_ic = helper.getView(R.id.iv_dream_type_ic);
        int position = helper.getAdapterPosition();
        iv_dream_type_ic.setImageResource(icons[position]);
        if (helper.getAdapterPosition() == 9) {
            tv_dream_type.setText("更多");
        } else {
            tv_dream_type.setText(item.getName());
        }
    }
}
