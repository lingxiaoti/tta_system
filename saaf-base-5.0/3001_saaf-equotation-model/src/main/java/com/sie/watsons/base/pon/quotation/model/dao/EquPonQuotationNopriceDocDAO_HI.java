package com.sie.watsons.base.pon.quotation.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationNopriceDocEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonQuotationNopriceDocDAO_HI")
public class EquPonQuotationNopriceDocDAO_HI extends BaseCommonDAO_HI<EquPonQuotationNopriceDocEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationNopriceDocDAO_HI.class);

	public EquPonQuotationNopriceDocDAO_HI() {
		super();
	}

}
