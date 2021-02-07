package com.sie.saaf.schedule.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.schedule.model.entities.ScheduleJobAccessOrgsEntity_HI;
import org.springframework.stereotype.Component;

@Component("scheduleJobAccessOrgsDAO_HI")
public class ScheduleJobAccessOrgsDAO_HI extends BaseCommonDAO_HI<ScheduleJobAccessOrgsEntity_HI> {
	public ScheduleJobAccessOrgsDAO_HI() {
		super();
	}

	@Override
	public Object save(ScheduleJobAccessOrgsEntity_HI entity) {
		return super.save(entity);
	}
}
