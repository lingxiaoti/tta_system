package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqOperationStatusEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgqOperationStatus extends IBaseCommon<EquPosBgqOperationStatusEntity_HI>{
    //档案变更前-供应商经营情况查询
    JSONObject findBgqOperationalStatusInfo(JSONObject queryParamJSON, Integer pageIndex,
                                           Integer pageRows);

    //档案变更前-供应商经营情况保存
    EquPosBgqOperationStatusEntity_HI saveBgqOperationalStatusInfo(JSONObject params) throws Exception;
}
