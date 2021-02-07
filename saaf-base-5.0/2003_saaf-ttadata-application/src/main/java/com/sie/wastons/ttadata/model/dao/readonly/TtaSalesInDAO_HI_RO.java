package com.sie.wastons.ttadata.model.dao.readonly;

import com.sie.wastons.ttadata.model.entities.readyonly.TtaSalesInEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("ttaSalesInDAO_HI_RO")
public class TtaSalesInDAO_HI_RO extends DynamicViewObjectImpl<TtaSalesInEntity_HI_RO>  {
	//private static final Logger LOGGER = LoggerFactory.getLogger(TtaSalesInDAO_HI_RO.class);
	public TtaSalesInDAO_HI_RO() {
		super();
	}

}
