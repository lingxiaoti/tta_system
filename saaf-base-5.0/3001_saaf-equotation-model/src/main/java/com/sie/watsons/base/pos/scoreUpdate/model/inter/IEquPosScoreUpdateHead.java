package com.sie.watsons.base.pos.scoreUpdate.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.scoreUpdate.model.entities.EquPosScoreUpdateHeadEntity_HI;
import com.sie.watsons.base.pos.scoreUpdate.model.entities.readonly.EquPosScoreUpdateHeadEntity_HI_RO;

public interface IEquPosScoreUpdateHead extends IBaseCommon<EquPosScoreUpdateHeadEntity_HI>{

    /**
     * 分页查询头评分审核详情
     */
    JSONObject findScoreUpdatePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 查询头行评分审核详情
     */
    EquPosScoreUpdateHeadEntity_HI_RO findScoreUpdateHeadInfo(JSONObject queryParamJSON);

    /**
     * 保存头行评分审核详情
     */
    Integer saveScoreUpdateHeadAndLine(String params, Integer userId) throws Exception;
    /**
     * 删除头数据
     */
    String deleteScoreUpdate(JSONObject jsonObject, int userId);
}
