package com.sie.watsons.base.poc.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.poc.model.entities.XxPromTypeDetailEntity_HI;

import java.util.List;

public interface IXxPromTypeDetail extends IBaseCommon<XxPromTypeDetailEntity_HI> {

	List<XxPromTypeDetailEntity_HI> findXxPromTypeDetailInfo(JSONObject queryParamJSON);

	Object saveXxPromTypeDetailInfo(JSONObject queryParamJSON);

}
