package com.sie.watsons.base.pos.supplierinfo.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosOperationalStatusEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosOperationalStatus extends IBaseCommon<EquPosOperationalStatusEntity_HI>{
    //查询供应商经营情况
    JSONObject findOperationalStatusInfo(JSONObject queryParamJSON, Integer pageIndex,
                                         Integer pageRows);

    //供应商经营情况-保存
    EquPosOperationalStatusEntity_HI saveOperationalStatusInfo(JSONObject params) throws Exception;
}
