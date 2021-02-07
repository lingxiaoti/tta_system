package com.sie.saaf.bpm.model.dao.readonly;

import com.sie.saaf.bpm.model.entities.readonly.ActBpmHiTaskEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("actBpmHiTaskDAO_HI_RO")
public class ActBpmHiTaskDAO_HI_RO extends DynamicViewObjectImpl<ActBpmHiTaskEntity_HI_RO> {
    
	public ActBpmHiTaskDAO_HI_RO() {
		super();
	}

}
