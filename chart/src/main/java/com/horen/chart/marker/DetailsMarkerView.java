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

import java.text.DecimalFormat;

/**
 * @author Lai
 * @time 2018/5/13 17:32
 * @describe describe
 */

public class DetailsMarkerView extends MarkerView {

    private TextView tvValue;
    private String mText;
    private Context context;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public DetailsMarkerView(Context context, String mText, int layoutResouce) {
        super(context, layoutResouce);
        tvValue = findViewById(R.id.tv_value);
        this.mText = mText;
        this.context = context;
    }

    private int y1 = -1, y2 = -1;

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (highlight.getDataSetIndex() == 0) {
            y1 = (int) e.getY();
            tvValue.setTextColor(context.getResources().getColor(R.color.base_text_color_light));
            tvValue.setText((int) e.getY() + "");
        }
        if (highlight.getDataSetIndex() == 1) {
            y2 = (int) e.getY();
            tvValue.setTextColor(context.getResources().getColor(R.color.line_chart_send_color));
            tvValue.setText((int) e.getY() + "");
        }
        if (TextUtils.isEmpty(mText)) {
            tvValue.setText((int) e.getY() + mText);
        }
    }


    @Override
    public MPPointF getOffset() {
        if (y2 == -1 || y1 == -1) {
            return new MPPointF(-(getWidth() / 2), -getHeight());
        } else {
            y1 = -1;
            y2 = -1;
            return new MPPointF(-(getWidth() / 2), getHeight() - 40);
        }
    }
}
