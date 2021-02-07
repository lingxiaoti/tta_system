package com.sie.watsons.base.supplement.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaNonStandarHeaderEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaSaNonStandarHeaderDAO_HI_RO")
public class TtaSaNonStandarHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaSaNonStandarHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaNonStandarHeaderDAO_HI_RO.class);
	public TtaSaNonStandarHeaderDAO_HI_RO() {
		super();
	}

}
