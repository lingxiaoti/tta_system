package com.sie.watsons.base.report.model.dao.readonly;
import com.sie.watsons.base.report.model.entities.readonly.TtaSalesSiteEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/7/16/016.
 */
@Component("ttaSalesSiteDAO_HI_RO")
public class TtaSalesSiteDAO_HI_RO extends DynamicViewObjectImpl<TtaSalesSiteEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaSalesSiteDAO_HI_RO.class);
    public TtaSalesSiteDAO_HI_RO() {
        super();
    }

}