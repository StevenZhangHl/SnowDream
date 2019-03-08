package com.steven.base.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @user steven
 * @createDate 2019/3/8 13:42
 * @description 日期工具类
 */
public class DateTimeHelper {
    //只有月份和天
    public static SimpleDateFormat sdfMonthDay = new SimpleDateFormat("MM/dd");
    // 普通的日期类型(不包含时、分、秒)
    public static SimpleDateFormat sdfCommonNoTime = new SimpleDateFormat(
            "yyyy-MM-dd");
    // 普通的日期类型(包含时、分)
    public static SimpleDateFormat sdfCommonNoSecond = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    // 普通的日期类型(包含时、分、秒)
    public static SimpleDateFormat sdfCommon = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    // 日志文件日期类型
    public static SimpleDateFormat sdfLogFileName = new SimpleDateFormat(
            "yyyyMMdd-HHmmss");
    // 日志文件日期类型
    public static SimpleDateFormat sdfBookMarkID = new SimpleDateFormat(
            "yyMMddHHmmssSSS");
    // 照片文件日期类型
    public static SimpleDateFormat sdfPhoneFileName = new SimpleDateFormat(
            "'IMG'_yyyyMMdd_HHmmss");

    public static SimpleDateFormat sdfChinese = new SimpleDateFormat(
            "yyyy年MM月dd日 E");

    // 打赏日期类型
    public static SimpleDateFormat sdfTipDate = new SimpleDateFormat(
            "yyyyMMddHHmmss");

    /**
     * 获取当前时间(格式：MM-dd )
     *
     * @return
     */
    public static String getCurrentMonthDay() {
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return updateDateFormat(sdfMonthDay.format(curDate));
    }

    /**
     * 获取当前时间(格式：yyyy-MM-dd HH:mm)
     *
     * @return
     */
    public static String getCurrentSystemTime() {
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return updateDateFormat(sdfCommonNoSecond.format(curDate));
    }

    /**
     * 获取当前时间(格式：yyyy-MM-dd HH:mm)
     *
     * @return
     */
    public static String formatCurrentDate(String date) {
        String result = "";
        if (!TextUtils.isEmpty(date)) {
            result = date.substring(0, date.length() - 3);
        }
        return result;
    }

    /**
     * 时间戳转换为时间(格式：yyyy-MM-dd )
     *
     * @return
     */
    public static String getTimeFromLong(long l) {
        Date curDate = new Date(l);// 获取当前时间
        return updateDateFormat(sdfCommonNoTime.format(curDate));
    }

    public static String getTimeStr(long time) {
        Date date = new Date(time);
        return sdfCommon.format(date);
    }

    /**
     * 获取当前时间(格式：yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static String getCurrentHHMMSSTime() {
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return updateDateFormat(sdfCommon.format(curDate));
    }

    /**
     * 获取当前时间(格式：yyyy-MM-dd)
     *
     * @return
     */
    public static String getPicDate() {
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return updateDateFormat(sdfCommonNoTime.format(curDate));
    }

    public static String getAlmostAneYearDate(int alomstTime) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, alomstTime);
        String oneYear = sdfCommonNoTime.format(c.getTime());
        return oneYear;
    }

    public static String getAlmostAneYearNoSecondTime(int alomstTime) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, alomstTime);
        String oneYear = sdfCommonNoSecond.format(c.getTime());
        return oneYear;
    }

    /**
     * 获取按时间设置的ID
     *
     * @return
     */
    public static long getNotesBookMarkID() {
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return Long.valueOf(sdfBookMarkID.format(curDate));
    }

    /**
     * 获取指定格式类型的日期（若为今天则显示"09:30"，昨天则显示"昨天 09:30",今年则显示"02-11 09:30",今年以前则为
     * "2014-12-11 09:30"）
     *
     * @param orderTime
     * @return
     */
    public static String getOrderFormatTime(String orderTime) {
        if (orderTime == null || orderTime.length() < 16) {
            return orderTime;
        }
        String resultDate = orderTime;
        int midDateYear = 0;
        if (orderTime != null && orderTime.length() > 4) {
            midDateYear = Integer.parseInt(orderTime.substring(0, 4));
        }

        // 获取当前日期
        String currentDateStr = sdfCommonNoTime.format(new Date());
        int currentDateYear = Integer.parseInt(currentDateStr.substring(0, 4));

        if (midDateYear < currentDateYear)
            resultDate = orderTime.substring(0, 16); // 今年以前
        else {
            try {
                Date currentDate = sdfCommonNoTime.parse(currentDateStr);
                Date midDate = sdfCommonNoTime.parse(orderTime);
                if ((currentDate.getTime() - midDate.getTime()) > 0
                        && (currentDate.getTime() - midDate.getTime()) <= 86400000) // 昨天
                    resultDate = "昨天 " + orderTime.substring(11, 16);
                else if ((currentDate.getTime() - midDate.getTime()) <= 0)
                    resultDate = "今天 " + orderTime.substring(11, 16); // 今天
                else {
                    resultDate = orderTime.substring(5, 16); // 昨天以前
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return updateDateFormat(resultDate);
    }

    /**
     * 获取指定格式类型的日期（若为今天则显示"09:30"，昨天则显示"昨天  09:30",今年则显示"02-11 09:30",今年以前则为
     * "2014-12-11 09:30"）
     *
     * @param orderTime
     * @return
     */
    public static String getChatFormatTime(String orderTime) {
        if (orderTime == null || orderTime.length() < 16) {
            return orderTime;
        }
        String resultDate = orderTime;
        int midDateYear = 0;
        if (orderTime != null && orderTime.length() > 4) {
            midDateYear = Integer.parseInt(orderTime.substring(0, 4));
        }

        // 获取当前日期
        String currentDateStr = sdfCommonNoTime.format(new Date());
        int currentDateYear = Integer.parseInt(currentDateStr.substring(0, 4));

        if (midDateYear < currentDateYear)
            resultDate = orderTime.substring(0, 16); // 今年以前
        else {
            try {
                Date currentDate = sdfCommonNoTime.parse(currentDateStr);
                Date midDate = sdfCommonNoTime.parse(orderTime);
                if ((currentDate.getTime() - midDate.getTime()) > 0
                        && (currentDate.getTime() - midDate.getTime()) <= 86400000) // 昨天
                    resultDate = "昨天  " + orderTime.substring(11, 16);
                else if ((currentDate.getTime() - midDate.getTime()) <= 0)
                    resultDate = orderTime.substring(11, 16); // 今天
                else {
                    resultDate = orderTime.substring(5, 16); // 昨天以前
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return updateDateFormat(resultDate);
    }

    /**
     * 获取指定格式类型的日期（若为今天显示"今天"，昨天则显示"昨天",今年则显示"02-11",今年以前则为"2014-02-11"
     *
     * @param dateTime
     * @return
     */
    public static String getNoTimeFormatTime(String dateTime) {
        if (dateTime == null || dateTime.length() < 10) {
            return dateTime;
        }
        String resultDate = dateTime;
        int midDateYear = 0;
        if (dateTime != null && dateTime.length() > 4) {
            midDateYear = Integer.parseInt(dateTime.substring(0, 4));
        }

        // 获取当前日期
        String currentDateStr = sdfCommonNoTime.format(new Date());
        int currentDateYear = Integer.parseInt(currentDateStr.substring(0, 4));

        if (midDateYear < currentDateYear)
            resultDate = dateTime.substring(0, 10); // 今年以前
        else {
            try {
                Date currentDate = sdfCommonNoTime.parse(currentDateStr);
                Date midDate = sdfCommonNoTime.parse(dateTime);
                if ((currentDate.getTime() - midDate.getTime()) > 0
                        && (currentDate.getTime() - midDate.getTime()) <= 86400000) // 昨天
                    resultDate = "昨天  ";
                else if ((currentDate.getTime() - midDate.getTime()) <= 0)
                    resultDate = "今天"; // 今天
                else {
                    resultDate = dateTime.substring(5, 10); // 昨天以前
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return updateDateFormat(resultDate);
    }

    // 使用系统当前日期加以调整作为照片的名称
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        return sdfPhoneFileName.format(date) + ".jpg";
    }

    public static long getLongByTimeString(String time) {
        if (time == null) {
            return 0;
        }

        try {
            return sdfCommon.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 截取时间里面的年、月、日、时、分、秒
     *
     * @param time
     * @return
     */
    public static String getDataYYMMDDTT(String time) {
        if (time == null) {
            return "";
        }
        time = updateDateFormat(time);
        int strLen = time.length();
        if (strLen >= 16) {
            return time.substring(0, 16);
        } else {
            return time;
        }
    }

    /**
     * 截取时间里面的年、月、日
     *
     * @param time
     * @return
     */
    public static String getData(String time) {
        if (time == null) {
            return "";
        }
        time = updateDateFormat(time);
        int strLen = time.length();
        if (strLen >= 10) {
            return time.substring(0, 10);
        } else {
            return time;
        }
    }

    /**
     * 截取时间里面的年、月
     *
     * @param time
     * @return
     */
    public static String getTimeYYMM(String time) {
        if (time == null) {
            return "";
        }
        time = updateDateFormat(time);
        int strLen = time.length();
        if (strLen >= 7) {
            return time.substring(0, 7);
        } else {
            return time;
        }
    }

    public static String getTimeMMDD(String time) {
        if (time == null) {
            return "";
        }
        time = updateDateFormat(time);
        int strLen = time.length();
        if (strLen >= 10) {
            return time.substring(5, 10);
        } else {
            return time;
        }
    }

    /**
     * 截取时间里面的年
     *
     * @param time
     * @return
     */
    public static String getTimeYY(String time) {
        if (time == null) {
            return "";
        }
        time = updateDateFormat(time);
        int strLen = time.length();
        if (strLen >= 4) {
            return time.substring(0, 4);
        } else {
            return time;
        }
    }

    /**
     * 截取时间里面的月
     *
     * @param time
     * @return
     */
    public static String getTimeMM(String time) {
        if (time == null) {
            return "";
        }
        time = updateDateFormat(time);
        int strLen = time.length();
        if (strLen >= 7) {
            return time.substring(5, 7);

        } else {
            return "";
        }
    }

    /**
     * 截取时间里面的日
     *
     * @param time
     * @return
     */
    public static String getTimeDD(String time) {
        if (time == null) {
            return "";
        }
        time = updateDateFormat(time);
        int strLen = time.length();
        if (strLen >= 10) {
            return time.substring(8, 10);

        } else {
            return "";
        }
    }

    /**
     * 截取时间里面的时
     *
     * @param time
     * @return
     */
    public static String getTimeHH(String time) {
        if (time == null) {
            return "";
        }
        time = updateDateFormat(time);
        int strLen = time.length();
        if (strLen >= 13) {
            return time.substring(11, 13);

        } else {
            return "";
        }
    }

    /**
     * 截取时间里面的分
     *
     * @param time
     * @return
     */
    public static String getTimeMinute(String time) {
        if (time == null) {
            return "";
        }
        time = updateDateFormat(time);
        int strLen = time.length();
        if (strLen >= 14) {
            return time.substring(14, 16);

        } else {
            return "";
        }
    }

    /**
     * 截取时间里面的时、分
     *
     * @param time
     * @return
     */
    public static String getTimeHHMM(String time) {
        if (time == null) {
            return "";
        }
        time = updateDateFormat(time);
        int strLen = time.length();
        if (strLen >= 16) {
            return time.substring(11, 16);
        } else {
            return "";
        }

    }

    /**
     * 截取时间里面的时、分、秒
     *
     * @param time
     * @return
     */
    public static String getTimeHHMMSS(String time) {
        if (time == null) {
            return "";
        }
        time = updateDateFormat(time);
        int strLen = time.length();
        if (strLen > 11) {
            return time.substring(11, strLen);
        } else {
            return "";
        }

    }

    /**
     * 截掉时间里面的秒
     *
     * @param time
     * @return
     */
    public static String getNoSecondTimeStr(String time) {
        if (time == null) {
            return "";
        }
        time = updateDateFormat(time);
        int strLen = time.length();
        if (strLen < 19) {
            return time;
        }
        return time.substring(0, strLen - 3);
    }

    /**
     * 获取持续天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date date = null;
        java.util.Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    public static String getDynamicTime(String date1) {
        String timeStr = "";
        if (date1 == null || date1.equals(""))
            return "";
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = null;
        java.util.Date mydate = null;
        try {
            date = myFormatter.parse(getOldDateFormat(getCurrentHHMMSSTime()));
            mydate = myFormatter.parse(date1);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (/*24 * 60 * 60 * */1000);
        if (day < 60) {
            timeStr = "刚刚";
        } else if (day >= 60 && day < 3600) {
            timeStr = day / 60 + "分钟前";
        } else if (day >= 3600 && day < 3600 * 24) {
            timeStr = day / 3600 + "小时前";
        } else if (day >= 3600 * 24) {
            timeStr = day / (3600 * 24) + "天前";
        }
        return timeStr;
    }

    public static String getHomePageTime(String date1) {
        String timeStr = "";
        if (date1 == null || date1.equals(""))
            return "";
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = null;
        java.util.Date mydate = null;
        try {
            date = myFormatter.parse(getOldDateFormat(getCurrentHHMMSSTime()));
            mydate = myFormatter.parse(date1);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (/*24 * 60 * 60 * */1000);
        if (day < 60 * 2) {
            timeStr = "刚刚";
        } else if (day >= 60 * 2 && day < 3600) {
            timeStr = day / 60 + "分钟前";
        } else if (day >= 3600 && day < 3600 * 24) {
            timeStr = day / 3600 + "小时前";
        } else if (day >= 3600 * 24 && day < 3600 * 24 * 30) {
            timeStr = day / (3600 * 24) + "天前";
        } else if (day >= 3600 * 24 * 30 && day < 3600 * 24 * 365) {
            timeStr = day / (3600 * 24 * 30) + "月前";
        } else if (day >= 3600 * 24 * 365) {
            timeStr = day / (3600 * 24 * 365) + "年前";
        }

        return timeStr;
    }

    public static String getMeetingTime(String date1) {
        String timeStr = "";
        if (date1 == null || date1.equals("") || date1.equals("0"))
            return "";
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = null;
        java.util.Date mydate = null;
        try {
            date = myFormatter.parse(getOldDateFormat(getCurrentHHMMSSTime()));
            mydate = myFormatter.parse(date1);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (/*24 * 60 * 60 * */1000);
        if (day < 60 * 2) {
            timeStr = day + "秒";
        } else if (day >= 60 * 2 && day < 3600) {
            timeStr = day / 60 + "分钟";
        } else if (day >= 3600 && day < 3600 * 24) {
            timeStr = day / 3600 + "小时";
        } else if (day >= 3600 * 24 && day < 3600 * 24 * 30) {
            timeStr = day / (3600 * 24) + "天";
        } else if (day >= 3600 * 24 * 30 && day < 3600 * 24 * 365) {
            timeStr = day / (3600 * 24 * 30) + "月";
        } else if (day >= 3600 * 24 * 365) {
            timeStr = day / (3600 * 24 * 365) + "年";
        }
        return timeStr;
    }

    /**
     * 获取指定格式类型的日期（若为今天显示"今天"，昨天则显示"昨天",其他返回null）
     *
     * @param dateTime
     * @return
     */
    public static String getDayStr(String dateTime) {
        if (dateTime == null || dateTime.length() < 10) {
            return dateTime;
        }
        String resultDate = dateTime;
        int midDateYear = 0;
        if (dateTime != null && dateTime.length() > 4) {
            midDateYear = Integer.parseInt(dateTime.substring(0, 4));
        }

        // 获取当前日期
        String currentDateStr = sdfCommonNoTime.format(new Date());
        int currentDateYear = Integer.parseInt(currentDateStr.substring(0, 4));

        if (midDateYear < currentDateYear)
            resultDate = dateTime.substring(0, 10); // 今年以前
        else {
            try {
                Date currentDate = sdfCommonNoTime.parse(currentDateStr);
                Date midDate = sdfCommonNoTime.parse(dateTime);
                if ((currentDate.getTime() - midDate.getTime()) > 0
                        && (currentDate.getTime() - midDate.getTime()) <= 86400000) // 昨天
                    resultDate = "昨天  ";
                else if ((currentDate.getTime() - midDate.getTime()) <= 0)
                    resultDate = "今天"; // 今天
                else {
                    resultDate = null; // 昨天以前
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultDate;
    }

    /**
     * 获取撤回消息超时的时间描述，如果是小时，返回小时描述，如果是分钟，返回分钟描述
     *
     * @param revokeTime
     * @return
     */
    public static String getRevokeTimeDesc(long revokeTime) {
        if (revokeTime > 3600000) {
            int revokeHour = (int) (revokeTime / 3600000);
            return (revokeHour + "小时");
        }

        int revokeMinute = (int) (revokeTime / 60000);
        return (revokeMinute + "分钟");
    }

    public static String getNoTimeStr(String time) {
        if (time == null) {
            return "";
        }

        if (time.length() > 10) {
            return time.substring(0, 10);
        }

        return time;
    }

    public static Date parseDate(String time) {
        try {
            return sdfCommon.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static long getTime(String dateTime) {
        if (dateTime == null || dateTime.length() <= 0) {
            return 0;
        }
        long time = 0;
        try {
            if (dateTime.length() == 10) {
                time = sdfCommonNoTime.parse(dateTime).getTime();
            } else if (dateTime.length() == 16) {
                time = sdfCommonNoSecond.parse(dateTime).getTime();
            } else {
                time = sdfCommon.parse(dateTime).getTime();
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return time;
    }

    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static String formatDuration(long milliseconds) {
        long seconds = milliseconds / 1000;
        long secondPart = seconds % 60;
        long minutePart = seconds / 60 % 60;
        long hourPart = seconds / 60 / 60;
        if (hourPart <= 0) {
            return (minutePart >= 10 ? minutePart : "0" + minutePart) + ":"
                    + (secondPart >= 10 ? secondPart : "0" + secondPart);
        } else {
            return (hourPart >= 10 ? hourPart : "0" + hourPart) + ":"
                    + (minutePart >= 10 ? minutePart : "0" + minutePart) + ":"
                    + (secondPart >= 10 ? secondPart : "0" + secondPart);
        }
    }

    /**
     * 00:00:00格式
     */
    static String s;

    public static String getFormatDuration(int time) {
        s = String.format("%02d", time / 3600) + ":"
                + String.format("%02d", time % 3600 / 60) + ":"
                + String.format("%02d", time % 3600 % 60);
        if (s.startsWith("00")) {
            s = s.substring(3, s.length());
        }

        return s;
    }

    /**
     * 日期格式的转换 例：2017/09/03
     *
     * @param sourDateFormat 要转换成的日期格式
     * @param
     * @return
     */
    public static String getDateFormat(String sourDateFormat) {
        sourDateFormat = sourDateFormat.replace("-", "/");
        return getNoSecondTimeStr(sourDateFormat);
    }

    /**
     * 将时间字符串中的“-”变成新版的“/”
     *
     * @param sourDateFormat
     * @return
     */
    public static String updateDateFormat(String sourDateFormat) {
        return sourDateFormat = sourDateFormat.replace("-", "/");
    }

    /**
     * 将时间字符串中的“/”变成旧版的“-”
     *
     * @param sourDateFormat
     * @return
     */
    public static String getOldDateFormat(String sourDateFormat) {
        return sourDateFormat = sourDateFormat.replace("/", "-");
    }

    public static String UpDateChinaFormat(String sourDateFormat) {
        return getTimeYY(sourDateFormat) + "年" + getTimeMM(sourDateFormat) + "月" + getTimeDD(sourDateFormat) + "日";
    }

    //获取精确到秒的时间戳
    public static long getSecondTimestampTwo(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Long.valueOf(timestamp);
    }
}
