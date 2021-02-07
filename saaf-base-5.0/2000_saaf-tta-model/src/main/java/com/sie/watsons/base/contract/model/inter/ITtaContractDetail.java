package com.sie.watsons.base.contract.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractDetailEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.contract.model.entities.TtaContractDetailEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaContractDetail extends IBaseCommon<TtaContractDetailEntity_HI>{
    Pagination<TtaContractDetailEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaContractDetailEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaContractDetailEntity_HI_RO findByRoId(JSONObject queryParamJSON);

}
