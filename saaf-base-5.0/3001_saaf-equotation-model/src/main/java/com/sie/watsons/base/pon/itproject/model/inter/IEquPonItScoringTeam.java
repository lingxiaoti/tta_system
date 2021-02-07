package com.sie.watsons.base.pon.itproject.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItScoringTeamEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonItScoringTeam extends IBaseCommon<EquPonItScoringTeamEntity_HI>{
    //IT报价管理-评分小组查询，分页查询
    JSONObject findItScoringTeam(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //IT报价管理-评分小组删除
    void deleteItScoringTeam(JSONObject params) throws Exception;
}
