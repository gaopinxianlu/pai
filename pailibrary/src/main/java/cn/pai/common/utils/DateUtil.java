package cn.pai.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author linyt
 */
public class DateUtil {

    /**
     * 当前时间
     * 默认格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 当前时间
     *
     * @param format
     * @return
     */
    public static String getCurrentTime(String format) {
        return formatDate(new Date(), format);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将字符串的日志格式化为date对象
     *
     * @param date
     * @param format
     * @return
     */
    public static Date formatDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化日期
     *
     * @param oldDateStr
     * @param oldFormat
     * @param newFormat
     * @return
     */
    public static String formatDate(String oldDateStr, String oldFormat, String newFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        try {
            Date oldDate = sdf.parse(oldDateStr);
            return formatDate(oldDate, newFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return oldDateStr;
    }

    /**
     * 计算日期差
     *
     * @param current
     * @param last
     * @return
     */
    public static long differDate(Date current, Date last) {
        long diff = current.getTime() - last.getTime();
        return diff / (1000 * 60 * 60 * 24);
    }

    /**
     * 日期比较
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareTo(Date date1, Date date2) {
        return date1.compareTo(date2);
    }

    /**
     * 任意日期增加天数
     *
     * @param date
     * @param increase
     * @return
     */
    public static Date addDate(Date date, int increase) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, increase);
        return calendar.getTime();
    }

    /**
     * 从今天增加天数
     *
     * @param num
     * @return
     */
    public static Date addCurrentDate(int num) {
        return addDate(new Date(), num);
    }

    /**
     * 任意时间增加小时
     *
     * @param date
     * @param increase
     * @return
     */
    public static Date addHour(Date date, int increase) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, increase);
        return calendar.getTime();
    }

    /**
     * 当前时间增加小时
     *
     * @param num
     * @return
     */
    public static Date addCurrentHour(int num) {
        return addDate(new Date(), num);
    }

    /**
     * 获取当前是星期几
     *
     * @return
     */
    public static String getCurrentDatOfWeek() {
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "星期一";
            case Calendar.TUESDAY:
                return "星期二";
            case Calendar.WEDNESDAY:
                return "星期三";
            case Calendar.THURSDAY:
                return "星期四";
            case Calendar.FRIDAY:
                return "星期五";
            case Calendar.SATURDAY:
                return "星期六";
            case Calendar.SUNDAY:
            default:
                return "星期日";
        }
    }
}
