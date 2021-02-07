package com.sie.watsons.base.usergroupdeptbrand.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.TtaUserGroupDeptBrandEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaUserGroupDeptBrandDAO_HI")
public class TtaUserGroupDeptBrandDAO_HI extends BaseCommonDAO_HI<TtaUserGroupDeptBrandEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaUserGroupDeptBrandDAO_HI.class);

	public TtaUserGroupDeptBrandDAO_HI() {
		super();
	}

}
