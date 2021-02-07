package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqCategoryEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgqCategory extends IBaseCommon<EquPosBgqCategoryEntity_HI>{
    //档案变更前-供应商品类查询
    JSONObject findBgqSupplierCategorysInfo(JSONObject queryParamJSON, Integer pageIndex,
                                           Integer pageRows);

    //档案变更前-供应商品类保存
    EquPosBgqCategoryEntity_HI saveBgqSupplierCategorysInfo(JSONObject params) throws Exception;

    //档案变更前-供应商品类删除
    String deleteBgqSupplierCategorysInfo(JSONObject jsonObject);
}
