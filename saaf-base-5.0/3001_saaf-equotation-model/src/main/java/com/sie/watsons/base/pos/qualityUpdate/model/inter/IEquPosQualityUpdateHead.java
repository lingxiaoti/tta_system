package com.sie.watsons.base.pos.qualityUpdate.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.qualityUpdate.model.entities.EquPosQualityUpdateHeadEntity_HI;
import com.sie.watsons.base.pos.qualityUpdate.model.entities.readonly.EquPosQualityUpdateHeadEntity_HI_RO;

public interface IEquPosQualityUpdateHead extends IBaseCommon<EquPosQualityUpdateHeadEntity_HI>{

    /**
     * 分页查询头质量审核详情
     */
    JSONObject findQualityUpdatePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 查询头质量审核详情
     */
    EquPosQualityUpdateHeadEntity_HI_RO findQualityUpdateHeadInfo(JSONObject queryParamJSON);

    /**
     * 保存头行质量审核详情
     */
    Integer saveQualityUpdateHeadAndLine(String params,Integer userId) throws Exception;
    /**
     * 删除头数据
     */
    String deleteQualityUpdate(JSONObject jsonObject, int userId);
}
