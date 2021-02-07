package com.sie.watsons.base.ttaImport.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaAboiBillLineEntity_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaTesteroiLineEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/10/23/023.
 */
@Component("ttaTesteroiLineDAO_HI")
public class TtaTesteroiLineDAO_HI extends BaseCommonDAO_HI<TtaTesteroiLineEntity_HI> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaTesteroiLineDAO_HI.class);

    public TtaTesteroiLineDAO_HI() {
        super();
    }

}