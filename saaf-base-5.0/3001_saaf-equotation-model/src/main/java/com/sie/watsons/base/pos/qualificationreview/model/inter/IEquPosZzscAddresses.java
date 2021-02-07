package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscAddressesEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscAddresses extends IBaseCommon<EquPosZzscAddressesEntity_HI>{
    //资质审查-查询供应商地址信息
    JSONObject findZzscSupplierAddressInfo(JSONObject queryParamJSON, Integer pageIndex,
                                       Integer pageRows);

    //资质审查-供应商地址信息保存
    EquPosZzscAddressesEntity_HI saveZzscSupplierAddressInfo(JSONObject params) throws Exception;

    //资质审查-供应商地址信息删除
    String deleteZzscSupplierAddressInfo(JSONObject jsonObject);
}
