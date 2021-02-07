/*
 * API 拆分
 * 基础服务 \ 主服务
 * */
define(['webconfig'],function (webconfig) {
    //var webconfig.url.baseServer = serviceURL + 'baseServer/' ; // 基础服务\ 主服务
    return {

        menuDelete: webconfig.url.baseServer + 'baseMenuService/delete', // 删除菜单
        menuByPagination: webconfig.url.baseServer + 'baseMenuService/findPagination', // 获取菜单列表－分页查询
        menuList: webconfig.url.baseServer + 'baseMenuService/findBaseMenuList', // 获取菜单列表
        menuListByButton: webconfig.url.baseServer + 'baseMenuService/findBaseMenuResourceTree',// 获取所有树菜单与功能按钮


        /* 职责管理 */
        responsibility: webconfig.url.baseServer + 'baseResponsibilityService/findPagination',// 查询
        baseResponsibilitySave: webconfig.url.baseServer + 'baseResponsibilityService/save',// *保存、修改 // 保存职责，职责角色，职责关联的profile
        responsibilityDelete: webconfig.url.baseServer + 'baseResponsibilityService/delete',// * 删除
        responsibilityByRole: webconfig.url.baseServer + 'baseResponsibilityService/findResponsibilityByRoleId',//* 根据角色Id查询分配的职责
        responsibilityById: webconfig.url.baseServer + 'baseResponsibilityService/findById',// 根据职责Id查询职责

        /*角色管理*/
        roleList: webconfig.url.baseServer + 'baseRoleService/findPagination',// 查询
        roleSave: webconfig.url.baseServer + 'baseRoleService/save',//*保存/修改
        roleDelete: webconfig.url.baseServer + 'baseRoleService/delete', // 删除
        roleByMenu: webconfig.url.baseServer + 'baseRoleService/findBaseRoleJoinMenu', // 查询菜单与权限关联视图
        roleByResource: webconfig.url.baseServer + 'baseRoleService/findBaseRoleByResourceId',// 根据资源id查询可访问该权限的角色列表

        /*权限管理*/
        userRespSave: webconfig.url.baseServer + 'baseUserResponsibilityService/saveUserResp', //   保存用户职责（用户分配到职责）
        userRespList: webconfig.url.baseServer + 'baseUsersService/findBaseUsersByRespId',// 根据职责查询用户
        findProfileSqlDatas: webconfig.url.baseServer + 'baseProfileService/findProfileSqlDatas',// 查询profile中sql的数据
        menuByRoleSave: webconfig.url.baseServer + 'baseMenuService/saveMenuResource',// /// 保存当前分配的角色菜单


        /*资源管理*/
        resourceList: webconfig.url.baseServer + 'baseResourceService/findPagination', // 查询列表
        resourceSave: webconfig.url.baseServer + 'baseResourceService/save', // 保存修改
        resourceDelete: webconfig.url.baseServer + 'baseResourceService/delete', // 删除
        resourceByMenu: webconfig.url.baseServer + 'baseResourceService/findBaseResourceByMenuId', // 根据菜单查询资源
        resourceByRole: webconfig.url.baseServer + 'baseResourceService/findBaseResourceByRoleId', // 根据角色Id查询角色所分配的资源
        resourceByRespMenuId:webconfig.url.baseServer + '/baseResourceService/findBaseResourceByRespMenuId', //根据菜单和职责id获取资源

        /*按钮功能*/
        buttonList: webconfig.url.baseServer + 'baseButtonDataService/findPagination', // 获取按钮列表
        buttonSave: webconfig.url.baseServer + 'baseButtonDataService/save', // 获取按钮列表
        buttonDetel: webconfig.url.baseServer + 'baseButtonDataService/delete',// 获取按钮列表

        /*组织架构*/
        orgList: webconfig.url.baseServer + 'baseOrganizationService/findPagination', // 查询组织
        orgChildrenAllList: webconfig.url.baseServer + 'baseOrganizationService/findAllChildrens',// 查询下层组织，包括所有的子级，孙级
        orgParentList: webconfig.url.baseServer + 'baseOrganizationService/findAllParents',// 查询所有上组织
        orgChildrenList: webconfig.url.baseServer + 'baseOrganizationService/findCurrentOrgChildrens',  // 下一层组织机构列表，只有直接的子级．没有孙级
        orgDelete: webconfig.url.baseServer + 'baseOrganizationService/delete',// 删除组织
        orgPersonDelete: webconfig.url.baseServer + 'basePersonOrganizationService/delete',// 删除组织架构里的人员
        orgPersonSave: webconfig.url.baseServer + 'basePersonOrganizationService/saveOrgPerson',//  保存组织或职位与多个员工关系

        personListByOrgId: webconfig.url.baseServer + 'basePersonService/findBasePersonsByOrgId', // 根据组织机构Id查询人员列表
        positionListByOrgId: webconfig.url.baseServer + 'basePositionService/findBasePositionsByOrgId', // 根据组织机构Id查询职位

        /*动态报表 图表管理*/
        dynamicReportTypeList: webconfig.url.baseServer + 'baseReportChartsTypeService/find',// 获取动态图表类型
        dynamicReportTypeSave: webconfig.url.baseServer + 'baseReportChartsTypeService/saveOrUpdate', // 保存图表类型（新增或修改)
        dynamicReportTypeDelete: webconfig.url.baseServer + 'baseReportChartsTypeService/delete', // 删除图表类型

        /*动态报表 报表管理*/
        dynamicReportList: webconfig.url.baseServer + 'baseReportService/findReportHeader', // 获取 报表头查询
        dynamicReportLine: webconfig.url.baseServer + 'baseReportService/findReportLine', //  查询报表行
        dynamicReportCreatLine: webconfig.url.baseServer + 'baseReportService/autoSaveReportLine', //
        dynamicReportSave: webconfig.url.baseServer + 'baseReportService/saveOrUpdate', //  保存动态报表
        dynamicReportDataList: webconfig.url.baseServer + 'baseReportDatasourceService/find', // 数据源查询
        dynamicReportListDelete: webconfig.url.baseServer + 'baseReportService/deleteHeader', // 删除 头 报表
        dynamicReportLineDelete: webconfig.url.baseServer + 'baseReportService/deleteLine', // 删除行(字段)
        dynamicReportTablePreview: webconfig.url.baseServer + 'baseReportService/findReportSql', // 表格预览

        dynamicReportGroupList: webconfig.url.baseServer + 'baseReportGroupService/findPagination',// 动态报表组列表
        dynamicReportGroupSave: webconfig.url.baseServer + 'baseReportGroupService/saveOrUpdate', // 动态报表组修改
        dynamicReportGroupDeleteItem: webconfig.url.baseServer + 'baseReportGroupService/deleteReportHeaderInGroup',// 动态报表组删除已添加的报表
        dynamicReportGroupDelete: webconfig.url.baseServer + 'baseReportGroupService/delete', // 删除动态报表组
        dynamicReportGroupDetail: webconfig.url.baseServer + 'baseReportGroupService/findGroupDetails', // 动态报表组详情

        logList: webconfig.url.baseServer + 'mongoService/findPagination' // 日志聚合

    }
})