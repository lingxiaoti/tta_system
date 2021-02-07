package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSpecialProductTypeEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmSpecialProductTypeEntity_HI;

public interface IPlmSpecialProductType extends IBaseCommon<PlmSpecialProductTypeEntity_HI> {

	Pagination<PlmSpecialProductTypeEntity_HI_RO> findPlmSpecialProductTypeInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmSpecialProductTypeEntity_HI savePlmSpecialProductTypeInfo(JSONObject queryParamJSON);

	Integer deletePlmSpecialProductTypeInfo(JSONObject queryParamJSON);

}
