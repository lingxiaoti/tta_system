package com.sie.watsons.report.model.inter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.report.model.entities.readonly.ActBpmTaskConfigEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import java.util.Map;

import com.sie.watsons.report.model.entities.ActBpmTaskConfigEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IActBpmTaskConfig extends IBaseCommon<ActBpmTaskConfigEntity_HI>{

    public List<Map<String, Object>> findNodeList(String  lookupType);


    public List<List<Map<String, Object>>> findPropsolProcessSummery(JSONObject params);


    /**
     * 查询合同流程管理信息
     */
    public List<List<Map<String, Object>>> findContratProcessSummery(JSONObject params);

}
