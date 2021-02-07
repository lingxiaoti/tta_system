package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCapacityEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscCapacity extends IBaseCommon<EquPosZzscCapacityEntity_HI>{
    //资质审查-查询供应商产能信息
    JSONObject findZzscCapacityInfo(JSONObject queryParamJSON, Integer pageIndex,
                                Integer pageRows);

    //资质审查-供应商产能信息保存
    EquPosZzscCapacityEntity_HI saveZzscCapacityInfo(JSONObject params) throws Exception;

    //资质审查-供应商产能信息删除
    String deleteZzscCapacityInfo(JSONObject jsonObject);
}
