package com.sie.saaf.base.shiro.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.app.event.PermissionCacheSyncEvent;
import com.sie.saaf.base.shiro.model.entities.BaseMenuEntity_HI;
import com.sie.saaf.base.shiro.model.inter.IBaseMenu;
import com.sie.saaf.base.sso.model.entities.readonly.BaseFunctionCollectionEntity_HI_RO;
import com.sie.saaf.base.sso.model.inter.IBaseFunctionCollection;
import com.sie.saaf.base.user.model.entities.readonly.BaseMenuRoleEntity_HI_RO;
import com.sie.saaf.base.user.model.inter.server.BaseAccreditServer;
import com.sie.saaf.common.bean.OrgBean;
import com.sie.saaf.common.bean.ResponsibilityBean;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.yhg.access.log.model.entities.BaseUserAccessLogMiddleGroundEntity_MO;
import com.yhg.access.log.model.server.BaseUserAccessLogServer;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ZhangJun
 * @creteTime 2017-12-18
 */
@RestController
@RequestMapping("/baseMenuService")
public class BaseMenuService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseMenuService.class);
	@Autowired
	private IBaseMenu baseMenuServer;
	@Autowired
	private IBaseFunctionCollection baseFunctionCollectionServer;
	@Override
	public IBaseCommon getBaseCommonServer() {
		return baseMenuServer;
	}

//	@Autowired
//	private BaseUserAccessLogServer baseUserAccessLogServer;

	@Autowired
	private BaseAccreditServer baseAccreditServer;

	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 查找数据
	 *
	 * @param params JSON参数
	 * {
	 * menuParentId:上级菜单Id,
	 * menuCode:菜单编码,
	 * menuName:菜单名称,
	 * menuType:菜单类型：10-菜单节点；20-功能节点,
	 * systemCode:系统编码,
	 * fromType:访问来源,
	 * enableFlag:是否启用
	 * }
	 * @param pageIndex 页码
	 * @param pageRows 每页显示记录数
	 *
	 * @return {
	 * status:操作是否成功,E:失败，S:成功
	 * msg:成功或者失败后消息
	 * count:成功的记录数
	 * data:[{<br>
	 * menuId:菜单Id,<br>
	 * menuParentId:上级菜单Id,<br>
	 * menuCode:菜单编码,<br>
	 * orderNo:排序号,<br>
	 * menuName:菜单名称,<br>
	 * menuDesc:菜单提示信息,<br>
	 * levelId:层级ID,<br>
	 * menuType:菜单类型：10-菜单节点；20-功能节点,<br>
	 * parameter:参数,<br>
	 * systemCode:系统编码,<br>
	 * imageLink:图片样式,<br>
	 * imageColor:图标颜色,<br>
	 * htmlUrl:HTML路由链接,<br>
	 * fromType:访问来源,<br>
	 * enableFlag:是否启用,<br>
	 * startDateActive:启用时间,<br>
	 * endDateActive:失效时间,<br>
	 * creationDate:创建日期,<br>
	 * createdBy:创建人,<br>
	 * lastUpdatedBy:更新人,<br>
	 * lastUpdateDate:更新日期,<br>
	 * versionNum:版本号,<br>
	 * lastUpdateLogin:last_update_login,<br>
	 * }],
	 * firstIndex: 首页索引,<br>
	 * lastIndex: 尾页索引,<br>
	 * nextIndex: 下一页索引,<br>
	 * pageSize: 每页记录数,<br>
	 * pagesCount: 总页数,<br>
	 * preIndex: 上一页索引<br>
	 * }
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
								 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = super.parseObject(params);
		//解决menuType查询出错问题
		Pagination<BaseMenuEntity_HI> pagination = baseMenuServer.findPagination(queryParamJSON, pageIndex, pageRows);
		JSONObject result = (JSONObject) JSONObject.toJSON(pagination);
		result.put(STATUS, SUCCESS_STATUS);
		result.put(MSG, "成功");
		return result.toString();
	}

	/**
	 * 保存或更新数据
	 *
	 * @param params JSON参数
	 * {
	 * menuId:菜单Id（更新时必填）,
	 * menuParentId:上级菜单Id,
	 * menuCode:菜单编码,
	 * orderNo:排序号,
	 * menuName:菜单名称,
	 * menuDesc:菜单提示信息,
	 * levelId:层级ID,
	 * menuType:菜单类型：10-菜单节点；20-功能节点,
	 * parameter:参数,
	 * systemCode:系统编码,
	 * imageLink:图片样式,
	 * imageColor:图标颜色,
	 * htmlUrl:HTML路由链接,
	 * fromType:访问来源,
	 * enableFlag:是否启用,
	 * startDateActive:启用时间,
	 * endDateActive:失效时间,
	 * versionNum:版本号（更新时必填）,
	 * }
	 *
	 * @return
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		String result=super.saveOrUpdate(params);
		JSONObject queryParamJSON = JSON.parseObject(params);
		if (StringUtils.isNotBlank(queryParamJSON.getString("menuId"))){
			Set<String> menuIds=new HashSet<>();
			menuIds.add(queryParamJSON.getString("menuId"));
			//发布缓存跟新事件
			PermissionCacheSyncEvent event=new PermissionCacheSyncEvent("sync",menuIds,null,null,null);
			SpringBeanUtil.applicationContext.publishEvent(event);
		}
		return result;
	}

	/**
	 * 删除数据
	 *
	 * @param params 参数id
	 * {
	 * id:需要删除的数据Id，如果需要删除多个，则用;分隔
	 * }
	 *
	 * @return
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		JSONObject queryParamJSON = parseObject(params);
		if(queryParamJSON==null || !queryParamJSON.containsKey("id")){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数 id ", 0, null).toString();
		}
		Set<String> menuIds=new HashSet<>();
		String[] idArr = queryParamJSON.getString("id").split(";");
		for (String id:idArr){
			if (StringUtils.isNotBlank(id)){
				menuIds.add(id);
			}
		}
		Set<Integer> userIds= baseAccreditServer.findRelatedUserId(menuIds,null,null,null);
		PermissionCacheSyncEvent event=new PermissionCacheSyncEvent("update",userIds);
		String result=super.delete(params);
		SpringBeanUtil.applicationContext.publishEvent(event);
		return result;
	}

	/**
	 * 查询菜单列表
	 *
	 * @param params JSON参数<br>
	 * {<br>
	 * menuParentId:上级菜单Id<br>
	 * menuCode:菜单Code<br>
	 * menuName:菜单名称<br>
	 * menuType:菜单类型，10-菜单节点；20-功能节点<br>
	 * systemCode:系统编码<br>
	 * fromType:访问来源<br>
	 * isValid:是否只查有效，true-查询有效；false-忽略有效条件<br>
	 * enabled:是否启用,isValid为false时生效<br>
	 * startDateActive:启用日期,isValid为false时生效<br>
	 * endDateActive:失效日期,isValid为false时生效<br>
	 * }
	 *
	 * @return 菜单对象列表 <br>
	 * [{<br>
	 * menuId:菜单Id,<br>
	 * menuParentId:上级菜单Id,<br>
	 * menuCode:菜单编码,<br>
	 * orderNo:排序号,<br>
	 * menuName:菜单名称,<br>
	 * menuDesc:菜单提示信息,<br>
	 * levelId:层级ID,<br>
	 * menuType:菜单类型：10-菜单节点；20-功能节点,<br>
	 * parameter:参数,<br>
	 * systemCode:系统编码,<br>
	 * imageLink:图片样式,<br>
	 * imageColor:图标颜色,<br>
	 * htmlUrl:HTML路由链接,<br>
	 * fromType:访问来源,<br>
	 * enabled:是否启用,<br>
	 * startDateActive:启用时间,<br>
	 * endDateActive:失效时间,<br>
	 * creationDate:创建日期,<br>
	 * createdBy:创建人,<br>
	 * lastUpdatedBy:更新人,<br>
	 * lastUpdateDate:更新日期,<br>
	 * versionNum:版本号,<br>
	 * lastUpdateLogin:last_update_login,<br>
	 * }]
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBaseMenuList")
	public String findBaseMenuList(@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON, "systemCode");
			List findList = this.baseMenuServer.findBaseMenuList(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 查询下层菜单
	 *
	 * @param params {menuParentId:父级菜单Id}
	 *
	 * @return 下层菜单列表<br>
	 * [{<br>
	 * menuId:菜单Id,<br>
	 * menuParentId:上级菜单Id,<br>
	 * menuCode:菜单编码,<br>
	 * orderNo:排序号,<br>
	 * menuName:菜单名称,<br>
	 * menuDesc:菜单提示信息,<br>
	 * levelId:层级ID,<br>
	 * menuType:菜单类型：10-菜单节点；20-功能节点,<br>
	 * parameter:参数,<br>
	 * systemCode:系统编码,<br>
	 * imageLink:图片样式,<br>
	 * imageColor:图标颜色,<br>
	 * htmlUrl:HTML路由链接,<br>
	 * fromType:访问来源,<br>
	 * enabled:是否启用,<br>
	 * startDateActive:启用时间,<br>
	 * endDateActive:失效时间,<br>
	 * creationDate:创建日期,<br>
	 * createdBy:创建人,<br>
	 * lastUpdatedBy:更新人,<br>
	 * lastUpdateDate:更新日期,<br>
	 * versionNum:版本号,<br>
	 * lastUpdateLogin:last_update_login,<br>
	 * }]
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findChildrenMenus")
	public String findChildrenMenus(@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			if(!queryParamJSON.containsKey("menuParentId")){
				return SToolUtils.convertResultJSONObj(ERROR_STATUS,"缺少参数 menuParentId",0,null).toJSONString();
			}
			Integer menuParentId = queryParamJSON.getIntValue("menuParentId");
			List findList = this.baseMenuServer.findChildrenMenus(menuParentId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 根据角色Id查询菜单
	 *
	 * @param params 角色Id
	 * {<br>
	 * menuParentId:上级菜单Id<br>
	 * menuCode:菜单Code<br>
	 * menuName:菜单名称<br>
	 * menuType:菜单类型，10-菜单节点；20-功能节点<br>
	 * systemCode:系统编码<br>
	 * fromType:访问来源<br>
	 * enabled:是否启用<br>
	 * startDateActive:启用日期<br>
	 * endDateActive:失效日期<br>
	 * roleId:角色Id<br>
	 * }
	 * @return 菜单与角色关联列表
	 * [{<br>
	 * menuId:菜单Id,<br>
	 * menuParentId:上级菜单Id,<br>
	 * menuCode:菜单编码,<br>
	 * orderNo:排序号,<br>
	 * menuName:菜单名称,<br>
	 * menuDesc:菜单提示信息,<br>
	 * levelId:层级ID,<br>
	 * menuType:菜单类型：10-菜单节点；20-功能节点,<br>
	 * parameter:参数,<br>
	 * systemCode:系统编码,<br>
	 * imageLink:图片样式,<br>
	 * imageColor:图标颜色,<br>
	 * htmlUrl:HTML路由链接,<br>
	 * fromType:访问来源,<br>
	 * enabled:是否启用,<br>
	 * startDateActive:启用时间,<br>
	 * endDateActive:失效时间,<br>
	 * creationDate:创建日期,<br>
	 * createdBy:创建人,<br>
	 * lastUpdatedBy:更新人,<br>
	 * lastUpdateDate:更新日期,<br>
	 * versionNum:版本号,<br>
	 * roleId:角色Id,<br>
	 * roleName:角色名称,<br>
	 * roleCode:角色编号,<br>
	 * roleDesc:角色描述,<br>
	 * roleStartDateActive:角色生效时间,<br>
	 * roleEndDateActive:角色失效时间,<br>
	 * roleVersionNum:角色版本号,<br>
	 * }]
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBaseMenuByRoleId")
	public String findBaseMenuByRoleId(@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			if(!queryParamJSON.containsKey("roleId")){
				return SToolUtils.convertResultJSONObj(ERROR_STATUS,"缺少参数 roleId",0,null).toJSONString();
			}
			List findList = this.baseMenuServer.findBaseMenuByRoleId( queryParamJSON.getString("roleId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 根据职责Id获取菜单
	 * @param params 职责Id
	 * {<br>
	 * menuParentId:上级菜单Id<br>
	 * menuCode:菜单Code<br>
	 * menuName:菜单名称<br>
	 * menuType:菜单类型，10-菜单节点；20-功能节点<br>
	 * systemCode:系统编码<br>
	 * fromType:访问来源<br>
	 * enabled:是否启用<br>
	 * startDateActive:启用日期<br>
	 * endDateActive:失效日期<br>
	 * responsibilityId:职责Id<br>
	 * }
	 * @return 菜单与角色关联列表
	 * [{<br>
	 * menuId:菜单Id,<br>
	 * menuParentId:上级菜单Id,<br>
	 * menuCode:菜单编码,<br>
	 * orderNo:排序号,<br>
	 * menuName:菜单名称,<br>
	 * menuDesc:菜单提示信息,<br>
	 * levelId:层级ID,<br>
	 * menuType:菜单类型：10-菜单节点；20-功能节点,<br>
	 * parameter:参数,<br>
	 * systemCode:系统编码,<br>
	 * imageLink:图片样式,<br>
	 * imageColor:图标颜色,<br>
	 * htmlUrl:HTML路由链接,<br>
	 * fromType:访问来源,<br>
	 * enabled:是否启用,<br>
	 * startDateActive:启用时间,<br>
	 * endDateActive:失效时间,<br>
	 * creationDate:创建日期,<br>
	 * createdBy:创建人,<br>
	 * lastUpdatedBy:更新人,<br>
	 * lastUpdateDate:更新日期,<br>
	 * versionNum:版本号,<br>
	 * roleId:角色Id,<br>
	 * roleName:角色名称,<br>
	 * roleCode:角色编号,<br>
	 * roleDesc:角色描述,<br>
	 * roleStartDateActive:角色生效时间,<br>
	 * roleEndDateActive:角色失效时间,<br>
	 * roleVersionNum:角色版本号,<br>
	 * }]
	 * @author ZhangJun
	 * @createTime 2018/1/26 09:41
	 * @description 根据职责Id获取菜单
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBaseMenuByRespId")
	public String findBaseMenuByRespId(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			if(!queryParamJSON.containsKey("responsibilityId") && !StringUtils.equals("Y",queryParamJSON.getString("varIsadmin"))){
				return SToolUtils.convertResultJSONObj(ERROR_STATUS,"缺少参数 responsibilityId",0,null).toJSONString();
			}
			UserSessionBean userSessionBean = getUserSessionBean();
			if(userSessionBean != null){
				userSessionBean.setOperationRespId(queryParamJSON.getInteger("responsibilityId"));
				if(!StringUtils.equals(userSessionBean.getIsadmin(),"Y")) {
					List<ResponsibilityBean> responsibilityBeans = userSessionBean.getUserRespList();
					if (responsibilityBeans != null && !responsibilityBeans.isEmpty()) {
						List<Integer> operationOrgIds = userSessionBean.getOperationOrgIds();
						operationOrgIds.clear();
						for (ResponsibilityBean responsibilityBean : responsibilityBeans) {
							if (queryParamJSON.getIntValue("responsibilityId") == responsibilityBean.getResponsibilityId().intValue()) {
								OrgBean orgBeans = responsibilityBean.getOrgBean();
								if (orgBeans != null) {
									operationOrgIds.add(orgBeans.getOrgId());
								} else {
									userSessionBean.setOperationOrgIds(null);
								}
							}
						}
					}
				}
				jedisCluster.hset("cookie_" + userSessionBean.getCertificate(), "sessionInfo",JSON.toJSONString(userSessionBean));
				if (!userSessionBean.isFromApp())
					jedisCluster.expire("cookie_" + userSessionBean.getCertificate(), CommonConstants.COOKIE_EXPIRED);
			}

			List findList = this.baseMenuServer.findBaseMenuByRespId(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}
	/**
	* @param params JSON参数，对象属性的JSON格式
     *  {
     *      respId; //职责id
	 *      systemCode//系统编码
     *  }
     * @return
	 *@author yuzhenli
     *@description 查询内部可收藏功能菜单
	 */
	@RequestMapping(method = RequestMethod.POST,value="findSelectMenu")
	public String findSelectMenu(@RequestParam(required=false) String params,
								 @RequestParam(required = false, defaultValue = "-1") Integer pageIndex,
								 @RequestParam(required = false, defaultValue = "-1") Integer pageRows){
		try{
			JSONObject queryParamJSON = parseObject(params);
			if(!queryParamJSON.containsKey("respId")){
				return SToolUtils.convertResultJSONObj(ERROR_STATUS,"缺少参数 respId",0,queryParamJSON).toJSONString();
			}
			if(!queryParamJSON.containsKey("systemCode")){
				return SToolUtils.convertResultJSONObj(ERROR_STATUS,"缺少参数 systemCode",0,queryParamJSON).toJSONString();
			}
			//获取到已收藏的菜单id
			List<BaseFunctionCollectionEntity_HI_RO> result = baseFunctionCollectionServer.findInCollection(queryParamJSON.fluentPut("userId",getSessionUserId()));
			Set<Integer> menuIds = new HashSet<>();
			for (BaseFunctionCollectionEntity_HI_RO entity : result) {
				menuIds.add(entity.getMenuId());
			}
			queryParamJSON.put("menuIds", StringUtils.join(menuIds, ","));
			JSONObject jsonObject = new JSONObject();
			Pagination findList = this.baseMenuServer.findSelectMenu(queryParamJSON, pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(findList);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	/**
	 * 关联菜单与角色查询
	 *
	 * @param params JSON参数<br>
	 * {<br>
	 * menuParentId:上级菜单Id<br>
	 * menuCode:菜单Code<br>
	 * menuName:菜单名称<br>
	 * menuType:菜单类型，10-菜单节点；20-功能节点<br>
	 * systemCode:系统编码<br>
	 * fromType:访问来源<br>
	 * enabled:是否启用<br>
	 * startDateActive:启用日期<br>
	 * endDateActive:失效日期<br>
	 * roleId:角色Id
	 * }
	 *
	 * @return 菜单与角色关联列表<br>
	 * [{<br>
	 * menuId:菜单Id,<br>
	 * menuParentId:上级菜单Id,<br>
	 * menuCode:菜单编码,<br>
	 * orderNo:排序号,<br>
	 * menuName:菜单名称,<br>
	 * menuDesc:菜单提示信息,<br>
	 * levelId:层级ID,<br>
	 * menuType:菜单类型：10-菜单节点；20-功能节点,<br>
	 * parameter:参数,<br>
	 * systemCode:系统编码,<br>
	 * imageLink:图片样式,<br>
	 * imageColor:图标颜色,<br>
	 * htmlUrl:HTML路由链接,<br>
	 * fromType:访问来源,<br>
	 * enabled:是否启用,<br>
	 * startDateActive:启用时间,<br>
	 * endDateActive:失效时间,<br>
	 * creationDate:创建日期,<br>
	 * createdBy:创建人,<br>
	 * lastUpdatedBy:更新人,<br>
	 * lastUpdateDate:更新日期,<br>
	 * versionNum:版本号,<br>
	 * roleId:角色Id,<br>
	 * roleName:角色名称,<br>
	 * roleCode:角色编号,<br>
	 * roleDesc:角色描述,<br>
	 * roleStartDateActive:角色生效时间,<br>
	 * roleEndDateActive:角色失效时间,<br>
	 * roleVersionNum:角色版本号,<br>
	 * }]
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBaseMenuJoinRole")
	public String findBaseMenuJoinRole(@RequestParam(required = false) String params) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			List findList = this.baseMenuServer.findBaseMenuJoinRole(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 查询菜单及资源，构建成树
	 * @param params 参数<br>
	 * {<br>
	 *     roleId:角色Id,<br>
	 *     systemCode:系统编码<br>
	 * }
	 * @return 返回结果
	 * {<br>
	 * msg:返回信息,<br>
	 * status:返回状态,<br>
	 * count:返回总记录数,<br>
	 * data:[{<br>
	 *    menuId:Id,<br>
	 *    menuParentId:上级Id,<br>
	 *    menuCode:编码,<br>
	 *    menuName:名称,<br>
	 *    menuDesc:描述,<br>
	 *    menuType:类型,10菜单，20功能，30资源<br>
	 *    systemCode:系统编码<br>
	 * }],
	 * }
	 * @author ZhangJun
	 * @createTime 2018/1/5 10:43
	 * @description 查询菜单及资源，构建成树
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBaseMenuResourceTree")
	public String findBaseMenuResourceTree(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			List findList = this.baseMenuServer.findBaseMenuResourceTree(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}


	/**
	 * 保存菜单资源跟角色的关联关系接口
	 * @param params JSON参数<br>
	 *     {<br>
	 *         roleId:角色Id,<br>
	 *         menuData:[{<br>
	 *				menuId:Id,<br>
	 *			    menuType:类型,10菜单，20功能，30资源<br>
	 *         }]<br>
	 *     }
	 * @return 返回操作信息
	 * {<br>
	 *		msg:返回消息,<br>
	 *	    status:状态，E:失败;S:成功<br>
	 * }
	 * @author ZhangJun
	 * @createTime 2018/1/5 11:51
	 * @description 保存菜单资源跟角色的关联关系接口
	 */
	@RequestMapping(method = RequestMethod.POST,value="saveMenuResource")
	public String saveMenuResource(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			if(!queryParamJSON.containsKey("roleId")){
				throw new IllegalArgumentException("缺少参数 roleId");
			}
			this.baseMenuServer.saveMenuResourceRole(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	@RequestMapping(method = RequestMethod.POST,value = "findMenuList")
	public String findMenuList(@RequestParam String params){
		try{
			UserSessionBean userSessionBean=getUserSessionBean();
			Assert.notNull(userSessionBean,"请重新登录");
			String isAdmin = userSessionBean.getIsadmin();
			JSONArray array=userSessionBean.getRoles();
			JSONObject queryParamJSON = parseObject(params);
		/*	if(!"Y".equals(isAdmin)){
				if (array.size()==0)
					return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
			}else{
                List<BaseMenuRoleEntity_HI_RO>  list=baseMenuServer.findMenuList(queryParamJSON);
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", list.size(), list).toString();
            }
			StringBuilder roles=new StringBuilder();
			for (int i=0;i<array.size();i++){
				JSONObject tmpJson=array.getJSONObject(i);
				roles.append(tmpJson.getString("roleId"));
				if (i==array.size()-1)
					break;
				roles.append(",");
			}
			List<BaseMenuRoleEntity_HI_RO>  list=baseMenuServer.findMenuList(queryParamJSON.fluentPut("roleId_in",roles.toString()));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", list.size(), list).toString();*/
			List<BaseMenuRoleEntity_HI_RO>  list=baseMenuServer.findMenuList(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", list.size(), list).toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}

	}


	/**
	 * 菜单点击统计
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "menuClickCount")
	public String menuClickCount(@RequestParam String params){
		try {
		    JSONObject paramJson=JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(paramJson,"menuId");
            String menuId=paramJson.getString("menuId");
			UserSessionBean userSessionBean=getUserSessionBean();
			if (userSessionBean==null)
				return SToolUtils.convertResultJSONObj(ERROR_STATUS,"登录已失效", 0, null).toString();
			String key="MENU_CLICK_COUNT";
			String userId=userSessionBean.getUserId()+"";
			String clickData=jedisCluster.hget(key,userId);
			JSONObject jsonData=new JSONObject();
			if (StringUtils.isNotBlank(clickData)){
				jsonData=JSON.parseObject(clickData);
			}
			Integer val= (Integer) jsonData.getOrDefault(menuId,0);
			jsonData.put(menuId,++val);
			jedisCluster.hset(key,userId,jsonData.toJSONString());
			saveUserAccessLog(Integer.valueOf(menuId));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}

	}


	/**
	 * 保存用户行为记录
	 * @param menuId
	 */
	private void saveUserAccessLog(Integer menuId){
		try {
			UserSessionBean userSessionBean=getUserSessionBean();
			if (userSessionBean==null || menuId==null)
				return;
			BaseUserAccessLogMiddleGroundEntity_MO logInfo=new BaseUserAccessLogMiddleGroundEntity_MO();
			logInfo.setAccessDate(new Date());
			logInfo.setAccessUserLoginName(userSessionBean.getUserName());
			logInfo.setAccessDevice(request.getHeader("User-Agent"));
			logInfo.setAccessSourceIp(getIpAddr());
			logInfo.setAccessUserId(userSessionBean.getUserId()+"");
			BaseMenuEntity_HI menuInfo= baseMenuServer.getById(menuId);
			if (menuInfo==null)
				return;
			logInfo.setAccessMenuTitle(menuInfo.getMenuName());
			logInfo.setAccessSystemCode(menuInfo.getSystemCode());
//			baseUserAccessLogServer.saveMGAccessLogInfo2MQ(logInfo);
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}




}
