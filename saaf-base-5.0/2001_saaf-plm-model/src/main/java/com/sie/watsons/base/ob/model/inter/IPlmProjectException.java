package com.sie.watsons.base.ob.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProjectExceptionEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ob.model.entities.PlmProjectExceptionEntity_HI;

public interface IPlmProjectException extends IBaseCommon<PlmProjectExceptionEntity_HI> {

	Pagination<PlmProjectExceptionEntity_HI_RO> findPlmProjectExceptionInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmProjectExceptionEntity_HI> savePlmProjectExceptionInfo(JSONObject queryParamJSON);

	Integer deletePlmProjectExceptionInfo(JSONObject queryParamJSON);

}
