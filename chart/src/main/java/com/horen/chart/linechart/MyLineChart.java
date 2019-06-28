package com.horen.chart.linechart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.horen.chart.marker.DetailsMarkerView;
import com.horen.chart.marker.RoundMarker;

import java.lang.ref.WeakReference;

/**
 * Author:Steven
 * Time:2018/8/27 12:17
 * Description:This isMyLineChart
 */
public class MyLineChart extends LineChart {
    //弱引用覆盖物对象,防止内存泄漏
    private WeakReference<DetailsMarkerView> mDetailsReference;
    private WeakReference<RoundMarker> mRoundMarkerReference;

    /**
     * 所有覆盖物是否为空
     *
     * @return TRUE FALSE
     */
    public boolean isMarkerAllNull() {
        return mDetailsReference.get() == null && mRoundMarkerReference.get() == null;
    }

    public void setDetailsMarkerView(DetailsMarkerView detailsMarkerView) {
        mDetailsReference = new WeakReference<>(detailsMarkerView);
    }

    public void setRoundMarker(RoundMarker roundMarker) {
        mRoundMarkerReference = new WeakReference<>(roundMarker);
    }

    public MyLineChart(Context context) {
        super(context);
    }

    public MyLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * draws all MarkerViews on the highlighted positions
     */
    protected void drawMarkers(Canvas canvas) {

        // if there is no marker view or drawing marker is disabled
        DetailsMarkerView mDetailsMarkerView = mDetailsReference.get();
        RoundMarker mRoundMarker = mRoundMarkerReference.get();

        if (mDetailsMarkerView == null || mRoundMarker == null || !isDrawMarkersEnabled() || !valuesToHighlight())
            return;

        for (int i = 0; i < mIndicesToHighlight.length; i++) {

            Highlight highlight = mIndicesToHighlight[i];

            IDataSet set = mData.getDataSetByIndex(highlight.getDataSetIndex());

            Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);

            int entryIndex = set.getEntryIndex(e);

            // make sure entry not null
            if (e == null || entryIndex > set.getEntryCount() * mAnimator.getPhaseX())
                continue;

            float[] pos = getMarkerPosition(highlight);

            LineDataSet dataSetByIndex = (LineDataSet) getLineData().getDataSetByIndex(highlight.getDataSetIndex());

            // check bounds
            if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                continue;

            float circleRadius = dataSetByIndex.getCircleRadius();

            // callbacks to update the content
            mDetailsMarkerView.refreshContent(e, highlight);

            mDetailsMarkerView.draw(canvas, pos[0], pos[1]);

            mRoundMarker.refreshContent(e, highlight);
            mRoundMarker.draw(canvas, pos[0] - mRoundMarker.getWidth() / 2, pos[1] + circleRadius - mRoundMarker.getHeight());
        }
    }
}

