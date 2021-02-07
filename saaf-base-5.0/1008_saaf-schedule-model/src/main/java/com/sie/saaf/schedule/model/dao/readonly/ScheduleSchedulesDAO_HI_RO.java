package com.sie.saaf.schedule.model.dao.readonly;


import com.sie.saaf.schedule.model.entities.readonly.ScheduleSchedulesEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

import org.springframework.stereotype.Component;

@Component("scheduleSchedulesDAO_HI_RO")
public class ScheduleSchedulesDAO_HI_RO extends DynamicViewObjectImpl<ScheduleSchedulesEntity_HI_RO>{
    public ScheduleSchedulesDAO_HI_RO() {
        super();
    }
    
}
