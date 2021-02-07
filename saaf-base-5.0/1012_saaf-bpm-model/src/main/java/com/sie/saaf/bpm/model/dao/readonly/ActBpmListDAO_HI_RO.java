package com.sie.saaf.bpm.model.dao.readonly;

import com.sie.saaf.bpm.model.entities.readonly.ActBpmListEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("actBpmListDAO_HI_RO")
public class ActBpmListDAO_HI_RO extends DynamicViewObjectImpl<ActBpmListEntity_HI_RO> {
    
	public ActBpmListDAO_HI_RO() {
		super();
	}

}
