package com.sie.watsons.base.exclusive.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleSupplierEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleSupplierEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface ITtaSoleSupplier extends IBaseCommon<TtaSoleSupplierEntity_HI> {

    Pagination<TtaSoleSupplierEntity_HI_RO> findSoleSupplierPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    void checkOut(JSONObject queryParamJSON) throws Exception;

    TtaSoleSupplierEntity_HI saveProposalSupplier(JSONObject queryParamJSON) throws Exception;

    TtaSoleSupplierEntity_HI deleteProposalSupplierById(Integer id) throws Exception;

    List<TtaSoleSupplierEntity_HI> ttaProposalSupplierSaveForSplitMerge(JSONObject queryParamJSON) throws Exception;

    Pagination<TtaSoleSupplierEntity_HI_RO> find(JSONObject jsonObject, Integer pageIndex, Integer pageRows);
}
