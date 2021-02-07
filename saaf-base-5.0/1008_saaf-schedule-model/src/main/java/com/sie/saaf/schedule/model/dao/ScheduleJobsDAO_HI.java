package com.sie.saaf.schedule.model.dao;


import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;

import org.springframework.stereotype.Component;

import com.sie.saaf.schedule.model.entities.ScheduleJobsEntity_HI;

@Component("scheduleJobsDAO_HI")
public class ScheduleJobsDAO_HI extends BaseCommonDAO_HI<ScheduleJobsEntity_HI> {
    public ScheduleJobsDAO_HI() {
        super();
    }
}
