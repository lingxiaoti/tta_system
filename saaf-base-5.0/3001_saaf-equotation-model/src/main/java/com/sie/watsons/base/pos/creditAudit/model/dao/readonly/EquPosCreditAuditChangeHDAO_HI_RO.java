package com.sie.watsons.base.pos.creditAudit.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.creditAudit.model.entities.readonly.EquPosCreditAuditChangeHEntity_HI_RO;
import org.springframework.stereotype.Component;import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosCreditAuditChangeHDAO_HI_RO")
public class EquPosCreditAuditChangeHDAO_HI_RO extends DynamicViewObjectImpl<EquPosCreditAuditChangeHEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCreditAuditChangeHDAO_HI_RO.class);
	public EquPosCreditAuditChangeHDAO_HI_RO() {
		super();
	}

}
