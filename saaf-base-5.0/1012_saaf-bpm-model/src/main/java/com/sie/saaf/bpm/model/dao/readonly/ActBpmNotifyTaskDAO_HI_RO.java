package com.sie.saaf.bpm.model.dao.readonly;

import com.sie.saaf.bpm.model.entities.readonly.ActBpmNotifyTaskEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("actBpmNotifyTaskDAO_HI_RO")
public class ActBpmNotifyTaskDAO_HI_RO extends DynamicViewObjectImpl<ActBpmNotifyTaskEntity_HI_RO> {
    
	public ActBpmNotifyTaskDAO_HI_RO() {
		super();
	}

}
