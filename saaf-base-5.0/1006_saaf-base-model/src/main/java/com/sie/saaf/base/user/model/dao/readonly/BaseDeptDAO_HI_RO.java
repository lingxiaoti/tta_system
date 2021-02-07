package com.sie.saaf.base.user.model.dao.readonly;

import com.sie.saaf.base.user.model.entities.readonly.BaseDept_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseDeptDAO_HI_RO")
public class BaseDeptDAO_HI_RO  extends DynamicViewObjectImpl<BaseDept_HI_RO> {
    public BaseDeptDAO_HI_RO(){
        super();
    }
}
