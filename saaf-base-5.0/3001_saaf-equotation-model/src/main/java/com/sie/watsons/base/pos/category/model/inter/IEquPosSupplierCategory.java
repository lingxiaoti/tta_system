package com.sie.watsons.base.pos.category.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.category.model.entities.EquPosSupplierCategoryEntity_HI;
import com.sie.watsons.base.pos.category.model.entities.readonly.EquPosSupplierCategoryEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IEquPosSupplierCategory extends IBaseCommon<EquPosSupplierCategoryEntity_HI>{

    /**
     * 分页查询供应商品分类详情
     */
    Pagination<EquPosSupplierCategoryEntity_HI_RO> findSupplierCategoryPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 批量保存供应商品分类
     */
    String saveSupplierCategoryList(String params, int userId) throws Exception;
    /**
     * 删除单行数据
     */
    String deleteSupplierCategory(JSONObject jsonObject, int userId);

    /**
     * 获取各供应商状态下总行数信息
     */
    List<EquPosSupplierCategoryEntity_HI_RO> findSupplierStatusReportForm();
    /**
     * 获取各供应商库报表
     */
    Pagination<EquPosSupplierCategoryEntity_HI_RO> findSupplierReportForm(JSONObject jsonObject,Integer pageIndex,Integer pageRows);
}
