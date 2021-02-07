package com.sie.watsons.base.supplier.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplier.model.entities.TtaSupplierAttrEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSupplierAttrDAO_HI")
public class TtaSupplierAttrDAO_HI extends BaseCommonDAO_HI<TtaSupplierAttrEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierAttrDAO_HI.class);

	public TtaSupplierAttrDAO_HI() {
		super();
	}

}
