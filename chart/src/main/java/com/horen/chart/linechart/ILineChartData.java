package com.horen.chart.linechart;

/**
 * @author :ChenYangYi
 * @date :2018/08/03/13:16
 * @description :折线图上层接口
 * @github :https://github.com/chenyy0708
 */
public interface ILineChartData {
    /**
     * 图表Y轴值
     */
    float getValue();

    /**
     * 对应名字
     */
    String getLabelName();
}
