package com.example.zhao.mycountryselect.adapter;

import android.content.Context;

import com.example.zhao.mycountryselect.R;
import com.example.zhao.mycountryselect.bean.CountryBean;
import com.example.zhao.mycountryselect.utils.CommonAdapter;
import com.example.zhao.mycountryselect.utils.ViewHolder;

import java.util.List;

/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class CountrySelectAdapter extends CommonAdapter<CountryBean> {
    public CountrySelectAdapter(Context context, int layoutId, List<CountryBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final CountryBean cityBean) {
        holder.setText(R.id.tvCity, cityBean.getCity());
    }
}