package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqCapacityEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgqCapacity extends IBaseCommon<EquPosBgqCapacityEntity_HI>{
    //档案变更前-查询供应商产能信息
    JSONObject findBgqCapacityInfo(JSONObject queryParamJSON, Integer pageIndex,
                                  Integer pageRows);

    //档案变更前-供应商产能信息保存
    EquPosBgqCapacityEntity_HI saveBgqCapacityInfo(JSONObject params) throws Exception;

    //档案变更前-供应商产能信息删除
    String deleteBgqCapacityInfo(JSONObject jsonObject);
}
