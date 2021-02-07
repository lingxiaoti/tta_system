package com.sie.saaf.schedule.model.dao.readonly;

import com.sie.saaf.schedule.model.entities.readonly.ScheduleSchedulesErrorEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("scheduleSchedulesErrorDAO_HI_RO")
public class ScheduleSchedulesErrorDAO_HI_RO extends DynamicViewObjectImpl<ScheduleSchedulesErrorEntity_HI_RO> {
    public ScheduleSchedulesErrorDAO_HI_RO() {
        super();
    }

}
