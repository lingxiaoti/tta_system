package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqCredentialsEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgqCredentials extends IBaseCommon<EquPosBgqCredentialsEntity_HI>{
    //档案变更前-供应商资质信息查询
    JSONObject findBgqSupplierCredentialsInfo(JSONObject queryParamJSON, Integer pageIndex,
                                             Integer pageRows);

    //档案变更前-供应商资质信息保存
    EquPosBgqCredentialsEntity_HI saveBgqSupplierCredentialsInfo(JSONObject params) throws Exception;
}
