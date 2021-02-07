package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierQaDealerEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaDealerEntity_HI;

public interface IPlmSupplierQaDealer extends IBaseCommon<PlmSupplierQaDealerEntity_HI> {

	Pagination<PlmSupplierQaDealerEntity_HI_RO> findPlmSupplierQaDealerInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmSupplierQaDealerEntity_HI> savePlmSupplierQaDealerInfo(JSONObject queryParamJSON);

	Integer deletePlmSupplierQaDealerInfo(JSONObject queryParamJSON) throws Exception;

}
