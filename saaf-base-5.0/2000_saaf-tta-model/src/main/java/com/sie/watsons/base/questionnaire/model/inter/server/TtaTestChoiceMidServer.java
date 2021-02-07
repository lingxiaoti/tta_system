package com.sie.watsons.base.questionnaire.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.questionnaire.model.entities.TtaTestChoiceMidEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.questionnaire.model.inter.ITtaTestChoiceMid;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaTestChoiceMidServer")
public class TtaTestChoiceMidServer extends BaseCommonServer<TtaTestChoiceMidEntity_HI> implements ITtaTestChoiceMid{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTestChoiceMidServer.class);

	@Autowired
	private ViewObject<TtaTestChoiceMidEntity_HI> ttaTestChoiceMidDAO_HI;

	public TtaTestChoiceMidServer() {
		super();
	}

}
