package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCategoryEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgCategory extends IBaseCommon<EquPosBgCategoryEntity_HI>{
    //档案变更后-供应商品类查询
    JSONObject findBgSupplierCategorysInfo(JSONObject queryParamJSON, Integer pageIndex,
                                             Integer pageRows);

    //档案变更后-供应商品类保存
    EquPosBgCategoryEntity_HI saveBgSupplierCategorysInfo(JSONObject params) throws Exception;

    //档案变更后-供应商品类删除
    String deleteBgSupplierCategorysInfo(JSONObject jsonObject);
}
