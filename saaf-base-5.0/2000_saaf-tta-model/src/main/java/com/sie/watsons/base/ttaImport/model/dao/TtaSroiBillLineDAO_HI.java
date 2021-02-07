package com.sie.watsons.base.ttaImport.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaSroiBillLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSroiBillLineDAO_HI")
public class TtaSroiBillLineDAO_HI extends BaseCommonDAO_HI<TtaSroiBillLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSroiBillLineDAO_HI.class);

	public TtaSroiBillLineDAO_HI() {
		super();
	}

}
