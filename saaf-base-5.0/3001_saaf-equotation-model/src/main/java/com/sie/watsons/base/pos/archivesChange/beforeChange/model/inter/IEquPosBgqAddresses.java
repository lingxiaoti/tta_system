package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqAddressesEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgqAddresses extends IBaseCommon<EquPosBgqAddressesEntity_HI>{
    //档案变更前-查询供应商地址信息
    JSONObject findBgqSupplierAddressInfo(JSONObject queryParamJSON, Integer pageIndex,
                                         Integer pageRows);

    //档案变更前-供应商地址信息保存
    EquPosBgqAddressesEntity_HI saveBgqSupplierAddressInfo(JSONObject params) throws Exception;

    //档案变更前-供应商地址信息删除
    String deleteBgqSupplierAddressInfo(JSONObject jsonObject);
}
