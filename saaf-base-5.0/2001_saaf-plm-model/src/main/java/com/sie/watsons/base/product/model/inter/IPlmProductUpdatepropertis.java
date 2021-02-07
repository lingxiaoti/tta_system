package com.sie.watsons.base.product.model.inter;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.product.model.entities.PlmProductUpdatepropertisEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductUpdatepropertisEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmProductUpdatepropertis extends
		IBaseCommon<PlmProductUpdatepropertisEntity_HI> {

	List<PlmProductUpdatepropertisEntity_HI> findListAll(
			JSONObject queryParamJSON);

	Pagination<PlmProductUpdatepropertisEntity_HI_RO> findProductUpdateList(
			JSONObject param, Integer pageIndex, Integer pageRows);

	Pagination<PlmProductHeadEntity_HI_RO> FindUpdateByPropertiesList(
			JSONObject param, Integer pageIndex, Integer pageRows);

}
