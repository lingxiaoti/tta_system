package com.sie.watsons.base.supplier.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierBrandEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierBrandEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaRelSupplierBrand extends IBaseCommon<TtaRelSupplierBrandEntity_HI>{
    Pagination<TtaRelSupplierBrandEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaRelSupplierBrandEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaRelSupplierBrandEntity_HI_RO findByRoId(JSONObject queryParamJSON);
}
