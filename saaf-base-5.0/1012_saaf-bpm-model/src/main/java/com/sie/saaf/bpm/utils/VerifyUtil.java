package com.sie.saaf.bpm.utils;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.lang3.StringUtils;

public class VerifyUtil {
	
	public static void verifyJSON(JSONObject paramsJSON, String propName, boolean noEmpty, int maxLength, String tip) {
		if(!noEmpty && maxLength<1) {
			return;
		}
		String[] propNames = propName.indexOf("\\.")>-1 ? propName.split(".") : new String[] {propName};
		Object value = paramsJSON.get(propNames[0]);
		Assert.isTrue(noEmpty && value != null || !noEmpty, tip + "不能为空");
		if(value != null) {
			if(propNames.length == 1) {
				Assert.isTrue(noEmpty && StringUtils.isNotBlank(value.toString()) || !noEmpty, tip + "不能为空！");
				Assert.isTrue(maxLength < 1 || value.toString().length() <= maxLength, tip + "长度不能超过" + maxLength + "！");
			}else {
				verifyJSON((JSONObject) value, propName.substring(propName.indexOf(".") + 1), noEmpty, maxLength, tip);
			}
		}
	}

}
