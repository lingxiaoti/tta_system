package com.sie.watsons.base.ttasastdtpl.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdTplDefHeaderEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaSaStdTplDefHeaderDAO_HI_RO")
public class TtaSaStdTplDefHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaSaStdTplDefHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdTplDefHeaderDAO_HI_RO.class);
	public TtaSaStdTplDefHeaderDAO_HI_RO() {
		super();
	}

}
