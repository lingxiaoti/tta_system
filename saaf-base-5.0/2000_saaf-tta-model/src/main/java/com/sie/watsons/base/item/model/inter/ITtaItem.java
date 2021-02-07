package com.sie.watsons.base.item.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.item.model.entities.readonly.TtaItemEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.watsons.base.item.model.entities.TtaItemEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

public interface ITtaItem extends IBaseCommon<TtaItemEntity_HI>{
    Pagination<TtaItemEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaItemEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaItemEntity_HI_RO findByRoId(JSONObject queryParamJSON);
    TtaItemEntity_HI_RO findByVendor(JSONObject queryParamJSON);

    List<TtaItemEntity_HI_RO> findDeptInfo();
}
