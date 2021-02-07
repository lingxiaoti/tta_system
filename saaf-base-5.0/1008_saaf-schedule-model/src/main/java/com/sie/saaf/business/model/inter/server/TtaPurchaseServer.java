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
import com.sie.saaf.business.model.entities.TtaPurchaseEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.business.model.inter.ITtaPurchase;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaPurchaseServer")
public class TtaPurchaseServer extends BaseCommonServer<TtaPurchaseEntity_HI> implements ITtaPurchase{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPurchaseServer.class);

	@Autowired
	private ViewObject<TtaPurchaseEntity_HI> ttaPurchaseDAO_HI;

	public TtaPurchaseServer() {
		super();
	}

	@Override
	public List<TtaPurchaseEntity_HI> saveBatchPurchase(List<TtaPurchaseEntity_HI> ttaPurchaseEntity_his) {
		return null;
	}
}
