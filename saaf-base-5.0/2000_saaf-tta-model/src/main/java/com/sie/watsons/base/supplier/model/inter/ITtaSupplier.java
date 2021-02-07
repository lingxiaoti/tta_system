package com.sie.watsons.base.supplier.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.supplier.model.entities.TtaSupplierEntity_HI;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSupplier extends IBaseCommon<TtaSupplierEntity_HI>{
    Pagination<TtaSupplierEntity_HI_RO> findTtaSuppliers(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaSupplierEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaSupplierEntity_HI_RO findByRoId(JSONObject queryParamJSON);
}
