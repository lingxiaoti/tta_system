package com.sie.saaf.bpm.model.dao.readonly;

import com.sie.saaf.bpm.model.entities.readonly.ActStatisticTaskEntity_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("actStatisticTaskDAO_HI_RO")
public class ActStatisticTaskDAO_HI_RO extends DynamicViewObjectImpl<ActStatisticTaskEntity_RO> {
	public ActStatisticTaskDAO_HI_RO() {
		super();
	}

}
