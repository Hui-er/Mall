package com.fish.lib_common.util;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
/**
 * @ClassName: DateFormatUtils
 * @Description: 日期响应实体类
 * @author lhj
 * @date 2016年11月3日
 */
public class DateFormatUtils {

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat specialDateFormat;
    private SimpleDateFormat dateTimeFormat;
    private SimpleDateFormat timeFormat;
    private SimpleDateFormat dayOutYearFormat;
    private SimpleDateFormat dayHourMinuteFormat;
    private SimpleDateFormat hourMinuteFormat;
    private SimpleDateFormat monthDayHourMinuteFormat;

    private DateFormatUtils() {
    }

    public static DateFormatUtils get() {
        DateFormatUtils dateUtils = Holder.utils;
        dateUtils.initDateFormat();
        return dateUtils;
    }

    private static class Holder {
        private static DateFormatUtils utils = new DateFormatUtils();
    }

    private void initDateFormat() {
        if (dateFormat == null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            setDateFormat(dateFormat);
        }
        if (specialDateFormat == null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("M.dd");
            setSpecialDateFormat(dateFormat);
        }
        if (dateTimeFormat == null) {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            setDateTimeFormat(dateTimeFormat);
        }

        if (timeFormat == null) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            setTimeFormat(timeFormat);
        }

        if (dayOutYearFormat == null) {
            SimpleDateFormat dayOutYearFormat = new SimpleDateFormat("MM-dd");
            setDayOutYearFormat(dayOutYearFormat);
        }

        if (dayHourMinuteFormat == null) {
            SimpleDateFormat dayOutYearFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            setDayHourMinuteFormat(dayOutYearFormat);
        }

        if (hourMinuteFormat == null) {
            SimpleDateFormat dayOutYearFormat = new SimpleDateFormat("HH:mm");
            setHourMinuteFormat(dayOutYearFormat);
        }

        if (monthDayHourMinuteFormat == null) {
            SimpleDateFormat dayOutYearFormat = new SimpleDateFormat("MM-dd HH:mm");
            setMonthDayHourMinuteFormat(dayOutYearFormat);
        }

    }

    public SimpleDateFormat getSpecialDateFormat() {
        return specialDateFormat;
    }

    public void setSpecialDateFormat(SimpleDateFormat specialDateFormat) {
        this.specialDateFormat = specialDateFormat;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public SimpleDateFormat getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(SimpleDateFormat dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public SimpleDateFormat getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(SimpleDateFormat timeFormat) {
        this.timeFormat = timeFormat;
    }

    public void setDayOutYearFormat(SimpleDateFormat simpleDateFormat) {
        this.dayOutYearFormat = simpleDateFormat;
    }

    public SimpleDateFormat getDayOutYearFormat() {
        return dayOutYearFormat;
    }

    public SimpleDateFormat getDayHourMinuteFormat() {
        return dayHourMinuteFormat;
    }

    public void setDayHourMinuteFormat(SimpleDateFormat dayHourMinuteFormat) {
        this.dayHourMinuteFormat = dayHourMinuteFormat;
    }

    public SimpleDateFormat getHourMinuteFormat() {
        return hourMinuteFormat;
    }

    public void setHourMinuteFormat(SimpleDateFormat hourMinuteFormat) {
        this.hourMinuteFormat = hourMinuteFormat;
    }

    public SimpleDateFormat getMonthDayHourMinuteFormat() {
        return monthDayHourMinuteFormat;
    }

    public void setMonthDayHourMinuteFormat(SimpleDateFormat monthDayHourMinuteFormat) {
        this.monthDayHourMinuteFormat = monthDayHourMinuteFormat;
    }

    /**
     * 获取现在时间
     *
     * @return 返回当天短时间字符串格式为yyyy-MM-dd的日期字符串
     */
    public String getStringDateShort() {
        Date currentTime = new Date();
        return getDateFormat().format(currentTime);
    }

    /**
     * 获取增加多少月的时间
     *
     * @return 增加多少月的新Date对象
     */
    public static Date getAddMonthDate(Date date, int addMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, addMonth);
        return calendar.getTime();
    }

    /**
     * 获取增加多少天的时间
     *
     * @return 增加多少天的新Date对象
     */
    public static Date getAddDayDate(Date date, int addDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, addDay);
        return calendar.getTime();
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return 返回短时间字符串格式为yyyy-MM-dd的日期字符串
     */
    public String dateToStr(Date dateDate) {
        return getDateFormat().format(dateDate);
    }

    /**
     * 得到现在时间
     *
     * @return 当天的日期date对象
     */
    public Date getNow() {
        Date currentTime = new Date();
        return currentTime;
    }

    /**
     * 获取当前时间（年-月-日 时:分:秒）
     *
     * @return 返回当天短时间字符串格式为yyyy-MM-dd HH:mm:ss:SSS的日期字符串
     */
    public String getNowTimeStr() {
        Date currentTime = new Date();
        return getDateTimeFormat().format(currentTime);
    }

    /**
     * 获取当前时间的对应的时间格式字符串
     *
     * @return 返回当天短时间字符串(默认为格式为yyyy - MM - dd HH : mm : ss的日期字符串)
     */
    public String getCurrentFormatDataString(Date date, SimpleDateFormat simpleDateFormat) {
        if (simpleDateFormat == null) {
            return getDateTimeFormat().format(date);
        } else {
            return simpleDateFormat.format(date);
        }
    }

    /**
     * 根据字符串转换成date，格式为yyyy-MM-dd HH:mm:ss:SSS
     *
     * @param strDate
     * @return 返回短时间字符串格式为yyyy-MM-dd HH:mm:ss:SSS的date对象
     */
    public Date strToDateByYMDHMS(String strDate) {
        ParsePosition pos = new ParsePosition(0);
        Date strToDate = getDateTimeFormat().parse(strDate, pos);
        return strToDate;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return 返回短时间格式为 yyyy-MM-dd 的日期对象
     */
    public Date strToDate(String strDate) {
        ParsePosition pos = new ParsePosition(0);
        return getDateFormat().parse(strDate, pos);
    }

    /**
     * 获取系统的年份
     *
     * @return 返回当天的年份字符串
     */
    public String getCurrentSystemYear() {
        Date d = new Date();
        String dateContent = getDateFormat().format(d);
        return dateContent.split("-")[0];
    }

    /**
     * 获取系统的年月日
     *
     * @return 返回当天短时间格式为 yyyy-MM-dd 的字符串
     */
    public String getYearAndMonthAndDay() {
        Date d = new Date();
        return getDateFormat().format(d);
    }

    /**
     * 此方法为特殊的格式请谨慎使用
     *
     * @param dateDate
     * @return 返回时间格式为M.dd的字符串
     */
    public String getSpecialDateToStr(Date dateDate) {
        String dateString = getSpecialDateFormat().format(dateDate);
        return dateString;
    }

    /**
     * 把 yyyyMM 转成 yyyy-MM格式
     */
    public static String formatDate(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM");
        String result = "";
        try {
            result = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取某个日期的上一年日期
     *
     * @param strDate 传入 yyyy-MM-dd 日期字符串
     * @return 返回 yyyy-MM-dd 日期字符串
     */
    public Date getStrLastYearToDate(String strDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strToDate(strDate));
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 今天 0：00 - 23：59 显示 今天 12：34；
     * 昨天 0：00 - 23：59 显示 昨天 16：15；
     * 前天至今年1月1日，显示 04-23 18：12；
     * 去年至以前，显示：2019-04-12
     */
    public String getShowTime(Context context, String strDate) {
        try {
            String descriptiveText = null;
            SimpleDateFormat simpleDateFormat = getDateTimeFormat(); //默认取全部

            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(new Date());

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(simpleDateFormat.parse(strDate, new ParsePosition(0)));

            if (currentCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                //同年
                switch (currentCalendar.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR)) {
                    case 0:
                        descriptiveText = "今天";
                        simpleDateFormat = getHourMinuteFormat();
                        break;
                    case 1:
                        descriptiveText = "昨天";
                        simpleDateFormat = getHourMinuteFormat();
                        break;
                    default:
                        simpleDateFormat = getMonthDayHourMinuteFormat();
                        break;
                }
            }else {
                simpleDateFormat = getDateFormat();
            }

            String formatDate = simpleDateFormat.format(calendar.getTime());
            if (descriptiveText != null) {
                return descriptiveText + " " + formatDate;
            } else
                return formatDate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将对应的日期字符串转为时间戳
     * @param dateString
     * @return
     */
    public long getDateTimeMillion(SimpleDateFormat simpleDateFormat, String dateString) {
        if (simpleDateFormat == null) simpleDateFormat = getDateFormat();
        try {
            return simpleDateFormat.parse(dateString).getTime();
        } catch (ParseException e) {

        }
        return 0;
    }
}
