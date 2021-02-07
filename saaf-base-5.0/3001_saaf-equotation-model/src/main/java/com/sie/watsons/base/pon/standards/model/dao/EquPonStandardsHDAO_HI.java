package com.sie.watsons.base.pon.standards.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.standards.model.entities.EquPonStandardsHEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonStandardsHDAO_HI")
public class EquPonStandardsHDAO_HI extends BaseCommonDAO_HI<EquPonStandardsHEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonStandardsHDAO_HI.class);

	public EquPonStandardsHDAO_HI() {
		super();
	}

}
