package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqSuppDeptInfoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgqSuppDeptInfo extends IBaseCommon<EquPosBgqSuppDeptInfoEntity_HI>{
    //档案变更前-供应商基础信息查询区分部门
    JSONObject findBgqSupplierInfoWithDept(JSONObject queryParamJSON, Integer pageIndex,
                                          Integer pageRows);

    //档案变更前-供应商基础信息保存区分部门
    EquPosBgqSuppDeptInfoEntity_HI saveBgqSupplierInfoWithDept(JSONObject params) throws Exception;
}
