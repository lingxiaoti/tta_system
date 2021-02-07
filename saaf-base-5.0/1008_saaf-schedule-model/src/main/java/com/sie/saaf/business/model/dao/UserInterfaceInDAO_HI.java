package com.sie.saaf.business.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.business.model.entities.UserInterfaceInEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("userInterfaceInDAO_HI")
public class UserInterfaceInDAO_HI extends BaseCommonDAO_HI<UserInterfaceInEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserInterfaceInDAO_HI.class);

	public UserInterfaceInDAO_HI() {
		super();
	}

}
