package com.sie.saaf.base.redisdata.model.dao;

import com.sie.saaf.base.redisdata.model.entities.BaseRedisDataEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseRedisDataDAO_HI")
public class BaseRedisDataDAO_HI extends BaseCommonDAO_HI<BaseRedisDataEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseRedisDataDAO_HI.class);
	public BaseRedisDataDAO_HI() {
		super();
	}
}
