package com.sie.watsons.base.elecsign.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.elecsign.model.entities.TtaAttrCheckItemEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaAttrCheckItemDAO_HI")
public class TtaAttrCheckItemDAO_HI extends BaseCommonDAO_HI<TtaAttrCheckItemEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAttrCheckItemDAO_HI.class);

	public TtaAttrCheckItemDAO_HI() {
		super();
	}

}
