package com.sie.watsons.base.poc.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.poc.model.entities.XxPromStoreEntity_HI;

import java.util.List;

public interface IXxPromStore extends IBaseCommon<XxPromStoreEntity_HI> {

	List<XxPromStoreEntity_HI> findXxPromStoreInfo(JSONObject queryParamJSON);

	Object saveXxPromStoreInfo(JSONObject queryParamJSON);

}
