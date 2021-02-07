define(["app", "../../processform/processFormRouter"], function (app, view) {
    var path = appName.toLocaleLowerCase();
    app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('main', {
                url: '/' + path + '/main',
                templateUrl: 'layout/default/main/main.html',
                controllerUrl: '../layout/default/main/mainCtrl'
            })

            //用户角色权限
            .state('userRoleProfile',
                {
                    stateName: '用户角色权限',
                    buttonUrl: '/userRoleProfile',
                    url: '/' + path + '/userRoleProfile',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmBaseUserRoleProfile.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmBaseUserRoleProfileCtrl'
                })

            //用户角色菜单
            .state('userRoleMenu',
                {
                    stateName: '用户角色菜单',
                    buttonUrl: '/userRoleMenu',
                    url: '/' + path + '/userRoleMenu',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmBaseUserRoleMenu.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmBaseUserRoleMenuCtrl'
                })

            .state('test',
                {
                    stateName: '测试',
                    url: '/' + path + '/test/t1',
                    templateUrl: './test/t1/t1.html',
                    controllerUrl: '../test/t1/t1Ctrl'
                })
            .state('gotoDepartmentStructureList',
                {
                    stateName: '菜单功能',
                    url: '/' + path + '/gotoDepartmentStructureList',
                    templateUrl: 'app/' + path + '/templates/computer/menu/menuFunction.html',
                    controllerUrl: '../app/' + path + '/templates/computer/menu/menuFunctionCtrl'
                })
            .state('menuFunction',
                {
                    stateName: '菜单功能',
                    url: '/' + path + '/systemsetting/menuFunction',
                    templateUrl: 'app/' + path + '/templates/computer/menu/menuFunction.html',
                    controllerUrl: '../app/' + path + '/templates/computer/menu/menuFunctionCtrl'
                })
            .state('menuFuncPrivilege',
                {
                    url: '/' + path + '/systemsetting/menuFuncPrivilege',
                    stateName: '菜单权限',
                    templateUrl: 'app/' + path + '/templates/computer/menu/menuFuncPrivilege.html',
                    controllerUrl: '../app/' + path + '/templates/computer/menu/menuFuncPrivilegeCtrl'
                })

            .state('responsibility',
                {
                    url: '/' + path + '/systemsetting/responsibility',
                    stateName: '职责管理',
                    templateUrl: 'app/' + path + '/templates/computer/responsibility/responsibility.html',
                    controllerUrl: '../app/' + path + '/templates/computer/responsibility/responsibilityCtrl'
                })
            .state('respSave', {
                url: '/' + path + '/systemsetting/respSave/:id',
                stateName: '职责编辑',
                templateUrl: 'app/' + path + '/templates/computer/responsibility/respSave.html',
                controllerUrl: '../app/' + path + '/templates/computer/responsibility/respSaveCtrl'
            })
            .state('userMaintenance',
                {
                    url: '/' + path + '/systemsetting/userMaintenance',
                    stateName: '用户管理',
                    templateUrl: 'app/' + path + '/templates/computer/userMaintenance/userMaintenance.html',
                    controllerUrl: '../app/' + path + '/templates/computer/userMaintenance/userMaintenanceCtrl'
                })

            .state('role',
                {
                    url: '/' + path + '/systemsetting/role',
                    stateName: '角色管理',
                    templateUrl: 'app/' + path + '/templates/computer/role/role.html',
                    controllerUrl: '../app/' + path + '/templates/computer/role/roleCtrl'

                })
            /*权限管理*/
            .state('userResponsibility',
                {
                    url: '/' + path + '/systemsetting/userResponsibility',
                    stateName: '职责权限分配',
                    templateUrl: 'app/' + path + '/templates/computer/responsibility/userResponsibility.html',
                    controllerUrl: '../app/' + path + '/templates/computer/responsibility/userResponsibilityCtrl'
                })

            /*组织架构*/
            .state('institution',
                {
                    url: '/' + path + '/systemsetting/institution/:treeType',
                    stateName: '组织架构维护',
                    templateUrl: 'app/' + path + '/templates/computer/institution/institution.html',
                    controllerUrl: '../app/' + path + '/templates/computer/institution/institutionCtrl'
                })

            // 动态报表 图表类型配置
            .state('dynamicChartsInfo',
                {
                    url: '/base/dynamicCharts/chartsInfo',
                    stateName: '图表类型配置',
                    templateUrl: 'app/base/templates/computer/dynamicCharts/chartsInfo/dynamicChartsInfo.html',
                    controllerUrl: '../app/base/templates/computer/dynamicCharts/chartsInfo/dynamicChartsInfoCtrl'
                })

            .state('dynamicChartsGroup',
                {
                    url: '/base/dynamicCharts/group',
                    stateName: '动态报表组配置',
                    templateUrl: 'app/base/templates/computer/dynamicCharts/group/dynamicChartsGroup.html',
                    controllerUrl: '../app/base/templates/computer/dynamicCharts/group/dynamicChartsGroupCtrl'
                })
            .state('dynamicChartslist',
                {
                    url: '/base/dynamicCharts/list',
                    stateName: '动态报表配置',
                    templateUrl: 'app/base/templates/computer/dynamicCharts/list/dynamicChartslist.html',
                    controllerUrl: '../app/base/templates/computer/dynamicCharts/list/dynamicChartslistCtrl'
                })
            .state('dynamicChartsGroupDetail',
                {
                    url: '/base/dynamicCharts/groupDetail/:id',
                    stateName: '动态报表组配置',
                    templateUrl: 'app/base/templates/computer/dynamicCharts/group/dynamicChartsGroupDetail.html',
                    controllerUrl: '../app/base/templates/computer/dynamicCharts/group/dynamicChartsGroupDetailCtrl'
                })
            .state('dynamicChartsDetail',
                {
                    url: '/base/dynamicCharts/detail/:reportHeaderId',
                    stateName: '动态报表详情',
                    templateUrl: 'app/base/templates/computer/dynamicCharts/detail/dynamicChartsDetail.html',
                    controllerUrl: '../app/base/templates/computer/dynamicCharts/detail/dynamicChartsDetailCtrl'
                })

            //api  管理模块
            .state('apiContent',
                {
                    url: '/' + path + '/apiContent/:id',
                    stateName: 'api详情',
                    templateUrl: 'app/' + path + '/templates/api_manager/apiContent/apiContent.html',
                    controllerUrl: '../app/' + path + '/templates/api_manager/apiContent/apiContentCtrl'
                })
            .state('apiManager',
                {
                    url: '/' + path + '/apiManager',
                    stateName: 'api列表',
                    templateUrl: 'app/' + path + '/templates/api_manager/apiManager/apiManager.html',
                    controllerUrl: '../app/' + path + '/templates/api_manager/apiManager/apiManagerCtrl'
                })
            .state('projectAndModule',
                {
                    url: '/' + path + '/projectAndModule',
                    stateName: '项目&模块',
                    templateUrl: 'app/' + path + '/templates/api_manager/projectAndModule/projectAndModule.html',
                    controllerUrl: '../app/' + path + '/templates/api_manager/projectAndModule/projectAndModuleCtrl'
                })



            //sso 模块
            .state('baseSSO',
                {
                    url: '/' + path + '/baseSSO',
                    stateName: 'SSO管理',
                    templateUrl: 'app/' + path + '/templates/SSO/baseSSO.html',
                    controllerUrl: '../app/' + path + '/templates/SSO/baseSSOCtrl'
                })
            .state('baseSSODetail',
                {
                    url: '/' + path + '/baseSSODetail/:id',
                    templateUrl: 'app/' + path + '/templates/SSO/baseSSODetail.html',
                    controllerUrl: '../app/' + path + '/templates/SSO/baseSSODetailCtrl'
                })

            .state('baseSSORole',
                {
                    url: '/' + path + '/baseSSORole',
                    stateName: '角色映射',
                    templateUrl: 'app/' + path + '/templates/SSO/baseSSORole.html',
                    controllerUrl: '../app/' + path + '/templates/SSO/baseSSORoleCtrl'
                })


            //数据字典  快码
            .state('lookuptype',
                {
                    url: '/' + path + '/lookuptype',
                    stateName: '快码列表',
                    templateUrl: 'app/' + path + '/templates/computer/lookuptype/lookuptype.html',
                    controllerUrl: '../app/' + path + '/templates/computer/lookuptype/lookuptypeCtrl'
                })
            //执行事务补偿
            .state('transactionCompen',
                {
                    url: '/' + path + '/transactionCompen',
                    stateName: '执行事务补偿',
                    templateUrl: 'app/' + path + '/templates/computer/traCom/transactionCompen.html',
                    controllerUrl: '../app/' + path + '/templates/computer/traCom/transactionCompenCtrl'
                })
            .state('editLookuptype',
                {
                    url: '/' + path + '/editLookuptype/:lookupTypeId',
                    stateName: '快码详情',
                    templateUrl: 'app/' + path + '/templates/computer/lookuptype/editLookuptype.html',
                    controllerUrl: '../app/' + path + '/templates/computer/lookuptype/editLookuptypeCtrl'
                })


            //数据源配置
            .state('datasource',
                {
                    url: '/' + path + '/datasource',
                    stateName: '数据源配置管理',
                    templateUrl: 'app/' + path + '/templates/computer/datasource/datasource.html',
                    controllerUrl: '../app/' + path + '/templates/computer/datasource/datasourceCtrl'
                })



            /* 缓存管理*/
            .state('cacheManager',
                {
                    url: '/' + path + '/cacheManager',
                    templateUrl: 'app/' + path + '/templates/cache/cacheManager.html',
                    controllerUrl: '../app/' + path + '/templates/cache/cacheManagerCtrl'
                })


            /* 按钮列表*/
            .state('btnList',
                {
                    url: '/' + path + '/systemSetting/btnList',
                    templateUrl: 'app/' + path + '/templates/computer/btnList/btnList.html',
                    controllerUrl: '../app/' + path + '/templates/computer/btnList/btnListCtrl'
                })

            /* 用户维护*/
            .state('userCenter',
                {
                    url: '/' + path + '/userCenter',
                    templateUrl: 'app/' + path + '/templates/userCenter/userCenter.html',
                    controllerUrl: '../app/' + path + '/templates/userCenter/userCenterCtrl'
                })
            /* 用户维护详情*/
            .state('userDetail',
                {
                    url: '/' + path + '/userDetail/:id',
                    templateUrl: 'app/' + path + '/templates/userCenter/userDetail.html',
                    controllerUrl: '../app/' + path + '/templates/userCenter/userDetailCtrl'
                })


            /* 员工维护*/
            .state('employee',
                {
                    url: '/' + path + '/employee',
                    templateUrl: 'app/' + path + '/templates/employee/employee.html',
                    controllerUrl: '../app/' + path + '/templates/employee/employeeCtrl'
                })
            /* 员工维护详情*/
            .state('employeeDetail',
                {
                    url: '/' + path + '/employeeDetail/:id',
                    templateUrl: 'app/' + path + '/templates/employee/employeeDetail.html',
                    controllerUrl: '../app/' + path + '/templates/employee/employeeDetailCtrl'
                })
            /* profile管理*/
            .state('profile',
                {
                    url: '/' + path + '/profile',
                    templateUrl: 'app/' + path + '/templates/profile/profile.html',
                    controllerUrl: '../app/' + path + '/templates/profile/profileCtrl'
                })
            /* 配置文件管理*/
            .state('configuration',
                {
                    url: '/' + path + '/configuration',
                    templateUrl: 'app/' + path + '/templates/configuration/configuration.html',
                    controllerUrl: '../app/' + path + '/templates/configuration/configurationCtrl'
                })


            /* 资源管理*/
            .state('resource',
                {
                    url: '/' + path + '/resource',
                    templateUrl: 'app/' + path + '/templates/resource/resource.html',
                    controllerUrl: '../app/' + path + '/templates/resource/resourceCtrl'
                })
            /* 资源管理详情*/
            .state('resourceDetail',
                {
                    url: '/' + path + '/resourceDetail',
                    templateUrl: 'app/' + path + '/templates/resource/resourceDetail.html',
                    controllerUrl: '../app/' + path + '/templates/resource/resourceDetailCtrl'
                })
            /* 收藏夹*/
            .state('collect',
                {
                    url: '/' + path + '/collect',
                    templateUrl: 'app/' + path + '/templates/collect/collect.html',
                    controllerUrl: '../app/' + path + '/templates/collect/collectCtrl'
                })

            /* 文章列表*/
            .state('articleManagementList',
                {
                    url: '/' + path + '/articleManagementList',
                    templateUrl: 'app/' + path + '/templates/cms/articleManagementList.html',
                    controllerUrl: '../app/' + path + '/templates/cms/articleManagementListCtrl'
                })
            .state('articleManagementDetail',
                {
                    url: '/' + path + '/articleManagementDetail/:id',
                    templateUrl: 'app/' + path + '/templates/cms/articleManagementDetail.html',
                    controllerUrl: '../app/' + path + '/templates/cms/articleManagementDetailCtrl'
                })
            //广告维护
            .state('cmsAdvertisement',
                {
                    url: '/' + path + '/cmsAdvertisement',
                    templateUrl: 'app/' + path + '/templates/cms/cmsAdvertisement.html',
                    controllerUrl: '../app/' + path + '/templates/cms/cmsAdvertisementCtrl'
                })
            //消息模版
            .state('cmsActionMessage',
                {
                    url: '/' + path + '/cmsActionMessage',
                    templateUrl: 'app/' + path + '/templates/cms/cmsActionMessage.html',
                    controllerUrl: '../app/' + path + '/templates/cms/cmsActionMessageCtrl'
                })
            //消息推送
            .state('messageMaintenance',
                {
                    url: '/' + path + '/messageMaintenance',
                    templateUrl: 'app/' + path + '/templates/cms/messageMaintenance.html',
                    controllerUrl: '../app/' + path + '/templates/cms/messageMaintenanceCtrl'
                })
            //redis
            .state('redis',
                {
                    url: '/' + path + '/redis',
                    templateUrl: 'app/' + path + '/templates/redis/redis.html',
                    controllerUrl: '../app/' + path + '/templates/redis/redisCtrl'
                })

            //提交请求
            .state('submitRequest',
                {
                    url: '/' + path + '/submitRequest',
                    templateUrl: 'app/' + path + '/templates/dispatch/submitRequest.html',
                    controllerUrl: '../app/' + path + '/templates/dispatch/submitRequestCtrl'
                })

            //调度错误监控
            .state('schedulingError',
                {
                    url: '/' + path + '/schedulingError',
                    templateUrl: 'app/' + path + '/templates/dispatch/schedulingError.html',
                    controllerUrl: '../app/' + path + '/templates/dispatch/schedulingErrorCtrl'
                })

                /*********************OI报表start********************************/
                //报表1生成记录信息
              .state('reportExportRecord',
                {
                    url: '/' + path + '/reportExportRecord/:reportType',
                    templateUrl: 'app/' + path + '/templates/tta/report/reportExportRecord.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/reportExportRecordCtrl'
                })
            .state('ttaReportABOISummary',
                {
                    url: '/' + path + '/ttaReportABOISummary',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaReportABOISummary.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaReportABOISummaryCtrl'
                })
              //报表头管理
            .state('oiReportHeader',
                {
                    url: '/' + path + '/oiReportHeader/:reportType',
                    templateUrl: 'app/' + path + '/templates/tta/report/oiReportHeader.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/oiReportHeaderCtrl'
                })

            .state('oiGpReport',
                {
                    url: '/' + path + '/oiGpReport/:batchCode',
                    templateUrl: 'app/' + path + '/templates/tta/report/oiGpReport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/oiGpReportCtrl'
                })

            .state('ytdSceneExportRecord',
                {
                    url: '/' + path + '/ytdSceneExportRecord/:sceneType',
                    templateUrl: 'app/' + path + '/templates/tta/ytdScene/ytdSceneExportRecord.html',
                    controllerUrl: '../app/' + path + '/templates/tta/ytdScene/ytdSceneExportRecordCtrl'
                })
            /*********************OI报表end********************************/
            

            //并发程序
            .state('concurrentPrograms',
                {
                    url: '/' + path + '/concurrentPrograms',
                    templateUrl: 'app/' + path + '/templates/dispatch/concurrentPrograms.html',
                    controllerUrl: '../app/' + path + '/templates/dispatch/concurrentProgramsCtrl'
                })
            //并发程序
            .state('concurrentProgramsDetail',
                {
                    url: '/' + path + '/concurrentProgramsDetail/:jobId',
                    templateUrl: 'app/' + path + '/templates/dispatch/concurrentProgramsDetail.html',
                    controllerUrl: '../app/' + path + '/templates/dispatch/concurrentProgramsDetailCtrl'
                })
            //请求详情
            .state('requestContent',
                {
                    url: '/' + path + '/requestContent/:id',
                    templateUrl: 'app/' + path + '/templates/dispatch/requestContent.html',
                    controllerUrl: '../app/' + path + '/templates/dispatch/requestContentCtrl'
                })

            /* 导出*/
            .state('export',
                {
                    url: '/' + path + '/export',
                    templateUrl: 'app/' + path + '/templates/derivation/export.html',
                    controllerUrl: '../app/' + path + '/templates/derivation/exportCtrl'
                })
            /* 日志聚合 */
            .state('log',
                {
                    url: '/' + path + '/log',
                    templateUrl: 'app/' + path + '/templates/computer/diary/diary.html',
                    controllerUrl: '../app/' + path + '/templates/computer/diary/logCtrl'
                })

            /*流程管理  ----------------------------------------------------------------------------------------------*/
            .state('processList',
                {
                    url: '/' + path + '/processList',
                    templateUrl: 'app/' + path + '/templates/processDesigner/processList.html',
                    controllerUrl: '../app/' + path + '/templates/processDesigner/processListCtrl'
                })

            /*发起流程*/
            .state('processInitiate',
                {
                    url: '/' + path + '/processInitiate',
                    templateUrl: 'app/' + path + '/templates/processDesigner/processInitiate.html',
                    controllerUrl: '../app/' + path + '/templates/processDesigner/processInitiateCtrl'
                })

            /*流程角色*/
            .state('processRoleList',
                {
                    url: '/' + path + '/processRoleList',
                    templateUrl: 'app/' + path + '/templates/processDesigner/processRoleList.html',
                    controllerUrl: '../app/' + path + '/templates/processDesigner/processRoleListCtrl'
                })
            //流程模型节点设置
            .state('setProcessTask',
                {
                    url: '/' + path + '/setProcessTask/:id/:key',
                    templateUrl: 'app/' + path + '/templates/processDesigner/setProcessTask.html',
                    controllerUrl: '../app/' + path + '/templates/processDesigner/setProcessTaskCtrl'
                })
            //我的流程 myProcessList
            .state('myProcessList',
                {
                    url: '/' + path + '/myProcessList',
                    templateUrl: 'app/' + path + '/templates/processDesigner/myProcessList.html',
                    controllerUrl: '../app/' + path + '/templates/processDesigner/myProcessListCtrl'
                })

            //我的流程 已审批
            .state('myApproval',
                {
                    url: '/' + path + '/myApproval',
                    templateUrl: 'app/' + path + '/templates/processDesigner/myApproval.html',
                    controllerUrl: '../app/' + path + '/templates/processDesigner/myApprovalCtrl'
                })

            //我的流程 未审批
            .state('myUpcoming',
                {
                    url: '/' + path + '/myUpcoming',
                    templateUrl: 'app/' + path + '/templates/processDesigner/myUpcoming.html',
                    controllerUrl: '../app/' + path + '/templates/processDesigner/myUpcomingCtrl'
                })
            //发起流程后 显示流程页面
            .state('gtasks',
                {
                    url: '/' + path + '/gtasks',
                    templateUrl: 'app/' + path + '/templates/processDesigner/gtasks.html',
                    controllerUrl: '../app/' + path + '/templates/processDesigner/gtasksCtrl'
                })
            //待阅
            .state('unread',
                {
                    url: '/' + path + '/unread',
                    templateUrl: 'app/' + path + '/templates/processDesigner/unread.html',
                    controllerUrl: '../app/' + path + '/templates/processDesigner/unreadCtrl'
                })

            // 流程监控
            .state('processMonitoring',
                {
                    url: '/' + path + '/processMonitoring',
                    templateUrl: 'app/' + path + '/templates/processDesigner/processMonitoring.html',
                    controllerUrl: '../app/' + path + '/templates/processDesigner/processMonitoringCtrl'
                })

            // 催办设置
            .state('reminderSetting',
                {
                    url: '/' + path + '/reminderSetting',
                    templateUrl: 'app/' + path + '/templates/processDesigner/reminderSetting.html',
                    controllerUrl: '../app/' + path + '/templates/processDesigner/reminderSettingCtrl'
                })

            /*  //发起流程后 显示流程页面
              .state('showProcess',
                  {
                      url: '/' + path + '/showProcess/:url',
                      templateUrl: 'app/' + path + '/templates/processDesigner/showProcess.html',
                      controllerUrl: '../app/' + path + '/templates/processDesigner/showProcessCtrl'
                  })
              //发起流程后 显示流程页面
              .state('gtasks',
                  {
                      url: '/' + path + '/gtasks',
                      templateUrl: 'app/' + path + '/templates/processDesigner/gtasks.html',
                      controllerUrl: '../app/' + path + '/templates/processDesigner/gtasksCtrl'
                  })
              //待阅
              .state('unread',
                  {
                      url: '/' + path + '/unread',
                      templateUrl: 'app/' + path + '/templates/processDesigner/unread.html',
                      controllerUrl: '../app/' + path + '/templates/processDesigner/unreadCtrl'
                  })

                  //


            // 表单路由 -------------------------------------------------------------------------------------------------
              .state('OAForm',
                  {
                      url: '/' + path + '/OAForm',
                      templateUrl: 'app/' + path + '/templates/processDesigner/formHtml/OAForm.html',
                      controllerUrl: '../app/' + path + '/templates/processDesigner/formHtml/OAFormCtrl'
                  })
              .state('assetApplyForm',
                  {
                      url: '/' + path + '/assetApplyForm',
                      templateUrl: 'app/oa/templates/processDesigner/formHtml/asset/assetApplyForm.html',
                      controllerUrl: '../app/oa/templates/processDesigner/formHtml/asset/assetApplyFormCtrl'
                  })
              */

            //行政组织（部门）列表
            .state('department',
                {
                    url: '/' + path + '/department',
                    templateUrl: 'app/' + path + '/templates/organizationStructure/department.html',
                    controllerUrl: '../app/' + path + '/templates/organizationStructure/departmentCtrl'
                })
            //条款管理
            .state('clauseManage',
                {
                    url: '/' + path + '/clauseManage',
                    templateUrl: 'app/' + path + '/templates/tta/clause/clauseManage.html',
                    controllerUrl: '../app/' + path + '/templates/tta/clause/clauseManageCtrl'
                })
            //条款管理详情
            .state('clauseManageD',
                {
                    url: '/' + path + '/clauseManageD/:itemId',
                    templateUrl: 'app/' + path + '/templates/tta/clause/clauseManageD.html',
                    controllerUrl: '../app/' + path + '/templates/tta/clause/clauseManageDCtrl'
                })


            //电子签章列表查询
            .state('elecSignList',
                {
                    url: '/' + path + '/elecSignList',
                    templateUrl: 'app/' + path + '/templates/tta/elecSign/elecSignList.html',
                    controllerUrl: '../app/' + path + '/templates/tta/elecSign/elecSignListCtrl'
                })

            //电子签章详情页面
            .state('elecSignDetail',
                {
                    url: '/' + path + '/elecSignDetail/:itemId',
                    templateUrl: 'app/' + path + '/templates/tta/elecSign/elecSignDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/elecSign/elecSignDetailCtrl'
                })


            //条款名目
            .state('clauseItemManage',
                {
                    url: '/' + path + '/clauseItemManage',
                    templateUrl: 'app/' + path + '/templates/tta/clauseItemManage/clauseItemManage.html',
                    controllerUrl: '../app/' + path + '/templates/tta/clauseItemManage/clauseItemManageCtrl'
                })
            //条款名目详情
            .state('clauseItemManageD',
                {
                    url: '/' + path + '/clauseItemManageD/:itemId',
                    templateUrl: 'app/' + path + '/templates/tta/clauseItemManage/clauseItemManageD.html',
                    controllerUrl: '../app/' + path + '/templates/tta/clauseItemManage/clauseItemManageDCtrl'
                })
            .state('departmentDetail',
                {
                    url: '/' + path + '/departmentDetail/:respId/:itemId',
                    templateUrl: 'app/' + path + '/templates/organizationStructure/departmentDetail.html',
                    controllerUrl: '../app/' + path + '/templates/organizationStructure/departmentDetailCtrl'
                })
            //新品种设置
            .state('newBreed',
                {
                    url: '/' + path + '/newBreed',
                    templateUrl: 'app/' + path + '/templates/tta/newBreed/newBreed.html',
                    controllerUrl: '../app/' + path + '/templates/tta/newBreed/newBreedCtrl'
                })
            //人员权限
            .state('userGroupDeptBrand',
                {
                    url: '/' + path + '/userGroupDeptBrand',
                    templateUrl: 'app/' + path + '/templates/tta/usergroupdeptbrand/userGroupDeptBrand.html',
                    controllerUrl: '../app/' + path + '/templates/tta/usergroupdeptbrand/userGroupDeptBrandCtrl'
                })
            //人员权限
            .state('userGroupDeptBrandSearch',
                {
                    url: '/' + path + '/userGroupDeptBrandSearch/:id',
                    templateUrl: 'app/' + path + '/templates/tta/usergroupdeptbrand/userGroupDeptBrandSearch.html',
                    controllerUrl: '../app/' + path + '/templates/tta/usergroupdeptbrand/userGroupDeptBrandSearchCtrl'
                })
            //新品种设置 新增，编辑
            .state('newBreedDetail',
                {
                    url: '/' + path + '/newBreedDetail/:itemId',
                    templateUrl: 'app/' + path + '/templates/tta/newBreed/newBreedD.html',
                    controllerUrl: '../app/' + path + '/templates/tta/newBreed/newBreedDCtrl'
                })
            //页面路径：预设管理 - 补充协议模板设置 - 标准模板字段定义设置
            //    功能：新增，编辑
            .state('ttaSaStdTplFieldCfg',
                {
                    url: '/' + path + '/ttaSaStdTplFieldCfg',
                    templateUrl: 'app/' + path + '/templates/tta/ttasastdtplfield/ttaSaStdTplFieldCfg.html',
                    controllerUrl: '../app/' + path + '/templates/tta/ttasastdtplfield/ttaSaStdTplFieldCfgCtrl'
                })
            //页面路径：预设管理 - 补充协议模板设置 - 标准模板设置
            //    功能：新增，删除，保存
            .state('ttaSaStdTplDefHeader',
                {
                    url: '/' + path + '/ttaSaStdTplDefHeader',
                    templateUrl: 'app/' + path + '/templates/tta/ttasastdtplfield/ttaSaStdTplDefHeader.html',
                    controllerUrl: '../app/' + path + '/templates/tta/ttasastdtplfield/ttaSaStdTplDefHeaderCtrl'
                })
            //页面路径：预设管理 - 标准模板设置
            //    功能：查询，修改，新增
            .state('ttaStdApplyHeader',
                {
                    url: '/' + path + '/ttaStdApplyHeader',
                    templateUrl: 'app/' + path + '/templates/tta/ttasastdtplfield/ttaStdApplyHeader.html',
                    controllerUrl: '../app/' + path + '/templates/tta/ttasastdtplfield/ttaStdApplyHeaderCtrl'
                })
            //页面路径：预设管理 - 标准模板设置
            //    功能：查询，修改，新增
            .state('ttaStdApplyHeaderD',
                {
                    url: '/' + path + '/ttaStdApplyHeaderD/:id',
                    templateUrl: 'app/' + path + '/templates/tta/ttasastdtplfield/ttaStdApplyHeaderD.html',
                    controllerUrl: '../app/' + path + '/templates/tta/ttasastdtplfield/ttaStdApplyHeaderDCtrl'
                })
            .state('ttaSaNonStandarHeader',
                {
                    url: '/' + path + '/ttaSaNonStandarHeader',
                    templateUrl: 'app/' + path + '/templates/tta/supplement/ttaSaNonStandarHeader.html',
                    controllerUrl: '../app/' + path + '/templates/tta/supplement/ttaSaNonStandarHeaderCtrl'
                })
            .state('ttaSaNonStandarHeaderD',
                {
                    url: '/' + path + '/ttaSaNonStandarHeaderD/:id',
                    templateUrl: 'app/' + path + '/templates/tta/supplement/ttaSaNonStandarHeaderD.html',
                    controllerUrl: '../app/' + path + '/templates/tta/supplement/ttaSaNonStandarHeaderDCtrl'
                })
            .state('ttaSoleNonStandarHeader',
                {
                    url: '/' + path + '/ttaSoleNonStandarHeader',
                    templateUrl: 'app/' + path + '/templates/tta/exclusive/ttaSoleNonStandarHeader.html',
                    controllerUrl: '../app/' + path + '/templates/tta/exclusive/ttaSoleNonStandarHeaderCtrl'
                })
            .state('ttaSoleNonStandarHeaderD',
                {
                    url: '/' + path + '/ttaSoleNonStandarHeaderD/:id',
                    templateUrl: 'app/' + path + '/templates/tta/exclusive/ttaSoleNonStandarHeaderD.html',
                    controllerUrl: '../app/' + path + '/templates/tta/exclusive/ttaSoleNonStandarHeaderDCtrl'
                })
            //职务列表
            .state('job',
                {
                    url: '/' + path + '/job',
                    templateUrl: 'app/' + path + '/templates/organizationStructure/job.html',
                    controllerUrl: '../app/' + path + '/templates/organizationStructure/jobCtrl'
                })
            //新增、编辑职务
            .state('jobDetail',
                {
                    url: '/' + path + '/jobDetail/:respId/:itemId',
                    templateUrl: 'app/' + path + '/templates/organizationStructure/jobDetail.html',
                    controllerUrl: '../app/' + path + '/templates/organizationStructure/jobDetailCtrl'
                })
            //职位列表
            .state('position',
                {
                    url: '/' + path + '/position',
                    templateUrl: 'app/' + path + '/templates/organizationStructure/position.html',
                    controllerUrl: '../app/' + path + '/templates/organizationStructure/positionCtrl'
                })
            //新增、编辑职位
            .state('positionDetail',
                {
                    url: '/' + path + '/positionDetail/:respId/:orgId/:itemId',
                    templateUrl: 'app/' + path + '/templates/organizationStructure/positionDetail.html',
                    controllerUrl: '../app/' + path + '/templates/organizationStructure/positionDetailCtrl'
                })
            //员工查询
            .state('staffInquiry',
                {
                    url: '/' + path + '/staffInquiry',
                    templateUrl: 'app/' + path + '/templates/organizationStructure/staffInquiry.html',
                    controllerUrl: '../app/' + path + '/templates/organizationStructure/staffInquiryCtrl'
                })
            //职位层级
            .state('positionLevel',
                {
                    url: '/' + path + '/positionLevel',
                    templateUrl: 'app/' + path + '/templates/organizationStructure/positionLevel.html',
                    controllerUrl: '../app/' + path + '/templates/organizationStructure/positionLevelCtrl'
                })
            //新增、编辑职位层级
            .state('positionLevelDetail',
                {
                    url: '/' + path + '/positionLevelDetail/:respId/:itemId',
                    templateUrl: 'app/' + path + '/templates/organizationStructure/positionLevelDetail.html',
                    controllerUrl: '../app/' + path + '/templates/organizationStructure/positionLevelDetailCtrl'
                })



            //职位分配
            .state('positionAllot',
                {
                    url: '/' + path + '/positionAllot',
                    templateUrl: 'app/' + path + '/templates/organizationStructure/positionAllot.html',
                    controllerUrl: '../app/' + path + '/templates/organizationStructure/positionAllotCtrl'
                })
            //职位分配新增
            .state('positionAllotAdd',
                {
                    url: '/' + path + '/positionAllotAdd/:respId/:ouId/:itemId',
                    templateUrl: 'app/' + path + '/templates/organizationStructure/positionAllotAdd.html',
                    controllerUrl: '../app/' + path + '/templates/organizationStructure/positionAllotAddCtrl'
                })
            .state('positionLevelTree',
                {
                    stateName: '职位层级树',
                    url: '/' + path + '/positionLevelTree',
                    templateUrl: 'app/' + path + '/templates/organizationStructure/positionLevelTree.html',
                    controllerUrl: '../app/' + path + '/templates/organizationStructure/positionLevelTreeCtrl'
                })


            /*阳杰 start*/
            //消息源
            .state('msgSourceCfg',
                {
                    url: '/' + path + '/msgSourceCfg',
                    templateUrl: 'app/' + path + '/templates/messageCenter/msgSourceCfg.html',
                    controllerUrl: '../app/' + path + '/templates/messageCenter/msgSourceCfgCtrl'
                })
            //消息模版
            .state('msgTempleCfg',
                {
                    url: '/' + path + '/msgTempleCfg',
                    templateUrl: 'app/' + path + '/templates/messageCenter/msgTempleCfg.html',
                    controllerUrl: '../app/' + path + '/templates/messageCenter/msgTempleCfgCtrl'
                })
            //消息配置
            .state('msgCfg',
                {
                    url: '/' + path + '/msgCfg',
                    templateUrl: 'app/' + path + '/templates/messageCenter/msgCfg.html',
                    controllerUrl: '../app/' + path + '/templates/messageCenter/msgCfgCtrl'
                })
            //消息实例
            .state('msgInstance',
                {
                    url: '/' + path + '/msgInstance',
                    templateUrl: 'app/' + path + '/templates/messageCenter/msgInstance.html',
                    controllerUrl: '../app/' + path + '/templates/messageCenter/msgInstanceCtrl'
                })
            //消息历史
            .state('msgHistory',
                {
                    url: '/' + path + '/msgHistory',
                    templateUrl: 'app/' + path + '/templates/messageCenter/msgHistory.html',
                    controllerUrl: '../app/' + path + '/templates/messageCenter/msgHistoryCtrl'
                })
            //消息接收对象
            .state('msgReceive',
                {
                    url: '/' + path + '/msgReceive',
                    templateUrl: 'app/' + path + '/templates/messageCenter/msgReceive.html',
                    controllerUrl: '../app/' + path + '/templates/messageCenter/msgReceiveCtrl'
                })
            //消息退订
            .state('msgTd',
                {
                    url: '/' + path + '/msgTd',
                    templateUrl: 'app/' + path + '/templates/messageCenter/msgTd.html',
                    controllerUrl: '../app/' + path + '/templates/messageCenter/msgTdCtrl'
                })
            //消息退订
            .state('msgUser',
                {
                    url: '/' + path + '/msgUser',
                    templateUrl: 'app/' + path + '/templates/messageCenter/msgUser.html',
                    controllerUrl: '../app/' + path + '/templates/messageCenter/msgUserCtrl'
                })
            //消息日志
            .state('msgLog',
                {
                    url: '/' + path + '/msgLog',
                    templateUrl: 'app/' + path + '/templates/messageCenter/msgLog.html',
                    controllerUrl: '../app/' + path + '/templates/messageCenter/msgLogCtrl'
                })
            // 查看详情
            .state('msgInstanceDetail',
                {
                    url: '/' + path + '/msgInstanceDetail/:msgInstanceId',
                    templateUrl: 'app/' + path + '/templates/messageCenter/msgInstanceDetail.html',
                    controllerUrl: '../app/' + path + '/templates/messageCenter/msgInstanceDetailCtrl'
                })

            /*阳杰 end*/

            // 业务线维护
            .state('business',
                {
                    url: '/' + path + '/business',
                    templateUrl: 'app/' + path + '/templates/ruleEngine/business.html',
                    controllerUrl: '../app/' + path + '/templates/ruleEngine/businessCtrl'
                })
            // 表达式设置
            .state('expression',
                {

                    url: '/' + path + '/expression',
                    templateUrl: 'app/' + path + '/templates/ruleEngine/expression.html',
                    controllerUrl: '../app/' + path + '/templates/ruleEngine/expressionCtrl'
                })

            // 执行结果
            .state('doResult',
                {
                    url: '/' + path + '/doResult',
                    templateUrl: 'app/' + path + '/templates/ruleEngine/doResult.html',
                    controllerUrl: '../app/' + path + '/templates/ruleEngine/doResultCtrl'
                })
            // 服务查询
            .state('service',
                {
                    url: '/' + path + '/service',
                    templateUrl: 'app/' + path + '/templates/ruleEngine/service.html',
                    controllerUrl: '../app/' + path + '/templates/ruleEngine/serviceCtrl'
                })
            // 模板设置
            .state('model',
                {
                    url: '/' + path + '/model',
                    templateUrl: 'app/' + path + '/templates/ruleEngine/model.html',
                    controllerUrl: '../app/' + path + '/templates/ruleEngine/modelCtrl'
                })
            // 生成页面
            .state('buildPage',
                {
                    url: '/' + path + '/buildPage',
                    templateUrl: 'app/' + path + '/templates/ruleEngine/buildPage.html',
                    controllerUrl: '../app/' + path + '/templates/ruleEngine/buildPageCtrl'
                })



            //供应商信息-BIC
            .state('supplierCente',
                {
                    url: '/' + path + '/supplierCente',
                    templateUrl: 'app/' + path + '/templates/tta/supplier/supplierCenter.html',
                    controllerUrl: '../app/' + path + '/templates/tta/supplier/supplierCenterCtrl'
                })
            //供应商详情-BIC
            .state('supplierDetail',
                {
                    url: '/' + path + '/supplierDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/supplier/supplierDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/supplier/supplierDetailCtrl'
                })


            //供应商信息-采购
            .state('purchaseSupplierCenter',
                {
                    url: '/' + path + '/purchaseSupplierCenter',
                    templateUrl: 'app/' + path + '/templates/tta/supplier/purchaseSupplierCenter.html',
                    controllerUrl: '../app/' + path + '/templates/tta/supplier/purchaseSupplierCenterCtrl'
                })

            //供应商详情-采购
            .state('purchaseSupplierDetail',
                {
                    url: '/' + path + '/purchaseSupplierDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/supplier/purchaseSupplierDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/supplier/purchaseSupplierDetailCtrl'
                })
            //不在ttaSystem的品牌信息
            .state('noBrandOutStandList',
                {
                    url: '/' + path + '/noBrandOutStandList',
                    templateUrl: 'app/' + path + '/templates/tta/supplier/ttaSystemNoBrandOutStandList.html',
                    controllerUrl: '../app/' + path + '/templates/tta/supplier/ttaSystemNoBrandOutStandListCtrl'
                })


            //商品信息
            .state('itemCente',
                {
                    url: '/' + path + '/itemCente',
                    templateUrl: 'app/' + path + '/templates/tta/item/itemCenter.html',
                    controllerUrl: '../app/' + path + '/templates/tta/item/itemCenterCtrl'
                })
            //商品详情
            .state('itemDetail',
                {
                    url: '/' + path + '/itemDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/item/itemDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/item/itemDetailCtrl'
                })
            //termsAnalysis
            .state('termsAnalysis',
                {
                    url: '/' + path + '/termsAnalysis',
                    templateUrl: 'app/' + path + '/templates/tta/contract/termsAnalysis.html',
                    controllerUrl: '../app/' + path + '/templates/tta/contract/termsAnalysisCtrl'
                })
            //报表管理Promotion Guideline
            .state('promotionGuideline',
                {
                    url: '/' + path + '/promotionGuideline',
                    templateUrl: 'app/' + path + '/templates/tta/report/promotionGuideline.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/promotionGuidelineCtrl'
                })

            .state('CWcheckingreport',
                {
                    url: '/' + path + '/CWcheckingreport/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaCWCheckingReport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaCWCheckingReportCtrl'
                })

            //DM
            .state('DMcheckingreport',
                {
                    url: '/' + path + '/DMcheckingreport/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaDMCheckingReport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaDMCheckingReportCtrl'
                })

            .state('processDMcheckingReport',
                {
                    url: '/' + path + '/processDMcheckingReport/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaProcessDMCheckingReport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaProcessDMCheckingReportCtrl'
                })
            .state('processOSDcheckingReport',
                {
                    url: '/' + path + '/processOSDcheckingReport/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaProcessOSDCheckingReport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaProcessOSDCheckingReportCtrl'
                })
            .state('processCWcheckingReport',
                {
                    url: '/' + path + '/processCWcheckingReport/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaProcessCWCheckingReport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaProcessCWCheckingReportCtrl'
                })
            .state('processHWBcheckingReport',
                {
                    url: '/' + path + '/processHWBcheckingReport/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaProcessHWBCheckingReport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaProcessHWBCheckingReportCtrl'
                })
            .state('processNPPcheckingReport',
                {
                    url: '/' + path + '/processNPPcheckingReport/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaProcessNPPcheckingReport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaProcessNPPcheckingReportCtrl'
                })

            .state('CWcheckingreport2',
                {
                    url: '/' + path + '/CWcheckingreport2/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaCWCheckingReport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaCWCheckingReportCtrl'
                })

            .state('salesSite',
                {
                    url: '/' + path + '/salesSite',
                    templateUrl: 'app/' + path + '/templates/tta/report/salesSite.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/salesSiteCtrl'
                })

            .state('monthlyChecking',
                {
                    url: '/' + path + '/monthlyChecking',
                    templateUrl: 'app/' + path + '/templates/tta/report/monthlyChecking.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/monthlyCheckingCtrl'
                })

            .state('monthlyChecking2',
                {
                    url: '/' + path + '/monthlyChecking2/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/monthlyChecking.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/monthlyCheckingCtrl'
                })

            .state('freeGoods',
                {
                    url: '/' + path + '/freeGoods',
                    templateUrl: 'app/' + path + '/templates/tta/report/freeGoods.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/freeGoodsCtrl'
                })
            .state('freeGoods2',
                {
                    url: '/' + path + '/freeGoods2/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/freeGoods.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/freeGoodsCtrl'
                })
                //Npp新品报表
            .state('NppNewProduct',
                {
                    url: '/' + path + '/NppNewProduct/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaNppNewProductReport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaNppNewProductReportLineCtrl'
                })

            .state('irTerms2',
                {
                    url: '/' + path + '/irTerms2/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/irTerms.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/irTermsCtrl'
                })

            .state('irTerms',
                {
                    url: '/' + path + '/irTerms',
                    templateUrl: 'app/' + path + '/templates/tta/report/irTerms.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/irTermsCtrl'
                })

            //报表管理 ttaOiBillImport
            .state('ttaOiBillLine',
                {
                    url: '/' + path + '/ttaOiBillLine',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaOiBillLine.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaOiBillLineCtrl'
                })
            //导入查询 ttaBeoiBillLine
            .state('ttaBeoiBillLine',
                {
                    url: '/' + path + '/ttaBeoiBillLine',
                    templateUrl: 'app/' + path + '/templates/tta/oiImport/ttaBeoiBillLine.html',
                    controllerUrl: '../app/' + path + '/templates/tta/oiImport/ttaBeoiBillLineCtrl'
                })
            //导入查询 ttaAboiBillLine
            //导入查询 ttaAboiSummary
            .state('ttaAboiSummary',
                {
                    url: '/' + path + '/ttaAboiSummary',
                    templateUrl: 'app/' + path + '/templates/tta/oiImport/ttaAboiSummary.html',
                    controllerUrl: '../app/' + path + '/templates/tta/oiImport/ttaAboiSummaryCtrl'
                })
            //导入查询 ttaSroiBillLine
            .state('ttaAboiBillLine',
                {
                    url: '/' + path + '/ttaAboiBillLine',
                    templateUrl: 'app/' + path + '/templates/tta/oiImport/ttaABOIimport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/oiImport/ttaABOIimportCtrl'
                })
            //导入查询 ttaEroiBillLine
            .state('ttaSupplierItemStore',
                {
                    url: '/' + path + '/ttaSupplierItemStore',
                    templateUrl: 'app/' + path + '/templates/tta/oiImport/ttaSupplierItemStore.html',
                    controllerUrl: '../app/' + path + '/templates/tta/oiImport/ttaSupplierItemStoreCtrl'
                })

            //导入查询 ttaEroiBillLine
            .state('ttaEroiBillLine',
                {
                    url: '/' + path + '/ttaEroiBillLine',
                    templateUrl: 'app/' + path + '/templates/tta/oiImport/ttaTesteroiLine.html',
                    controllerUrl: '../app/' + path + '/templates/tta/oiImport/ttaTesteroiLineCtrl'
                })
            //导入查询 ttaSroiBillLine
            .state('ttaSroiBillLine',
                {
                    url: '/' + path + '/ttaSroiBillLine',
                    templateUrl: 'app/' + path + '/templates/tta/oiImport/ttaSroiBillLine.html',
                    controllerUrl: '../app/' + path + '/templates/tta/oiImport/ttaSroiBillLineCtrl'
                })
            //导入查询 ttaOiSummaryLine
            .state('ttaOiSummaryLine',
                {
                    url: '/' + path + '/ttaOiSummaryLine',
                    templateUrl: 'app/' + path + '/templates/tta/oiImport/ttaOiSummaryLine.html',
                    controllerUrl: '../app/' + path + '/templates/tta/oiImport/ttaOiSummaryLineCtrl'
                })
            //导入查询 ttaOiTax
            .state('ttaOiTax',
                {
                    url: '/' + path + '/ttaOiTax',
                    templateUrl: 'app/' + path + '/templates/tta/oiImport/ttaOiTax.html',
                    controllerUrl: '../app/' + path + '/templates/tta/oiImport/ttaOiTaxCtrl'
                })
            //导入查询 ttaTermsAcCount
            .state('ttaTermsAcCount',
                {
                    url: '/' + path + '/ttaTermsAcCount',
                    templateUrl: 'app/' + path + '/templates/tta/oiImport/ttaTermsAcCount.html',
                    controllerUrl: '../app/' + path + '/templates/tta/oiImport/ttaTermsAcCountCtrl'
                })
            //导入查询 ttaTradeCalendar
            .state('ttaTradeCalendar',
                {
                    url: '/' + path + '/ttaTradeCalendar',
                    templateUrl: 'app/' + path + '/templates/tta/oiImport/ttaTradeCalendar.html',
                    controllerUrl: '../app/' + path + '/templates/tta/oiImport/ttaTradeCalendarCtrl'
                })
            //报表管理 ttaNppNewProductLine
            .state('ttaNppNewProductLine',
                {
                    url: '/' + path + '/ttaNppNewProductLine',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaNppNewProductReport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaNppNewProductReportLineCtrl'
                })

            //报表管理 ttaSystemCurrentLine
            .state('ttaSystemCurrentLine',
                {
                    url: '/' + path + '/ttaSystemCurrentLine',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaSystemCurrentLine.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaSystemCurrentLineCtrl'
                })
            //报表管理 ttaFreeGoodsPolist
            .state('ttaFreeGoodsPolist',
                {
                    url: '/' + path + '/ttaFreeGoodsPolist',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaFreeGoodsPolist.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaFreeGoodsPolistCtrl'
                })


            //报表管理 ttaHwbBaseLine
            .state('ttaHwbBaseLine',
                {
                    url: '/' + path + '/ttaHwbBaseLine',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaHwbBaseLine.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaHwbBaseLineCtrl'
                })

            //报表管理 hwbSittingPlan
            .state('hwbSittingPlan',
                {
                    url: '/' + path + '/hwbSittingPlan',
                    templateUrl: 'app/' + path + '/templates/tta/report/hwbSittingPlan.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/hwbSittingPlanCtrl'
                })

            //报表管理 hwbSittingPlan
            .state('ttaHwbChecking',
                {
                    url: '/' + path + '/ttaHwbChecking/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaHwbChecking.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaHwbCheckingCtrl'
                })

            //报表管理 hwbSittingPlan
            .state('hwbAttendanceFee2',
                {
                    url: '/' + path + '/hwbAttendanceFee2/:batchId',
                    templateUrl: 'app/' + path + '/templates/tta/report/hwbAttendanceFee.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/hwbAttendanceFeeCtrl'
                })

            //报表头管理
            .state('reportHeader',
                {
                    url: '/' + path + '/reportHeader/:reportType',
                    templateUrl: 'app/' + path + '/templates/tta/report/reportHeader.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/reportHeaderCtrl'
                })

            //流程报表头管理
            .state('processReportHeader',
                {
                    url: '/' + path + '/processReportHeader/:reportType',
                    templateUrl: 'app/' + path + '/templates/tta/report/processReportHeader.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/processReportHeaderCtrl'
                })

            //报表头审批管理
            .state('reportProcessHeader',
                {
                    url: '/' + path + '/reportProcessHeader',
                    templateUrl: 'app/' + path + '/templates/tta/report/reportProcessHeader.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/reportProcessHeaderCtrl'
                })
            //报表头审批详情
            .state('reportProcessHeaderD',
                {
                    url: '/' + path + '/reportProcessHeaderD/:itemId',
                    templateUrl: 'app/' + path + '/templates/tta/report/reportProcessHeaderD.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/reportProcessHeaderDCtrl'
                })
            //OSD_BASE_LINE
            .state('ttaOsdBaseLine',
                {
                    url: '/' + path + '/ttaOsdBaseLine',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaOsdBaseLine.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaOsdBaseLineCtrl'
                })
            //报表管理 ttaPogSpaceLine
            .state('ttaPogSpaceLine',
                {
                    url: '/' + path + '/ttaPogSpaceLine',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaPogSpaceLine.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaPogSpaceLineCtrl'
                })
            //报表管理 ttaDmFullLine
            .state('ttaDmFullLine',
                {
                    url: '/' + path + '/ttaDmFullLine',
                    templateUrl: 'app/' + path + '/templates/tta/report/ttaDmFullLine.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/ttaDmFullLineCtrl'
                })
            //报表管理 TTA Proposal Status
            .state('proposalStatus',
                {
                    url: '/' + path + '/proposalStatus',
                    templateUrl: 'app/' + path + '/templates/tta/report/proposal/ttaProposalStatus.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/proposal/ttaProposalStatusCtrl'
                })

            //报表管理 TTA contract Status
            .state('contractStatus',
                {
                    url: '/' + path + '/contractStatus',
                    templateUrl: 'app/' + path + '/templates/tta/report/contract/ttaContractStatus.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/contract/ttaContractStatusCtrl'
                })


            //业务合同书流程进度
            .state('contractProcess',
                {
                    url: '/' + path + '/contractProcess',
                    templateUrl: 'app/' + path + '/templates/tta/report/process/ttaContractProcess.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/process/ttaContractProcessCtrl'
                })

            //报表管理 ttaPromotionoalLeafletDM
            .state('ttaPromotionoalLeafletDM',
                {
                    url: '/' + path + '/ttaPromotionoalLeafletDM',
                    templateUrl: 'app/' + path + '/templates/tta/report/promotionalleaflet/promotionoalLeafletDM.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/promotionalleaflet/promotionoalLeafletDMCtrl'
                })
            //合同信息
            .state('contractCente',
                {
                    url: '/' + path + '/contractCente',
                    templateUrl: 'app/' + path + '/templates/tta/contract/contractCenter.html',
                    controllerUrl: '../app/' + path + '/templates/tta/contract/contractCenterCtrl'
                })
            //新增合同详情
            .state('contractAddDetail',
                {
                    url: '/' + path + '/contractAddDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/contract/contractAddDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/contract/contractAddDetailCtrl'
                })

            //修改合同详情
            .state('contractUpdateDetail',
                {
                    url: '/' + path + '/contractUpdateDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/contract/contractUpdateDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/contract/contractUpdateDetailCtrl'
                })

            .state('contractTermsDetail',
                {
                    url: '/' + path + '/contractTermsDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/contract/contractTerms.html',
                    controllerUrl: '../app/' + path + '/templates/tta/contract/contractTermsCtrl'
                })
            .state('contractTermsPdf',
                {
                    url: '/' + path + '/contractTermsPdf/:id',
                    templateUrl: 'app/' + path + '/templates/tta/contract/contractTermsPdf.html',
                    controllerUrl: '../app/' + path + '/templates/tta/contract/contractTermsPdfCtrl'
                })
            //部门协定收费标准设置信息
            .state('feedeptCente',
                {
                    url: '/' + path + '/feedeptCente',
                    templateUrl: 'app/' + path + '/templates/tta/feedept/feedeptCenter.html',
                    controllerUrl: '../app/' + path + '/templates/tta/feedept/feedeptCenterCtrl'
                })
            //部门协定收费标准设置详情
            .state('feedeptDetail',
                {
                    url: '/' + path + '/feedeptDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/feedept/feedeptDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/feedept/feedeptDetailCtrl'
                })


            //proposal信息
            .state('proposalCente',
                {
                    url: '/' + path + '/proposalCente',
                    templateUrl: 'app/' + path + '/templates/tta/proposal/proposalCenter.html',
                    controllerUrl: '../app/' + path + '/templates/tta/proposal/proposalCenterCtrl'
                })
            //部门协定收费标准设置详情
            .state('proposalDetail',
                {
                    url: '/' + path + '/proposalDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/proposal/proposalDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/proposal/proposalDetailCtrl'
                })
            .state('proposalDetailPDF',
                {
                    url: '/' + path + '/proposalDetailPDF/:id',
                    templateUrl: 'app/' + path + '/templates/tta/proposal/proposalDetailPDF.html',
                    controllerUrl: '../app/' + path + '/templates/tta/proposal/proposalDetailCtrlPDF'
                })
            .state('analysisInit',
            {
                url: '/' + path + '/analysisInit/:id',
                templateUrl: 'app/' + path + '/templates/tta/proposal/analysisInit.html',
                controllerUrl: '../app/' + path + '/templates/tta/proposal/analysisInitCtrl'
            })
            .state('ttaContractSpecialHeader',
                {
                    url: '/' + path + '/ttaContractSpecialHeader',
                    templateUrl: 'app/' + path + '/templates/tta/contract/ttaContractSpecialHeader.html',
                    controllerUrl: '../app/' + path + '/templates/tta/contract/ttaContractSpecialHeaderCtrl'
                })
            .state('ttaContractSpecialHeaderD',
                {
                    url: '/' + path + '/ttaContractSpecialHeaderD/:id',
                    templateUrl: 'app/' + path + '/templates/tta/contract/ttaContractSpecialHeaderD.html',
                    controllerUrl: '../app/' + path + '/templates/tta/contract/ttaContractSpecialHeaderDCtrl'
                })

            .state('ttaContractRecordHeader',
                {
                    url: '/' + path + '/ttaContractRecordHeader',
                    templateUrl: 'app/' + path + '/templates/tta/contract/ttaContractRecordHeader.html',
                    controllerUrl: '../app/' + path + '/templates/tta/contract/ttaContractRecordHeaderCtrl'
                })


            //proposal部门协定标准设置详情
            .state('deptFeeDetail',
                {
                    url: '/' + path + '/deptFeeDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/proposal/deptFeeDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/proposal/deptFeeDetailCtrl'
                })




            //模板参数列表信息
            .state('paramsManage',
                {
                    url: '/' + path + '/paramsManage',
                    templateUrl: 'app/' + path + '/templates/tta/params/paramsManagementList.html',
                    controllerUrl: '../app/' + path + '/templates/tta/params/paramsManagementListCtrl'
                })
            //模板参数详情
            .state('paramsManageDetail',
                {
                    url: '/' + path + '/paramsManageDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/params/paramsManagementDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/params/paramsManagementDetailCtrl'
                })


            //模板参数列表信息
            .state('ruleManage',
                {
                    url: '/' + path + '/ruleManage',
                    templateUrl: 'app/' + path + '/templates/tta/rule/ruleList.html',
                    controllerUrl: '../app/' + path + '/templates/tta/rule/ruleListCtrl'
                })
            //模板参数详情
            .state('ruleManageDetail',
                {
                    url: '/' + path + '/ruleManageDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/rule/ruleDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/rule/ruleDetailCtrl'
                })

            //实际发生费用分摊规则设置列表信息
            .state('costManage',
                {
                    url: '/' + path + '/costManage',
                    templateUrl: 'app/' + path + '/templates/tta/cost/costList.html',
                    controllerUrl: '../app/' + path + '/templates/tta/cost/costListCtrl'
                })


            // 调查问卷 start
            .state(
                'saafQuestionnaire',
                {
                    stateName: '问卷调查模板列表',
                    buttonUrl: '/saafQuestionnaire',
                    url: '/' + path + '/saafQuestionnaire',
                    templateUrl: 'app/' + path + '/templates/tta/questionnaire/saafQuestionnaire.html',
                    controllerUrl: '../app/' + path + '/templates/tta/questionnaire/saafQuestionnaireCtrl'
                })

            .state(
                'saafQuestionnaireDetail',
                {
                    stateName: '题库编辑',
                    buttonUrl: '/saafQuestionnaire',
                    //url: '/' + path + '/saafQuestionnaireDetail/:allowUpdate/:qnHeadId',
                    url: '/' + path + '/saafQuestionnaireDetail',
                    templateUrl: 'app/' + path + '/templates/tta/questionnaire/saafQuestionnaireDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/questionnaire/saafQuestionnaireDetailCtrl'
                })

            .state(
                'questionList',
                {
                    stateName: '题库列表查询',
                    buttonUrl: '/questionList',
                    url: '/' + path + '/questionList',
                    templateUrl: 'app/' + path + '/templates/tta/question/questionList.html',
                    controllerUrl: '../app/' + path + '/templates/tta/question/questionListCtrl'
                })


            .state('editQuestionDetail',
                {
                    url: '/' + path + '/editQuestionDetail/:qHeaderId',
                    stateName: '题库编辑',
                    templateUrl: 'app/' + path + '/templates/tta/question/editQuestionDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/question/editQuestionDetailCtrl'
                })

            .state(
                'saafQuestionnairePublish',
                {
                    stateName: '问卷调查发布',
                    buttonUrl: '/saafQuestionnairePublish',
                    url: '/' + path + '/saafQuestionnairePublish',
                    templateUrl: 'app/' + path + '/templates/tta/questionnaire/saafQuestionnairePublish.html',
                    controllerUrl: '../app/' + path + '/templates/tta/questionnaire/saafQuestionnairePublishCtrl'
                })

            .state(
                'saafQuestionnaireStatus',
                {
                    stateName: '问卷调查审批',
                    buttonUrl: '/saafQuestionnaire',
                    url: '/' + path + '/saafQuestionnaireStatus/:allowUpdate/:surveyHeadId',
                    templateUrl: 'app/' + path + '/templates/tta/questionnaire/saafQuestionnaireStatus.html',
                    controllerUrl: '../app/' + path + '/templates/tta/questionnaire/saafQuestionnaireStatusCtrl'
                })

            .state(
                'saafQuestionnaireReport',
                {
                    stateName: '问卷调查报表',
                    buttonUrl: '/saafQuestionnaireReport',
                    url: '/' + path + '/saafQuestionnaireReport/:publishId',
                    templateUrl: 'app/' + path + '/templates/tta/questionnaire/saafQuestionnaireReport.html',
                    controllerUrl: '../app/' + path + '/templates/tta/questionnaire/saafQuestionnaireReportCtrl'
                })

            .state(
                'saafQuestionnairePublishStatus',
                {
                    stateName: '发布问卷调查审批',
                    url: '/' + path + '/saafQuestionnairePublishStatus/:allowUpdate/:publishId',
                    templateUrl: 'app/' + path + '/templates/tta/questionnaire/saafQuestionnairePublishStatus.html',
                    controllerUrl: '../app/' + path + '/templates/tta/questionnaire/saafQuestionnairePublishStatusCtrl'
                })

            .state(
                'saafQuestionnaireList',
                {
                    stateName: '问卷调查列表',
                    buttonUrl: '/saafQuestionnaireList',
                    url: '/' + path + '/saafQuestionnaireList/:statusFlag',
                    templateUrl: 'app/' + path + '/templates/tta/questionnaire/saafQuestionnaireList.html',
                    controllerUrl: '../app/' + path + '/templates/tta/questionnaire/saafQuestionnaireListCtrl'
                })

            .state(
                'saafQuestionnaireView',
                {
                    stateName: '问卷调查信息',
                    buttonUrl: '/saafQuestionnaireView',
                    url: '/' + path + '/saafQuestionnaireView/:allowUpdate/:publishId',
                    templateUrl: 'app/' + path + '/templates/tta/questionnaire/saafQuestionnaireView.html',
                    controllerUrl: '../app/' + path + '/templates/tta/questionnaire/saafQuestionnaireViewCtrl'
                })
            .state(
                'saafTestQuestionnaireH',
                {
                    stateName: '问卷列表',
                    buttonUrl: '/saafTestQuestionnaireH',
                    url: '/' + path + '/saafTestQuestionnaireH',
                    templateUrl: 'app/' + path + '/templates/tta/questionnaire/saafTestQuestionnaireH.html',
                    controllerUrl: '../app/' + path + '/templates/tta/questionnaire/saafTestQuestionnaireHCtrl'
                })

            .state(
                'saafTestQuestionnaireL',
                {
                    stateName: '问卷调查模板编辑',
                    buttonUrl: '/saafTestQuestionnaireL',
                    url: '/' + path + '/saafTestQuestionnaireL/:allowUpdate/:testQnHeadId',
                    templateUrl: 'app/' + path + '/templates/tta/questionnaire/saafTestQuestionnaireL.html',
                    controllerUrl: '../app/' + path + '/templates/tta/questionnaire/saafTestQuestionnaireLCtrl'
                })
            // 调查问卷 end

            .state(
                'withdrawalList',
                {
                    stateName: '拆分与合并列表',
                    buttonUrl: '/withdrawalList',
                    url: '/' + path + '/withdrawalList',
                    templateUrl: 'app/' + path + '/templates/tta/withdrawal/withdrawalList.html',
                    controllerUrl: '../app/' + path + '/templates/tta/withdrawal/withdrawalListCtrl'
                })

            .state(
                'withdrawalDetail',
                {
                    stateName: '拆分与合并编辑',
                    buttonUrl: '/withdrawalDetail',
                    url: '/' + path + '/withdrawalDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/withdrawal/withdrawalDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/withdrawal/withdrawalDetailCtrl'
                })
            .state(
                'supplement',
                {
                    stateName: '补充协议列表',
                    buttonUrl: '/supplement',
                    url: '/' + path + '/supplement',
                    templateUrl: 'app/' + path + '/templates/tta/supplement/supplementList.html',
                    controllerUrl: '../app/' + path + '/templates/tta/supplement/supplementListCtrl'
                })

            .state(
                'supplementDetail',
                {
                    stateName: '补充协议',
                    buttonUrl: '/supplementDetail',
                    url: '/' + path + '/supplementDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/supplement/supplementDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/supplement/supplementDetailCtrl'
                })
            .state(
                'supplementAgreementStandar',
                {
                    stateName: '补充协议标准列表',
                    buttonUrl: '/supplementAgreementStandar',
                    url: '/' + path + '/supplementAgreementStandar',
                    templateUrl: 'app/' + path + '/templates/tta/supplement/supplementAgreementStandardList.html',
                    controllerUrl: '../app/' + path + '/templates/tta/supplement/supplementAgreementStandardListCtrl'
                })
            .state(
                'supplementAgreementStandarDetail',
                {
                    stateName: '补充协议标准详情',
                    buttonUrl: '/supplementAgreementStandarDetail',
                    url: '/' + path + '/supplementAgreementStandarDetail/:id',
                    templateUrl: 'app/' + path + '/templates/tta/supplement/supplementAgreementStandardDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/supplement/supplementAgreementStandardDetailCtrl'
                })
            .state(
                'rule',
                {
                    stateName: '规则管理',
                    buttonUrl: '/rule',
                    url: '/' + path + '/rule',
                    templateUrl: 'app/' + path + '/templates/tta/questionnaire/rule.html',
                    controllerUrl: '../app/' + path + '/templates/tta/questionnaire/ruleCtrl'
                })

            .state(
                'exclusive',
                {
                    stateName: '独家协议',
                    buttonUrl: '/exclusive',
                    url: '/' + path + '/exclusive/:type',
                    templateUrl: 'app/' + path + '/templates/tta/exclusive/exclusiveList.html',
                    controllerUrl: '../app/' + path + '/templates/tta/exclusive/exclusiveListCtrl'
                })

            .state(
                'exclusiveDetail',
                {
                    stateName: '独家协议详情',
                    buttonUrl: '/exclusiveDetail',
                    url: '/' + path + '/exclusiveDetail/:id/:type',
                    templateUrl: 'app/' + path + '/templates/tta/exclusive/exclusiveDetail.html',
                    controllerUrl: '../app/' + path + '/templates/tta/exclusive/exclusiveDetailCtrl'
                })
            .state(
                'ttaConAttrLineFile',
                {
                    stateName: '部门附件',
                    buttonUrl: '/ttaConAttrLineFile',
                    url: '/' + path + '/ttaConAttrLineFile',
                    templateUrl: 'app/' + path + '/templates/tta/dept/ttaConAttrLineFile.html',
                    controllerUrl: '../app/' + path + '/templates/tta/dept/ttaConAttrLineFileCtrl'
                })
            //规则
            .state('baseRule',
                {
                    url: '/' + path + '/baseRule',
                    templateUrl: 'app/' + path + '/templates/tta/rule/rule.html',
                    controllerUrl: '../app/' + path + '/templates/tta/rule/ruleCtrl'
                })


            /* OI拆分字段配置*/
            .state('fieldConfig',
                {
                    url: '/' + path + '/fieldConfig',
                    templateUrl: 'app/' + path + '/templates/tta/fieldconfig/fieldConfig.html',
                    controllerUrl: '../app/' + path + '/templates/tta/fieldconfig/fieldConfigCtrl'
                })

            /* TTA Proposal Summary 流程报表*/
            .state('proposalSummary',
                {
                    url: '/' + path + '/proposalSummary',
                    templateUrl: 'app/' + path + '/templates/tta/report/proposalSummary.html',
                    controllerUrl: '../app/' + path + '/templates/tta/report/proposalSummaryCtrl'
                })

            //PLM-OB项目管理
            .state('plmProjectInfo',
                {
                    stateName: 'OB项目查询',
                    buttonUrl: '/plmProjectInfo',
                    url: '/' + path + '/plmProjectInfo',
                    templateUrl: 'app/' + path + '/templates/ob/project/plmProjectInfo.html',
                    controllerUrl: '../app/' + path + '/templates/ob/project/plmProjectInfoCtrl'
                })

            //PLM-OB项目异常查询
            .state('projectExceptionReport',
                {
                    stateName: 'OB项目异常查询',
                    buttonUrl: '/projectExceptionReport',
                    url: '/' + path + '/projectExceptionReport',
                    templateUrl: 'app/' + path + '/templates/ob/project/projectExceptionReport.html',
                    controllerUrl: '../app/' + path + '/templates/ob/project/projectExceptionReportCtrl'
                })

            //PLM—OB项目详情
            .state('plmProjectDetail',
                {
                    stateName: 'OB项目详情',
                    buttonUrl: '/plmProjectDetail',
                    url: '/' + path + '/plmProjectDetail/:id',
                    templateUrl: 'app/' + path + '/templates/ob/project/plmProjectDetail.html',
                    controllerUrl: '../app/' + path + '/templates/ob/project/plmProjectDetailCtrl'
                })

            //PLM-OB开发头
            .state('plmDevelopmentInfo',
                {
                    stateName: 'OB产品开发',
                    buttonUrl: '/plmDevelopmentInfo',
                    url: '/' + path + '/plmDevelopmentInfo',
                    templateUrl: 'app/' + path + '/templates/ob/development/plmDevelopmentInfo.html',
                    controllerUrl: '../app/' + path + '/templates/ob/development/plmDevelopmentInfoCtrl'
                })

            //PLM-OB开发详情
            .state('plmDevelopmentDetail',
                {
                    stateName: 'OB产品开发详情',
                    buttonUrl: '/plmDevelopmentDetail',
                    url: '/' + path + '/plmDevelopmentDetail/:id',
                    templateUrl: 'app/' + path + '/templates/ob/development/plmDevelopmentDetail.html',
                    controllerUrl: '../app/' + path + '/templates/ob/development/plmDevelopmentDetailCtrl'
                })

            //PLM-OB产品异常头
            .state('plmProductExceptionInfo',
                {
                    stateName: 'OB产品异常',
                    buttonUrl: '/plmProductExceptionInfo',
                    url: '/' + path + '/plmProductExceptionInfo',
                    templateUrl: 'app/' + path + '/templates/ob/productException/plmProductExceptionInfo.html',
                    controllerUrl: '../app/' + path + '/templates/ob/productException/plmProductExceptionInfoCtrl'
                })

            //PLM-OB产品异常详情
            .state('plmProductExceptionDetail',
                {
                    stateName: 'OB产品异常详情',
                    buttonUrl: '/plmProductExceptionDetail',
                    url: '/' + path + '/plmProductExceptionDetail/:id',
                    templateUrl: 'app/' + path + '/templates/ob/productException/plmProductExceptionDetail.html',
                    controllerUrl: '../app/' + path + '/templates/ob/productException/plmProductExceptionDetailCtrl'
                })

            //PLM-OB产品异常查询
            .state('exceptionProductReport',
                {
                    stateName: 'OB产品异常查询',
                    buttonUrl: '/exceptionProductReport',
                    url: '/' + path + '/exceptionProductReport',
                    templateUrl: 'app/' + path + '/templates/ob/productException/exceptionProductReport.html',
                    controllerUrl: '../app/' + path + '/templates/ob/productException/exceptionProductReportCtrl'
                })

            //PLM-OB产品资质文件状态查询
            .state('qaFileStatusSearch',
                {
                    stateName: 'OB产品异常查询',
                    buttonUrl: '/qaFileStatusSearch',
                    url: '/' + path + '/qaFileStatusSearch',
                    templateUrl: 'app/' + path + '/templates/ob/development/qaFileStatusSearch.html',
                    controllerUrl: '../app/' + path + '/templates/ob/development/qaFileStatusSearchCtrl'
                })

            //PLM-OB产品成分表报表
            .state('plmProductIngredientsReport',
                {
                    stateName: 'OB产品成分表报表',
                    buttonUrl: '/plmProductIngredientsReport',
                    url: '/' + path + '/plmProductIngredientsReport',
                    templateUrl: 'app/' + path + '/templates/ob/report/plmProductIngredientsReport.html',
                    controllerUrl: '../app/' + path + '/templates/ob/report/plmProductIngredientsReportCtrl'
                })

            //PLM-OB产品资质文件报表
            .state('plmQaFileReport',
                {
                    stateName: '资质文件报表',
                    buttonUrl: '/plmQaFileReport',
                    url: '/' + path + '/plmQaFileReport',
                    templateUrl: 'app/' + path + '/templates/ob/report/plmQaFileReport.html',
                    controllerUrl: '../app/' + path + '/templates/ob/report/plmQaFileReportCtrl'
                })
            //PLM-OB产品包材规格书报表
            .state('plmPackageSpecificationReport',
                {
                    stateName: '包材规格书报表',
                    buttonUrl: '/plmPackageSpecificationReport',
                    url: '/' + path + '/plmPackageSpecificationReport',
                    templateUrl: 'app/' + path + '/templates/ob/report/plmPackageSpecificationReport.html',
                    controllerUrl: '../app/' + path + '/templates/ob/report/plmPackageSpecificationReportCtrl'
                })
            //PLM-OB产品包材消耗量报表
            .state('plmPackageConsumptionReport',
                {
                    stateName: '包材消耗量报表',
                    buttonUrl: '/plmPackageConsumptionReport',
                    url: '/' + path + '/plmPackageConsumptionReport',
                    templateUrl: 'app/' + path + '/templates/ob/report/plmPackageConsumptionReport.html',
                    controllerUrl: '../app/' + path + '/templates/ob/report/plmPackageConsumptionReportCtrl'
                })

            //PLM-OB产品生产批次报表
            .state('plmBatchReport',
                {
                    stateName: '生产批次报表',
                    buttonUrl: '/plmBatchReport',
                    url: '/' + path + '/plmBatchReport',
                    templateUrl: 'app/' + path + '/templates/ob/report/plmBatchReport.html',
                    controllerUrl: '../app/' + path + '/templates/ob/report/plmBatchReportCtrl'
                })

            //PLM-BASE供应商资质管理列表
            .state('plmSupplierQaNonObInfo',
                {
                    stateName: '供应商资质管理列表',
                    buttonUrl: '/plmSupplierQaNonObInfo',
                    url: '/' + path + '/plmSupplierQaNonObInfo',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmSupplierQaNonObInfo.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmSupplierQaNonObInfoCtrl'
                })

            //PLM-BASE供应商资质管理详情
            .state('plmSupplierQaNonObDetail',
                {
                    stateName: '供应商资质管理详情',
                    buttonUrl: '/plmSupplierQaNonObDetail',
                    url: '/' + path + '/plmSupplierQaNonObDetail/:id',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmSupplierQaNonObDetail.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmSupplierQaNonObDetailCtrl'
                })

            //PLM-BASE特殊商品类型
            .state('plmSpecialProductTypeInfo',
                {
                    stateName: '特殊商品类型',
                    buttonUrl: '/plmSpecialProductTypeInfo',
                    url: '/' + path + '/plmSpecialProductTypeInfo',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmSpecialProductTypeInfo.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmSpecialProductTypeInfoCtrl'
                })

            //PLM-BASE地点清单
            .state('plmLocationList',
                {
                    stateName: '地点清单',
                    buttonUrl: '/plmLocationList',
                    url: '/' + path + '/plmLocationList',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmLocationList.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmLocationListCtrl'
                })

            //PLM-BASE供应商信息(RMS)
            .state('plmRmsSupplierInfoList',
                {
                    stateName: '供应商信息(RMS)',
                    buttonUrl: '/plmRmsSupplierInfoList',
                    url: '/' + path + '/plmRmsSupplierInfoList',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmRmsSupplierInfoList.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmRmsSupplierInfoListCtrl'
                })

            //PLM-BASE部门及分类清单
            .state('plmDeptListInfo',
                {
                    stateName: '部门及分类清单',
                    buttonUrl: '/plmDeptListInfo',
                    url: '/' + path + '/plmDeptListInfo',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmDeptListInfo.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmDeptListInfoCtrl'
                })

            //PLM-BASE部门及分类清单详情
            .state('plmDeptListDetail',
                {
                    stateName: '部门及分类清单详情',
                    buttonUrl: '/plmDeptListDetail',
                    url: '/' + path + '/plmDeptListDetail/:id',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmDeptListDetail.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmDeptListDetailCtrl'
                })

            //PLM-BASE品牌
            .state('plmBrandInfoList',
                {
                    stateName: '品牌列表',
                    buttonUrl: '/plmBrandInfoList',
                    url: '/' + path + '/plmBrandInfoList',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmBrandInfoList.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmBrandInfoListCtrl'
                })

            //PLM-BASE品牌详情
            .state('plmBrandInfoDetail',
                {
                    stateName: '品牌详情',
                    buttonUrl: '/plmBrandInfoDetail',
                    url: '/' + path + '/plmBrandInfoDetail/:id',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmBrandInfoDetail.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmBrandInfoDetailCtrl'
                })

            //PLM-BASE线上渠道列表
            .state('plmOnlineRouteList',
                {
                    stateName: '线上渠道列表',
                    buttonUrl: '/plmOnlineRouteList',
                    url: '/' + path + '/plmOnlineRouteList',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmOnlineRouteList.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmOnlineRouteListCtrl'
                })

            //PLM-BASE线上渠道详情
            .state('plmOnlineRouteDetail',
                {
                    stateName: '线上渠道详情',
                    buttonUrl: '/plmOnlineRouteDetail',
                    url: '/' + path + '/plmOnlineRouteDetail/:id',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmOnlineRouteDetail.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmOnlineRouteDetailCtrl'
                })

            //PLM-BASE字段说明
            .state('plmHelpStatementsList',
                {
                    stateName: '字段说明',
                    buttonUrl: '/plmHelpStatementsList',
                    url: '/' + path + '/plmHelpStatementsList',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmHelpStatementsList.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmHelpStatementsListCtrl'
                })

            //PLM-BASE字段说明详情
            .state('plmHelpStatementsDetail',
                {
                    stateName: '字段说明详情',
                    buttonUrl: '/plmHelpStatementsDetail',
                    url: '/' + path + '/plmHelpStatementsDetail/:id',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmHelpStatementsDetail.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmHelpStatementsDetailCtrl'
                })

            //PLM-BASE原产国
            .state('plmCountryOfOriginList',
                {
                    stateName: '原产国',
                    buttonUrl: '/plmCountryOfOriginList',
                    url: '/' + path + '/plmCountryOfOriginList',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmCountryOfOriginList.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmCountryOfOriginListCtrl'
                })

            //PLM-BASE售价区域列表
            .state('plmSalesAreaList',
                {
                    stateName: '售价区域列表',
                    buttonUrl: '/plmSalesAreaList',
                    url: '/' + path + '/plmSalesAreaList',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmSalesAreaList.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmSalesAreaListCtrl'
                })

            //PLM-BASE售价区域详情
            .state('plmSalesAreaDetail',
                {
                    stateName: '售价区域详情',
                    buttonUrl: '/plmSalesAreaDetail',
                    url: '/' + path + '/plmSalesAreaDetail/:id',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmSalesAreaDetail.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmSalesAreaDetailCtrl'
                })

            //PLM-BASE税收信息
            .state('plmTaxTypeList',
                {
                    stateName: '税收信息',
                    buttonUrl: '/plmTaxTypeList',
                    url: '/' + path + '/plmTaxTypeList',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmTaxTypeList.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmTaxTypeListCtrl'
                })


            //============================E-Quotation==============================
            .state('qualification',
                {
                    stateName: '供应商资质审查',
                    buttonUrl: '/qualification',
                    url: '/' + path + '/qualification',
                    templateUrl: 'app/' + path + '/templates/Equotation/qualificationreview/qualification.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/qualificationreview/qualificationCtrl'
                })

            .state('qualificationDetail',
                {
                    stateName: '供应商资质审查详情',
                    buttonUrl: '/qualificationDetail',
                    url: '/' + path + '/qualificationDetail/:qualificationId',
                    templateUrl: 'app/' + path + '/templates/Equotation/qualificationreview/qualificationDetail.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/qualificationreview/qualificationDetailCtrl'
                })

            .state('qualificationDescription',
                {
                    stateName: '资质文件说明',
                    buttonUrl: '/qualificationDescription',
                    url: '/' + path + '/qualificationDescription',
                    templateUrl: 'app/' + path + '/templates/Equotation/qualificationreview/qualificationDescription.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/qualificationreview/qualificationDescriptionCtrl'
                })

            .state('supplierFiles',
                {
                    stateName: '供应商档案查看',
                    buttonUrl: '/supplierFiles',
                    url: '/' + path + '/supplierFiles',
                    templateUrl: 'app/' + path + '/templates/Equotation/supplierinfo/supplierFiles.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/supplierinfo/supplierFilesCtrl'
                })

            .state('supplierFilesDetail',
                {
                    stateName: '供应商档案详情',
                    buttonUrl: '/supplierFilesDetail',
                    url: '/' + path + '/supplierFilesDetail/:supplierId/:supplierName',
                    templateUrl: 'app/' + path + '/templates/Equotation/supplierinfo/supplierFilesDetail.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/supplierinfo/supplierFilesDetailCtrl'
                })

            .state('equPosQualityAudit',
                {
                    stateName: '供应商质量审核',
                    buttonUrl: '/equPosQualityAudit',
                    url: '/' + path + '/equPosQualityAudit',
                    templateUrl: 'app/' + path + '/templates/Equotation/qualityAudit/equPosQualityAudit.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/qualityAudit/equPosQualityAuditCtrl'
                })

            .state('equPosQualityAuditDetail',
                {
                    stateName: '供应商质量审核详情',
                    buttonUrl: '/equPosQualityAuditDetail',
                    url: '/' + path + '/equPosQualityAuditDetail/:qualityAuditId',
                    templateUrl: 'app/' + path + '/templates/Equotation/qualityAudit/equPosQualityAuditDetail.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/qualityAudit/equPosQualityAuditDetailCtrl'
                })

            .state('equPosCsrAudit',
                {
                    stateName: '供应商CSR审核',
                    buttonUrl: '/equPosCsrAudit',
                    url: '/' + path + '/equPosCsrAudit',
                    templateUrl: 'app/' + path + '/templates/Equotation/csrAudit/equPosCsrAudit.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/csrAudit/equPosCsrAuditCtrl'
                })

            .state('equPosCsrAuditDetail',
                {
                    stateName: '供应商CSR审核详情',
                    buttonUrl: '/equPosCsrAuditDetail',
                    url: '/' + path + '/equPosCsrAuditDetail/:csrAuditId',
                    templateUrl: 'app/' + path + '/templates/Equotation/csrAudit/equPosCsrAuditDetail.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/csrAudit/equPosCsrAuditDetailCtrl'
                })

            .state('equPosArchivesChange',
                {
                    stateName: '供应商档案变更单据',
                    buttonUrl: '/equPosArchivesChange',
                    url: '/' + path + '/equPosArchivesChange',
                    templateUrl: 'app/' + path + '/templates/Equotation/archivesChange/equPosArchivesChange.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/archivesChange/equPosArchivesChangeCtrl'
                })

            .state('equPosArchivesChangeDetail',
                {
                    stateName: '供应商档案变更单据详情',
                    buttonUrl: '/equPosArchivesChangeDetail',
                    url: '/' + path + '/equPosArchivesChangeDetail/:changeId',
                    templateUrl: 'app/' + path + '/templates/Equotation/archivesChange/equPosArchivesChangeDetail.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/archivesChange/equPosArchivesChangeDetailCtrl'
                })

            .state('equPonProject',
                {
                    stateName: '立项',
                    buttonUrl: '/equPonProject',
                    url: '/' + path + '/equPonProject',
                    templateUrl: 'app/' + path + '/templates/Equotation/project/equPonProject.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/project/equPonProjectCtrl'
                })

            .state('equPonProjectDetail',
                {
                    stateName: '立项详情',
                    buttonUrl: '/equPonProjectDetail',
                    url: '/' + path + '/equPonProjectDetail/:projectId/:changeFlag',
                    templateUrl: 'app/' + path + '/templates/Equotation/project/equPonProjectDetail.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/project/equPonProjectDetailCtrl'
                })

            .state('equPonScoring',
                {
                    stateName: '评分',
                    buttonUrl: '/equPonScoring',
                    url: '/' + path + '/equPonScoring',
                    templateUrl: 'app/' + path + '/templates/Equotation/scoring/equPonScoring.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/scoring/equPonScoringCtrl'
                })

            .state('equPonScoringDetail',
                {
                    stateName: '评分详情',
                    buttonUrl: '/equPonScoringDetail',
                    url: '/' + path + '/equPonScoringDetail/:scoringId/:dataListOptions/:dataMessage',
                    templateUrl: 'app/' + path + '/templates/Equotation/scoring/equPonScoringDetail.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/scoring/equPonScoringDetailCtrl'
                })    

            // EQU 引入场景管理  HLH-----------------------------------------------------------------------------
            .state('equPosManage',
                {
                    stateName: '供应商引入场景',
                    buttonUrl: '/equPosManage',
                    url: '/' + path + '/equPosManage',
                    templateUrl: 'app/' + path + '/templates/Equotation/manage/equPosManage.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/manage/equPosManageCtrl'
                })

            .state('equPosSuspend',
                {
                    stateName: '供应商暂停淘汰',
                    url: '/' + path + '/equPosSuspend',
                    templateUrl: 'app/' + path + '/templates/Equotation/suspend/equPosSuspend.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/suspend/equPosSuspendCtrl'
                })

            .state('equPosSuspendEdit',
                {
                    url: '/' + path + '/equPosSuspendEdit/:supSuspendId',
                    buttonUrl: '/equPosSuspend',
                    stateName: '供应商暂停淘汰详情',
                    templateUrl: 'app/' + path + '/templates/Equotation/suspend/equPosSuspendEdit.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/suspend/equPosSuspendEditCtrl'
                })

            .state('equPosRecover',
                {
                    stateName: '供应商恢复',
                    buttonUrl: '/equPosRecover',
                    url: '/' + path + '/equPosRecover',
                    templateUrl: 'app/' + path + '/templates/Equotation/recover/equPosRecover.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/recover/equPosRecoverCtrl'
                })

            .state('equPosRecoverEdit',
                {
                    url: '/' + path + '/equPosRecoverEdit/:supRecoverId',
                    buttonUrl: '/equPosRecover',
                    stateName: '供应商恢复详情',
                    templateUrl: 'app/' + path + '/templates/Equotation/recover/equPosRecoverEdit.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/recover/equPosRecoverEditCtrl'
                })

            .state('equPosBlacklist',
                {
                    stateName: '供应商黑名单',
                    buttonUrl: '/equPosBlacklist',
                    url: '/' + path + '/equPosBlacklist',
                    templateUrl: 'app/' + path + '/templates/Equotation/blacklist/equPosBlacklist.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/blacklist/equPosBlacklistCtrl'
                })

            .state('equPosBlacklistEdit',
                {
                    url: '/' + path + '/equPosBlacklistEdit/:supBlacklistId',
                    buttonUrl: '/equPosBlacklist',
                    stateName: '供应商黑名单详情',
                    templateUrl: 'app/' + path + '/templates/Equotation/blacklist/equPosBlacklistEdit.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/blacklist/equPosBlacklistEditCtrl'
                })

            .state('equPosCreditAudit',
                {
                    stateName: '供应商信用审核',
                    buttonUrl: '/equPosCreditAudit',
                    url: '/' + path + '/equPosCreditAudit',
                    templateUrl: 'app/' + path + '/templates/Equotation/creditAudit/equPosCreditAudit.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/creditAudit/equPosCreditAuditCtrl'
                })

            .state('equPosCreditAuditEdit',
                {
                    url: '/' + path + '/equPosCreditAuditEdit/:supCreditAuditId',
                    buttonUrl: '/equPosCreditAudit',
                    stateName: '供应商信用审核详情',
                    templateUrl: 'app/' + path + '/templates/Equotation/creditAudit/equPosCreditAuditEdit.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/creditAudit/equPosCreditAuditEditCtrl'
                })

            .state('equPosWarehousing',
                {
                    stateName: '供应商入库审核',
                    buttonUrl: '/equPosWarehousing',
                    url: '/' + path + '/equPosWarehousing',
                    templateUrl: 'app/' + path + '/templates/Equotation/warehousing/equPosWarehousing.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/warehousing/equPosWarehousingCtrl'
                })

            .state('equPosWarehousingEdit',
                {
                    url: '/' + path + '/equPosWarehousingEdit/:supWarehousingId',
                    buttonUrl: '/equPosWarehousing',
                    stateName: '供应商入库审核详情',
                    templateUrl: 'app/' + path + '/templates/Equotation/warehousing/equPosWarehousingEdit.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/warehousing/equPosWarehousingEditCtrl'
                })

            .state('equPosCreditAuditChange',
                {
                    stateName: '供应商信用审核导入',
                    url: '/' + path + '/equPosCreditAuditChange',
                    templateUrl: 'app/' + path + '/templates/Equotation/creditAuditChange/equPosCreditAuditChange.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/creditAuditChange/equPosCreditAuditChangeCtrl'
                })

            .state('equPosCreditAuditChangeEdit',
                {
                    url: '/' + path + '/equPosCreditAuditChangeEdit/:changeCreditAuditHId',
                    buttonUrl: '/equPosCreditAuditChange',
                    stateName: '供应商信用审核详情',
                    templateUrl: 'app/' + path + '/templates/Equotation/creditAuditChange/equPosCreditAuditChangeEdit.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/creditAuditChange/equPosCreditAuditChangeEditCtrl'
                })


            .state('equPonStandards',
                {
                    stateName: '评分标准列表',
                    url: '/' + path + '/equPonStandards',
                    templateUrl: 'app/' + path + '/templates/Equotation/standards/equPonStandards.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/standards/equPonStandardsCtrl'
                })

            .state('equPonStandardsEdit',
                {
                    url: '/' + path + '/equPonStandardsEdit/:standardsId',
                    buttonUrl: '/equPonStandardsEdit',
                    stateName: '评分标准详情',
                    templateUrl: 'app/' + path + '/templates/Equotation/standards/equPonStandardsEdit.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/standards/equPonStandardsEditCtrl'
                })


            .state('equPonInformation',
                {
                    stateName: '报价资料开启列表',
                    url: '/' + path + '/equPonInformation',
                    templateUrl: 'app/' + path + '/templates/Equotation/information/equPonInformation.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/information/equPonInformationCtrl'
                })

            .state('equPonInformationEdit',
                {
                    stateName: '报价资料开启详情',
                    url: '/' + path + '/equPonInformationEdit/:informationId',
                    templateUrl: 'app/' + path + '/templates/Equotation/information/equPonInformationEdit.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/information/equPonInformationEditCtrl'
                })

            .state('monitorPriceInfo',
                {
                    stateName: '监控报价详情',
                    url: '/' + path + '/monitorPriceInfo/:informationId/:witnessFlag',
                    templateUrl: 'app/' + path + '/templates/Equotation/information/monitorPriceInfo.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/information/monitorPriceInfoCtrl'
                })


            .state('equPonQuotationApproval',
                {
                    stateName: '报价资料审批',
                    url: '/' + path + '/equPonQuotationApproval',
                    templateUrl: 'app/' + path + '/templates/Equotation/information/equPonQuotationApproval.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/information/equPonQuotationApprovalCtrl'
                })

            .state('equPonQuotationApprovalEdit',
                {
                    stateName: '报价资料审批',
                    url: '/' + path + '/equPonQuotationApprovalEdit/:approvalId',
                    templateUrl: 'app/' + path + '/templates/Equotation/information/equPonQuotationApprovalEdit.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/information/equPonQuotationApprovalEditCtrl'
                })



            //    HLH--------------------------------------------------------------------------------------------------
            //============================E-Quotation==============================
            // EQU 供应商状态报表
            .state('supplierStatusReportForm',
                {
                    stateName: '供应商状态报表',
                    buttonUrl: '/supplierStatusReportForm',
                    url: '/' + path + '/supplierStatusReportForm',
                    templateUrl: 'app/' + path + '/templates/Equotation/reportForm/supplierStatusReportForm.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/reportForm/supplierStatusReportFormCtrl'
                })
            // EQU 供应商库报表
            .state('supplierReportForm',
                {
                    stateName: '供应商库报表',
                    buttonUrl: '/supplierReportForm',
                    url: '/' + path + '/supplierReportForm',
                    templateUrl: 'app/' + path + '/templates/Equotation/reportForm/supplierReportForm.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/reportForm/supplierReportFormCtrl'
                })
            .state('supplierBidStatusReportForm',
                {
                    stateName: '供应商投标情况报表',
                    buttonUrl: '/supplierBidStatusReportForm',
                    url: '/' + path + '/supplierBidStatusReportForm',
                    templateUrl: 'app/' + path + '/templates/Equotation/reportForm/supplierBidStatusReportForm.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/reportForm/supplierBidStatusReportFormCtrl'
                })

            // EQU 供应商品分类
            .state('supplierCategory',
                {
                    stateName: '供应商品分类',
                    buttonUrl: '/supplierCategory',
                    url: '/' + path + '/supplierCategory',
                    templateUrl: 'app/' + path + '/templates/Equotation/category/supplierCategory.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/category/supplierCategoryCtrl'
                })
            // EQU 临时特批
            .state('tempSpecial',
                {
                    stateName: '供应商临时特批',
                    buttonUrl: '/tempSpecial',
                    url: '/' + path + '/tempSpecial',
                    templateUrl: 'app/' + path + '/templates/Equotation/tempSpecial/tempSpecial.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/tempSpecial/tempSpecialCtrl'
                })

            .state('editTempSpecial',
                {
                    url: '/' + path + '/editTempSpecial/:tempSpecialId',
                    buttonUrl: '/editTempSpecial',
                    templateUrl: 'app/' + path + '/templates/Equotation/tempSpecial/editTempSpecial.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/tempSpecial/editTempSpecialCtrl'
                })
        // 年度质量审核导入更新列表
            .state('qualityUpdate',
                {
                    stateName: '年度质量审核导入更新列表',
                    buttonUrl: '/qualityUpdate',
                    url: '/' + path + '/qualityUpdate',
                    templateUrl: 'app/' + path + '/templates/Equotation/qualityUpdate/qualityUpdate.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/qualityUpdate/qualityUpdateCtrl'
                })

            .state('editQualityUpdate',
                {
                    stateName: '年度质量审核导入更新新增页面',
                    buttonUrl: '/editQualityUpdate',
                    url: '/' + path + '/editQualityUpdate/:updateHeadId',
                    templateUrl: 'app/' + path + '/templates/Equotation/qualityUpdate/editQualityUpdate.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/qualityUpdate/editQualityUpdateCtrl'
                })

            // 年度CSR审核导入更新列表
            .state('csrUpdate',
                {
                    stateName: '年度CSR审核导入更新列表',
                    buttonUrl: '/csrUpdate',
                    url: '/' + path + '/csrUpdate',
                    templateUrl: 'app/' + path + '/templates/Equotation/csrUpdate/csrUpdate.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/csrUpdate/csrUpdateCtrl'
                })

            .state('editCsrUpdate',
                {
                    stateName: '年度CSR审核导入更新新增页面',
                    buttonUrl: '/editCsrUpdate',
                    url: '/' + path + '/editCsrUpdate/:updateHeadId',
                    templateUrl: 'app/' + path + '/templates/Equotation/csrUpdate/editCsrUpdate.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/csrUpdate/editCsrUpdateCtrl'
                })

            // 年度评分审核导入更新列表
            .state('scoreUpdate',
                {
                    stateName: '年度评分审核导入更新列表',
                    buttonUrl: '/scoreUpdate',
                    url: '/' + path + '/scoreUpdate',
                    templateUrl: 'app/' + path + '/templates/Equotation/scoreUpdate/scoreUpdate.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/scoreUpdate/scoreUpdateCtrl'
                })

            .state('editScoreUpdate',
                {
                    stateName: '年度评分审核导入更新新增页面',
                    buttonUrl: '/editScoreUpdate',
                    url: '/' + path + '/editScoreUpdate/:updateHeadId',
                    templateUrl: 'app/' + path + '/templates/Equotation/scoreUpdate/editScoreUpdate.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/scoreUpdate/editScoreUpdateCtrl'
                })

            // spend审核导入更新列表
            .state('spendUpdate',
                {
                    stateName: 'spend审核导入更新列表',
                    buttonUrl: '/spendUpdate',
                    url: '/' + path + '/spendUpdate',
                    templateUrl: 'app/' + path + '/templates/Equotation/spendUpdate/spendUpdate.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/spendUpdate/spendUpdateCtrl'
                })

            .state('editSpendUpdate',
                {
                    stateName: 'spend审核导入更新新增页面',
                    buttonUrl: '/editSpendUpdate',
                    url: '/' + path + '/editSpendUpdate/:updateHeadId',
                    templateUrl: 'app/' + path + '/templates/Equotation/spendUpdate/editSpendUpdate.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/spendUpdate/editSpendUpdateCtrl'
                })


            // contract审核导入更新列表
            .state('contractUpdate',
                {
                    stateName: 'contract审核导入更新列表',
                    buttonUrl: '/contractUpdate',
                    url: '/' + path + '/contractUpdate',
                    templateUrl: 'app/' + path + '/templates/Equotation/contractUpdate/contractUpdate.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/contractUpdate/contractUpdateCtrl'
                })

            .state('editContractUpdate',
                {
                    stateName: 'contract审核导入更新新增页面',
                    buttonUrl: '/editContractUpdate',
                    url: '/' + path + '/editContractUpdate/:updateHeadId',
                    templateUrl: 'app/' + path + '/templates/Equotation/contractUpdate/editContractUpdate.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/contractUpdate/editContractUpdateCtrl'
                })

            // 供应商报价管理(待报价)OEM界面
            .state('waitQuotationInfo',
                {
                    stateName: '供应商报价管理(待报价)OEM界面',
                    buttonUrl: '/waitQuotationInfo',
                    url: '/' + path + '/waitQuotationInfo',
                    templateUrl: 'app/' + path + '/templates/Equotation/quotation/waitQuotationInfo.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/quotation/waitQuotationInfoCtrl'
                })
            // 供应商报价管理(待报价)立项OEM查看界面
            .state('readOnlyProjectInfo',
                {
                    stateName: '供应商报价管理(待报价)OEM查看界面',
                    buttonUrl: '/readOnlyProjectInfo',
                    url: '/' + path + '/readOnlyProjectInfo/:projectId',
                    templateUrl: 'app/' + path + '/templates/Equotation/quotation/readOnlyProjectInfo.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/quotation/readOnlyProjectInfoCtrl'
                })

            // 报价管理详情
            .state('quotationInfo/quotation',
                {
                    stateName: '报价管理详情',
                    buttonUrl: '/quotationInfo/quotation',
                    url: '/' + path + '/quotationInfo/:docStatus',
                    templateUrl: 'app/' + path + '/templates/Equotation/quotation/quotationInfo.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/quotation/quotationInfoCtrl'
                })
            // 报价管理详情值报价中编辑页面
            .state('editQuotationInfo',
                {
                    stateName: '报价管理报价中详情',
                    buttonUrl: '/editQuotationInfo',
                    url: '/' + path + '/editQuotationInfo/:docStatus/:quotationId',
                    templateUrl: 'app/' + path + '/templates/Equotation/quotation/editQuotationInfo.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/quotation/editQuotationInfoCtrl'
                })
            // 报价管理之已截止
            .state('stopQuotationInfo/stop',
                {
                    stateName: '报价管理之已截止',
                    buttonUrl: '/stopQuotationInfo/stop',
                    url: '/' + path + '/stopQuotationInfo/:docStatus/:quotationId',
                    templateUrl: 'app/' + path + '/templates/Equotation/quotation/stopQuotationInfo.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/quotation/stopQuotationInfoCtrl'
                })
            // 报价管理之已完成
            .state('completeQuotationInfo/complete',
                {
                    stateName: '报价管理之已完成',
                    buttonUrl: '/completeQuotationInfo/complete',
                    url: '/' + path + '/completeQuotationInfo/:docStatus/:quotationId',
                    templateUrl: 'app/' + path + '/templates/Equotation/quotation/completeQuotationInfo.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/quotation/completeQuotationInfoCtrl'
                })
            // 报价管理之待议价
            .state('bargainQuotationInfo/bargain',
                {
                    stateName: '报价管理之待议价',
                    buttonUrl: '/bargainQuotationInfo/bargain',
                    url: '/' + path + '/bargainQuotationInfo/:docStatus/:quotationId',
                    templateUrl: 'app/' + path + '/templates/Equotation/quotation/bargainQuotationInfo.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/quotation/bargainQuotationInfoCtrl'
                })
            // 报价管理之终止
            .state('breakQuotationInfo/break',
                {
                    stateName: '报价管理之终止',
                    buttonUrl: '/breakQuotationInfo/break',
                    url: '/' + path + '/breakQuotationInfo/:docStatus/:quotationId',
                    templateUrl: 'app/' + path + '/templates/Equotation/quotation/breakQuotationInfo.html',
                    controllerUrl: '../app/' + path + '/templates/Equotation/quotation/breakQuotationInfoCtrl'
                })
            //    proMng-------------------------------------------------------------------------------------------------

            // ob商品新增界面
            .state('addObGoods',
                {
                    stateName: 'ob商品新增',
                    buttonUrl: '/addObGoods',
                    url: '/' + path + '/addObGoods',
                    templateUrl: 'app/' + path + '/templates/proMng/increaseGoods/addObGoods.html',
                    controllerUrl: '../app/' + path + '/templates/proMng/increaseGoods/addObGoodsCtrl'
                })
            // 非ob商品新增界面
            .state('addNotObGoods',
                {
                    stateName: '非ob商品新增',
                    buttonUrl: '/addNotObGoods',
                    url: '/' + path + '/addNotObGoods/:productHeadId',
                    templateUrl: 'app/' + path + '/templates/proMng/increaseNotObGoods/addNotObGoods.html',
                    controllerUrl: '../app/' + path + '/templates/proMng/increaseNotObGoods/addNotObGoodsCtrl'
                })
             // 采购完善
             .state('finishProcurement',
                {
                    stateName: '采购完善',
                    buttonUrl: '/finishProcurement',
                    url: '/' + path + '/finishProcurement/:productHeadId',
                    templateUrl: 'app/' + path + '/templates/proMng/finishProcurement/finishProcurement.html',
                    controllerUrl: '../app/' + path + '/templates/proMng/finishProcurement/finishProcurementCtrl'
                })
                
                ///mytemple
                     .state('mytemple',
                {
                    stateName: '我的模板',
                    buttonUrl: '/mytemple',
                    url: '/' + path + '/mytemple',
                    templateUrl: 'app/' + path + '/templates/product/templedetail.html',
                    controllerUrl: '../app/' + path + '/templates/product/templedetailCtrl'
                })
                
                //待处理列表
               .state('productlist',
                {
                    stateName: '待处理列表',
                    buttonUrl: '/productlist/:productHeadId',
                    url: '/' + path + '/productlist',
                    templateUrl: 'app/' + path + '/templates/product/productlist.html',
                    controllerUrl: '../app/' + path + '/templates/product/productlistCtrl'
                })
                
                // 供应商完善
                .state('supplierFinish',
                {
                    stateName: '供应商完善',
                    buttonUrl: '/supplierFinish',
                    url: '/' + path + '/supplierFinish/:productHeadId',
                    templateUrl: 'app/' + path + '/templates/proMng/supplierFinish/supplierFinish.html',
                    controllerUrl: '../app/' + path + '/templates/proMng/supplierFinish/supplierFinishCtrl'
                })
                //字段列表
                .state('productupdate',
                {
                    stateName: '字段列表',
                    buttonUrl: '/productupdate',
                    url: '/' + path + '/productupdate',
                    templateUrl: 'app/' + path + '/templates/product/productupdate.html',
                    controllerUrl: '../app/' + path + '/templates/product/productupdateCtrl'
                })
                

                // 商品详情
                .state('productDetails',
                {
                    stateName: '商品详情',
                    buttonUrl: '/productDetails',
                    url: '/' + path + '/productDetails/:productHeadId',
                    templateUrl: 'app/' + path + '/templates/product/productDetails.html',
                    controllerUrl: '../app/' + path + '/templates/product/productDetailsCtrl'
                })
                
                // 修改商品列表
                .state('modifyProList',
                {
                    stateName: '修改商品列表',
                    buttonUrl: '/modifyProList',
                    url: '/' + path + '/modifyProList',
                    templateUrl: 'app/' + path + '/templates/product/modifyProList.html',
                    controllerUrl: '../app/' + path + '/templates/product/modifyProListCtrl'
                })
                
                // 每日新增商品
                .state('productAddReport',
                {
                    stateName: '新增商品汇总表',
                    buttonUrl: '/productAddReport',
                    url: '/' + path + '/productAddReport',
                    templateUrl: 'app/' + path + '/templates/product/productAddReport.html',
                    controllerUrl: '../app/' + path + '/templates/product/productAddReportCtrl'
                })
                //每日新增商品
                        // 每日新增商品
                .state('productAddReportT',
                {
                    stateName: '每日新增商品',
                    buttonUrl: '/productAddReportT',
                    url: '/' + path + '/productAddReportT',
                    templateUrl: 'app/' + path + '/templates/product/productAddReportT.html',
                    controllerUrl: '../app/' + path + '/templates/product/productAddReportTCtrl'
                })
                
                   .state('productConditionReport',
                {
                    stateName: '有条件审批',
                    buttonUrl: '/productConditionReport',
                    url: '/' + path + '/productConditionReport',
                    templateUrl: 'app/' + path + '/templates/product/productConditionReport.html',
                    controllerUrl: '../app/' + path + '/templates/product/productConditionReportCtrl'
                })

                
                // 商品新增导航
                .state('productManageIndex',
                {
                    stateName: '商品新增导航',
                    buttonUrl: '/productManageIndex',
                    url: '/' + path + '/productManageIndex',
                    templateUrl: 'app/' + path + '/templates/proMng/productManageIndex/productManageIndex.html',
                    controllerUrl: '../app/' + path + '/templates/proMng/productManageIndex/productManageIndexCtrl'
                })
                
                
              .state('productPackageReport',
                {
                    stateName: '商品外包装修改报表',
                    buttonUrl: '/productPackageReport',
                    url: '/' + path + '/productPackageReport',
                    templateUrl: 'app/' + path + '/templates/product/productUpdatePackReport.html',
                    controllerUrl: '../app/' + path + '/templates/product/productUpdatePackReportCtrl'
                }) 
                
                .state('productSupplierReport',
                        {
                            stateName: '修改优先供应商报表',
                            buttonUrl: '/productSupplierReport',
                            url: '/' + path + '/productSupplierReport',
                            templateUrl: 'app/' + path + '/templates/product/productUpdateSupplierReport.html',
                            controllerUrl: '../app/' + path + '/templates/product/productUpdateSupplierReportCtrl'
                        })

                .state('replenishmentBase',
                {
                    stateName: '补货属性基础表',
                    buttonUrl: '/replenishmentBase',
                    url: '/' + path + '/replenishmentBase',
                    templateUrl: 'app/' + path + '/templates/proMng/replenishment/replenishmentBase.html',
                    controllerUrl: '../app/' + path + '/templates/proMng/replenishment/replenishmentBaseCtrl'
                })

                .state('applicationFormList',
                {
                    stateName: '新增申请表',
                    buttonUrl: '/applicationFormList',
                    url: '/' + path + '/applicationFormList',
                    templateUrl: 'app/' + path + '/templates/proMng/replenishment/applicationFormList.html',
                    controllerUrl: '../app/' + path + '/templates/proMng/replenishment/applicationFormListCtrl'
                })

                .state('goodsMaintenance',
                {
                    stateName: '商品维护',
                    buttonUrl: '/goodsMaintenance',
                    url: '/' + path + '/goodsMaintenance/:productId',
                    params: {datas: null},
                    templateUrl: 'app/' + path + '/templates/proMng/replenishment/goodsMaintenance.html',
                    controllerUrl: '../app/' + path + '/templates/proMng/replenishment/goodsMaintenanceCtrl'
                })

                .state('newApplicationForm',
                {
                    stateName: '申请单新增',
                    buttonUrl: '/newApplicationForm',
                    url: '/' + path + '/newApplicationForm/:productId',
                    templateUrl: 'app/' + path + '/templates/proMng/replenishment/newApplicationForm.html',
                    controllerUrl: '../app/' + path + '/templates/proMng/replenishment/newApplicationFormCtrl'
                })
                
                                   .state('节点人员设置',
                {
                    stateName: '新增流程人员设置',
                    buttonUrl: '/processProductList',
                    url: '/' + path + '/processProductList',
                    templateUrl: 'app/' + path + '/templates/product/processProductList.html',
                    controllerUrl: '../app/' + path + '/templates/product/processProductListCtrl'
                })
                //系列页面
                                             .state('商品系列列表',
                {
                    stateName: '商品系列列表',
                    buttonUrl: '/SeiralList',
                    url: '/' + path + '/SeiralList',
                    templateUrl: 'app/' + path + '/templates/plmBase/plmBankList.html',
                    controllerUrl: '../app/' + path + '/templates/plmBase/plmBankListCtrl'
                })






        /*gic start*/
            .state('userGroupConfig',
                {
                    stateName: '用户群组配置',
                    url: '/' + path + '/userGroupConfig',
                    templateUrl: 'app/' + path + '/templates/computer/userGroupConfig/userGroupConfig.html',
                    controllerUrl: '../app/' + path + '/templates/computer/userGroupConfig/userGroupConfigCtrl'
                })
        /*gjc end*/
                
    }]);
});
