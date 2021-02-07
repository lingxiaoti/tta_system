package com.sie.saaf.base.shiro.model.dao.readonly;

import com.sie.saaf.base.shiro.model.entities.readonly.BaseResponsibilityRole_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * 查询职责与角色视图
 * @author ZhangJun
 * @creteTime 2017-12-13
 */
@Component("baseResponsibilityRoleDAO_HI_RO")
public class BaseResponsibilityRoleDAO_HI_RO extends DynamicViewObjectImpl<BaseResponsibilityRole_HI_RO> {
	public BaseResponsibilityRoleDAO_HI_RO() {
		super();
	}
}
