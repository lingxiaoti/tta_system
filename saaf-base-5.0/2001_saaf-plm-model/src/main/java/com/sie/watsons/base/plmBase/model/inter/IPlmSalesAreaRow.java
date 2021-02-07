package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSalesAreaRowEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmSalesAreaRowEntity_HI;

public interface IPlmSalesAreaRow extends IBaseCommon<PlmSalesAreaRowEntity_HI> {

	List<PlmSalesAreaRowEntity_HI_RO> findListByGroup(List<String> priceGroups) throws Exception;

	Pagination<PlmSalesAreaRowEntity_HI_RO> findPlmSalesAreaRowInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmSalesAreaRowEntity_HI> savePlmSalesAreaRowInfo(JSONObject queryParamJSON);

	Integer deletePlmSalesAreaRowInfo(JSONObject queryParamJSON);
}
