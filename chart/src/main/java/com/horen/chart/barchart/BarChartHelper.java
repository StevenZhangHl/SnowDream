package com.horen.chart.barchart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.horen.chart.DefaultValueFormatterUtil;
import com.horen.chart.MyBarDataSet;
import com.horen.chart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/02/11:09
 * @description :柱状图工具类
 * @github :https://github.com/chenyy0708
 */
public class BarChartHelper {

    private Context mContext;

    private BarChart mBarChart;
    /**
     * 单个柱状图数据
     */
    private List<IBarData> barData;
    /**
     * 多个柱状图数据
     */
    private List<List<IBarData>> barSetData;
    /**
     * 标签Label名字
     */
    private List<String> mLabels;
    /**
     * 背景颜色
     */
    private int backgroundColor;
    /**
     * 右边Y轴显示
     */
    private boolean yAxisRightEnable;
    /**
     * 左边Y轴显示
     */
    private boolean yAxisRLeftEnable;
    /**
     * 缩放
     */
    private boolean mScaleEnabled;
    /**
     * 是否可以通过双击屏幕放大图表
     */
    private boolean mDoubleTapToZoomEnabled;
    /**
     * 描述
     */
    private boolean mDescriptionEnable;
    /**
     * 按比例放缩柱状图
     */
    private boolean pinchZoom;
    /**
     * 标签
     */
    private boolean mLegendEnable;
    /**
     * X,y轴显示网格线
     */
    private boolean mDrawGridLines;
    /**
     * X轴显示自定义数据
     */
    private boolean xValueEnable;
    /**
     * 标签文字大小
     */
    private float legendTextSize;
    /**
     * 标签文字大小
     */
    private float valueTextSize;
    /**
     * 每组柱之间的宽度
     */
    private float groupSpace;
    /**
     * 单个柱的宽度
     */
    private float barWidth;
    /**
     * 一页柱状图显示个数
     */
    private int displayCount;
    /**
     * 动画时间
     */
    private int durationMillis;
    /**
     * 动画类型
     */
    private Easing.EasingOption mEasing;
    /**
     * 颜色集合
     */
    private List<Integer> mBarColors;
    /**
     * 单个柱状图的颜色
     */
    private int barColor;
    /**
     * 无数据时显示的文本
     */
    private String noDataText;
    /**
     * 无数据时文本的颜色
     */
    private int noDataTextColor;
    private YAxis yAxis;
    private XAxis xAxis;
    /**
     * 是否让柱状图的颜色根据条件改变
     */
    private boolean hasBarDifferentColor;

    /**
     * 固定柱状图多种颜色柱状图，提前预知，
     */
    private boolean multipleDifferentColor;

    /**
     * 渐变色
     */
    private boolean isGradualColor = false;
    /**
     * 开始渐变色
     */
    private int startGradualColor;
    /**
     * 结束渐变色
     */
    private int endGradualColor;

    private int[] barColors;

    private boolean isInit;

    private BarChartHelper(Builder builder) {
        this.mBarChart = builder.barChart;
        this.mContext = builder.mContext;
        this.barData = builder.barData;
        this.barSetData = builder.barSetData;
        this.mLabels = builder.mLabels;
        this.backgroundColor = builder.backgroundColor;
        this.yAxisRightEnable = builder.yAxisRightEnable;
        this.yAxisRLeftEnable = builder.yAxisRLeftEnable;
        this.mScaleEnabled = builder.mScaleEnabled;
        this.mDoubleTapToZoomEnabled = builder.mDoubleTapToZoomEnabled;
        this.mDescriptionEnable = builder.mDescriptionEnable;
        this.pinchZoom = builder.pinchZoom;
        this.mLegendEnable = builder.mLegendEnable;
        this.mDrawGridLines = builder.mDrawGridLines;
        this.legendTextSize = builder.legendTextSize;
        this.valueTextSize = builder.valueTextSize;
        this.groupSpace = builder.groupSpace;
        this.barWidth = builder.barWidth;
        this.displayCount = builder.displayCount;
        this.durationMillis = builder.durationMillis;
        this.mEasing = builder.mEasing;
        this.mBarColors = builder.mBarColors;
        this.xValueEnable = builder.xValueEnable;
        this.barColor = builder.barColor;
        this.noDataText = builder.noDataText;
        this.noDataTextColor = builder.noDataTextColor;
        this.hasBarDifferentColor = builder.hasBarDifferentColor;
        this.multipleDifferentColor = builder.multipleDifferentColor;
        this.barColors = builder.barColors;
        this.isGradualColor = builder.isGradualColor;
        this.startGradualColor = builder.startGradualColor;
        this.endGradualColor = builder.endGradualColor;
        initLineChart();
    }

    /**
     * 初始化LineChart
     */
    private void initLineChart() {
        //折线图例 标签 设置
        Legend legend = mBarChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(legendTextSize);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        //背景颜色
        mBarChart.setBackgroundColor(backgroundColor);
        // 右边Y轴
        mBarChart.getAxisRight().setEnabled(yAxisRightEnable);
        mBarChart.setScaleEnabled(mScaleEnabled);
        //设置是否可以通过双击屏幕放大图表。默认是true
        mBarChart.setDoubleTapToZoomEnabled(mDoubleTapToZoomEnabled);
        //设置描述
        mBarChart.getDescription().setEnabled(mDescriptionEnable);
        //设置按比例放缩柱状图
        mBarChart.setPinchZoom(pinchZoom);
        mBarChart.setNoDataText(noDataText);
        mBarChart.setNoDataTextColor(noDataTextColor);
        mBarChart.setExtraTopOffset(20);
        //x坐标轴设置
        xAxis = mBarChart.getXAxis();
        yAxis = mBarChart.getAxisLeft();
        // x轴对齐位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        // 隐藏网格线
        xAxis.setDrawGridLines(mDrawGridLines);
        yAxis.setDrawGridLines(mDrawGridLines);
        yAxis.setDrawAxisLine(false);
        yAxis.setValueFormatter(new DefaultValueFormatterUtil());
        //保证Y轴从0开始，不然会上移一点
        yAxis.setAxisMinimum(0f);
        mBarChart.setScaleYEnabled(false);
        mBarChart.setDragYEnabled(false);
        // 单柱状图
        if (barData != null) {
            setData(barData);
        }
        // 多柱状图
        if (barSetData != null) {
            setMultipleData(barSetData);
        }
    }

    /**
     * 展示柱状图(多条)
     *
     * @param barSetData 多个柱状图数据
     */
    private void setMultipleData(List<List<IBarData>> barSetData) {
        // X轴真实显示label
        List<String> xValue = new ArrayList<>();
        // 多柱状图数据集
        BarData data = new BarData();
        // 多柱状图循环，得到每一种柱状图集合数据
        for (int i = 0; i < barSetData.size(); i++) {
            List<IBarData> barSetDatum = barSetData.get(i);
            // 单种柱状图数据集
            ArrayList<BarEntry> entries = new ArrayList<>();
            for (int j = 0; j < barSetDatum.size(); j++) {
                entries.add(new BarEntry(j, barSetDatum.get(j).getValue()));
            }
            // 数据集合标签名
            BarDataSet barDataSet = new BarDataSet(entries, mLabels.get(i));
            barDataSet.setColor(mBarColors.get(i));
            barDataSet.setValueTextColor(mBarColors.get(i));
            barDataSet.setValueTextSize(valueTextSize);
            data.addDataSet(barDataSet);
        }
        // 初始化X轴值
        if (barSetData.size() > 0) {
            for (IBarData barData : barSetData.get(0)) {
                xValue.add(barData.getLabelName());
            }
        }
        //折线图例 标签 设置
        mBarChart.getLegend().setEnabled(mLegendEnable);
        //设置标签居中
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount(xValue.size());
        // 是否使用自定义X轴数据
        if (xValueEnable) {
            xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        }
        mBarChart.setData(data);
        // 每个组之间的宽度
        float groupSpace = this.groupSpace;
        // 每组柱之间的宽度
        float barSpace = (1 - groupSpace) / barSetData.size() / 10;
        // 柱宽度
        float barWidth = (1 - groupSpace) / barSetData.size() / 10 * 9;
        data.setBarWidth(barWidth);
        mBarChart.getXAxis().setAxisMinimum(0);
        mBarChart.getXAxis().setAxisMaximum(mBarChart.getBarData().getGroupWidth(groupSpace, barSpace) * xValue.size() + 0);
        data.groupBars(0, groupSpace, barSpace);
        // 自定义一页X轴显示多少数据
        if (displayCount != 0) {
            //设置动画效果
            Matrix m = new Matrix();
            //两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的2倍
            if (xValue.size() > displayCount) {
                m.postScale(xValue.size() / displayCount, 1f);
            }
            //将图表动画显示之前进行缩放
            mBarChart.getViewPortHandler().refresh(m, mBarChart, false);
        }
        mBarChart.animateY(durationMillis, mEasing);
        mBarChart.animateX(durationMillis, mEasing);
    }

    /**
     * 展示柱状图(一条)
     *
     * @param barChartData 单个柱状图数据
     */
    private void setData(List<IBarData> barChartData) {
        // X轴真实显示label
        List<String> xValue = new ArrayList<>();
        // Y轴图标数据
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < barChartData.size(); i++) {
            xValue.add(barChartData.get(i).getLabelName());
            entries.add(new BarEntry((float) i, barChartData.get(i).getValue()));
        }
        //折线图例 标签 设置
        mBarChart.getLegend().setEnabled(mLegendEnable);
        // 是否使用自定义X轴数据
        if (xValueEnable) {
            xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        }
        // 每一个BarDataSet代表一类柱状图

        if (hasBarDifferentColor) {
            MyBarDataSet dataSet = new MyBarDataSet(entries, "");
            dataSet.setBarColors(barColors);
            dataSet.setColors(barColors);
            dataSet.setHighLightAlpha(0);
            BarData data = new BarData(dataSet);
            mBarChart.setData(data);
            float barWidth = this.barWidth;
            data.setBarWidth(barWidth);
        } else if (multipleDifferentColor) {
            MultipleBarDataSet dataSet = new MultipleBarDataSet(entries, "");
            dataSet.setBarColors(barColors);
            dataSet.setColors(barColors);
            // 柱状图上面的值颜色
            dataSet.setValueTextColor(Color.parseColor("#333333"));
            dataSet.setValueTextSize(13);
            dataSet.setHighlightEnabled(false);
            BarData data = new BarData(dataSet);
            mBarChart.setData(data);
            float barWidth = this.barWidth;
            data.setBarWidth(barWidth);
        } else if (isGradualColor) {
            // 渐变色
            BarDataSet barDataSet = new BarDataSet(entries, "");
//            barDataSet.setGradientColor(endGradualColor, startGradualColor);
            barDataSet.setColor(endGradualColor/*, startGradualColor*/);
            barDataSet.setHighLightAlpha(0);
            // 柱状图上面的值颜色
            barDataSet.setValueTextColor(Color.parseColor("#333333"));
            barDataSet.setValueTextSize(valueTextSize);
            BarData data = new BarData(barDataSet);
            mBarChart.setData(data);
            float barWidth = this.barWidth;
            data.setBarWidth(barWidth);
        } else {
            BarDataSet barDataSet = new BarDataSet(entries, "");
            barDataSet.setColor(barColor);
            barDataSet.setHighLightAlpha(0);
            // 柱状图上面的值颜色
            barDataSet.setValueTextColor(Color.parseColor("#333333"));
            barDataSet.setValueTextSize(valueTextSize);
            BarData data = new BarData(barDataSet);
            mBarChart.setData(data);
            float barWidth = this.barWidth;
            data.setBarWidth(barWidth);
        }
        yAxis.setLabelCount(4, true);
        yAxis.setDrawLimitLinesBehindData(false);
        // 自定义一页X轴显示多少数据
        if (displayCount != 0 && !isInit) {
            Matrix m = new Matrix();
            //两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的2倍
            if (xValue.size() > displayCount) {
                m.postScale((float) xValue.size() / (float) displayCount, 1f);
            }
            //将图表动画显示之前进行缩放
            mBarChart.getViewPortHandler().refresh(m, mBarChart, false);
            isInit = true;
        }
        mBarChart.animateY(durationMillis, mEasing);
        mBarChart.animateX(durationMillis, mEasing);
    }

    /**
     * 刷新数据
     *
     * @param barData 新数据
     */
    public void setNewData(List<IBarData> barData) {
        this.barData = barData;
        mBarChart.animateY(durationMillis, mEasing);
        mBarChart.animateX(durationMillis, mEasing);
        setData(barData);
    }

    public static class Builder {

        private Context mContext;

        private BarChart barChart;
        /**
         * 单个柱状图数据
         */
        private List<IBarData> barData;
        /**
         * 多个柱状图数据
         */
        private List<List<IBarData>> barSetData;
        /**
         * 标签Label名字
         */
        private List<String> mLabels;
        /**
         * 背景颜色
         */
        private int backgroundColor = Color.WHITE;
        /**
         * 右边Y轴显示
         */
        private boolean yAxisRightEnable = false;
        /**
         * 左边Y轴显示
         */
        private boolean yAxisRLeftEnable = false;
        /**
         * 缩放
         */
        private boolean mScaleEnabled = false;
        /**
         * 是否可以通过双击屏幕放大图表
         */
        private boolean mDoubleTapToZoomEnabled = false;
        /**
         * 描述
         */
        private boolean mDescriptionEnable = false;
        /**
         * 按比例放缩柱状图
         */
        private boolean pinchZoom = true;
        /**
         * 标签
         */
        private boolean mLegendEnable = false;
        /**
         * X,y轴显示网格线
         */
        private boolean mDrawGridLines = false;
        /**
         * 标签文字大小
         */
        private float legendTextSize = 12f;
        /**
         * 标签文字大小
         */
        private float valueTextSize = 10f;
        /**
         * 每组柱之间的宽度
         */
        private float groupSpace = 0.3f;
        /**
         * 单个柱的宽度
         */
        private float barWidth = 0.3f;
        /**
         * 一页柱状图显示个数
         */
        private int displayCount;
        /**
         * 动画时间
         */
        private int durationMillis = 500;
        /**
         * 动画类型
         */
        private Easing.EasingOption mEasing = Easing.EasingOption.Linear;
        /**
         * 颜色集合
         */
        private List<Integer> mBarColors;
        /**
         * 单个柱状图的颜色
         */
        private int barColor;
        /**
         * X轴显示自定义数据
         */
        private boolean xValueEnable = true;
        /**
         * 无数据时显示的文本
         */
        private String noDataText;
        /**
         * 无数据时文本的颜色
         */
        private int noDataTextColor;
        /**
         * 是否让柱状图的颜色根据条件改变
         */
        private boolean hasBarDifferentColor;
        /**
         * 是否让柱状图的颜色根据条件改变
         */
        private boolean multipleDifferentColor;
        /**
         * 设置条件改变柱状体颜色的集合
         */
        private int[] barColors;

        /**
         * 渐变色
         */
        private boolean isGradualColor = false;
        /**
         * 开始渐变色
         */
        private int startGradualColor;
        /**
         * 结束渐变色
         */
        private int endGradualColor;
        /**
         * 最大值
         */
        private int minValue;
        /**
         * 最小值
         */
        private int maxValue;

        public Builder() {
        }

        public Builder setContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public Builder setBarChart(BarChart barChart) {
            this.barChart = barChart;
            return this;
        }

        public Builder setBarData(List<IBarData> barData) {
            this.barData = barData;
            return this;
        }

        public Builder setBarSetData(List<List<IBarData>> barSetData) {
            this.barSetData = barSetData;
            return this;
        }

        public Builder setLabels(List<String> mLabels) {
            this.mLabels = mLabels;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setyAxisRightEnable(boolean yAxisRightEnable) {
            this.yAxisRightEnable = yAxisRightEnable;
            return this;
        }

        public Builder setyAxisRLeftEnable(boolean yAxisRLeftEnable) {
            this.yAxisRLeftEnable = yAxisRLeftEnable;
            return this;
        }

        public Builder setScaleEnabled(boolean mScaleEnabled) {
            this.mScaleEnabled = mScaleEnabled;
            return this;
        }

        public Builder setDoubleTapToZoomEnabled(boolean mDoubleTapToZoomEnabled) {
            this.mDoubleTapToZoomEnabled = mDoubleTapToZoomEnabled;
            return this;
        }

        public Builder setDescriptionEnable(boolean mDescriptionEnable) {
            this.mDescriptionEnable = mDescriptionEnable;
            return this;
        }

        public Builder setPinchZoom(boolean pinchZoom) {
            this.pinchZoom = pinchZoom;
            return this;
        }

        public Builder setLegendEnable(boolean mLegendEnable) {
            this.mLegendEnable = mLegendEnable;
            return this;
        }

        public Builder setDrawGridLines(boolean mDrawGridLines) {
            this.mDrawGridLines = mDrawGridLines;
            return this;
        }

        public Builder setLegendTextSize(float legendTextSize) {
            this.legendTextSize = legendTextSize;
            return this;
        }

        public Builder setValueTextSize(float valueTextSize) {
            this.valueTextSize = valueTextSize;
            return this;
        }

        public Builder setGroupSpace(float groupSpace) {
            this.groupSpace = groupSpace;
            return this;
        }

        public Builder setBarWidth(float barWidth) {
            this.barWidth = barWidth;
            return this;
        }

        public Builder setDisplayCount(int displayCount) {
            this.displayCount = displayCount;
            return this;
        }

        public Builder setDurationMillis(int durationMillis) {
            this.durationMillis = durationMillis;
            return this;
        }

        public Builder setEasing(Easing.EasingOption mEasing) {
            this.mEasing = mEasing;
            return this;
        }

        public Builder setBarColors(List<Integer> mBarColors) {
            this.mBarColors = mBarColors;
            return this;
        }

        public Builder setXValueEnable(boolean xValueEnable) {
            this.xValueEnable = xValueEnable;
            return this;
        }

        public Builder setBarColor(int barColor) {
            this.barColor = barColor;
            return this;
        }

        public Builder setBarNoDataText(String noDataText) {
            this.noDataText = noDataText;
            return this;
        }

        public Builder setBarNoDataTextColor(int noDataTextColor) {
            this.noDataTextColor = noDataTextColor;
            return this;
        }

        public Builder setBarDifferentColor(boolean hasBarDifferentColor) {
            this.hasBarDifferentColor = hasBarDifferentColor;
            return this;
        }

        public Builder setBarMultopleColor(boolean multipleDifferentColor) {
            this.multipleDifferentColor = multipleDifferentColor;
            return this;
        }

        public Builder setBarDifferentColors(int[] barColors) {
            this.barColors = barColors;
            return this;
        }

        public int[] getBarColors() {
            return barColors;
        }

        public void setBarColors(int[] barColors) {
            this.barColors = barColors;
        }

        public boolean isHasBarDifferentColor() {
            return hasBarDifferentColor;
        }

        public void setHasBarDifferentColor(boolean hasBarDifferentColor) {
            this.hasBarDifferentColor = hasBarDifferentColor;
        }

        public Context getmContext() {
            return mContext;
        }

        public void setmContext(Context mContext) {
            this.mContext = mContext;
        }

        public String getNoDataText() {
            return noDataText;
        }

        public Builder setNoDataText(String noDataText) {
            this.noDataText = noDataText;
            return this;
        }

        public int getNoDataTextColor() {
            return noDataTextColor;
        }

        public void setNoDataTextColor(int noDataTextColor) {
            this.noDataTextColor = noDataTextColor;
        }

        public Builder setGradualColor(boolean gradualColor) {
            isGradualColor = gradualColor;
            return this;
        }

        public Builder setStartGradualColor(int startGradualColor) {
            this.startGradualColor = startGradualColor;
            return this;
        }

        public Builder setEndGradualColor(int endGradualColor) {
            this.endGradualColor = endGradualColor;
            return this;
        }

        public BarChartHelper build() {
            if (mBarColors == null) {
                //颜色填充
                String[] colors = mContext.getResources().getStringArray(R.array.chart_colors);
                List<Integer> chartColors = new ArrayList<Integer>();
                for (String color : colors) {
                    chartColors.add(Color.parseColor(color));
                }
                mBarColors = chartColors;
            }
            return new BarChartHelper(this);
        }
    }
}
