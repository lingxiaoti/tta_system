package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqSupplierEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgqSupplier extends IBaseCommon<EquPosBgqSupplierEntity_HI>{
    //档案变更前-供应商基础信息查询
    JSONObject findBgqSupplierInfo(JSONObject queryParamJSON, Integer pageIndex,
                                  Integer pageRows);

    //档案变更前-供应商基础信息保存
    EquPosBgqSupplierEntity_HI saveBgqSupplierInfo(JSONObject params) throws Exception;
}
