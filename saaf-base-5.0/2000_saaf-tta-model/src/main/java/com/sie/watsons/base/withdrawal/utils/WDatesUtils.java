package com.sie.watsons.base.withdrawal.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author hmb
 * @date 2019/8/14 12:15
 * 获取日期工具类
 */
public class WDatesUtils {

    /**
     * 判断是否是相同的年份
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static boolean isSameDate(String date1, String date2) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(sdf.parse(date1));
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(sdf.parse(date2));
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    /**
     * 判断是否是相同的年份
     * @param date1
     * @param date2
     * @param patern
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2, String patern){
        SimpleDateFormat sdf = new SimpleDateFormat(patern);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    };

    /**
     * 获取年份
     * @param date 日期
     * @param parten 匹配日期格式
     * @return
     */
    public static int getYear(Date date,String parten) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        return year;
    }


    /**
     * 通过日期字符串获取指定的年份
     * @param dateStr
     * @param parren
     * @return
     */
    public static int getYearByString(String dateStr,String parren) throws ParseException {
        Date date = new SimpleDateFormat(parren).parse(dateStr);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        //int day = ca.get(Calendar.DAY_OF_YEAR);//一年中的第几天
       // int week = ca.get(Calendar.WEEK_OF_YEAR);//一年中的第几周
        //int month = ca.get(Calendar.MONTH);//第几个月
        int year = ca.get(Calendar.YEAR);//年份数值
        return year;
    }

    /**
     * 根据指定的日期获取月份
     * @param dateStr
     * @param pattern
     * @return
     */
    public static int getMonth(String dateStr,String pattern) throws ParseException{
        Date date = new SimpleDateFormat(pattern).parse(dateStr);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int month = ca.get(Calendar.MONTH);
        return month;
    }

    /**
     * 获取传入日期所在月的第一天
     * @param date 指定的日期
     * @return
     */
    public static Date getFirstDayDateOfMonth(final Date date) {

        final Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, last);

        return cal.getTime();

    }

    /**
     * 获取传入日期所在月的最后一天
     * @param date 指定的日期
     * @return
     */
    public static Date getLastDayOfMonth(final Date date) {

        final Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, last);

        return cal.getTime();

    }

    /**
     * 获取指定日期的年
     * @param minDate
     * @param maxDate
     * @return
     * @throws Exception
     */
    public static List<Integer> getMonthBetween(Date minDate, Date maxDate)  throws Exception{
        ArrayList<Integer> result = new ArrayList<>();
        Calendar min = Calendar.getInstance();
        min.setTime(minDate);

        Calendar max = Calendar.getInstance();
        max.setTime(maxDate);

        Calendar curr = min;
        while (curr.get(Calendar.YEAR)<=max.get(Calendar.YEAR)) {
            result.add(curr.get(Calendar.YEAR));
            curr.add(Calendar.YEAR, 1);
        }

        return result;
    }

    public static List<String> getAllMonth(Date date) throws ParseException {
        List<String> dateList = new ArrayList<>();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int month = ca.get(Calendar.MONTH) + 1;//第几个月
        int year = ca.get(Calendar.YEAR);//年份数值
        for (int i = 1; i <= month; i++) {
            String periodMonth = "";
            if (i >= 10) {
                periodMonth = year + "" + i;
            } else {
                periodMonth = year + "0" + i;
            }
            dateList.add(periodMonth);
        }
        return dateList;
    }

    /**
     * 获取两个月份区间中的中间月份
     * @param oldMonth 201602
     * @param newMonth 201605
     * @return {201602,201603,201604,201605}
     * @throws ParseException 转换异常
     */
    public static List<String> getFrom2Month(String oldMonth, String newMonth, String dateFormat) throws ParseException {
        List list = new ArrayList();
        if (StringUtils.isBlank(dateFormat)) {
            dateFormat = "yyyyMM";
        }
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        Date oldDate = df.parse(oldMonth);
        Date newDate = df.parse(newMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        oldDate = calendar.getTime();
        while (oldDate.compareTo(newDate) <= 0) {
            String s = df.format(oldDate);
            list.add(s);
            calendar.add(Calendar.MONTH, 1);
            oldDate = calendar.getTime();
        }
        return list;
    }
    /**
     * 功能描述： 默认入参是yyyyMM格式
     * @author xiaoga
     * @date 2019/8/27
     * @param
     * @return
     */
    public static List<String> getFrom2Month(String oldMonth, String newMonth) throws ParseException {
        return getFrom2Month(oldMonth, newMonth, null);
    }

    public static String getAddMonth(String month,String dateFormat) throws ParseException {
        if (StringUtils.isBlank(dateFormat)){
            dateFormat = "yyyyMM";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date monthDate = sdf.parse(month);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(monthDate);
        calendar.add(Calendar.MONTH, 1);
        monthDate = calendar.getTime();
        return sdf.format(monthDate);
    }


    public static String tranferDateFormat(String yearMonth) throws ParseException {
        if (StringUtils.isBlank(yearMonth)) {
            return null;
        }
        SimpleDateFormat betweenDateFormat = new SimpleDateFormat("yyyy-MM");
        Date yearMonthDate = betweenDateFormat.parse(yearMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
       return simpleDateFormat.format(yearMonthDate);
    }

   public static int getMonthsBetween(String startDate,String endDate){
       DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMM");
       DateTime start = formatter.parseDateTime(startDate);
       DateTime end = formatter.parseDateTime(endDate);
       int months = Months.monthsBetween(end, start).getMonths();
       return Math.abs(months) + 1;
   }

    public static void main(String[] args) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date date = format.parse("201908");//201908
        Date endData = format.parse("202007");
        String yyyyMM = new SimpleDateFormat("yyyyMM").format(date);

        Calendar ca = Calendar.getInstance();
        ca.setTime(date);//起始月
        Calendar endCa = Calendar.getInstance();
        endCa.setTime(endData);//结束月

        int day = ca.get(Calendar.DAY_OF_YEAR);//一年中的第几天
        System.out.println("一年中的第几天:" + day);
        int week = ca.get(Calendar.WEEK_OF_YEAR);//一年中的第几周
        System.out.println("一年中的第几周:" + week);
        int month = ca.get(Calendar.MONTH);//第几个月
        int endMonth = endCa.get(Calendar.MONTH);
        System.out.println(endMonth);
        System.out.println("一年中的第几个月:" + (month + 1));
        int year = ca.get(Calendar.YEAR);//年份数值
        System.out.println("获取年份:" + year);

        System.out.println("**************************************************************");
        System.out.println("测试跨年月份");
        //List<String> from2Month = getFrom2Month("20", "202003");
        List<String> from2Month = getFrom2Month("201901", "201912");
        System.out.println(from2Month);
        System.out.println("测试月份");
        String addMonth = getAddMonth("201908", null);
        System.out.println(addMonth);


        System.out.println("计算两个日期的时间差");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMM");
        DateTime start = formatter.parseDateTime("201911");
        DateTime end = formatter.parseDateTime("201903");
        int months = Months.monthsBetween(end, start).getMonths();
        System.out.println(months);
        //取绝对值
        int abs = Math.abs(months);
        System.out.println(abs);

    }


}
