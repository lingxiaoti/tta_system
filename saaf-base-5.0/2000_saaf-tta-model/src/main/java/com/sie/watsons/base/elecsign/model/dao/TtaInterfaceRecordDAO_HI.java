package com.sie.watsons.base.elecsign.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.elecsign.model.entities.TtaInterfaceRecordEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaInterfaceRecordDAO_HI")
public class TtaInterfaceRecordDAO_HI extends BaseCommonDAO_HI<TtaInterfaceRecordEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaInterfaceRecordDAO_HI.class);

	public TtaInterfaceRecordDAO_HI() {
		super();
	}

}
