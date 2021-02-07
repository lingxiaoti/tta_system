package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductGoodsHeadDAO_HI_RO")
public class PlmProductGoodsHeadDAO_HI_RO extends DynamicViewObjectImpl<PlmProductHeadEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductGoodsHeadDAO_HI_RO.class);
	public PlmProductGoodsHeadDAO_HI_RO() {
		super();
	}

}
