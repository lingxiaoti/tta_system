package com.sie.saaf.base.shiro.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseRoleEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseRoleMenu_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseRoleResource_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseUserRole_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseRole extends IBaseCommon<BaseRoleEntity_HI> {

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
	List<BaseRoleMenu_HI_RO> findBaseRoleByMenuId(Integer menuId);

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
	 * pcImageLink:PC端图片样式,<br>
	 * mobileImageLink:APP端图片样式,<br>
	 * pcImageColor:PC端图标颜色,<br>
	 * appImageColor:APP端图标颜色,<br>
	 * pcHtmlUrl:PC端HTML路由链接,<br>
	 * appHtmlUrl:APP端路由链接,<br>
	 * isMobileShow:是否移动端显示,<br>
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
	List<BaseRoleMenu_HI_RO> findBaseRoleJoinMenu(JSONObject queryParamJSON);

	/**
	 * 根据资源id查询可访问该权限的角色列表
	 *
	 * @param resourceId 资源Id
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 *
	 * @return 角色列表<br>
	 * { <br>
	 * count: 总记录数,<br>
	 * curIndex: 当前页索引,<br>
	 * data: [{<br>
	 * roleCode: 角色编码,<br>
	 * roleDesc: 角色描述,<br>
	 * roleId: 角色Id,<br>
	 * roleName: 角色名称,<br>
	 * startDateActive: 生效时间,<br>
	 * systemCode: 失效时间<br>
	 * creationDate:创建日期<br>
	 * createdBy:创建人<br>
	 * lastUpdatedBy:更新人<br>
	 * lastUpdateDate:更新日期<br>
	 * versionNum:版本号<br>
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
	 * @creteTime 2017/12/14
	 */
	Pagination<BaseRoleResource_HI_RO> findBaseRoleByResourceId(Integer resourceId, Integer pageIndex, Integer pageRows);


	List<BaseUserRole_HI_RO> findRoleByuserId(Integer userId);

    List<BaseRoleEntity_HI> check(JSONObject jsonObject);
}
