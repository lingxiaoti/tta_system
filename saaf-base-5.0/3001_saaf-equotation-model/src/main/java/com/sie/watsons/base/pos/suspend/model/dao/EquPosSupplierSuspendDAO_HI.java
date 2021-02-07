package com.sie.watsons.base.pos.suspend.model.dao;

import com.yhg.hibernate.core.dao.ViewObjectImpl;
import com.sie.watsons.base.pos.suspend.model.entities.EquPosSupplierSuspendEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierSuspendDAO_HI")
public class EquPosSupplierSuspendDAO_HI extends ViewObjectImpl<EquPosSupplierSuspendEntity_HI>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierSuspendDAO_HI.class);
	public EquPosSupplierSuspendDAO_HI() {
		super();
	}

	@Override
	public Object save(EquPosSupplierSuspendEntity_HI entity) {
		return super.save(entity);
	}
}
