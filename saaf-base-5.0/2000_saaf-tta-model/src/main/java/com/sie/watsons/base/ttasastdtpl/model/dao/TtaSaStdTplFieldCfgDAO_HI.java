package com.sie.watsons.base.ttasastdtpl.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaStdTplFieldCfgEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSaStdTplFieldCfgDAO_HI")
public class TtaSaStdTplFieldCfgDAO_HI extends BaseCommonDAO_HI<TtaSaStdTplFieldCfgEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdTplFieldCfgDAO_HI.class);

	public TtaSaStdTplFieldCfgDAO_HI() {
		super();
	}

}
