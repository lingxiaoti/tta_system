package com.sie.watsons.base.pos.category.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.category.model.entities.EquPosSupplierCategoryEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierCategoryDAO_HI")
public class EquPosSupplierCategoryDAO_HI extends BaseCommonDAO_HI<EquPosSupplierCategoryEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCategoryDAO_HI.class);

	public EquPosSupplierCategoryDAO_HI() {
		super();
	}

}
