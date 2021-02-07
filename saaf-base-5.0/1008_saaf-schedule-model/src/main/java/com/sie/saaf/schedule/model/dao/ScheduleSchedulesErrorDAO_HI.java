package com.sie.saaf.schedule.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import com.sie.saaf.schedule.model.entities.ScheduleSchedulesErrorEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("scheduleSchedulesErrorDAO_HI")
public class ScheduleSchedulesErrorDAO_HI extends BaseCommonDAO_HI<ScheduleSchedulesErrorEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleSchedulesErrorDAO_HI.class);
	public ScheduleSchedulesErrorDAO_HI() {
		super();
	}

	@Override
	public Object save(ScheduleSchedulesErrorEntity_HI entity) {
		return super.save(entity);
	}
}
