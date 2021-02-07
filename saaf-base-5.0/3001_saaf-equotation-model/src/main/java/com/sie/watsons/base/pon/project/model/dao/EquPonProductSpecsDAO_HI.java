package com.sie.watsons.base.pon.project.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonProductSpecsEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonProductSpecsDAO_HI")
public class EquPonProductSpecsDAO_HI extends BaseCommonDAO_HI<EquPonProductSpecsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonProductSpecsDAO_HI.class);

	public EquPonProductSpecsDAO_HI() {
		super();
	}

}
