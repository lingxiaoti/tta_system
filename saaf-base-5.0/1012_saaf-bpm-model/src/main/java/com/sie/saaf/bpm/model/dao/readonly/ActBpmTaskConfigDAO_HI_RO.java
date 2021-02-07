package com.sie.saaf.bpm.model.dao.readonly;

import com.sie.saaf.bpm.model.entities.readonly.ActBpmTaskConfigEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("actBpmTaskConfigDAO_HI_RO")
public class ActBpmTaskConfigDAO_HI_RO extends DynamicViewObjectImpl<ActBpmTaskConfigEntity_HI_RO> {
    
	public ActBpmTaskConfigDAO_HI_RO() {
		super();
	}

}
