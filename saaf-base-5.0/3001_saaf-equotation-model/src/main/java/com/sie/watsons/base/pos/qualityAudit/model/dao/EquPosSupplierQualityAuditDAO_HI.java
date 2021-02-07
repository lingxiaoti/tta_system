package com.sie.watsons.base.pos.qualityAudit.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.qualityAudit.model.entities.EquPosSupplierQualityAuditEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierQualityAuditDAO_HI")
public class EquPosSupplierQualityAuditDAO_HI extends BaseCommonDAO_HI<EquPosSupplierQualityAuditEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierQualityAuditDAO_HI.class);

	public EquPosSupplierQualityAuditDAO_HI() {
		super();
	}

}
