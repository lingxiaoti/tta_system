package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscSuppDeptInfoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscSuppDeptInfo extends IBaseCommon<EquPosZzscSuppDeptInfoEntity_HI>{
    //资质审查-供应商基础信息查询区分部门
    JSONObject findZzscSupplierInfoWithDept(JSONObject queryParamJSON, Integer pageIndex,
                                        Integer pageRows);

    //资质审查-供应商基础信息保存区分部门
    EquPosZzscSuppDeptInfoEntity_HI saveZzscSupplierInfoWithDept(JSONObject params) throws Exception;
}
