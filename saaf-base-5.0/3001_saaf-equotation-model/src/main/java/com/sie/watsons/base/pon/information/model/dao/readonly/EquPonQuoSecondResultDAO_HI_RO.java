package com.sie.watsons.base.pon.information.model.dao.readonly;

import com.sie.watsons.base.pon.information.model.entities.readonly.EquPonQuoSecondResultEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuoSecondResultDAO_HI_RO")
public class EquPonQuoSecondResultDAO_HI_RO extends DynamicViewObjectImpl<EquPonQuoSecondResultEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuoSecondResultDAO_HI_RO.class);
	public EquPonQuoSecondResultDAO_HI_RO() {
		super();
	}

}
