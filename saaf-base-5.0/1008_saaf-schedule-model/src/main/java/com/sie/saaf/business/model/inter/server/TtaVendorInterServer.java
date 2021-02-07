package com.sie.saaf.business.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.saaf.business.model.entities.TtaVendorInterEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.business.model.inter.ITtaVendorInter;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaVendorInterServer")
public class TtaVendorInterServer extends BaseCommonServer<TtaVendorInterEntity_HI> implements ITtaVendorInter{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaVendorInterServer.class);

	@Autowired
	private ViewObject<TtaVendorInterEntity_HI> ttaVendorInterDAO_HI;

	public TtaVendorInterServer() {
		super();
	}

	@Override
	public List<TtaVendorInterEntity_HI> saveBatchVendor(List<TtaVendorInterEntity_HI> ttaVendorInterEntity_his) {
		return null;
	}
}
