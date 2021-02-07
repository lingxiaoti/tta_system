package com.sie.watsons.base.proposal.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Util {

    private static final Logger log = LoggerFactory.getLogger(Util.class);

    public static String[] IllegalSqlArr = {"\\s{0,}insert\\s+\\w+", "\\s{0,}update\\s+\\w+", "\\s{0,}delete\\s+\\w+", "\\s{0,}truncate\\s+\\w+", "\\s{0,}grant\\s+\\w+", "\\s{0,}alter\\s+\\w+", "\\s{0,}drop\\s+\\w+"};

    /**
     * 格式化数字为千分位显示；
     *
     * @param ；
     * @return
     */
    public static String fmtMicrometer(String text) {
        if (StringUtils.isBlank(text) || "null".equalsIgnoreCase(text)) {
            return null;
        }
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.0");
            } else {
                df = new DecimalFormat("###,##0.00");
            }
        } else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }


    /**
     * 功能描述： scal精确到多少位小数
     *
     * @return
     */
    public static String fmtMicrometer(String text, int scal, String charStr) {
        try {
            if (StringUtils.isBlank(text) || "null".equalsIgnoreCase(text)) {
                return null;
            }
            if (StringUtils.isBlank(charStr)) {
                charStr = "";
            }
            text = fmtMicrometer(new BigDecimal(text).setScale(scal, BigDecimal.ROUND_HALF_UP).toString()) + charStr;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return text;
    }

    public static String fmtMicrometer(String text, int scal) {
        return fmtMicrometer(text, scal,"");
    }

    /**
     * 功能描述： scal精确到多少位小数,roundingMode取值模式 如：BigDecimal.ROUND_HALF_UP
     *
     * @return
     */
    public static String fmtMicrometer(String text, int scal, int roundingMode) {
        try {
            if (StringUtils.isBlank(text) || "null".equalsIgnoreCase(text)) {
                return null;
            }
            text = fmtMicrometer(new BigDecimal(text).setScale(scal, roundingMode).toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return text;
    }

    /**
     * 功能描述： 是否非法sql语句
     *
     * @param
     * @return
     * @date 2019/9/1
     */
    public static boolean isIllegalSqlChar(String sqlValue) {
        boolean flag = false;
        if (StringUtils.isNotBlank(sqlValue)) {
            for (String str : IllegalSqlArr) {
                if (sqlValue.toLowerCase().trim().matches(str)) {
                    flag = true;
                    break;
                }
            }
        }
        log.info("校验sql是否含有非法字符,入参:{}, 校验结果:{}", sqlValue, flag);
        return flag;
    }

    /**
     * 输出异常信息
     * @param e
     * @return
     */
    public static String getErrorMsg (Exception e) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.fillInStackTrace().printStackTrace(printWriter);
        printWriter.close();
        return result.toString();
    }
}
