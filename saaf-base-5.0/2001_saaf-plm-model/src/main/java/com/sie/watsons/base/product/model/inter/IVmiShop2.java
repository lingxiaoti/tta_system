package com.sie.watsons.base.product.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.product.model.entities.VmiShopEntity_HI2;
import com.sie.watsons.base.product.model.entities.readonly.VmiShopEntity_HI_RO2;
import com.yhg.hibernate.core.paging.Pagination;

public interface IVmiShop2 extends IBaseCommon<VmiShopEntity_HI2> {

	Pagination<VmiShopEntity_HI_RO2> findShopinfo(JSONObject queryParamJSON,
			Integer pageIndex, Integer pageRows);

	Pagination<VmiShopEntity_HI_RO2> findArea(JSONObject queryParamJSON,
			Integer pageIndex, Integer pageRows);

}
