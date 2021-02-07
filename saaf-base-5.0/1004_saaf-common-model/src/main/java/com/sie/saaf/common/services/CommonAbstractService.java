package com.sie.saaf.common.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import redis.clients.jedis.JedisCluster;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.PositionBean;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * 通用的Service抽象类，所有的对外Service服务都可继承此类
 * 
 * @author ZhangJun
 * @creteTime 2017/12/19
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public abstract class CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CommonAbstractService.class);
	public static final String STATUS = "status";
	public static final String MSG = "msg";
	public static final String COUNT = "count";
	public static final String DATA = "data";
	public static final String PARAMS = "params";
	public static final String PAGE_INDEX = "pageIndex";
	public static final String PAGE_ROWS = "pageRows";
	public static final String ERROR_STATUS = "E";
	public static final String SUCCESS_STATUS = "S";
	public static final String ERROR_MSG_STATUS = "M";
	public static final String SUCCESS_MSG = "操作成功";
	public static final String ERROR_MSG = "操作失败";
	public static final Integer LOGIN_TTA_RESOURCE = 1;
	public static final Integer LOGIN_EMPLOYEE_TOTTA = 2;

	@Autowired
	private JedisCluster jedisCluster;

	@Autowired
	public HttpServletRequest request;

	@Autowired
	public HttpServletResponse response;

	@Autowired
	public IBaseAccreditCache baseAccreditCacheServer;

	@Autowired
	public ApplicationContext applicationContext;

	public CommonAbstractService() {
		super();
	}

	/**
	 * 获取Server接口，此方法为抽象方法，在继承类中设置返回值，返回值必须继承IBaseCommon接口
	 * 
	 * @return IBaseCommon类或其子类
	 * @author ZhangJun
	 * @creteTime 2017/12/19
	 */
	public abstract IBaseCommon<?> getBaseCommonServer();

	/**
	 * 从redis缓存中获取UserSessionBean对象
	 * 
	 * @return UserSessionBean对象
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	public UserSessionBean getUserSessionBean() {

		try {
			String certificate = request.getHeader("Certificate");
			if (StringUtils.isBlank(certificate)) {
				return null;
			}
			String key = "cookie_" + certificate;
			String result = jedisCluster.hget(key, "sessionInfo");
			if (StringUtils.isBlank(result))
				return null;
			UserSessionBean userSessionBean = JSON.parseObject(result,
					UserSessionBean.class);
			String expire = jedisCluster.hget(
					CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE,
					"appSessionExprire");
			if (StringUtils.isBlank(expire) && !StringUtils.isNumeric(expire)) {
				jedisCluster.hset(
						CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE,
						"appSessionExprire", "48");
				expire = "48";
			}
			jedisCluster
					.expire(key,
							userSessionBean.isFromApp() ? Integer
									.valueOf(expire) * 3600
									: CommonConstants.COOKIE_EXPIRED);
			return userSessionBean;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 请求参数处理 1.转换参数为Json对象 2.统一处理请求Token认证 3.增加公共参数
	 * 
	 * @param params
	 *            JSON格式字符串
	 * @return JSON参数格式，在params参数基础上增加如下内容：{ varUserId:登录用户Id,
	 *         varSystemCode:登录系统编码, varUserName:登录帐号,
	 *         varEmployeeNumber:登录人员编号/员工号, varUserFullName:登录用户姓名,
	 *         varOrgId:登录用户组织机构Id, varOrgCode:登录用户组织机构编码,
	 *         varLeaderId:登录用户部门领导Id, operatorUserId:操作者Id }
	 */
	public JSONObject parseObject(String params) {
		JSONObject jsonParams = null;
		if (null != params && !"".equals(params)) {
			jsonParams = JSON.parseObject(params);
			// 忽略用户名大小写，如果存在userName或username，则将值转换为大写
			if (jsonParams.containsKey("userName")) {
				jsonParams
						.put("userName", StringUtils.upperCase(jsonParams
								.getString("userName")));
			}
			if (jsonParams.containsKey("username")) {
				jsonParams
						.put("username", StringUtils.upperCase(jsonParams
								.getString("username")));
			}
			// 由于PDA中没有登录Session，所以PDA的请求都将带有userId，如果请求中存在userId，则存储varUserId
			if (jsonParams.containsKey("userId")) {
				jsonParams.put("varUserId", jsonParams.getString("userId"));
			}
		} else {
			jsonParams = new JSONObject();
		}
		UserSessionBean sessionBean = getUserSessionBean();
		if (sessionBean != null) {
			jsonParams.put("varUserId", sessionBean.getUserId());
			jsonParams.put("varSystemCode", sessionBean.getSystemCode());
			jsonParams.put("varUserName", sessionBean.getUserName());
			jsonParams
					.put("varEmployeeNumber", sessionBean.getEmployeeNumber());
			jsonParams.put("varUserFullName", sessionBean.getUserFullName());
			jsonParams.put("varOrgId", sessionBean.getOrgId());
			jsonParams.put("varOrgCode", sessionBean.getOrgCode());
			jsonParams.put("varLeaderId", sessionBean.getLeaderId());
			jsonParams.put("varIsadmin", sessionBean.getIsadmin());
			jsonParams.put("varUserType", sessionBean.getUserType());
			if (request.getAttribute("currentOprRespId") != null) {
				// 为了兼容以前的代码逻辑
				jsonParams.put("operationOrgIds", baseAccreditCacheServer
						.getOrgId(sessionBean.getUserId()));
				jsonParams.put("operationOrgId", getOrgId());
			}
			jsonParams.put("operationRespId", sessionBean.getOperationRespId());
			jsonParams.put("operatorUserId", sessionBean.getUserId());
		}

		return jsonParams;
	}

	/**
	 * 获取客户端IP
	 * 
	 * @return ip地址
	 */
	public String getIpAddr() {
		LOGGER.info("获取ip:http_x_forwarded_for:{},x-forwarded-for:{}",
				request.getHeader("HTTP_X_FORWARDED_FOR"),
				request.getHeader("x-forwarded-for"));
		String ip = request.getHeader("http_x_forwarded_for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 取得HttpServletRequest
	 * 
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * 取得HttpServletResponse
	 * 
	 * @return HttpServletResponse
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * 获取登录用户的userId
	 * 
	 * @return 登录用户Id
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	public Integer getSessionUserId() {
		UserSessionBean userSessionBean = getUserSessionBean();
		if (userSessionBean == null || userSessionBean.getUserId() == null)
			return -1;
		return getUserSessionBean().getUserId();
	}

	/**
	 * 获取登录用户的帐号userName
	 * 
	 * @return 登录用户帐号
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	public String geSessiontUserName() {
		return getUserSessionBean() != null ? getUserSessionBean()
				.getUserName() : "";
	}

	/**
	 * 获取系统编码systemCode
	 * 
	 * @return 系统编码
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	public String getSystemCode() {
		return getUserSessionBean() != null ? getUserSessionBean()
				.getSystemCode() : "";
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param params
	 *            JSON参数，对象属性的JSON格式
	 * @return { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数 data:成功的数据 }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	public String saveOrUpdate(String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);

			Object entity = getBaseCommonServer().saveOrUpdate(queryParamJSON);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					new JSONArray().fluentAdd(entity)).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查找数据
	 * 
	 * @param params
	 *            JSON参数，查询条件的JSON格式
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数 data:成功的数据 }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	public String findPagination(String params, Integer pageIndex,
			Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);

			Pagination findList = getBaseCommonServer().findPagination(
					queryParamJSON, pageIndex, pageRows);

			JSONObject results = JSONObject.parseObject(JSON
					.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param params
	 *            参数id { id:需要删除的数据Id，如果需要删除多个，则用;分隔 }
	 * @return 返回 { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数
	 *         data:成功的数据 }
	 * @author ZhangJun
	 * @creteTime 2017/12/15
	 */
	public String delete(String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			if (queryParamJSON == null || !queryParamJSON.containsKey("id")) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS,
						"缺少参数 id ", 0, null).toString();
			}

			List<Serializable> ids = new ArrayList<Serializable>();
			String[] idArr = queryParamJSON.getString("id").split(";");
			for (String s : idArr) {
				ids.add(s);
			}

			getBaseCommonServer().deleteAll(ids);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
					ids.size(), ids).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 根据Id主键查询对象
	 * 
	 * @param params
	 *            查询参数<br>
	 *            {<br>
	 *            id:ID主键<br>
	 *            }
	 * @return { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数 data:成功的数据 }
	 * @author ZhangJun
	 * @createTime 2018/1/10 14:13
	 * @description 根据Id主键查询对象
	 */
	public String findById(String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			if (queryParamJSON == null || !queryParamJSON.containsKey("id")) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS,
						"缺少参数 id ", 0, null).toString();
			}
			Serializable id = queryParamJSON
					.getObject("id", Serializable.class);
			Object entity = getBaseCommonServer().getById(id);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,
					new JSONArray().fluentAdd(entity)).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 获取列表
	 * 
	 * @param params
	 *            参数
	 * @return { status:操作是否成功,E:失败，S:成功 msg:成功或者失败后消息 count:成功的记录数 data:成功的数据 }
	 * @author ZhangJun
	 * @createTime 2018/1/31
	 * @description 获取列表
	 */
	public String findList(String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			List list = getBaseCommonServer().findList(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
					list.size(), list).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 到用户当前职责对应的OrgId
	 * 
	 * @return
	 */
	public Integer getOrgId() {
		int orgId = -1;
		int userId = getSessionUserId();
		if (userId != -1) {
			List<Integer> list = baseAccreditCacheServer.getOrgId(userId);
			if (list.size() > 0) {
				orgId = list.get(0);
			}
		}
		return orgId;
	}

	/**
	 * 到用户当前职责对应的UserType
	 * 
	 * @return
	 */
	public String getUserType() {
		String userType = "";
		int userId = getSessionUserId();
		if (userId != -1) {
			ProFileBean proFileBean = baseAccreditCacheServer
					.getUserType(userId);
			if (proFileBean != null) {
				userType = proFileBean.getProfileValue();
			}
		}
		return userType;
	}

	/**
	 * 到用户当前职责对应的ChannelType
	 * 
	 * @return
	 */
	public String getChannelType() {
		String channelType = "";
		int userId = getSessionUserId();
		if (userId != -1) {
			List<ProFileBean> list = baseAccreditCacheServer
					.getChannelType(userId);
			if (list.size() > 0) {
				ProFileBean proFileBean = list.get(0);
				if (proFileBean != null) {
					channelType = proFileBean.getProfileValue();
				}
			}
		}
		return channelType;
	}

	/**
	 * 获取当前登录人/申请人用户信息
	 * 
	 * @param paramJSON
	 * @throws IllegalArgumentException
	 */
	public void parseUserInfo(JSONObject paramJSON)
			throws IllegalArgumentException {
		try {

			UserSessionBean user = getUserSessionBean();
			int userId = user.getUserId();
			int orgId = getOrgId();

			String userType = getUserType();
			int personId = user.getPersonId() == null ? 0 : user.getPersonId();
			String personName = user.getUserFullName();
			/*
			 * ouId前端传来的orgId暂时只有app端会传
			 */
			String ouId = paramJSON.getString("ouId");
			if (StringUtils.isNotBlank(ouId)) {
				orgId = Integer.parseInt(ouId);
				// 暂时APP取不到userType默认给20
				if (StringUtils.isBlank(userType)) {
					userType = "20";
				}
			}
			paramJSON.put("orgId", orgId);
			paramJSON.put("userType", userType);
			paramJSON.put("reqUserId", userId);
			paramJSON.put("reqPersonId", personId);
			paramJSON.put("reqPersonName", personName);
			paramJSON.put("certificate", user.getCertificate());
			List<PositionBean> list = user.getPositionList();
			if (null != list && list.size() > 0) {
				paramJSON.put("positionList", list);
			}
			SaafToolUtils.validateJsonParms(paramJSON, "orgId", "userType",
					"reqUserId", "reqPersonId", "reqPersonName");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new IllegalArgumentException("获取用户信息失败:" + e.getMessage());
		}

	}

	public JSONObject parseJson(String params) {
		if (StringUtils.isBlank(params))
			return new JSONObject();
		try {
			return JSON.parseObject(params);
		} catch (Exception e) {
			throw new IllegalArgumentException("参数格式错误");
		}

	}

	/**
	 * 查询接口返回数据
	 * 
	 * @param status
	 * @param msg
	 * @param list
	 * @return
	 */
	public static String convertResultJSONObj(String status, String msg,
			Object list) {
		JSONObject json = list == null ? new JSONObject() : (JSONObject) JSON
				.toJSON(list);
		json.put(STATUS, status);
		json.put(MSG, msg);
		return json.toString();
	}

	public static String getException(Exception e, Logger logger) {
		if (e instanceof StaleObjectStateException) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "该单据已经被操作过！", 0, null)
					.toString();
		} else if (e instanceof HibernateOptimisticLockingFailureException) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "该单据已经被操作过！", 0, null)
					.toString();
		} else {
			logger.error(e.getMessage(), e);
			return SToolUtils
					.convertResultJSONObj("E", e.getMessage(), 0, null)
					.toString();
		}
	}

}
