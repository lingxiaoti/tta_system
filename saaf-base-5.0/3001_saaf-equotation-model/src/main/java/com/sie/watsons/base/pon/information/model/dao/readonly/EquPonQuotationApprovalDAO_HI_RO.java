package com.sie.watsons.base.pon.information.model.dao.readonly;

import com.sie.watsons.base.pon.information.model.entities.readonly.EquPonQuotationApprovalEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuotationApprovalDAO_HI_RO")
public class EquPonQuotationApprovalDAO_HI_RO extends DynamicViewObjectImpl<EquPonQuotationApprovalEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationApprovalDAO_HI_RO.class);
	public EquPonQuotationApprovalDAO_HI_RO() {
		super();
	}

}
