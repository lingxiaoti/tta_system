package com.sie.watsons.base.product.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.product.model.entities.PlmProductModifyCheckEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IPlmProductModifyCheck extends IBaseCommon<PlmProductModifyCheckEntity_HI>{
    /**
     * 根据EcoId 查询修改中间表
     * @param jsonObject
     * @return
     */
    List<PlmProductModifyCheckEntity_HI>  findProductModifyCheckByEcoId(JSONObject jsonObject) throws Exception;
}
