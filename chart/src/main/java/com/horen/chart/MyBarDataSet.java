package com.horen.chart;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.horen.base.app.BaseApp;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/15 10:52
 * Description:This isMyBarDataSet
 */
public class MyBarDataSet extends BarDataSet {
    private int mColors[];

    public void setBarColors(int[] colors) {
        mColors = new int[colors.length];
        for (int i = 0; i < colors.length; i++) {
            mColors[i] = colors[i];
        }
    }

    public MyBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index) {
        Log.i("getY:", getEntryForIndex(index).getY() + "");
        if (getEntryForIndex(index).getY() <= 15) {
            return mColors[1];
        } else {
            return mColors[0];
        }
    }
}
