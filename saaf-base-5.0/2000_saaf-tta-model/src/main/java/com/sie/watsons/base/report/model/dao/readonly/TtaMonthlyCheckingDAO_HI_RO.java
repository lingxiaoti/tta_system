package com.sie.watsons.base.report.model.dao.readonly;

import com.sie.watsons.base.report.model.entities.readonly.TtaMonthlyCheckingEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/7/16/016.
 */
@Component("ttaMonthlyCheckingDAO_HI_RO")
public class TtaMonthlyCheckingDAO_HI_RO extends DynamicViewObjectImpl<TtaMonthlyCheckingEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaMonthlyCheckingDAO_HI_RO.class);
    public TtaMonthlyCheckingDAO_HI_RO() {
        super();
    }

}
