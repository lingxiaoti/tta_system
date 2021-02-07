package com.sie.watsons.base.exclusive.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtInfoEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtInfoEntity_HI_RO;
import com.sie.watsons.base.item.model.entities.TtaItemEntity_HI;
import com.sie.watsons.base.item.model.entities.readonly.TtaItemEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnLEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.TtaSupplierEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;

public interface ITtaSoleAgrtInfo extends IBaseCommon<TtaSoleAgrtInfoEntity_HI>{
    TtaSoleAgrtInfoEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void deleteSoleAgrtInfoById(Integer id) throws Exception;

    void saveBatchSoleAgrtInfo(JSONObject paramsJson, Integer userId) throws Exception;

    Pagination<TtaItemEntity_HI_RO> findItemDept(JSONObject jsonObject, Integer pageIndex, Integer pageRows) throws Exception;

    int saveImportItemDetail(JSONObject jsonObject,int userId) throws Exception;

    Pagination<TtaSoleAgrtInfoEntity_HI_RO> findSoleAgrtInfo(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    TtaSoleAgrtInfoEntity_HI_RO findSoleAgrtInfoResult(Integer soleAgrtInfoId);

    Pagination<TtaBrandplnLEntity_HI_RO> findProposalBrand(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    void deleteTtaSoleItem(Integer id) throws Exception;

    Pagination<TtaBrandplnLEntity_HI_RO> findProposalDept(JSONObject jsonObject, Integer pageIndex, Integer pageRows);
}
