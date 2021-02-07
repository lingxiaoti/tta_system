package com.sie.watsons.base.pos.supplierinfo.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSuppInfoWithDeptEntity_HI;

public interface IEquPosSuppInfoWithDept extends IBaseCommon<EquPosSuppInfoWithDeptEntity_HI>{
    //供应商基础信息查询-区分部门
    JSONObject findSupplierInfoWithDept(JSONObject queryParamJSON, Integer pageIndex,
                                Integer pageRows);

    //供应商基础信息保存-区分部门
    EquPosSuppInfoWithDeptEntity_HI saveSupplierInfoWithDept(JSONObject params) throws Exception;

    EquPosSuppInfoWithDeptEntity_HI saveForSupplierFilesDetail(JSONObject params,Integer userId);
}
