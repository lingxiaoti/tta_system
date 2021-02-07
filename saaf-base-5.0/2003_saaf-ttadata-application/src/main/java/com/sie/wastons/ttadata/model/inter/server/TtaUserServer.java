package com.sie.wastons.ttadata.model.inter.server;

import com.github.xiaolyuh.annotation.Cacheable;
import com.github.xiaolyuh.annotation.FirstCache;
import com.github.xiaolyuh.annotation.SecondaryCache;
import com.google.common.collect.Maps;
import com.sie.wastons.sql.SqlTemplateUtil;
import com.sie.wastons.ttadata.model.entities.readyonly.UserInfoEntity_RO;
import com.sie.wastons.ttadata.model.entities.readyonly.VmiPersonInfoEntity_HI_RO;
import com.sie.wastons.ttadata.model.inter.ITtaUser;
import com.sie.wastons.view.ApiRequest;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class TtaUserServer implements ITtaUser {

	@Autowired
	private BaseViewObject<UserInfoEntity_RO> userInfoDAO_HI_RO;
	@Autowired
	private BaseViewObject<VmiPersonInfoEntity_HI_RO> vmiPersonInfoDAO_HI_RO;

	@Override
	@Cacheable(key = "#userId+''", value = "userInfo", depict = "用户信息", firstCache = @FirstCache(expireTime = 10), secondaryCache = @SecondaryCache(expireTime = 10, timeUnit = TimeUnit.MINUTES))
	public UserInfoEntity_RO getUserInfo(Integer userId) {
		if (userId == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer(UserInfoEntity_RO.QUERY_SQL);
		sb.append(" and bu.user_id = " + userId);
		UserInfoEntity_RO userInfoEntity_ro = userInfoDAO_HI_RO.get(sb,
				Maps.newHashMap());
		return userInfoEntity_ro;
	}

	/**
	 * 查询人员信息
	 * 
	 * @param params
	 * @return
	 * @throws IllegalAccessException
	 */
	public Pagination<VmiPersonInfoEntity_HI_RO> findPersonInfo(
			ApiRequest<VmiPersonInfoEntity_HI_RO> params)
			throws IllegalAccessException {
		try {
			return SqlTemplateUtil.findSqlPagination(vmiPersonInfoDAO_HI_RO,
					params.getParams(), VmiPersonInfoEntity_HI_RO.personInfo_sql,
					params.getPageIndex(), params.getPageRows());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	@Override
	public UserInfoEntity_RO getUserInfoByUsrName(String userName) {
		if (userName == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer(UserInfoEntity_RO.QUERY_SQL);
		sb.append(" and bu.USER_NAME = '" + userName+"'");
		UserInfoEntity_RO userInfoEntity_ro = userInfoDAO_HI_RO.get(sb,
				Maps.newHashMap());
		return userInfoEntity_ro;
	}

	@Override
	@Cacheable(key = "#userType+''", value = "userInfoForType", depict = "用户信息", firstCache = @FirstCache(expireTime = 10), secondaryCache = @SecondaryCache(expireTime = 10, timeUnit = TimeUnit.MINUTES))
	public List<UserInfoEntity_RO> getUserInfoByType(String userType) {
		StringBuffer sb = new StringBuffer(
				UserInfoEntity_RO.QUERY_BY_USER_TYPE_SQL);
		sb.append(" and baseUsers.user_type = " + userType);
		List<UserInfoEntity_RO> userInfoEntityRo = userInfoDAO_HI_RO.findList(
				sb, Maps.newHashMap());
		return userInfoEntityRo;
	}

	@Override
	// @Cacheable(key = "#userIds+''", value = "userInfos", depict = "用户信息",
	// firstCache = @FirstCache(expireTime = 10), secondaryCache =
	// @SecondaryCache(expireTime = 10, timeUnit = TimeUnit.MINUTES))
	public List<UserInfoEntity_RO> findProccessUsersByuserId(Integer userId) {

		List<UserInfoEntity_RO> personList = null;
		HashMap<String, Object> queryMap = new HashMap<>();
		List<List<UserInfoEntity_RO>> data = new ArrayList<List<UserInfoEntity_RO>>();
		StringBuffer sb = new StringBuffer(
				UserInfoEntity_RO.QUERY_USER_BY_PROCCESS_START_USER_ID);
		do {
			queryMap.put("userId", userId);
			personList = userInfoDAO_HI_RO.findList(sb, queryMap);

			if (personList != null && !personList.isEmpty()) {
				UserInfoEntity_RO personHiRo = personList.get(0);
				userId = personHiRo.getUserId();
				data.add(personList);
			}

		} while (personList != null && !personList.isEmpty());

		if (data.size() == 0) {
			personList = new ArrayList<UserInfoEntity_RO>();
			data.add(personList);
		}
		// List<UserInfoEntity_RO> result = new ArrayList<UserInfoEntity_RO>();
		// for (List<UserInfoEntity_RO> li : data) {
		// List<UserInfoEntity_RO> l = li;
		// for (UserInfoEntity_RO r : l) {
		// result.add(r);
		// }
		// }
		return data.get(0);
	}

	@Override
	// @Cacheable(key = "#userName+''", value = "userInfo", depict = "用户信息",
	// firstCache = @FirstCache(expireTime = 10), secondaryCache =
	// @SecondaryCache(expireTime = 10, timeUnit = TimeUnit.MINUTES))
	public UserInfoEntity_RO getUserInfoByuserName(String userName) {
		if (userName == null) {
			// return null;
			userName = "ADMIN";
		}

		String sqls = "SELECT bu.user_id        userId,\n"
				+ "       bu.USER_NAME      userName,\n"
				+ "       bu.USER_FULL_NAME userFullName,\n"
				+ "       bu.PHONE_NUMBER   phoneNumber,\n"
				+ "       bp.email          email\n" + "  FROM BASE_USERS bu\n"
				+ "  left join base_person bp\n"
				+ "    on bu.person_id = bp.person_id\n"
				+ " WHERE bu.user_name=:userName";

		StringBuffer sb = new StringBuffer(sqls);
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("userName", userName);
		sb.append(" and bu.user_name = '" + userName + "'");
		UserInfoEntity_RO userInfoEntity_ro = userInfoDAO_HI_RO.get(sb,
				queryMap);
		return userInfoEntity_ro;
	}

}
