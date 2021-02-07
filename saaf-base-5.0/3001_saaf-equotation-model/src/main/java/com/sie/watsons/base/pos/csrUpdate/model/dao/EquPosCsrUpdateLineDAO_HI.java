package com.sie.watsons.base.pos.csrUpdate.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.csrUpdate.model.entities.EquPosCsrUpdateLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosCsrUpdateLineDAO_HI")
public class EquPosCsrUpdateLineDAO_HI extends BaseCommonDAO_HI<EquPosCsrUpdateLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCsrUpdateLineDAO_HI.class);

	public EquPosCsrUpdateLineDAO_HI() {
		super();
	}

}
