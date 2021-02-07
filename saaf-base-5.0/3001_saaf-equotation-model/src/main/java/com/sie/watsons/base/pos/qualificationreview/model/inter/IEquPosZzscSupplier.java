package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscSupplierEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscSupplierEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscSupplier extends IBaseCommon<EquPosZzscSupplierEntity_HI>{
    //资质审查-供应商基础信息查询
    JSONObject findZzscSupplierInfo(JSONObject queryParamJSON, Integer pageIndex,
                                Integer pageRows);

    //资质审查-供应商基础信息保存
    EquPosZzscSupplierEntity_HI saveZzscSupplierInfo(JSONObject params) throws Exception;

    Pagination<EquPosZzscSupplierEntity_HI_RO> findZzscSupplierLov(JSONObject jsonObject, Integer pageIndex, Integer pageRows);
}
