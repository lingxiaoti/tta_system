package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscOperationStatusEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscOperationStatus extends IBaseCommon<EquPosZzscOperationStatusEntity_HI>{
    //资质审查-供应商经营情况查询
    JSONObject findZzscOperationalStatusInfo(JSONObject queryParamJSON, Integer pageIndex,
                                         Integer pageRows);

    //资质审查-供应商经营情况保存
    EquPosZzscOperationStatusEntity_HI saveZzscOperationalStatusInfo(JSONObject params) throws Exception;
}
