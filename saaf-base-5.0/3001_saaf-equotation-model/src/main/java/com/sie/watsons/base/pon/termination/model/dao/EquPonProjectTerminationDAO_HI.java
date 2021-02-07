package com.sie.watsons.base.pon.termination.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.termination.model.entities.EquPonProjectTerminationEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonProjectTerminationDAO_HI")
public class EquPonProjectTerminationDAO_HI extends BaseCommonDAO_HI<EquPonProjectTerminationEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonProjectTerminationDAO_HI.class);

	public EquPonProjectTerminationDAO_HI() {
		super();
	}

}
