package com.sie.saaf.schedule.model.dao.readonly;


import org.springframework.stereotype.Component;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobInstEntity_HI_RO;


@Component
public class ScheduleJobInstDAO_HI_RO extends DynamicViewObjectImpl<ScheduleJobInstEntity_HI_RO> {
    public ScheduleJobInstDAO_HI_RO() {
        super();
    }
}
