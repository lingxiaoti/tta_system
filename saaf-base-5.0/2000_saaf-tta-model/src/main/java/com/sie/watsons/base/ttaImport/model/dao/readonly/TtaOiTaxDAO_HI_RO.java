package com.sie.watsons.base.ttaImport.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaOiTaxEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaOiTaxDAO_HI_RO")
public class TtaOiTaxDAO_HI_RO extends DynamicViewObjectImpl<TtaOiTaxEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiTaxDAO_HI_RO.class);
	public TtaOiTaxDAO_HI_RO() {
		super();
	}

}
