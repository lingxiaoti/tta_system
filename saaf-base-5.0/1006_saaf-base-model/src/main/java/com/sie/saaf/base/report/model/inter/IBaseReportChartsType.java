package com.sie.saaf.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportChartsTypeEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

public interface IBaseReportChartsType extends IBaseCommon<BaseReportChartsTypeEntity_HI> {

	List<BaseReportChartsTypeEntity_HI> findBaseReportChartsTypeInfo(JSONObject queryParamJSON);

	BaseReportChartsTypeEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;
}
