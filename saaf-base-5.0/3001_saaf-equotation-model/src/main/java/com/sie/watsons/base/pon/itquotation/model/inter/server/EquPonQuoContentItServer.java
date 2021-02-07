package com.sie.watsons.base.pon.itquotation.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuoContentItEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuoContentItEntity_HI_RO;
import com.sie.watsons.base.pon.itquotation.model.inter.IEquPonQuoContentIt;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("equPonQuoContentItServer")
public class EquPonQuoContentItServer extends BaseCommonServer<EquPonQuoContentItEntity_HI> implements IEquPonQuoContentIt {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuoContentItServer.class);
    @Autowired
    private ViewObject<EquPonQuoContentItEntity_HI> equPonQuoContentItDAO_HI;
    @Autowired
    private BaseViewObject<EquPonQuoContentItEntity_HI_RO> equPonQuoContentItDAO_HI_RO;

    public EquPonQuoContentItServer() {
        super();
    }

    @Override
    public List<EquPonQuoContentItEntity_HI_RO> findQuoContentIt(JSONObject jsonObject) {
        StringBuffer sb = new StringBuffer(EquPonQuoContentItEntity_HI_RO.QUERY_SQL);
        Map<String, Object> map = Maps.newHashMap();
        SaafToolUtils.parperParam(jsonObject, "ci.quotation_id", "quotationId", sb, map, "=");
        List<EquPonQuoContentItEntity_HI_RO> list = equPonQuoContentItDAO_HI_RO.findList(sb,map);
        return list;
    }
}
