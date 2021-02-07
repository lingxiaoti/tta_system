package com.sie.watsons.base.pos.contractUpdate.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.contractUpdate.model.entities.EquPosContractUpdateHeadEntity_HI;
import com.sie.watsons.base.pos.contractUpdate.model.entities.readonly.EquPosContractUpdateHeadEntity_HI_RO;

public interface IEquPosContractUpdateHead extends IBaseCommon<EquPosContractUpdateHeadEntity_HI> {

    /**
     * 分页查询头质量审核详情
     */
    JSONObject findContractUpdatePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 分页查询头行质量审核详情
     */
    EquPosContractUpdateHeadEntity_HI_RO findContractUpdateHeadInfo(JSONObject queryParamJSON);

    /**
     * 保存头行质量审核详情
     */
    Integer saveContractUpdateHeadAndLine(String params, Integer userId) throws Exception;
    /**
     * 删除头数据
     */
    String deleteContractUpdate(JSONObject jsonObject, int userId);
}
