package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgOperationStatusEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgOperationStatus extends IBaseCommon<EquPosBgOperationStatusEntity_HI>{
    //档案变更后-供应商经营情况查询
    JSONObject findBgOperationalStatusInfo(JSONObject queryParamJSON, Integer pageIndex,
                                             Integer pageRows);

    //档案变更后-供应商经营情况保存
    EquPosBgOperationStatusEntity_HI saveBgOperationalStatusInfo(JSONObject params) throws Exception;
}
