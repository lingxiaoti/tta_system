package com.sie.saaf.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportDatasourceEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.SQLException;
import java.util.List;

public interface IBaseReportDatasource extends IBaseCommon<BaseReportDatasourceEntity_HI> {

	List<BaseReportDatasourceEntity_HI> findBaseReportDatasourceInfo(JSONObject queryParamJSON);

	BaseReportDatasourceEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

	Pagination<BaseReportDatasourceEntity_HI> findBaseUserSystemInfoWithPage(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	BasicDataSource getDatasource(Integer dsId) throws SQLException;
}
