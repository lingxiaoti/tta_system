package com.sie.watsons.base.contract.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.contract.model.entities.TtaTermsAcCountEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaTermsAcCountDAO_HI")
public class TtaTermsAcCountDAO_HI extends BaseCommonDAO_HI<TtaTermsAcCountEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsAcCountDAO_HI.class);

	public TtaTermsAcCountDAO_HI() {
		super();
	}

}
