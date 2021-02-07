package com.sie.watsons.base.pos.supplierinfo.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosProductionInfoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosProductionInfoDAO_HI")
public class EquPosProductionInfoDAO_HI extends BaseCommonDAO_HI<EquPosProductionInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosProductionInfoDAO_HI.class);

	public EquPosProductionInfoDAO_HI() {
		super();
	}

}
