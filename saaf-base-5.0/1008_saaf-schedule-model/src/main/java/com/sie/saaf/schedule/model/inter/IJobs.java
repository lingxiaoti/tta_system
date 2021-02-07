package com.sie.saaf.schedule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.schedule.model.entities.ScheduleJobsEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobsEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IJobs extends IBaseCommon<ScheduleJobsEntity_HI> {
    String saveJob(JSONObject parameters);
    
    String saveJobInfo(JSONObject parameters);

    String deleteJob(JSONObject parameters);

    String updateJob(JSONObject parameters);

    Pagination<ScheduleJobsEntity_HI_RO> findJobs(JSONObject parameters, Integer pageIndex, Integer pageRows);
}
