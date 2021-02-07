package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgSuppDeptInfoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgSuppDeptInfo extends IBaseCommon<EquPosBgSuppDeptInfoEntity_HI>{
    //档案变更后-供应商基础信息查询区分部门
    JSONObject findBgSupplierInfoWithDept(JSONObject queryParamJSON, Integer pageIndex,
                                            Integer pageRows);

    //档案变更后-供应商基础信息保存区分部门
    EquPosBgSuppDeptInfoEntity_HI saveBgSupplierInfoWithDept(JSONObject params) throws Exception;
}
