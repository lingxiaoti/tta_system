package com.sie.watsons.base.ob.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentBatchInfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentBatchInfoEntity_HI;

public interface IPlmDevelopmentBatchInfo extends IBaseCommon<PlmDevelopmentBatchInfoEntity_HI> {

	Pagination<PlmDevelopmentBatchInfoEntity_HI_RO> findPlmDevelopmentBatchInfoInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmDevelopmentBatchInfoEntity_HI> savePlmDevelopmentBatchInfoInfo(JSONObject queryParamJSON);

	Integer deletePlmDevelopmentBatchInfoInfo(JSONObject queryParamJSON);

}
