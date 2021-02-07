package com.sie.watsons.base.plmBase.model.inter;

import java.rmi.ServerException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.PlmOnlineSubrouteEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmOnlineSubrouteEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmOnlineSubroute extends
		IBaseCommon<PlmOnlineSubrouteEntity_HI> {

	Pagination<PlmOnlineSubrouteEntity_HI_RO> findPlmOnlineSubrouteInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmOnlineSubrouteEntity_HI> savePlmOnlineSubrouteInfo(
			JSONObject queryParamJSON) throws ServerException;

	Integer deletePlmOnlineSubrouteInfo(JSONObject queryParamJSON);

	String updatePlmOnlineSubrouteInfo(JSONObject parseObject);

}
