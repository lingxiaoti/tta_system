package com.sie.saaf.schedule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.schedule.model.entities.ScheduleSchedulesEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleSchedulesEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;


public interface ISchedules extends IBaseCommon<ScheduleSchedulesEntity_HI> {
    String saveRequest(JSONObject parameters);

    String deleteSchedule(JSONObject parameters);

    String deleteScheduleBatch(JSONObject parameters);

    Pagination<ScheduleSchedulesEntity_HI_RO> findRequests(JSONObject parameters, Integer pageIndex, Integer pageRows);

    String getRequestLog(JSONObject parameters);

    String cancelRequest(JSONObject parameters);

    String launchRequest(JSONObject parameters);

    String pauseRequest(JSONObject parameters);

    String resumeRequest(JSONObject parameters);
}
