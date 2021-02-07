package com.sie.saaf.schedule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.schedule.model.entities.ScheduleJobsEntity_HI;
import com.sie.saaf.schedule.model.entities.ScheduleSchedulesErrorEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleSchedulesErrorEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IScheduleSchedulesError  extends IBaseCommon<ScheduleSchedulesErrorEntity_HI> {

    String saveScheduleError(JSONObject parameters);

    Pagination<ScheduleSchedulesErrorEntity_HI_RO> findScheduleErrors(JSONObject parameters, Integer pageIndex, Integer pageRows);

    String findSchedulesErrorLog(JSONObject parameters);

}
