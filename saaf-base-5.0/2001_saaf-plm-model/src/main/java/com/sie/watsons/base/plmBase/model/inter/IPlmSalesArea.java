package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSalesAreaEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmSalesAreaEntity_HI;

public interface IPlmSalesArea extends IBaseCommon<PlmSalesAreaEntity_HI> {

	Pagination<PlmSalesAreaEntity_HI_RO> findPlmSalesAreaInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmSalesAreaEntity_HI savePlmSalesAreaInfo(JSONObject queryParamJSON) throws Exception;

}
