package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.app.event.PermissionCacheUpdateEvent;
import com.sie.saaf.base.shiro.model.entities.BaseMenuEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseResponsibilityRoleEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseRoleMenuEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseRoleResourceEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseMenuResource_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseRoleMenu_HI_RO;
import com.sie.saaf.base.shiro.model.inter.IBaseMenu;
import com.sie.saaf.base.user.model.entities.readonly.BaseMenuRoleEntity_HI_RO;
import com.sie.saaf.base.user.model.inter.server.BaseAccreditServer;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 对菜单表base_menu进行CRUD操作
 *
 * @author ZhangJun
 * @creteTime 2017/12/13
 */
@Component("baseMenuServer")
public class BaseMenuServer extends BaseCommonServer<BaseMenuEntity_HI> implements IBaseMenu {
	@Autowired
	private ViewObject<BaseMenuEntity_HI> baseMenuDAO_HI;
	@Autowired
	private BaseViewObject<BaseRoleMenu_HI_RO> baseRoleMenuDAO_HI_RO;
	@Autowired
	private BaseViewObject<BaseMenuResource_HI_RO> baseMenuResourceDAO_HI_RO;
	@Autowired
	private ViewObject<BaseRoleMenuEntity_HI> baseRoleMenu_HI_RO;
	@Autowired
	private ViewObject<BaseRoleResourceEntity_HI> baseRoleResourceDAO_HI;
	@Autowired
	private ViewObject<BaseResponsibilityRoleEntity_HI> baseResponsibilityRoleDAO_HI;
	@Autowired
	private BaseViewObject<BaseMenuRoleEntity_HI_RO> baseMenuRoleDAO_HI_RO;
	@Autowired
	private BaseAccreditServer baseAccreditServer;


	public BaseMenuServer() {
		super();
	}

	/**
	 * 分页查询菜单
	 *
	 * @param queryParamJSON JSON参数<br>
	 * {<br>
	 * menuParentId:上级菜单Id<br>
	 * menuCode:菜单Code<br>
	 * menuName:菜单名称<br>
	 * menuType:菜单类型，10-菜单节点；20-功能节点<br>
	 * systemCode:系统编码<br>
	 * fromType:访问来源<br>
	 * enableFlag:是否启用<br>
	 * startDateActive:启用日期<br>
	 * endDateActive:失效日期<br>
	 * levelIds：菜单层级[]
	 *
	 * }
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 *
	 * @return 分页菜单对象列表<br>
	 * { <br>
	 * count: 总记录数,<br>
	 * curIndex: 当前页索引,<br>
	 * data: [{<br>
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
	 * }],<br>
	 * firstIndex: 首页索引,<br>
	 * lastIndex: 尾页索引,<br>
	 * nextIndex: 下一页索引,<br>
	 * pageSize: 每页记录数,<br>
	 * pagesCount: 总页数,<br>
	 * preIndex: 上一页索引<br>
	 * }
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@Override
	public Pagination<BaseMenuEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer(" from BaseMenuEntity_HI as baseMenu where 1=1 ");
		//解决menuType查询出错问题
		if(StringUtils.isNotBlank(queryParamJSON.getString("menuType"))){
			sb.append(" and menuType=:menuType ");
			queryParamMap.put("menuType", queryParamJSON.getString("menuType"));
			queryParamJSON.remove("menuType");
		}
		if(StringUtils.isNotBlank(queryParamJSON.getString("levelIds"))){
			JSONArray levelIdsJSON = queryParamJSON.getJSONArray("levelIds");
			if(levelIdsJSON != null && !levelIdsJSON.isEmpty()){
				sb.append(" and levelId in (" + StringUtils.join(levelIdsJSON, ",") + ")");
				queryParamJSON.remove("levelIds");
			}
		}
		changeQuerySql(queryParamJSON, queryParamMap, sb, true);
		SaafToolUtils.changeQuerySort(queryParamJSON, sb, "baseMenu.order_no asc", true);
		Pagination<BaseMenuEntity_HI> findListResult = baseMenuDAO_HI.findPagination(sb, queryParamMap, pageIndex, pageRows);
		return findListResult;
	}

	/**
	 * 保存或更新菜单项
	 *
	 * @param queryParamJSON JSON参数<br>
	 * {<br>
	 * menuId:菜单Id（更新时必填）,<br>
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
	 * versionNum:版本号（更新时必填）,<br>
	 * }
	 *
	 * @return 菜单对象
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@Override
	public BaseMenuEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		BaseMenuEntity_HI baseMenuEntity_HI = JSON.parseObject(queryParamJSON.toString(), BaseMenuEntity_HI.class);
		List<BaseMenuEntity_HI> list;
		if (baseMenuEntity_HI.getMenuId() != null) {
			//更新
			if (StringUtils.isEmpty(queryParamJSON.getString("versionNum"))) {
				throw new IllegalArgumentException("缺少参数 versionNum");
			}
			list=baseMenuDAO_HI.findList("from BaseMenuEntity_HI where menuCode=? and menuId!=?",baseMenuEntity_HI.getMenuCode(),baseMenuEntity_HI.getMenuId());
		}else {
			list=baseMenuDAO_HI.findByProperty("menuCode",baseMenuEntity_HI.getMenuCode());
		}
		if (list.size()>0)
			throw new IllegalArgumentException("菜单编码重复");
		BaseMenuEntity_HI parentMenuEntity = null;
		if (baseMenuEntity_HI.getMenuParentId() != null) {
			parentMenuEntity = getById(baseMenuEntity_HI.getMenuParentId());
		}
		setBaseMenuDefaultValue(baseMenuEntity_HI, parentMenuEntity);
		baseMenuDAO_HI.saveOrUpdate(baseMenuEntity_HI);

		// 失效时同步更新缓存
		Long now= SaafDateUtils.getDate(0).getTime();
        if ("N".equals(baseMenuEntity_HI.getEnabled())
				|| (baseMenuEntity_HI.getStartDateActive()!=null && baseMenuEntity_HI.getStartDateActive().getTime()> now)
				|| (baseMenuEntity_HI.getEndDateActive()!=null && baseMenuEntity_HI.getEndDateActive().getTime()<now)){
        	Set<String> menuId=new HashSet<>();
        	PermissionCacheUpdateEvent event=new PermissionCacheUpdateEvent("update",menuId,null,null,null);
			SpringBeanUtil.applicationContext.publishEvent(event);
		}

		return baseMenuEntity_HI;
	}

	/**
	 * 设置菜单默认值
	 *
	 * @param baseMenuEntity_HI 菜单对象
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	private void setBaseMenuDefaultValue(BaseMenuEntity_HI baseMenuEntity_HI, BaseMenuEntity_HI parentMenuEntity) {
		if (StringUtils.isEmpty(baseMenuEntity_HI.getEnabled())) {
			baseMenuEntity_HI.setEnabled(CommonConstants.ENABLED_TRUE);
		}
		if (baseMenuEntity_HI.getStartDateActive() == null) {
			baseMenuEntity_HI.setStartDateActive(new Date());
		}
		if (parentMenuEntity == null) {
			baseMenuEntity_HI.setMenuParentId(CommonConstants.ROOT_PARENT_ID);
			baseMenuEntity_HI.setLevelId(1);
		} else {
			baseMenuEntity_HI.setMenuParentId(parentMenuEntity.getMenuId());
			baseMenuEntity_HI.setLevelId(parentMenuEntity.getLevelId() + 1);
		}
	}

	/**
	 * 查询菜单列表
	 *
	 * @param queryParamJSON JSON参数<br>
	 * {<br>
	 * menuParentId:上级菜单Id<br>
	 * menuCode:菜单Code<br>
	 * menuName:菜单名称<br>
	 * menuType:菜单类型，10-菜单节点；20-功能节点<br>
	 * systemCode:系统编码<br>
	 * fromType:访问来源<br>
	 * enableFlag:是否启用<br>
	 * startDateActive:启用日期<br>
	 * endDateActive:失效日期<br>
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
	 * fromType:访问来源<br>
	 * enableFlag:是否启用,<br>
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
	@Override
	public List<BaseMenuEntity_HI> findBaseMenuList(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append("from BaseMenuEntity_HI baseMenu where 1=1 ");
		changeQuerySql(queryParamJSON,queryParamMap,sql,true);
		changeQuerySort(queryParamJSON,sql,"",true);
		return baseMenuDAO_HI.findList(sql,queryParamMap);
	}

	/**
	 * 查询下层菜单
	 *
	 * @param menuParentId 父级菜单Id
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
	 * fromType:访问来源<br>
	 * enableFlag:是否启用,<br>
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
	@Override
	public List<BaseMenuEntity_HI> findChildrenMenus(Integer menuParentId) {
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("menuParentId", menuParentId);
		return this.findBaseMenuList(queryParamJSON);
	}

	/**
	 * 设置查询条件
	 *
	 * @param queryParamJSON 入参
	 * @param paramsMap sql或hql参数
	 * @param sql sql或hql
	 * @param isHql 是否HQL查询，如果是HQL查询，自动将查询字段转换为对象属性
	 */
	@Override
	protected void changeQuerySql(JSONObject queryParamJSON, Map<String, Object> paramsMap, StringBuffer sql,boolean isHql) {
		SaafToolUtils.parperParam(queryParamJSON, "menu_id", "menuId", sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "menu_parent_id", "menuParentId", sql, paramsMap, "=",
				isHql);
		SaafToolUtils.parperParam(queryParamJSON, "menu_code", "menuCode", sql, paramsMap, "=",
				isHql);
		SaafToolUtils.parperParam(queryParamJSON, "menu_name", "menuName", sql, paramsMap, "like",
				isHql);
		SaafToolUtils.parperParam(queryParamJSON, "menu_type", "menuType", sql, paramsMap, "=",
				isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseMenu.system_code", "systemCode", sql, paramsMap, "=",
				isHql);
		SaafToolUtils.parperParam(queryParamJSON, "from_type", "fromType", sql, paramsMap, "in",
				isHql);

		boolean isValid = false;
		if(queryParamJSON.containsKey("isValid")){
			isValid = queryParamJSON.getBooleanValue("isValid");
		}

		if (isValid) {
			// 查询有效的
			if (isHql) {
				sql.append(
						" and baseMenu.startDateActive<=:startDateActive and (baseMenu.endDateActive is null or baseMenu.endDateActive>=:endDateActive) and baseMenu.enabled=:enabledTrue");
			} else {
				sql.append(
						" and baseMenu.start_date_active<=:startDateActive and (baseMenu.end_date_active is null or baseMenu.end_date_active>=:endDateActive) and baseMenu.enabled=:enabledTrue");
			}
			paramsMap.put("startDateActive", new Date());
			paramsMap.put("endDateActive", new Date());
			paramsMap.put("enabledTrue", CommonConstants.ENABLED_TRUE);
		} else {
			SaafToolUtils.parperParam(queryParamJSON, "baseMenu.enabled", "enabled", sql, paramsMap, "=", isHql);
			SaafToolUtils.parperParam(queryParamJSON, "baseMenu.start_date_active", "startDateActive", sql, paramsMap, ">=", isHql);
			SaafToolUtils.parperParam(queryParamJSON, "baseMenu.end_date_active", "endDateActive", sql, paramsMap, "<=", isHql);
		}
	}

	@Override
	protected void changeQuerySort(JSONObject queryParamJSON, StringBuffer sql, String defaultOrderBy, boolean isHql) {
		super.changeQuerySort(queryParamJSON, sql, "order_no asc,last_update_date desc", isHql);
	}

	/**
	 * 根据角色Id查询菜单
	 *
	 * @param roleIds 角色Id ,多个id逗号隔开
	 *
	 * @return 菜单与角色关联列表
	 * <p>
	 * <p>
	 * [{<br>
	 * wmenuId:菜单Id,<br>
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
	 * fromType:访问来源<br>
	 * enableFlag:是否启用,<br>
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
	@Override
	public List<BaseRoleMenu_HI_RO> findBaseMenuByRoleId(String roleIds) {
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("roleId", roleIds);
		return findBaseMenuJoinRole(queryParamJSON);
	}

	/**
	 * 关联菜单与角色查询
	 *
	 * @param queryParamJSON JSON参数<br>
	 * {<br>
	 * menuParentId:上级菜单Id<br>
	 * menuCode:菜单Code<br>
	 * menuName:菜单名称<br>
	 * menuType:菜单类型，10-菜单节点；20-功能节点<br>
	 * systemCode:系统编码<br>
	 * fromType:访问来源<br>
	 * enableFlag:是否启用<br>
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
	 * fromType:访问来源<br>
	 * enableFlag:是否启用,<br>
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
	@Override
	public List<BaseRoleMenu_HI_RO> findBaseMenuJoinRole(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer(BaseRoleMenu_HI_RO.QUERY_MENU_ROLE_SQL);
		changeQuerySql(queryParamJSON, queryParamMap, sb, false);
		SaafToolUtils.parperParam(queryParamJSON, "baseRoleMenu.role_id", "roleId", sb, queryParamMap, "in", false);
		SaafToolUtils.parperParam(queryParamJSON, "baseMenu.menu_type", "menuType", sb, queryParamMap, "=", false);
		SaafToolUtils.changeQuerySort(queryParamJSON, sb, "baseMenu.order_no asc", false);
		List<BaseRoleMenu_HI_RO> findList = baseRoleMenuDAO_HI_RO.findList(sb, queryParamMap);
		List<BaseRoleMenu_HI_RO> newList = new ArrayList<>();
		Set<Integer> menuIds = new HashSet<>();
		for(BaseRoleMenu_HI_RO entity : findList){
			if(!menuIds.contains(entity.getMenuId())){
				newList.add(entity);
				menuIds.add(entity.getMenuId());
			}
		}
		return newList;
	}

	/**
	 * 查询菜单及资源，构建成树
	 * @param queryParamJSON
	 * {
	 *     roleId:角色Id,
	 *     systemCode:系统编码
	 * }
	 * @return
	 * [{
	 *    menuId:Id,
	 *    menuParentId:上级Id,
	 *    menuCode:编码,
	 *    menuName:名称,
	 *    menuDesc:描述,
	 *    menuType:类型,10菜单，20功能，30资源,
	 *    systemCode:系统编码
	 * }]
	 * @author ZhangJun
	 * @createTime 2018/1/5 10:43
	 * @description 查询菜单及资源，构建成树
	 */
	@Override
	public List<BaseMenuResource_HI_RO> findBaseMenuResourceTree(JSONObject queryParamJSON){
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer(BaseMenuResource_HI_RO.QUERY_SQL);
		SaafToolUtils.parperParam(queryParamJSON,"menuButton.roleId","roleId",sb,queryParamMap,"=",false);
		SaafToolUtils.parperParam(queryParamJSON,"menuButton.systemCode","systemCode",sb,queryParamMap,"=",false);
		SaafToolUtils.changeQuerySort(queryParamJSON, sb, "menuButton.menuId", true);
		List<BaseMenuResource_HI_RO> findList = baseMenuResourceDAO_HI_RO.findList(sb, queryParamMap);
		if(findList != null && !findList.isEmpty()){

			Map<String,String> menuIdMap = new HashMap<>();
			for(BaseMenuResource_HI_RO entity : findList){
				if(!StringUtils.equals(entity.getMenuType(),"30")) {
					menuIdMap.put(entity.getMenuId()+"_menu", entity.getMenuId() + "_" + entity.getMenuType()+"_menu");
				}else{
					menuIdMap.put(entity.getMenuId()+"_resource",entity.getMenuId()+"_"+entity.getMenuType()+"_resource");
				}
			}

			for(BaseMenuResource_HI_RO entity : findList){
				if(!StringUtils.equals(entity.getMenuType(),"30")) {
					entity.setMenuId(entity.getMenuId()+"_"+entity.getMenuType()+"_menu");
				}else{
					entity.setMenuId(entity.getMenuId()+"_"+entity.getMenuType()+"_resource");
				}

				if(!StringUtils.equalsIgnoreCase(entity.getMenuParentId(),"0")){
					//菜单
					entity.setMenuParentId(menuIdMap.get(entity.getMenuParentId()+"_menu"));
				}
			}
		}
		return findList;
	}

	/**
	 * 保存菜单资源跟角色的关联关系接口
	 * @param queryParamJSON JSON参数<br>
	 *     {<br>
	 *         roleId:角色Id,<br>
	 *         menuData:[{<br>
	 *				menuId:Id,<br>
	 *			    menuType:类型,10菜单，20功能，30资源<br>
	 *         }]<br>
	 *     }
	 * @author ZhangJun
	 * @createTime 2018/1/5 11:59
	 * @description 保存菜单资源跟角色的关联关系接口
	 */
	@Override
	public void saveMenuResourceRole(JSONObject queryParamJSON){

		Integer roleId = queryParamJSON.getInteger("roleId");
		JSONArray array = queryParamJSON.getJSONArray("menuData");
		Integer operatorUserId = queryParamJSON.getInteger("operatorUserId");

		Set<String> roleIdSet=new HashSet<>();
		Set<String> deleteRoleSet=new HashSet<>();

		JSONObject jsonObjectParam = new JSONObject();
		jsonObjectParam.put("roleId",roleId);
		List<BaseRoleMenuEntity_HI> findList = baseRoleMenu_HI_RO.findList("from BaseRoleMenuEntity_HI where roleId=:roleId",jsonObjectParam);
		Map<Integer,BaseRoleMenuEntity_HI> roleMenuMap = new HashMap<>();
		for(BaseRoleMenuEntity_HI entity : findList){
			roleMenuMap.put(entity.getMenuId(),entity);
		}

		JSONObject jsonResParam = new JSONObject();
		jsonResParam.put("roleId",roleId);
		List<BaseRoleResourceEntity_HI> findResourceList = baseRoleResourceDAO_HI.findList("from BaseRoleResourceEntity_HI where roleId=:roleId",jsonResParam);
		Map<Integer,BaseRoleResourceEntity_HI> roleResMap = new HashMap<>();
		for(BaseRoleResourceEntity_HI entity : findResourceList){
			roleResMap.put(entity.getResourceId(),entity);
		}

		ListIterator it = array.listIterator();
		while(it.hasNext()){
			JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(it.next()));
			String menuId = jsonObject.getString("menuId");
			String menuType = jsonObject.getString("menuType");

			if("10".equals(menuType)||"20".equals(menuType)){
				if(!roleMenuMap.containsKey(menuId)) {
					//
					Integer mid = Integer.parseInt(menuId.split("_")[0]);
					BaseRoleMenuEntity_HI roleMenu = new BaseRoleMenuEntity_HI();
					roleMenu.setRoleId(roleId);
					roleMenu.setMenuId(mid);
					roleMenu.setOperatorUserId(operatorUserId);
					baseRoleMenu_HI_RO.saveOrUpdate(roleMenu);
					roleIdSet.add(roleMenu.getRoleId()+"");
				}else{
					roleMenuMap.remove(menuId);
				}
			}else if("30".equals(menuType)) {
				if(!roleResMap.containsKey(menuId)) {
					//
					BaseRoleResourceEntity_HI roleResource = new BaseRoleResourceEntity_HI();
					roleResource.setRoleId(roleId);
					Integer mid = Integer.parseInt(menuId.split("_")[0]);
					roleResource.setResourceId(mid);
					roleResource.setOperatorUserId(operatorUserId);
					baseRoleResourceDAO_HI.saveOrUpdate(roleResource);
					roleIdSet.add(roleResource.getRoleId()+"");
				}else{
					roleResMap.remove(menuId);
				}
			}

		}

		Iterator<BaseRoleMenuEntity_HI> demenulit = roleMenuMap.values().iterator();
		while(demenulit.hasNext()) {
			BaseRoleMenuEntity_HI obj=demenulit.next();
			deleteRoleSet.add(obj.getRoleId()+"");
			baseRoleMenu_HI_RO.delete(obj);
		}

		Iterator<BaseRoleResourceEntity_HI> delresit = roleResMap.values().iterator();
		while(delresit.hasNext()){
			BaseRoleResourceEntity_HI obj=delresit.next();
			deleteRoleSet.add(obj.getRoleId()+"");
			baseRoleResourceDAO_HI.delete(obj);
		}

		//发布缓存跟新事件
		Set<Integer> userIdSet=baseAccreditServer.findRelatedUserId(null,deleteRoleSet,null,null);
		PermissionCacheUpdateEvent event=new PermissionCacheUpdateEvent("update",roleIdSet,userIdSet);
		SpringBeanUtil.applicationContext.publishEvent(event);
	}

	@Override
	public List findBaseMenuByRespId(JSONObject queryParamJSON) {
		List<BaseRoleMenu_HI_RO> findList = new ArrayList<>();
		if(!StringUtils.equalsIgnoreCase("Y",queryParamJSON.getString("varIsadmin"))) {
			//非超级管理员
			String responsibilityIds = queryParamJSON.getString("responsibilityId");
			Assert.notNull(responsibilityIds, "缺少参数 responsibilityId");

			Map<String, Object> paramsMap = new HashMap<>();
			StringBuffer sb = new StringBuffer(" from BaseResponsibilityRoleEntity_HI where 1=1 ");
			SaafToolUtils.parperHbmParam(BaseResponsibilityRoleEntity_HI.class, queryParamJSON, "responsibilityId", "responsibilityId", sb, paramsMap, "in");
			List<BaseResponsibilityRoleEntity_HI> respRoles = baseResponsibilityRoleDAO_HI.findList(sb, paramsMap);
			Set<Integer> roleIds = new HashSet<>();
			for (BaseResponsibilityRoleEntity_HI respRole : respRoles) {
				roleIds.add(respRole.getRoleId());
			}
			if(roleIds.size() > 0) {
                queryParamJSON.put("roleId", StringUtils.join(roleIds, ","));
            }else{
                queryParamJSON.put("roleId", "-1");
            }
			findList = this.findBaseMenuJoinRole(queryParamJSON);
		}else{
			//超级管理员
			List<BaseMenuEntity_HI> menus = super.findList(queryParamJSON);
			if(!CollectionUtils.isEmpty(menus)){
				for(BaseMenuEntity_HI menu:menus){
					BaseRoleMenu_HI_RO entity = new BaseRoleMenu_HI_RO();
					BeanUtils.copyProperties(menu,entity);
					findList.add(entity);
				}
			}
		}

		return findList;
	}

    @Override
    public Pagination<BaseRoleMenu_HI_RO> findSelectMenu(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        queryParamJSON.put("menuType", "20");
        String responsibilityId = queryParamJSON.getString("respId");
        if (!StringUtils.equalsIgnoreCase("Y", queryParamJSON.getString("varIsadmin"))) {
            queryParamJSON.put("responsibilityId", responsibilityId);
        }
        Assert.notNull(responsibilityId, "缺少参数 responsibilityId");

        Map<String, Object> paramsMap = new HashMap<>();
        StringBuffer sb = new StringBuffer(" from BaseResponsibilityRoleEntity_HI where 1=1 ");
        SaafToolUtils.parperHbmParam(BaseResponsibilityRoleEntity_HI.class, queryParamJSON, "responsibilityId", "responsibilityId", sb, paramsMap, "in");
        List<BaseResponsibilityRoleEntity_HI> respRoles = baseResponsibilityRoleDAO_HI.findList(sb, paramsMap);
        Set<Integer> roleIds = new HashSet<>();
        for (BaseResponsibilityRoleEntity_HI respRole : respRoles) {
            roleIds.add(respRole.getRoleId());
        }
        queryParamJSON.put("roleId", StringUtils.join(roleIds, ","));

        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        StringBuffer sb1 = new StringBuffer(BaseRoleMenu_HI_RO.QUERY_MENU_ROLE_SQL);
        changeQuerySql(queryParamJSON, queryParamMap, sb1, false);
        SaafToolUtils.parperParam(queryParamJSON, "baseRoleMenu.role_id", "roleId", sb1, queryParamMap, "in", false);

        SaafToolUtils.parperParam(queryParamJSON, "baseRoleMenu.menu_id", "menuIds", sb1, queryParamMap, "not in", false);
        SaafToolUtils.parperParam(queryParamJSON, "baseMenu.menu_type", "menuType", sb1, queryParamMap, "=", false);
        sb1.append(" GROUP BY baseRoleMenu.menu_id ");
        SaafToolUtils.changeQuerySort(queryParamJSON, sb1, " baseMenu.order_no asc", false);
		Pagination<BaseRoleMenu_HI_RO> findList1 = baseRoleMenuDAO_HI_RO.findPagination(sb1,SaafToolUtils.getSqlCountString(sb1), queryParamMap,pageIndex,pageRows);
        return findList1;
    }



    @Override
    public List<BaseMenuRoleEntity_HI_RO> findMenuList(JSONObject paramJSON){
		Map<String,Object> map=new JSONObject();
		map.put("userId", paramJSON.getString("operatorUserId"));
		StringBuffer sql=new StringBuffer(BaseMenuRoleEntity_HI_RO.SQL);
		SaafToolUtils.parperHbmParam(BaseMenuRoleEntity_HI_RO.class,paramJSON,sql,map);
		sql.append(" ORDER BY menu.menu_id,menu.system_code,menu.order_no");
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select  menuId as menuId,\n" +
				"            max(menuName) as menuName,\n" +
				"            max(menuDesc) as menuDesc,\n" +
				"            max(systemCode) as systemCode,\n" +
				"            max(htmlUrl) as htmlUrl,\n" +
				"            max(menuCode) as menuCode,\n" +
				"            max(systemName) as systemName,\n" +
				"            roleId as roleId from (").append(sql).append(") t " +
				" where exists ( \n" +
				" SELECT role.role_id AS roleId\n" +
				"   FROM base_user_responsibility bur\n" +
				"  inner JOIN base_responsibility_role brr\n" +
				"     ON bur.responsibility_id = brr.responsibility_id\n" +
				"  inner JOIN base_responsibility br\n" +
				"     ON br.responsibility_id = bur.responsibility_id\n" +
				"  inner JOIN base_role role\n" +
				"     ON brr.role_id = role.role_id\n" +
				"  where t.roleId=brr.role_id and  bur.user_id =:userId)" +
				" group by menuId, roleId");
		List<BaseMenuRoleEntity_HI_RO> list=baseMenuRoleDAO_HI_RO.findList(sqlBuf,map);
		return list;

	}

    @Override
    public List<BaseMenuRoleEntity_HI_RO> findMenuList(JSONObject paramJSON, Integer pageIndex, Integer pageSize){
        Map<String,Object> map=new JSONObject();
        StringBuffer sql=new StringBuffer(BaseMenuRoleEntity_HI_RO.SQL);
        SaafToolUtils.parperHbmParam(BaseMenuRoleEntity_HI_RO.class,paramJSON,sql,map);
        sql.append(" GROUP BY menu.menu_id ORDER BY menu.system_code,menu.order_no,menu.creation_date ");
        Pagination<BaseMenuRoleEntity_HI_RO> list=baseMenuRoleDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql),map,pageIndex,pageSize);
        return list.getData();

    }



}
