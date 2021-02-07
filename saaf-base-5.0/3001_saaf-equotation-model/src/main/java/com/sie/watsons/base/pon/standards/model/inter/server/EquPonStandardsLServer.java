package com.sie.watsons.base.pon.standards.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.standards.model.entities.EquPonStandardsLEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.standards.model.inter.IEquPonStandardsL;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonStandardsLServer")
public class EquPonStandardsLServer extends BaseCommonServer<EquPonStandardsLEntity_HI> implements IEquPonStandardsL{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonStandardsLServer.class);

	@Autowired
	private ViewObject<EquPonStandardsLEntity_HI> equPonStandardsLDAO_HI;

	public EquPonStandardsLServer() {
		super();
	}

}
