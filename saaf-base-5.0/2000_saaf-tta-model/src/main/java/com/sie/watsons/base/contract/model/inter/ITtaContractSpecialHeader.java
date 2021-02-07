package com.sie.watsons.base.contract.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractSpecialHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.contract.model.entities.TtaContractSpecialHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaContractSpecialHeader extends IBaseCommon<TtaContractSpecialHeaderEntity_HI>{

    Pagination<TtaContractSpecialHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    void updateStatus(Integer pkId,Integer userId) throws Exception;

    TtaContractSpecialHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON);

    TtaContractSpecialHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, UserSessionBean userSessionBean, int userId) throws Exception;

    JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception;

}
