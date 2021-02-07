package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaAnalysisEditDataEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaAnalysisEditDataDAO_HI_RO")
public class TtaAnalysisEditDataDAO_HI_RO extends DynamicViewObjectImpl<TtaAnalysisEditDataEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAnalysisEditDataDAO_HI_RO.class);
	public TtaAnalysisEditDataDAO_HI_RO() {
		super();
	}

}
