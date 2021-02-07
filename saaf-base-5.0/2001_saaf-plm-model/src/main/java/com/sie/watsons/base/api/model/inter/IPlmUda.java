package com.sie.watsons.base.api.model.inter;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2020/1/10/010.
 */
public interface IPlmUda {
    public void updateItemSupp(JSONObject queryParamJSON) throws Exception;
    public void updateItemMaster(JSONObject queryParamJSON) throws Exception;
    public void updateUdaInformation(JSONObject queryParamJSON) throws Exception;
}
