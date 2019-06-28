package com.horen.chart;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.horen.chart.linechart.ILineChartData;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/20 15:49
 * Description:This isLineValueMarker
 */
public class LineValueMarker extends MarkerView {
    private TextView tvValue;
    private String mText;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public LineValueMarker(Context context, String mText) {
        super(context, R.layout.chart_marker_view);
        tvValue = findViewById(R.id.tv_value);
        this.mText = mText;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvValue.setText((int) e.getY() + mText);
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
