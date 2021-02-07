package com.sie.saaf.business.model.inter.server;

import java.util.LinkedHashSet;

import com.sie.saaf.business.model.dao.TtaOrgInDAO_HI;
import com.sie.saaf.business.model.dao.readonly.TtaOrgInDAO_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.business.model.entities.TtaOrgInEntity_HI;
import com.sie.saaf.business.model.inter.ITtaOrgIn;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("ttaOrgInServer")
public class TtaOrgInServer extends BaseCommonServer<TtaOrgInEntity_HI> implements ITtaOrgIn{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOrgInServer.class);

	@Autowired
	private  TtaOrgInDAO_HI ttaOrgInDAO_HI;

	@Autowired
	private TtaOrgInDAO_HI_RO ttaOrgInDAOHiRo;

	public TtaOrgInServer() {
		super();
	}

	@Override
	public void saveOrUpdateBatchOrg(LinkedHashSet<TtaOrgInEntity_HI> list) {
		ttaOrgInDAO_HI.saveOrUpdateAll(list);
	}

	@Override
	public void callProBaseOrganization() {
		ttaOrgInDAO_HI.callProName("PRO_BASE_ORGANIZATION");
		
	}

}
