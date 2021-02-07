package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.readonly.ActIdUserEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActStatisticEntity_RO;
import com.sie.saaf.bpm.model.entities.readonly.ActStatisticProcessEntity_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IActBpmStatistic {

	ActStatisticEntity_RO taskStatistic(JSONObject queryParamJSON);

	Pagination<ActStatisticProcessEntity_RO> findProcesses(JSONObject queryParamJSON, ActIdUserEntity_RO user,
														   Integer pageIndex, Integer pageRows);

	

}
