package com.sie.saaf.business.model.inter.server;

import java.util.List;

import com.sie.saaf.business.model.dao.TtaPersonInDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.business.model.entities.TtaPersonInEntity_HI;
import com.sie.saaf.business.model.inter.ITtaPersonIn;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("ttaPersonInServer")
public class TtaPersonInServer extends BaseCommonServer<TtaPersonInEntity_HI> implements ITtaPersonIn{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPersonInServer.class);

	@Autowired
	private TtaPersonInDAO_HI ttaPersonInDAO_HI;
	
	
	public TtaPersonInServer() {
		super();
	}

	
	@Override
	public void saveOrUpdateAll(List<TtaPersonInEntity_HI> personList) {
		ttaPersonInDAO_HI.saveOrUpdateAll(personList);
	}

	@Override
	public void callProBasePersonJob() {
		ttaPersonInDAO_HI.callProBasePersonJob();
	}

}
