package com.sie.watsons.base.ttasastdtpl.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdFieldCfgLineEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaSaStdFieldCfgLineDAO_HI_RO")
public class TtaSaStdFieldCfgLineDAO_HI_RO extends DynamicViewObjectImpl<TtaSaStdFieldCfgLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdFieldCfgLineDAO_HI_RO.class);
	public TtaSaStdFieldCfgLineDAO_HI_RO() {
		super();
	}

}
