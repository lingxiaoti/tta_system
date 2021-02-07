package com.sie.watsons.base.pon.information.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonInformationRejectionEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonInformationRejectionDAO_HI")
public class EquPonInformationRejectionDAO_HI extends BaseCommonDAO_HI<EquPonInformationRejectionEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonInformationRejectionDAO_HI.class);

	public EquPonInformationRejectionDAO_HI() {
		super();
	}

}
