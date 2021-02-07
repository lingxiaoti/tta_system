package com.sie.watsons.base.ttasastdtpl.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaStdApplyHeaderEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaStdApplyHeaderDAO_HI_RO")
public class TtaStdApplyHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaStdApplyHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaStdApplyHeaderDAO_HI_RO.class);
	public TtaStdApplyHeaderDAO_HI_RO() {
		super();
	}

}
