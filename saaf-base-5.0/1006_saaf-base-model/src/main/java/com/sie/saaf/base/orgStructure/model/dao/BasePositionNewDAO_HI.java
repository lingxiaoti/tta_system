package com.sie.saaf.base.orgStructure.model.dao;

import com.sie.saaf.base.orgStructure.model.entities.BasePositionNewEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("basePositionNewDAO_HI")
public class BasePositionNewDAO_HI extends BaseCommonDAO_HI<BasePositionNewEntity_HI> {
    public BasePositionNewDAO_HI() {
        super();
    }

    @Override
    public Object save(BasePositionNewEntity_HI entity) {
        return super.save(entity);
    }
}
