package com.sie.watsons.base.product.model.inter;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.product.model.entities.PlmProductBpmPersonEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductBpmPersonEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmProductBpmPerson extends
		IBaseCommon<PlmProductBpmPersonEntity_HI> {

	Pagination<PlmProductBpmPersonEntity_HI_RO> FindProductProcessList(
			JSONObject param, Integer pageIndex, Integer pageRows);

	List<BaseUsersPerson_HI_RO> findProccessUsers(JSONObject queryParamJSON);

	List<BaseUsersPerson_HI_RO> findProccessUsersByuserId(
			JSONObject queryParamJSON);

	List<BaseUsersPerson_HI_RO> findCurenPerson(JSONObject queryParamJSON);

	List<BaseUsersPerson_HI_RO> findUserProcess(JSONObject queryParamJSON);

}
