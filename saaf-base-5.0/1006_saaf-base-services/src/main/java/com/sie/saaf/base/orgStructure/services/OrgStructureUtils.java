package com.sie.saaf.base.orgStructure.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafDateUtils;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;

/**
 * @auther: huqitao 2018/8/2
 */
public class OrgStructureUtils {

    /**
     * 校验生效日期和失效日期
     * @param paramsJSON
     * {
     *    dateFrom：生效日期，
     *    dateTo：失效日期
     * }
     * @return 校验结果，同时给dateTo参数赋默认值
     */
    public static JSONObject checkDateFromAndDateTo(JSONObject paramsJSON){
        try {
            if (StringUtils.isNotBlank(paramsJSON.getString("keyId"))) {
                int dateFromCompareDateTo = SaafDateUtils.compare2Days(paramsJSON.getString("dateFrom"), paramsJSON.getString("dateTo"), "yyyy-MM-dd");
                if (dateFromCompareDateTo == 1) {
                    return getResultJson("E", "生效日期不能早于失效日期");
                }
            } else {
                int dateFromCompareNow = SaafDateUtils.compare2Days(paramsJSON.getString("dateFrom"), null, "yyyy-MM-dd");
                if (dateFromCompareNow == -1) {
                    return getResultJson("E", "生效日期不能早于当前日期");
                }
                if (StringUtils.isNotBlank(paramsJSON.getString("dateTo"))) {
                    int dateToCompareDateFrom = SaafDateUtils.compare2Days(paramsJSON.getString("dateTo"), paramsJSON.getString("dateFrom"), "yyyy-MM-dd");
                    if (dateToCompareDateFrom == -1) {
                        return getResultJson("E", "失效日期不能早于生效日期");
                    }
                } else {
                    paramsJSON.put("dateTo", "2099-12-31");
                }
            }
            paramsJSON.put("status", "S");
            paramsJSON.put("msg", "OK");
            return paramsJSON;
        } catch (ParseException e) {
            return getResultJson("E", "日期格式错误");
        }
    }

    public static JSONObject getResultJson(String resultStatus, String resultMessage){
        JSONObject json = new JSONObject();
        json.put("status", resultStatus);
        json.put("msg", resultMessage);
        return json;
    }
}
