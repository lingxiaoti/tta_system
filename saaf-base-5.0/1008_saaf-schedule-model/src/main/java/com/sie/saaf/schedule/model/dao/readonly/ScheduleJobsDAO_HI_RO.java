package com.sie.saaf.schedule.model.dao.readonly;


import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

import org.springframework.stereotype.Component;

import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobsEntity_HI_RO;

@Component("scheduleJobsDAO_HI_RO")
public class ScheduleJobsDAO_HI_RO extends DynamicViewObjectImpl<ScheduleJobsEntity_HI_RO> {
    public ScheduleJobsDAO_HI_RO() {
        super();
    }

}
