package com.sie.watsons.base.feedept.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.feedept.model.entities.readonly.TtaFeeDeptHEntity_HI_RO;
import com.sie.watsons.base.feedept.model.entities.TtaFeeDeptHEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.Map;

import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaFeeDeptH extends IBaseCommon<TtaFeeDeptHEntity_HI>{
    Pagination<TtaFeeDeptHEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaFeeDeptHEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaFeeDeptHEntity_HI_RO findByRoId(JSONObject queryParamJSON);



    public Map<String, Object> callCopyOrder(JSONObject paramsJSON, int userId);


    public Map<String, Object> callChangeOrder(JSONObject paramsJSON, int userId);

    TtaFeeDeptHEntity_HI updateApprove(JSONObject queryParamJSON, int userId) throws Exception;




}
