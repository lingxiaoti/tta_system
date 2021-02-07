package com.sie.watsons.base.pon.project.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pon.project.model.entities.readonly.EquPonProjectChangeCauseEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonProjectChangeCauseDAO_HI_RO")
public class EquPonProjectChangeCauseDAO_HI_RO extends DynamicViewObjectImpl<EquPonProjectChangeCauseEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonProjectChangeCauseDAO_HI_RO.class);
	public EquPonProjectChangeCauseDAO_HI_RO() {
		super();
	}

}
