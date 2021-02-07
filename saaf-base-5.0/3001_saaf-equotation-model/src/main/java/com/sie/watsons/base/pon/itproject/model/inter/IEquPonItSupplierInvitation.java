package com.sie.watsons.base.pon.itproject.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItSupplierInvitationEntity_HI;

public interface IEquPonItSupplierInvitation extends IBaseCommon<EquPonItSupplierInvitationEntity_HI>{
    // 报价管理拒绝立项查询
    JSONObject findRejectSupplierInvitationIt(JSONObject paramsJONS, Integer pageIndex, Integer pageRows);
    //报价管理-发起供应商邀请报价
    JSONObject btnSendInvitationIt(JSONObject paramsJONS,Integer userId) throws Exception;

    //IT报价管理-邀请供应商查询，分页查询
    JSONObject findItSupplierInvitation(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //IT报价管理-邀请供应商删除
    void deleteItSupplierInvitation(JSONObject params) throws Exception;

    //报价管理-退出供应商报价
    void quitSupplierInvitation(JSONObject params) throws Exception;
}
