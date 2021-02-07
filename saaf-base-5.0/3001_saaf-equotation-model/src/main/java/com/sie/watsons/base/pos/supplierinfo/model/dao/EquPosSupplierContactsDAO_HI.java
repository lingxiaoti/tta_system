package com.sie.watsons.base.pos.supplierinfo.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierContactsEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierContactsDAO_HI")
public class EquPosSupplierContactsDAO_HI extends BaseCommonDAO_HI<EquPosSupplierContactsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierContactsDAO_HI.class);

	public EquPosSupplierContactsDAO_HI() {
		super();
	}

}
