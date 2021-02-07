package com.sie.watsons.base.pon.itproject.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItQuotationContentEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonItQuotationContent extends IBaseCommon<EquPonItQuotationContentEntity_HI>{
    //IT报价管理-报价内容查询，分页查询
    JSONObject findItQuotationContent(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //IT报价管理-报价内容删除
    void deleteItQuotationContent(JSONObject params) throws Exception;
}
