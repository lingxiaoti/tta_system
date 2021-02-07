package com.sie.watsons.base.report.model.dao.readonly;

import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsPolistEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaFreeGoodsPolistDAO_HI_RO")
public class TtaFreeGoodsPolistDAO_HI_RO extends DynamicViewObjectImpl<TtaFreeGoodsPolistEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaFreeGoodsPolistDAO_HI_RO.class);
	public TtaFreeGoodsPolistDAO_HI_RO() {
		super();
	}

}
