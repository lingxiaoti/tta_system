package com.sie.saaf.base.shiro.model.dao.readonly;

import com.sie.saaf.base.shiro.model.entities.readonly.BaseResponsibilityUserEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * 查询职责与角色视图
 * @author ZhangJun
 * @creteTime 2017-12-13
 */
@Component("baseResponsibilityUserDAO_HI_RO")
public class BaseResponsibilityUserDAO_HI_RO extends DynamicViewObjectImpl<BaseResponsibilityUserEntity_HI_RO> {
    public BaseResponsibilityUserDAO_HI_RO() {
        super();
    }

}
