package com.sie.saaf.base.user.model.inter.server;

import com.sie.saaf.base.user.model.entities.PerAllPeopleFEntity_HI;
import com.sie.saaf.base.user.model.inter.IPerAllPeopleF;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("perAllPeopleFServer")
public class PerAllPeopleFServer extends BaseCommonServer<PerAllPeopleFEntity_HI> implements IPerAllPeopleF {
//	private static final Logger LOGGER = LoggerFactory.getLogger(PerAllPeopleFServer.class);
//	@Autowired
//	private ViewObject<PerAllPeopleFEntity_HI> perAllPeopleFDAO_HI;
//	public PerAllPeopleFServer() {
//		super();
//	}

}
