package com.sie.saaf.message.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

public class DateFomatUtil {
    public static void dateFormat(JSONObject jsonObject,String... timeCloums){
        for(String timeCloum:timeCloums){
            String endTime = jsonObject.getString(timeCloum);
            if(StringUtils.isNotBlank(endTime)){
                endTime = endTime + " 23:59:59";
                jsonObject.put(timeCloum,endTime);
            }
        }
    }
}
