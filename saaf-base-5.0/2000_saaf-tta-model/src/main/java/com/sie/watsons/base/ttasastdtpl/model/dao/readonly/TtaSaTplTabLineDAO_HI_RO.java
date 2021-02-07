package com.sie.watsons.base.ttasastdtpl.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaTplTabLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaSaTplTabLineDAO_HI_RO")
public class TtaSaTplTabLineDAO_HI_RO extends DynamicViewObjectImpl<TtaSaTplTabLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaTplTabLineDAO_HI_RO.class);
	public TtaSaTplTabLineDAO_HI_RO() {
		super();
	}

}
