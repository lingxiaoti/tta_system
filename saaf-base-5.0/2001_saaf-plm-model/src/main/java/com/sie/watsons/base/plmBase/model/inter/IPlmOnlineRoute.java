package com.sie.watsons.base.plmBase.model.inter;

import java.rmi.ServerException;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.PlmOnlineRouteEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmOnlineRouteEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmOnlineRoute extends IBaseCommon<PlmOnlineRouteEntity_HI> {

	Pagination<PlmOnlineRouteEntity_HI_RO> findPlmOnlineRouteInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmOnlineRouteEntity_HI savePlmOnlineRouteInfo(JSONObject queryParamJSON)
			throws ServerException;

}
