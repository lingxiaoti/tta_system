package com.sie.saaf.base.commmon.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.base.commmon.model.entities.BaseWatsonsBpmNodeUserEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("baseWatsonsBpmNodeUserDAO_HI")
public class BaseWatsonsBpmNodeUserDAO_HI extends BaseCommonDAO_HI<BaseWatsonsBpmNodeUserEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseWatsonsBpmNodeUserDAO_HI.class);

	public BaseWatsonsBpmNodeUserDAO_HI() {
		super();
	}

}
