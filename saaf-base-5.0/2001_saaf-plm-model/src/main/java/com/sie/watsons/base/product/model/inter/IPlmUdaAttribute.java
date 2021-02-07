package com.sie.watsons.base.product.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.api.model.entities.PlmUdaAttributeEntity_HI;
import com.sie.watsons.base.api.model.entities.readonly.PlmUdaAttributeEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmUdaAttribute extends IBaseCommon<PlmUdaAttributeEntity_HI> {

	Pagination<PlmUdaAttributeEntity_HI_RO> findUdaById(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmUdaAttributeEntity_HI findByUdaDesc(String udsDesc);
}
