package com.sie.watsons.base.ob.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProductExceptionInfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ob.model.entities.PlmProductExceptionInfoEntity_HI;

public interface IPlmProductExceptionInfo extends IBaseCommon<PlmProductExceptionInfoEntity_HI> {

	Pagination<PlmProductExceptionInfoEntity_HI_RO> findPlmProductExceptionInfoInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmProductExceptionInfoEntity_HI savePlmProductExceptionInfoInfo(JSONObject queryParamJSON);

}
