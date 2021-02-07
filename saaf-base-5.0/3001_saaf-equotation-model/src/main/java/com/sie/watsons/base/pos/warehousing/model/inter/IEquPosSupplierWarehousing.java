package com.sie.watsons.base.pos.warehousing.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.warehousing.model.entities.EquPosSupplierWarehousingEntity_HI;
import com.sie.watsons.base.pos.warehousing.model.entities.readonly.EquPosSupplierWarehousingEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IEquPosSupplierWarehousing extends IBaseCommon<EquPosSupplierWarehousingEntity_HI>{

    Pagination<EquPosSupplierWarehousingEntity_HI_RO> findEquPosWarehousingInfo(String params, Integer pageIndex, Integer pageRows);

    EquPosSupplierWarehousingEntity_HI_RO findSupWarehousingDatail(String params);

    EquPosSupplierWarehousingEntity_HI saveEquPosWarehousing(JSONObject jsonObject, int userId) throws Exception ;

    Integer deleteSupplierWarehousing(JSONObject jsonObject, int userId);

    EquPosSupplierWarehousingEntity_HI updateWarehousingCallback(JSONObject paramsObject, int userId);

    EquPosSupplierWarehousingEntity_HI saveSupWarehousingDatailSumbit(JSONObject jsonObject, int userId);

    JSONObject findSupWarehousingDatailLine(JSONObject jsonObject);
}
