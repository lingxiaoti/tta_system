package com.sie.watsons.base.pos.supplierinfo.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierBankEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierBankDAO_HI")
public class EquPosSupplierBankDAO_HI extends BaseCommonDAO_HI<EquPosSupplierBankEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierBankDAO_HI.class);

	public EquPosSupplierBankDAO_HI() {
		super();
	}

}
