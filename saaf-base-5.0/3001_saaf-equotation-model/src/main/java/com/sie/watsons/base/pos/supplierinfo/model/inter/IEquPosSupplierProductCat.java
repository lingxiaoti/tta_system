package com.sie.watsons.base.pos.supplierinfo.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierProductCatEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosSupplierProductCat extends IBaseCommon<EquPosSupplierProductCatEntity_HI>{
    //供应商品类查询
    JSONObject findSupplierCategorysInfo(JSONObject queryParamJSON, Integer pageIndex,
                                         Integer pageRows);

    //供应商品类-保存
    EquPosSupplierProductCatEntity_HI saveSupplierCategorysInfo(JSONObject params) throws Exception;

    //供应商品类-删除
    String deleteSupplierCategorysInfo(JSONObject jsonObject);
}
