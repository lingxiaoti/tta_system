package com.sie.saaf.business.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.business.model.entities.readonly.TtaDeptInEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ttaDeptInDAO_HI_RO")
public class TtaDeptInDAO_HI_RO extends DynamicViewObjectImpl<TtaDeptInEntity_HI_RO> {
	private static final Logger logger = LoggerFactory.getLogger(TtaDeptInDAO_HI_RO.class);

	public TtaDeptInDAO_HI_RO() {
		super();
	}
}