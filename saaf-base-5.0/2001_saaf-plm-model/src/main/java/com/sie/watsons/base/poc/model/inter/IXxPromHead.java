package com.sie.watsons.base.poc.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.poc.model.entities.XxPromHeadEntity_HI;

import java.util.List;

public interface IXxPromHead extends IBaseCommon<XxPromHeadEntity_HI> {

	List<XxPromHeadEntity_HI> findXxPromHeadInfo(JSONObject queryParamJSON);

	Object saveXxPromHeadInfo(JSONObject queryParamJSON);

}
