package com.sie.watsons.base.newbreedextend.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.newbreedextend.model.entities.readonly.TtaNewbreedExtendLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaNewbreedExtendLineDAO_HI_RO")
public class TtaNewbreedExtendLineDAO_HI_RO extends DynamicViewObjectImpl<TtaNewbreedExtendLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedExtendLineDAO_HI_RO.class);
	public TtaNewbreedExtendLineDAO_HI_RO() {
		super();
	}

}
