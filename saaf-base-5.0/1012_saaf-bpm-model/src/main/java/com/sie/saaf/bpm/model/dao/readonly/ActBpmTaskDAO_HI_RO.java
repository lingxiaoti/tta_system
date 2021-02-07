package com.sie.saaf.bpm.model.dao.readonly;

import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("actBpmTaskDAO_HI_RO")
public class ActBpmTaskDAO_HI_RO extends DynamicViewObjectImpl<ActBpmTaskEntity_HI_RO> {
    
	public ActBpmTaskDAO_HI_RO() {
		super();
	}

}
