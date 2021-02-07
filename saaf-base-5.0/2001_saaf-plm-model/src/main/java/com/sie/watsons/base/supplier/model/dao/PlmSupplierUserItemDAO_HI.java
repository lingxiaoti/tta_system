package com.sie.watsons.base.supplier.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplier.model.entities.PlmSupplierUserItemEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 张韧炼
 * @create 2020-09-25 上午9:47
 **/
@Component("plmSupplierUserItemDAO_HI")
public class PlmSupplierUserItemDAO_HI extends BaseCommonDAO_HI<PlmSupplierUserItemEntity_HI> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierUserItemDAO_HI.class);

    public PlmSupplierUserItemDAO_HI() {
        super();
    }
}
