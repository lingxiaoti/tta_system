package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmDeptListEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmDeptListEntity_HI;

public interface IPlmDeptList extends IBaseCommon<PlmDeptListEntity_HI> {

	Pagination<PlmDeptListEntity_HI_RO> findPlmDeptListInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmDeptListEntity_HI savePlmDeptListInfo(JSONObject queryParamJSON);

}
