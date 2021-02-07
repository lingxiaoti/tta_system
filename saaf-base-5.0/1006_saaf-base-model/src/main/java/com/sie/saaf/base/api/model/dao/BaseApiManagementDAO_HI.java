package com.sie.saaf.base.api.model.dao;

import com.sie.saaf.base.api.model.entities.BaseApiManagementEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseApiManagementDAO_HI")
public class BaseApiManagementDAO_HI extends BaseCommonDAO_HI<BaseApiManagementEntity_HI> {
	public BaseApiManagementDAO_HI() {
		super();
	}
}
