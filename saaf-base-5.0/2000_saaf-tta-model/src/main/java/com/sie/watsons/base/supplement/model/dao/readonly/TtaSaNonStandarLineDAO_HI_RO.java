package com.sie.watsons.base.supplement.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaNonStandarLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaSaNonStandarLineDAO_HI_RO")
public class TtaSaNonStandarLineDAO_HI_RO extends DynamicViewObjectImpl<TtaSaNonStandarLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaNonStandarLineDAO_HI_RO.class);
	public TtaSaNonStandarLineDAO_HI_RO() {
		super();
	}

}
