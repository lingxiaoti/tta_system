package com.sie.watsons.base.report.model.dao.readonly;

import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/7/30/030.
 */
@Component("ttaFreeGoodsDAO_HI_RO")
public class TtaFreeGoodsDAO_HI_RO extends DynamicViewObjectImpl<TtaFreeGoodsEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaFreeGoodsDAO_HI_RO.class);
    public TtaFreeGoodsDAO_HI_RO() {
        super();
    }

}
