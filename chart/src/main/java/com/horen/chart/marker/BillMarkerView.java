package com.horen.chart.marker;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.horen.base.util.MatcherUtils;
import com.horen.chart.R;

/**
 * Author:Steven
 * Time:2018/9/12 9:34
 * Description:This isBillMarkerView
 */
public class BillMarkerView extends MarkerView {
    private TextView tvValue;
    private Context context;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public BillMarkerView(Context context, int layoutResouce) {
        super(context, layoutResouce);
        tvValue = findViewById(R.id.tv_value);
        this.context = context;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvValue.setText(MatcherUtils.formatNumber(e.getY()));
    }


    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
