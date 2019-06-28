package com.horen.chart.piechart;

/**
 * @author :ChenYangYi
 * @date :2018/08/03/12:59
 * @description :饼状图数据接口
 * @github :https://github.com/chenyy0708
 */
public interface IPieData {
    /**
     * 图表Y轴值
     */
    float getValue();

    /**
     * 对应名字
     */
    String getLabelName();
}
