package com.sie.watsons.base.contract.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.contract.model.entities.TtaContractLineHEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaContractLineHDAO_HI")
public class TtaContractLineHDAO_HI extends BaseCommonDAO_HI<TtaContractLineHEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractLineHDAO_HI.class);

	public TtaContractLineHDAO_HI() {
		super();
	}

}
