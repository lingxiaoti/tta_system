package com.sie.watsons.base.withdrawal.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaSupplierItemLineServer")
public class TtaSupplierItemLineServer extends BaseCommonServer<TtaSupplierItemLineEntity_HI> implements ITtaSupplierItemLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemLineServer.class);

	@Autowired
	private ViewObject<TtaSupplierItemLineEntity_HI> ttaSupplierItemLineDAO_HI;

	public TtaSupplierItemLineServer() {
		super();
	}

}
