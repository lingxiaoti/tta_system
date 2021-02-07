package com.sie.saaf.ewarning.model.inter.server;

import com.sie.saaf.ewarning.model.entities.EwConfigurationManEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.ewarning.model.inter.IEwConfigurationMan;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ewConfigurationManServer")
public class EwConfigurationManServer extends BaseCommonServer<EwConfigurationManEntity_HI> implements IEwConfigurationMan {
	private static final Logger LOGGER = LoggerFactory.getLogger(EwConfigurationManServer.class);

	@Autowired
	private ViewObject<EwConfigurationManEntity_HI> ewConfigurationManDAO_HI;

	public EwConfigurationManServer() {
		super();
	}

}
