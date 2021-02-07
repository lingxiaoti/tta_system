package com.sie.watsons.base.product.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.product.model.entities.PlmProductSupplierplaceinfoEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductSupplierplaceinfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmProductSupplierplaceinfo extends
		IBaseCommon<PlmProductSupplierplaceinfoEntity_HI> {

	Pagination<PlmProductSupplierplaceinfoEntity_HI_RO> FindSupplierPlaceInfo(
			JSONObject param, Integer pageIndex, Integer pageRows);

}
