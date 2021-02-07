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
import com.sie.watsons.base.elecsign.model.entities.TtaElecSignResultLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.elecsign.model.inter.ITtaElecSignResultLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaElecSignResultLineServer")
public class TtaElecSignResultLineServer extends BaseCommonServer<TtaElecSignResultLineEntity_HI> implements ITtaElecSignResultLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaElecSignResultLineServer.class);

	@Autowired
	private ViewObject<TtaElecSignResultLineEntity_HI> ttaElecSignResultLineDAO_HI;

	public TtaElecSignResultLineServer() {
		super();
	}

}
