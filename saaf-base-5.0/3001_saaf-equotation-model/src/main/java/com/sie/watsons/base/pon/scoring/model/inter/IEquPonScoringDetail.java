package com.sie.watsons.base.pon.scoring.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringDetailEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonScoringDetail extends IBaseCommon<EquPonScoringDetailEntity_HI>{
    //评分单据打分分数保存
    EquPonScoringDetailEntity_HI saveScoringDetail(JSONObject params) throws Exception;
    //评分单据打分分数查询
    JSONObject findScoringDetail(JSONObject queryParamJSON);
    //查询供应商基础分数
    JSONObject findSupplierBaseScore(JSONObject queryParamJSON);
}
