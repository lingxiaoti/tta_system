package com.sie.saaf.schedule.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

import org.springframework.stereotype.Component;

import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobsRespEntity_HI_RO;

@Component("scheduleJobsRespDAO_HI_RO")
public class ScheduleJobsRespDAO_HI_RO extends DynamicViewObjectImpl<ScheduleJobsRespEntity_HI_RO> {
    public ScheduleJobsRespDAO_HI_RO() {
        super();
    }
}
