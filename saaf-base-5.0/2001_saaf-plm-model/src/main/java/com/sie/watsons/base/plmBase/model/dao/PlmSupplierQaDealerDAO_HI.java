package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaDealerEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmSupplierQaDealerDAO_HI")
public class PlmSupplierQaDealerDAO_HI extends BaseCommonDAO_HI<PlmSupplierQaDealerEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierQaDealerDAO_HI.class);
	public PlmSupplierQaDealerDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSupplierQaDealerEntity_HI entity) {
		return super.save(entity);
	}
}
