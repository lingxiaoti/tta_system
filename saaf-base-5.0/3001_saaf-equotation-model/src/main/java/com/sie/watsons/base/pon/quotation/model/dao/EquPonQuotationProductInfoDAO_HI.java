package com.sie.watsons.base.pon.quotation.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationProductInfoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonQuotationProductInfoDAO_HI")
public class EquPonQuotationProductInfoDAO_HI extends BaseCommonDAO_HI<EquPonQuotationProductInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationProductInfoDAO_HI.class);

	public EquPonQuotationProductInfoDAO_HI() {
		super();
	}

}
