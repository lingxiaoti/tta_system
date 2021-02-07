package com.sie.watsons.base.newbreedextend.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.newbreedextend.model.entities.readonly.TtaNewbreedExtendHeaderEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaNewbreedExtendHeaderDAO_HI_RO")
public class TtaNewbreedExtendHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaNewbreedExtendHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedExtendHeaderDAO_HI_RO.class);
	public TtaNewbreedExtendHeaderDAO_HI_RO() {
		super();
	}

}
