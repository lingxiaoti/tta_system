package com.sie.watsons.base.pon.itscoring.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringInfoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonItScoringInfo extends IBaseCommon<EquPonItScoringInfoEntity_HI>{
    //报价管理-评分单据查询，分页查询
    JSONObject findScoringInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //报价管理-评分单据保存
    EquPonItScoringInfoEntity_HI saveScoringInfo(JSONObject params) throws Exception;

    //报价管理-评分单据删除
    Integer deleteScoringInfo(JSONObject params) throws Exception;

    //报价管理-评分单据非价格计算
    void saveNonPriceCalculate(JSONObject params) throws Exception;

    void saveQuotationScoreCalculate(JSONObject params) throws Exception;

    //审批流程回调方法
    EquPonItScoringInfoEntity_HI scoringApprovalCallback(JSONObject parseObject,int userId) throws Exception;

    //报价管理-评分单据查询，分页查询
    JSONObject findScoringInfoForFlow(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //报价管理-资料开启单据LOV，分页查询
    JSONObject findPonInformationInfoLov(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    void checkQuotationStatus(JSONObject queryParamJSON)  throws Exception;
}
