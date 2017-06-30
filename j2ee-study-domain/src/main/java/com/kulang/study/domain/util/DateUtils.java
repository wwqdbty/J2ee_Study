package com.kulang.study.domain.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目中复制的一个工具类, 后期整理
 */
public class DateUtils {
    public static final String YYYYMMDD = "yyyy-MM-dd";

    public static final String YYYYMMDD_ZH = "yyyy年MM月dd日";

    public static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY; // 中国周一是一周的第一天

//    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);


    public static Date getNow() {

        return Calendar.getInstance().getTime();
    }

    private static ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };


    public static String formatDate(Date date) {

        if (date == null) {
            return null;
        }

        return local.get().format(date);
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat format = new SimpleDateFormat(pattern);

        return format.format(date);
    }

    public static Date formatDate(String value) {

        Date date = null;
        if ((null != value) && (!value.trim().equals(""))
                && (!value.trim().equals("null"))) {
            value = value.trim();
            String format = "yyyy-MM-dd HH:mm:ss";
            try {

                if (value.contains("-")) {
                    if (value.length() == 19) {
                        format = "yyyy-MM-dd HH:mm:ss";
                    } else if (value.length() == 16) {
                        format = "yyyy-MM-dd HH:mm";
                    } else if (value.length() == 13) {
                        format = "yyyy-MM-dd HH";
                    } else if (value.length() == 10) {
                        format = "yyyy-MM-dd";
                    }
                } else if (value.contains(":")) {
                    if (value.length() == 8) {
                        format = "HH:mm:ss";
                    } else if (value.length() == 5) {
                        format = "HH:mm";
                    }
                } else if (value.length() == 2) {
                    format = "HH";
                } else if (value.length() == 4) {
                    format = "HHmm";
                } else if (value.length() == 6) {
                    format = "HHmmss";
                } else if (value.length() == 8) {
                    format = "yyyyMMdd";
                } else if (value.length() == 10) {
                    format = "yyyyMMddHH";
                } else if (value.length() == 12) {
                    format = "yyyyMMddHHmm";
                } else if (value.length() == 14) {
                    format = "yyyyMMddHHmmss";
                }
                if (null != format) {
                    date = new SimpleDateFormat(format).parse(value);
                }
            } catch (ParseException e) {
//                log.error("异常", e);
            } catch (Exception e) {
//                log.error("异常", e);
            } finally {
                format = null;
            }
        }
        return date;
    }

    /**
     * 验证字符串是否为有效日期格式
     *
     * @param str 日期字符串
     * @param fm 日期格式化字符串
     * @return
     */
    public static boolean isValidDate(String str, String fm) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(fm);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 获取日期date前后的第几天
     */
    public static Date gapDay(Date date, int gapDay) {
        Calendar calendar = getCalendar(date);
        addDay(calendar, gapDay);
        return calendar.getTime();
    }

    /**
     * 获取日期date前后的第几天
     */
    public static Date gapDayAndClearTime(Date date, int gapDay) {
        Calendar calendar = getCalendar(date);
        addDay(calendar, gapDay);
        clearTime(calendar);
        return calendar.getTime();
    }

    /**
     * 在calendar上添加天数
     */
    public static void addDay(Calendar calendar, int day) {
        calendar.add(Calendar.DATE, day);
    }

    /**
     * 在calendar上添加月份
     */
    public static void addMonth(Calendar calendar, int month) {
        calendar.add(Calendar.MONTH, month);
    }

    /**
     * 在calendar上添加月份
     */
    public static Date addMonth(Date date, int month) {
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 获取昨天
     */
    public static Date getYestoryDay() {
        return gapDay(null, -1);
    }

    /**
     * 获取明天
     */
    public static Date getTomorrow() {
        return gapDay(null, 1);
    }

    /**
     * 获取第二天零点时刻
     */
    public static Date getTomorrowAndClearTime() {
        return gapDay(null, 1);
    }

    /**
     * 获取当天
     */
    public static Date getToday() {
        return new Date();
    }

    /**
     * 获取当天
     */
    public static Date getTodayAndClearTime() {
        Calendar calendar = getCalendarAndClearTime(null);
        return calendar.getTime();
    }

    /**
     * 根据时间戳来获取日期
     */
    public static Date getDate(long times) {
        return new Date(times);
    }

    /**
     * 清空日期里的时间
     */
    public static Date clearTime(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 清空日期里的时间
     */
    public static void clearTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 根据date获取Calendar对象
     */
    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return calendar;
    }

    /**
     * 清空日期里的时间
     */
    public static Calendar getCalendarAndClearTime(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 获取两个日期之间的间隔
     */
    public static int betweenDateByDay(Date startDate, Date endDate) {
        long t1 = startDate.getTime();
        long t2 = endDate.getTime();
        return (int) ((t2 - t1) / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取两个日期之间的间隔
     */
    public static Date getRandomTime(int day) {
        Random r = new Random();
        int m = r.nextInt(day);
        long t1 = System.currentTimeMillis();
        return getDate(t1 - m * 24 * 3600 * 100 - (m + 1) * 3600 * 800);
    }

    /**
     * 格式化日期
     */
    public static String formate(Date date, String pattern) {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 格式化日期
     */
    public static String formate2LongStr(Date date) {
        return formate(date, "yyyyMMddHHmmss");
    }

    /**
     * 格式化日期格式  yyyy-MM-dd
     */
    public static String formate2Date(Date date) {
        return formate(date, "yyyy-MM-dd");
    }

    /**
     * 格式化日期时间格式  yyyy-MM-dd HH:mm:ss
     */
    public static String formate2Datetime(Date date) {
        return formate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化时间格式 HH:mm:ss
     */
    public static String formate2TimeWithSecond(Date date) {
        return formate(date, "HH:mm:ss");
    }

    /**
     * 格式化时间格式 HH:mm
     */
    public static String formate2Time(Date date) {
        return formate(date, "HH:mm");
    }

    /**
     * 转换日期
     */
    public static Date parse(String formateDate, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(formateDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //应用级API

    /**
     * 根据年和月获取Calendar实例
     */
    public static Calendar getCalendar(int year, int month) {
        Calendar calendar = getCalendarAndClearTime(null);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar;
    }

    /**
     * 根据年和月获取Calendar实例
     */
    public static Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = getCalendarAndClearTime(null);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        return calendar;
    }

    /**
     * 获取某个月份下的第一天和最后一天
     */
    public static Date[] getFLDatesInMonth(int year, int month) {
        Calendar calendar = getCalendar(year, month);
        Date firstDate = calendar.getTime();
        firstDate = getFirstDayOfMonth(firstDate);
        calendar.add(Calendar.MONTH, 1);
        Date lastDate = calendar.getTime();
        lastDate = getFirstDayOfMonth(lastDate);
        return new Date[]{firstDate, lastDate};
    }

    /**
     * 获取某一天的第一天和第二天
     */
    public static Date[] getFLDay(int year, int month, int day) {
        Calendar calendar = getCalendar(year, month, day);
        clearTime(calendar);
        Date currentDay = calendar.getTime();
        addDay(calendar, 1);
        Date nextDay = calendar.getTime();
        return new Date[]{currentDay, nextDay};
    }

    /**
     * 判断某一日期是否在某个月内,不指定年份
     */
    public static boolean isDateInMonth(Date date, int startMonth, int endMonth) {
        Calendar calendar = getCalendar(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        return month > startMonth && month < endMonth;
    }


    /**
     * 判断某一日期是否在某个月内,指定年份
     */
    public static boolean isDateInMonth(Date date, int startMonth, int endMonth, int year) {
        Calendar calendar = getCalendar(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        return month > startMonth && month < endMonth && year == calendar.get(Calendar.YEAR);
    }

    /**
     * 判断某一日期是否在某天内
     */
    public static boolean isDateInDay(Date date, int startDay, int endDay) {
        Calendar calendar = getCalendar(date);
        int day = calendar.get(Calendar.DATE);
        return day >= startDay && day < endDay;
    }

    /**
     * 获取星期几
     */
    public static String getWeek(Date date) {
        if (date == null) {
            date = new Date();
        }
        String[] weeks = {"", "日", "一", "二", "三", "四", "五", "六"};
        Calendar calendar = getCalendar(date);
        int d = calendar.get(Calendar.DAY_OF_WEEK);
        return weeks[d];
    }

    /**
     * 获取一个月里有多少天
     */
    public static int getDaysOfMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = getCalendar(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取某一个月有多少天
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = getCalendar(year, month);
        return getDaysOfMonth(calendar.getTime());
    }

    /**
     * 获取当前月的第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        String mm = formate(date, "yyyy-MM-01");
        return parse(mm, "yyyy-MM-dd");
    }

    /**
     * 获取当前月的最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        int maxDay = getDaysOfMonth(date);
        String mm = formate(date, "yyyy-MM-" + maxDay);
        return parse(mm, "yyyy-MM-dd");
    }


    /**
     * @param strDate
     * @return
     */
    public static Date parseDate(String strDate) {
        return parseDate(strDate, null);
    }

    /**
     * parseDate
     *
     * @param strDate
     * @param pattern
     * @return
     */
    public static Date parseDate(String strDate, String pattern) {
        Date date = null;
        try {
            if (pattern == null) {
                pattern = YYYYMMDD;
            }
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            date = format.parse(strDate);
        } catch (Exception e) {
//            logger.error("parseDate error:" + e);
        }
        return date;
    }


    /**
     * 取得日期：年
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        return year;
    }

    /**
     * 取得日期：年
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        return month + 1;
    }

    /**
     * 取得日期：年
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int da = c.get(Calendar.DAY_OF_MONTH);
        return da;
    }

    /**
     * 取得当天日期是周几
     *
     * @param date
     * @return
     */
    public static int getWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(Calendar.DAY_OF_WEEK);
        return week_of_year - 1;
    }

    /**
     * 取得一年的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(Calendar.WEEK_OF_YEAR);
        return week_of_year;
    }

    /**
     * getWeekBeginAndEndDate
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getWeekBeginAndEndDate(Date date, String pattern) {
        Date monday = getMondayOfWeek(date);
        Date sunday = getSundayOfWeek(date);
        return formatDate(monday, pattern) + " - "
                + formatDate(sunday, pattern);
    }

    /**
     * 根据日期取得对应周周一日期
     *
     * @param date
     * @return
     */
    public static Date getMondayOfWeek(Date date) {
        Calendar monday = Calendar.getInstance();
        monday.setTime(date);
        monday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
        monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return monday.getTime();
    }

    /**
     * 根据日期取得对应周周日日期
     *
     * @param date
     * @return
     */
    public static Date getSundayOfWeek(Date date) {
        Calendar sunday = Calendar.getInstance();
        sunday.setTime(date);
        sunday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
        sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return sunday.getTime();
    }

    /**
     * 取得月的剩余天数
     *
     * @param date
     * @return
     */
    public static int getRemainDayOfMonth(Date date) {
        int dayOfMonth = getDayOfMonth(date);
        int day = getPassDayOfMonth(date);
        return dayOfMonth - day;
    }

    /**
     * 取得月已经过的天数
     *
     * @param date
     * @return
     */
    public static int getPassDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得月天数
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得季度第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfSeason(Date date) {
        return getFirstDateOfMonth(getSeasonDate(date)[0]);
    }

    /**
     * 取得季度最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfSeason(Date date) {
        return getLastDateOfMonth(getSeasonDate(date)[2]);
    }

    /**
     * 取得季度天数
     *
     * @param date
     * @return
     */
    public static int getDayOfSeason(Date date) {
        int day = 0;
        Date[] seasonDates = getSeasonDate(date);
        for (Date date2 : seasonDates) {
            day += getDayOfMonth(date2);
        }
        return day;
    }

    /**
     * 取得季度剩余天数
     *
     * @param date
     * @return
     */
    public static int getRemainDayOfSeason(Date date) {
        return getDayOfSeason(date) - getPassDayOfSeason(date);
    }

    /**
     * 取得季度已过天数
     *
     * @param date
     * @return
     */
    public static int getPassDayOfSeason(Date date) {
        int day = 0;

        Date[] seasonDates = getSeasonDate(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);

        if (month == Calendar.JANUARY || month == Calendar.APRIL
                || month == Calendar.JULY || month == Calendar.OCTOBER) {// 季度第一个月
            day = getPassDayOfMonth(seasonDates[0]);
        } else if (month == Calendar.FEBRUARY || month == Calendar.MAY
                || month == Calendar.AUGUST || month == Calendar.NOVEMBER) {// 季度第二个月
            day = getDayOfMonth(seasonDates[0])
                    + getPassDayOfMonth(seasonDates[1]);
        } else if (month == Calendar.MARCH || month == Calendar.JUNE
                || month == Calendar.SEPTEMBER || month == Calendar.DECEMBER) {// 季度第三个月
            day = getDayOfMonth(seasonDates[0]) + getDayOfMonth(seasonDates[1])
                    + getPassDayOfMonth(seasonDates[2]);
        }
        return day;
    }

    /**
     * 取得季度月
     *
     * @param date
     * @return
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason == 1) {// 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {// 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {// 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {// 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     */
    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }


    //用来全局控制 上一周，本周，下一周的周数变化
    private int weeks = 0;

    // 获得当前日期与本周日相差的天数
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;         //因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    /**
     * 获得下周星期一的日期
     *
     * @return
     */
    public static Date getNextMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String preMonday = df.format(monday);
        return formatDate(preMonday);
    }

    /**
     * 获得下周星期日的日期
     *
     * @return
     */
    public static Date getNextSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
        Date monday = currentDate.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String preMonday = df.format(monday);
        return formatDate(preMonday);
    }


    /**
     * 获取下个月的第一天
     *
     * @return
     */
    public static Date getNextMonthFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);// 当前月＋1，即下个月
        cal.set(Calendar.DATE, 1);// 将下个月1号作为日期初始值
        String currentMonth = df.format(cal.getTime());
        return formatDate(currentMonth);
    }

    /**
     * 获取下个月的最后一天
     *
     * @return
     */
    public static Date getNextMonthLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);// 将下2个月1号作为日期初始值
        cal.add(Calendar.MONTH, 2);// 当前月＋2，即下2个月
        cal.add(Calendar.DATE, -1);// 下2个月1号减去一天，即得到下1一个月最后一天
        String currentMonth = df.format(cal.getTime());
        return formatDate(currentMonth);
    }

    /***
     * 获取上个月的今天
     * @return
     */
    public static String getNowOfLastMonth() {
        // Date Format will be display
        SimpleDateFormat aSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar aGregorianCalendar = new GregorianCalendar();
        // Get last month GregorianCalendar object
        aGregorianCalendar.set(Calendar.MONTH, aGregorianCalendar
                .get(Calendar.MONTH) - 1);
        // Format the date to get year and month
        String nowOfLastMonth = aSimpleDateFormat
                .format(aGregorianCalendar.getTime());
        return nowOfLastMonth;
    }

    /***
     * 获取上个月
     * @return
     */
    public static String getLastMonth() {
        // Date Format will be display
        SimpleDateFormat aSimpleDateFormat = new SimpleDateFormat("yyyy-MM");
        GregorianCalendar aGregorianCalendar = new GregorianCalendar();
        // Get last month GregorianCalendar object
        aGregorianCalendar.set(Calendar.MONTH, aGregorianCalendar
                .get(Calendar.MONTH) - 1);
        // Format the date to get year and month
        String nowOfLastMonth = aSimpleDateFormat
                .format(aGregorianCalendar.getTime());
        return nowOfLastMonth;
    }

    /**
     * 获取去年的今天
     *
     * @return
     */
    public static String getNowOfLastYear() {
        // Date Format will be display
        SimpleDateFormat aSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar aGregorianCalendar = new GregorianCalendar();
        // Get last month GregorianCalendar object
        aGregorianCalendar.set(Calendar.YEAR, aGregorianCalendar
                .get(Calendar.YEAR) - 1);
        // Format the date to get year and month
        String currentYearAndMonth = aSimpleDateFormat
                .format(aGregorianCalendar.getTime());
        return currentYearAndMonth;
    }

    /**
     * 获取指定时间段内所有的月份
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getMonthTimePeriod(String startDate, String endDate) {
        List<String> monthList = new ArrayList<>();
        try {
            Date d1 = new SimpleDateFormat("yyyy-MM").parse(startDate);//定义起始日期
            Date d2 = new SimpleDateFormat("yyyy-MM").parse(endDate);//定义结束日期
            Calendar dd = Calendar.getInstance();//定义日期实例
            dd.setTime(d1);//设置日期起始时间
            while (dd.getTime().before(d2)) {//判断是否到结束日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                String str = sdf.format(dd.getTime());
                monthList.add(str);
                dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
            }
        } catch (Exception e) {
            monthList = null;
//            log.error("getMonth error", e);
        }
        return monthList;
    }


    public static void main(String args[]) {
//        System.out.println("当前日期：" + new DateTime(new Date()).toString("yyyy-MM-dd"));
//        System.out.println("当前的日期：" + Arrays.asList(true, false));


        String strDate = "2013-01-01";

        Date date = parseDate(strDate);

        System.out.println(strDate + " 今天是哪一年？" + getYear(date));
        System.out.println(strDate + " 今天是哪个月？" + getMonth(date));
        System.out.println(strDate + " 今天是几号？" + getDay(date));
        System.out.println(strDate + " 今天是周几？" + getWeekDay(date));
        System.out.println(strDate + " 是一年的第几周？" + getWeekOfYear(date));
        System.out.println(strDate + " 所在周起始结束日期？"
                + getWeekBeginAndEndDate(date, "yyyy年MM月dd日"));
        System.out.println(strDate + " 所在周周一是？"
                + formatDate(getMondayOfWeek(date)));
        System.out.println(strDate + " 所在周周日是？"
                + formatDate(getSundayOfWeek(date)));

        System.out.println(strDate + " 当月第一天日期？"
                + formatDate(getFirstDateOfMonth(date)));
        System.out.println(strDate + " 当月最后一天日期？"
                + formatDate(getLastDateOfMonth(date)));
        System.out.println(strDate + " 当月天数？" + getDayOfMonth(date));
        System.out.println(strDate + " 当月已过多少天？" + getPassDayOfMonth(date));
        System.out.println(strDate + " 当月剩余多少天？" + getRemainDayOfMonth(date));

        System.out.println(strDate + " 所在季度第一天日期？"
                + formatDate(getFirstDateOfSeason(date)));
        System.out.println(strDate + " 所在季度最后一天日期？"
                + formatDate(getLastDateOfSeason(date)));
        System.out.println(strDate + " 所在季度天数？" + getDayOfSeason(date));
        System.out.println(strDate + " 所在季度已过多少天？" + getPassDayOfSeason(date));
        System.out
                .println(strDate + " 所在季度剩余多少天？" + getRemainDayOfSeason(date));
        System.out.println(strDate + " 是第几季度？" + getSeason(date));
        System.out.println(strDate + " 所在季度月份？"
                + formatDate(getSeasonDate(date)[0], "yyyy年MM月") + "/"
                + formatDate(getSeasonDate(date)[1], "yyyy年MM月") + "/"
                + formatDate(getSeasonDate(date)[2], "yyyy年MM月"));

    }
}
