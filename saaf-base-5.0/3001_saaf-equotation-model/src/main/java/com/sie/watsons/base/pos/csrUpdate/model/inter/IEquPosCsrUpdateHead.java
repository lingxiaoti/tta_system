package com.sie.watsons.base.pos.csrUpdate.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.csrUpdate.model.entities.EquPosCsrUpdateHeadEntity_HI;
import com.sie.watsons.base.pos.csrUpdate.model.entities.readonly.EquPosCsrUpdateHeadEntity_HI_RO;

public interface IEquPosCsrUpdateHead extends IBaseCommon<EquPosCsrUpdateHeadEntity_HI>{
    /**
     * 分页查询头Csr审核详情
     */
    JSONObject findCsrUpdatePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 查询头Csr审核详情
     */
    EquPosCsrUpdateHeadEntity_HI_RO findCsrUpdateHeadInfo(JSONObject queryParamJSON);

    /**
     * 保存头行Csr审核详情
     */
    Integer saveCsrUpdateHeadAndLine(String params,Integer userId) throws Exception;
    /**
     * 删除头数据
     */
    String deleteCsrUpdate(JSONObject jsonObject, int userId);
}
