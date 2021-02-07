package com.sie.watsons.base.pon.information.model.inter.server;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuoSecondResultEntity_HI;
import com.sie.watsons.base.pon.information.model.inter.IEquPonQuoSecondResult;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("equPonQuoSecondResultServer")
public class EquPonQuoSecondResultServer extends BaseCommonServer<EquPonQuoSecondResultEntity_HI> implements IEquPonQuoSecondResult{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuoSecondResultServer.class);

	@Autowired
	private ViewObject<EquPonQuoSecondResultEntity_HI> equPonQuoSecondResultDAO_HI;

	public EquPonQuoSecondResultServer() {
		super();
	}

}
