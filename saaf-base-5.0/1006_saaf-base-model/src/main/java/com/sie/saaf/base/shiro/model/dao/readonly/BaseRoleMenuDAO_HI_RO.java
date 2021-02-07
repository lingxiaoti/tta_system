package com.sie.saaf.base.shiro.model.dao.readonly;

import com.sie.saaf.base.shiro.model.entities.readonly.BaseRoleMenu_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * 查询角色与功能视图DAO
 * @author ZhangJun
 * @creteTime 2017-12-13
 */
@Component("baseRoleMenuDAO_HI_RO")
public class BaseRoleMenuDAO_HI_RO extends DynamicViewObjectImpl<BaseRoleMenu_HI_RO> {

    public BaseRoleMenuDAO_HI_RO(){
        super();
    }
}
