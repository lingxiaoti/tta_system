package com.sie.watsons.base.api.model.dao.readonly;
import com.sie.watsons.base.api.model.entities.readonly.PlmUdaAttributeEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmUdaAttributeDAO_HI_RO")
public class PlmUdaAttributeDAO_HI_RO extends DynamicViewObjectImpl<PlmUdaAttributeEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmUdaAttributeDAO_HI_RO.class);
	public PlmUdaAttributeDAO_HI_RO() {
		super();
	}

}
