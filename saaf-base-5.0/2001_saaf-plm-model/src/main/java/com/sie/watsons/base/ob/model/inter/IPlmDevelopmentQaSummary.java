package com.sie.watsons.base.ob.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentQaSummaryEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentQaSummaryEntity_HI;

public interface IPlmDevelopmentQaSummary extends IBaseCommon<PlmDevelopmentQaSummaryEntity_HI> {


	Pagination<PlmDevelopmentQaSummaryEntity_HI_RO> findPlmDevelopmentQaSummaryInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmDevelopmentQaSummaryEntity_HI> savePlmDevelopmentQaSummaryInfo(JSONObject queryParamJSON);

	List<PlmDevelopmentQaSummaryEntity_HI> savePlmDevelopmentQaSummaryInfoByCategory(JSONObject queryParamJSON);

	Integer deletePlmDevelopmentQaSummaryInfo(JSONObject queryParamJSON);

}
