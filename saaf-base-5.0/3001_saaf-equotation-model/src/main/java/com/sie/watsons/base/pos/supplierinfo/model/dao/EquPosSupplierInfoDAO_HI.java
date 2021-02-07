package com.sie.watsons.base.pos.supplierinfo.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierInfoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierInfoDAO_HI")
public class EquPosSupplierInfoDAO_HI extends BaseCommonDAO_HI<EquPosSupplierInfoEntity_HI> {

	public EquPosSupplierInfoDAO_HI() {
		super();
	}

}
