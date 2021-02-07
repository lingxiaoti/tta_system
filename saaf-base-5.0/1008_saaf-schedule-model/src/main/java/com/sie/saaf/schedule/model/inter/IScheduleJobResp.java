package com.sie.saaf.schedule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.schedule.model.entities.ScheduleJobRespEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobsRespEntity_HI_RO;

public interface IScheduleJobResp extends IBaseCommon<ScheduleJobRespEntity_HI> {

	List<ScheduleJobRespEntity_HI> findSaafJobRespInfo(JSONObject queryParamJSON);

	Pagination<ScheduleJobsRespEntity_HI_RO> findJobRespAll(JSONObject queryParamJSON);

	Object saveSaafJobRespInfo(JSONObject queryParamJSON);

	Pagination findRemainderJobResp(JSONObject parameters, Integer pageIndex, Integer pageRows);

	List saveSaafJobResp(JSONObject parameters);

	JSONObject deleteSaafJobResp(JSONObject parameters);
	

}
