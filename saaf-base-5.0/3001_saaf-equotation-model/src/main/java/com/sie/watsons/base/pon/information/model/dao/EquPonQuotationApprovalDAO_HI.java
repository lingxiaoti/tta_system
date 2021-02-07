package com.sie.watsons.base.pon.information.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationApprovalEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuotationApprovalDAO_HI")
public class EquPonQuotationApprovalDAO_HI extends BaseCommonDAO_HI<EquPonQuotationApprovalEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationApprovalDAO_HI.class);

	public EquPonQuotationApprovalDAO_HI() {
		super();
	}

}
