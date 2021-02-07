package com.sie.wastons.ttadata.model.dao.readonly;

import com.sie.wastons.ttadata.model.entities.readyonly.ActBpmListEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("actBpmListDAO_HI_RO")
public class ActBpmListDAO_HI_RO extends DynamicViewObjectImpl<ActBpmListEntity_HI_RO>  {
	public ActBpmListDAO_HI_RO() {
		super();
	}

}
