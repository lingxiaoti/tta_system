package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierQaBrandEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaBrandEntity_HI;

public interface IPlmSupplierQaBrand extends IBaseCommon<PlmSupplierQaBrandEntity_HI> {

	Pagination<PlmSupplierQaBrandEntity_HI_RO> findPlmSupplierQaBrandInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmSupplierQaBrandEntity_HI> savePlmSupplierQaBrandInfo(JSONObject queryParamJSON);

	Integer deletePlmSupplierQaBrandInfo(JSONObject queryParamJSON) throws Exception;

	void saveCheckSupplierQaDelete(Integer plmSupplierQaNonObInfoId) throws Exception;
}
