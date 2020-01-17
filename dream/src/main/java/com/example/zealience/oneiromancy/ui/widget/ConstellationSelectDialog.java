package com.example.zealience.oneiromancy.ui.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.ConstellationEntity;
import com.flyco.dialog.widget.base.BottomBaseDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @user steven
 * @createDate 2019/4/1 11:02
 * @description 自定义
 */
public class ConstellationSelectDialog extends BottomBaseDialog<ConstellationSelectDialog> {
    private RecyclerView recyclerview_constellation;
    private List<ConstellationEntity> mData = new ArrayList<>();
    private MyOnItemClickListener myOnItemClickListener;

    public ConstellationSelectDialog(Context context, View animateView) {
        super(context, animateView);
    }

    @Override
    public View onCreateView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_constellation_select, null);
        recyclerview_constellation = (RecyclerView) view.findViewById(R.id.recyclerview_constellation);
        setData();
        return view;
    }

    private void setData() {
        ConstellationAdapter adapter = new ConstellationAdapter(R.layout.item_dream_type, getConstellationData());
        recyclerview_constellation.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerview_constellation.setAdapter(adapter);
        recyclerview_constellation.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                dismiss();
                myOnItemClickListener.getSelectType(mData.get(position).getTitle());
            }
        });
    }

    private List<ConstellationEntity> getConstellationData() {
        List<ConstellationEntity> constellationList = new ArrayList<>();
        ConstellationEntity entity1 = new ConstellationEntity("白羊座", R.mipmap.icon_aries_unselect);
        ConstellationEntity entity2 = new ConstellationEntity("金牛座", R.mipmap.icon_taurus_unselect);
        ConstellationEntity entity3 = new ConstellationEntity("双子座", R.mipmap.icon_gemini_unselect);
        ConstellationEntity entity4 = new ConstellationEntity("巨蟹座", R.mipmap.icon_cancer_unselect);
        ConstellationEntity entity5 = new ConstellationEntity("狮子座", R.mipmap.icon_leo_unselect);
        ConstellationEntity entity6 = new ConstellationEntity("处女座", R.mipmap.icon_virgo_unselect);
        ConstellationEntity entity7 = new ConstellationEntity("天秤座", R.mipmap.icon_libra_unselect);
        ConstellationEntity entity8 = new ConstellationEntity("天蝎座", R.mipmap.icon_scorpio_unselect);
        ConstellationEntity entity9 = new ConstellationEntity("射手座", R.mipmap.icon_sagittarius_unselect);
        ConstellationEntity entity10 = new ConstellationEntity("摩羯座", R.mipmap.icon_capricorn_unselect);
        ConstellationEntity entity11 = new ConstellationEntity("水瓶座", R.mipmap.icon_aquarius_unselect);
        ConstellationEntity entity12 = new ConstellationEntity("双鱼座", R.mipmap.icon_pisces_unselect);
        constellationList.add(entity1);
        constellationList.add(entity2);
        constellationList.add(entity3);
        constellationList.add(entity4);
        constellationList.add(entity5);
        constellationList.add(entity6);
        constellationList.add(entity7);
        constellationList.add(entity8);
        constellationList.add(entity9);
        constellationList.add(entity10);
        constellationList.add(entity11);
        constellationList.add(entity12);
        mData.addAll(constellationList);
        return constellationList;
    }

    @Override
    public void setUiBeforShow() {
        widthScale(1.0f);
    }

    private class ConstellationAdapter extends BaseQuickAdapter<ConstellationEntity, BaseViewHolder> {

        public ConstellationAdapter(int layoutResId, @Nullable List<ConstellationEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ConstellationEntity item) {
            helper.setText(R.id.tv_dream_type, item.getTitle());
            ImageView iv_type = helper.getView(R.id.iv_dream_type_ic);
            iv_type.setImageResource(item.getDrawableId());
        }
    }

    public interface MyOnItemClickListener {
        void getSelectType(String s);
    }

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }
}
