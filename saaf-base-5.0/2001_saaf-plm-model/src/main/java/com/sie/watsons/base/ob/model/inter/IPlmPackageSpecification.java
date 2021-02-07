package com.sie.watsons.base.ob.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ob.model.entities.readonly.PlmObPackageReportEntity_HI_RO;
import com.sie.watsons.base.ob.model.entities.readonly.PlmPackageSpecificationEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ob.model.entities.PlmPackageSpecificationEntity_HI;

public interface IPlmPackageSpecification extends IBaseCommon<PlmPackageSpecificationEntity_HI> {

	Pagination<PlmPackageSpecificationEntity_HI_RO> findPlmPackageSpecificationInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	Pagination<PlmObPackageReportEntity_HI_RO> findPlmObPackageReportInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<PlmPackageSpecificationEntity_HI> savePlmPackageSpecificationInfo(JSONObject queryParamJSON) throws Exception;

	Integer deletePlmPackageSpecificationInfo(JSONObject queryParamJSON);

}
