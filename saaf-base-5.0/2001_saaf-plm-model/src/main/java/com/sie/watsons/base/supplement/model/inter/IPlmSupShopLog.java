package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplement.model.entities.PlmSupShopEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupShopLogEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupShopLogEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.supplement.model.entities.PlmSupLogEntity_HI;

public interface IPlmSupShopLog extends IBaseCommon<PlmSupShopLogEntity_HI> {

    List<PlmSupShopLogEntity_HI> findPlmSupShopLogInfo(JSONObject queryParamJSON);

    Object savePlmSupShopLogInfo(JSONObject queryParamJSON);

    public List<PlmSupShopLogEntity_HI_RO> findShopList(JSONObject queryParamJSON);

}
