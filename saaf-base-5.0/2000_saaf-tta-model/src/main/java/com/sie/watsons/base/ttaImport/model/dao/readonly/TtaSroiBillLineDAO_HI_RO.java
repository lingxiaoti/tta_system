package com.sie.watsons.base.ttaImport.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaSroiBillLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaSroiBillLineDAO_HI_RO")
public class TtaSroiBillLineDAO_HI_RO extends DynamicViewObjectImpl<TtaSroiBillLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSroiBillLineDAO_HI_RO.class);
	public TtaSroiBillLineDAO_HI_RO() {
		super();
	}

}
