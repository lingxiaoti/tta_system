package com.sie.watsons.base.supplement.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdFieldLineEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaSaStdFieldLineDAO_HI_RO")
public class TtaSaStdFieldLineDAO_HI_RO extends DynamicViewObjectImpl<TtaSaStdFieldLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdFieldLineDAO_HI_RO.class);
	public TtaSaStdFieldLineDAO_HI_RO() {
		super();
	}

}
