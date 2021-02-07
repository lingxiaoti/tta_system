package com.sie.watsons.base.pos.supplierinfo.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosCapacityInfoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosCapacityInfo extends IBaseCommon<EquPosCapacityInfoEntity_HI>{
    //查询供应商产能信息
    JSONObject findCapacityInfo(JSONObject queryParamJSON, Integer pageIndex,
                                Integer pageRows);

    //供应商产能信息-保存
    EquPosCapacityInfoEntity_HI saveCapacityInfo(JSONObject params) throws Exception;

    //供应商产能信息-删除
    String deleteCapacityInfo(JSONObject jsonObject);
}
