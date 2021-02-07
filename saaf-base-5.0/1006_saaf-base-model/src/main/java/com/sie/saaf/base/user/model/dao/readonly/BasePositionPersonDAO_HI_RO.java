package com.sie.saaf.base.user.model.dao.readonly;

import com.sie.saaf.base.user.model.entities.readonly.BasePositionPerson_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("basePositionPersonDAO_HI_RO")
public class BasePositionPersonDAO_HI_RO extends DynamicViewObjectImpl<BasePositionPerson_HI_RO> {
	public BasePositionPersonDAO_HI_RO() {
		super();
	}

}
