package com.sie.watsons.base.pon.project.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.project.model.entities.EquPonWitnessTeamEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonWitnessTeam extends IBaseCommon<EquPonWitnessTeamEntity_HI>{
    //报价管理-见证小组查询，分页查询
    JSONObject findWitnessTeam(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //报价管理-见证小组保存
    EquPonWitnessTeamEntity_HI saveWitnessTeam(JSONObject params) throws Exception;

    //报价管理-见证小组删除
    void deleteWitnessTeam(JSONObject params) throws Exception;

    void saveSupplierInvitationRemark(JSONObject jsonObject,Integer userId);

    JSONObject findScoringCommon(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
