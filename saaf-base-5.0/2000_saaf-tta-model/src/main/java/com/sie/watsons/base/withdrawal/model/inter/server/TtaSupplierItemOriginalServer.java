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
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemOriginalEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemOriginal;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaSupplierItemOriginalServer")
public class TtaSupplierItemOriginalServer extends BaseCommonServer<TtaSupplierItemOriginalEntity_HI> implements ITtaSupplierItemOriginal{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemOriginalServer.class);

	@Autowired
	private ViewObject<TtaSupplierItemOriginalEntity_HI> ttaSupplierItemOriginalDAO_HI;

	public TtaSupplierItemOriginalServer() {
		super();
	}

}
