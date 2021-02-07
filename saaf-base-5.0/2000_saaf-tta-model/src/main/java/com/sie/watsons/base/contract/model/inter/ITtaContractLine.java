package com.sie.watsons.base.contract.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.contract.model.entities.TtaContractLineEntity_HI;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractLineEntity_HI_RO;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractLineProposalEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiBillLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;
import java.util.Map;

public interface ITtaContractLine extends IBaseCommon<TtaContractLineEntity_HI>{
    Pagination<TtaContractLineEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    Map<String, Object> findByPro(JSONObject queryParamJSON);

    /**
     * 功能描述： 合同拆分供应商信息
     */
    void saveSplitVenders(JSONObject queryParamJSON);

    /**
     * 功能描述：保存拆分结果信息
     */
    void saveBatchSplitResult(JSONObject queryParamJSON);

    Pagination<TtaContractLineProposalEntity_HI_RO> findByCode(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaContractLineEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    public Pagination<BaseLookupValuesEntity_HI_RO> findLookUpCode() ;

    JSONObject saveOrUpdate2(JSONObject paramsJSON, int userId) throws Exception;
    public List<TtaContractLineEntity_HI> findByTerm(String proposalId);

    TtaContractLineEntity_HI save(String paramsJSON,String contractIdString, int userId) throws Exception;

    public void updateStatus(String proposalId,int userId) throws Exception;

    TtaContractLineEntity_HI submit(String paramsJSON,String status, int userId) throws Exception;

    void delete(Integer articleId);

    TtaContractLineEntity_HI_RO findByRoId(JSONObject queryParamJSON);

    public Pagination<TtaOiBillLineEntity_HI_RO> findBillOi(String vendorCode, String year) ;

    List<TtaContractLineEntity_HI_RO> findById(JSONObject queryParamJSON);

    public void deleteRow(List<String> vendorCodeList,String contractHId);

    void saveTtaTermsLToContract(JSONObject queryParamJSON,Integer userId) throws Exception;

    List<TtaContractLineEntity_HI_RO> findTtaSummaryById(JSONObject queryParamJSON,Integer userId) throws Exception;

    public List<Map<String, Object>> findOIList(String oiType,String actuallyCountDate,String proposalId,String year);

    public List<Map<String, Object>> findContractLine(String contractLine);

    public List<TtaContractLineEntity_HI_RO> findAnalysisLine(String proposalId,String year);

    public List<TtaContractLineEntity_HI_RO> findAnalysisFreeGood(String proposalId);

    public List<Map<String, Object>> findBrand(String proposalId,String vendorCode);

    List<Map<String,Object>> findOiTableName(String proposalYear);

    List<Map<String, Object>> findOiResultList(String tableName, String actuallyCountDate, String vendorCode, String oldProposalYear);

    List<Map<String, Object>> findTermsAcCount(String oldProposalYear);
}
