package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgAddressesEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgAddresses extends IBaseCommon<EquPosBgAddressesEntity_HI>{
    //档案变更后-查询供应商地址信息
    JSONObject findBgSupplierAddressInfo(JSONObject queryParamJSON, Integer pageIndex,
                                           Integer pageRows);

    //档案变更后-供应商地址信息保存
    EquPosBgAddressesEntity_HI saveBgSupplierAddressInfo(JSONObject params) throws Exception;

    //档案变更后-供应商地址信息删除
    String deleteBgSupplierAddressInfo(JSONObject jsonObject);
}
