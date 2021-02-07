package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmGroupBrandEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmGroupBrandEntity_HI;

public interface IPlmGroupBrand extends IBaseCommon<PlmGroupBrandEntity_HI> {

	Pagination<PlmGroupBrandEntity_HI_RO> findPlmGroupBrandInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmGroupBrandEntity_HI savePlmGroupBrandInfo(JSONObject queryParamJSON);

	PlmGroupBrandEntity_HI saveConfirmedPlmGroupStatus(JSONObject params) throws Exception;

	PlmGroupBrandEntity_HI findByGroupBrandName(String groupBrandName);
}
