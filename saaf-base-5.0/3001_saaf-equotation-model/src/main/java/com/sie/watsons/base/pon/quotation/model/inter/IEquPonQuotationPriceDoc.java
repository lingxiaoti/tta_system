package com.sie.watsons.base.pon.quotation.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationPriceDocEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationPriceDocEntity_HI_RO;

import java.util.List;

public interface IEquPonQuotationPriceDoc extends IBaseCommon<EquPonQuotationPriceDocEntity_HI>{

    // 查询报价管理价格信息
    List<EquPonQuotationPriceDocEntity_HI_RO> findQuotationPriceInfo(JSONObject jsonObject);
}
