package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCategoryEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscCategory extends IBaseCommon<EquPosZzscCategoryEntity_HI>{
    //资质审查-供应商品类查询
    JSONObject findZzscSupplierCategorysInfo(JSONObject queryParamJSON, Integer pageIndex,
                                         Integer pageRows);

    //资质审查-供应商品类保存
    EquPosZzscCategoryEntity_HI saveZzscSupplierCategorysInfo(JSONObject params) throws Exception;

    //资质审查-供应商品类删除
    String deleteZzscSupplierCategorysInfo(JSONObject jsonObject);
}
