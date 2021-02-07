package com.sie.watsons.base.ttaImport.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaAboiSummaryEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaAboiSummaryDAO_HI_RO")
public class TtaAboiSummaryDAO_HI_RO extends DynamicViewObjectImpl<TtaAboiSummaryEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAboiSummaryDAO_HI_RO.class);
	public TtaAboiSummaryDAO_HI_RO() {
		super();
	}

}
