package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqAssociateSuppEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgqAssociateSupp extends IBaseCommon<EquPosBgqAssociateSuppEntity_HI>{
    //档案变更前-供应商关联工厂查询
    JSONObject findBgqAssociatedSupplier(JSONObject queryParamJSON, Integer pageIndex,
                                        Integer pageRows);

    //档案变更前-供应商关联工厂保存
    EquPosBgqAssociateSuppEntity_HI saveBgqAssociatedSupplier(JSONObject params) throws Exception;

    //档案变更前-供应商关联工厂删除
    String deleteBgqAssociatedSupplier(JSONObject jsonObject);
}
