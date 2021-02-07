package com.sie.watsons.base.elecsign.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.elecsign.model.entities.TtaInterfaceRecordEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.elecsign.model.inter.ITtaInterfaceRecord;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaInterfaceRecordServer")
public class TtaInterfaceRecordServer extends BaseCommonServer<TtaInterfaceRecordEntity_HI> implements ITtaInterfaceRecord{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaInterfaceRecordServer.class);

	@Autowired
	private ViewObject<TtaInterfaceRecordEntity_HI> ttaInterfaceRecordDAO_HI;

	public TtaInterfaceRecordServer() {
		super();
	}

}
