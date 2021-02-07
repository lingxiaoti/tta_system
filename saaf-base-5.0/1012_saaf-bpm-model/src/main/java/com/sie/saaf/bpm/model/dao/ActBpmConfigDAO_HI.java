package com.sie.saaf.bpm.model.dao;

import com.sie.saaf.bpm.model.entities.ActBpmConfigEntity_HI;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("actBpmConfigDAO_HI")
public class ActBpmConfigDAO_HI extends ViewObjectImpl<ActBpmConfigEntity_HI> {
    
	public ActBpmConfigDAO_HI() {
		super();
	}

}
