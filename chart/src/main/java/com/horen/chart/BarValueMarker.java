package com.horen.chart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.horen.chart.barchart.IBarData;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/13/15:52
 * @description :自定义柱状图MarkerView
 * @github :https://github.com/chenyy0708
 */
public class BarValueMarker extends MarkerView {

    private TextView tvValue;
    private String mText;
    private List<IBarData> mData;

    public BarValueMarker(Context context, String mText, List<IBarData> mData) {
        super(context, R.layout.chart_marker_view);
        tvValue = findViewById(R.id.tv_value);
        this.mText = mText;
        this.mData = mData;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvValue.setText((int) mData.get((int) e.getX()).getValue() + mText);
        super.refreshContent(e, highlight);
    }

    /**
     * Marker对齐位置
     */
    @Override
    public MPPointF getOffset() {
        MPPointF offset = super.getOffset();
        offset.x = -(getWidth() / 2);
        offset.y = -getHeight();
        return offset;
    }
}
