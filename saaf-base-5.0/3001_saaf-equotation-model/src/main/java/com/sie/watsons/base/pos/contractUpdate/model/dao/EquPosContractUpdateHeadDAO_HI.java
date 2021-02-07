package com.sie.watsons.base.pos.contractUpdate.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.contractUpdate.model.entities.EquPosContractUpdateHeadEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosContractUpdateHeadDAO_HI")
public class EquPosContractUpdateHeadDAO_HI extends BaseCommonDAO_HI<EquPosContractUpdateHeadEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosContractUpdateHeadDAO_HI.class);

	public EquPosContractUpdateHeadDAO_HI() {
		super();
	}

}
