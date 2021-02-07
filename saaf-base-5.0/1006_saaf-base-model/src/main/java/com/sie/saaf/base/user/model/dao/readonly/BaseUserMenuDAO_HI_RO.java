package com.sie.saaf.base.user.model.dao.readonly;

import com.sie.saaf.base.user.model.entities.readonly.BasePDAUserMenuEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * @author xiangyipo
 * @date 2018/2/5
 */

@Component("baseUserMenuDAO_HI_RO")
public class BaseUserMenuDAO_HI_RO extends DynamicViewObjectImpl<BasePDAUserMenuEntity_HI_RO> {
    public BaseUserMenuDAO_HI_RO() {
    }
}
