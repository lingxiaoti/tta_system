package com.sie.saaf.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportGroupHeaderEntity_HI;

import java.util.List;

public interface IBaseReportGroupHeader {

	List<BaseReportGroupHeaderEntity_HI> findBaseReportGroupHeaderInfo(JSONObject queryParamJSON);

	Object saveBaseReportGroupHeaderInfo(JSONObject queryParamJSON);

	BaseReportGroupHeaderEntity_HI saveOrUpdatetGroupHeader(JSONObject paramsJSON, int userId) throws Exception;
}
