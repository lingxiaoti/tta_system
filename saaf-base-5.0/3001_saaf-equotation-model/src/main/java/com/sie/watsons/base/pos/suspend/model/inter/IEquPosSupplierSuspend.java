package com.sie.watsons.base.pos.suspend.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.suspend.model.entities.readonly.EquPosSupplierSuspendEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.suspend.model.entities.EquPosSupplierSuspendEntity_HI;

public interface IEquPosSupplierSuspend {


	Pagination<EquPosSupplierSuspendEntity_HI_RO> findEquPosSuspendInfo(String params, Integer pageIndex, Integer pageRows);

    Pagination<EquPosSupplierSuspendEntity_HI_RO> findSupplierLov(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    EquPosSupplierSuspendEntity_HI saveEquPosSuspend(JSONObject jsonObject, int userId) throws Exception;

    EquPosSupplierSuspendEntity_HI_RO findSupSuspendDatail(String params);

    Integer deleteSuspend(JSONObject jsonObject, int userId);

    //供应商暂停/淘汰审批回调接口
    EquPosSupplierSuspendEntity_HI suspendApprovalCallback(JSONObject parseObject,int userId) throws Exception;

    EquPosSupplierSuspendEntity_HI saveEquPosSuspendSubmit(JSONObject jsonObject, int userId);

    void deleteSuspendFile(JSONObject jsonObject, int userId);
}
