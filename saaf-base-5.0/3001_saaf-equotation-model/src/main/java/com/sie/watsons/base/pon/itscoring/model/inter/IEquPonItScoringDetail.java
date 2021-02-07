package com.sie.watsons.base.pon.itscoring.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringDetailEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonItScoringDetail extends IBaseCommon<EquPonItScoringDetailEntity_HI>{
    //评分单据打分分数保存
    EquPonItScoringDetailEntity_HI saveScoringDetail(JSONObject params) throws Exception;
    //评分单据打分分数查询
    JSONObject findScoringDetail(JSONObject queryParamJSON);
}
