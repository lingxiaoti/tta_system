package com.sie.watsons.base.pon.quotation.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationPriceDocEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonQuotationPriceDocDAO_HI")
public class EquPonQuotationPriceDocDAO_HI extends BaseCommonDAO_HI<EquPonQuotationPriceDocEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationPriceDocDAO_HI.class);

	public EquPonQuotationPriceDocDAO_HI() {
		super();
	}

}
