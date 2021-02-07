package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscAssociateSuppEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscAssociateSupp extends IBaseCommon<EquPosZzscAssociateSuppEntity_HI>{
    //资质审查-供应商关联工厂查询
    JSONObject findZzscAssociatedSupplier(JSONObject queryParamJSON, Integer pageIndex,
                                      Integer pageRows);

    //资质审查-供应商关联工厂保存
    EquPosZzscAssociateSuppEntity_HI saveZzscAssociatedSupplier(JSONObject params) throws Exception;

    //资质审查-供应商关联工厂删除
    String deleteZzscAssociatedSupplier(JSONObject jsonObject);
}
