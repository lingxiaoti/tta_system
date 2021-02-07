package com.sie.watsons.base.pon.scoring.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.yhg.hibernate.core.paging.Pagination;

import java.io.IOException;
import java.util.List;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringInfoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import javax.servlet.http.HttpServletResponse;

public interface IEquPonScoringInfo extends IBaseCommon<EquPonScoringInfoEntity_HI>{
    //报价管理-评分单据查询，分页查询
    JSONObject findScoringInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
    JSONObject findScoringInfoByDeptCode(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //报价管理-评分单据保存
    EquPonScoringInfoEntity_HI saveScoringInfo(JSONObject params) throws Exception;

    //报价管理-评分单据删除
    Integer deleteScoringInfo(JSONObject params) throws Exception;

    //报价管理-评分单据非价格计算
    void saveNonPriceCalculate(JSONObject params) throws Exception;

    //报价管理-报价总分计算
    void saveQuotationScoreCalculate(JSONObject params) throws Exception;

    //审批流程回调方法
    EquPonScoringInfoEntity_HI scoringApprovalCallback(JSONObject parseObject,int userId) throws Exception;

    //报价管理-资料开启单据LOV，分页查询
    JSONObject findPonInformationInfoLov(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //panel test 导入模板下载
    ResultFileEntity btnExportTemplage(JSONObject queryParamJson, HttpServletResponse response) throws Exception;

    //导出评分信息
    ResultFileEntity exportScoringTemplate(JSONObject queryParamJson, HttpServletResponse response) throws Exception;

    //报价管理-评分单据终止
    void terminateScoringInfo(JSONObject params) throws Exception;

    //报价管理-评分单据查询，分页查询
    JSONObject findScoringInfoForFlow(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    void checkQuotationStatus(JSONObject queryParamJSON)  throws Exception;

    //批量导入
    int updatePanelTestScoreImport(JSONObject params) throws Exception;

    //校验用户操作权限
    String validateScoringUserOperator(JSONObject queryParamJSON) throws Exception;

    JSONObject findScoringOwner(JSONObject queryParamJSON);
}
