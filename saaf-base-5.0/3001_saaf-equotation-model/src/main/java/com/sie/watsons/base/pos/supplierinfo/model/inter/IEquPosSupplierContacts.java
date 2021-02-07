package com.sie.watsons.base.pos.supplierinfo.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierContactsEntity_HI;

public interface IEquPosSupplierContacts extends IBaseCommon<EquPosSupplierContactsEntity_HI>{

    EquPosSupplierContactsEntity_HI findContactPeopleInfo(String params);

    //供应商联系人目录查询
    JSONObject findSupplierContactsInfo(JSONObject queryParamJSON, Integer pageIndex,
                                        Integer pageRows);

    //供应商联系人信息-保存
    EquPosSupplierContactsEntity_HI saveSupplierContactsInfo(JSONObject params) throws Exception;

    //供应商联系人信息-删除
    String deleteSupplierContactsInfo(JSONObject jsonObject);

    //供应商联系人信息报表查询(Non-trade)
    JSONObject findSupplierContactReportForm(JSONObject queryParamJSON, Integer pageIndex,
                                        Integer pageRows);
}
