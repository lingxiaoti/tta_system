package com.sie.watsons.base.contract.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.contract.model.entities.TtaContractRecordHeaderEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaContractRecordHeaderDAO_HI")
public class TtaContractRecordHeaderDAO_HI extends BaseCommonDAO_HI<TtaContractRecordHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractRecordHeaderDAO_HI.class);

	public TtaContractRecordHeaderDAO_HI() {
		super();
	}

}
