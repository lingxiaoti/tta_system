package com.sie.watsons.base.fieldconfig.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.fieldconfig.model.entities.TtaOiFieldMappingEntity_HI;
import com.sie.watsons.base.fieldconfig.model.entities.readonly.TtaOiFieldMappingEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;


public interface ITtaOiFieldMapping extends IBaseCommon<TtaOiFieldMappingEntity_HI>{

    /**
     * 功能描述：分页查询字段信息
     */
    public Pagination<TtaOiFieldMappingEntity_HI_RO> findFieldPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);


    /**
     * 功能描述：查询来源字段信息
     */
    public Pagination<TtaOiFieldMappingEntity_HI_RO> findResourceField(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 功能描述：更新或保存
     */
    public void saveOrUpdateField(JSONObject jsonObject);
}
