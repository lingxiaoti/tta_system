package com.sie.watsons.base.proposal.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.proposal.model.entities.TtaTermsLEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsLEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.math.BigDecimal;
import java.util.List;

public interface ITtaTermsL extends IBaseCommon<TtaTermsLEntity_HI>{
    Pagination<TtaTermsLEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaTermsLEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    List<TtaTermsLEntity_HI_RO> saveFindByRoId(JSONObject queryParamJSON,Integer userId) throws Exception;

    JSONObject findValue(JSONObject queryParamJSON,Integer userId) throws Exception ;

    void saveTtaTermsLEntity(JSONObject queryParamJSON,Integer userId) throws Exception;

    List<TtaTermsLEntity_HI> saveOrUpdateALL(JSONArray paramsJSON,JSONArray paramsDelete, int userId,int hId,int proposalId,String beoiTax) throws Exception;

    void saveTtaTermsLComfirm(JSONObject queryParamJSON,Integer userId) throws Exception;

    void saveTtaTermsLCancelComfirm(JSONObject queryParamJSON,Integer userId) throws Exception;

    List<TtaTermsLEntity_HI_RO> findMethod(JSONObject queryParamJSON,Integer userId) throws Exception;

    JSONObject saveOrUpdateCountFee(JSONObject paramsJSON, Integer userId) throws Exception ;
}
