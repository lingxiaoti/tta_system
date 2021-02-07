package com.sie.watsons.base.api.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.api.model.entities.PlmRmsMapEntity_HI;
import com.sie.watsons.base.api.model.entities.readonly.PlmRmsMapEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IPlmRmsMap extends IBaseCommon<PlmRmsMapEntity_HI>{
    public void update(JSONObject queryParamJSON) throws Exception;
    public void save(JSONObject queryParamJSON) throws Exception;
    public void delete(JSONObject queryParamJSON) throws Exception;
    Pagination<PlmRmsMapEntity_HI_RO> findRmsData(JSONObject queryParamJSON, Integer pageRows, Integer pageIndex);
}
