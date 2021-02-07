package com.sie.saaf.base.commmon.model.dao;

import com.sie.saaf.base.commmon.model.entities.BaseRequestLogEntity_HI;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseRequestLogDAO_HI")
public class BaseRequestLogDAO_HI extends ViewObjectImpl<BaseRequestLogEntity_HI>  {
	public BaseRequestLogDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseRequestLogEntity_HI entity) {
		return super.save(entity);
	}
}
