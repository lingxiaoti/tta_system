package com.sie.watsons.base.pos.obfile.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.obfile.model.entities.EquPosZzscObFileEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosZzscObFileDAO_HI")
public class EquPosZzscObFileDAO_HI extends BaseCommonDAO_HI<EquPosZzscObFileEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscObFileDAO_HI.class);

	public EquPosZzscObFileDAO_HI() {
		super();
	}

}
