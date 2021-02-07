package com.sie.watsons.base.supplement.model.dao.readonly;

import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupShopLogEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupLogEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("plmSupShopLogDAO_HI_RO")
public class PlmSupShopLogDAO_HI_RO extends DynamicViewObjectImpl<PlmSupShopLogEntity_HI_RO>  {
    public PlmSupShopLogDAO_HI_RO() {
        super();
    }

}
