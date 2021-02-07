package com.sie.watsons.base.pon.project.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.project.model.entities.EquPonScoringTeamEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonScoringTeam extends IBaseCommon<EquPonScoringTeamEntity_HI>{
    //报价管理-评分小组查询，分页查询
    JSONObject findScoringTeam(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //报价管理-评分小组保存
    EquPonScoringTeamEntity_HI saveScoringTeam(JSONObject params) throws Exception;

    //报价管理-评分小组删除
    void deleteScoringTeam(JSONObject params) throws Exception;

    //报价管理-评分小组-人员查询LOV，分页查询
    JSONObject findScoringMemberLov(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
