package com.sie.watsons.base.pos.qualificationreview.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscAddressesEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosZzscAddressesDAO_HI")
public class EquPosZzscAddressesDAO_HI extends BaseCommonDAO_HI<EquPosZzscAddressesEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscAddressesDAO_HI.class);

	public EquPosZzscAddressesDAO_HI() {
		super();
	}

}
