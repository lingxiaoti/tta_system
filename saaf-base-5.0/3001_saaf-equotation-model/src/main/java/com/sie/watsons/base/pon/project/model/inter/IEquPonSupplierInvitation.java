package com.sie.watsons.base.pon.project.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pon.project.model.entities.EquPonSupplierInvitationEntity_HI;

public interface IEquPonSupplierInvitation extends IBaseCommon<EquPonSupplierInvitationEntity_HI>{
    //报价管理-邀请供应商查询，分页查询
    JSONObject findSupplierInvitation(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //报价管理-邀请供应商保存
    EquPonSupplierInvitationEntity_HI saveSupplierInvitation(JSONObject params) throws Exception;

    //报价管理-邀请供应商删除
    void deleteSupplierInvitation(JSONObject params) throws Exception;

    //报价管理-退出供应商报价
    void quitSupplierInvitation(JSONObject params) throws Exception;

    //报价管理-发起供应商邀请报价
    JSONObject updateSendInvitation(JSONObject paramsJONS,Integer userId) throws Exception;

    // 报价管理拒绝立项查询
    JSONObject findRejectSupplierInvitation(JSONObject paramsJONS, Integer pageIndex, Integer pageRows);
}
