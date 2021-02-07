package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierBrandMapEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierBrandMapEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IPlmSupplierBrandMap extends IBaseCommon<PlmSupplierBrandMapEntity_HI>{

    Pagination<PlmSupplierBrandMapEntity_HI_RO> findSupplierBrandMap(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    Pagination<PlmSupplierBrandMapEntity_HI_RO> findDistinctSupplierBrandMap(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
