package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmDeptSubclassEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmDeptSubclassEntity_HI;

public interface IPlmDeptSubclass extends IBaseCommon<PlmDeptSubclassEntity_HI> {

	Pagination<PlmDeptSubclassEntity_HI_RO> findPlmDeptSubclassInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmDeptSubclassEntity_HI> savePlmDeptSubclassInfo(JSONObject queryParamJSON);

	Integer deletePlmDeptSubclassInfo(JSONObject queryParamJSON);

}
