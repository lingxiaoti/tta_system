package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgAssociateSuppEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgAssociateSupp extends IBaseCommon<EquPosBgAssociateSuppEntity_HI>{
    //档案变更后-供应商关联工厂查询
    JSONObject findBgAssociatedSupplier(JSONObject queryParamJSON, Integer pageIndex,
                                          Integer pageRows);

    //档案变更后-供应商关联工厂保存
    EquPosBgAssociateSuppEntity_HI saveBgAssociatedSupplier(JSONObject params) throws Exception;

    //档案变更后-供应商关联工厂删除
    String deleteBgAssociatedSupplier(JSONObject jsonObject);
}
