package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.PlmBaseLevelEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBaseLevelEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmBaseLevel extends IBaseCommon<PlmBaseLevelEntity_HI> {

	Pagination<PlmBaseLevelEntity_HI_RO> findBaseLevelinfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

}
