package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaNonObInfoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmSupplierQaNonObInfoDAO_HI")
public class PlmSupplierQaNonObInfoDAO_HI extends BaseCommonDAO_HI<PlmSupplierQaNonObInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierQaNonObInfoDAO_HI.class);
	public PlmSupplierQaNonObInfoDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSupplierQaNonObInfoEntity_HI entity) {
		return super.save(entity);
	}
}
