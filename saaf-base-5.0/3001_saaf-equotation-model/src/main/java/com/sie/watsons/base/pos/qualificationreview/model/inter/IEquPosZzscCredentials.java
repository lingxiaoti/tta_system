package com.sie.watsons.base.pos.qualificationreview.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCredentialsEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscCredentials extends IBaseCommon<EquPosZzscCredentialsEntity_HI>{
    //资质审查-供应商资质信息查询
    JSONObject findZzscSupplierCredentialsInfo(JSONObject queryParamJSON, Integer pageIndex,
                                           Integer pageRows);

    //资质审查-供应商资质信息保存
    EquPosZzscCredentialsEntity_HI saveZzscSupplierCredentialsInfo(JSONObject params) throws Exception;
}
