package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.PlmTaxTypeListEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmTaxTypeListEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IPlmTaxTypeList extends IBaseCommon<PlmTaxTypeListEntity_HI> {

	Pagination<PlmTaxTypeListEntity_HI_RO> findPlmTaxTypeListInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmTaxTypeListEntity_HI savePlmTaxTypeListInfo(JSONObject queryParamJSON);

}
