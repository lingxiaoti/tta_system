package com.sie.watsons.base.pos.tempspecial.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.tempspecial.model.entities.EquPosTempSpecialEntity_HI;
import com.sie.watsons.base.pos.tempspecial.model.entities.readonly.EquPosTempSpecialEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IEquPosTempSpecial extends IBaseCommon<EquPosTempSpecialEntity_HI>{

    /**
     * 分页查询供应商品分类详情
     */
    Pagination<EquPosTempSpecialEntity_HI_RO> findTempSpecialPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 保存临时特批
     */
    EquPosTempSpecialEntity_HI saveTempSpecialInfo(String params, int userId) throws Exception;
    /**
     * 删除单行数据
     */
    String deleteTempSpecial(JSONObject jsonObject, int userId);
    /**
     * 根据id查询详情
     */
    EquPosTempSpecialEntity_HI_RO findTempSpecialDetail(String params);

    /**
     * 工作流回调
     */
    EquPosTempSpecialEntity_HI processCallback(JSONObject paramsObject, int userId);
}
