package com.sie.saaf.base.message.model.dao.readonly;

import com.sie.saaf.base.message.model.entities.readonly.BaseMessageDepartmentEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseMessageDepartmentDAO_HI_RO")
public class BaseMessageDepartmentDAO_HI_RO extends DynamicViewObjectImpl<BaseMessageDepartmentEntity_HI_RO> {

    public BaseMessageDepartmentDAO_HI_RO() {
        super();
    }

}
