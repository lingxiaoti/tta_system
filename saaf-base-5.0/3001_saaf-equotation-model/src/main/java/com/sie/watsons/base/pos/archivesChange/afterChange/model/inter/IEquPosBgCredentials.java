package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCredentialsEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgCredentials extends IBaseCommon<EquPosBgCredentialsEntity_HI>{
    //档案变更后-供应商资质信息查询
    JSONObject findBgSupplierCredentialsInfo(JSONObject queryParamJSON, Integer pageIndex,
                                               Integer pageRows);

    //档案变更后-供应商资质信息保存
    EquPosBgCredentialsEntity_HI saveBgSupplierCredentialsInfo(JSONObject params) throws Exception;
}
