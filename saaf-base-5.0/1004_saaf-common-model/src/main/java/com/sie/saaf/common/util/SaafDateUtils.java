package com.sie.saaf.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 比较两个日期区间工具类
 * 是否相同checkSameEdge，
 * 重叠contain，
 *
 * @author luofenwu
 */
public class SaafDateUtils {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static Logger logger = Logger.getLogger(SaafDateUtils.class);
    /**
     * 字符串转日期类型
     *
     * @param dateStr 日期字符串
     * @param formatStr 格式化表达式
     * @return Date
     */
    public static Date string2UtilDate(String dateStr, String formatStr) {
        if (null == formatStr) {
            formatStr = "yyyy-MM-dd";
        }
        DateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return date;
    }

    /**
     * 字符串转日期类型
     *
     * @param dateStr 日期字符串
     * @return Date
     */
    public static Date string2UtilDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        String formatStr = "";
        if (dateStr.length() == 10) {
            formatStr = "yyyy-MM-dd";
        }
        if(StringUtils.isBlank(formatStr)){
            formatStr = DEFAULT_PATTERN;
        }
        return string2UtilDate(dateStr, formatStr);
    }

    /**
     * 将日期转换为带时间的字符串格式
     * @param date 日期
     * @return 字符串日期:yyyy-MM-dd HH:mm:ss
     * @author ZhangJun
     * @createTime 2018/3/14
     * @description 将日期转换为String格式
     */
    public static String convertDateToString(Date date){
        if(date == null){
            return null;
        }
        return convertDateToString(date,DEFAULT_PATTERN);
    }

    /**
     * 将日期转换为字符串格式
     * @param date 日期
     * @param pattern 格式
     * @return 字符串日期
     * @author ZhangJun
     * @createTime 2018/3/14
     * @description 将日期转换为String格式
     */
    public static String convertDateToString(Date date,String pattern){
        if(date==null){
            return null;
        }
        if(StringUtils.isBlank(pattern)){
            pattern = DEFAULT_PATTERN;
        }
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 获取日期(获取当天日期getDate(0))
     * @param day 天数
     * @return 返回增加day天后的日期
     * @author ZhangJun
     * @createTime 2018/3/15
     * @description 获取日期(获取当天日期getDate(0))
     */
    public static Date getDate(int day) {
        Calendar cal = getCalendar(new Date());
        cal.add(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取后几天日期
     * @param date 指定日期
     * @param day 增加的天数
     * @return 指定日期增加day天后的日期
     * @author ZhangJun
     * @createTime 2018/3/15
     * @description 获取后面第几天
     */
    public static Date getNextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 取得指定日期的Calendar对象
     * @param date 日期
     * @return 指定日期的Calendar对象
     * @author ZhangJun
     * @createTime 2018/3/15
     * @description 取得当前日期的Calendar对象
     */
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    
    /**
     * 比较两个日期是不是在同一个自然天内
     *
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return true/false
     */
    public static boolean checkSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyyMMdd");
        return formatTime.format(date1).equals(formatTime.format(date2));
    }

    // 比较两个日期相差的天数 相对日期来查

    public static long getDistDatesExt(Date startDate, Date endDate) {
        long totalDate = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStartString = format.format(startDate);
        String dateEndString = format.format(endDate);
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        sb1.append(dateStartString).append(" 00:00:00");
        sb2.append(dateEndString).append(" 00:00:00");
        try {
            startDate = sdf.parse(sb1.toString());
            endDate = sdf.parse(sb2.toString());
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        long timestart = startDate.getTime();
        long timeend = endDate.getTime();
        totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);

        logger.info(startDate + " 与 " + endDate + "相差天数================" + totalDate);
        return totalDate;
    }

    public static class DateInterval {
        private Date startDate;
        private Date endDate;
        private List<Date> intervalDateList = new ArrayList<Date>(); //时间区间内的日期list

        private DateInterval(Date startDate, Date endDate) {
            super();
            this.startDate = startDate;
            this.endDate = endDate;
            this.init();
        }

        private void init() {
            if (startDate == null || endDate == null) {
                throw new IllegalArgumentException("params error:startDate and endDate can not be null!    startDate和endDate参数不能为空");
            }
            if (startDate.after(endDate)) {
                throw new IllegalArgumentException("params error：startDate can not afeter endDate!please check your params!  startDate不能在endDate 之后");
            }
            intervalDateList.add(startDate);
            long distDatesExt = getDistDatesExt(startDate, endDate);
            Calendar c = Calendar.getInstance();
            for (int i = 1; i < distDatesExt; i++) {
                c.setTime(startDate);
                c.add(Calendar.DAY_OF_MONTH, i);
                intervalDateList.add(c.getTime());
            }
            intervalDateList.add(endDate);
        }

        public Date getStartDate() {
            return startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public List<Date> getIntervalDateList() {
            return intervalDateList;
        }

        /**
         * this区间是否与参数区间重叠
         *
         * @param dateInterval DateInterval对象
         * @return true/false
         */
        public boolean contain(DateInterval dateInterval) {
            List<Date> thisIntervalDate = this.getIntervalDateList();
            List<Date> otherIntervalDate = dateInterval.getIntervalDateList();
            for (Date thisD : thisIntervalDate) {
                for (Date otherD : otherIntervalDate) {
                    if (checkSameDay(thisD, otherD)) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * this区间与参数区间是否相同
         *
         * @param dateInterval DateInterval对象
         * @return true/false
         */
        public boolean checkSameEdge(DateInterval dateInterval) {
            return (checkSameDay(this.getStartDate(), dateInterval.getStartDate()) && checkSameDay(this.getEndDate(), dateInterval.getEndDate()));
        }

        /**
         * 判断两个DateInterval单元的首尾端点是否相隔指定时间
         *
         * @param dateInterval DateInterval对象
         * @param intervalDay  两个日期相隔几天
         * @return true/false
         */
        public boolean edgeInvCheck(DateInterval dateInterval, int intervalDay) {
            if (getDistDatesExt(this.getEndDate(), dateInterval.getStartDate()) < intervalDay) {
                return false;
            }

            if (getDistDatesExt(this.getStartDate(), dateInterval.getEndDate()) < intervalDay) {
                return false;
            }
            return true;
        }
    }

    public static void print(List<DateInterval> list) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        logger.info("print begin=======================================");
        for (DateInterval di : list) {
            for (Date d : di.getIntervalDateList()) {
                logger.info("di:" + df.format(d));
            }
        }
        logger.info("print end========================================");
    }

    /***
     * 判断时间区间是否重叠
     *
     * @param listTocheck 源有时间区间list
     * @param diToCheck   需要检验的时间区间
     * @return true/false
     */
    public static boolean checkListContain(List<DateInterval> listTocheck, DateInterval diToCheck) {
        boolean checkFlag = false;
        for (DateInterval di : listTocheck) {
            logger.info("是否同一区间checkSameEdge:" + diToCheck.checkSameEdge(di));
            logger.info("是否包含 contain:" + diToCheck.contain(di));
            logger.info("首尾端点是否相隔指定n天 edgeInvCheck:" + diToCheck.edgeInvCheck(di, 1));
            logger.info("unit check ret:" + (diToCheck.checkSameEdge(di) || (!diToCheck.contain(di) && diToCheck.edgeInvCheck(di, 1))));
            if (diToCheck.contain(di)) {
                checkFlag = true;
            }
        }
        return checkFlag;
    }

    /**
     * 判断两个时间区间是否重叠
     *
     * @param startDate1 区间1起始日期
     * @param endDate1   区间1终止日期
     * @param startDate2 区间2起始日期
     * @param endDate2   区间2终止日期
     * @return true重叠，false不重叠
     */
    public static boolean checkContain(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        if (endDate1 == null) {
            endDate1 = string2UtilDate("2099-12-31", null);
        }
        if (endDate2 == null) {
            endDate2 = string2UtilDate("2099-12-31", null);
        }
        DateInterval di = new DateInterval(startDate1, endDate1);
        DateInterval di2 = new DateInterval(startDate2, endDate2);
        logger.info(startDate1 + " 到 " + endDate1 + " 与 " + startDate2 + " 到 " + endDate2 + " 是否重叠======= " + di2.contain(di));
        return di2.contain(di);
    }

    /**
     * 获取两个月份区间中的中间月份
     *
     * @param oldMonth 201602
     * @param newMonth 201605
     * @return {201603,201604}
     * @throws ParseException 转换异常
     */
    public static List getFrom2Month(String oldMonth, String newMonth) throws ParseException {
        List list = new ArrayList();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        Date oldDate = df.parse(oldMonth);
        Date newDate = df.parse(newMonth);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.MONTH, 1);
        oldDate = calendar.getTime();
        while (oldDate.compareTo(newDate) < 0) {

            String s = df.format(oldDate);
            list.add(s);
            calendar.add(Calendar.MONTH, 1);
            oldDate = calendar.getTime();
        }

        return list;
    }

    /**
     * 比较两个时间的大小
     * @param day1Str 时间字符串1
     * @param day2Str 时间字符串2
     * @return -1：day1Str < day2Str;
     *          0: day1Str = day2Str;
     *          1: day1Str > day2Str;
     * @throws ParseException 抛出异常
     */
    public static int compare2Days(String day1Str, String day2Str, String pattern) throws ParseException {
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_PATTERN;
        }
        if (day2Str == null) {
            day2Str = convertDateToString(new Date());
        }
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        //将字符串形式的时间转化为Date类型的时间
        Date day1 = sdf.parse(day1Str);
        Date day2 = sdf.parse(day2Str);
        //Date类的一个方法，如果a早于b返回true，否则返回false
        return day1.compareTo(day2);
    }

    /**
     * 获取"yyyyMMddHHmmss"的格式字符串
     * @return
     */
    public static String getDateSeq() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String mDateTime = formatter.format(cal.getTime());
        return mDateTime;
    }


    /**
     * 功能描述：年份相加减，year 为负数表示年份相减，为正数表示年份相加
     * @author xiaoga
     * @date 2019/9/16
     * @param
     * @return
     */
    public static String dateDiffYear(String str, Integer year)  {
        String reStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date dt = sdf.parse(str);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.YEAR, year);// 日期加1年
            Date dt1 = rightNow.getTime();
            reStr = sdf.format(dt1);
        } catch (Exception e) {
            logger.info("转换日期出错：{}", e);
        }
        return reStr;
    }


    /**
     * 功能描述：月份相加减
     * @param
     * @return
     */
    public static String dateDiffMonth(String yearMonth, Integer month)  {
        String reStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            Date dt = sdf.parse(yearMonth);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.MONTH, month);
            Date dt1 = rightNow.getTime();
            reStr = sdf.format(dt1);
        } catch (Exception e) {
            logger.info("转换日期出错：{}", e);
        }
        return reStr;
    }

    public static String dateDiffDay(String yearMonthDay, Integer day)  {
        String reStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date dt = sdf.parse(yearMonthDay);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.DATE, day);
            Date dt1 = rightNow.getTime();
            reStr = sdf.format(dt1);
        } catch (Exception e) {
            logger.info("转换日期出错：{}", e);
        }
        return reStr;
    }
}
