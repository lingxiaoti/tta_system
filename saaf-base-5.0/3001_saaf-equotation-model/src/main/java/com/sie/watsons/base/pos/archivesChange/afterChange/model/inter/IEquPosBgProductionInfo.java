package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgProductionInfoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgProductionInfo extends IBaseCommon<EquPosBgProductionInfoEntity_HI>{
    //档案变更后-查询供应商生产信息
    JSONObject findBgProductionInfo(JSONObject queryParamJSON, Integer pageIndex,
                                      Integer pageRows);

    //档案变更后-供应商生产信息保存
    EquPosBgProductionInfoEntity_HI saveBgProductionInfo(JSONObject params) throws Exception;
}
