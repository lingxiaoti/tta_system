package com.sie.saaf.deploy.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.deploy.constant.DeployConstant;
import com.sie.saaf.deploy.model.entities.BaseDeployeeAppInfoEntity_HI;
import com.sie.saaf.deploy.model.entities.BaseDeployeeAppMenuEntity_HI;
import com.sie.saaf.deploy.model.entities.readonly.BaseDeployeeAppInfoEntity_HI_RO;
import com.sie.saaf.deploy.model.inter.IBaseDeployeeAppInfo;
import com.sie.saaf.deploy.model.inter.IBaseDeployeeAppMenu;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

import redis.clients.jedis.JedisCluster;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseAppDeployeeMenuServer")
public class BaseDeployeeAppMenuServer extends BaseCommonServer<BaseDeployeeAppMenuEntity_HI> implements IBaseDeployeeAppMenu {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDeployeeAppMenuServer.class);
	
	
	
	@Autowired
	private ViewObject<BaseDeployeeAppMenuEntity_HI> baseAppDeployeeMenuDAO_HI;
	
	@Autowired
	private IBaseDeployeeAppInfo baseDeployeeAppInfoServer;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	public BaseDeployeeAppMenuServer() {
		super();
	}
	
	/**
	 * 根据ID查询应用菜单信息
	 * @author laoqunzhao 2018-08-16
	 * @param id 主键
	 * @return BaseDeployeeAppInfoEntity_HI
	 */
	@Override
	public BaseDeployeeAppMenuEntity_HI getById(Integer id) {
		return baseAppDeployeeMenuDAO_HI.getById(id);
	}
	
	
	/**
	 * 根据应用ID查询应用菜单信息，按排序码进行排序
	 * @author laoqunzhao 2018-08-16
	 * @param appWapId 应用主键
	 * @return List<BaseDeployeeAppMenuEntity_HI>
	 */
	@Override
	public List<BaseDeployeeAppMenuEntity_HI> findByAppWapId(Integer appWapId){
		StringBuffer hql = new StringBuffer("from BaseDeployeeAppMenuEntity_HI menu where 1=1");
		hql.append(" and menu.deleteFlag=0 and menu.appWapId=" + appWapId);
		hql.append(" order by menu.appMenuSort asc");
		return baseAppDeployeeMenuDAO_HI.findList(hql.toString());
	}

	/**
	 * 通过属性查询应用菜单
	 * @author huqitao
	 * @param queryParamJSON 对象属性的JSON格式
	 * @return List<BaseDeployeeAppMenuEntity_HI>
	 */
	@Override
	public List<BaseDeployeeAppMenuEntity_HI> findByProperty(JSONObject queryParamJSON){
		return baseAppDeployeeMenuDAO_HI.findByProperty(queryParamJSON);
	}

	/**
	 * 查询应用菜单（分页）
	 *
	 * @param queryParamJSON 对象属性的JSON格式
	 * {
	 * appCode 应用编码
	 * appWapId 应用Id
	 * appMenuCode 菜单编码
	 * appMenuName 菜单名称
	 * }
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 * @return 应用菜单（分页）
	 */
	@Override
	public Pagination<BaseDeployeeAppMenuEntity_HI> findAppMenus(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows){
		StringBuffer hql = new StringBuffer(" from BaseDeployeeAppMenuEntity_HI menu where 1=1");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "menu.deleteFlag", "deleteFlag", hql,  queryParamMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "menu.appWapId", "appWapId", hql,  queryParamMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "menu.appMenuCode", "appMenuCode", hql,  queryParamMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "menu.appMenuName", "appMenuName", hql,  queryParamMap, "fulllike");
		String appCode = StringUtils.trimToNull(queryParamJSON.getString("appCode"));
		if(appCode != null) {
			hql.append(" and exists(select 1 from BaseDeployeeAppInfoEntity_HI app where app.appCode =:appCode and app.appWapId=menu.appWapId)");
			queryParamMap.put("appCode", appCode);
		}
		hql.append(" order by appMenuSort");
		return baseAppDeployeeMenuDAO_HI.findPagination(hql.toString(), queryParamMap, pageIndex, pageRows);
	}

	/**
	 * 新增、修改应用菜单信息
	 * @author laoqunzhao 2018-08-16
	 * @param paramsJSON
	 * {
	 * appWapId 应用ID
	 * dataTable
	 * [{
	 * appMenuId 菜单ID
	 * appMenuName app菜单名称
	 * appMenuCode app菜单编码
	 * appMenuImagePath app菜单图标
	 * appMenuUrl app菜单访问地址
	 * appMenuSort app菜单排序
	 * appDefaultDisplay 默认显示在用户常用收藏栏,1表示显示 0表示不显示 默认值为0
	 * appDefaultDisplayYN 默认显示在用户常用收藏栏,Y表示显示 N表示不显示 默认值为N
	 * }]
	 * }
	 * @param userId     当前用户ID
	 * @throws Exception 抛出异常
	 */
	@Override
	public void saveOrUpdate(JSONObject paramsJSON, Integer userId) throws Exception{
		SaafToolUtils.validateJsonParms(paramsJSON, "appWapId", "dataTable");
		BaseDeployeeAppInfoEntity_HI deployeeAppInfo = baseDeployeeAppInfoServer.getById(paramsJSON.getInteger("appWapId"));
		Assert.notNull(deployeeAppInfo, "应用版本不存在！");
		//查询应用下已有菜单
		List<BaseDeployeeAppMenuEntity_HI> menus = findByAppWapId(deployeeAppInfo.getAppWapId());
		//记录菜单ID
		List<Integer> menuIds = new ArrayList<Integer>();
		//保存菜单数据
		JSONArray dataTableJSON = paramsJSON.getJSONArray("dataTable");
		for(int i = 0; i < dataTableJSON.size(); i++){
			JSONObject instanceJSON = dataTableJSON.getJSONObject(i);
			instanceJSON.put("operatorUserId", userId);
			instanceJSON.put("appWapId", deployeeAppInfo.getAppWapId());
			SaafToolUtils.validateJsonParms(instanceJSON, "appMenuName", "appMenuCode");
			BaseDeployeeAppMenuEntity_HI instance = SaafToolUtils.setEntity(BaseDeployeeAppMenuEntity_HI.class, instanceJSON, baseAppDeployeeMenuDAO_HI, userId);
			instance.setAppWapId(deployeeAppInfo.getAppWapId());
			if(instance.getAppDefaultDisplay() == null) {
				instance.setAppDefaultDisplay(0);
			}
			instance.setDeleteFlag(0);
			instance.setOperatorUserId(userId);
			baseAppDeployeeMenuDAO_HI.saveOrUpdate(instance);
			menuIds.add(instance.getAppMenuId());
			LOGGER.info("saved BaseDeployeeAppMenu:{}", JSON.toJSON(instance));
		}
		//删除未传入的菜单
		if(menus != null && !menus.isEmpty()){
			for(BaseDeployeeAppMenuEntity_HI menu : menus){
				if(!menuIds.contains(menu.getAppMenuId())){
					menu.setDeleteFlag(1);
					menu.setOperatorUserId(userId);
					baseAppDeployeeMenuDAO_HI.saveOrUpdate(menu);
				}
			}
		}
	}
	
	/**
	 * 删除应用菜单信息
	 * @author laoqunzhao 2018-08-16
	 * @param paramsJSON 对象属性的JSON格式
	 * {
	 * appMenuIds:[] 主键数组
	 * }
	 * @param userId     当前用户ID
	 * @return 返回实体行
	 */
	@Override
	public void delete(JSONObject paramsJSON, Integer userId) {
		JSONArray appMenuIds = paramsJSON.getJSONArray("appMenuIds");
	    if(appMenuIds == null || appMenuIds.isEmpty()) {
	    	return;
	    }
	    for(int i=0; i<appMenuIds.size(); i++){
		    int id = appMenuIds.getIntValue(i);
		    BaseDeployeeAppMenuEntity_HI instance = getById(id);
			if(instance != null) {
				instance.setDeleteFlag(1);
				instance.setOperatorUserId(userId);
				baseAppDeployeeMenuDAO_HI.update(instance);
			}
		}
	    LOGGER.info("delete BaseDeployeeAppMenu: {}", paramsJSON);
	}
	
	
	/**
	 * 查询用户最近使用的菜单
	 * @author laoqunzhao 2018-08-18
	 * @param ouId  OUID
	 * @param userId  用户ID
	 * @param appCode APP编码
	 * @return List<BaseDeployeeAppMenuEntity_HI>
	 */
	@Override
	public List<BaseDeployeeAppMenuEntity_HI> getMyLatestMenus(int ouId, int userId, String appCode){
		String mylMenuCodeKey = DeployConstant.REDIS_KEY_APP_MENU_MYL + ouId + "::" + userId + "::" + appCode;
		String appMenuCodesStr = jedisCluster.hget(DeployConstant.REDIS_KEY_APP_MENU_MYL_KEY, mylMenuCodeKey);
		List<BaseDeployeeAppMenuEntity_HI> latestAppMenus= new ArrayList<BaseDeployeeAppMenuEntity_HI>();
		if(StringUtils.isNotBlank(appMenuCodesStr)) {
			//Redis中存储的最近使用菜单信息[menuCode1, menuCode2]
			JSONArray appMenuCodesJSON = (JSONArray) JSON.parse(appMenuCodesStr);
			//获取用户的所有菜单
			Map<String, BaseDeployeeAppMenuEntity_HI> appMenus = getUserAppMenus(ouId, userId, appCode);
			List<String> invalidCodes = new ArrayList<String>();//记录失效的菜单，从最近使用菜单中移除
			for(int i=0; i<appMenuCodesJSON.size(); i++) {
				String appMenuCode = appMenuCodesJSON.getString(i);
				if(!appMenus.containsKey(appMenuCode) || appMenus.get(appMenuCode) == null) {
					invalidCodes.add(appMenuCode);
					continue;
				}
				latestAppMenus.add(appMenus.get(appMenuCode));
			}
			//移除无效的最近使用菜单
			if(invalidCodes != null && !invalidCodes.isEmpty()) {
				for(String invalidCode : invalidCodes) {
					appMenuCodesJSON.remove(invalidCode);
				}
				jedisCluster.hset(DeployConstant.REDIS_KEY_APP_MENU_MYL_KEY, mylMenuCodeKey, appMenuCodesJSON.toString());
			}
		}
		return latestAppMenus;
	}

	/**
	 * 添加最近使用的菜单Code,以JSONArray的形式存储到Redis中
	 * @author laoqunzhao 2018-08-18
	 * @param ouId ouID
	 * @param userId 用户ID
	 * @param appCode APP编码
	 * @param appMenuCode 菜单Code
	 */
	@Override
	public void addMyLatestMenu(int ouId, int userId, String appCode, String appMenuCode){
		String mylMenuCodeKey = DeployConstant.REDIS_KEY_APP_MENU_MYL  + ouId + "::" + userId + "::" + appCode;
		String appMenuCodesStr = jedisCluster.hget(DeployConstant.REDIS_KEY_APP_MENU_MYL_KEY, mylMenuCodeKey);
		JSONArray appMenuCodesJSON = new JSONArray();
		if(StringUtils.isNotBlank(appMenuCodesStr)) {
			appMenuCodesJSON = (JSONArray) JSON.parse(appMenuCodesStr);
			appMenuCodesJSON.add(0, appMenuCode);
			//移除重复值
			for(int i=appMenuCodesJSON.size()-1; i>0; i--) {
				if(StringUtils.equals(appMenuCodesJSON.getString(i), appMenuCode)) {
					appMenuCodesJSON.remove(i);
				}
			}
		}else {
			appMenuCodesJSON.add(appMenuCode);
		}
		jedisCluster.hset(DeployConstant.REDIS_KEY_APP_MENU_MYL_KEY, mylMenuCodeKey, appMenuCodesJSON.toString());
	}

	
	/**
	 * 查询我的收藏菜单
	 * @author laoqunzhao 2018-08-18
	 * @param ouId  OUID
	 * @param userId  用户ID
	 * @param appCode APP编码
	 * @return List<BaseDeployeeAppMenuEntity_HI>
	 */
	@Override
	public List<BaseDeployeeAppMenuEntity_HI> getMyFavoriteMenus(int ouId, int userId, String appCode){
		
		String myfMenuCodeKey = DeployConstant.REDIS_KEY_APP_MENU_MYF + ouId + "::" + userId + "::" + appCode;
		String appMenuCodesStr = jedisCluster.hget(DeployConstant.REDIS_KEY_APP_MENU_MYF_KEY, myfMenuCodeKey);
		List<BaseDeployeeAppMenuEntity_HI> favoriteAppMenus= new ArrayList<BaseDeployeeAppMenuEntity_HI>();
		if(StringUtils.isNotBlank(appMenuCodesStr)) {
			//Redis中存储的菜单收藏信息[menuCode1, menuCode2]
			JSONArray appMenuCodesJSON = (JSONArray) JSON.parse(appMenuCodesStr);
			//获取用户的所有菜单
			Map<String, BaseDeployeeAppMenuEntity_HI> appMenus = getUserAppMenus(ouId, userId, appCode);
			List<String> invalidCodes = new ArrayList<String>();//记录失效的菜单，从菜单收藏中移除
			for(int i=0; i<appMenuCodesJSON.size(); i++) {
				String appMenuCode = appMenuCodesJSON.getString(i);
				if(!appMenus.containsKey(appMenuCode) || appMenus.get(appMenuCode) == null) {
					invalidCodes.add(appMenuCode);
					continue;
				}
				favoriteAppMenus.add(appMenus.get(appMenuCode));
			}
			//移除无效的最近使用菜单
			if(invalidCodes != null && !invalidCodes.isEmpty()) {
				for(String invalidCode : invalidCodes) {
					appMenuCodesJSON.remove(invalidCode);
				}
				jedisCluster.hset(DeployConstant.REDIS_KEY_APP_MENU_MYF_KEY, myfMenuCodeKey, appMenuCodesJSON.toString());
			}
		}else {
			//第一次查询返回默认收藏的菜单
			favoriteAppMenus = getDefaultMyFavoriteMenus(ouId, userId, appCode);
			JSONArray appMenuCodesJSON = new JSONArray();
			if(favoriteAppMenus != null && !favoriteAppMenus.isEmpty()) {
				for(BaseDeployeeAppMenuEntity_HI favoriteAppMenu : favoriteAppMenus) {
					appMenuCodesJSON.add(favoriteAppMenu.getAppMenuCode());
				}
			}
			//将默认收藏的菜单存到Redis
			jedisCluster.hset(DeployConstant.REDIS_KEY_APP_MENU_MYF_KEY, myfMenuCodeKey, appMenuCodesJSON.toString());
		}
		return favoriteAppMenus;
	}
	
	
	
	/**
	 * 将我的收藏菜单ID存在到redis中
	 * @author laoqunzhao 2018-08-17
	 * @param paramsJSON
	 * {
	 * ouId  OUID
	 * userId 用户ID
	 * appCode APP编码
	 * appMenuCodes 收藏的菜单CODE数组[]
	 * }
	 */
	@Override
	public void resetMyFavoriteMenus(JSONObject paramsJSON) {
		SaafToolUtils.validateJsonParms(paramsJSON, "ouId");
		SaafToolUtils.validateJsonParms(paramsJSON, "userId");
		SaafToolUtils.validateJsonParms(paramsJSON, "appCode");
		SaafToolUtils.validateJsonParms(paramsJSON, "appMenuCodes");
		int ouId = paramsJSON.getIntValue("ouId");
		int userId = paramsJSON.getIntValue("userId");
		String appCode = paramsJSON.getString("appCode");
		JSONArray appMenuCodesJSON = paramsJSON.getJSONArray("appMenuCodes");
		//Assert.isTrue(appMenuCodesJSON == null || appMenuCodesJSON.size() <= 12, "最多只允许收藏12个菜单！");
		List<String> menuCodes = new ArrayList<String>();
		List<Integer> invalidIndexs= new ArrayList<Integer>();//记录重复的菜单，从菜单收藏中移除
		for(int i=0; i<appMenuCodesJSON.size(); i++) {
			if(menuCodes.contains(appMenuCodesJSON.getString(i))) {
				invalidIndexs.add(i);
			}else {
				menuCodes.add(appMenuCodesJSON.getString(i));
			}
		}
		//移除重复的菜单
		if(!invalidIndexs.isEmpty()) {
			for(int i=invalidIndexs.size()-1; i>=0; i--) {
				appMenuCodesJSON.remove((int)invalidIndexs.get(i));
			}
		}
		String myfMenuCodeKey = DeployConstant.REDIS_KEY_APP_MENU_MYF + ouId + "::" + userId + "::" + appCode;
		jedisCluster.hset(DeployConstant.REDIS_KEY_APP_MENU_MYF_KEY, myfMenuCodeKey, appMenuCodesJSON.toString());
	}
	
	/**
	 * 获取默认收藏的菜单
	 * @author laoqunzhao 2018-08-18
	 * @param ouId  OUID
	 * @param userId  用户ID
	 * @param appCode APP编码
	 * @return List<BaseDeployeeAppMenuEntity_HI>
	 */
	private List<BaseDeployeeAppMenuEntity_HI> getDefaultMyFavoriteMenus(int ouId, int userId, String appCode) {
		List<BaseDeployeeAppMenuEntity_HI> favoriteAppMenus= new ArrayList<BaseDeployeeAppMenuEntity_HI>();
		List<BaseDeployeeAppInfoEntity_HI_RO> appInfos = baseDeployeeAppInfoServer.findDeployAppsByUserIdInRedis(ouId, userId, appCode);
		if(appInfos != null && !appInfos.isEmpty()) {
			for(BaseDeployeeAppInfoEntity_HI_RO appInfo : appInfos) {
				List<BaseDeployeeAppMenuEntity_HI> instances = findByAppWapId(appInfo.getAppWapId());
				if(instances != null && !instances.isEmpty()) {
					for(BaseDeployeeAppMenuEntity_HI instance : instances) {
						if(instance.getAppDefaultDisplay() != null && instance.getAppDefaultDisplay() == 1) {
							favoriteAppMenus.add(instance);
						}
					}
				}
			}
		}
		return favoriteAppMenus;
	}
	
//	/**
//	 * 判断应用菜单是否已经存在
//	 * @author laoqunzhao 2018-08-16
//	 * @param appWapId 应用ID
//	 * @param appMenuId 菜单ID
//	 * @param appMenuCode 菜单编码
//	 * @param appMenuName 菜单名称
//	 * @return true.已存在   false.不存在
//	 */
//	private boolean appMenuExisted(Integer appWapId, Integer appMenuId, String appMenuCode, String appMenuName) {
//		//根据菜单编码判断应用菜单是否已经存在
//		List<BaseDeployeeAppMenuEntity_HI> instances = findByCode(appWapId, appMenuCode);
//		if(instances != null && !instances.isEmpty()) {
//			for(BaseDeployeeAppMenuEntity_HI instance : instances) {
//				if(!instance.getAppMenuId().equals(appMenuId)) {
//					return true;
//				}
//			}
//		}
//		//根据菜单名称判断应用菜单是否已经存在
//		instances = findByName(appWapId, appMenuName);
//		if(instances != null && !instances.isEmpty()) {
//			for(BaseDeployeeAppMenuEntity_HI instance : instances) {
//				if(!instance.getAppMenuId().equals(appMenuId)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
	/**
	 * 根据菜单编码查询菜单信息
	 * @author laoqunzhao 2018-08-16
	 * @param  appWapId 应用ID
	 * @param appMenuCode 菜单编码
	 * @return List<BaseDeployeeAppInfoEntity_HI>
	 */
	private List<BaseDeployeeAppMenuEntity_HI> findByCode(Integer appWapId, String appMenuCode) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("appWapId", appWapId);
		properties.put("appMenuCode", appMenuCode);
		properties.put("deleteFlag", 0);
		return baseAppDeployeeMenuDAO_HI.findByProperty(properties);
	}
	
	/**
	 * 根据菜单名称查询菜单信息
	 * @author laoqunzhao 2018-08-16
	 * @param  appWapId 应用ID
	 * @param appMenuName 菜单名称
	 * @return List<BaseDeployeeAppInfoEntity_HI>
	 */
	private List<BaseDeployeeAppMenuEntity_HI> findByName(Integer appWapId, String appMenuName) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("appWapId", appWapId);
		properties.put("appMenuName", appMenuName);
		properties.put("deleteFlag", 0);
		return baseAppDeployeeMenuDAO_HI.findByProperty(properties);
	}
	
	/**
	 * 获取用户的所有菜单，以MAP的方式返回值，方便使用
	 * @param userId 用户ID
	 * @return Map<String, BaseDeployeeAppMenuEntity_HI>
	 */
	private Map<String, BaseDeployeeAppMenuEntity_HI> getUserAppMenus(int ouId, int userId, String appCode){
		Map<String, BaseDeployeeAppMenuEntity_HI> appMenus = new HashMap<String, BaseDeployeeAppMenuEntity_HI>();
		List<BaseDeployeeAppInfoEntity_HI_RO> appInfos = baseDeployeeAppInfoServer.findDeployAppsByUserIdInRedis(ouId, userId, appCode);
		if(appInfos != null && !appInfos.isEmpty()) {
			for(BaseDeployeeAppInfoEntity_HI_RO appInfo : appInfos) {
				if(appInfo.getMenuData() == null || appInfo.getMenuData().isEmpty()) {
					continue;
				}
				for(BaseDeployeeAppMenuEntity_HI menu : appInfo.getMenuData()) {
					appMenus.put(menu.getAppMenuCode(), menu);
				}
			}
		}
		return appMenus;
	}
}
