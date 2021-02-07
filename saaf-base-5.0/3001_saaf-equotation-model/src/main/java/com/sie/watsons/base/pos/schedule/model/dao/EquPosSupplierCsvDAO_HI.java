package com.sie.watsons.base.pos.schedule.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.schedule.model.entities.EquPosSupplierCsvEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierCsvDAO_HI")
public class EquPosSupplierCsvDAO_HI extends BaseCommonDAO_HI<EquPosSupplierCsvEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCsvDAO_HI.class);

	public EquPosSupplierCsvDAO_HI() {
		super();
	}

}
