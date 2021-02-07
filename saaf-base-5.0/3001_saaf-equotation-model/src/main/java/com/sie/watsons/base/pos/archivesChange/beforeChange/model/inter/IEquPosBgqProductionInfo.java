package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqProductionInfoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosBgqProductionInfo extends IBaseCommon<EquPosBgqProductionInfoEntity_HI>{
    //档案变更前-查询供应商生产信息
    JSONObject findBgqProductionInfo(JSONObject queryParamJSON, Integer pageIndex,
                                    Integer pageRows);

    //档案变更前-供应商生产信息保存
    EquPosBgqProductionInfoEntity_HI saveBgqProductionInfo(JSONObject params) throws Exception;
}
