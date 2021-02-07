package com.sie.saaf.base.user.model.inter.server;

import com.sie.saaf.base.user.model.entities.BaseChannelEntity_HI;
import com.sie.saaf.base.user.model.inter.IBaseChannel;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("baseChannelServer")
public class BaseChannelServer extends BaseCommonServer<BaseChannelEntity_HI> implements IBaseChannel {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseChannelServer.class);
//	@Autowired
//	private ViewObject<BaseChannelEntity_HI> baseChannelDAO_HI;
//	public BaseChannelServer() {
//		super();
//	}

}
