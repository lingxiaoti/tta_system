package com.sie.watsons.base.ob.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentQaDetailEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentQaDetailEntity_HI;

public interface IPlmDevelopmentQaDetail extends IBaseCommon<PlmDevelopmentQaDetailEntity_HI> {

	Pagination<PlmDevelopmentQaDetailEntity_HI_RO> findPlmDevelopmentQaDetailInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmDevelopmentQaDetailEntity_HI> savePlmDevelopmentQaDetailInfo(JSONObject queryParamJSON) throws Exception;

	Integer deletePlmDevelopmentQaDetailInfo(JSONObject queryParamJSON) throws Exception;
}
