package com.sie.saaf.base.orgStructure.model.dao;

import com.sie.saaf.base.orgStructure.model.entities.BaseJobEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseJobDAO_HI")
public class BaseJobDAO_HI extends BaseCommonDAO_HI<BaseJobEntity_HI> {
    public BaseJobDAO_HI() {
        super();
    }

    @Override
    public Object save(BaseJobEntity_HI entity) {
        return super.save(entity);
    }
}
