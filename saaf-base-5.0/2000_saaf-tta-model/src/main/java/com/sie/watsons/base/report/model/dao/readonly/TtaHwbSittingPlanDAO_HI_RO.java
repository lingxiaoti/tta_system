package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbSittingPlanEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaHwbSittingPlanDAO_HI_RO")
public class TtaHwbSittingPlanDAO_HI_RO extends DynamicViewObjectImpl<TtaHwbSittingPlanEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbSittingPlanDAO_HI_RO.class);
	public TtaHwbSittingPlanDAO_HI_RO() {
		super();
	}

}
