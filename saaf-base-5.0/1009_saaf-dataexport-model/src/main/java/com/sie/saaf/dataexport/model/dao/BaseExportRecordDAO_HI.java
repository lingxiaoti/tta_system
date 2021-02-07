package com.sie.saaf.dataexport.model.dao;

import com.sie.saaf.dataexport.model.entities.BaseExportRecordEntity_HI;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseExportRecordDAO_HI")
public class BaseExportRecordDAO_HI extends ViewObjectImpl<BaseExportRecordEntity_HI>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseExportRecordDAO_HI.class);
	public BaseExportRecordDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseExportRecordEntity_HI entity) {
		return super.save(entity);
	}
}
