package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseResponsibilityRoleEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseRoleEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseRoleMenu_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseRoleResource_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseUserRole_HI_RO;
import com.sie.saaf.base.shiro.model.inter.IBaseRole;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseRoleServer")
public class BaseRoleServer extends BaseCommonServer<BaseRoleEntity_HI> implements IBaseRole {
	@Autowired
	private ViewObject<BaseRoleEntity_HI> baseRoleDAO_HI;
	@Autowired
	private ViewObject<BaseResponsibilityRoleEntity_HI> baseResponsibilityRoleDAO_HI;
	@Autowired
	private BaseViewObject<BaseRoleMenu_HI_RO> baseRoleMenuDAO_HI_RO;
	@Autowired
	private BaseViewObject<BaseRoleResource_HI_RO> baseRoleResourceDAO_HI_RO;
	@Autowired
	private BaseViewObject<BaseUserRole_HI_RO> baseUserRoleDAO_HI_RO;

	public BaseRoleServer() {
		super();
	}

	/**
	 * 分页查询角色列表
	 *
	 * @param queryParamJSON 查询参数<br>
	 * {<br>
	 * roleName:角色名称<br>
	 * roleCode:角色编码<br>
	 * systemCode:系统编码<br>
	 * startDateActive:生效时间<br>
	 * endDateActive:失效时间<br>
	 * }
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 *
	 * @return 分页权限列表
	 * { <br>
	 * count: 总记录数,<br>
	 * curIndex: 当前页索引,<br>
	 * data: [{<br>
	 * roleId:角色Id,<br>
	 * roleName:角色名称,<br>
	 * roleCode:角色编号,<br>
	 * roleDesc:角色描述,<br>
	 * systemCode:系统编码,<br>
	 * startDateActive:生效时间,<br>
	 * endDateActive:失效时间,<br>
	 * creationDate:创建日期,<br>
	 * createdBy:创建人,<br>
	 * lastUpdatedBy:更新人,<br>
	 * lastUpdateDate:更新日期,<br>
	 * versionNum:版本号,<br>
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
	public Pagination<BaseRoleEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" from BaseRoleEntity_HI as baseRole where 1=1 ");
		if(!"Y".equals(queryParamJSON.getString("varIsadmin"))){
			Map<String,Object> ms = new HashMap<String,Object>();
			ms.put("varUserId",queryParamJSON.getInteger("varUserId"));
			ms.put("varStartDateActive", new Date());
			ms.put("varEndDateActive", new Date());
			List<BaseResponsibilityRoleEntity_HI>  list = baseResponsibilityRoleDAO_HI.findList(" from BaseResponsibilityRoleEntity_HI brr where exists ( from BaseUserResponsibilityEntity_HI userResp where userResp.responsibilityId = brr.responsibilityId and userResp.userId = :varUserId and userResp.startDateActive<=:varStartDateActive and (userResp.endDateActive is null or userResp.endDateActive>=:varEndDateActive) )",ms);
			if(list.size() > 0){
				String roles = "";
				for(BaseResponsibilityRoleEntity_HI baseResponsibilityRoleEntity_HI:list){
					roles = "," + baseResponsibilityRoleEntity_HI.getRoleId().toString() + roles;
				}
				sb.append( " and roleId in ( " + roles.substring(1) + " ) ");
			}
		}
		changeQuerySql(queryParamJSON, queryParamMap, sb, true);
		Pagination<BaseRoleEntity_HI> findListResult = baseRoleDAO_HI.findPagination(sb, queryParamMap, pageIndex, pageRows);
		return findListResult;
	}

	/**
	 * 保存一条数据
	 *
	 * @param queryParamJSON JSON参数<br>
	 * {<br>
	 * roleId:角色Id,（更新数据必填）<br>
	 * roleName:角色名称,<br>
	 * roleCode:角色编号,<br>
	 * roleDesc:角色描述,<br>
	 * systemCode:系统编码,<br>
	 * startDateActive:生效时间,<br>
	 * endDateActive:失效时间,<br>
	 * versionNum:版本号,（更新数据必填）<br>
	 * operatorUserId:操作者<br>
	 * }
	 *
	 * @return BaseRoleEntity_HI对象
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@Override
	public BaseRoleEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

	@Override
	protected void setEntityDefaultValue(BaseRoleEntity_HI entity) {
		if (entity.getStartDateActive() == null) {
			entity.setStartDateActive(new Date());
		}
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
	protected void changeQuerySql(JSONObject queryParamJSON, Map<String, Object> paramsMap, StringBuffer sql, boolean isHql) {
		SaafToolUtils.parperParam(queryParamJSON, "baseRole.role_name", "roleName", sql, paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseRole.role_code", "roleCode", sql, paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseRole.system_code", "systemCode", sql, paramsMap, "=", isHql);
		boolean isValid = false;
		if(queryParamJSON.containsKey("isValid")){
			isValid = queryParamJSON.getBooleanValue("isValid");
		}
		if (isValid) {
			// 查询有效的
			if (isHql) {
				sql.append(
						" and baseRole.startDateActive<=:startDateActive and (baseRole.endDateActive is null or baseRole.endDateActive>=:endDateActive) ");
			} else {
				sql.append(
						" and baseRole.start_date_active<=:startDateActive and (baseRole.end_date_active is null or baseRole.end_date_active>=:endDateActive)");
			}
			paramsMap.put("startDateActive", new Date());
			paramsMap.put("endDateActive", new Date());
		} else {
			SaafToolUtils.parperParam(queryParamJSON, "baseRole.start_date_active", "startDateActive", sql, paramsMap, ">=", isHql);
			SaafToolUtils.parperParam(queryParamJSON, "baseRole.end_date_active", "endDateActive", sql, paramsMap, "<=", isHql);
		}
	}

	/**
	 * 根据菜单Id查询角色列表
	 *
	 * @param menuId 菜单Id
	 *
	 * @return 菜单与权限关联视图列表<br>
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@Override
	public List<BaseRoleMenu_HI_RO> findBaseRoleByMenuId(Integer menuId) {
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("menuId", menuId);
		return this.findBaseRoleJoinMenu(queryParamJSON);
	}

	/**
	 * 查询菜单与权限关联视图
	 *
	 * @param queryParamJSON JSON查询参数
	 * {<br>
	 * roleName:角色名称<br>
	 * roleCode:角色编码<br>
	 * systemCode:系统编码<br>
	 * startDateActive:生效时间<br>
	 * endDateActive:失效时间<br>
	 * menuId:菜单Id
	 * }
	 *
	 * @return 菜单与权限关联视图列表<br>
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
	 * from_type:访问来源,<br>
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
	 * roleVersionNum;//角色版本号,<br>
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@Override
	public List<BaseRoleMenu_HI_RO> findBaseRoleJoinMenu(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(BaseRoleMenu_HI_RO.QUERY_MENU_ROLE_SQL);
		SaafToolUtils.parperParam(queryParamJSON, "baseMenu.menu_type", "menuType", sb, queryParamMap, "=", false);
		SaafToolUtils.parperParam(queryParamJSON, "baseRoleMenu.menu_id", "menuId", sb, queryParamMap, "in", false);
		changeQuerySql(queryParamJSON, queryParamMap, sb, false);
		List<BaseRoleMenu_HI_RO> findList = baseRoleMenuDAO_HI_RO.findList(sb, queryParamMap);
		return findList;
	}


	@Override
	public Pagination<BaseRoleResource_HI_RO> findBaseRoleByResourceId(Integer resourceId, Integer pageIndex, Integer pageRows) {
		StringBuffer sb = new StringBuffer();
		sb.append(BaseRoleResource_HI_RO.QUERY_ROLE_SQL);

		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("resourceId",resourceId);

		Map<String,Object> queryParamMap = new HashMap<String,Object>();
		changeQuerySql(queryParamJSON, queryParamMap, sb, false);
		SaafToolUtils.parperParam(queryParamJSON,"baseRoleResource.resource_id","resourceId",sb,queryParamMap,"=",false);

		Pagination<BaseRoleResource_HI_RO> findList = baseRoleResourceDAO_HI_RO.findPagination(sb,SaafToolUtils.getSqlCountString(sb),queryParamMap,pageIndex,pageRows);

		return findList;
	}

	@Override
	public List<BaseUserRole_HI_RO> findRoleByuserId(Integer userId){
		StringBuffer sb = new StringBuffer(BaseUserRole_HI_RO.QUERY);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		//SaafToolUtils.parperHbmParam(BaseUserRole_HI_RO.class,new JSONObject().fluentPut("userId",userId),sb,map);
		sb.append(" group by br.role_id ");
		return baseUserRoleDAO_HI_RO.findList(sb,map);
	}

    @Override
    public List<BaseRoleEntity_HI> check(JSONObject jsonObject) {
        String     roleCode   = jsonObject.getString("roleCode");
        String     systemCode = jsonObject.getString("systemCode");
        String     roleName   = jsonObject.getString("roleName");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("roleCode",roleCode);
        paramMap.put("systemCode",systemCode);
        paramMap.put("roleName",roleName);
        StringBuffer sql = new StringBuffer(" from BaseRoleEntity_HI where (roleCode = :roleCode and systemCode = :systemCode)" +
                " or (roleName = :roleName and systemCode = :systemCode)");
        List<BaseRoleEntity_HI> list = baseRoleDAO_HI.findList(sql, paramMap);
        return list;
    }
}
