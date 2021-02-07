package com.sie.saaf.base.commmon.model.inter;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.commmon.model.entities.BaseRequestLogEntity_HI;
import com.sie.saaf.base.commmon.model.entities.TtaSupplierEntity_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IBaseRequestLog {

	List<BaseRequestLogEntity_HI> findBaseRequestLogInfo(
			JSONObject queryParamJSON);

	BaseRequestLogEntity_HI saveBaseRequestLogInfo(JSONObject queryParamJSON);

	Pagination<TtaSupplierEntity_RO> findPlmSupplier(JSONObject queryParamJSON,
			Integer pageIndex, Integer pageRows);

}
