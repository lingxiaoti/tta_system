package com.sie.watsons.base.supplement.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdHeaderEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaSaStdHeaderDAO_HI_RO")
public class TtaSaStdHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaSaStdHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdHeaderDAO_HI_RO.class);
	public TtaSaStdHeaderDAO_HI_RO() {
		super();
	}

}
