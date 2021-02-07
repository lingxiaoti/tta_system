package com.sie.saaf.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportHeaderEntity_HI;
import com.sie.saaf.base.report.model.entities.readonly.BaseReportHeaerDatasourceEntity_HI_RO;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IBaseReportHeader extends IBaseCommon<BaseReportHeaderEntity_HI> {

	List<BaseReportHeaderEntity_HI> findBaseReportHeaderInfo(JSONObject queryParamJSON);

	BaseReportHeaderEntity_HI saveOrUpdateHeader(JSONObject paramsJSON, UserSessionBean userSessionBean) throws Exception;

	Pagination<JSONObject> findReportSql(String reportHeaderCode, JSONObject sessionBean, JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws SQLException;

	Pagination<JSONObject> executeNativeSql(Integer dsId, String sql, Map<String, Object> map, Integer pageIndex, Integer pageRows) throws SQLException;

	Pagination<JSONObject> executeNativeSql(Integer dsId, String sql, Integer pageIndex, Integer pageRows, Object... args) throws SQLException;

	String replaceSystemVariable(String sql, JSONObject sessionBean);

	Pagination<BaseReportHeaerDatasourceEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	void delete(String[] ids);
}
