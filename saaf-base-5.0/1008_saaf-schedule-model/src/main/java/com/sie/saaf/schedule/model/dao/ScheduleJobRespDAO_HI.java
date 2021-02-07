package com.sie.saaf.schedule.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.schedule.model.entities.ScheduleJobRespEntity_HI;
import org.springframework.stereotype.Component;

@Component("scheduleJobRespDAO_HI")
public class ScheduleJobRespDAO_HI extends BaseCommonDAO_HI<ScheduleJobRespEntity_HI> {
	public ScheduleJobRespDAO_HI() {
		super();
	}

	@Override
	public Object save(ScheduleJobRespEntity_HI entity) {
		return super.save(entity);
	}
}
