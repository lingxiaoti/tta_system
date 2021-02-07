package com.sie.watsons.base.elecsign.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.elecsign.model.entities.TtaElecConAttrLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaElecConAttrLineDAO_HI")
public class TtaElecConAttrLineDAO_HI extends BaseCommonDAO_HI<TtaElecConAttrLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaElecConAttrLineDAO_HI.class);

	public TtaElecConAttrLineDAO_HI() {
		super();
	}

}
