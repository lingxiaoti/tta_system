package com.sie.watsons.base.contract.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.contract.model.entities.TtaContractLineHEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaContractLineH extends IBaseCommon<TtaContractLineHEntity_HI>{

    TtaContractLineHEntity_HI saveOrUpdateAll(JSONObject paramsJSON, int userId) throws Exception;

}
