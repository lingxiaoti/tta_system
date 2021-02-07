package com.sie.watsons.base.ttasastdtpl.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdTplFieldCfgEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaSaStdTplFieldCfgDAO_HI_RO")
public class TtaSaStdTplFieldCfgDAO_HI_RO extends DynamicViewObjectImpl<TtaSaStdTplFieldCfgEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdTplFieldCfgDAO_HI_RO.class);
	public TtaSaStdTplFieldCfgDAO_HI_RO() {
		super();
	}

}
