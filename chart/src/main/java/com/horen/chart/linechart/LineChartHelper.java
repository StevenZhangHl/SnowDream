package com.horen.chart.linechart;

import android.graphics.Color;
import android.graphics.Matrix;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.horen.base.util.CollectionUtils;
import com.horen.chart.DefaultValueFormatterUtil;
import com.horen.chart.YAxisRendererUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/03/14:19
 * @description :折线图的封装
 * @github :https://github.com/chenyy0708
 */
public class LineChartHelper {

    private LineChart lineChart;
    private YAxis yAxis;
    private XAxis xAxis;

    private boolean isInit;

    public LineChartHelper(LineChart lineChart) {
        this.lineChart = lineChart;
        yAxis = lineChart.getAxisLeft();
        xAxis = lineChart.getXAxis();
        //背景颜色
        lineChart.setBackgroundColor(Color.WHITE);
        // 隐藏右边Y轴
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setScaleEnabled(false);
        //设置是否可以通过双击屏幕放大图表。默认是true
        lineChart.setDoubleTapToZoomEnabled(false);
        //设置描述
        lineChart.getDescription().setEnabled(false);
        //设置按比例放缩柱状图
        lineChart.setPinchZoom(true);
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
        //可以拖拽
        lineChart.setDragEnabled(true);
        //禁止y轴缩放
        lineChart.setScaleYEnabled(false);
        lineChart.setDragYEnabled(false);
        //以防止值由敲击姿态被突出显示。
        lineChart.setHighlightPerTapEnabled(true);
        lineChart.setHorizontalFadingEdgeEnabled(false);
        lineChart.setHighlightPerDragEnabled(false);
    }


    /**
     * 初始化LineChart
     */
    private void initLineChart() {
        //折线图例 标签 设置
        Legend legend = lineChart.getLegend();
        legend.setTextSize(12);
        // 图例间隔
        legend.setXEntrySpace(30);
        // 图例和文字间隔
        legend.setFormToTextSpace(10);
        // 线高
        legend.setFormLineWidth(4);
        // 线宽
        legend.setFormSize(30);
        //显示位置
        legend.setTextColor(Color.parseColor("#333333"));
        legend.setForm(Legend.LegendForm.LINE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        //x坐标轴设置
        XAxis xAxis = lineChart.getXAxis();
        // x轴对齐位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.parseColor("#DCDCDC"));
        // 隐藏网格线
        xAxis.setDrawGridLines(false);
        // 隐藏X轴横线
//        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.parseColor("#666666"));
        xAxis.setTextSize(11);
        //y轴设置
        YAxis leftAxis = lineChart.getAxisLeft();
        //此代码段处理y轴文本居做显示
        ViewPortHandler viewPortHandler = lineChart.getViewPortHandler();
        Transformer transformer = lineChart.getTransformer(leftAxis.getAxisDependency());
        lineChart.setRendererLeftYAxis(new YAxisRendererUtil(viewPortHandler, leftAxis, transformer));
        leftAxis.setXOffset(10);
        leftAxis.setTextColor(Color.parseColor("#333333"));
        leftAxis.setTextSize(12);
        leftAxis.setLabelCount(4, true);
        leftAxis.setDrawGridLines(false);
        // Y轴在图表内部
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //设置y轴的线是否显示
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setYOffset(-5);
        leftAxis.setValueFormatter(new DefaultValueFormatterUtil());//自定义格式化数据
        //绘制零线颜色
//        xAxis.setAxisMinimum(1f);
//        lineChart.setExtraTopOffset(25);
    }

    /**
     * 展示折线图(多条)
     *
     * @param lineSetData  多个折线图数据
     * @param labels       标签集合
     * @param barColors    颜色
     * @param displayCount 一页显示个数
     */
    public void showLines(List<List<ILineChartData>> lineSetData, List<String> labels, List<Integer> barColors, int displayCount, int maxValue, boolean isShowHorizontaltIndicator, boolean isShowVerticalIndicator) {
        // 初始化图表X、Y轴属性
        initLineChart();
        // 设置坐标轴的最大值和最小值
        if (maxValue % 3 != 0 || maxValue % 5 != 0) {
            maxValue = ((((maxValue / 5) / 3) + 1) * 3) * 5;
        }
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(maxValue == 0 ? 300 : maxValue);
        // X轴真实显示lable
        List<String> xValue = new ArrayList<>();
        // 多折线图数据集
        LineData lineData = new LineData();
        // 多折线图循环，得到每一种折线图集合数据
        for (int i = 0; i < lineSetData.size(); i++) {
            List<ILineChartData> barSetDatum = lineSetData.get(i);
            // 单种柱状图数据集
            ArrayList<Entry> entries = new ArrayList<>();
            for (int j = 0; j < barSetDatum.size(); j++) {
                entries.add(new Entry(j, barSetDatum.get(j).getValue()));
            }
            if (!CollectionUtils.isNullOrEmpty(entries)) {
                // 数据集合标签名
                LineDataSet lineDataSet = new LineDataSet(entries, labels.get(i));
                lineDataSet.setDrawHorizontalHighlightIndicator(isShowHorizontaltIndicator);//去掉点击时的横向指示器
                lineDataSet.setDrawVerticalHighlightIndicator(isShowVerticalIndicator);
                lineDataSet.setHighLightColor(barColors.get(i));
                lineDataSet.setColor(barColors.get(i));
                lineDataSet.setValueTextColor(barColors.get(i));
                lineDataSet.setValueTextSize(10f);
                //设置该线的宽度
                lineDataSet.setLineWidth(1f);
                lineDataSet.setDrawCircles(false);
                // 曲线显示数字
                lineDataSet.setDrawValues(false);
                lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                lineData.addDataSet(lineDataSet);
            }
        }
        // 初始化X轴值
        if (lineSetData.size() > 0) {
            for (ILineChartData iLineChartData : lineSetData.get(0)) {
                xValue.add(iLineChartData.getLabelName());
            }
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        lineChart.setData(lineData);
        if (displayCount != 0 && !isInit) {
            //设置动画效果
            Matrix m = new Matrix();
            //两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的2倍
            if (xValue.size() > displayCount) {
                m.postScale(xValue.size() / displayCount * 1.0f, 1f);
            }
//            xAxis.setLabelCount(12);
            //将图表动画显示之前进行缩放
            lineChart.getViewPortHandler().refresh(m, lineChart, false);
            isInit = true;
        }
        lineChart.animateY(500, Easing.EasingOption.Linear);
        lineChart.animateX(500, Easing.EasingOption.Linear);
        lineChart.invalidate();
    }

    /**
     * 展示折线图(一条)
     *
     * @param barChartData 单个柱状图数据
     * @param color        柱状图颜色
     * @param displayCount 一页显示柱状图数
     */
    public void showLine(List<ILineChartData> barChartData, int color, int displayCount) {
        initLineChart();
        // X轴真实显示lable
        List<String> xValue = new ArrayList<>();
        // Y轴图标数据
        // 单种柱状图数据集
        ArrayList<Entry> entries = new ArrayList<>();
        for (int j = 0; j < barChartData.size(); j++) {
            if (!xValue.contains(barChartData.get(j).getLabelName())) {
                xValue.add(barChartData.get(j).getLabelName());
            }
            entries.add(new Entry(j, barChartData.get(j).getValue()));
        }
        // 多折线图数据集
        LineData lineData = new LineData();
        //折线图例 标签 设置
        lineChart.getLegend().setEnabled(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setLabelCount(4, true);
        // 数据集合标签名
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        lineDataSet.setColor(color);
        lineDataSet.setValueTextColor(color);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawVerticalHighlightIndicator(false);
        //设置该线的宽度
        lineDataSet.setLineWidth(1f);
        lineDataSet.setDrawCircles(false);
        // 曲线显示数字
        lineDataSet.setDrawValues(false);
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);
        if (displayCount != 0 && !isInit) {
            //设置动画效果
            Matrix m = new Matrix();
            //两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的2倍
            if (xValue.size() > displayCount) {
                m.postScale(xValue.size() / displayCount, 1f);
            }
            //将图表动画显示之前进行缩放
            lineChart.getViewPortHandler().refresh(m, lineChart, false);
            isInit = true;
        }
        lineChart.animateY(500, Easing.EasingOption.Linear);
        lineChart.animateX(500, Easing.EasingOption.Linear);
    }
}
