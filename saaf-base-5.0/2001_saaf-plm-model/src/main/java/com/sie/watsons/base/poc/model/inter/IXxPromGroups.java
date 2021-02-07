package com.sie.watsons.base.poc.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.poc.model.entities.XxPromGroupsEntity_HI;

import java.util.List;

public interface IXxPromGroups extends IBaseCommon<XxPromGroupsEntity_HI> {

	List<XxPromGroupsEntity_HI> findXxPromGroupsInfo(JSONObject queryParamJSON);

	Object saveXxPromGroupsInfo(JSONObject queryParamJSON);

}
