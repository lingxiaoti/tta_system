package com.sie.saaf.bpm.model.dao.readonly;

import com.sie.saaf.bpm.model.entities.readonly.ActBpmCommunicateEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("actBpmCommunicateDAO_HI_RO")
public class ActBpmCommunicateDAO_HI_RO extends DynamicViewObjectImpl<ActBpmCommunicateEntity_HI_RO> {
    
	public ActBpmCommunicateDAO_HI_RO() {
		super();
	}

}
