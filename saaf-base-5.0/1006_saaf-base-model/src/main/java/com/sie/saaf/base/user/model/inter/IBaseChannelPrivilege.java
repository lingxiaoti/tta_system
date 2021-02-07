package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BaseChannelPrivilegeEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

public interface IBaseChannelPrivilege extends IBaseCommon<BaseChannelPrivilegeEntity_HI> {

	/**
	 * 同步产品渠道授权信息
	 * @author ZhangJun
	 * @createTime 2018/3/14
	 * @description 同步产品渠道授权信息
	 */
	JSONObject saveSyncBaseChannelPrivilege(JSONObject queryParamJSON) throws Exception;

	List<String> findListByOrgId(JSONObject paramJSON);
}
