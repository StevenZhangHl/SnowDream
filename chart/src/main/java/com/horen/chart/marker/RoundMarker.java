package com.horen.chart.marker;

import android.content.Context;
import android.widget.ImageView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.horen.chart.R;

/**
 * @author Lai
 * @time 2018/5/26 14:31
 * @describe describe
 */

public class RoundMarker extends MarkerView {
    private ImageView iv_point;
    private Context context;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public RoundMarker(Context context, int layoutResourse) {
        super(context, layoutResourse);
        this.context = context;
        iv_point = (ImageView) findViewById(R.id.iv_point);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (highlight.getDataSetIndex() == 0) {
            iv_point.setImageResource(R.drawable.bg_first_line_round);
        }
        if (highlight.getDataSetIndex() == 1) {
            iv_point.setImageResource(R.drawable.bg_sencond_line_round);
        }
    }
}
