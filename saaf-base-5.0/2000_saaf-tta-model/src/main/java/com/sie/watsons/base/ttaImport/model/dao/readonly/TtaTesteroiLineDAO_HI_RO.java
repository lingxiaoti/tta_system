package com.sie.watsons.base.ttaImport.model.dao.readonly;

import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaTesteroiLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/10/23/023.
 */
@Component("ttaTesteroiLineDAO_HI_RO")
public class TtaTesteroiLineDAO_HI_RO extends DynamicViewObjectImpl<TtaTesteroiLineEntity_HI_RO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaTesteroiLineEntity_HI_RO.class);
    public TtaTesteroiLineDAO_HI_RO() {
        super();
    }

}