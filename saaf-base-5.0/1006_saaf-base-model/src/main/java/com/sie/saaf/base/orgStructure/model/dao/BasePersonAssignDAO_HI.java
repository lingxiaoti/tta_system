package com.sie.saaf.base.orgStructure.model.dao;

import com.sie.saaf.base.orgStructure.model.entities.BasePersonAssignEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("basePersonAssignDAO_HI")
public class BasePersonAssignDAO_HI extends BaseCommonDAO_HI<BasePersonAssignEntity_HI> {
    public BasePersonAssignDAO_HI() {
        super();
    }

    @Override
    public Object save(BasePersonAssignEntity_HI entity) {
        return super.save(entity);
    }
}
