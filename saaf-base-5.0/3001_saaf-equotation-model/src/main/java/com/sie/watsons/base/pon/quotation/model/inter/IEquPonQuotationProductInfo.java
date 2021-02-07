package com.sie.watsons.base.pon.quotation.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationProductInfoEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationProductInfoEntity_HI_RO;

import java.util.List;

public interface IEquPonQuotationProductInfo extends IBaseCommon<EquPonQuotationProductInfoEntity_HI>{

    // 导入成分表
    int saveImportForQuotationProduct(String params,Integer userId) throws Exception;

    // 查询成分表
    List<EquPonQuotationProductInfoEntity_HI_RO> findQuoProductInfo(JSONObject jsonObject);

    // 产品删除行
    String deleteQuotationProductInfo(JSONObject jsonObject, int userId);

    // 产品表动态导出
    ResultFileEntity productTemplateExport(JSONObject jsonObject) throws Exception;
}
