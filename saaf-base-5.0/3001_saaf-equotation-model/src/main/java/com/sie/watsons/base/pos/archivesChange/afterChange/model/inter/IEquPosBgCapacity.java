package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCapacityEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgCapacity extends IBaseCommon<EquPosBgCapacityEntity_HI>{
    //档案变更后-查询供应商产能信息
    JSONObject findBgCapacityInfo(JSONObject queryParamJSON, Integer pageIndex,
                                    Integer pageRows);

    //档案变更后-供应商产能信息保存
    EquPosBgCapacityEntity_HI saveBgCapacityInfo(JSONObject params) throws Exception;

    //档案变更后-供应商产能信息删除
    String deleteBgCapacityInfo(JSONObject jsonObject);
}
