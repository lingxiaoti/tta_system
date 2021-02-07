package com.sie.watsons.base.pon.information.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pon.information.model.entities.EquPonInformationRejectionEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pon.information.model.inter.IEquPonInformationRejection;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPonInformationRejectionServer")
public class EquPonInformationRejectionServer extends BaseCommonServer<EquPonInformationRejectionEntity_HI> implements IEquPonInformationRejection{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonInformationRejectionServer.class);

	@Autowired
	private ViewObject<EquPonInformationRejectionEntity_HI> equPonInformationRejectionDAO_HI;

	public EquPonInformationRejectionServer() {
		super();
	}

}
