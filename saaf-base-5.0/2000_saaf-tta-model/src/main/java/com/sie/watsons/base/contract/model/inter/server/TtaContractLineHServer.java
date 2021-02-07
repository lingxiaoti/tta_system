package com.sie.watsons.base.contract.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.contract.model.entities.TtaContractLineHEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.contract.model.inter.ITtaContractLineH;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaContractLineHServer")
public class TtaContractLineHServer extends BaseCommonServer<TtaContractLineHEntity_HI> implements ITtaContractLineH{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractLineHServer.class);

	@Autowired
	private ViewObject<TtaContractLineHEntity_HI> ttaContractLineHDAO_HI;

	public TtaContractLineHServer() {
		super();
	}

	@Override
	public TtaContractLineHEntity_HI saveOrUpdateAll(JSONObject paramsJSON, int userId) throws Exception {
		return null;
	}
}
