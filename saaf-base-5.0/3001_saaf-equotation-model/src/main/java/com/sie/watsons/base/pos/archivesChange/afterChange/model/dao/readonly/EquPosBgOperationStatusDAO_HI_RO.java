package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgOperationStatusEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosBgOperationStatusDAO_HI_RO")
public class EquPosBgOperationStatusDAO_HI_RO extends DynamicViewObjectImpl<EquPosBgOperationStatusEntity_HI_RO>  {
	public EquPosBgOperationStatusDAO_HI_RO() {
		super();
	}

}
