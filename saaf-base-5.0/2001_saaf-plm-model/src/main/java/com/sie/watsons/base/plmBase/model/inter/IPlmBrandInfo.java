package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.PlmBrandInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmUserBrandMapEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBrandInfoEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IPlmBrandInfo extends IBaseCommon<PlmBrandInfoEntity_HI> {

	Pagination<PlmBrandInfoEntity_HI_RO> findPlmBrandInfoInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmBrandInfoEntity_HI savePlmBrandInfoInfo(JSONObject queryParamJSON)
			throws Exception;

	PlmBrandInfoEntity_HI saveConfirmedPlmBrandStatus(JSONObject params)
			throws Exception;

	PlmBrandInfoEntity_HI_RO findPlmBrandInfocode();

	/**
	 * 查询brandMap
	 * @param cname
	 * @param ename
	 * @param motherCompanyId
	 * @param groupbrandId
	 * @return
	 */
	PlmBrandInfoEntity_HI findBybrandOrCompany(String cname, String ename, Integer motherCompanyId,
											   Integer groupbrandId, Integer brandInfoId);

	PlmBrandInfoEntity_HI findBybrandOrCompany(String cname, String ename, Integer motherCompanyId,
											   Integer groupbrandId, Integer brandInfoId, List<String> status);

	PlmBrandInfoEntity_HI findBybrand(String cname, String ename, String motherCompany,
									  String groupbrand, Integer brandInfoId);

	/**
	 * 初始化补全brandInfo表中的motherCompanyId
	 */
	void initMotherCompanyIdForMC();

	void initGroupBrandId();

}
