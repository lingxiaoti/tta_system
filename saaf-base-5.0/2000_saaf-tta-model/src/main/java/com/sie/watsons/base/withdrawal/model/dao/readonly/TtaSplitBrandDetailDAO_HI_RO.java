package com.sie.watsons.base.withdrawal.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSplitBrandDetailEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component("ttaSplitBrandDetailDAO_HI_RO")
public class TtaSplitBrandDetailDAO_HI_RO extends DynamicViewObjectImpl<TtaSplitBrandDetailEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSplitBrandDetailDAO_HI_RO.class);
	public TtaSplitBrandDetailDAO_HI_RO() {
		super();
	}

}
