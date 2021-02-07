package com.sie.wastons.ttadata.model.inter;

import java.util.List;

import com.sie.wastons.ttadata.model.entities.readyonly.UserInfoEntity_RO;
import com.sie.wastons.ttadata.model.entities.readyonly.VmiPersonInfoEntity_HI_RO;
import com.sie.wastons.view.ApiRequest;
import com.yhg.hibernate.core.paging.Pagination;

public interface ITtaUser {
	UserInfoEntity_RO getUserInfo(Integer userId);

	List<UserInfoEntity_RO> findProccessUsersByuserId(Integer userId);

	public Pagination<VmiPersonInfoEntity_HI_RO> findPersonInfo(
			ApiRequest<VmiPersonInfoEntity_HI_RO> params)
			throws IllegalAccessException;

	/**
	 * 传入用户类型的json字符串
	 * 
	 * @param jsonStr
	 *            需要查询的用户信息参数
	 * @return
	 */
	List<UserInfoEntity_RO> getUserInfoByType(String jsonStr);

	UserInfoEntity_RO getUserInfoByuserName(String userName);

	UserInfoEntity_RO getUserInfoByUsrName(String userName);
}
