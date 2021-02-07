package com.sie.watsons.base.pos.recover.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.recover.model.entities.EquPosSupplierRecoverEntity_HI;
import com.sie.watsons.base.pos.recover.model.entities.readonly.EquPosSupplierRecoverEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IEquPosSupplierRecover {


	Pagination<EquPosSupplierRecoverEntity_HI_RO> findEquPosRecoverInfo(String params, Integer pageIndex, Integer pageRows);

    Pagination<EquPosSupplierRecoverEntity_HI_RO> findSupplierLov(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    EquPosSupplierRecoverEntity_HI saveEquPosRecover(JSONObject jsonObject, int userId) throws Exception;

    EquPosSupplierRecoverEntity_HI_RO findSupRecoverDatail(String params);

    Integer deleteSupplierRecover(JSONObject jsonObject, int userId);

    EquPosSupplierRecoverEntity_HI processCallback(JSONObject paramsObject, int userId);

    EquPosSupplierRecoverEntity_HI saveEquPosRecoverSumbit(JSONObject jsonObject, int userId);
}
