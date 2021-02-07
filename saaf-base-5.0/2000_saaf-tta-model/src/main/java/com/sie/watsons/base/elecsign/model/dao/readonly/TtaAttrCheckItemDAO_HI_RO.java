package com.sie.watsons.base.elecsign.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.elecsign.model.entities.readonly.TtaAttrCheckItemEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaAttrCheckItemDAO_HI_RO")
public class TtaAttrCheckItemDAO_HI_RO extends DynamicViewObjectImpl<TtaAttrCheckItemEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAttrCheckItemDAO_HI_RO.class);
	public TtaAttrCheckItemDAO_HI_RO() {
		super();
	}

}
