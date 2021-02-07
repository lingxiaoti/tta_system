package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmMotherCompanyEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.plmBase.model.entities.PlmMotherCompanyEntity_HI;

public interface IPlmMotherCompany extends IBaseCommon<PlmMotherCompanyEntity_HI> {

	Pagination<PlmMotherCompanyEntity_HI_RO> findPlmMotherCompanyInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmMotherCompanyEntity_HI savePlmMotherCompanyInfo(JSONObject queryParamJSON);

	PlmMotherCompanyEntity_HI saveConfirmedPlmMotherCompanyStatus(JSONObject params) throws Exception;

	PlmMotherCompanyEntity_HI findByCompanyName(String companyName);

	/**
	 * 调用  同步MotherCompany的 存储过程
	 * @return
	 */
	String syncMotherCompany() throws Exception;
}
