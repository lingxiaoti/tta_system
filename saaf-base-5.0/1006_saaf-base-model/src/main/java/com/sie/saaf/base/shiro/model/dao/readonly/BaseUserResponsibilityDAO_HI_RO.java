package com.sie.saaf.base.shiro.model.dao.readonly;

import com.sie.saaf.base.shiro.model.entities.readonly.BaseUserResponsibility_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * 查询用户与职责视图
 * @author ZhangJun
 * @creteTime 2017-12-13
 */
@Component("baseUserResponsibilityDAO_HI_RO")
public class BaseUserResponsibilityDAO_HI_RO extends DynamicViewObjectImpl<BaseUserResponsibility_HI_RO> {
	public BaseUserResponsibilityDAO_HI_RO() {
		super();
	}
}
