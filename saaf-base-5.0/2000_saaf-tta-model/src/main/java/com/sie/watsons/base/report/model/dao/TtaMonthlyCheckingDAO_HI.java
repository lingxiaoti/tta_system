package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaMonthlyCheckingEntity_HI;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/16/016.
 */
@Component("ttaMonthlyCheckingDAO_HI")
public class TtaMonthlyCheckingDAO_HI extends BaseCommonDAO_HI<TtaMonthlyCheckingEntity_HI> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaMonthlyCheckingDAO_HI.class);

    public TtaMonthlyCheckingDAO_HI() {
        super();
    }


}
