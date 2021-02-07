package com.sie.watsons.base.contract.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.contract.model.entities.TtaContractSpecialHeaderEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaContractSpecialHeaderDAO_HI")
public class TtaContractSpecialHeaderDAO_HI extends BaseCommonDAO_HI<TtaContractSpecialHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractSpecialHeaderDAO_HI.class);

	public TtaContractSpecialHeaderDAO_HI() {
		super();
	}

}
