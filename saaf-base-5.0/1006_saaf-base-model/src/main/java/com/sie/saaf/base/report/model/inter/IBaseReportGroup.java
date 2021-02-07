package com.sie.saaf.base.report.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportGroupEntity_HI;
import com.sie.saaf.base.report.model.entities.readonly.BaseReportHeaerDatasourceEntity_HI_RO;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

public interface IBaseReportGroup extends IBaseCommon<BaseReportGroupEntity_HI> {

	BaseReportGroupEntity_HI saveOrUpdateReportGroup(JSONObject group, JSONArray headers, UserSessionBean userSessionBean) throws Exception;

	List<BaseReportHeaerDatasourceEntity_HI_RO> deleteAndFindReportGroupDetail(Integer groupId);

	void deleteReportHeaderInGroup(Integer reportGroupId, String[] ids);

	void delete(String[] ids);
}
