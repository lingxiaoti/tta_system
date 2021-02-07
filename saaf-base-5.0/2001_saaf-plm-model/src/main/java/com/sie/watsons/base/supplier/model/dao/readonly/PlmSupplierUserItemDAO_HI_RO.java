package com.sie.watsons.base.supplier.model.dao.readonly;

import com.sie.watsons.base.supplier.model.entities.readonly.PlmSupplierUserItemEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 张韧炼
 * @create 2020-09-25 上午9:45
 **/
@Component("plmSupplierUserItemDAO_HI_RO")
public class PlmSupplierUserItemDAO_HI_RO extends DynamicViewObjectImpl<PlmSupplierUserItemEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierUserItemDAO_HI_RO.class);
    public PlmSupplierUserItemDAO_HI_RO() {
        super();
    }
}
