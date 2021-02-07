package com.sie.saaf.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BigDecimalUtils {

    public static final int MONEY_POINT = 2; // 货币保留两位小数

    /**
     * 格式化精度
     *
     * @param v
     * @param point
     *            小数位数
     * @return double
     */
    public static Double format(double v, int point) {
        BigDecimal b = new BigDecimal(v);
        return b.setScale(point, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double formatDouble(double d,int point) {
        // 旧方法，已经不再推荐使用
        // BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(d).setScale(point, RoundingMode.UP);
        return bg.doubleValue();
    }

    /**
     *
     * @param v
     * @param point
     * @return
     */
    public static Double formatRoundUp(double v, int point) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setRoundingMode(RoundingMode.HALF_UP);//设置四舍五入
        nf.setMinimumFractionDigits(point);//设置最小保留几位小数
        nf.setMaximumFractionDigits(point);//设置最大保留几位小数
        return Double.valueOf(nf.format(v));
    }

    /**
     * 格式化金额。带千位符
     *
     * @param v
     * @return
     */
    public static String moneyFormat(Double v) {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(3);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(v.doubleValue());
    }

    /**
     * 带小数的显示小数。不带小数的显示整数
     * @param d
     * @return
     */
    public static String doubleTrans(Double d) {
        if (Math.round(d) - d == 0) {
            return String.valueOf((long) d.doubleValue());
        }
        return String.valueOf(d);
    }

    public static double abs(double v){
        BigDecimal bigDecimal = new BigDecimal(v);
        return  bigDecimal.abs().doubleValue();
    }
    /**
     * BigDecimal 相加
     *
     * @param v1
     * @param v2
     * @return double
     */
    public static Double add(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.add(n2).doubleValue();
    }

    /**
     * BigDecimal 相减
     *
     * @param v1
     * @param v2
     * @return double
     */
    public static Double subtract(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.subtract(n2).doubleValue();
    }

    /**
     * BigDecimal 相乘
     *
     * @param v1
     * @param v2
     * @return double
     */
    public static Double multiply(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.multiply(n2).doubleValue();
    }

    /**
     * BigDecimal 相除
     *
     * @param v1
     * @param v2
     * @return double
     */
    public static Double divide(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.divide(n2, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double divide(double v1, double v2,int  scale) {
        if (scale < 0 ){
            throw   new  IllegalArgumentException(
                    " The scale must be a positive integer or zero " );
        }
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.divide(n2, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 比较大小 小于0：v1 < v2 大于0：v1 > v2 等于0：v1 = v2
     * @param v1
     * @param v2
     * @return
     */
    public static int compare(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.compareTo(n2);
    }

    /**
     * BigDecimal  相乘
     * @param rateFcssal
     * @param feeIntax
     * @return
     */
    public static BigDecimal multiply(BigDecimal rateFcssal,BigDecimal feeIntax){
        BigDecimal rateFcssal1 = rateFcssal == null?BigDecimal.ZERO:rateFcssal;
        BigDecimal feeIntax1 = feeIntax == null?BigDecimal.ZERO:feeIntax;
        return rateFcssal1.multiply(feeIntax1);
    }

    /**
     * BigDecimal相加
     * @param contratValue1
     * @param contratValue2
     * @return
     */
    public static BigDecimal add(BigDecimal contratValue1,BigDecimal contratValue2) {
        contratValue1 = contratValue1 == null ? BigDecimal.ZERO: contratValue1;
        contratValue2 = contratValue2 == null ? BigDecimal.ZERO: contratValue2;
        return contratValue1.add(contratValue2);
    }

    public static BigDecimal dealNull(BigDecimal value){
        BigDecimal returnValue =  value == null ? BigDecimal.ZERO : value;
        return returnValue;
    }

    public static BigDecimal doubleToBigDecimal(Double v) {
        BigDecimal n1 = new BigDecimal(Double.toString(v));
        return n1;
    }

}
