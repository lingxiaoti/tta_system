package com.sie.watsons.base.pon.itproject.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItProjectInfoEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPonItProjectInfoDAO_HI_RO")
public class EquPonItProjectInfoDAO_HI_RO extends DynamicViewObjectImpl<EquPonItProjectInfoEntity_HI_RO>  {
//	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItProjectInfoDAO_HI_RO.class);
	public EquPonItProjectInfoDAO_HI_RO() {
		super();
	}

}
