package com.sie.watsons.base.pos.blacklist.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.blacklist.model.entities.readonly.EquPosSupplierBlacklistEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.watsons.base.pos.blacklist.model.entities.EquPosSupplierBlacklistEntity_HI;

public interface IEquPosSupplierBlacklist {


	Pagination<EquPosSupplierBlacklistEntity_HI_RO> findEquPosBlacklistInfo(String params, Integer pageIndex, Integer pageRows);

    Pagination<EquPosSupplierBlacklistEntity_HI_RO> findSupplierLov(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    EquPosSupplierBlacklistEntity_HI saveEquPosBlacklist(JSONObject jsonObject, int userId) throws Exception;

    EquPosSupplierBlacklistEntity_HI_RO findSupBlacklistDatail(String params);

    Integer deleteSupplierBlacklist(JSONObject jsonObject, int userId);

    //供应商黑名单审批回调接口
    EquPosSupplierBlacklistEntity_HI backListApprovalCallback(JSONObject parseObject,int userId) throws Exception;

    EquPosSupplierBlacklistEntity_HI saveEquPosBlack(JSONObject jsonObject, int userId);
}
