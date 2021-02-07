package com.sie.watsons.base.product.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.product.model.entities.VmiWarehouseEntity_HI2;
import com.sie.watsons.base.product.model.entities.readonly.VmiWarehouseEntity_HI_RO2;
import com.yhg.hibernate.core.paging.Pagination;

public interface IVmiWarehouse2 extends IBaseCommon<VmiWarehouseEntity_HI2> {

	Pagination<VmiWarehouseEntity_HI_RO2> findWarehouseinfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

}
