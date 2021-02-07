package com.sie.saaf.ewarning.model.inter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.saaf.ewarning.model.entities.EwHandleEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.ewarning.model.inter.IEwHandle;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ewHandleServer")
public class EwHandleServer extends BaseCommonServer<EwHandleEntity_HI> implements IEwHandle {
	private static final Logger LOGGER = LoggerFactory.getLogger(EwHandleServer.class);

	@Autowired
	private ViewObject<EwHandleEntity_HI> ewHandleDAO_HI;

	public EwHandleServer() {
		super();
	}

}
