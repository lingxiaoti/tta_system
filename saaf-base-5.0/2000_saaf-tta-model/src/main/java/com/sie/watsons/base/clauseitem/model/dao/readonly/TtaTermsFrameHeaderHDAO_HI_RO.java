package com.sie.watsons.base.clauseitem.model.dao.readonly;

import com.sie.watsons.base.clauseitem.model.entities.readonly.TtaTermsFrameHeaderHEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Component("ttaTermsFrameHeaderHDAO_HI_RO")
public class TtaTermsFrameHeaderHDAO_HI_RO extends DynamicViewObjectImpl<TtaTermsFrameHeaderHEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsFrameHeaderHDAO_HI_RO.class);
	public TtaTermsFrameHeaderHDAO_HI_RO() {
		super();
	}

}
