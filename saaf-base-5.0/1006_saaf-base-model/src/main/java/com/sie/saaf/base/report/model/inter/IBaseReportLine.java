package com.sie.saaf.base.report.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportHeaderEntity_HI;
import com.sie.saaf.base.report.model.entities.BaseReportLineEntity_HI;
import com.sie.saaf.base.report.model.entities.readonly.BaseReportLineEntity_HI_RO;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.SQLException;
import java.util.List;

public interface IBaseReportLine extends IBaseCommon<BaseReportLineEntity_HI> {

	List<BaseReportLineEntity_HI> findBaseReportLineInfo(JSONObject queryParamJSON);

	Pagination<BaseReportLineEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	Object saveBaseReportLineInfo(JSONObject queryParamJSON);

	List<BaseReportLineEntity_HI> saveReportLinesByDatasource(JSONObject params, UserSessionBean userSessionBean) throws SQLException;

	BaseReportLineEntity_HI saveOrUpdate(JSONObject params, UserSessionBean userSessionBean) throws Exception;

	List<BaseReportLineEntity_HI> saveOrUpdateList(JSONArray jsonArray, UserSessionBean userSessionBean) throws Exception;

	List<BaseReportLineEntity_HI> generateColumns(BasicDataSource basicDataSource, BaseReportHeaderEntity_HI header, UserSessionBean userSessionBean);

	void delete(String[] ids);
}
