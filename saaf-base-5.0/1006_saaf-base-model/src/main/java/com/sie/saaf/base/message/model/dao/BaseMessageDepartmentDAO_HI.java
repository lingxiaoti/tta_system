package com.sie.saaf.base.message.model.dao;

import com.sie.saaf.base.message.model.entities.BaseMessageDepartmentEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseMessageDepartmentDAO_HI")
public class BaseMessageDepartmentDAO_HI extends BaseCommonDAO_HI<BaseMessageDepartmentEntity_HI> {

    public BaseMessageDepartmentDAO_HI() {
        super();
    }

    @Override
    public Object save(BaseMessageDepartmentEntity_HI entity) {
        return super.save(entity);
    }
}
