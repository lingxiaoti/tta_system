package com.sie.saaf.base.task.model.dao;

import com.sie.saaf.base.task.model.entities.BaseTaskCollectionEntity_HI;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseTaskCollectionDAO_HI")
public class BaseTaskCollectionDAO_HI extends ViewObjectImpl<BaseTaskCollectionEntity_HI>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseTaskCollectionDAO_HI.class);
	public BaseTaskCollectionDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseTaskCollectionEntity_HI entity) {
		return super.save(entity);
	}
}
