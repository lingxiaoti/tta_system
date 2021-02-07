package com.sie.watsons.base.pos.supplierinfo.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierCredentialsEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierCredentialsDAO_HI")
public class EquPosSupplierCredentialsDAO_HI extends BaseCommonDAO_HI<EquPosSupplierCredentialsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCredentialsDAO_HI.class);

	public EquPosSupplierCredentialsDAO_HI() {
		super();
	}

}
