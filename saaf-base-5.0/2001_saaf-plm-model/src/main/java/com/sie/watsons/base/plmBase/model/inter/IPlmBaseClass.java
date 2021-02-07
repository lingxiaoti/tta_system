package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.PlmBaseClassEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBaseClassEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmBaseClass extends IBaseCommon<PlmBaseClassEntity_HI> {

	Pagination<PlmBaseClassEntity_HI_RO> findBaseClassinfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

}
