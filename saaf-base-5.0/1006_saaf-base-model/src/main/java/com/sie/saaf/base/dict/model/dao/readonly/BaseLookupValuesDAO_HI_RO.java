package com.sie.saaf.base.dict.model.dao.readonly;

import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseLookupValuesDAO_HI_RO")
public class BaseLookupValuesDAO_HI_RO extends DynamicViewObjectImpl<BaseLookupValuesEntity_HI_RO> {
	public BaseLookupValuesDAO_HI_RO() {
		super();
	}

}
