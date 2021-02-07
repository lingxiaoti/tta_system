package com.sie.watsons.base.contract.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractRecordHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.contract.model.entities.TtaContractRecordHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaContractRecordHeader extends IBaseCommon<TtaContractRecordHeaderEntity_HI>{
    /**
     * 查询列表
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination<TtaContractRecordHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 保存列表
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    List<TtaContractRecordHeaderEntity_HI> saveOrUpdateALL(JSONObject paramsJSON, int userId) throws Exception;

    void findContractVendor(JSONObject jsonObject, int userId);
}
