package com.sie.saaf.schedule.model.dao;


import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.schedule.model.entities.ScheduleJobParametersEntity_HI;
import org.springframework.stereotype.Component;


@Component("scheduleJobParametersDAO_HI")
public class ScheduleJobParametersDAO_HI extends BaseCommonDAO_HI<ScheduleJobParametersEntity_HI> {
    public ScheduleJobParametersDAO_HI() {
        super();
    }
}
