package com.sie.watsons.base.pos.csrAudit.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.csrAudit.model.entities.EquPosSupplierCsrAuditEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierCsrAuditDAO_HI")
public class EquPosSupplierCsrAuditDAO_HI extends BaseCommonDAO_HI<EquPosSupplierCsrAuditEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCsrAuditDAO_HI.class);

	public EquPosSupplierCsrAuditDAO_HI() {
		super();
	}

}
