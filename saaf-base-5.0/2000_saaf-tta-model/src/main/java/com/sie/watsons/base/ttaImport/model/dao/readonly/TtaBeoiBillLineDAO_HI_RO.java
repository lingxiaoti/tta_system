package com.sie.watsons.base.ttaImport.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaBeoiBillLineEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaBeoiBillLineDAO_HI_RO")
public class TtaBeoiBillLineDAO_HI_RO extends DynamicViewObjectImpl<TtaBeoiBillLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBeoiBillLineDAO_HI_RO.class);
	public TtaBeoiBillLineDAO_HI_RO() {
		super();
	}

}
