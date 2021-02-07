package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.supplement.model.entities.PlmSupShopEntity_HI;

public interface IPlmSupShop extends IBaseCommon<PlmSupShopEntity_HI> {

	List<PlmSupShopEntity_HI> findPlmSupShopInfo(JSONObject queryParamJSON);

	Object savePlmSupShopInfo(JSONObject queryParamJSON);

}
