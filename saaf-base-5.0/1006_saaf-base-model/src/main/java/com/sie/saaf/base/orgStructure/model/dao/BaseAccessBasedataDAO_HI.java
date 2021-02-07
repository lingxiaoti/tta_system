package com.sie.saaf.base.orgStructure.model.dao;

import com.sie.saaf.base.orgStructure.model.entities.BaseAccessBasedataEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseAccessBasedataDAO_HI")
public class BaseAccessBasedataDAO_HI extends BaseCommonDAO_HI<BaseAccessBasedataEntity_HI> {
    public BaseAccessBasedataDAO_HI() {
        super();
    }

    @Override
    public Object save(BaseAccessBasedataEntity_HI entity) {
        return super.save(entity);
    }
}
