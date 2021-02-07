package com.sie.watsons.base.pon.quotation.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationNopriceDocEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationNopriceDocEntity_HI_RO;

import java.util.List;

public interface IEquPonQuotationNopriceDoc extends IBaseCommon<EquPonQuotationNopriceDocEntity_HI>{

    // 查询非价格文件
    List<EquPonQuotationNopriceDocEntity_HI_RO> findQuotationNopriceInfo(JSONObject jsonObject);
}
