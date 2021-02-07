package com.sie.saaf.business.model.inter.server;

import java.util.LinkedHashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.business.model.entities.TtaMarketInEntity_HI;
import com.sie.saaf.business.model.inter.ITtaMarketIn;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("ttaMarketInServer")
public class TtaMarketInServer extends BaseCommonServer<TtaMarketInEntity_HI> implements ITtaMarketIn{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaMarketInServer.class);

	@Autowired
	private ViewObject<TtaMarketInEntity_HI> ttaMarketInDAO_HI;

	public TtaMarketInServer() {
		super();
	}

	@Override
	public void saveOrUpdateBatchMarket(LinkedHashSet<TtaMarketInEntity_HI> list) {
		ttaMarketInDAO_HI.saveOrUpdateAll(list);
	}

}
