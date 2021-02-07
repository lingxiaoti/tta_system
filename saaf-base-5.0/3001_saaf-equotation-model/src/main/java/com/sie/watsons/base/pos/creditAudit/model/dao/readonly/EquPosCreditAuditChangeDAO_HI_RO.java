package com.sie.watsons.base.pos.creditAudit.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.creditAudit.model.entities.readonly.EquPosCreditAuditChangeEntity_HI_RO;
import org.springframework.stereotype.Component;import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosCreditAuditChangeDAO_HI_RO")
public class EquPosCreditAuditChangeDAO_HI_RO extends DynamicViewObjectImpl<EquPosCreditAuditChangeEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCreditAuditChangeDAO_HI_RO.class);
	public EquPosCreditAuditChangeDAO_HI_RO() {
		super();
	}

}
