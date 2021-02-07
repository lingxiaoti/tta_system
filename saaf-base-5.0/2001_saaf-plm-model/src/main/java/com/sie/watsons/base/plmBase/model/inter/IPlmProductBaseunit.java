package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.PlmProductBaseunitEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmProductBaseunitEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmProductBaseunit extends
		IBaseCommon<PlmProductBaseunitEntity_HI> {

	Pagination<PlmProductBaseunitEntity_HI_RO> findPlmProductBaseunit(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

}
