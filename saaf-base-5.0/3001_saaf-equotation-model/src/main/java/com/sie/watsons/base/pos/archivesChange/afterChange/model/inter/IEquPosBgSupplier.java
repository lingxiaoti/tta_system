package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgSupplierEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgSupplier extends IBaseCommon<EquPosBgSupplierEntity_HI>{
    //档案变更后-供应商基础信息查询
    JSONObject findBgSupplierInfo(JSONObject queryParamJSON, Integer pageIndex,
                                    Integer pageRows);

    //档案变更后-供应商基础信息保存
    EquPosBgSupplierEntity_HI saveBgSupplierInfo(JSONObject params) throws Exception;
}
