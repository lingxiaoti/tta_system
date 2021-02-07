package com.sie.watsons.base.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaSalesSiteEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/7/16/016.
 */
@Component("ttaSalesSiteDAO_HI")
public class TtaSalesSiteDAO_HI extends BaseCommonDAO_HI<TtaSalesSiteEntity_HI> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaSalesSiteDAO_HI.class);

    public TtaSalesSiteDAO_HI() {
        super();
    }

}