package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscProductionInfoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscProductionInfo extends IBaseCommon<EquPosZzscProductionInfoEntity_HI>{
    //资质审查-查询供应商生产信息
    JSONObject findZzscProductionInfo(JSONObject queryParamJSON, Integer pageIndex,
                                  Integer pageRows);

    //资质审查-供应商生产信息保存
    EquPosZzscProductionInfoEntity_HI saveZzscProductionInfo(JSONObject params) throws Exception;
}
