package com.sie.saaf.base.commmon.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.commmon.model.entities.BaseWatsonsBpmNodeUserEntity_HI;
import com.sie.saaf.base.commmon.model.entities.readonly.BaseWatsonsBpmNodeUserEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

public interface IBaseWatsonsBpmNodeUser extends
		IBaseCommon<BaseWatsonsBpmNodeUserEntity_HI> {

	Pagination<BaseWatsonsBpmNodeUserEntity_HI_RO> findBpmNodeUser(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

}
