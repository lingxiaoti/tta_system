package com.sie.watsons.base.ttaImport.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaBeoiBillLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaBeoiBillLineDAO_HI")
public class TtaBeoiBillLineDAO_HI extends BaseCommonDAO_HI<TtaBeoiBillLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBeoiBillLineDAO_HI.class);

	public TtaBeoiBillLineDAO_HI() {
		super();
	}

}
