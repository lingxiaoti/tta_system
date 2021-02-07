package com.sie.watsons.base.supplier.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierEntity_HI;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface ITtaRelSupplier extends IBaseCommon<TtaRelSupplierEntity_HI>{
    Pagination<TtaRelSupplierEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaRelSupplierEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaRelSupplierEntity_HI_RO findByRoId(JSONObject queryParamJSON);
}
