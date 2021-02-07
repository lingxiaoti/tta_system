package com.sie.saaf.base.orgStructure.model.dao;

import com.sie.saaf.base.orgStructure.model.entities.BasePersonLevelEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("basePersonLevelDAO_HI")
public class BasePersonLevelDAO_HI extends BaseCommonDAO_HI<BasePersonLevelEntity_HI> {
    public BasePersonLevelDAO_HI() {
        super();
    }

    @Override
    public Object save(BasePersonLevelEntity_HI entity) {
        return super.save(entity);
    }
}
