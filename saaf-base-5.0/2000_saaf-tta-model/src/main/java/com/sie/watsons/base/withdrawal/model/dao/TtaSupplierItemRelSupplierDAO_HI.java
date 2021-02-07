package com.sie.watsons.base.withdrawal.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemRelSupplierEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSupplierItemRelSupplierDAO_HI")
public class TtaSupplierItemRelSupplierDAO_HI extends BaseCommonDAO_HI<TtaSupplierItemRelSupplierEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemRelSupplierDAO_HI.class);

	public TtaSupplierItemRelSupplierDAO_HI() {
		super();
	}

}
