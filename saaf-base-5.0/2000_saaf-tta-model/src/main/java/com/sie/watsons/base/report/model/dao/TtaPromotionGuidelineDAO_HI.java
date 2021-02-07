package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaPromotionGuidelineEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/7/11/011.
 */
@Component("ttaPromotionGuidelineDAO_HI")
public class TtaPromotionGuidelineDAO_HI extends BaseCommonDAO_HI<TtaPromotionGuidelineEntity_HI> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaPromotionGuidelineDAO_HI.class);

    public TtaPromotionGuidelineDAO_HI() {
        super();
    }

}