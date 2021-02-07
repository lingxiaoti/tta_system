package com.sie.watsons.base.supplement.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupLogEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupShopLogEntity_HI;
import org.springframework.stereotype.Component;

@Component("plmSupShopLogDAO_HI")
public class PlmSupShopLogDAO_HI extends BaseCommonDAO_HI<PlmSupShopLogEntity_HI> {
    public PlmSupShopLogDAO_HI() {
        super();
    }

    @Override
    public Object save(PlmSupShopLogEntity_HI entity) {
        return super.save(entity);
    }
}
