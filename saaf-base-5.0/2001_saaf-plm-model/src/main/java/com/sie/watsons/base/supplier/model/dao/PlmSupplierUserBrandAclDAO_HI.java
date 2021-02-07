package com.sie.watsons.base.supplier.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplier.model.entities.PlmSupplierUserBrandAclEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmSupplierUserBrandAclDAO_HI")
public class PlmSupplierUserBrandAclDAO_HI extends BaseCommonDAO_HI<PlmSupplierUserBrandAclEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierUserBrandAclDAO_HI.class);

	public PlmSupplierUserBrandAclDAO_HI() {
		super();
	}

}
