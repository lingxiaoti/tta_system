package com.sie.watsons.base.pos.qualificationreview.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCategoryEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosZzscCategoryDAO_HI")
public class EquPosZzscCategoryDAO_HI extends BaseCommonDAO_HI<EquPosZzscCategoryEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscCategoryDAO_HI.class);

	public EquPosZzscCategoryDAO_HI() {
		super();
	}

}
