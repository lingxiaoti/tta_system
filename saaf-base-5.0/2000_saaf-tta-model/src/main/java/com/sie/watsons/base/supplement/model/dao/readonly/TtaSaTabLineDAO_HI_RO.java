package com.sie.watsons.base.supplement.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaTabLineEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaSaTabLineDAO_HI_RO")
public class TtaSaTabLineDAO_HI_RO extends DynamicViewObjectImpl<TtaSaTabLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaTabLineDAO_HI_RO.class);
	public TtaSaTabLineDAO_HI_RO() {
		super();
	}

}
