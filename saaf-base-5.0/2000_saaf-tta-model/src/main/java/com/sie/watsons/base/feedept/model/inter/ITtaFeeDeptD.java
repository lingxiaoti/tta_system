package com.sie.watsons.base.feedept.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.feedept.model.entities.readonly.TtaFeeDeptDEntity_HI_RO;
import com.sie.watsons.base.feedept.model.entities.TtaFeeDeptDEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaFeeDeptD extends IBaseCommon<TtaFeeDeptDEntity_HI>{
    Pagination<TtaFeeDeptDEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaFeeDeptDEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaFeeDeptDEntity_HI_RO findByRoId(JSONObject queryParamJSON);
}
