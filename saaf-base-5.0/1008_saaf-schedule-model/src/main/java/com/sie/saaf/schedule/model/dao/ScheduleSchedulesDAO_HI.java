package com.sie.saaf.schedule.model.dao;


import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.schedule.model.entities.ScheduleSchedulesEntity_HI;
import org.springframework.stereotype.Component;

@Component("scheduleSchedulesDAO_HI")
public class ScheduleSchedulesDAO_HI extends BaseCommonDAO_HI<ScheduleSchedulesEntity_HI> {
    public ScheduleSchedulesDAO_HI() {
        super();
    }
}
