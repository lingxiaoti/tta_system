package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmRmsSupplierInfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmRmsSupplierInfoEntity_HI;

public interface IPlmRmsSupplierInfo extends IBaseCommon<PlmRmsSupplierInfoEntity_HI> {

	Pagination<PlmRmsSupplierInfoEntity_HI_RO> findPlmRmsSupplierInfoInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmRmsSupplierInfoEntity_HI savePlmRmsSupplierInfoInfo(JSONObject queryParamJSON);

}
