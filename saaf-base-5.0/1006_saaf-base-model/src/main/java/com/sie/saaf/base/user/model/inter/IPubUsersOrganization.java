package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.PubUsersOrganizationEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.PubUsersOrganizationEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

/**
 * 人员与组织关系
 *
 */
public interface IPubUsersOrganization extends IBaseCommon<PubUsersOrganizationEntity_HI> {

	List<PubUsersOrganizationEntity_HI_RO> findListAssign(JSONObject queryParamJSON);
	/**
	 *yzl
	 * @param queryParamJSON
	 * @return
	 */
	void saveListAssign(JSONObject queryParamJSON);
}
