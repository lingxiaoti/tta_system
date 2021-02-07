package com.sie.saaf.base.orgStructure.model.dao;

import com.sie.saaf.base.orgStructure.model.entities.BasePersonAccessTempEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("basePersonAccessTempDAO_HI")
public class BasePersonAccessTempDAO_HI extends BaseCommonDAO_HI<BasePersonAccessTempEntity_HI> {
    public BasePersonAccessTempDAO_HI() {
        super();
    }

    @Override
    public Object save(BasePersonAccessTempEntity_HI entity) {
        return super.save(entity);
    }
}
