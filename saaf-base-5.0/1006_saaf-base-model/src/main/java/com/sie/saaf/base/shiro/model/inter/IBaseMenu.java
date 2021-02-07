package com.sie.saaf.base.shiro.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseMenuEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseMenuResource_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseRoleMenu_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseMenuRoleEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

/**
 * 接口：对菜单表base_menu进行CRUD操作
 *
 * @author ZhangJun
 * @creteTime 2017/12/13
 *
 */
public interface IBaseMenu extends IBaseCommon<BaseMenuEntity_HI> {

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
     * }]
     *
     * @author ZhangJun
     * @creteTime 2017/12/13
     */
    List<BaseMenuEntity_HI> findBaseMenuList(JSONObject queryParamJSON);

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
     * }]
     */
    List<BaseMenuEntity_HI> findChildrenMenus(Integer menuParentId);

    /**
     * 根据角色Id查询菜单
     *
     * @param roleIds 角色Id
     *
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
    List<BaseRoleMenu_HI_RO> findBaseMenuByRoleId(String roleIds);

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
     * fromType:访问来源,<br>
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
    List<BaseRoleMenu_HI_RO> findBaseMenuJoinRole(JSONObject queryParamJSON);

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
     *    menuType:类型,
     *    systemCode:系统编码
     * }]
     * @author ZhangJun
     * @createTime 2018/1/5 10:43
     * @description 查询菜单及资源，构建成树
     */
    List<BaseMenuResource_HI_RO> findBaseMenuResourceTree(JSONObject queryParamJSON);

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
	void saveMenuResourceRole(JSONObject queryParamJSON);

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
     * fromType:访问来源,<br>
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
	List findBaseMenuByRespId(JSONObject queryParamJSON);

    Pagination<BaseRoleMenu_HI_RO> findSelectMenu(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    List<BaseMenuRoleEntity_HI_RO> findMenuList(JSONObject paramJSON);

    List<BaseMenuRoleEntity_HI_RO> findMenuList(JSONObject paramJSON, Integer pageIndex, Integer pageSize);
}
