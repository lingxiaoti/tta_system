package com.sie.watsons.base.product.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.product.model.entities.PlmDataAclHeaderEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmDataAclHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmDataAclHeader extends
		IBaseCommon<PlmDataAclHeaderEntity_HI> {

	Pagination<PlmDataAclHeaderEntity_HI_RO> FindDataAclInfo(JSONObject param,
			Integer pageIndex, Integer pageRows);

	JSONObject DataAclInfo(JSONObject param);

    JSONObject saveHeadInfo(JSONObject param);
}
