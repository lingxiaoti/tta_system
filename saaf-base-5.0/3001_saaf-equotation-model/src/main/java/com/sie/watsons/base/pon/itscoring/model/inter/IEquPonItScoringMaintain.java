package com.sie.watsons.base.pon.itscoring.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringMaintainEntity_HI;

public interface IEquPonItScoringMaintain extends IBaseCommon<EquPonItScoringMaintainEntity_HI>{
    //评分单据打分分数保存
    EquPonItScoringMaintainEntity_HI saveScoringMaintain(JSONObject params) throws Exception;
    //评分单据打分分数查询
    JSONObject findScoringMaintain(JSONObject queryParamJSON);
}
