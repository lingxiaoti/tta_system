package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.bind.annotation.RequestParam;

public interface ITtaProposalStatus {
    Pagination<JSONObject> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
    Long export(JSONObject paramsJSON) throws Exception;
    Pagination<JSONObject> findElecContractReport(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    Pagination<JSONObject> findBusinessBookProces(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    Pagination<JSONObject> findExclusiveProces(JSONObject jsonObject, Integer pageIndex, Integer pageRows);
}
