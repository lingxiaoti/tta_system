package com.sie.saaf.bpm.model.dao.readonly;

import com.sie.saaf.bpm.model.entities.readonly.ActStatisticProcessEntity_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("actStatisticProcessDAO_HI_RO")
public class ActStatisticProcessDAO_HI_RO extends DynamicViewObjectImpl<ActStatisticProcessEntity_RO> {
	
	public ActStatisticProcessDAO_HI_RO() {
		super();
	}
}
