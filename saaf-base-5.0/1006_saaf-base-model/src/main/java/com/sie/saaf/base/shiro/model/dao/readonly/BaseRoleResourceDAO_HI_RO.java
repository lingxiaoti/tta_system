package com.sie.saaf.base.shiro.model.dao.readonly;

import com.sie.saaf.base.shiro.model.entities.readonly.BaseRoleResource_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * 查询角色与资源视图
 * @author ZhangJun
 * @creteTime 2017-12-14
 */
@Component("baseRoleResourceDAO_HI_RO")
public class BaseRoleResourceDAO_HI_RO extends DynamicViewObjectImpl<BaseRoleResource_HI_RO> {
	public BaseRoleResourceDAO_HI_RO() {
		super();
	}

}
