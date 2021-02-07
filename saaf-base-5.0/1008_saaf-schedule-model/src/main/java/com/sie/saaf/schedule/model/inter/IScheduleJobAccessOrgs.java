package com.sie.saaf.schedule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.schedule.model.entities.ScheduleJobAccessOrgsEntity_HI;
import java.util.List;

public interface IScheduleJobAccessOrgs extends IBaseCommon<ScheduleJobAccessOrgsEntity_HI> {

	List<ScheduleJobAccessOrgsEntity_HI> findSaafJobAccessOrgsInfo(JSONObject queryParamJSON);

	Object saveSaafJobAccessOrgsInfo(JSONObject queryParamJSON);
	
	JSONObject saveJobInsts(JSONObject parameters);
	
	List findJobsInstTree(JSONObject parameters);

}
