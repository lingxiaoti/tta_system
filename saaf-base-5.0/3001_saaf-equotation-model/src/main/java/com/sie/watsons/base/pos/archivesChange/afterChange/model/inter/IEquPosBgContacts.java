package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgContactsEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgContacts extends IBaseCommon<EquPosBgContactsEntity_HI>{
    //档案变更后-供应商联系人目录查询
    JSONObject findBgSupplierContactsInfo(JSONObject queryParamJSON, Integer pageIndex,
                                            Integer pageRows);

    //档案变更后-供应商联系人信息保存
    EquPosBgContactsEntity_HI saveBgSupplierContactsInfo(JSONObject params) throws Exception;

    //档案变更后-供应商联系人信息删除
    String deleteBgSupplierContactsInfo(JSONObject jsonObject);
}
