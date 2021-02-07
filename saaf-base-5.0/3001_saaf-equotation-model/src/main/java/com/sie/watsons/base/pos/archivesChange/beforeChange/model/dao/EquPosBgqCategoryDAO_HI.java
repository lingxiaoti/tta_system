package com.sie.watsons.base.pos.archivesChange.beforeChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqCategoryEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgqCategoryDAO_HI")
public class EquPosBgqCategoryDAO_HI extends BaseCommonDAO_HI<EquPosBgqCategoryEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqCategoryDAO_HI.class);

	public EquPosBgqCategoryDAO_HI() {
		super();
	}

}
