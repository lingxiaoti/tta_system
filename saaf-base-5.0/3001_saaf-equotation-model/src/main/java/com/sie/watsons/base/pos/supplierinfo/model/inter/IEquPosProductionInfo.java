package com.sie.watsons.base.pos.supplierinfo.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosProductionInfoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosProductionInfo extends IBaseCommon<EquPosProductionInfoEntity_HI>{
    //查询供应商生产信息
    JSONObject findProductionInfo(JSONObject queryParamJSON, Integer pageIndex,
                                  Integer pageRows);

    //供应商生产信息-保存
    EquPosProductionInfoEntity_HI saveProductionInfo(JSONObject params) throws Exception;
}
