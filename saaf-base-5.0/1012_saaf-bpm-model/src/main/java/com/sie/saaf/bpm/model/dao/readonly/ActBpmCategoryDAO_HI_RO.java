package com.sie.saaf.bpm.model.dao.readonly;

import com.sie.saaf.bpm.model.entities.readonly.ActBpmCategoryEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("actBpmCategoryDAO_HI_RO")
public class ActBpmCategoryDAO_HI_RO extends DynamicViewObjectImpl<ActBpmCategoryEntity_HI_RO> {
    
	public ActBpmCategoryDAO_HI_RO() {
		super();
	}

}
