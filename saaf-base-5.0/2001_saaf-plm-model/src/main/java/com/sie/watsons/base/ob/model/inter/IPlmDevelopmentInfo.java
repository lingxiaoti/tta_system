package com.sie.watsons.base.ob.model.inter;

import java.rmi.ServerException;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentInfoEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentInfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmDevelopmentInfo extends
		IBaseCommon<PlmDevelopmentInfoEntity_HI> {

	Pagination<PlmDevelopmentInfoEntity_HI_RO> findPlmDevelopmentInfoInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmDevelopmentInfoEntity_HI savePlmDevelopmentInfoInfo(
			JSONObject queryParamJSON) throws ServerException;

    Pagination<PlmDevelopmentInfoEntity_HI_RO> findPlmDevelopmentInfoInfoNew(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
