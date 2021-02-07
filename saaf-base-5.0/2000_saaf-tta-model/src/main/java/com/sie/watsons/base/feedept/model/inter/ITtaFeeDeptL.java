package com.sie.watsons.base.feedept.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.feedept.model.entities.readonly.TtaFeeDeptLEntity_HI_RO;
import com.sie.watsons.base.feedept.model.entities.TtaFeeDeptLEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaFeeDeptL extends IBaseCommon<TtaFeeDeptLEntity_HI>{
    Pagination<TtaFeeDeptLEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaFeeDeptLEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaFeeDeptLEntity_HI_RO findByRoId(JSONObject queryParamJSON);
}
