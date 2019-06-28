package com.horen.chart.linechart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.horen.base.app.BaseApp;
import com.horen.chart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/02/09:35
 * @description : 折线图工具类
 * @github :https://github.com/chenyy0708
 */
public class LineChartUtils {
    /**
     * 设置图表数据的方法
     *
     * @param lineChart      要设置数据的图表
     * @param formData       表格的Y轴数据
     * @param mode           线条形状
     * @param fadeDrawableId 阴影区渐变色的Drawable文件id
     * @param color          线条的颜色设置
     */
    public static void setLineData(Context context, LineChart lineChart, List<Float> formData, LineDataSet.Mode mode, int fadeDrawableId, int color) {

        //根据Y轴数据产生表格数据
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < formData.size(); i++) {
            values.add(new Entry(i, formData.get(i)));
        }

        LineDataSet set1;
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "");
            //不显示数值
            set1.setDrawValues(false);
            //数值不以原点显示
            set1.setDrawCircles(true);
            //设置线条的形状
            set1.setMode(mode);
            //设置线条颜色
            set1.setColor(color);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setHighLightColor(color);

            //set1.enableDashedLine(10f, 5f, 0f); //设置折线为虚线
            //set1.enableDashedHighlightLine(10f, 5f, 0f);
            //set1.setCircleColor(Color.BLUE);   //设置圆圈颜色
            //设置线条宽度
            set1.setLineWidth(2f);
            //set1.setCircleRadius(3f);   //设置眼圈直径
            //set1.setDrawCircleHole(true);  //设置圆圈空心
            set1.setValueTextSize(9f);
            //设置绘制阴影区域
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15f);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            LineData data = new LineData(dataSets);

            lineChart.setData(data);
        }
    }

    /**
     * 初始化表格
     *
     * @param lineChart 表格
     * @param maxRange  Y轴最大显示数值
     * @param minRange  Y轴最小显示数值
     * @param b         是否绘制Y轴Label
     * @param drawValue 是否绘制Y轴value
     */
    public static void initLineChart(Context context, LineChart lineChart, float maxRange, int minRange, boolean b, boolean drawValue) {
        //是否绘制表格背景
        lineChart.setDrawGridBackground(false);
        //设置触摸
        lineChart.setTouchEnabled(true);
        //设置课拖动
        lineChart.setDragEnabled(false);
        //设置可缩放
        lineChart.setScaleEnabled(false);
        //设置中心缩放
        lineChart.setPinchZoom(false);
        //数据描述
        lineChart.getDescription().setEnabled(false);

        lineChart.setNoDataText("没有数据");
        /*lineChart.setNoDataTextColor(Color.RED);*/
        //设置Y轴相关数据
        //隐藏Y轴右边的轴线
        lineChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        //设置最大数值范围
        leftAxis.setAxisMaximum(maxRange);
        //设置最小数值范围
        leftAxis.setAxisMinimum(minRange);
        //不绘制轴线
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(b);
        //设置Y轴Label的位置
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(false);
        //设置X轴相关数据
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setGridColor(android.R.color.transparent); //设置X轴线为透明
        //不绘制X轴
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(9);

        //设置图表动画
        lineChart.animateXY(1500, 1500);
        //不显示表名
        lineChart.getLegend().setEnabled(false);
    }

    /**
     * 初始化表格
     *
     * @param lineChart 表格
     * @param maxRange  Y轴最大显示数值
     * @param minRange  Y轴最小显示数值
     * @param b         是否绘制Y轴Label
     */
    public static void initLineChart(Context context, LineChart lineChart, int maxRange, int minRange, boolean b) {
        //是否绘制表格背景
//        lineChart.setDrawGridBackground(false);
//        //设置触摸
//        lineChart.setTouchEnabled(true);
//        //设置可拖动
//        lineChart.setDragEnabled(true);
//        //设置可缩放
//        lineChart.setScaleEnabled(true);
//        //设置中心缩放
//        lineChart.setPinchZoom(true);
        //数据描述
        lineChart.getDescription().setEnabled(false);

        lineChart.setNoDataText("");
        /*lineChart.setNoDataTextColor(Color.RED);*/

        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.DEFAULT);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        //设置Y轴相关数据
        //隐藏Y轴右边的轴线
        lineChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        //设置最大数值范围
        leftAxis.setAxisMaximum(maxRange);
        //设置最小数值范围
        leftAxis.setAxisMinimum(minRange);
        //不绘制轴线
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawLabels(b);
        //设置Y轴Label的位置
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(false);

        //设置X轴相关数据
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setGridColor(android.R.color.transparent); //设置X轴线为透明
        //不绘制X轴
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(9);

        //设置图表动画
        lineChart.animateXY(1500, 1500);
        //不显示表名
        lineChart.getLegend().setEnabled(true);
    }

    /**
     * 设置图表数据的方法 多个数据组
     *
     * @param lineChart      要设置数据的图表
     * @param formData1      表格的Y轴数据1
     * @param formData2      表格的Y轴数据2
     * @param mode           线条形状
     * @param fadeDrawableId 阴影区渐变色的Drawable文件id
     */
    public static void setLineData(Context context, LineChart lineChart, final List<String> xDatas, List<Float> formData1, List<Float> formData2, LineDataSet.Mode mode, int fadeDrawableId) {

        //根据Y轴数据产生表格数据
        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < formData1.size(); i++) {
            values1.add(new Entry(i, formData1.get(i)));
        }

        ArrayList<Entry> values2 = new ArrayList<>();
        for (int i = 0; i < formData2.size(); i++) {
            values2.add(new Entry(i, formData2.get(i)));
        }
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xDatas.get((int) value);
            }
        });

        LineDataSet set1, set2;
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
            set1.setValues(values1);
            set2.setValues(values2);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {

            set1 = new LineDataSet(values1, "收");
            //不显示数值
            set1.setDrawValues(false);
            //数值不以原点显示
            set1.setDrawCircles(true);
            //设置线条的形状
            set1.setMode(mode);
            //set1.enableDashedLine(10f, 5f, 0f); //设置折线为虚线
            //set1.enableDashedHighlightLine(10f, 5f, 0f);
            //设置圆圈颜色
            set1.setCircleColor(BaseApp.getAppResources().getColor(R.color.bar_color));
            set1.setColor(BaseApp.getAppResources().getColor(R.color.bar_color));
            //设置线条宽度
            set1.setLineWidth(2f);
            //设置眼圈直径
            set1.setCircleRadius(3f);
            //设置圆圈空心
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            //设置绘制阴影区域
            set1.setDrawFilled(false);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            set2 = new LineDataSet(values2, "发");
            set2.setCircleColor(BaseApp.getAppResources().getColor(R.color.bar_warning_color));
            set2.setDrawCircleHole(false);
            set2.setCircleRadius(3f);
            set2.setColor(BaseApp.getAppResources().getColor(R.color.bar_warning_color));
            //不显示数值
            set2.setDrawValues(false);
            //数值不以原点显示
            set2.setDrawCircles(true);
            //设置线条的形状
            set2.setMode(mode);
            //设置线条宽度
            set2.setLineWidth(2f);
            set2.setValueTextSize(9f);
            //设置绘制阴影区域
            set2.setDrawFilled(false);
            set2.setFormLineWidth(1f);
            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set2.setFormSize(15.f);
            LineData data = new LineData(set1, set2);
            lineChart.setData(data);
        }
    }
}
