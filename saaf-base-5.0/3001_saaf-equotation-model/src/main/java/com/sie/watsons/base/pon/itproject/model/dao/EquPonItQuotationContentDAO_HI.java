package com.sie.watsons.base.pon.itproject.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItQuotationContentEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonItQuotationContentDAO_HI")
public class EquPonItQuotationContentDAO_HI extends BaseCommonDAO_HI<EquPonItQuotationContentEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItQuotationContentDAO_HI.class);

	public EquPonItQuotationContentDAO_HI() {
		super();
	}

}
