package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/7/30/030.
 */
@Component("ttaFreeGoodsDAO_HI")
public class TtaFreeGoodsDAO_HI extends BaseCommonDAO_HI<TtaFreeGoodsEntity_HI> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaFreeGoodsDAO_HI.class);

    public TtaFreeGoodsDAO_HI() {
        super();
    }

}