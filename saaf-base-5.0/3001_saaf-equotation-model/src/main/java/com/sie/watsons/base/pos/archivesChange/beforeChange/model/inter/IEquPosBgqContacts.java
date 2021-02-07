package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqContactsEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgqContacts extends IBaseCommon<EquPosBgqContactsEntity_HI>{
    //档案变更前-供应商联系人目录查询
    JSONObject findBgqSupplierContactsInfo(JSONObject queryParamJSON, Integer pageIndex,
                                          Integer pageRows);

    //档案变更前-供应商联系人信息保存
    EquPosBgqContactsEntity_HI saveBgqSupplierContactsInfo(JSONObject params) throws Exception;

    //档案变更前-供应商联系人信息删除
    String deleteBgqSupplierContactsInfo(JSONObject jsonObject);
}
