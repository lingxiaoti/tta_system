package com.sie.saaf.base.user.model.inter.server;

import com.sie.saaf.base.user.model.entities.CuxCdmAccessBasedataEntity_HI;
import com.sie.saaf.base.user.model.inter.ICuxCdmAccessBasedata;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("cuxCdmAccessBasedataServer")
public class CuxCdmAccessBasedataServer extends BaseCommonServer<CuxCdmAccessBasedataEntity_HI> implements ICuxCdmAccessBasedata {
//	private static final Logger LOGGER = LoggerFactory.getLogger(CuxCdmAccessBasedataServer.class);
//	@Autowired
//	private ViewObject<CuxCdmAccessBasedataEntity_HI> cuxCdmAccessBasedataDAO_HI;

//	public CuxCdmAccessBasedataServer() {
//		super();
//	}

}
