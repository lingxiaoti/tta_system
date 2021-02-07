package com.sie.watsons.base.ob.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProjectInfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ob.model.entities.PlmProjectInfoEntity_HI;

public interface IPlmProjectInfo extends IBaseCommon<PlmProjectInfoEntity_HI> {

	Pagination<PlmProjectInfoEntity_HI_RO> findPlmProjectInfoInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmProjectInfoEntity_HI savePlmProjectInfoInfo(JSONObject queryParamJSON) throws Exception;

	void deletePlmProjectInfoInfo(JSONObject params);

}
