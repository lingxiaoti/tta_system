package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.sie.watsons.base.plmBase.model.entities.readonly.PlmUserBrandMapEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Greate Summer on 2020/11/19.
 */
@Component("PlmUserBrandMapDAO_HI_RO")
public class PlmUserBrandMapDAO_HI_RO extends DynamicViewObjectImpl<PlmUserBrandMapEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmUserBrandMapDAO_HI_RO.class);
    public PlmUserBrandMapDAO_HI_RO() {
        super();
    }
}
