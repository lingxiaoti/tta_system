package com.sie.watsons.base.proposal.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import java.util.Map;

import com.sie.watsons.base.proposal.model.entities.TtaDeptFeeLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaDeptFeeLine extends IBaseCommon<TtaDeptFeeLineEntity_HI>{

    Pagination<TtaDeptFeeLineEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaDeptFeeLineEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaDeptFeeLineEntity_HI_RO findByRoId(JSONObject queryParamJSON);

    JSONObject findDeptFeeLineReport(JSONObject queryParamJSON) throws Exception;

    JSONObject findDeptFeeLFindByOldYear(JSONObject jsonObject) throws Exception;

    List<Map<String, Object>> ttaDeptFeeLFindSearchPromotionCost(JSONObject jsonObject) throws Exception;

    void deleteDeptFeeByProposalAndDmFlyer(JSONObject jsonObject) throws Exception;

    JSONObject findDeptFeeShowHideByProposal(JSONObject jsonObject);
}
