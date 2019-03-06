package com.steven.base.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @user steven
 * @createDate 2019/3/5 18:05
 * @description 自定义
 */
public class NumberUtil {
    /**
     * 保留2位小数且带千位符
     *
     * @param number
     * @return
     */
    public static String formitNumber(double number) {
        DecimalFormat df = new DecimalFormat("###,###.##");
        NumberFormat nf = NumberFormat.getInstance();
        String result = "";
        try {
            result = df.format((nf.parse(String.valueOf(number))));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!result.contains(".")) {
            result = result + ".00";
        }
        return result;
    }

    /**
     * 保留2位小数不带千位符
     *
     * @param number
     * @return
     */
    public static String formitNumberTwoPoint(double number) {
        String result = "";
        DecimalFormat df = new DecimalFormat("###.##");
        NumberFormat nf = NumberFormat.getInstance();
        try {
            result = df.format((nf.parse(String.valueOf(number))));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!result.contains(".")) {
            result = result + ".00";
        }
        return result;
    }

    /**
     * 保留2位小数不带千位符
     *
     * @param number
     * @return
     */
    public static String formitNewNumberTwoPoint(double number) {
        String result = "";
        DecimalFormat df = new DecimalFormat("###.##");
        NumberFormat nf = NumberFormat.getInstance();
        try {
            result = df.format((nf.parse(String.valueOf(number))));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!result.contains(".")) {
            result = result + ".00";
        } else {
            String substring = result.substring(result.indexOf(".") + 1, result.length()); // 截取 . 之后的字符串
            if (substring.length() < 2) {
                result += "0";
            }
        }

        return result;
    }

    /**
     * 保留2位小数不带千位符 带¥符号
     *
     * @param number
     * @return
     */
    public static String formitNumberTwo(double number) {
        String result = "";
        DecimalFormat df = new DecimalFormat("###.##");
        NumberFormat nf = NumberFormat.getInstance();
        try {
            result = df.format((nf.parse(String.valueOf(number))));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!result.contains(".")) {
            result = result + ".00";
        }
        return "¥ " + result;
    }

    /**
     * 格式化万数据，带两个小数点
     */
    public static String formitNumberTenthousand(double number) {
        double money;
        // 大于一万格式化万
        if (number >= 10000) {
            money = number / 10000;
        } else {
            money = number;
        }
        String result = "";
        DecimalFormat df = new DecimalFormat("###.##");
        NumberFormat nf = NumberFormat.getInstance();
        try {
            result = df.format((nf.parse(String.valueOf(money))));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!result.contains(".")) {
            result = result + ".00";
        }
        if (number >= 10000) {
            result += "万";
        }
        return result;
    }

    /**
     * 带千位符但是不保留小数
     *
     * @param number
     * @return
     */
    public static String formitNumberNoPoint(double number) {
        String result = "";
        DecimalFormat df = new DecimalFormat("###,###");
        NumberFormat nf = NumberFormat.getInstance();
        try {
            result = df.format((nf.parse(String.valueOf(number))));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
