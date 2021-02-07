package com.sie.watsons.base.pon.project.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectInfoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonProjectInfo extends IBaseCommon<EquPonProjectInfoEntity_HI>{
    //报价管理-立项单据查询，分页查询
    JSONObject findProjectInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    JSONObject findProjectInfoById(JSONObject queryParamJSON);

    //报价管理-立项单据保存
    EquPonProjectInfoEntity_HI saveProjectInfo(JSONObject params) throws Exception;

    EquPonProjectInfoEntity_HI saveProjectInfos(JSONObject params) throws Exception;

    //报价管理-立项单据删除
    Integer deleteProjectInfo(JSONObject params) throws Exception;

    //报价管理-立项单据终止
    void terminateProjectInfo(JSONObject params) throws Exception;

    //审批流程回调方法
    EquPonProjectInfoEntity_HI projectApprovalCallback(JSONObject parseObject,int userId) throws Exception;

    JSONObject findWitnessApproval(JSONObject queryParamJSON);

    JSONObject findProjectWitnessApproval(JSONObject queryParamJSON);

    JSONObject findProjectScoringApproval(JSONObject queryParamJSON);

    JSONObject findQuoInformationWitnessApproval(JSONObject queryParamJSON);

    JSONObject findQuoInformationDefaultWitnessApproval(JSONObject queryParamJSON);

    //查找导航菜单节点
    JSONObject findPonNavigationMenuNodeList(JSONObject queryParamJSON);

    //供应商报价管理查询
    JSONObject findSupplierQuotationList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //校验用户操作权限
    String validateProjectUserOperator(JSONObject queryParamJSON) throws Exception;
}
