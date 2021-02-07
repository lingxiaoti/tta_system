package com.sie.saaf.base.user.model.dao.readonly;

import com.sie.saaf.base.user.model.entities.readonly.BaseUserGroupAssignEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseUserGroupAssignDAO_HI_RO")
public class BaseUserGroupAssignDAO_HI_RO extends DynamicViewObjectImpl<BaseUserGroupAssignEntity_HI_RO> {
    public BaseUserGroupAssignDAO_HI_RO(){
        super();
    }
}
