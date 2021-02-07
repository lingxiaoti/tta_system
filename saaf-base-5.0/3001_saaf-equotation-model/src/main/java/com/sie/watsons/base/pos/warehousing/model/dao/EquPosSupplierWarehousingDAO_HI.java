package com.sie.watsons.base.pos.warehousing.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.warehousing.model.entities.EquPosSupplierWarehousingEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierWarehousingDAO_HI")
public class EquPosSupplierWarehousingDAO_HI extends BaseCommonDAO_HI<EquPosSupplierWarehousingEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierWarehousingDAO_HI.class);

	public EquPosSupplierWarehousingDAO_HI() {
		super();
	}

}
