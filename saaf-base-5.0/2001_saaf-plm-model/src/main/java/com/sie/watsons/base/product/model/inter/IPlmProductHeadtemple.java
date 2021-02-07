package com.sie.watsons.base.product.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.product.model.entities.PlmProductHeadtempleEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadtempleEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmProductHeadtemple extends
		IBaseCommon<PlmProductHeadtempleEntity_HI> {

	Pagination<PlmProductHeadtempleEntity_HI_RO> findProductTempList(
			JSONObject param, Integer pageIndex, Integer pageRows);

}
