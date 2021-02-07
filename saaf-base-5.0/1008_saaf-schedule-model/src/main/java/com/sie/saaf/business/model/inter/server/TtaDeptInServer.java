package com.sie.saaf.business.model.inter.server;

import java.util.LinkedHashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.business.model.dao.TtaDeptInDAO_HI;
import com.sie.saaf.business.model.entities.TtaDeptInEntity_HI;
import com.sie.saaf.business.model.inter.ITtaDeptIn;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaDeptInServer")
public class TtaDeptInServer extends BaseCommonServer<TtaDeptInEntity_HI> implements ITtaDeptIn{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaDeptInServer.class);

	@Autowired
	private TtaDeptInDAO_HI ttaDeptInDAO_HI;

	public TtaDeptInServer() {
		super();
	}

	@Override
	public void saveOrUpdateBatchDept(LinkedHashSet<TtaDeptInEntity_HI> list) {
		ttaDeptInDAO_HI.saveOrUpdateAll(list);
		ttaDeptInDAO_HI.callProBaseDeptPositionAssign();
	}
}
