package com.sie.saaf.ewarning.model.dao.readonly;

import com.sie.saaf.ewarning.model.entities.readonly.EwHandleEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ewHandleDAO_HI_RO")
public class EwHandleDAO_HI_RO extends DynamicViewObjectImpl<EwHandleEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EwHandleDAO_HI_RO.class);
	public EwHandleDAO_HI_RO() {
		super();
	}

}
