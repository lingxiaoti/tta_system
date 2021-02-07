package com.sie.watsons.base.pos.supplierinfo.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosAssociatedSupplierEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosAssociatedSupplier extends IBaseCommon<EquPosAssociatedSupplierEntity_HI>{
    //供应商关联工厂查询
    JSONObject findAssociatedSupplier(JSONObject queryParamJSON, Integer pageIndex,
                                Integer pageRows);

    //供应商关联工厂-保存
    EquPosAssociatedSupplierEntity_HI saveAssociatedSupplier(JSONObject params) throws Exception;

    //供应商关联工厂-删除
    String deleteAssociatedSupplier(JSONObject jsonObject);
}
