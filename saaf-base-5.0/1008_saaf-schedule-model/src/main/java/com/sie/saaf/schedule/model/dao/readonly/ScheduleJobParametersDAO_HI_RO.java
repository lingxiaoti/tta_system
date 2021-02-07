package com.sie.saaf.schedule.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

import org.springframework.stereotype.Component;

import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobParametersEntity_HI_RO;

@Component("scheduleJobParametersDAO_HI_RO")
public class ScheduleJobParametersDAO_HI_RO extends DynamicViewObjectImpl<ScheduleJobParametersEntity_HI_RO> {
    public ScheduleJobParametersDAO_HI_RO() {
        super();
    }
}
