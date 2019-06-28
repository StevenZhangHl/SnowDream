package com.horen.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Author:Steven
 * Time:2018/9/13 17:53
 * Description:This isDefaultValueFormatterUtil
 */
public class DefaultValueFormatterUtil implements IAxisValueFormatter {

    /**
     * DecimalFormat for formatting
     */
    protected DecimalFormat mFormat;

    /**
     * Constructor that specifies to how many digits the value should be
     * formatted.
     */
    public DefaultValueFormatterUtil() {
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return (int)value+"";
    }
}
