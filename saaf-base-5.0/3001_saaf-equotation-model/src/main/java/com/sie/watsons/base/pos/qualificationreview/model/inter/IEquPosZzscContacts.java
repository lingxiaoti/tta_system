package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscContactsEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscContacts extends IBaseCommon<EquPosZzscContactsEntity_HI>{
    //资质审查-供应商联系人目录查询
    JSONObject findZzscSupplierContactsInfo(JSONObject queryParamJSON, Integer pageIndex,
                                        Integer pageRows);

    //资质审查-供应商联系人信息保存
    EquPosZzscContactsEntity_HI saveZzscSupplierContactsInfo(JSONObject params) throws Exception;

    //资质审查-供应商联系人信息删除
    String deleteZzscSupplierContactsInfo(JSONObject jsonObject);
}
