package com.sie.watsons.base.report.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsOrderDetailEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaFreeGoodsOrderDetailDAO_HI_RO")
public class TtaFreeGoodsOrderDetailDAO_HI_RO extends DynamicViewObjectImpl<TtaFreeGoodsOrderDetailEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFreeGoodsOrderDetailDAO_HI_RO.class);
	public TtaFreeGoodsOrderDetailDAO_HI_RO() {
		super();
	}

}
