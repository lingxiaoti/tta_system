package com.sie.watsons.base.pos.suspend.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.suspend.model.entities.EquPosSupplierSuspendFileEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierSuspendFileDAO_HI")
public class EquPosSupplierSuspendFileDAO_HI extends BaseCommonDAO_HI<EquPosSupplierSuspendFileEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierSuspendFileDAO_HI.class);

	public EquPosSupplierSuspendFileDAO_HI() {
		super();
	}

}
