package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsOrderDetailEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaFreeGoodsOrderDetailDAO_HI")
public class TtaFreeGoodsOrderDetailDAO_HI extends BaseCommonDAO_HI<TtaFreeGoodsOrderDetailEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFreeGoodsOrderDetailDAO_HI.class);

	public TtaFreeGoodsOrderDetailDAO_HI() {
		super();
	}

}
