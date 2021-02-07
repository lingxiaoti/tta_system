package com.sie.watsons.base.usergroupdeptbrand.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.TtaUserDataTypeEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaUserDataTypeDAO_HI")
public class TtaUserDataTypeDAO_HI extends BaseCommonDAO_HI<TtaUserDataTypeEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaUserDataTypeDAO_HI.class);

	public TtaUserDataTypeDAO_HI() {
		super();
	}

}
