package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosQualificationInfoEntity_HI;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosQualificationInfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IEquPosQualificationInfo extends IBaseCommon<EquPosQualificationInfoEntity_HI> {
    //资质审查单据，分页查询
    JSONObject findList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //资质审查单据-保存
    EquPosQualificationInfoEntity_HI saveQualificationInfo(JSONObject params) throws Exception;

    //资质审查单据-删除
    Integer deleteQualificationInfo(JSONObject params) throws Exception;

    //资质审查单据-提交
    EquPosQualificationInfoEntity_HI submitQualificationInfo(JSONObject params) throws Exception;

    //资质审查单据-取消
    EquPosQualificationInfoEntity_HI cancelQualificationInfo(JSONObject params) throws Exception;

    //资质审查单据-撤回
    EquPosQualificationInfoEntity_HI withdrawQualificationInfo(JSONObject params) throws Exception;

    //资质审查单据-驳回供应商
    EquPosQualificationInfoEntity_HI rejectQualificationInfo(JSONObject params) throws Exception;

    //品类查询(一级分类)，分页查询
    JSONObject findFirstCategoryLov(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //品类查询(二级分类)，分页查询
    JSONObject findSecondCategoryLov(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //品类查询(三级分类)，分页查询
    JSONObject findThridCategoryLov(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //查找导航菜单节点
    JSONObject findNavigationMenuNodeList(JSONObject queryParamJSON);

    //供应商准入管理查询
    JSONObject findSupplierManagerList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //审批流程回调方法
    EquPosQualificationInfoEntity_HI qualificationApprovalCallback(JSONObject parseObject,int userId) throws Exception;
}
