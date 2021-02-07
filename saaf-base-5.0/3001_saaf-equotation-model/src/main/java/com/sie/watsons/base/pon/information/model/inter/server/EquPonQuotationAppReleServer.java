package com.sie.watsons.base.pon.information.model.inter.server;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationAppReleEntity_HI;
import com.sie.watsons.base.pon.information.model.inter.IEquPonQuotationAppRele;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("equPonQuotationAppReleServer")
public class EquPonQuotationAppReleServer extends BaseCommonServer<EquPonQuotationAppReleEntity_HI> implements IEquPonQuotationAppRele{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationAppReleServer.class);

	@Autowired
	private ViewObject<EquPonQuotationAppReleEntity_HI> equPonQuotationAppReleDAO_HI;

	public EquPonQuotationAppReleServer() {
		super();
	}

}
