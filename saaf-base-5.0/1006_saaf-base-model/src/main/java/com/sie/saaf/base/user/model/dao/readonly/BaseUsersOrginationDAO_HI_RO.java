package com.sie.saaf.base.user.model.dao.readonly;

import com.sie.saaf.base.user.model.entities.readonly.BaseUsersOrganization_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * @author ZhangJun
 * @createTime 2017-12-25 10:29
 * @description
 */
@Component("baseUsersOrginationDAO_HI_RO")
public class BaseUsersOrginationDAO_HI_RO extends DynamicViewObjectImpl<BaseUsersOrganization_HI_RO> {
	public BaseUsersOrginationDAO_HI_RO() {
		super();
	}


}
