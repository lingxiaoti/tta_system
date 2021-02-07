package com.sie.watsons.base.supplier.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierDeptEntity_HI;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierDeptEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface ITtaRelSupplierDept extends IBaseCommon<TtaRelSupplierDeptEntity_HI>{
    Pagination<TtaRelSupplierDeptEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaRelSupplierDeptEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaRelSupplierDeptEntity_HI_RO findByRoId(JSONObject queryParamJSON);
}
