package com.sie.saaf.bpm.model.dao.readonly;

import com.sie.saaf.bpm.model.entities.readonly.ActBpmModelEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("actBpmModelDAO_HI_RO")
public class ActBpmModelDAO_HI_RO extends DynamicViewObjectImpl<ActBpmModelEntity_HI_RO> {

	public ActBpmModelDAO_HI_RO() {
		super();
	}

}
