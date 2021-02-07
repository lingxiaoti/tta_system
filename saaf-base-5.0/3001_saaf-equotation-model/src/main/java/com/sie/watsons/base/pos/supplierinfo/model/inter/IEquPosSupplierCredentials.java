package com.sie.watsons.base.pos.supplierinfo.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierCredentialsEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosSupplierCredentials extends IBaseCommon<EquPosSupplierCredentialsEntity_HI>{
    //供应商资质信息查询
    JSONObject findSupplierCredentialsInfo(JSONObject queryParamJSON, Integer pageIndex,
                                           Integer pageRows);

    //供应商资质信息-保存
    EquPosSupplierCredentialsEntity_HI saveSupplierCredentialsInfo(JSONObject params) throws Exception;
}
