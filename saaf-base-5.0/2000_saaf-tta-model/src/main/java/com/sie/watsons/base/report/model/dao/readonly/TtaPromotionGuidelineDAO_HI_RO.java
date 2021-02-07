package com.sie.watsons.base.report.model.dao.readonly;

import com.sie.watsons.base.report.model.entities.readonly.TtaPromotionGuidelineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/7/11/011.
 */
@Component("ttaPromotionGuidelineDAO_HI_RO")
public class TtaPromotionGuidelineDAO_HI_RO extends DynamicViewObjectImpl<TtaPromotionGuidelineEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaPromotionGuidelineDAO_HI_RO.class);
    public TtaPromotionGuidelineDAO_HI_RO() {
        super();
    }

}