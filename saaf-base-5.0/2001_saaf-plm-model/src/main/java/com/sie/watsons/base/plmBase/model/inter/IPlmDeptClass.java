package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmDeptClassEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmDeptClassEntity_HI;

public interface IPlmDeptClass extends IBaseCommon<PlmDeptClassEntity_HI> {

	Pagination<PlmDeptClassEntity_HI_RO> findPlmDeptClassInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmDeptClassEntity_HI> savePlmDeptClassInfo(JSONObject queryParamJSON);

	Integer deletePlmDeptClassInfo(JSONObject queryParamJSON);

}
