package com.sie.watsons.base.pos.spendUpdate.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.spendUpdate.model.entities.EquPosSpendUpdateHeadEntity_HI;
import com.sie.watsons.base.pos.spendUpdate.model.entities.readonly.EquPosSpendUpdateHeadEntity_HI_RO;

public interface IEquPosSpendUpdateHead extends IBaseCommon<EquPosSpendUpdateHeadEntity_HI> {

    /**
     * 分页查询头概要文件审核详情
     */
    JSONObject findSpendUpdatePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 查询行概要文件审核详情
     */
    EquPosSpendUpdateHeadEntity_HI_RO findSpendUpdateHeadInfo(JSONObject queryParamJSON);

    /**
     * 保存头行概要文件审核详情
     */
    Integer saveSpendUpdateHeadAndLine(String params, Integer userId) throws Exception;
    /**
     * 删除头数据
     */
    String deleteSpendUpdate(JSONObject jsonObject, int userId);
}
