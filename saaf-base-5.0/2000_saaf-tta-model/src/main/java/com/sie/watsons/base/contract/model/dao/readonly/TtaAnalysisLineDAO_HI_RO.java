package com.sie.watsons.base.contract.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.contract.model.entities.readonly.TtaAnalysisLineEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaAnalysisLineDAO_HI_RO")
public class TtaAnalysisLineDAO_HI_RO extends DynamicViewObjectImpl<TtaAnalysisLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAnalysisLineDAO_HI_RO.class);
	public TtaAnalysisLineDAO_HI_RO() {
		super();
	}

}
