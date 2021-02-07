package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.PlmLocationListEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmLocationListEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IPlmLocationList extends IBaseCommon<PlmLocationListEntity_HI> {

	Pagination<PlmLocationListEntity_HI_RO> findPlmLocationListInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmLocationListEntity_HI savePlmLocationListInfo(JSONObject queryParamJSON);

}
