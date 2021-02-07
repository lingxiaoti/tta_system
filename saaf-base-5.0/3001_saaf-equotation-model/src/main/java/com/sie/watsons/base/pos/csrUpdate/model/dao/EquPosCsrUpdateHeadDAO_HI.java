package com.sie.watsons.base.pos.csrUpdate.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.csrUpdate.model.entities.EquPosCsrUpdateHeadEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosCsrUpdateHeadDAO_HI")
public class EquPosCsrUpdateHeadDAO_HI extends BaseCommonDAO_HI<EquPosCsrUpdateHeadEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosCsrUpdateHeadDAO_HI.class);

	public EquPosCsrUpdateHeadDAO_HI() {
		super();
	}

}
