package com.sie.watsons.base.proposal.model.inter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.proposal.model.entities.TtaNewFeeEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.proposal.model.inter.ITtaNewFee;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaNewFeeServer")
public class TtaNewFeeServer extends BaseCommonServer<TtaNewFeeEntity_HI> implements ITtaNewFee{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewFeeServer.class);

	@Autowired
	private ViewObject<TtaNewFeeEntity_HI> ttaNewFeeDAO_HI;

	public TtaNewFeeServer() {
		super();
	}

}
