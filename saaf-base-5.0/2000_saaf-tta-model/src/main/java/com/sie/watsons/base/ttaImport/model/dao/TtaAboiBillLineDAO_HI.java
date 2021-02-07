package com.sie.watsons.base.ttaImport.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaAboiBillLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaAboiBillLineDAO_HI")
public class TtaAboiBillLineDAO_HI extends BaseCommonDAO_HI<TtaAboiBillLineEntity_HI> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaAboiBillLineDAO_HI.class);

    public TtaAboiBillLineDAO_HI() {
        super();
    }

}
