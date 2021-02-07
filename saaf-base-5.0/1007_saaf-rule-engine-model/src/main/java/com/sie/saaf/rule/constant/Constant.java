package com.sie.saaf.rule.constant;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Admin on 2017/6/28.
 */
public class Constant {
    public static final SimpleDateFormat DATEFORMAT_DATE_SEQ = new SimpleDateFormat("yyyyMMdd");
    public static final String BUSINESSLINE_CODE = "BL_";
    public static final String WEBSERVICE_CODE = "WS_SEQ_";
    public static final String BUSINESSLINE_SEQ = "BUSINESSLINE_SEQ";
    public static final String MODELCODE_SEQ = "MDC_";

    public static final Map<String, String> BUSINESSLINE_MAPPTYPE = new HashMap<>();
    public static final Map<String, String> RULEVIEW_MAPPTYPE = new HashMap<>();
    public static final Map<String, String> DIMVALUEFROM_MAPPTYPE = new HashMap<>();
    public static final Map<String, String> DIMDATA_MAPPTYPE = new HashMap<>();
    public static final Map<String, String> TARGET_MAPPTYPE = new HashMap<>();

    static {
//        BUSINESSLINE_MAPPTYPE.put("random", "随机匹配");
        BUSINESSLINE_MAPPTYPE.put("all", "全部匹配");
        BUSINESSLINE_MAPPTYPE.put("weight", "权重匹配");
        BUSINESSLINE_MAPPTYPE.put("businessPoint", "业务点匹配");
//        BUSINESSLINE_MAPPTYPE.put("dimSize", "优选匹配");

        RULEVIEW_MAPPTYPE.put("inputText", "inputText");
        RULEVIEW_MAPPTYPE.put("inputArea", "inputArea");
        RULEVIEW_MAPPTYPE.put("inputDate", "inputDate");
        RULEVIEW_MAPPTYPE.put("listOfValue", "listOfValue");
        RULEVIEW_MAPPTYPE.put("selectOneChoice", "selectOneChoice");
        RULEVIEW_MAPPTYPE.put("selectManyBox", "selectManyBox");

        DIMVALUEFROM_MAPPTYPE.put("webservice", "webservice");
        DIMVALUEFROM_MAPPTYPE.put("redis", "redis");
        DIMVALUEFROM_MAPPTYPE.put("sql", "sql");

        DIMDATA_MAPPTYPE.put("String", "String");
        DIMDATA_MAPPTYPE.put("Double", "Double");
        DIMDATA_MAPPTYPE.put("Integer", "Integer");
        DIMDATA_MAPPTYPE.put("Date", "Date");

        TARGET_MAPPTYPE.put("serviceURL", "serviceURL");
        TARGET_MAPPTYPE.put("confirmed", "confirmed");
    }


}
