package com.sie.saaf.ewarning.model.inter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.saaf.ewarning.model.entities.EwConfigurationLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.ewarning.model.inter.IEwConfigurationLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ewConfigurationLineServer")
public class EwConfigurationLineServer extends BaseCommonServer<EwConfigurationLineEntity_HI> implements IEwConfigurationLine {
	private static final Logger LOGGER = LoggerFactory.getLogger(EwConfigurationLineServer.class);

	@Autowired
	private ViewObject<EwConfigurationLineEntity_HI> ewConfigurationLineDAO_HI;

	public EwConfigurationLineServer() {
		super();
	}

}
