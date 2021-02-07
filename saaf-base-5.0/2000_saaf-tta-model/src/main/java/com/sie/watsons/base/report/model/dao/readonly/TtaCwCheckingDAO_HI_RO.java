package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaCwCheckingEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaCwCheckingDAO_HI_RO")
public class TtaCwCheckingDAO_HI_RO extends DynamicViewObjectImpl<TtaCwCheckingEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaCwCheckingDAO_HI_RO.class);
	public TtaCwCheckingDAO_HI_RO() {
		super();
	}

}
