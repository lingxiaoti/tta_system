package com.sie.saaf.deploy.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.deploy.constant.DeployConstant;
import com.sie.saaf.deploy.model.entities.BaseAppAuthContainEntity_HI;
import com.sie.saaf.deploy.model.entities.BaseAppAuthExcludeEntity_HI;
import com.sie.saaf.deploy.model.entities.BaseDeployeeAppInfoEntity_HI;
import com.sie.saaf.deploy.model.entities.BaseDeployeeAppMenuEntity_HI;
import com.sie.saaf.deploy.model.entities.readonly.BaseDeployeeAppAuthEntity_HI_RO;
import com.sie.saaf.deploy.model.entities.readonly.BaseDeployeeAppInfoEntity_HI_RO;
import com.sie.saaf.deploy.model.inter.IBaseAppAuthContain;
import com.sie.saaf.deploy.model.inter.IBaseAppAuthExclude;
import com.sie.saaf.deploy.model.inter.IBaseDeployeeAppInfo;
import com.sie.saaf.deploy.model.inter.IBaseDeployeeAppMenu;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import redis.clients.jedis.JedisCluster;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("baseAppDeployeeInfoServer")
public class BaseDeployeeInfoAppServer extends BaseCommonServer<BaseDeployeeAppInfoEntity_HI> implements IBaseDeployeeAppInfo {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDeployeeInfoAppServer.class);
	
	@Autowired
	private ViewObject<BaseDeployeeAppInfoEntity_HI> baseAppDeployeeInfoDAO_HI;
	
	@Autowired
	private BaseViewObject<BaseDeployeeAppInfoEntity_HI_RO> baseAppDeployeeInfoDAO_HI_RO;

	@Autowired
	private BaseViewObject<BaseDeployeeAppAuthEntity_HI_RO> baseDeployeeAppAuthDAO_HI_RO;
		
	@Autowired
	private IBaseDeployeeAppMenu baseDeployeeAppMenuServer;
	
	@Autowired
	private IBaseAppAuthContain baseAppAuthContainServer;
	
	@Autowired
	private IBaseAppAuthExclude baseAppAuthExcludeServer;

	@Autowired
	private IBaseAccreditCache baseAccreditCacheServerServer;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	public BaseDeployeeInfoAppServer() {
		super();
	}
	
	/**
	 * 根据ID查询应用信息
	 * @author laoqunzhao 2018-08-15
	 * @param id 主键
	 * @return BaseDeployeeAppInfoEntity_HI
	 */
	@Override
	public BaseDeployeeAppInfoEntity_HI getById(Integer id) {
		return baseAppDeployeeInfoDAO_HI.getById(id);
	}
	

	/**
	 * 通过属性查询应用信息
	 * @author huqitao
	 * @param queryParamJSON 对象属性的JSON格式
	 * @return 应用列表
	 */
	@Override
	public List<BaseDeployeeAppInfoEntity_HI> findByProperty(JSONObject queryParamJSON){
		return baseAppDeployeeInfoDAO_HI.findByProperty(queryParamJSON);
	}
	
	

	/**
	 * 查询应用列表（分页）
	 * @author laoqunzhao 2018-08-15
	 * @param queryParamJSON 对象属性的JSON格式
	 * {
	 * appCode APP编码
	 * appName APP名称
	 * appWapCode 应用编码
	 * appWapName 应用名称
	 * appWapDesc 应用描述
	 * appWapVersion 应用版本
	 * appWapStatus 1.上架  0.下架
	 * appWapDeployeeTimeStart 发布起始时间 yyyy-MM-dd
	 * appWapDeployeeTimeEnd 发布截止时间 yyyy-MM-dd
	 * }
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 * @return Pagination<BaseDeployeeAppInfoEntity_HI>
	 * @throws Exception 
	 */
	@Override
	public Pagination<BaseDeployeeAppInfoEntity_HI> findDeployApps(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
		StringBuffer hql = new StringBuffer(" from BaseDeployeeAppInfoEntity_HI app where 1=1");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "app.deleteFlag", "deleteFlag", hql,  queryParamMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "app.appCode", "appCode", hql,  queryParamMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "app.appName", "appName", hql, queryParamMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "app.appWapCode", "appWapCode", hql,  queryParamMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "app.appWapName", "appWapName", hql,  queryParamMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "app.appWapDesc", "appWapDesc",hql,  queryParamMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "app.appWapStatus", "appWapStatus", hql,  queryParamMap, "=");
		if(StringUtils.isNotBlank(queryParamJSON.getString("appWapVersion"))){
			hql.append(" and concat(app.appWapVersion,'') like:appWapVersion ");
			queryParamMap.put("appWapVersion", queryParamJSON.getString("appWapVersion") + "%");
		}
		if(StringUtils.isNotBlank(queryParamJSON.getString("appWapStatus"))){
			hql.append(" and app.appWapStatus=:appWapStatus");
			queryParamMap.put("appWapStatus", queryParamJSON.getString("appWapStatus"));
		}
		String appWapDeployeeTimeStart = StringUtils.trimToNull(queryParamJSON.getString("appWapDeployeeTimeStart"));
		if(appWapDeployeeTimeStart != null) {
			hql.append(" and app.appWapDeployeeTime >= :appWapDeployeeTimeStart");
			queryParamMap.put("appWapDeployeeTimeStart", new SimpleDateFormat("yyyy-MM-dd").parse(appWapDeployeeTimeStart));
		}
		String appWapDeployeeTimeEnd = StringUtils.trimToNull(queryParamJSON.getString("appWapDeployeeTimeEnd"));
		if(appWapDeployeeTimeEnd != null) {
			hql.append(" and app.appWapDeployeeTime < :appWapDeployeeTimeEnd");
            Calendar c = new GregorianCalendar();
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(appWapDeployeeTimeEnd));
            c.add(Calendar.DATE, 1);//日期向后移一天
            queryParamMap.put("appWapDeployeeTimeEnd", c.getTime());
		}
		hql.append(" order by app.appWapSortId asc, app.appWapVersion desc");
		return baseAppDeployeeInfoDAO_HI.findPagination(hql.toString(), queryParamMap, pageIndex, pageRows);
	}
	
	/**
	 * 新增、修改应用信息
	 * @author laoqunzhao 2018-08-15
	 * @param paramsJSON 对象属性的JSON格式
	 * {
	 * appWapId 主键Id
     * appName app底座名称
     * appWapCode 应用编码
     * appWapName 应用名称
     * appWapDesc 描述
     * appWapSortId 应用排序
     * appWapVersion 应用版本
     * appPackingVersion 打包版本
     * appWapAccessHomePath 应用首页访问地址
     * appWapFullScreen 是否全屏 Y.是  N.否
     * appWapBackupPath 应用备份地址
     * appWapImagePath 应用图标地址
     * appWapUploadPath 应用发布地址
     * appWapStatus 1.上架  0.下架
     * appWapDeployeeTime 发布时间
	 * }
	 * @param userId     当前用户ID
	 * @return BaseDeployeeAppInfoEntity_HI
	 * @throws Exception 
	 */
	@Override
	public BaseDeployeeAppInfoEntity_HI saveOrUpdate(JSONObject paramsJSON, Integer userId) throws Exception{
		BaseDeployeeAppInfoEntity_HI instance = SaafToolUtils.setEntity(BaseDeployeeAppInfoEntity_HI.class, paramsJSON, baseAppDeployeeInfoDAO_HI, userId);
		Assert.isTrue(paramsJSON.getString("appWapVersion") != null && paramsJSON.getString("appWapVersion").matches("[\\d]{1,3}(\\.[\\d]{1,2})?"), "应用版本格式不正确！");
		//Assert.isTrue(paramsJSON.getString("appPackingVersion") != null && paramsJSON.getString("appPackingVersion").matches("[\\d]{1,3}(\\.[\\d]{1,2})?"), "打包版本格式不正确！");
		Assert.isTrue(!appVersioinExisted(instance.getAppWapId(), instance.getAppWapCode(), 
				instance.getAppWapName(), instance.getAppWapVersion()), "应用版本已存在！");
		instance.setDeleteFlag(0);
		if(!"1".equals(instance.getAppWapStatus())) {
			instance.setAppWapStatus("0");
			instance.setAppWapDeployeeTime(null);
		}else{
			instance.setAppWapDeployeeTime(new Date());
		}
		baseAppDeployeeInfoDAO_HI.saveOrUpdate(instance);
		LOGGER.info("saved BaseDeployeeAppInfo:{}", JSON.toJSON(instance));
		return instance;
	}
	
	/**
	 * 更新应用状态：上架/下架
	 * @author laoqunzhao 2018-08-16
	 * @param paramsJSON 对象属性的JSON格式
	 * {
	 * appWapIds:[] 主键数组
	 * appWapStatus   1.上架  0.下架
	 * }
	 * @param userId     当前用户ID
	 * @return 返回实体行
	 */
	@Override
	public void updateStatus(JSONObject paramsJSON, Integer userId) {
		SaafToolUtils.validateJsonParms(paramsJSON, "appWapIds");
		SaafToolUtils.validateJsonParms(paramsJSON, "appWapStatus");
		Assert.isTrue("0".equals(paramsJSON.getString("appWapStatus"))
				|| "1".equals(paramsJSON.getString("appWapStatus")), "appWapStatus参数值错误！");
		JSONArray appWapIds = paramsJSON.getJSONArray("appWapIds");
	    for(int i=0; i<appWapIds.size(); i++){
		    int id = appWapIds.getIntValue(i);
		    BaseDeployeeAppInfoEntity_HI instance = getById(id);
			if(instance != null) {
				instance.setAppWapStatus(paramsJSON.getString("appWapStatus"));
				instance.setOperatorUserId(userId);
				if("1".equals(instance.getAppWapStatus())){
					instance.setAppWapDeployeeTime(new Date());
				}else{
					instance.setAppWapDeployeeTime(null);
				}
				baseAppDeployeeInfoDAO_HI.update(instance);
			}
		}
	    LOGGER.info("delete BaseDeployeeAppInfo: {}", paramsJSON);
	}
	
	/**
	 * 删除应用信息
	 * @author laoqunzhao 2018-08-16
	 * @param paramsJSON 对象属性的JSON格式
	 * {
	 * appWapIds:[] 主键数组
	 * }
	 * @param userId     当前用户ID
	 * @return 返回实体行
	 */
	@Override
	public void delete(JSONObject paramsJSON, Integer userId) {
		SaafToolUtils.validateJsonParms(paramsJSON, "appWapIds");
		JSONArray appWapIds = paramsJSON.getJSONArray("appWapIds");
	    for(int i=0; i<appWapIds.size(); i++){
		    int id = appWapIds.getIntValue(i);
		    BaseDeployeeAppInfoEntity_HI instance = getById(id);
			if(instance != null) {
				instance.setDeleteFlag(1);
				instance.setOperatorUserId(userId);
				baseAppDeployeeInfoDAO_HI.update(instance);
			}
		}
	    LOGGER.info("delete BaseDeployeeAppInfo: {}", paramsJSON);
	}
	
	
	/**
	 * 查询用户应用列表，同时查询应用的菜单列表
	 * @author laoqunzhao 2018-08-23
	 * @param queryParamJSON
	 * {
	 * ouId  OUID
	 * userId  用户ID
	 * appCode APP编码
	 * }
	 * @return List<BaseDeployeeAppInfoEntity_HI_RO>
	 */
	@Override
	public List<BaseDeployeeAppInfoEntity_HI_RO> findDeployAppsByUserId(JSONObject queryParamJSON){
		int ouId = queryParamJSON.getIntValue("ouId");
		int userId = queryParamJSON.getIntValue("userId");
		String appCode = queryParamJSON.getString("appCode");
		//有权限的应用
		List<BaseDeployeeAppInfoEntity_HI_RO> instances = findDeployAppsInAuthorization(queryParamJSON, false);
		//无权限的应用
		List<BaseDeployeeAppInfoEntity_HI_RO> outAuthApps = findDeployAppsInAuthorization(queryParamJSON, true);
		//移除无权限的应用
		if(instances != null && !instances.isEmpty() && outAuthApps != null && !outAuthApps.isEmpty()) {
			for(BaseDeployeeAppInfoEntity_HI_RO outAuthApp : outAuthApps) {
				for(int i=instances.size()-1; i>=0; i--) {
					if(outAuthApp.getAppWapId().equals(instances.get(i).getAppWapId())) {
						instances.remove(i);
						break;
					}
				}
			}
		}
		//移除低版本
		if(instances != null && !instances.isEmpty()) {
			Map<String, BigDecimal> appVersions = new HashMap<>();
			for(BaseDeployeeAppInfoEntity_HI_RO instance : instances){
				String appWapCode = instance.getAppWapCode();
				if(appVersions.containsKey(appWapCode)){
					BigDecimal version1 = appVersions.get(appWapCode);
					BigDecimal version2 = instance.getAppWapVersion();
					if(version2 != null && (version1 == null || version2.compareTo(version1)>0)){
						appVersions.put(appWapCode, version2);
					}
				}else{
					appVersions.put(appWapCode, instance.getAppWapVersion());
				}
			}
			for(int i=instances.size()-1; i>=0; i--) {
				BaseDeployeeAppInfoEntity_HI_RO instance = instances.get(i);
				String appWapCode = instance.getAppWapCode();
				if(!appVersions.containsKey(appWapCode)){
					instances.remove(i);
				}else{
					BigDecimal version1 = appVersions.get(appWapCode);
					BigDecimal version2 = instance.getAppWapVersion();
					if(version2 != null && version2.equals(version1)){
						appVersions.remove(appWapCode);
					}else{
						instances.remove(i);
					}
				}
			}
		}
		//添加应用菜单
		if(instances != null && !instances.isEmpty()) {
			//从职责菜单中查询用户有权限的菜单
			Set<String> menuCodes = baseAccreditCacheServerServer.findAppApply(userId, ouId);
			//去除无权限的菜单
			for(int i=instances.size()-1; i>=0; i--){
				BaseDeployeeAppInfoEntity_HI_RO instance = instances.get(i);
				if(menuCodes == null || !menuCodes.contains(instance.getAppWapCode())){
					instances.remove(i);
				}else{
					List<BaseDeployeeAppMenuEntity_HI> menus = baseDeployeeAppMenuServer.findByAppWapId(instance.getAppWapId());
					if(menus == null || menus.isEmpty()){
						instances.remove(i);
					}else{
						List<BaseDeployeeAppMenuEntity_HI> menuDatas = new ArrayList<>();
						for(BaseDeployeeAppMenuEntity_HI menu : menus){
							if(menuCodes != null && menuCodes.contains(menu.getAppMenuCode())){
								menuDatas.add(menu);
							}
						}
						if(menuDatas.isEmpty()){
							instances.remove(i);
						}else{
							instance.setMenuData(menuDatas);
						}
					}
				}
			}
		}
		//存Redis
		jedisCluster.hset(DeployConstant.REDIS_KEY_USR_APP_KEY, DeployConstant.REDIS_KEY_USR_APP + ouId + "::" + userId + "::" + appCode, JSON.toJSONString(instances));
		return instances;
	}
	
	/**
	 * 查询用户应用列表，同时查询应用的菜单列表,优先从Redis中查询
	 * @author laoqunzhao 2018-08-22
	 * @param ouId ou ID
	 * @param userId  用户ID
	 * @param appCode APP编码
	 * @return List<BaseDeployeeAppInfoEntity_HI>
	 */
	@Override
	public List<BaseDeployeeAppInfoEntity_HI_RO> findDeployAppsByUserIdInRedis(int ouId, int userId, String appCode){
		String appInfosStr = jedisCluster.hget(DeployConstant.REDIS_KEY_USR_APP_KEY, DeployConstant.REDIS_KEY_USR_APP + ouId + "::" + userId + "::" + appCode);
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("ouId", ouId);
		queryParamJSON.put("userId", userId);
		queryParamJSON.put("appCode", appCode);
		if(StringUtils.isNotBlank(appInfosStr)) {
			try {
				List<BaseDeployeeAppInfoEntity_HI_RO> instances = new ArrayList<BaseDeployeeAppInfoEntity_HI_RO>();
				JSONArray appInfosJSON = (JSONArray) JSON.parse(appInfosStr);
				for(Object appInfoJSON : appInfosJSON) {
					BaseDeployeeAppInfoEntity_HI_RO instance = JSON.toJavaObject((JSONObject)appInfoJSON, BaseDeployeeAppInfoEntity_HI_RO.class);
					instances.add(instance);
				}
				return instances;
			}catch(Exception e) {
				return findDeployAppsByUserId(queryParamJSON);
			}
		}else {
			return findDeployAppsByUserId(queryParamJSON);
		}
	}

	/**
	 * 查询应用权限对象列表（分页）
	 * @author laoqunzhao 2018-09-18
	 * @param queryParamJSON 对象属性的JSON格式
	 * {
	 * appWapId 应用ID
	 * ouId OU ID
	 * objectType 对象类型
     * type 1.包含  0.排除
	 * }
	 * @param pageIndex      页码
	 * @param pageRows       每页查询记录数
	 * @return Pagination<BaseDeployeeAppAuthEntity_HI_RO>
	 */
	@Override
	public Pagination<BaseDeployeeAppAuthEntity_HI_RO> findAuthEntities(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows){
		SaafToolUtils.validateJsonParms(queryParamJSON, "appWapId", "ouId", "objectType");
		StringBuffer sql = new StringBuffer();
		String baseQuery = " and exists(select 1 from " + ("0".equals(queryParamJSON.getString("type"))?"base_app_auth_exclude auth":"base_app_auth_contain auth")
		    + " where auth.app_wap_id=:appWapId and auth.ou_id=:ouId  and auth.object_type=:objectType";
        String objectType = queryParamJSON.getString("objectType");
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        queryParamMap.put("appWapId", queryParamJSON.getIntValue("appWapId"));
        queryParamMap.put("ouId", queryParamJSON.getIntValue("ouId"));
        queryParamMap.put("objectType", queryParamJSON.getString("objectType"));
		if(DeployConstant.AUTH_TYPE_DEALER.equals(objectType)){
            //经销商
            sql.append(BaseDeployeeAppAuthEntity_HI_RO.QUERY_CUSTOMER_SQL + BaseDeployeeAppAuthEntity_HI_RO.QUERY_CUSTOMER_IN_DEPT_SQL.replaceAll("__",""));
            sql.append(baseQuery);
            sql.append(" and auth.dealer=customer.customer_number and (auth.dep_code is null or auth.dep_code=department.department_code)) ");
            sql.append(" group by customer.customer_number,department.department_id");
        }else if(DeployConstant.AUTH_TYPE_STORE.equals(objectType)){
            //门店
            sql.append(BaseDeployeeAppAuthEntity_HI_RO.QUERY_STORE_SQL + BaseDeployeeAppAuthEntity_HI_RO.QUERY_STORE_IN_CUSTOMER_SQL.replaceAll("__",""));
            sql.append(baseQuery);
            sql.append(" and auth.store=invStoreT.store_code and (auth.dep_code is null or auth.dep_code=department.department_code))");
			sql.append(" group by invStoreT.store_code,department.department_id");
        }else{
            //员工
            sql.append(BaseDeployeeAppAuthEntity_HI_RO.QUERY_PERSON_SQL + BaseDeployeeAppAuthEntity_HI_RO.QUERY_PERSON_IN_DEPT_SQL.replaceAll("__",""));
            sql.append(baseQuery);
            sql.append(" and auth.emp_id=person.person_id and (auth.dep_code is null or auth.dep_code=department.department_code))");
			sql.append(" group by person.person_id,department.department_id");
        }
		return baseDeployeeAppAuthDAO_HI_RO.findPagination(sql.toString(), queryParamMap, pageIndex, pageRows);
	}

	/**
	 * 保存应用权限对象列表
	 * @author laoqunzhao 2018-09-13
	 * @param paramsJSON
	 * {
	 * appWapId  应用ID
	 * dataTable
	 * [{
	 * 	 ouId ouID
	 * 	 objectType 对象类型
	 * }]
	 * }
	 * @param userId 操作人
	 */
	@Override
	public void saveAuths(JSONObject paramsJSON, int userId) {
		SaafToolUtils.validateJsonParms(paramsJSON, "appWapId", "dataTable");
		int appWapId = paramsJSON.getIntValue("appWapId");
		BaseDeployeeAppInfoEntity_HI appInfo = getById(appWapId);
		Assert.notNull(appInfo, "应用版本不存在！");
		//所有包含对象
		List<BaseAppAuthContainEntity_HI> contains = baseAppAuthContainServer.findByAppWapId(appWapId);
		//所有排除对象
		List<BaseAppAuthExcludeEntity_HI> excludes = baseAppAuthExcludeServer.findByAppWapId(appWapId);
		List<String> newObjs = new ArrayList<String>();//新对象集合
		List<String> currentObjs = new ArrayList<String>();//当前对象集合
		if(contains != null && !contains.isEmpty()){
			for(BaseAppAuthContainEntity_HI contain : contains){
				String key = contain.getOuId() + ":" + contain.getObjectType();
				if(!currentObjs.contains(key)){
					currentObjs.add(key);
				}
			}
		}
		if(excludes != null && !excludes.isEmpty()){
			for(BaseAppAuthExcludeEntity_HI exclude : excludes){
				String key = exclude.getOuId() + ":" + exclude.getObjectType();
				if(!currentObjs.contains(key)){
					currentObjs.add(key);
				}
			}
		}
		JSONArray authsJSON = paramsJSON.getJSONArray("dataTable");
		for(int i=0; i<authsJSON.size(); i++){
			JSONObject authJSON = authsJSON.getJSONObject(i);
			if(StringUtils.isBlank(authJSON.getString("ouId")) || StringUtils.isBlank(authJSON.getString("objectType"))){
				continue;
			}
			String key = authJSON.getString("ouId") + ":" + authJSON.getString("objectType");
			newObjs.add(key);
			//新增
			if(!currentObjs.contains(key)){
				authJSON.put("appWapId", appWapId);
				authJSON.put("depCodes", "[\"-1\"]");//添加部门保存空记录到数据库
				baseAppAuthContainServer.save(authJSON, userId);
			}
		}
		//删除
		for(String key : currentObjs){
			if(!newObjs.contains(key)){
				int ouId = Integer.parseInt(key.substring(0, key.indexOf(":")));
				String objectType = key.substring(key.indexOf(":") + 1);
				deleteAuths(appWapId, ouId, objectType);
			}
		}
	}

	
	/**
	 * 根据应用ID、权限对象类型删除权限设置对象
	 * @author laoqunzhao 2018-08-27
	 * @param appWapId 应用ID
	 * @param objectType 对象类型   员工 / 经销商 / 门店
	 * @param ouId ouId
	 */
	@Override
	public void deleteAuths(int appWapId, int ouId, String objectType) {
		baseAppAuthContainServer.delete(appWapId, ouId, objectType);
		baseAppAuthExcludeServer.delete(appWapId, ouId, objectType);
	}

	/**
	 * 校验当前用户是否重新登录，刷新Redis缓存
	 * @param ouId
	 * @param userId
	 * @param appCode
	 * @param certificate
	 */
	@Override
	public void checkUserCertificate(Integer ouId, Integer userId, String appCode, String certificate){
		String userCertificateKey = DeployConstant.REDIS_KEY_APP_USR_CTF  + ouId + "::"+ userId + "::" + appCode;
		String certificateStr = jedisCluster.hget(DeployConstant.REDIS_KEY_APP_USR_CTF_KEY, userCertificateKey);
		//与上一次的certificate与当前登录的certificate比较，当certificate不一致表示已重新登录（不考虑多设备登录同一账号），重新查询全部应用权限
		if(!StringUtils.equals(certificate, certificateStr)){
			JSONObject queryParamJSON = new JSONObject();
			queryParamJSON.put("ouId", ouId);
			queryParamJSON.put("userId", userId);
			queryParamJSON.put("appCode", appCode);
			findDeployAppsByUserId(queryParamJSON);
			//重设Redis certificate
			jedisCluster.hset(DeployConstant.REDIS_KEY_APP_USR_CTF_KEY, userCertificateKey, certificate);
		}
	}


	
	private List<BaseDeployeeAppInfoEntity_HI_RO> findDeployAppsInAuthorization(JSONObject queryParamJSON, boolean exclude){
		int ouId = queryParamJSON.getIntValue("ouId");
		int userId = queryParamJSON.getIntValue("userId");
		StringBuffer sql = new StringBuffer(BaseDeployeeAppInfoEntity_HI_RO.QUERY_SQL_USR_APP);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		sql.append(" and app.app_wap_status=1 and app.delete_flag=0");
		sql.append(" and app.app_code=:appCode");
		paramsMap.put("appCode", queryParamJSON.getString("appCode"));
		//查询包含权限
		sql.append(" and exists(select 1 from " + (exclude?"base_app_auth_exclude":"base_app_auth_contain") + " autho");
		sql.append(" where autho.app_wap_id=app.app_wap_id and autho.delete_flag=0 and autho.ou_id=:ouId");
		paramsMap.put("ouId", ouId);
		//员工
		sql.append(" and (autho.object_type=:objectType1 and exists(select 1 " + BaseDeployeeAppAuthEntity_HI_RO.QUERY_PERSON_IN_DEPT_SQL.replaceAll("__", "1"));
		sql.append(" and department1.ou_id=autho.ou_id and users1.user_id=:userId1");
		sql.append(" and (department1.department_code=autho.dep_code");
		sql.append(" or autho.emp_id=person1.person_id))");
		paramsMap.put("userId1", userId);
		paramsMap.put("objectType1", DeployConstant.AUTH_TYPE_EMP);
		//经销商
		sql.append(" or autho.object_type=:objectType2 and exists(select 1 " + BaseDeployeeAppAuthEntity_HI_RO.QUERY_CUSTOMER_IN_DEPT_SQL.replaceAll("__", "2"));
		sql.append(" and department2.ou_id=autho.ou_id and users2.user_id=:userId2");
		sql.append(" and (department2.department_code=autho.dep_code");
		sql.append(" or customer2.customer_number=autho.dealer))");
		paramsMap.put("userId2", userId);
		paramsMap.put("objectType2", DeployConstant.AUTH_TYPE_DEALER);
		//门店
		sql.append(" or autho.object_type=:objectType3 and exists(select 1 " + BaseDeployeeAppAuthEntity_HI_RO.QUERY_STORE_IN_CUSTOMER_SQL.replaceAll("__", "3"));
		sql.append(" and department3.ou_id=autho.ou_id and users3.user_id=:userId3");
		sql.append(" and (department3.department_code=autho.dep_code");
		sql.append(" or invStoreT3.store_code=autho.store))");
		paramsMap.put("userId3", userId);
		paramsMap.put("objectType3", DeployConstant.AUTH_TYPE_STORE);
		sql.append("))");
		sql.append(" order by app.app_wap_sort_id asc, app.app_wap_version asc");
		
		return baseAppDeployeeInfoDAO_HI_RO.findList(sql.toString(), paramsMap);
	}
	

	/**
	 * 判断应用版本是否已经存在
	 * @author laoqunzhao 2018-08-15
	 * @param appWapId 应用ID
	 * @param appWapCode 应用编码
	 * @param appWapName 应用名称
	 * @param appWapVersion 应用版本
	 * @return true.已存在   false.不存在
	 */
	private boolean appVersioinExisted(Integer appWapId, String appWapCode, String appWapName, BigDecimal appWapVersion) {
		//根据应用编码和版本判断应用版本是否存在
		List<BaseDeployeeAppInfoEntity_HI> instances = findByCodeAndVersion(appWapCode, appWapVersion);
		if(instances != null && !instances.isEmpty()) {
			for(BaseDeployeeAppInfoEntity_HI instance : instances) {
				if(!instance.getAppWapId().equals(appWapId)) {
					return true;
				}
			}
		}
		//根据应用名称和版本判断应用版本是否存在
		instances = findByNameAndVersion(appWapName, appWapVersion);
		if(instances != null && !instances.isEmpty()) {
			for(BaseDeployeeAppInfoEntity_HI instance : instances) {
				if(!instance.getAppWapId().equals(appWapId)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 根据应用编码和版本查询应用版本信息
	 * @author laoqunzhao 2018-08-15
	 * @param appWapCode 应用编码
	 * @param appWapVersion 应用版本
	 * @return List<BaseDeployeeAppInfoEntity_HI>
	 */
	private List<BaseDeployeeAppInfoEntity_HI> findByCodeAndVersion(String appWapCode, BigDecimal appWapVersion) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("appWapCode", appWapCode);
		properties.put("appWapVersion", appWapVersion);
		properties.put("deleteFlag", 0);
		return baseAppDeployeeInfoDAO_HI.findByProperty(properties);
	}
	
	/**
	 * 根据应用名称和版本查询应用版本信息
	 * @author laoqunzhao 2018-08-15
	 * @param appWapName 应用名称
	 * @param appWapVersion 应用版本
	 * @return List<BaseDeployeeAppInfoEntity_HI>
	 */
	private List<BaseDeployeeAppInfoEntity_HI> findByNameAndVersion(String appWapName, BigDecimal appWapVersion) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("appWapName", appWapName);
		properties.put("appWapVersion", appWapVersion);
		properties.put("deleteFlag", 0);
		return baseAppDeployeeInfoDAO_HI.findByProperty(properties);
	}

	
}
