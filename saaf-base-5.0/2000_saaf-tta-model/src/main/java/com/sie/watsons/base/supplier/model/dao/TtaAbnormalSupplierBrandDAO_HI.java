package com.sie.watsons.base.supplier.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplier.model.entities.TtaAbnormalSupplierBrandEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaAbnormalSupplierBrandDAO_HI")
public class TtaAbnormalSupplierBrandDAO_HI extends BaseCommonDAO_HI<TtaAbnormalSupplierBrandEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAbnormalSupplierBrandDAO_HI.class);

	public TtaAbnormalSupplierBrandDAO_HI() {
		super();
	}

}
