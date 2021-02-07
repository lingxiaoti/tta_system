package com.sie.watsons.base.pon.quotation.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationPriceDocEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationPriceDocEntity_HI_RO;
import com.sie.watsons.base.pon.quotation.model.inter.IEquPonQuotationPriceDoc;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("equPonQuotationPriceDocServer")
public class EquPonQuotationPriceDocServer extends BaseCommonServer<EquPonQuotationPriceDocEntity_HI> implements IEquPonQuotationPriceDoc {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationPriceDocServer.class);

    @Autowired
    private ViewObject<EquPonQuotationPriceDocEntity_HI> equPonQuotationPriceDocDAO_HI;
    @Autowired
    private BaseViewObject<EquPonQuotationPriceDocEntity_HI_RO> equPonQuotationPriceDocDAO_HI_RO;

    public EquPonQuotationPriceDocServer() {
        super();
    }

    @Override
    public List<EquPonQuotationPriceDocEntity_HI_RO> findQuotationPriceInfo(JSONObject jsonObject) {
        StringBuffer sql = new StringBuffer(EquPonQuotationPriceDocEntity_HI_RO.SELECT_PRICE_SQL);
        Map<String, Object> map = new HashMap<String, Object>(16);
        // 根据头id查询头数据
        SaafToolUtils.parperParam(jsonObject, "pd.quotation_id", "quotationId", sql, map, "=");
        return equPonQuotationPriceDocDAO_HI_RO.findList(sql, map);
    }
}
