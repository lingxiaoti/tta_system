package com.sie.saaf.ewarning.model.inter.server;

import com.sie.saaf.ewarning.model.entities.EwConfigurationHeadEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.ewarning.model.inter.IEwConfigurationHead;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ewConfigurationHeadServer")
public class EwConfigurationHeadServer extends BaseCommonServer<EwConfigurationHeadEntity_HI> implements IEwConfigurationHead {
	private static final Logger LOGGER = LoggerFactory.getLogger(EwConfigurationHeadServer.class);

	@Autowired
	private ViewObject<EwConfigurationHeadEntity_HI> ewConfigurationHeadDAO_HI;

	public EwConfigurationHeadServer() {
		super();
	}

}
