/**
 * Created by dengdunxin on 2018/1/3.
 */
define(['webconfig'],function (webconfig) {
    return {
        // 数据源配置
        datasourceFind: webconfig.url.baseServer + 'baseReportDatasourceService/find',  //查询
        datasourceSave: webconfig.url.baseServer + 'baseReportDatasourceService/saveOrUpdate',  //保存
        datasourceDel: webconfig.url.baseServer + 'baseReportDatasourceService/delete',  //保存


        //项目
        baseApiCenterHSave: webconfig.url.baseServer + 'baseApiCenterHService/save',  //保存
        baseApiCenterHFind: webconfig.url.baseServer + 'baseApiCenterHService/findPagination', // 分页查询
        baseApiCenterHDel: webconfig.url.baseServer + 'baseApiCenterHService/delete', // 删除


        //profile 管理
        baseProfileSave: webconfig.url.baseServer + 'baseProfileService/save',  //保存
        baseProfileFind: webconfig.url.baseServer + 'baseProfileService/findPagination', // 分页查询
        baseProfileDel: webconfig.url.baseServer + 'baseProfileService/delete', // 删除
        baseVendorProfilefind: webconfig.url.vmiServer + 'baseDataService/v2/findVendor', // 查询
        baseVendorGroupProfilefind: webconfig.url.vmiServer + 'baseDataService/v2/findVendorGroup', // 查询
        baseVendorExteralProfilefind: webconfig.url.vmiServer + 'baseDataService/v2/findVendorExteral', // 查询


      // 将profile 中设置的 供应商和供应商组 拆分成具体的供应商
      //   findSupplier: "http://localhost:8041/vmi/vmiAuthorityService/findSupplier",
      //   // 查询供应商权限 的详情信息
      //   findSupplierAuthorityHead: "http://localhost:8041/vmi/vmiAuthorityService/findSupplierAuthorityHead",
      //   // 保存供应商权限数据
      //   saveAuthoritySupplier:"http://localhost:8041/vmi/vmiAuthorityService/v2/saveSupplierAuthority",
      //   // 查询部门
      //   findDept:  "http://localhost:8041/vmi/vmiAuthorityService/v2/findDept",
      //   // 查询class
      //   findClass:  "http://localhost:8041/vmi/vmiAuthorityService/v2/findClass",
      //   // 查询subClass
      //   findSubClass:  "http://localhost:8041/vmi/vmiAuthorityService/v2/findSubClass",
      //   // 查询货品
      //   findItem: "http://localhost:8041/vmi/vmiAuthorityService/v2/findItem",
      //   // 查询品牌
      //   findBrand: "http://localhost:8041/vmi/vmiAuthorityService/findBrand",
      //   // 查询group
      //   findGroups: "http://localhost:8041/vmi/vmiAuthorityService/findGroup",
      //   // 查询 Delivery Source Wh
      //   findDeliverySourceWh: "http://localhost:8041/vmi/vmiAuthorityService/v2/findDeliverySourceWh",
      //   // 查询仓库
      //   findStoreWarehouse:  "http://localhost:8041/vmi/vmiAuthorityService/findWarehouse",
      //   // 查询地区
      //   findArea:  "http://localhost:8041/vmi/vmiAuthorityService/v2/findArea",
      //   // 查询省
      //   findProvince: "http://localhost:8041/vmi/vmiAuthorityService/v2/findProvince",
      //   // 查询市
      //   findCity:  "http://localhost:8041/vmi/vmiAuthorityService/v2/findCity",
      //   // 查询市场
      //   findMarket: "http://localhost:8041/vmi/vmiAuthorityService/v2/findMarket",
      //   // 查询门店
      //   findStore: "http://localhost:8041/vmi/vmiAuthorityService/v2/findStore",
      //   findAuthorityImportResult: "http://localhost:8041/vmi/vmiAuthorityService/findAuthorityImportResult",

        //将profile 中设置的 供应商和供应商组 拆分成具体的供应商
        findSupplier:  webconfig.url.vmiServer + "vmiAuthorityService/findSupplier",
        // 查询供应商权限 的详情信息
        findSupplierAuthorityHead:  webconfig.url.vmiServer + "vmiAuthorityService/findSupplierAuthorityHead",
        // 保存供应商权限数据
        // saveAuthoritySupplier:  webconfig.url.vmiServer + "vmiAuthorityService/v2/saveSupplierAuthority",
        // 查询部门
        findDept:   webconfig.url.vmiServer + "vmiAuthorityService/v2/findDept",
        // 查询class
        findClass:   webconfig.url.vmiServer + "vmiAuthorityService/v2/findClass",
        // 查询subClass
        findSubClass: webconfig.url.vmiServer + "vmiAuthorityService/v2/findSubClass",
        // 查询货品
        findItem:  webconfig.url.vmiServer + "vmiAuthorityService/v2/findItem",
        // 查询品牌
        findBrand:  webconfig.url.vmiServer + "vmiAuthorityService/findBrand",
        // 查询group
        findGroups:  webconfig.url.vmiServer + "vmiAuthorityService/findGroup",
        // 查询 Delivery Source Wh
        findDeliverySourceWh:  webconfig.url.vmiServer + "vmiAuthorityService/v2/findDeliverySourceWh",
        // 查询仓库
        findStoreWarehouse:   webconfig.url.vmiServer + "vmiAuthorityService/findWarehouse",
        // 查询地区
        findArea:   webconfig.url.vmiServer + "vmiAuthorityService/v2/findArea",
        // 查询省
        findProvince:  webconfig.url.vmiServer + "vmiAuthorityService/v2/findProvince",
        // 查询市
        findCity:   webconfig.url.vmiServer + "vmiAuthorityService/v2/findCity",
        // 查询市场
        findMarket:  webconfig.url.vmiServer + "vmiAuthorityService/v2/findMarket",
        // 查询门店
        findStore:  webconfig.url.vmiServer + "vmiAuthorityService/v2/findStore",
        // 查询生成结果
        findAuthorityImportResult:  webconfig.url.vmiServer + "vmiAuthorityService/findAuthorityImportResult",

        //api 列表
        baseApiManagementSave: webconfig.url.baseServer + 'baseApiManagementService/save', // 保存
        baseApiManagementFind: webconfig.url.baseServer + 'baseApiManagementService/findPagination', // 分页查询
        baseApiManagementFindById: webconfig.url.baseServer + 'baseApiManagementService/findById', // 分页查询
        baseApiManagementDel: webconfig.url.baseServer + 'baseApiManagementService/delete', // 删除


        //模块
        baseApiCenterLSave: webconfig.url.baseServer + 'baseApiCenterLService/save', // 保存
        baseApiCenterLFind: webconfig.url.baseServer + 'baseApiCenterLService/findPagination', // 分页查询
        baseApiCenterLDel: webconfig.url.baseServer + 'baseApiCenterLService/delete', // 删除


        /*员工信息*/

        personSave: webconfig.url.baseServer + 'basePersonService/save', // 保存
        personFind: webconfig.url.baseServer + 'basePersonService/findPagination', // 查询
        personDel: webconfig.url.baseServer + 'basePersonService/delete', // 删除

        /*微信公众号*/

        wechatSave: webconfig.url.baseServer + 'baseWechatMngService/save', // 保存
        wechatFind: webconfig.url.baseServer + 'baseWechatMngService/findPagination', // 查询
        wechatDel: webconfig.url.baseServer + 'baseWechatMngService/delete', // 删除


        /*用户维护*/
        userChangePassword: webconfig.url.baseServer + 'baseUsersService/createPassword', // 忘记密码
        userSave: webconfig.url.baseServer + 'baseUsersService/save', // 保存
        userSaveAll:webconfig.url.baseServer + 'baseUsersService/saveOrUpdateAll', // 保存人员权限
        userFind: webconfig.url.baseServer + 'baseUsersService/findPagination', // 查询
        //userFind: 'http://139.159.227.77:8090/baseUsersService/findPagination',//

        userFindById: webconfig.url.baseServer + 'baseUsersService/findById', // 查询
        userFindUsersAuthority: webconfig.url.baseServer + 'baseUsersService/findBaseUsersPersonAuthorityPagination', // 查询
        userDel: webconfig.url.baseServer + 'baseUsersService/delete', // 删除

        /*职责查询*/
        responsibilityFind: webconfig.url.baseServer + 'baseResponsibilityService/findPagination', //
        responsibilitySave: webconfig.url.baseServer + 'baseUserResponsibilityService/save', //
        saveUserResp: webconfig.url.baseServer + 'baseUserResponsibilityService/saveUserResp', //多条保存
        deleteUserResp: webconfig.url.baseServer + 'baseUserResponsibilityService/delete', //多条保存
        findCurrentResponsibilityPagination: webconfig.url.baseServer + 'baseResponsibilityService/findCurrentResponsibilityPagination',//只能查出自己当前的职责

        /*组织*/

        organizationSave: webconfig.url.baseServer + 'baseOrganizationService/save', // 保存
        organizationFind: webconfig.url.baseServer + 'baseOrganizationService/findPagination', // 查询
        organizationDel: webconfig.url.baseServer + 'baseOrganizationService/delete', // 删除

        /*资源*/

        resourceSave: webconfig.url.baseServer + 'baseResourceService/save', // 保存
        resourceFind: webconfig.url.baseServer + 'baseResourceService/findPagination', // 查询
        resourceDel: webconfig.url.baseServer + 'baseResourceService/delete', // 删除
        findBaseResourceByMenuId: webconfig.url.baseServer + 'baseResourceService/findBaseResourceByMenuId', // 查询菜单所属资源
        findBaseResourceByRoleId: webconfig.url.baseServer + 'baseResourceService/findBaseResourceByRoleId', // 查询角色分配资源
        findBaseResourceById: webconfig.url.baseServer + 'baseResourceService/findById', // 根据资源Id查询资源

        /*部门*/

        findDeptPagination : webconfig.url.baseServer + 'baseDepartmentService/findDeptPagination', //查询部门
        findDeptByGroupIdAndDepartmentType : webconfig.url.baseServer + 'baseDepartmentService/findDeptByGroupIdAndDepartmentType', //查询部门(根据上级部门和部门类型)
        findDeptTree: webconfig.url.baseServer + 'baseDepartmentService/findDeptTree',
        saveOrUpdateBaseDepartment: webconfig.url.baseServer + 'baseDepartmentService/saveOrUpdateBaseDepartment',
        /*职位*/


        saveOrUpdateBaseJob: webconfig.url.baseServer + 'baseJobService/saveOrUpdateBaseJob', //新增、修改职务
        /*职位*/

        positionSave: webconfig.url.baseServer + 'basePositionService/save', // 保存
        positionFind: webconfig.url.baseServer + 'basePositionService/findPagination', // 查询
        positionDel: webconfig.url.baseServer + 'basePositionService/delete', // 删除
        /*职位*/
        findBasePositionNewPagination: webconfig.url.baseServer + 'basePositionNewService/findBasePositionNewPagination', //职位列表查询
        saveOrUpdatePositionNewInfo: webconfig.url.baseServer + 'basePositionNewService/saveOrUpdatePositionNewInfo', //新增、修改职位

        sam_findPersonPagination: webconfig.url.baseServer + 'baseOrgStructureService/findPersonPagination', //员工查询

        //职位层级
        findPersonLevelPagination: webconfig.url.baseServer + 'basePersonLevelService/findPersonLevelPagination', //职位层级列表查询
        saveOrUpdatePersonLevel: webconfig.url.baseServer + 'basePersonLevelService/saveOrUpdatePersonLevel',// 新增、修改职位层级
        findPersonLevelDetails: webconfig.url.baseServer + 'basePersonLevelService/findPersonLevelDetails', //获取职位层级明细
        deletePersonLevel: webconfig.url.baseServer + 'basePersonLevelService/deletePersonLevel', //删除行职位信息
        orgFindPersonPagination: webconfig.url.baseServer + 'baseOrgStructureService/findPersonPagination', //查询职位下的人员

        //职位分配
        findPersonAssignPagination: webconfig.url.baseServer + 'basePersonAssignService/findPersonAssignPagination', //职位分配列表
        saveOrUpdatePersonAssign: webconfig.url.baseServer + 'basePersonAssignService/saveOrUpdatePersonAssign',//职位分配 -保存
        findPersonAssignDetails: webconfig.url.baseServer + 'basePersonAssignService/findPersonAssignDetails',//职位分配 -查单条数据
        deletePersonAssign: webconfig.url.baseServer + 'basePersonAssignService/deletePersonAssign', //删除职位行数据

        //条款框架
        findTtaTermsFrameHeaderPagination: webconfig.url.ttaServer + 'ttaTermsFrameHeaderService/findTtaTermsFrameHeaderPagination',//条款框架列表
        findClauseTree:webconfig.url.ttaServer + 'ttaClauseTreeService/findClauseTree',//条款框架TREE
        findClauseInfo:webconfig.url.ttaServer + 'ttaClauseTreeService/findClauseInfo',//条款框架TREE_单条
        deleteClauseTree :webconfig.url.ttaServer + 'ttaClauseTreeService/deleteClauseTree',//条款框架TREE_删除当前节点
        saveOrUpdateTraermsAll:webconfig.url.ttaServer + 'ttaTermsFrameHeaderService/saveOrUpdateTraermsAll',//条款框架-保存
        saveOrUpdateCopy:webconfig.url.ttaServer + 'ttaTermsFrameHeaderService/saveOrUpdateCopy',//条款框架-复制
        changeTraermsAll:webconfig.url.ttaServer + 'ttaTermsFrameHeaderService/changeTraermsAll',//条款框架-变更
        updateStatus:webconfig.url.ttaServer +    'ttaTermsFrameHeaderService/updateStatus',//更新审批状态
        deleteCollectTypeLine :webconfig.url.ttaServer + 'ttaCollectTypeLineService/saveOrUpdateStatus',//条款框架TREE_删除单位


        //条框名目
        findTtaTermsFrameHeaderHPagination: webconfig.url.ttaServer + 'ttaTermsFrameHeaderHService/findTtaTermsFrameHeaderHPagination',//条款名目列表
        saveOrUpdateTraermsHAll:webconfig.url.ttaServer + 'ttaTermsFrameHeaderHService/saveOrUpdateTraermsHAll',//条款名目-保存
        findClausehTree:webconfig.url.ttaServer + 'ttaClauseTreeHService/findClausehTree',//条款名目TREE
        findClausehInfo:webconfig.url.ttaServer + 'ttaClauseTreeHService/findClausehInfo',//条款名目TREE_单条
        deleteClausehTree :webconfig.url.ttaServer + 'ttaClauseTreeHService/deleteClausehTree',//条款名目TREE_删除当前节点
        deleteCollectTypeLineH :webconfig.url.ttaServer + 'ttaCollectTypeLineHService/saveOrUpdateStatus',//条款名目TREE_删除单位
        //新品种设置
        findTtaNewbreedSetHeaderPagination: webconfig.url.ttaServer + 'ttaNewbreedSetHeaderService/findTtaNewbreedSetHeaderPagination',//查询新品种信息
        saveOrUpdateTtaNewbreedSetALL: webconfig.url.ttaServer + 'ttaNewbreedSetHeaderService/saveOrUpdateTtaNewbreedSetALL',//保存新品种信息
        findTtaNewbreedOne: webconfig.url.ttaServer + 'ttaNewbreedSetHeaderService/findTtaNewbreedOne',//查询新品种信息单条
        ttaNewbreedSetHeaderSaveOrUpdateCopy: webconfig.url.ttaServer + 'ttaNewbreedSetHeaderService/saveOrUpdateCopy',//新品种信息复制
        saveOrUpdateTtaNewbreedSetLIne: webconfig.url.ttaServer + 'ttaNewbreedSetLineService/saveOrUpdateTtaNewbreedSetLIne',//删除新品种行
        //新品种服务费
        findNBExtend: webconfig.url.ttaServer + 'ttaNewbreedExtendHeaderService/findById',//查询新品种信息单条
        deleteNBExtend: webconfig.url.ttaServer + 'ttaNewbreedExtendHeaderService/delete',//新品种信息单条
        saveOrUpdateNBExtend: webconfig.url.ttaServer + 'ttaNewbreedExtendHeaderService/saveOrUpdate',//查询新品种信息单条
        saveTtaNBEComfirm: webconfig.url.ttaServer + 'ttaNewbreedExtendHeaderService/saveTtaNBEComfirm',//新品种宣传服务费确认
        saveTtaNBECancelComfirm: webconfig.url.ttaServer + 'ttaNewbreedExtendHeaderService/saveTtaNBECancelComfirm',//新品种宣传服务费取消确认

        //新品种服务费 行信息
        findNBExtendL: webconfig.url.ttaServer + 'ttaNewbreedExtendLineService/findByLId',//查询新品种行信息单条
        //预设管理 - 补充协议模板设置 - 标准模板字段定义设置
        findTtaSaStdTplFieldCfgPagination: webconfig.url.ttaServer + 'ttaSaStdTplFieldCfgService/findTtaSaStdTplFieldCfgPagination',//查询 标准模板字段定义 LIST
        ttaSaStdTplFieldCfgSaveOrUpdateAll: webconfig.url.ttaServer + 'ttaSaStdTplFieldCfgService/saveOrUpdateAll',//查询 标准模板字段定义 LIST

        /*查询用户与员工信息*/
        findBaseUsersJoinPersonPagination: webconfig.url.baseServer + 'baseUsersService/findBaseUsersJoinPersonPagination',

        /*根据用户Id查询职责*/
        findResponsibilityByUserId: webconfig.url.baseServer + 'baseResponsibilityService/findResponsibilityByUserId',

        /*关联组织查询人员列表*/
        findBasePersonsJoinPersonOrg: webconfig.url.baseServer + 'basePersonService/findBasePersonsJoinPersonOrg',

        /*根据职位Id查询人员列表*/
        findBasePersonsByPositionId: webconfig.url.baseServer + 'basePersonService/findBasePersonsByPositionId',
        /*查询员工列表*/
        personList: baseServerURL + 'basePersonService/findList',

        /*查询员工列表 连接用户表*/
        personListJoinUser: baseServerURL + 'basePersonService/findJoinUser',

        /*查询员工分配的机构*/
        findListAssign: webconfig.url.baseServer + 'basePersonOrganizationService/findListAssign',
        saveListAssign: webconfig.url.baseServer + 'basePersonOrganizationService/saveListAssign',

        /*查询用户列表*/
        usersList: baseServerURL + 'baseUsersService/findUsersPagination',

        /*查询用户分配的机构*/
        findPubUsersOrg: webconfig.url.baseServer + 'pubUsersOrganizationService/findListAssign',
        savePubUsersOrg: webconfig.url.baseServer + 'pubUsersOrganizationService/saveListAssign',

        /*查询下层组织列表*/
        findAllChildrens: webconfig.url.baseServer + 'baseOrganizationService/findAllChildrens',

        /*查询所有上级*/
        findAllParents: webconfig.url.baseServer + 'baseOrganizationService/findAllParents',

        /*下一层组织机构列表*/
        findCurrentOrgChildrens: webconfig.url.baseServer + 'baseOrganizationService/findCurrentOrgChildrens',

        /*查询指定人员所属组织*/
        findOrganizationByPersonId: webconfig.url.baseServer + 'baseOrganizationService/findOrganizationByPersonId',
        /*查询员工职位*/
        findBasePositionsByPersonId: webconfig.url.baseServer + 'basePositionService/findBasePositionsByPersonId',
        /*查询部门下设置的职位*/
        findBasePositionsByOrgId: webconfig.url.baseServer + 'basePositionService/findBasePositionsByOrgId',
        /*关联组织查询职位*/
        findBasePositionsJoinPersonOrg: webconfig.url.baseServer + 'basePositionService/findBasePositionsJoinPersonOrg',

        /*SSO 系统获取排序*/
        getOrderNo: webconfig.url.baseServer + 'baseSystemSsoService/getOrderNo',


        /*收藏夹*/
        collectFind: webconfig.url.baseServer + 'baseFunctionCollectionService/find',
        collectSave: webconfig.url.baseServer + 'baseFunctionCollectionService/saveOrUpdate', // 保存
        collectDel: webconfig.url.baseServer + 'baseFunctionCollectionService/delete', // 删除 /*收藏夹*/

        //文章列表
        articleFind: webconfig.url.baseServer + 'baseCmsArticleService/find', // 查询
        articleFindById: webconfig.url.baseServer + 'baseCmsArticleService/findById', // 查询id
        articleSave: webconfig.url.baseServer + 'baseCmsArticleService/saveOrUpdate', // 保存
        articleDel: webconfig.url.baseServer + 'baseCmsArticleService/delete', // 删除

        /*陈智健 start */
        processGetProcesser: webconfig.url.processServer + 'bpmTaskConfigService/get',//  流程业务审批人数据

        /*陈智健 end */
        //轮播图
        carouseFind: webconfig.url.baseServer + 'baseCmsCarouselService/find', // 查询
        carouseSave: webconfig.url.baseServer + 'baseCmsCarouselService/saveOrUpdate', // 保存
        carouseDel: webconfig.url.baseServer + 'baseCmsCarouselService/delete', // 删除


        /*redis*/
        redisDel: webconfig.url.baseServer + 'baseRedisDataService/deleteRedis', // 删除redis缓存
        redisFindById: webconfig.url.baseServer + 'baseRedisDataService/findById', // 根据主键查询redis缓存记录
        redisFind: webconfig.url.baseServer + 'baseRedisDataService/findRedis', // 查询缓存列表
        redisSave: webconfig.url.baseServer + 'baseRedisDataService/saveRedis', // 保存redis缓存

        retryCompensation: webconfig.url.baseServer + 'transactionCompensationService/retryCompensation', // 保存redis缓存
        findMQMessageList: webconfig.url.baseServer + 'transactionCompensationService/findMQMessageList', // 保存redis缓存
        transactionDel: webconfig.url.baseServer + 'transactionCompensationService/delete', // 删除事务补偿
        findByParent: webconfig.url.baseServer + 'baseLookupValuesService/findByParent',// 查找父类
        findReasonSynDic: webconfig.url.baseServer + 'baseLookupValuesService/findReasonSynDic', // 查找父类


        /*阳杰 start*/
        msgSourceCfgFind:webconfig.url.messageSever + 'msgSourceCfgService/find',//消息源查询
        msgSourceCfgSave:webconfig.url.messageSever + 'msgSourceCfgService/saveOrUpdate',//消息源修改
        msgSourceCfgDel:webconfig.url.messageSever + 'msgSourceCfgService/delete',//消息源删除

        // 模版
        msgTempleCfgFind: webconfig.url.messageSever + 'msgTempleCfgService/find',//模版查询
        msgTempleCfgSave:webconfig.url.messageSever + 'msgTempleCfgService/saveOrUpdate',// 模版修改
        msgTempleCfgDel:webconfig.url.messageSever + 'msgTempleCfgService/delete',// 模版删除
        findChannelCfg: webconfig.url.baseServer + 'baseChannelService/findPagination', // 获取渠道
        orgList: webconfig.url.baseServer + 'baseOrganizationService/findPagination', // 查询组织


        // 消息配置
        msgCfgFind: webconfig.url.messageSever + 'msgCfgService/find',//配置查询
        msgCfgSave:webconfig.url.messageSever + 'msgCfgService/saveOrUpdate',//配置修改
        msgCfgDel:webconfig.url.messageSever + 'msgCfgService/delete',//配置删除

        // 消息实例
        msgInstanceFind: webconfig.url.messageSever + 'msgInstanceService/find',//实例查询
        msgInstanceDelete:webconfig.url.messageSever + 'msgInstanceService/delete',//实例删除
        findMsgInstanceDetailById:webconfig.url.messageSever + 'msgInstanceService/findMsgInstanceDetailById',//实例详情

        // 消息历史
        msgHistoryFind: webconfig.url.messageSever + 'msgHistoryService/find', // 消息历史查询
        msgHistoryDelete: webconfig.url.messageSever + 'msgHistoryService/delete', // 消息历史删除

        // 消息接收对象
        msgReceiveFind: webconfig.url.messageSever + 'msgReceiveSqlService/find', // 消息中心消息接收对象查询,
        msgReceiveSaveOrUpdate: webconfig.url.messageSever + 'msgReceiveSqlService/saveOrUpdate', // 消息中心消息接收对象新增修改
        msgReceiveDelete: webconfig.url.messageSever + 'msgReceiveSqlService/delete', // 消息中心消息接收对象删除,

        // 消息退订
        msgTdFind: webconfig.url.messageSever + 'msgTdService/find', // 消息中心消息退订
        msgTdDelete: webconfig.url.messageSever + 'msgTdService/delete', // 消息中心消息退订删除

        // 消息中心用户配置
        msgUserFind: webconfig.url.messageSever + 'msgUserService/find', // 用户查询
        msgUserSave:webconfig.url.messageSever + 'msgUserService/saveOrUpdate',//用户修改
        msgUserDel:webconfig.url.messageSever + 'msgUserService/delete',//用户删除

        // 消息日志
        msgLogFind: webconfig.url.messageSever + 'msgLogService/find', // 日志查询
        msgLogDel:webconfig.url.messageSever + 'msgLogService/delete',//用户删除
        /*阳杰 end*/


        // 供应商信息
        supplierFind: webconfig.url.ttaServer + 'ttaSupplierService/find', //
        supplierDel:webconfig.url.ttaServer + 'ttaSupplierService/delete',//
        supplierSave:webconfig.url.ttaServer + 'ttaSupplierService/saveOrUpdate',//
        supplierFindById:webconfig.url.ttaServer + 'ttaSupplierService/findById',//


        supplierFindLov: webconfig.url.ttaServer + 'ttaSupplierService/findLov', //

        // 关联供应商信息
        supplierRelFind: webconfig.url.ttaServer + 'ttaRelSupplierService/find', //
        supplierRelDel:webconfig.url.ttaServer + 'ttaRelSupplierService/delete',//
        supplierRelSave:webconfig.url.ttaServer + 'ttaRelSupplierService/saveOrUpdate',//

        // 供应商部门信息
        supplierDeptFind: webconfig.url.ttaServer + 'ttaRelSupplierDeptService/find', //
        supplierDeptDel:webconfig.url.ttaServer + 'ttaRelSupplierDeptService/delete',//
        supplierDeptSave:webconfig.url.ttaServer + 'ttaRelSupplierDeptService/saveOrUpdate',//

        // 供应商对应品牌信息
        supplierBrandFind: webconfig.url.ttaServer + 'ttaRelSupplierBrandService/find', //
        supplierBrandDel:webconfig.url.ttaServer + 'ttaRelSupplierBrandService/delete',//
        supplierBrandSave:webconfig.url.ttaServer + 'ttaRelSupplierBrandService/saveOrUpdate',//

        // 供应商品牌不在TTA system中的信息
        noSupplierBrandFind: webconfig.url.ttaServer + 'ttaAbnormalSupplierBrandService/find', //
        noSupplierBrandSaveOrUpdate:webconfig.url.ttaServer + 'ttaAbnormalSupplierBrandService/saveOrUpdate',//
        noSupplierBrandRefreshBrandInfo:webconfig.url.ttaServer + 'ttaAbnormalSupplierBrandService/refreshBrandInfo',//
        noSupplierBrandCreateSupplierBrand:webconfig.url.ttaServer + 'ttaAbnormalSupplierBrandService/createSupplierBrand',//
        //人员权限控制

        userGroupFind: webconfig.url.ttaServer + 'ttaUserGroupDeptBrandService/find', //
        userGroupFindUser: webconfig.url.ttaServer + 'ttaUserGroupDeptBrandService/findUser', //
        userGroupSave:webconfig.url.ttaServer + 'ttaUserGroupDeptBrandService/saveOrUpdate',//
        userGroupSaveRxpire:webconfig.url.ttaServer + 'ttaUserGroupDeptBrandService/saveOrUpdateRxpire',//
        userGroupSavePower:webconfig.url.ttaServer + 'ttaUserGroupDeptBrandService/saveOrUpdatePower',//
        saveTtaUserPowerImportInfo:webconfig.url.ttaServer + 'ttaUserDataTypeService/saveImportInfo',//

        // 物料信息
        itemFind: webconfig.url.ttaServer + 'ttaItemService/find', //
        itemDel:webconfig.url.ttaServer + 'ttaItemService/delete',//
        itemSave:webconfig.url.ttaServer + 'ttaItemService/saveOrUpdate',//
        itemFindById:webconfig.url.ttaServer + 'ttaItemService/findById',//

        itemFindLov: webconfig.url.ttaServer + 'ttaItemService/findLov', //

        brandFindLov: webconfig.url.ttaServer + 'ttaBrandService/findLov', //
        groupFindLov: webconfig.url.ttaServer + 'ttaGroupService/findLov', //
        findGroup:webconfig.url.ttaServer + 'ttaGroupService/find',

        // 部门协定收费标准设置
        feedeptHFind: webconfig.url.ttaServer + 'ttaFeeDeptHService/find', //
        feedeptHDel:webconfig.url.ttaServer + 'ttaFeeDeptHService/delete',//
        feedeptHSave:webconfig.url.ttaServer + 'ttaFeeDeptHService/saveOrUpdate',//
        feedeptHFindById:webconfig.url.ttaServer + 'ttaFeeDeptHService/findById',//
        feedeptHApprove:webconfig.url.ttaServer + 'ttaFeeDeptHService/approve',//
        feedeptHChange:webconfig.url.ttaServer + 'ttaFeeDeptHService/changeOrder',//
        feedeptHCopy:webconfig.url.ttaServer + 'ttaFeeDeptHService/copyOrder',//

        feedeptLFind: webconfig.url.ttaServer + 'ttaFeeDeptLService/find', //
        feedeptLDel:webconfig.url.ttaServer + 'ttaFeeDeptLService/delete',//
        feedeptLSave:webconfig.url.ttaServer + 'ttaFeeDeptLService/saveOrUpdate',//
        feedeptLFindById:webconfig.url.ttaServer + 'ttaFeeDeptLService/findById',//

        feedeptDFind: webconfig.url.ttaServer + 'ttaFeeDeptDService/find', //
        feedeptDDel:webconfig.url.ttaServer + 'ttaFeeDeptDService/delete',//
        feedeptDSave:webconfig.url.ttaServer + 'ttaFeeDeptDService/saveOrUpdate',//
        feedeptDFindById:webconfig.url.ttaServer + 'ttaFeeDeptDService/findById',//

        feedeptHFindLov: webconfig.url.ttaServer + 'ttaFeeDeptHService/findLov', //

        // proposal信息
        proposalFind: webconfig.url.ttaServer + 'ttaProposalHeaderService/find', //
        proposalFindVendor: webconfig.url.ttaServer + 'ttaProposalHeaderService/findVendor', //
        proposalTransferPdf: webconfig.url.ttaServer + 'ttaProposalHeaderService/printDownload', //
        proposalDel:webconfig.url.ttaServer + 'ttaProposalHeaderService/delete',//
        proposalSave:webconfig.url.ttaServer + 'ttaProposalHeaderService/saveOrUpdate',//
        proposalFindById:webconfig.url.ttaServer + 'ttaProposalHeaderService/findById',//
        proposalFindLov: webconfig.url.ttaServer + 'ttaProposalHeaderService/findLov', //
        proposalChangeStatus:webconfig.url.ttaServer + 'ttaProposalHeaderService/updateStatus',//
        findApproveCheck:webconfig.url.ttaServer + 'ttaProposalHeaderService/findApproveCheck',//
        proposalChange:webconfig.url.ttaServer + 'ttaProposalHeaderService/changeOrder',//
        proposalCutVersion:webconfig.url.ttaServer + 'ttaProposalHeaderService/cutVersion',//
        proposalBuilderChange:webconfig.url.ttaServer + 'ttaProposalHeaderService/changProposalBillStatus',//proposal生成首页:变更单据状态
        proposalBuilderCutVersion:webconfig.url.ttaServer + 'ttaProposalHeaderService/cutProposalBillVersion',//切换proposal生成管理页的 切换上一版本功能
        proposalFindSaleType: webconfig.url.ttaServer + 'ttaProposalHeaderService/findProposalSaleType',//查找往年proposal的销售方式
        updateSkipStatus:webconfig.url.ttaServer + 'ttaProposalHeaderService/updateSkipStatus',//更新是否需要GM审批状态
        proposalQueryProcessNodeStatus:webconfig.url.ttaServer + 'ttaProposalHeaderService/queryProccessNodeStauts',//查看流程状态,控制tab显示
        proposalCodeFindLov: webconfig.url.ttaServer + 'ttaProposalHeaderService/findProposalCode', //
        proposalPDFPrint:webconfig.url.service + 'pdftk/print/print', //
        contractLinePDFPrint:webconfig.url.service + 'pdftk/conattr/conattr', //
        proposalUpdateVendorName:webconfig.url.ttaServer + 'ttaProposalHeaderService/updateVendorName',//更新供应商名称
        // 品牌计划头信息
        brandplnHFind: webconfig.url.ttaServer + 'ttaBrandplnHService/find', //
        brandplnHDel:webconfig.url.ttaServer + 'ttaBrandplnHService/delete',//
        brandplnHSave:webconfig.url.ttaServer + 'ttaBrandplnHService/saveOrUpdate',//
        brandplnHFindById:webconfig.url.ttaServer + 'ttaBrandplnHService/findById',//
        brandplnHFindLov: webconfig.url.ttaServer + 'ttaBrandplnHService/findLov', //

        brandplnHConfirm:webconfig.url.ttaServer + 'ttaBrandplnHService/confirm',//
        brandplnHCancelConfirm:webconfig.url.ttaServer + 'ttaBrandplnHService/cancelConfirm',//
        brandplnHCount: webconfig.url.ttaServer + 'ttaBrandplnHService/count', //
        brandplnHUpdate:webconfig.url.ttaServer + 'ttaBrandplnHService/brandplnHUpdate',
        brandplnHCheckConfirm:webconfig.url.ttaServer + 'ttaBrandplnHService/brandplnHCheckConfirm',
        submitBrandCountTask:webconfig.url.ttaServer + 'ttaBrandplnHService/submitBrandCountTask',//提交请求队列
        getBrandCountCreateStatus:webconfig.url.ttaServer + 'ttaBrandplnHService/getBrandCountCreateStatus',
        // 品牌计划行信息
        brandplnLFind: webconfig.url.ttaServer + 'ttaBrandplnLService/find', //
        brandplnLDel:webconfig.url.ttaServer + 'ttaBrandplnLService/delete',//
        brandplnLClear:webconfig.url.ttaServer + 'ttaBrandplnLService/clear',//
        brandplnLSave:webconfig.url.ttaServer + 'ttaBrandplnLService/saveOrUpdate',//
        brandplnLFindById:webconfig.url.ttaServer + 'ttaBrandplnLService/findById',//
        brandplnLFindLov: webconfig.url.ttaServer + 'ttaBrandplnLService/findLov', //
        saveImportTtaBrandlpn: webconfig.url.ttaServer + 'ttaBrandplnLService/saveImportTtaBrandlpn', //

        // 部门协定头信息
        ttaDeptFeeHFind: webconfig.url.ttaServer + 'ttaDeptFeeHeaderService/find', //
        ttaDeptFeeHDel:webconfig.url.ttaServer + 'ttaDeptFeeHeaderService/delete',//
        ttaDeptFeeHSave:webconfig.url.ttaServer + 'ttaDeptFeeHeaderService/saveOrUpdate',//
        ttaDeptFeeHFindById:webconfig.url.ttaServer + 'ttaDeptFeeHeaderService/findById',//
        ttaDeptFeeHFindLov: webconfig.url.ttaServer + 'ttaDeptFeeHeaderService/findLov', //

        //预设-补充协议模板设置-标准模板设置
        findTtaSaStdTplDefHeaderTree: webconfig.url.ttaServer + 'ttaSaStdTplDefHeaderService/findTtaSaStdTplDefHeaderTree', //
        saveOrUpdateTtaSaStdTplDefHeaderTree: webconfig.url.ttaServer + 'ttaSaStdTplDefHeaderService/saveOrUpdate', //
        deleteTtaSaStdTplDefHeaderTree: webconfig.url.ttaServer + 'ttaSaStdTplDefHeaderService/delete', //
        findTtaSaStdFieldCfgLinePagination: webconfig.url.ttaServer + 'ttaSaStdFieldCfgLineService/findTtaSaStdFieldCfgLinePagination', //
        saveOrUpdateTtaSaStdFieldCfgLine: webconfig.url.ttaServer + 'ttaSaStdFieldCfgLineService/saveOrUpdateAll', //
        deleteTtaSaStdFieldCfgLine: webconfig.url.ttaServer + 'ttaSaStdFieldCfgLineService/delete', //
        saveOrUpdateAllTtaSaTplTabLine: webconfig.url.ttaServer + 'ttaSaTplTabLineService/saveOrUpdateAll', //
        findTtaSaTplTabLineList: webconfig.url.ttaServer + 'ttaSaTplTabLineService/findTtaSaTplTabLineList', //
        findTtaStdApplyHeader: webconfig.url.ttaServer + 'ttaStdApplyHeaderService/find', //
        findTtaStdApplyHeaderOne: webconfig.url.ttaServer + 'ttaStdApplyHeaderService/findById', //
        cancelTtaStdApplyHeader: webconfig.url.ttaServer + 'ttaStdApplyHeaderService/cancel', //
        saveOrUpdateTtaStdApplyHeader: webconfig.url.ttaServer + 'ttaStdApplyHeaderService/saveOrUpdate', //
        changeApplyAllTtaStdApplyHeader: webconfig.url.ttaServer + 'ttaStdApplyHeaderService/changeApplyAll', //
        showPdfTtaStdApplyHeader: webconfig.url.ttaServer + 'ttaStdApplyHeaderService/showPdf', //
        openEditTtaStdApplyHeader: webconfig.url.ttaServer + 'ttaStdApplyHeaderService/openEdit', //
        ttaPageOfficeService: webconfig.url.ttaServer + 'pageOfficeService', //
        showPdfPageOfficeService: webconfig.url.ttaServer + 'pageOfficeService/showPdf', //
        ttaStdApplyHeaderService: webconfig.url.ttaServer + 'ttaStdApplyHeaderService', //

        ttaReportAboiSummaryFindPagination: webconfig.url.ttaServer + 'ttaReportAboiSummaryFixLineService/findPagination', //
        findTermsListTtaReportAboiSummaryFixLine: webconfig.url.ttaServer + 'ttaReportAboiSummaryFixLineService/findTermsList', //
        ttaReportAboiSummarySaveOrUpdateALL: webconfig.url.ttaServer + 'ttaReportAboiSummaryFixLineService/saveOrUpdateALL', //
        ttaReportAboiSummaryFindBrandList: webconfig.url.ttaServer + 'ttaReportAboiSummaryFixLineService/findBrandList', //
        ttaReportAboiSummaryFindGroupList: webconfig.url.ttaServer + 'ttaReportAboiSummaryFixLineService/findGroupList', //

        findTtaSaNonStandarHeader: webconfig.url.ttaServer + 'ttaSaNonStandarHeaderService/find', //
        cancelTtaSaNonStandarHeader: webconfig.url.ttaServer + 'ttaSaNonStandarHeaderService/cancel', //
        findByIdTtaSaNonStandarHeader: webconfig.url.ttaServer + 'ttaSaNonStandarHeaderService/findById', //
        saveOrUpdateTtaSaNonStandarHeader: webconfig.url.ttaServer + 'ttaSaNonStandarHeaderService/saveOrUpdate', //
        updateStatusTtaSaNonStandarHeader: webconfig.url.ttaServer + 'ttaSaNonStandarHeaderService/updateStatus', //
        changeApplyAllTtaSaNonStandarHeader: webconfig.url.ttaServer + 'ttaSaNonStandarHeaderService/changeApplyAll', //
        showPdfTtaSaNonStandarHeader: webconfig.url.ttaServer + 'ttaSaNonStandarHeaderService/showPdf', //
        openEditTtaSaNonStandarHeader: webconfig.url.ttaServer + 'ttaSaNonStandarHeaderService/openEdit', //
        updateSkipStatusTtaSaNonStandarHeader: webconfig.url.ttaServer + 'ttaSaNonStandarHeaderService/updateSkipStatus', //
        saNonStandarHeaderPrint:webconfig.url.ttaServer  + 'ttaSaNonStandarHeaderService/print',//合同打印

        saveOrUpdateAllTtaSaNonStandarLine: webconfig.url.ttaServer + 'ttaSaNonStandarLineService/saveOrUpdateAll', //
        deleteTtaSaNonStandarLine: webconfig.url.ttaServer + 'ttaSaNonStandarLineService/delete', //
        findTtaSaNonStandarLinePagination: webconfig.url.ttaServer + 'ttaSaNonStandarLineService/findTtaSaNonStandarLinePagination', //

        findTtaSoleNonStandarHeader: webconfig.url.ttaServer + 'ttaSoleNonStandarHeaderService/find', //
        cancelTtaSoleNonStandarHeader: webconfig.url.ttaServer + 'ttaSoleNonStandarHeaderService/cancel', //
        findByIdTtaSoleNonStandarHeader: webconfig.url.ttaServer + 'ttaSoleNonStandarHeaderService/findById', //
        saveOrUpdateTtaSoleNonStandarHeader: webconfig.url.ttaServer + 'ttaSoleNonStandarHeaderService/saveOrUpdate', //
        updateStatusTtaSoleNonStandarHeader: webconfig.url.ttaServer + 'ttaSoleNonStandarHeaderService/updateStatus', //
        changeApplyAllTtaSoleNonStandarHeader: webconfig.url.ttaServer + 'ttaSoleNonStandarHeaderService/changeApplyAll', //
        showPdfTtaSoleNonStandarHeader: webconfig.url.ttaServer + 'ttaSoleNonStandarHeaderService/showPdf', //
        openEditTtaSoleNonStandarHeader: webconfig.url.ttaServer + 'ttaSoleNonStandarHeaderService/openEdit', //
        updateSkipStatusTtaSoleNonStandarHeader: webconfig.url.ttaServer + 'ttaSoleNonStandarHeaderService/updateSkipStatus', //

        findTtaContractSpecialHeader: webconfig.url.ttaServer + 'ttaContractSpecialHeaderService/find', //
        cancelTtaContractSpecialHeader: webconfig.url.ttaServer + 'ttaContractSpecialHeaderService/cancel', //
        findByIdTtaContractSpecialHeader: webconfig.url.ttaServer + 'ttaContractSpecialHeaderService/findById', //
        saveOrUpdateTtaContractSpecialHeader: webconfig.url.ttaServer + 'ttaContractSpecialHeaderService/saveOrUpdate', //
        updateStatusTtaContractSpecialHeader: webconfig.url.ttaServer + 'ttaContractSpecialHeaderService/updateStatus', //

        findTtaContractRecordHeader: webconfig.url.ttaServer + 'ttaContractRecordHeaderService/find', //
        saveOrUpdateALLTtaContractRecordHeader: webconfig.url.ttaServer + 'ttaContractRecordHeaderService/saveOrUpdateALL', //
        findContractVendorTtaContractRecordHeader: webconfig.url.ttaServer + 'ttaContractRecordHeaderService/findContractVendor', //

        saveOrUpdateAllTtaSoleNonStandarLine: webconfig.url.ttaServer + 'ttaSoleNonStandarLineService/saveOrUpdateAll', //
        deleteTtaSoleNonStandarLine: webconfig.url.ttaServer + 'ttaSoleNonStandarLineService/delete', //
        findTtaSoleNonStandarLinePagination: webconfig.url.ttaServer + 'ttaSoleNonStandarLineService/findTtaSoleNonStandarLinePagination', //

        ttaDeptFeeHConfirm:webconfig.url.ttaServer + 'ttaDeptFeeHeaderService/confirm',//
        ttaDeptFeeHCancelConfirm:webconfig.url.ttaServer + 'ttaDeptFeeHeaderService/cancelConfirm',//
        ttaDeptFeeHCount: webconfig.url.ttaServer + 'ttaDeptFeeHeaderService/count', //

        // 部门协定行信息
        ttaDeptFeeLFind: webconfig.url.ttaServer + 'ttaDeptFeeLineService/find', //
        ttaDeptFeeLDel:webconfig.url.ttaServer + 'ttaDeptFeeLineService/delete',//
        ttaDeptFeeLSave:webconfig.url.ttaServer + 'ttaDeptFeeLineService/saveOrUpdate',//
        ttaDeptFeeLFindById:webconfig.url.ttaServer + 'ttaDeptFeeLineService/findById',//
        ttaDeptFeeLFindLov: webconfig.url.ttaServer + 'ttaDeptFeeLineService/findLov', //
        ttaDeptFeeLFindReport: webconfig.url.ttaServer + 'ttaDeptFeeLineService/findDeptFeeLineReport', //
        ttaDeptFeeLFindByOldYear : webconfig.url.ttaServer + 'ttaDeptFeeLineService/findDeptFeeLFindByOldYear',//查找往年的部门协定标准值
        ttaDeptFeeLFindSearchPromotionCost : webconfig.url.ttaServer + 'ttaDeptFeeLineService/ttaDeptFeeLFindSearchPromotionCost',//查找部门的标准值和单位信息
        findTermLineDataByDeptFee:webconfig.url.ttaServer + 'ttaTermsHService/findTermLineDataByDeptFee',
        findDeptFeeShowHideByProposal:webconfig.url.ttaServer + 'ttaDeptFeeLineService/findDeptFeeShowHideByProposal',

        //proposal信息
        ttaProposalTermLineFind:webconfig.url.ttaServer + 'ttaProposalTermsLineService/find', //
        //PromotionGuideline
        promotionGuidelineImport: webconfig.url.ttaServer + 'ttaPromotionGuidelineService/dataImport', //
        promotionGuidelineFind: webconfig.url.ttaServer + 'ttaPromotionGuidelineService/find', //
        promotionGuidelineDel:webconfig.url.ttaServer + 'ttaPromotionGuidelineService/delete',//
        promotionGuidelineSave:webconfig.url.ttaServer + 'ttaPromotionGuidelineService/saveOrUpdate',//
        //proposal 报表信息
        ttaProposalStatusFind :webconfig.url.ttaServer + 'ttaProposalStatusService/find', //
        ttaProposalStatusExport :webconfig.url.ttaServer + 'ttaProposalStatusService/export', //
        findBusinessBookProces: webconfig.url.ttaServer + 'ttaProposalStatusService/findBusinessBookProces', //业务合同书流程进度
        findExclusiveProces: webconfig.url.ttaServer + 'ttaProposalStatusService/findExclusiveProces', //独家协议流程进度
        //地理信息位置维护
        ttaSalesSiteFind: webconfig.url.ttaServer + 'ttaSalesSiteService/findPagination', //
        ttaSalesSiteDel:webconfig.url.ttaServer + 'ttaSalesSiteService/deleteImportOsdInfo',//
        ttaSalesSiteSave:webconfig.url.ttaServer + 'ttaSalesSiteService/saveImportOsdInfo',//
        ttaSalesSiteSaveOrUpdate:webconfig.url.ttaServer + 'ttaSalesSiteService/saveOrUpdate',//

        //报表头管理
        reportHeaderFind: webconfig.url.ttaServer + 'ttaReportHeaderService/find', //
        processReportHeaderFind: webconfig.url.ttaServer + 'ttaReportHeaderService/find', //
        saveOrUpdateReportHeader: webconfig.url.ttaServer + 'ttaReportHeaderService/saveOrUpdate', //
        deleteReportHeader: webconfig.url.ttaServer + 'ttaReportHeaderService/deleteReportHeader',


        findTtaReportProcessHeader: webconfig.url.ttaServer + 'ttaReportProcessHeaderService/find', //
        saveOrUpdateTtaReportProcessHeader: webconfig.url.ttaServer + 'ttaReportProcessHeaderService/saveOrUpdate', //
        findTtaReportProcessHeaderOne: webconfig.url.ttaServer + 'ttaReportProcessHeaderService/findOne', //
        saveOrUpdate2TtaReportProcessHeader: webconfig.url.ttaServer + 'ttaReportProcessHeaderService/saveOrUpdate2', //

        //IR TERM
        irTermsFind: webconfig.url.ttaServer + 'ttaIrTermsService/find', //
        irTermsDel:webconfig.url.ttaServer + 'ttaIrTermsService/delete',//
        irTermsSave:webconfig.url.ttaServer + 'ttaIrTermsService/saveOrUpdate',//

        //免费商品
        freeGoodsFind: webconfig.url.ttaServer + 'ttaFreeGoodsService/find', //
        freeGoodsDel:webconfig.url.ttaServer + 'ttaFreeGoodsService/delete',//
        freeGoodsSave:webconfig.url.ttaServer + 'ttaFreeGoodsService/saveOrUpdate',//
        freeGoodsExcelEmport:webconfig.url.ttaServer + 'ttaFreeGoodsService/freeGoodsExcelEmport',//excel导出
        saveFreeGoodsByPoList:webconfig.url.ttaServer + 'ttaFreeGoodsService/saveFreeGoodsByPoList',

        //MonthlyChecking
        saveImportOsdMajorInfo: webconfig.url.ttaServer + 'ttaMonthlyCheckingService/saveImportOsdMajorInfo', //
        ttaOsdfindAmountList: webconfig.url.ttaServer + 'ttaMonthlyCheckingService/findAmountList', //
        updateImportOsdInfo: webconfig.url.ttaServer + 'ttaMonthlyCheckingService/updateImportOsdInfo', //
        saveOrUpdateTtaOsdMajorFind:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/saveOrUpdateFind',//
        findTtaOsdMajorPagination:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/findPagination',//
        findProcessTtaOsdInfo:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/findProcessPagination',//
        deleteImportTtaOsdMajorInfo:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/deleteImportOsdMajorInfo',//
        saveOrUpdateTtaOsdMajorAll:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/saveOrUpdateAll',//
        saveOrUpdateTtaOsdMajorSplitALL:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/saveOrUpdateSplitALL',//
        saveOrUpdateTtaOsdMajorTransferALL:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/saveOrUpdateTransferALL',//
        findTtaOsdMajorSummaryList:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/findOsdMajorSummaryList',//
        findTtaOsdMajorExportPagination:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/findExportPagination',//
        findProcessSummaryOsdInfo:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/findProcessSummaryOsdInfo',//
        findOsdCheckGroupCount:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/findCheckGroupCount',//
        findOsdNotExistPagination:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/findNotExistPagination',//
        findDmNotExistPagination:webconfig.url.ttaServer + 'ttaDmCheckingService/findDmNotExistPagination',//dm权限控制查看

        //ttaHwbCheckingService
        saveImportHwbMajorInfo: webconfig.url.ttaServer + 'ttaHwbCheckingService/saveImportHwbMajorInfo', //
        findHwbNotExistPagination: webconfig.url.ttaServer + 'ttaHwbCheckingService/findNotExistPagination', //
        updateImportHwbInfo: webconfig.url.ttaServer + 'ttaHwbCheckingService/updateImportHwbInfo', //
        saveOrUpdateTtaHwbMajorFind:webconfig.url.ttaServer + 'ttaHwbCheckingService/saveOrUpdateFind',//
        findTtaHwbMajorPagination:webconfig.url.ttaServer + 'ttaHwbCheckingService/findPagination',//
        deleteImportTtaHwbMajorInfo:webconfig.url.ttaServer + 'ttaHwbCheckingService/deleteImportHwbMajorInfo',//
        saveOrUpdateTtaHwbMajorAll:webconfig.url.ttaServer + 'ttaHwbCheckingService/saveOrUpdateAll',//
        saveOrUpdateTtaHwbMajorSplitALL:webconfig.url.ttaServer + 'ttaHwbCheckingService/saveOrUpdateSplitALL',//
        saveOrUpdateTtaHwbMajorTransferALL:webconfig.url.ttaServer + 'ttaHwbCheckingService/saveOrUpdateTransferALL',//
        findTtaHwbMajorSummaryList:webconfig.url.ttaServer + 'ttaHwbCheckingService/findHwbMajorSummaryList',//
        findTtaHwbMajorExportPagination:webconfig.url.ttaServer + 'ttaHwbCheckingService/findExportPagination',//
        findProcessTtaHwbInfo:webconfig.url.ttaServer + 'ttaHwbCheckingService/findProcessPagination',//
        findProcessSummaryHwbInfo:webconfig.url.ttaServer + 'ttaHwbCheckingService/findProcessSummaryHwbInfo',//
        findHwbCheckGroupCount:webconfig.url.ttaServer + 'ttaHwbCheckingService/findCheckGroupCount',//


        //ttaOsdBaseLineService
        saveTtaImportOsdBaseInfo:webconfig.url.ttaServer + 'ttaOsdBaseLineService/saveImportOsdBaseInfo',//
        findTtaOsdBaseLinePagination:webconfig.url.ttaServer + 'ttaOsdBaseLineService/findPagination',//
        deleteTtaImportOsdBaseInfo:webconfig.url.ttaServer + 'ttaOsdBaseLineService/deleteImportOsdBaseInfo',//

        //CWChecking
        cwCheckingFind: webconfig.url.ttaServer + 'ttaMonthlyCheckingService/findcw', //
        cwCheckingDel:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/delete',//
        cwCheckingSave:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/saveOrUpdate',//
        cwCheckingVendor:webconfig.url.ttaServer + 'ttaMonthlyCheckingService/vendor',//

        //ttaCwChecking
        saveTtaImportCwInfo: webconfig.url.ttaServer + 'ttaCwCheckingService/saveImportCwInfo', //
        ttaCwfindAmountList: webconfig.url.ttaServer + 'ttaCwCheckingService/findAmountList', //
        findCwNotExistPagination: webconfig.url.ttaServer + 'ttaCwCheckingService/findNotExistPagination', //
        updateImportCwInfo: webconfig.url.ttaServer + 'ttaCwCheckingService/updateImportCwInfo', //
        findTtaCwInfo:webconfig.url.ttaServer + 'ttaCwCheckingService/findPagination',//
        deleteTtaImportCwInfo:webconfig.url.ttaServer + 'ttaCwCheckingService/deleteImportCwInfo',//
        saveOrUpdateFindTtaCw:webconfig.url.ttaServer + 'ttaCwCheckingService/saveOrUpdateFind',//
        saveOrUpdateALLTtaCw:webconfig.url.ttaServer + 'ttaCwCheckingService/saveOrUpdateAll',//
        saveOrUpdateSplitALLTtaCw:webconfig.url.ttaServer + 'ttaCwCheckingService/saveOrUpdateSplitALL',//
        saveOrUpdateTransferALLTtaCw:webconfig.url.ttaServer + 'ttaCwCheckingService/saveOrUpdateTransferALL',//
        findTtaCwSummaryList:webconfig.url.ttaServer + 'ttaCwCheckingService/findCwSummaryList',//
        findTtaExportPagination:webconfig.url.ttaServer + 'ttaCwCheckingService/findExportPagination',//
        findProcessTtaCwInfo:webconfig.url.ttaServer + 'ttaCwCheckingService/findProcessPagination',//
        findProcessSummaryCwInfo:webconfig.url.ttaServer + 'ttaCwCheckingService/findProcessSummaryCwInfo',//
        findCwCheckGroupCount:webconfig.url.ttaServer + 'ttaCwCheckingService/findCheckGroupCount',//
        //ttaDMChecking
        saveTtaImportDmInfo: webconfig.url.ttaServer + 'ttaDmCheckingService/saveImportDmInfo', //
        findTtaDmInfo:webconfig.url.ttaServer + 'ttaDmCheckingService/findPagination',//
        findProcessTtaDmInfo:webconfig.url.ttaServer + 'ttaDmCheckingService/findProcessPagination',//
        findProcessSummaryDmInfo: webconfig.url.ttaServer + 'ttaDmCheckingService/findProcessSummaryDmInfo',//
        deleteTtaImportDmInfo:webconfig.url.ttaServer + 'ttaDmCheckingService/deleteImportDmInfo',//
        saveOrUpdateFindTtaDm:webconfig.url.ttaServer + 'ttaDmCheckingService/saveOrUpdateFind',//
        saveOrUpdateALLTtaDm:webconfig.url.ttaServer + 'ttaDmCheckingService/saveOrUpdateAll',//
        saveOrUpdateSplitALLTtaDm:webconfig.url.ttaServer + 'ttaDmCheckingService/saveOrUpdateSplitALL',//
        saveOrUpdateTransferALLTtaDm:webconfig.url.ttaServer + 'ttaDmCheckingService/saveOrUpdateTransferALL',//
        findCheckGroupCount: webconfig.url.ttaServer + 'ttaDmCheckingService/findCheckGroupCount',//
        findChangeVender: webconfig.url.ttaServer + 'ttaDmCheckingService/findChangeVender',//

        //promotionalLeafletDW
        promotionalLeafletDWFind: webconfig.url.ttaServer + 'ttaPromotionalLeafletService/find', //
        promotionalLeafletDWDel:webconfig.url.ttaServer + 'ttaPromotionalLeafletService/delete',//
        promotionalLeafletDWSave:webconfig.url.ttaServer + 'ttaPromotionalLeafletService/saveOrUpdate',//
        promotionalLeafletDWVendor:webconfig.url.ttaServer + 'ttaPromotionalLeafletService/vendor',//

        //------------------NPP 新品报表--------------------
        saveOrUpdateByNppQuery:webconfig.url.ttaServer + 'ttaNppNewProductReportService/saveOrUpdateByNppQuery',
        findTtaNppInfo:webconfig.url.ttaServer + 'ttaNppNewProductReportService/findPagination',
        saveOrUpdateSplitALLTtaNpp:webconfig.url.ttaServer + 'ttaNppNewProductReportService/saveOrUpdateSplitALL',
        saveOrUpdateALLTtaNpp:webconfig.url.ttaServer + 'ttaNppNewProductReportService/saveOrUpdateAll',
        saveOrUpdateTransferALLTtaNpp:webconfig.url.ttaServer + 'ttaNppNewProductReportService/saveOrUpdateTransferALL',//
        findProcessSummaryNPPInfo:webconfig.url.ttaServer + 'ttaNppNewProductReportService/findProcessSummaryNppInfo',//
        findProcessTtaNppInfo:webconfig.url.ttaServer + 'ttaNppNewProductReportService/findProcessPagination',//
        findNPPNotExistPagination:webconfig.url.ttaServer + 'ttaNppNewProductReportService/findNotExistPagination',//
        findChangeVenderByNpp: webconfig.url.ttaServer + 'ttaNppNewProductReportService/findChangeVender',//

        //合同头信息
        contractHeaderFind: webconfig.url.ttaServer + 'ttaContractHeaderService/find', //
        contractHeaderDel:webconfig.url.ttaServer + 'ttaContractHeaderService/delete',//
        contractHeaderSave:webconfig.url.ttaServer + 'ttaContractHeaderService/saveOrUpdate',//
        contractHeaderFindById:webconfig.url.ttaServer + 'ttaContractHeaderService/findById',//

        contractHeaderfindTerm:webconfig.url.ttaServer + 'ttaContractHeaderService/findTerm',//
        contractHeaderCallAnalysis:webconfig.url.ttaServer + 'ttaContractHeaderService/callAnalysis',//
        contractHeaderSaveAnalysis:webconfig.url.ttaServer + 'ttaContractHeaderService/saveAnalysis',//

        analysisLineReloadSaveAnalysis:webconfig.url.ttaServer + 'ttaAnalysisLineService/reloadAnalysisSave',//
        analysisLineFind:webconfig.url.ttaServer + 'ttaAnalysisLineService/findAnalysisData',

        contractHeaderFindLov: webconfig.url.ttaServer + 'ttaContractHeaderService/findLov', //
        contractHeaderConfirm:webconfig.url.ttaServer + 'ttaContractHeaderService/confirm',//
        contractHeaderCancelConfirm:webconfig.url.ttaServer + 'ttaContractHeaderService/cancelConfirm',//
        contractHeaderCount: webconfig.url.ttaServer + 'ttaContractHeaderService/count', //


        //合同行信息
        contractLineFindPro: webconfig.url.ttaServer + 'ttaContractLineService/findPro', //
        contractLineFind: webconfig.url.ttaServer + 'ttaContractLineService/find', //
        contractLineDel:webconfig.url.ttaServer + 'ttaContractLineService/delete',//
        contractLineDelRow:webconfig.url.ttaServer + 'ttaContractLineService/deleteRows',//
        contractLineSave:webconfig.url.ttaServer + 'ttaContractLineService/saveSplitVenders',//
        contractLineSaveOrUpdate:webconfig.url.ttaServer + 'ttaContractLineService/saveOrUpdate',//
        contractLineFindById:webconfig.url.ttaServer + 'ttaContractLineService/findById',//
        saveBatchSplitResult: webconfig.url.ttaServer + 'ttaContractLineService/saveBatchSplitResult', //保存供应商拆分结果

        contractLineFindLov: webconfig.url.ttaServer + 'ttaContractLineService/findLov', //
        contractLineFindTtaSummaryById: webconfig.url.ttaServer + 'ttaContractLineService/findTtaSummaryById',
        //合同明细信息
        contractDetailFind: webconfig.url.ttaServer + 'ttaContractDetailService/find', //
        contractDetailDel:webconfig.url.ttaServer + 'ttaContractDetailService/delete',//
        contractDetailSave:webconfig.url.ttaServer + 'ttaContractDetailService/saveOrUpdate',//
        contractDetailFindById:webconfig.url.ttaServer + 'ttaContractDetailService/findById',//

        contractDetailFindLov: webconfig.url.ttaServer + 'ttaContractDetailService/findLov', //



        // tta terms头信息
        termsHFind: webconfig.url.ttaServer + 'ttaTermsHService/find', //
        termsHDel:webconfig.url.ttaServer + 'ttaTermsHService/delete',//
        termsHSave:webconfig.url.ttaServer + 'ttaTermsHService/saveOrUpdate',//
        termsHFindById:webconfig.url.ttaServer + 'ttaTermsHService/findById',//
        termsHFindLov: webconfig.url.ttaServer + 'ttaTermsHService/findLov', //
        //termsHFindArea: webconfig.url.ttaServer + 'ttaTermsHService/findArea', //

        // tta terms行信息
        termsLFind: webconfig.url.ttaServer + 'ttaTermsLService/find', //
        termsLFindMethod: webconfig.url.ttaServer + 'ttaTermsLService/findMethod', //
        termsLDel:webconfig.url.ttaServer + 'ttaTermsLService/delete',//
        termsLCountFee:webconfig.url.ttaServer + 'ttaTermsLService/saveOrUpdateCountFee',//
        termsLSave:webconfig.url.ttaServer + 'ttaTermsLService/saveOrUpdate',//
        termsLSaveAll:webconfig.url.ttaServer + 'ttaTermsLService/saveOrUpdateAll',//
        termsLFindById:webconfig.url.ttaServer + 'ttaTermsLService/findById',//
        termsLFindLov: webconfig.url.ttaServer + 'ttaTermsLService/findLov', //
        termsLfindValue: webconfig.url.ttaServer + 'ttaTermsLService/findValue', //
        termsLComfirm: webconfig.url.ttaServer + 'ttaTermsLService/saveTtaTermsLComfirm', //
        termsLCancelComfirm: webconfig.url.ttaServer + 'ttaTermsLService/saveTtaTermsLCancelComfirm', //
        termsLDeleteDataByYterms:webconfig.url.ttaServer + 'ttaDeptFeeLineService/deleteDeptFeeByProposalAndDmFlyer',//删除部门协定标准中表格数据

        //查询单位
        termsLUnit: webconfig.url.ttaServer + 'ttaCollectTypeLineService/findUnit', //


        //模板参数设定
        paramsFind: webconfig.url.ttaServer + 'tempParamDefService/find', //查询
        paramsFindById: webconfig.url.ttaServer + 'tempParamDefService/findById', //查询id
        paramsSave: webconfig.url.ttaServer + 'tempParamDefService/saveOrUpdate', //保存
        paramsDel: webconfig.url.ttaServer + 'tempParamDefService/delete', //删除

        //模板规则
        findRule: webconfig.url.ttaServer + 'tempRuleDefService/findRulePagination', // 查询
        findRuleById: webconfig.url.ttaServer + 'tempRuleDefService/findById', //查询规则
        findParams: webconfig.url.ttaServer + 'tempRuleDefService/findParams', //查找参数信息
        findNotExistsList: webconfig.url.ttaServer + 'tempRuleDefService/findNotExistsList', //查询规则已排除的列表项
        saveParams : webconfig.url.ttaServer + 'tempParamRuleMidleService/saveParams', //批量保存参数信息
        deleteParamById : webconfig.url.ttaServer + 'tempParamRuleMidleService/deleteParamById',//通过id删除参数信息
        saveOrUpdate : webconfig.url.ttaServer + 'tempRuleDefService/saveOrUpdate',//保存或更新规则信息
        wordTempdownload : webconfig.url.ttaServer + 'tempRuleDefService/fileDownload',//模板下载
        ruleFileUpload: webconfig.url.ttaServer + '/tempRuleDefService/ruleFileUpload',//规则文件上传

        //实际发生费用分摊规则设置
        findCost: webconfig.url.ttaServer + 'ttaActualCostRuleService/findCostPagination', // 分页查询
        findCostById: webconfig.url.ttaServer + 'ttaActualCostRuleService/findCostById', //通过id查询
        saveOrUpdateCostRule: webconfig.url.ttaServer + 'ttaActualCostRuleService/saveOrUpdateCostRule', //更新或保存
        deleteById: webconfig.url.ttaServer + 'ttaActualCostRuleService/deleteById',//删除


        /* 调查问卷 start*/
        importNewProductList: webconfig.url.ttaServer + '/ttaQuestionNewMapDetailService/importNewProductList',
        findSaafQuestionnaireList: webconfig.url.ttaServer  + 'saafQuestionnaireService/findSaafQuestionnaireList',
        findSaafQuestionnaireById: webconfig.url.ttaServer  + 'saafQuestionnaireService/findSaafQuestionnaireById',
        findSaafQuestionnaireByIdTest:webconfig.url.ttaServer  +'ttaTestQuestionChoiceLineService/findRecQuestionHeaderList',
        findTestQuestionnaireByProposalId: webconfig.url.ttaServer  + 'saafQuestionnaireService/findTestQuestionnaireByProposalId',
        submitQuestionTest: webconfig.url.ttaServer  +'ttaTestQuestionChoiceLineService/submitQuestionTest',
        cancelQuestionTest: webconfig.url.ttaServer  +'ttaTestQuestionChoiceLineService/cancelQuestionTest',
        findSaafQuestionnaire: webconfig.url.ttaServer  + 'saafQuestionnaireService/findSaafQuestionnaire',
        findSaafQuestionnaires: webconfig.url.ttaServer  + 'saafQuestionnaireService/findSaafQuestionnaires',
        saveSaafQuestionnaireH: webconfig.url.ttaServer  + 'saafQuestionnaireService/saveSaafQuestionnaireH',
        deleteSaafQuestionnaireH: webconfig.url.ttaServer  + 'saafQuestionnaireService/deleteSaafQuestionnaireH',
        updateSaafQuestionnaireToCopy: webconfig.url.ttaServer  + 'saafQuestionnaireService/updateSaafQuestionnaireToCopy',

        saveSaafQuestionnaireL: webconfig.url.ttaServer  + 'saafQuestionnaireService/saveSaafQuestionnaireL',
        deleteSurveyChoice: webconfig.url.ttaServer  + 'saafQuestionnaireService/deleteSurveyChoice',
        deleteSaafQuestionnaireL: webconfig.url.ttaServer  + 'saafQuestionnaireService/deleteSaafQuestionnaireL',
        findSelectProjectList: webconfig.url.ttaServer  + 'saafQuestionnaireService/findSelectProjectList',

        saveSaafQuestionnairePublish: webconfig.url.ttaServer  + 'saafQuestionnaireService/saveSaafQuestionnairePublish',
        updateSaafQuestionnaireToAbandon: webconfig.url.ttaServer  + 'saafQuestionnaireService/updateSaafQuestionnaireToAbandon',

        deleteSaafQuestionnairePublish: webconfig.url.ttaServer  + 'saafQuestionnaireService/deleteSaafQuestionnairePublish',
        findSaafQuestionnairePublishList: webconfig.url.ttaServer  + 'saafQuestionnaireService/findSaafQuestionnairePublishList',
        updateSaafQuestionnairePublishToPublish: webconfig.url.ttaServer  + 'saafQuestionnaireService/updateSaafQuestionnairePublishToPublish',
        saveSaafQuestionnaireResult: webconfig.url.ttaServer  + 'saafQuestionnaireService/saveSaafQuestionnaireResult',
        findUserByInstIdList: webconfig.url.ttaServer  + 'saafQuestionnaireService/findUserByInstIdList',
        saveSubmitSaafQuestionnaire: webconfig.url.ttaServer  + 'saafQuestionnaireService/saveSubmitSaafQuestionnaire',


        findSaafTestQuestionnaireList: webconfig.url.ttaServer  + 'saafTestQuestionnaireHService/findSaafTestQuestionnaireList',
        saveSaafTestQuestionnaireH: webconfig.url.ttaServer  + 'saafTestQuestionnaireHService/saveSaafTestQuestionnaireH',
        findQuestionnaireLAndQuestionnaireChoice: webconfig.url.ttaServer  + 'saafQuestionnaireService/findQuestionnaireLAndQuestionnaireChoice',
        saveSaafTestQuestionnaireL: webconfig.url.ttaServer  + 'saafTestQuestionnaireHService/saveSaafTestQuestionnaireL',
        findLineAndchoice: webconfig.url.ttaServer  + 'saafTestQuestionnaireHService/findLineAndchoice',
        deleteLine: webconfig.url.ttaServer  + 'saafTestQuestionnaireHService/deleteLine',
        findSaafTestQuestionnaireLov: webconfig.url.ttaServer  + 'saafTestQuestionnaireHService/findSaafTestQuestionnaireLov',

        findResultPerson: webconfig.url.ttaServer  + 'saafQuestionnaireService/findResultPerson',
        findSaafQuestionnaireResult: webconfig.url.ttaServer  + 'saafQuestionnaireService/findSaafQuestionnaireResult',
        updateSaafQuestionnaireStatus: webconfig.url.ttaServer  + 'saafQuestionnaireService/updateSaafQuestionnaireStatus',
        findResultReport: webconfig.url.ttaServer  + 'saafQuestionnaireService/findResultReport',
        findAnswerResultList: webconfig.url.ttaServer  + 'saafQuestionnaireService/findAnswerResultList',

        findSaafQuestionnaireListByMobile: webconfig.url.ttaServer  + 'saafQuestionnaireService/findSaafQuestionnaireListByMobile',
        findSaafQuestionnaireByPublishId: webconfig.url.ttaServer  + 'saafQuestionnaireService/findSaafQuestionnaireByPublishId',
        findSaafQuestionnaireResultByUserId: webconfig.url.ttaServer  + 'saafQuestionnaireService/findSaafQuestionnaireResultByUserId',

        updateSaafQuestionnairePublishStatus: webconfig.url.ttaServer  + 'saafQuestionnaireService/updateSaafQuestionnairePublishStatus',
        saveSubmitSaafQuestionnairePublish: webconfig.url.ttaServer  + 'saafQuestionnaireService/saveSubmitSaafQuestionnairePublish',

        findSecQuestionnairePagination :   webconfig.url.ttaServer  + '/saafQuestionnaireLService/findSecQuestionnairePagination',//查询二级题目信息

        findPaginationQuestionHeader : webconfig.url.ttaServer  + '/ttaQuestionHeaderService/findPaginationQuestionHeader', //查询问卷头部信息
        findQuestionByHeaderId : webconfig.url.ttaServer  + '/ttaQuestionHeaderService/findQuestionByHeaderId', //查询问卷头及行信息
        saveOrUpdateQuestionALL : webconfig.url.ttaServer  + '/ttaQuestionHeaderService/saveOrUpdateALL', //保存问卷头行信息
        deleteQuestionHeaderOrLine: webconfig.url.ttaServer  + '/ttaQuestionHeaderService/deleteQuestionHeaderOrLine', //删除头或行信息，如果删除头信息级联删除行信息
        findQuestionChoiceLineChild : webconfig.url.ttaServer  + '/ttaQuestionChoiceLineService/findQuestionChoiceLineChild',//查询选项下级列表
        saveOrUpadateChoiceLineList : webconfig.url.ttaServer  + '/ttaQuestionChoiceLineService/saveOrUpadateChoiceLineList',//保存下层选项
        deleteQuestionNewMapDetail :  webconfig.url.ttaServer +  '/ttaQuestionNewMapDetailService/deleteQuestionNewMapDetail', //删除问卷上图新品行记录
        queryQuestionNewMapDetailList : webconfig.url.ttaServer +  '/ttaQuestionNewMapDetailService/queryQuestionNewMapDetailList', //查询问卷上图新品行记录
        saveOrUpadateBatchDetail : webconfig.url.ttaServer +  '/ttaQuestionNewMapDetailService/saveOrUpadateBatchDetail', //批量保存新品上图行信息
        /* 调查问卷 end*/

        /* 规则管理end*/
        editRuleHeader: webconfig.url.ttaServer  + 'ruleHService/editRuleHeader',                   //保存头
        editRuleLine: webconfig.url.ttaServer  + 'ruleHService/editRuleLine',
        editSubject: webconfig.url.ttaServer  + 'ruleHService/editSubject',
        deleteRuleLine: webconfig.url.ttaServer  + 'ruleHService/deleteRuleLine',
        findRuleHeaderPagination: webconfig.url.ttaServer  + 'ttaBaseRuleHeaderService/findRuleHeaderPagination',
        findRuleLinePagination: webconfig.url.ttaServer  + 'ttaBaseRuleHeaderService/findRuleLinePagination',
        findCheckRulePagination:  webconfig.url.ttaServer  + 'ttaBaseRuleHeaderService/findCheckRulePagination', //查询选项题
        saveCheckRuleList :  webconfig.url.ttaServer  + 'ttaBaseRuleHeaderService/saveCheckRuleList',
        saveRuleHeader : webconfig.url.ttaServer  + 'ttaBaseRuleHeaderService/saveRuleHeader',
        findCheckChildRulePagination : webconfig.url.ttaServer  + 'ttaBaseRuleHeaderService/findCheckChildRulePagination',
        saveChildRule : webconfig.url.ttaServer  + 'ttaBaseRuleHeaderService/saveChildRule', //保存规则信息
        updateChildRuleStatus : webconfig.url.ttaServer  + 'ttaBaseRuleHeaderService/updateChildRuleStatus',

        /* 拆分管理end*/
        editTtaSupplierItemHeaderEntityH: webconfig.url.ttaServer  + 'ttaSupplierItemHeaderService/saveOrUpdate',                   //保存头
        findTtaSupplierItemHeaderInfoList: webconfig.url.ttaServer  + 'ttaSupplierItemHeaderService/findTtaSupplierItemHeaderInfoList',//查询proposal拆分与合并头信息
        findTtaSupplierEntity_HI_RO: webconfig.url.ttaServer  + 'ttaSupplierItemHeaderService/findTtaSupplierEntity_HI_RO',
        ttaSupplierItemMidUpdateSupplier: webconfig.url.ttaServer  + 'ttaSupplierItemMidService/saveOrUpdate',//给符合条件明细的数据指定供应商
        findSplitDetailList:webconfig.url.ttaServer  + 'ttaSupplierItemMidService/findSplitDetailList',
        ttaSupplierItemSubmitBill: webconfig.url.ttaServer  + 'ttaSupplierItemHeaderService/ttaSupplierItemSubmitBill',//提交
        submitTask: webconfig.url.ttaServer  + 'ttaSupplierItemHeaderService/submitTask',//提交请求队列
        appointVendorNbrStatus: webconfig.url.ttaServer  + 'ttaSupplierItemHeaderService/appointVendorNbrStatus',//查询执行状态
        ttaSupplierItemDiscard: webconfig.url.ttaServer  + 'ttaSupplierItemHeaderService/ttaSupplierItemDiscard',//作废
        createTtaSupplierItemHeaderSplitConditionDetail:  webconfig.url.ttaServer  + 'ttaSupplierItemHeaderService/ttaSupplierItemSplitConditionDetail',//生成符合拆分条件明细
        ttaSupplierItemRelSupplierFind : webconfig.url.ttaServer  + 'ttaSupplierItemRelSupplierService/find',//根据proposal拆分与合并的单据供应商查询关联供应商
        selectSupplierItemRelSupplierList: webconfig.url.ttaServer  + 'ttaSupplierItemRelSupplierService/selectSupplierItemRelSupplierList',//根据供应商查询关联供应商
        ttaSupplierItemRelSupplierSave:webconfig.url.ttaServer  + 'ttaSupplierItemRelSupplierService/ttaSupplierItemRelSupplierSave',//保存关联供应商数据
        ttaSupplierItemRelSupplierDelete:webconfig.url.ttaServer  + 'ttaSupplierItemRelSupplierService/ttaSupplierItemRelSupplierDelete',//删除关联供应商数据
        ttaSupplierItemSchduelPurchaseStatus: webconfig.url.ttaServer  + 'ttaSupplierItemHeaderService/ttaSupplierItemSchduelPurchaseStatus',//定时轮询查单据状态
        ttaSupplierItemMidServiceSplitConditionDetailSave: webconfig.url.ttaServer  + 'ttaSupplierItemMidService/saveSplitConditionDetail',//保存拆分条件明细数据
        ttaSupplierItemMidServiceCheckSplitData: webconfig.url.ttaServer  + 'ttaSupplierItemMidService/checkSplitConditionDetail',//检查拆分明细数据
        saveWithdrawalSplitDataImport:webconfig.url.ttaServer  + 'ttaSupplierItemMidService/saveWithdrawalSplitDataImport',//excel导入

        /* 补充协议end*/
        saveTtaSideAgrtHeader: webconfig.url.ttaServer  + 'ttaSideAgrtHeaderService/saveTtaSideAgrtHeader',                   //保存头
        findSideAgrtHeaderList: webconfig.url.ttaServer  + 'ttaSideAgrtHeaderService/findSideAgrtHeaderList',
        /*findTtaSupplierEntity_HI_RO: webconfig.url.ttaServer  + 'ttaSideAgrtHeaderService/findTtaSupplierEntity_HI_RO',*/
        findBaseAttachmentList: webconfig.url.ttaServer  + 'ttaSideAgrtHeaderService/findBaseAttachmentList',//查询附件
        submitTtaSideAgrtHeader: webconfig.url.ttaServer  + 'ttaSideAgrtHeaderService/submitTtaSideAgrtHeader',//提交
        saveTtaPoposalToSideAgrtLine: webconfig.url.ttaServer  + 'ttaSideAgrtLineService/saveTtaPoposalToSideAgrtLine',//保存poposal供应商的数据
        findTtaProposalToSideAgrtLine: webconfig.url.ttaServer  + 'ttaSideAgrtLineService/find',
        ttaProposalToSideAgrtLineDelete: webconfig.url.ttaServer  + 'ttaSideAgrtLineService/deleteById',
        disSicradTtaSideAgrtHeader: webconfig.url.ttaServer  + 'ttaSideAgrtHeaderService/disSicradTtaSideAgrtHeader',//作废
        ttaSideAgrtHeaderUpload : webconfig.url.ttaServer  + 'ttaSideAgrtHeaderService/ttaSideAgrtHeaderUploadFile',//文件上传
        ttaSideAgrtHeaderFileDelete : webconfig.url.ttaServer  + 'ttaSideAgrtHeaderService/ttaSideAgrtHeaderFileDelete',//删除附件
        ttaSideAgrtHeaderDownLoad : webconfig.url.ttaServer  + 'ttaSideAgrtHeaderService/fileDownload',//文件下载
        ttaSideAgrtHeaderDownLoadBIC : webconfig.url.ttaServer  + 'ttaSideAgrtHeaderService/fileDownloadByBIC',//文件下载
        ttaAttachmentDownloadFind : webconfig.url.ttaServer  + 'ttaAttachmentDownloadService/ttaAttachmentDownloadFind',//文件下载
        ttaSideAgrtHeaderDownLoadType : webconfig.url.ttaServer  + 'ttaSideAgrtHeaderService/fileDownloadType',//文件下载

        ttaConAttrLineUploadFile : webconfig.url.ttaServer  + 'ttaProposalConAttrLineService/ttaConAttrLineUploadFile',//文件上传
        ttaConAttrLineFindFileIds : webconfig.url.ttaServer  + 'ttaProposalConAttrLineService/findFileIds',//文件上传
        ttaProposalConAttrLineFndPagination : webconfig.url.ttaServer  + 'ttaProposalConAttrLineService/findPagination',//
        ttaConAttrLineDeptUploadFile : webconfig.url.ttaServer  + 'ttaProposalConAttrLineService/ttaConAttrLineDeptUploadFile',//文件上传
        ttaConAttrLineDeleteInfo : webconfig.url.ttaServer  + 'ttaProposalConAttrLineService/deleteInfo',//
        findSupplierAttachmentList: webconfig.url.ttaServer  + 'ttaSupplierAttrService/findSupplierAttachmentList',//文件查询
        deletAttr: webconfig.url.ttaServer  + 'ttaSupplierAttrService/deletAttr',//删除附件
        ttaSupplierAttrUploadFile: webconfig.url.ttaServer  + 'ttaSupplierAttrService/ttaSupplierAttrUploadFile',//文件上传

        //补充协议标准
        findSaStdHeaderList: webconfig.url.ttaServer  + 'ttaSaStdHeaderService/find',
        supplementAgreementTemplateList:webconfig.url.ttaServer  + 'ttaSaStdHeaderService/findTemplateTreeData',//补充协议标准模板树
        supplementAgreementStandardPrint:webconfig.url.ttaServer  + 'ttaSaStdHeaderService/print',//合同打印
        ttaSupplementAgreementSave:webconfig.url.ttaServer  + 'ttaSaStdHeaderService/saveOrUpdate',
        ttaSupplementAgreementDisSicrad:webconfig.url.ttaServer  + 'ttaSaStdHeaderService/disSicrad',//作废
        ttaSupplementAgreementChange:webconfig.url.ttaServer  + 'ttaSaStdHeaderService/supplementAgreementStandardChange',//变更
        ttaSupplementAgreementFind:webconfig.url.ttaServer  + 'ttaSaStdHeaderService/findById',
        ttaSupplementExpandFind:webconfig.url.ttaServer  + 'ttaSaStdHeaderService/supplementExpandFind',
        contractProposalVendorList:webconfig.url.ttaServer  + 'ttaSaStdProposalLineService/findProposalVendor',
        saveContractProposalVendor:webconfig.url.ttaServer  + 'ttaSaStdProposalLineService/saveContractProposalVendor',
        contractProposalVendorFind:webconfig.url.ttaServer  + 'ttaSaStdProposalLineService/find',
        contractProposalVendorDelete:webconfig.url.ttaServer  + 'ttaSaStdProposalLineService/deleteById',
        findSupplementExpandInfo:webconfig.url.ttaServer  + 'ttaSaStdFieldLineService/findSupplementExpandInfo',
        supplementAgreementTtaSaTplTabLineList:webconfig.url.ttaServer  + 'ttaSaTabLineService/findTtaSaTplTabLineList',
        supplementAgreementSaveOrUpdateAllTabLine:webconfig.url.ttaServer  + 'ttaSaTabLineService/saveOrUpdateAll',
        supplementAgreementSandardTtaSaTplTabLineFind:webconfig.url.ttaServer  + 'ttaSaTabLineService/findSupplementAgreementSandardTabLine',
        updateSupplementSkipStatus:webconfig.url.ttaServer  + 'ttaSaStdHeaderService/updateSupplementSkipStatus',//更新GM审批状态

        //TTA Terms Comparision(TY vs LY) 报表查询
        findTermsComparision: webconfig.url.ttaServer + '/termsComparisionReport/find',
        //记录打印次数
        saveOrUpdatePrintCount: webconfig.url.ttaServer + '/ttaPrintRecordService/saveOrUpdatePrintCount',
        //获取打印次数
        getPrintCount: webconfig.url.ttaServer + '/ttaPrintRecordService/getPrintCount',

        //独家协议(标准)
        saveOrUpdateTtaSoleAgrt: webconfig.url.ttaServer  + 'ttaSoleAgrtService/saveOrUpdateTtaSoleAgrt',                   //保存头
        editTtaSoleAgrtInfo: webconfig.url.ttaServer  + 'ttaSoleAgrtService/editTtaSoleAgrtInfo',
        ttaSoleAgrtSubmitInfo:  webconfig.url.ttaServer  + 'ttaSoleAgrtService/ttaSoleAgrtSubmitInfo',//提交
        ttaSoleAgrtDiscard:  webconfig.url.ttaServer  + 'ttaSoleAgrtService/ttaSoleAgrtDiscard',//作废
        ttaSoleAgrtCancal:  webconfig.url.ttaServer  + 'ttaSoleAgrtService/ttaSoleAgrtCancal',//作废
        ttaSoleAgrtChange:  webconfig.url.ttaServer  + 'ttaSoleAgrtService/ttaSoleAgrtChange',
        ttaSoleAgrtContractFind: webconfig.url.ttaServer  + 'ttaSoleAgrtService/ttaSoleAgrtContractFind',
        ttaSoleAgrtContractDownload: webconfig.url.ttaServer  + 'ttaSoleAgrtService/download',
        findTtaSoleAgrtHeader: webconfig.url.ttaServer  + 'ttaSoleAgrtService/findById',
        findTtaSoleAgrtPagination: webconfig.url.ttaServer  + 'ttaSoleAgrtService/findTtaSoleAgrtPagination',//查询独家协议主信息
        proposalSupplierList: webconfig.url.ttaServer  + 'ttaSoleSupplierService/findPagination',//查询proposal供应商(合同拆分之后的结果)
        ttaProposalSupplierFind:webconfig.url.ttaServer  + 'ttaSoleSupplierService/find',//查询proposal供应商
        ttaProposalSupplierSave: webconfig.url.ttaServer  + 'ttaSoleSupplierService/save',//保存proposal供应商
        ttaProposalSupplierDelete:webconfig.url.ttaServer  + 'ttaSoleSupplierService/deleteById',//删除
        ttaProposalSupplierSaveForSplitMerge: webconfig.url.ttaServer  + 'ttaSoleSupplierService/ttaProposalSupplierSaveForSplitMerge',//保存proposal供应商,针对proposal拆分与合并模块
        updateExclusiveSkipStatus: webconfig.url.ttaServer  + 'ttaSoleAgrtService/updateExclusiveSkipStatus',//更新GM审批状态
        checkBillsStatus: webconfig.url.ttaServer  + 'ttaSoleAgrtService/checkBillsStatus',//检查单据状态

        //独家协议信息(Info)
        ttaSoleAgrtInfoSave: webconfig.url.ttaServer  + 'ttaSoleAgrtInfoService/saveOrUpdate',//保存
        ttaSoleAgrtInfoDelete: webconfig.url.ttaServer  + 'ttaSoleAgrtInfoService/delete',//删除
        ttaSoleAgrtInfoFind: webconfig.url.ttaServer  + 'ttaSoleAgrtInfoService/find',//查询
        ttaSoleAgrtInfoBatchSave: webconfig.url.ttaServer  + 'ttaSoleAgrtInfoService/saveBatchSoleAgrtInfo',//批量修改保存soleAgrtInfo信息
        saveImportItemDetail:webconfig.url.ttaServer  + 'ttaSoleAgrtInfoService/saveImportItemDetail',
        findProposalBrand:webconfig.url.ttaServer  + 'ttaSoleAgrtInfoService/findProposalBrand',//查询Proposal品牌
        findProposalDept:webconfig.url.ttaServer  + 'ttaSoleAgrtInfoService/findProposalDept',//查询Proposal DEPT

        //供应商列表弹窗
        supplierlistLov: webconfig.url.ttaServer + 'ttaSoleAgrtInfoService/findSupplier',//查询
        //部门列表弹窗
        departmentList: webconfig.url.ttaServer + 'ttaSoleAgrtInfoService/findItemDept',
        //item列表弹窗
        soleItemList: webconfig.url.ttaServer + 'ttaSoleItemService/findItemList',//查询物料
        findItemListByBrand:webconfig.url.ttaServer + 'ttaSoleItemService/findItemListByBrand',

        //独家协议(标准)的item明细
        ttaSoleItemSave: webconfig.url.ttaServer  + 'ttaSoleItemService/saveOrUpdate',//保存
        ttaSoleItemNewSave: webconfig.url.ttaServer  + 'ttaSoleItemService/saveOrUpdteBySingle',//保存
        ttaSoleItemDelete: webconfig.url.ttaServer  + 'ttaSoleItemService/delete',//删除
        ttaSoleItemFind: webconfig.url.ttaServer  + 'ttaSoleItemService/find',//查询
        ttaSoleItemBatchSave: webconfig.url.ttaServer  + 'ttaSoleItemService/saveBatchSoleItem',//批量保存
        ttaSoleItemFindSingal:webconfig.url.ttaServer  + 'ttaSoleItemService/findSoleItemSingal',
        ttaAddAllItemData: webconfig.url.ttaServer  + 'ttaSoleItemService/addAllItemData',

        //贸易条款
        findTermsAgrement: webconfig.url.ttaServer + '/ttaContractLineReportService/findTermsAgrement',

        //报表

        //OI
        saveImportOIInfo: webconfig.url.ttaServer + '/ttaOiBillLineService/saveImportOIInfo',
        findTtaOiBillLinePagination:webconfig.url.ttaServer + '/ttaOiBillLineService/findPagination',
        deleteImportOIInfo:webconfig.url.ttaServer + '/ttaOiBillLineService/deleteImportOIInfo',

        //ttaBeoiBillLine
        saveImportBEOIInfo: webconfig.url.ttaServer + '/ttaBeoiBillLineService/saveImportBEOIInfo',
        findBEOIPagination:webconfig.url.ttaServer + '/ttaBeoiBillLineService/findPagination',
        deleteImportBEOIInfo:webconfig.url.ttaServer + '/ttaBeoiBillLineService/deleteImportBEOIInfo',

        //ttaAboiSummary
        saveImportAboiSummaryInfo: webconfig.url.ttaServer + '/ttaAboiSummaryService/saveImportAboiSummaryInfo',
        findAboiSummaryPagination:webconfig.url.ttaServer + '/ttaAboiSummaryService/findPagination',
        deleteImportAboiSummaryInfo:webconfig.url.ttaServer + '/ttaAboiSummaryService/deleteImportAboiSummaryInfo',

        //ttaOiSummaryLine
        saveImportOISInfo: webconfig.url.ttaServer + '/ttaOiSummaryLineService/saveImportOISInfo',
        findOISPagination:webconfig.url.ttaServer + '/ttaOiSummaryLineService/findPagination',
        deleteImportOISInfo:webconfig.url.ttaServer + '/ttaOiSummaryLineService/deleteImportOISInfo',

        //ttaSroiBillLine
        saveImportSROIInfo: webconfig.url.ttaServer + '/ttaSroiBillLineService/saveImportSROIInfo',
        findSROIPagination:webconfig.url.ttaServer + '/ttaSroiBillLineService/findPagination',
        deleteImportSROIInfo:webconfig.url.ttaServer + '/ttaSroiBillLineService/deleteImportSROIInfo',
        getOiStatus:webconfig.url.ttaServer + '/ttaSroiBillLineService/getStatus',
        //ttaAboiBillLine
        saveImportABOIInfo: webconfig.url.ttaServer + '/ttaAboiBillLineService/saveImportAboiInfo',
        findABOIPagination:webconfig.url.ttaServer + '/ttaAboiBillLineService/findPagination',
        deleteImportABOIInfo:webconfig.url.ttaServer + '/ttaAboiBillLineService/deleteImportAboiInfo',

        //ttaSupplierItemStoreServer
        saveImportSupplierItemStoreInfo: webconfig.url.ttaServer + '/ttaSupplierItemStoreService/saveImportInfo',
        findSupplierItemStorePagination:webconfig.url.ttaServer + '/ttaSupplierItemStoreService/findPagination',
        deleteImportSupplierItemStoreInfo:webconfig.url.ttaServer + '/ttaSupplierItemStoreService/deleteImportInfo',

        //ttaTesteroiLineService
        saveImportEROIInfo: webconfig.url.ttaServer + '/ttaTesteroiLineService/saveImportInfo',
        findEROIPagination:webconfig.url.ttaServer + '/ttaTesteroiLineService/findPagination',
        deleteImportEROIInfo:webconfig.url.ttaServer + '/ttaTesteroiLineService/deleteImportInfo',

        //ttaOiTax
        saveImportTaxInfo: webconfig.url.ttaServer + '/ttaOiTaxService/saveImportTaxInfo',
        findTAXPagination:webconfig.url.ttaServer + '/ttaOiTaxService/findPagination',
        deleteImportTaxInfo:webconfig.url.ttaServer + '/ttaOiTaxService/deleteImportTaxInfo',

        //ttaTermsAcCount
        saveImportACInfo: webconfig.url.ttaServer + '/ttaTermsAcCountService/saveImportACInfo',
        findACPagination:webconfig.url.ttaServer + '/ttaTermsAcCountService/findPagination',
        deleteImportACInfo:webconfig.url.ttaServer + '/ttaTermsAcCountService/deleteImportACInfo',

        //ttaTradeCalendar
        saveImportTCInfo: webconfig.url.ttaServer + '/ttaTradeCalendarService/saveImportTCInfo',
        findTCPagination:webconfig.url.ttaServer + '/ttaTradeCalendarService/findPagination',
        deleteImportTCInfo:webconfig.url.ttaServer + '/ttaTradeCalendarService/deleteImportTCInfo',

        //ttaSystemCurrentLine
        saveImportTTASCInfo: webconfig.url.ttaServer + '/ttaSystemCurrentLineService/saveImportInfo',
        findTTASCPagination:webconfig.url.ttaServer + '/ttaSystemCurrentLineService/findPagination',
        deleteImportTTASCInfo:webconfig.url.ttaServer + '/ttaSystemCurrentLineService/deleteImportInfo',

        //ttaPogSpaceLine
        saveImportPogInfo: webconfig.url.ttaServer + '/ttaPogSpaceLineService/saveImportPogInfo',
        findPogPagination:webconfig.url.ttaServer + '/ttaPogSpaceLineService/findPagination',
        deleteImportPogInfo:webconfig.url.ttaServer + '/ttaPogSpaceLineService/deleteImportPogInfo',

        //ttaDmLine
        saveImportDmInfo: webconfig.url.ttaServer + '/ttaDmFullLineService/saveImportDmInfo',
        findDmPagination:webconfig.url.ttaServer + '/ttaDmFullLineService/findPagination',
        deleteImportDmInfo:webconfig.url.ttaServer + '/ttaDmFullLineService/deleteImportPogInfo',
        updateImportDmInfo: webconfig.url.ttaServer + '/ttaDmCheckingService/updateImportDmInfo',

        //ttaFreeGoodsPolistService
        saveFreeGoodsInfo: webconfig.url.ttaServer + '/ttaFreeGoodsPolistService/saveOrUpdate',
        saveImportFreeGoodsPolistInfo: webconfig.url.ttaServer + '/ttaFreeGoodsPolistService/saveImportInfo',
        findFreeGoodsPolistPagination:webconfig.url.ttaServer + '/ttaFreeGoodsPolistService/findPagination',
        deleteImportFreeGoodsPolistInfo:webconfig.url.ttaServer + '/ttaFreeGoodsPolistService/deleteImportInfo',
        freeGoodsPolistSave:webconfig.url.ttaServer + '/ttaFreeGoodsPolistService/saveOrUpdate',

        //ttaFreeGoodsOrderDetail
        findFreeGoodsPolistDetailPagination:webconfig.url.ttaServer + '/ttaFreeGoodsOrderDetailService/findPagination',
        freeGoodsPolistDetailSave:webconfig.url.ttaServer + '/ttaFreeGoodsOrderDetailService/saveOrUpdate',
        freeGoodsPolistDetailBatchSave:webconfig.url.ttaServer + '/ttaFreeGoodsOrderDetailService/saveOrUpdateAll',
        freeGoodsPolistDetailBatchJoin:webconfig.url.ttaServer + '/ttaFreeGoodsOrderDetailService/batchJoinCount',
        freeGoodsPolistDetailBatchJoinFeeYear:webconfig.url.ttaServer + '/ttaFreeGoodsOrderDetailService/batchJoinFeeYear',
        freeGoodsPolistDetailDelete:webconfig.url.ttaServer + '/ttaFreeGoodsOrderDetailService/batchDelete',

        //ttaDmFullLine
        saveImportTTADFInfo: webconfig.url.ttaServer + '/ttaDmFullLineService/saveImportInfo',
        findTTADFPagination:webconfig.url.ttaServer + '/ttaDmFullLineService/findPagination',
        deleteImportTTADFInfo:webconfig.url.ttaServer + '/ttaDmFullLineService/deleteImportInfo',
        findDmCreateInfo: webconfig.url.ttaServer + '/ttaDmFullLineService/findDmCreateInfo',//生成dm查询
        saveOrUpdateDmOrder: webconfig.url.ttaServer + '/ttaDmFullLineService/saveOrUpdateDmOrder',//生成单据信息

        /*************** OI报表start *************************/
        saveGpSimulation: webconfig.url.ttaServer + '/ttaOiReportGpSimulationService/saveGpSimulation',//生成单据信息
        oiReportHeaderFind: webconfig.url.ttaServer + '/ttaOiReportHeaderService/find', //报表头信息查询
        findTtaGpInfo:webconfig.url.ttaServer + '/ttaOiReportGpSimulationService/findGpSimulation',//查询报表明细数据
        saveOrUpdateALLTtaGp:webconfig.url.ttaServer + '/ttaOiReportGpSimulationService/saveOrUpdateAll',//保存报表明细数据
        findSummaryGpSimulation: webconfig.url.ttaServer + '/ttaOiReportGpSimulationService/findSummaryGpSimulation',//查询汇总数据
        saveOrUpdateOiReportHeader: webconfig.url.ttaServer + '/ttaOiReportHeaderService/saveOrUpdate',//报表5发布或者确认状态修改
        findPerformanceReport: webconfig.url.ttaServer +  '/ttaOiReportFieldHeaderService/find', //报表1. All Supplier Performance Report_sample
        findTopSupplierReport: webconfig.url.ttaServer +  '/ttaOiReportFieldHeaderService/findTopSupplierReport', //报表2
        findYtdYoyComparisonReport:webconfig.url.ttaServer +  '/ttaOiReportFieldHeaderService/findOiFeeTypeCombinationReport', //报表6,7,8,9,10
        saveQueque : webconfig.url.ttaServer + '/ttaOiReportFieldHeaderService/saveQueque',
        checkOiReportYearMonth : webconfig.url.ttaServer + '/ttaOiReportFieldHeaderService/checkOiReportYearMonth',
        /*************** OI报表end *************************/

        //独家货品报表
        findExclusiveItemReport : webconfig.url.ttaServer + '/ttaSoleItemService/findExclusiveItemReport',

        //条款-问卷规则
        findQueryRulePag:webconfig.url.ttaServer +    'ruleService/queryRulePag',//查询规则
        saveBaseRule:webconfig.url.ttaServer +    'ruleService/saveRule',//保存规则
        deleteBaseRule:webconfig.url.ttaServer +    'ruleService/deleteRule',//删除规则

        //SittingPlan
        saveImportSittingPlanInfo: webconfig.url.ttaServer + '/ttaHwbSittingPlanService/saveImportInfo',
        findSittingPlanPagination:webconfig.url.ttaServer + '/ttaHwbSittingPlanService/findPagination',
        deleteImportSittingPlanInfo:webconfig.url.ttaServer + '/ttaHwbSittingPlanService/deleteImportInfo',

        //ttaHwbBaseLineService
        saveImportHwbBaseInfo: webconfig.url.ttaServer + '/ttaHwbBaseLineService/saveImportHwbBaseInfo',
        findHwbPagination:webconfig.url.ttaServer + '/ttaHwbBaseLineService/findPagination',
        deleteImportHwbBaseInfo:webconfig.url.ttaServer + '/ttaHwbBaseLineService/deleteImportHwbBaseInfo',

        //employee portal跳转链接
        employeeLoginTta: webconfig.url.baseServer + 'baseLoginService/loginTta',

        //AwardPolist
        saveImportAwardPolistInfo: webconfig.url.ttaServer + '/ttaHwbAwardPolistService/saveImportInfo',
        findAwardPolistPagination:webconfig.url.ttaServer + '/ttaHwbAwardPolistService/findPagination',
        deleteImportAwardPolistInfo:webconfig.url.ttaServer + '/ttaHwbAwardPolistService/deleteImportInfo',

        //仓库
        //findTtaShopMarketPagination:webconfig.url.ttaServer +    'ttaShopMarketService/findPagination',//查询仓库列表
        findTtaShopMarketPagination: webconfig.url.ttaServer +    'ttaContractLineReportService/findPagination',//查询仓库列表

        //attendanceFee
        attendanceFeeFind: webconfig.url.ttaServer + 'ttaHwbAttendanceFeeService/find', //
        attendanceFeeFindUser: webconfig.url.ttaServer + 'ttaHwbAttendanceFeeService/findUser', //
        attendanceFeeDel:webconfig.url.ttaServer + 'ttaHwbAttendanceFeeService/delete',//
        attendanceFeeSave:webconfig.url.ttaServer + 'ttaHwbAttendanceFeeService/saveOrUpdate',//

        //PLM_OB_项目管理_详情头
        findPlmProjectInfoInfo: webconfig.url.plmServer + 'plmProjectInfoService/findPlmProjectInfoInfo',
        savePlmProjectInfoInfo: webconfig.url.plmServer + 'plmProjectInfoService/savePlmProjectInfoInfo',
        deletePlmProjectInfoInfo: webconfig.url.plmServer + 'plmProjectInfoService/deletePlmProjectInfoInfo',

        //PLM_OB_项目管理_产品明细
        findPlmProjectProductDetailInfo: webconfig.url.plmServer + 'plmProjectProductDetailService/findPlmProjectProductDetailInfo',
        savePlmProjectProductDetailInfo: webconfig.url.plmServer + 'plmProjectProductDetailService/savePlmProjectProductDetailInfo',
        deletePlmProjectProductDetailInfo: webconfig.url.plmServer + 'plmProjectProductDetailService/deletePlmProjectProductDetailInfo',

        //PLM_OB_项目管理_项目异常
        findPlmProjectExceptionInfo: webconfig.url.plmServer + 'plmProjectExceptionService/findPlmProjectExceptionInfo',
        savePlmProjectExceptionInfo: webconfig.url.plmServer + 'plmProjectExceptionService/savePlmProjectExceptionInfo',
        deletePlmProjectExceptionInfo: webconfig.url.plmServer + 'plmProjectExceptionService/deletePlmProjectExceptionInfo',

        //PLM_OB_产品开发_详情头
        findPlmDevelopmentInfoInfo: webconfig.url.plmServer + 'plmDevelopmentInfoService/findPlmDevelopmentInfoInfo',
        savePlmDevelopmentInfoInfo: webconfig.url.plmServer + 'plmDevelopmentInfoService/savePlmDevelopmentInfoInfo',

        findPlmSupplierInfo: webconfig.url.plmServer + 'ttaSupplierService/find',

        /***********************************************E-QUOTATION************************************************************/
        //EQU_POS_供应商资质审查单据查询
        findQualificationList:webconfig.url.equotationServer + 'equPosQualificationInfoService/findQualificationList',

        //EQU_POS_供应商资质审查单据保存
        saveQualificationInfo:webconfig.url.equotationServer + 'equPosQualificationInfoService/saveQualificationInfo',

        //EQU_POS_供应商资质审查单据提交
        submitQualificationInfo:webconfig.url.equotationServer + 'equPosQualificationInfoService/submitQualificationInfo',

        //EQU_POS_供应商资质审查单据取消
        cancelQualificationInfo:webconfig.url.equotationServer + 'equPosQualificationInfoService/cancelQualificationInfo',

        //EQU_POS_供应商资质审查单据删除
        deleteQualificationInfo:webconfig.url.equotationServer + 'equPosQualificationInfoService/deleteQualificationInfo',

        //EQU_POS_供应商档案查询
        findSupplierFiles:webconfig.url.equotationServer + 'equPosSupplierInfoService/findSupplierFiles',
        // EQU_POS_供应商档案保存,保存到部门供应商表
        saveForSupplierFilesDetail:webconfig.url.equotationServer + 'equPosSuppInfoWithDeptService/saveForSupplierFilesDetail',
        //EQU_POS_供应商档案查询
        findSupplierInfoLov:webconfig.url.equotationServer + 'equPosSupplierInfoService/findSupplierLov',

        //综合表现查询
        findSupplierScoreInfo:webconfig.url.equotationServer + 'equPosSupplierInfoService/findSupplierScoreInfo',

        //EQU_POS_供应商关联工厂查询(资质审查)
        findZzscAssociatedSupplier:webconfig.url.equotationServer + 'equPosZzscAssociateSuppService/findZzscAssociatedSupplier',

        //EQU_POS_供应商关联工厂查询
        findAssociatedSupplier:webconfig.url.equotationServer + 'equPosAssociatedSupplierService/findAssociatedSupplier',

        //EQU_POS_供应商关联工厂查询(变更前)
        findBgqAssociatedSupplier:webconfig.url.equotationServer + 'equPosBgqAssociateSuppService/findBgqAssociatedSupplier',

        //EQU_POS_供应商关联工厂查询(变更后)
        findBgAssociatedSupplier:webconfig.url.equotationServer + 'equPosBgAssociateSuppService/findBgAssociatedSupplier',

        //EQU_POS_供应商关联工厂删除
        deleteAssociatedSupplier:webconfig.url.equotationServer + 'equPosZzscAssociateSuppService/deleteZzscAssociatedSupplier',

        //EQU_POS_供应商关联工厂删除(变更后)
        deleteBgAssociatedSupplier:webconfig.url.equotationServer + 'equPosBgAssociateSuppService/deleteBgAssociatedSupplier',

        //EQU_POS_供应商基础信息查询(资质审查)
        findZzscSupplierInfo:webconfig.url.equotationServer + 'equPosZzscSupplierService/findZzscSupplierInfo',

        //EQU_POS_供应商基础信息查询
        findSupplierInfo:webconfig.url.equotationServer + 'equPosSupplierInfoService/findSupplierInfo',

        //EQU_POS_供应商基础信息查询(变更前)
        findBgqSupplierInfo:webconfig.url.equotationServer + 'equPosBgqSupplierService/findBgqSupplierInfo',

        //EQU_POS_供应商基础信息查询(变更后)
        findBgSupplierInfo:webconfig.url.equotationServer + 'equPosBgSupplierService/findBgSupplierInfo',

        //EQU_POS_供应商品类查询(资质审查)
        findZzscSupplierCategorysInfo:webconfig.url.equotationServer + 'equPosZzscCategoryService/findZzscSupplierCategorysInfo',

        //EQU_POS_供应商一级品类查询(资质审查)
        findFirstCategoryLov:webconfig.url.equotationServer + 'equPosQualificationInfoService/findFirstCategoryLov',

        //EQU_POS_供应商二级品类查询(资质审查)
        findSecondCategoryLov:webconfig.url.equotationServer + 'equPosQualificationInfoService/findSecondCategoryLov',

        //EQU_POS_供应商三级品类查询(资质审查)
        findThridCategoryLov:webconfig.url.equotationServer + 'equPosQualificationInfoService/findThridCategoryLov',

        //EQU_POS_供应商品类查询
        findSupplierCategorysInfo:webconfig.url.equotationServer + 'equPosSupplierProductCatService/findSupplierCategorysInfo',

        //EQU_POS_供应商品类查询(变更前)
        findBgqSupplierCategorysInfo:webconfig.url.equotationServer + 'equPosBgqCategoryService/findBgqSupplierCategorysInfo',

        //EQU_POS_供应商品类查询(变更后)
        findBgSupplierCategorysInfo:webconfig.url.equotationServer + 'equPosBgCategoryService/findBgSupplierCategorysInfo',

        //EQU_POS_供应商品类删除
        deleteSupplierCategorysInfo:webconfig.url.equotationServer + 'equPosZzscCategoryService/deleteZzscSupplierCategorysInfo',

        //EQU_POS_供应商品类删除(变更后)
        deleteBghSupplierCategorysInfo:webconfig.url.equotationServer + 'equPosBgCategoryService/deleteBgSupplierCategorysInfo',

        //EQU_POS_供应商联系人信息查询(资质审查)
        findZzscSupplierContactsInfo:webconfig.url.equotationServer + 'equPosZzscContactsService/findZzscSupplierContactsInfo',

        //EQU_POS_供应商联系人信息查询
        findSupplierContactsInfo:webconfig.url.equotationServer + 'equPosSupplierContactsService/findSupplierContactsInfo',

        //EQU_POS_供应商联系人信息查询(变更前)
        findBgqSupplierContactsInfo:webconfig.url.equotationServer + 'equPosBgqContactsService/findBgqSupplierContactsInfo',

        //EQU_POS_供应商联系人信息查询(变更后)
        findBgSupplierContactsInfo:webconfig.url.equotationServer + 'equPosBgContactsService/findBgSupplierContactsInfo',

        //EQU_POS_供应商联系人信息删除
        deleteSupplierContactsInfo:webconfig.url.equotationServer + 'equPosZzscContactsService/deleteZzscSupplierContactsInfo',

        //EQU_POS_供应商联系人信息删除(变更后)
        deleteBgSupplierContactsInfo:webconfig.url.equotationServer + 'equPosBgContactsService/deleteBgSupplierContactsInfo',

        //EQU_POS_供应商银行信息查询(资质审查)
        findZzscSupplierBankInfo:webconfig.url.equotationServer + 'equPosZzscBankService/findZzscSupplierBankInfo',

        //EQU_POS_供应商银行信息查询
        findSupplierBankInfo:webconfig.url.equotationServer + 'equPosSupplierBankService/findSupplierBankInfo',

        //EQU_POS_供应商银行信息查询(变更前)
        findBgqSupplierBankInfo:webconfig.url.equotationServer + 'equPosBgqBankService/findBgqSupplierBankInfo',

        //EQU_POS_供应商银行信息查询(变更后)
        findBgSupplierBankInfo:webconfig.url.equotationServer + 'equPosBgBankService/findBgSupplierBankInfo',

        //EQU_POS_供应商银行信息删除
        deleteBankInfo:webconfig.url.equotationServer + 'equPosZzscBankService/deleteZzscBankInfo',

        //EQU_POS_供应商银行信息删除(更新后)
        deleteBgBankInfo:webconfig.url.equotationServer + 'equPosBgBankService/deleteBgBankInfo',

        //EQU_POS_供应商资质文件查询(资质审查)
        findZzscCredentialsAttachmentInfo:webconfig.url.equotationServer + 'equPosZzscCredentialAttachService/findZzscCredentialsAttachmentInfo',

        //EQU_POS_供应商资质文件查询
        findCredentialsAttachmentInfo:webconfig.url.equotationServer + 'equPosCredentialsAttachmentService/findCredentialsAttachmentInfo',

        //EQU_POS_供应商资质文件查询(变更前)
        findBgqCredentialsAttachmentInfo:webconfig.url.equotationServer + 'equPosBgqCredentialAttachService/findBgqCredentialsAttachmentInfo',

        //EQU_POS_供应商资质文件查询(变更后)
        findBgCredentialsAttachmentInfo:webconfig.url.equotationServer + 'equPosBgCredentialAttachService/findBgCredentialsAttachmentInfo',

        //EQU_POS_供应商资质文件删除
        deleteCredentialsAttachmentInfo:webconfig.url.equotationServer + 'equPosZzscCredentialAttachService/deleteZzscCredentialsAttachmentInfo',

        //EQU_POS_供应商资质文件删除(变更后)
        deleteBgCredentialsAttachmentInfo:webconfig.url.equotationServer + 'equPosBgCredentialAttachService/deleteBgCredentialsAttachmentInfo',

        //EQU_POS_供应商地址信息查询(资质审查)
        findZzscSupplierAddressInfo:webconfig.url.equotationServer + 'equPosZzscAddressesService/findZzscSupplierAddressInfo',

        //EQU_POS_供应商地址信息查询
        findSupplierAddressInfo:webconfig.url.equotationServer + 'equPosSupplierAddressesService/findSupplierAddressInfo',

        //EQU_POS_供应商地址信息查询(变更前)
        findBgqSupplierAddressInfo:webconfig.url.equotationServer + 'equPosBgqAddressesService/findBgqSupplierAddressInfo',

        //EQU_POS_供应商地址信息查询(变更后)
        findBgSupplierAddressInfo:webconfig.url.equotationServer + 'equPosBgAddressesService/findBgSupplierAddressInfo',

        //EQU_POS_供应商地址信息删除
        deleteSupplierAddressInfo:webconfig.url.equotationServer + 'equPosZzscAddressesService/deleteZzscSupplierAddressInfo',

        //EQU_POS_供应商资质信息(资质审查)
        findZzscSupplierCredentialsInfo:webconfig.url.equotationServer + 'equPosZzscCredentialsService/findZzscSupplierCredentialsInfo',

        //EQU_POS_供应商资质信息
        findSupplierCredentialsInfo:webconfig.url.equotationServer + 'equPosSupplierCredentialsService/findSupplierCredentialsInfo',

        //EQU_POS_供应商资质信息(变更前)
        findBgqSupplierCredentialsInfo:webconfig.url.equotationServer + 'equPosBgqCredentialsService/findBgqSupplierCredentialsInfo',

        //EQU_POS_供应商资质信息(变更后)
        findBgSupplierCredentialsInfo:webconfig.url.equotationServer + 'equPosBgCredentialsService/findBgSupplierCredentialsInfo',

        //EQU_POS_供应商产能信息删除
        deleteCapacityInfo:webconfig.url.equotationServer + 'equPosZzscCapacityService/deleteZzscCapacityInfo',

        //EQU_POS_供应商基础信息保存
        // findSupplierInfo:webconfig.url.equotationServer + 'equPosZzscSupplierService/findZzscSupplierInfo',

        //EQU_POS_供应商质量审核单据查询
        findSupplierQualityAudit:webconfig.url.equotationServer + 'equPosSupplierQualityAuditService/findSupplierQualityAudit',

        //EQU_POS_供应商质量审查单据保存
        saveSupplierQualityAudit:webconfig.url.equotationServer + 'equPosSupplierQualityAuditService/saveSupplierQualityAudit',

        //EQU_POS_供应商质量审查单据删除
        deleteSupplierQualityAudit:webconfig.url.equotationServer + 'equPosSupplierQualityAuditService/deleteSupplierQualityAudit',

        //EQU_POS_供应商质量审查单据提交
        submitSupplierQualityAudit:webconfig.url.equotationServer + 'equPosSupplierQualityAuditService/submitSupplierQualityAudit',

        //EQU_POS_供应商CSR审核单据查询
        findSupplierCsrAudit:webconfig.url.equotationServer + 'equPosSupplierCsrAuditService/findSupplierCsrAudit',

        //EQU_POS_供应商CSR审查单据保存
        saveSupplierCsrAudit:webconfig.url.equotationServer + 'equPosSupplierCsrAuditService/saveSupplierCsrAudit',

        //EQU_POS_供应商CSR审查单据删除
        deleteSupplierCsrAudit:webconfig.url.equotationServer + 'equPosSupplierCsrAuditService/deleteSupplierCsrAudit',

        //EQU_POS_供应商CSR审查单据提交
        submitSupplierCsrAudit:webconfig.url.equotationServer + 'equPosSupplierCsrAuditService/submitSupplierCsrAudit',

        //EQU_POS_供应商档案变更单据查询
        findArchivesChangeOrder:webconfig.url.equotationServer + 'equPosSupplierChangeService/findArchivesChangeOrder',

        //EQU_POS_供应商档案变更单据保存
        saveArchivesChangeOrder:webconfig.url.equotationServer + 'equPosSupplierChangeService/saveArchivesChangeOrder',

        //EQU_POS_供应商档案变更单据删除
        deleteArchivesChangeOrder:webconfig.url.equotationServer + 'equPosSupplierChangeService/deleteArchivesChangeOrder',

        //EQU_PON_立项单据查询
        findProjectInfo:webconfig.url.equotationServer + 'equPonProjectInfoService/findProjectInfo',

        //EQU_PON_立项单据保存
        saveProjectInfo:webconfig.url.equotationServer + 'equPonProjectInfoService/saveProjectInfo',

        //EQU_PON_立项单据删除
        deleteProjectInfo:webconfig.url.equotationServer + 'equPonProjectInfoService/deleteProjectInfo',

        //EQU_PON_立项单据终止
        terminateProjectInfo:webconfig.url.equotationServer + 'equPonProjectInfoService/terminateProjectInfo',

        //EQU_PON_立项附件查询
        findProjectAttachment:webconfig.url.equotationServer + 'equPonProjectAttachmentService/findProjectAttachment',

        //EQU_PON_立项附件删除
        deleteProjectAttachment:webconfig.url.equotationServer + 'equPonProjectAttachmentService/deleteProjectAttachment',

        //EQU_PON_产品规格查询
        findProductSpecs:webconfig.url.equotationServer + 'equPonProductSpecsService/findProductSpecs',

        //EQU_PON_产品规格删除
        deleteProductSpecs:webconfig.url.equotationServer + 'equPonProductSpecsService/deleteProductSpecs',

        //EQU_PON_产品规格批量导入
        importProductSpecsInfo:webconfig.url.equotationServer + 'equPonProductSpecsService/importProductSpecsInfo',

        //EQU_PON_邀请供应商列表查询
        findSupplierInvitation:webconfig.url.equotationServer + 'equPonSupplierInvitationService/findSupplierInvitation',

        //EQU_PON_邀请供应商列表删除
        deleteSupplierInvitation:webconfig.url.equotationServer + 'equPonSupplierInvitationService/deleteSupplierInvitation',

        //EQU_PON_邀请供应商退出报价
        quitSupplierInvitation:webconfig.url.equotationServer + 'equPonSupplierInvitationService/quitSupplierInvitation',

        //EQU_PON_评分小组信息查询
        findScoringTeam:webconfig.url.equotationServer + 'equPonScoringTeamService/findScoringTeam',

        //EQU_PON_评分common查询
        findScoringCommon:webconfig.url.equotationServer + 'equPonWitnessTeamService/findScoringCommon',

        //EQU_PON_见证小组备注信息保存
        saveSupplierInvitationRemark:webconfig.url.equotationServer + 'equPonWitnessTeamService/saveSupplierInvitationRemark',

        //EQU_PON_见证小组信息查询
        findWitnessTeam:webconfig.url.equotationServer + 'equPonWitnessTeamService/findWitnessTeam',

        saveProjectSupplierQuit:webconfig.url.equotationServer + 'equPonQuotationInformationService/saveProjectSupplierQuit',

        //EQU_PON_评分人员/见证人员信息查询(LOV)
        findScoringMemberLov:webconfig.url.equotationServer + 'equPonScoringTeamService/findScoringMemberLov',
        // EQU_PON_发送供应商邀请
        btnSendInvitation:webconfig.url.equotationServer + 'equPonSupplierInvitationService/btnSendInvitation',
        //EQU_PON_评分单据查询
        findScoringInfo:webconfig.url.equotationServer + 'equPonScoringInfoService/findScoringInfo',

        //EQU_PON_评分单据终止
        terminateScoringInfo:webconfig.url.equotationServer + 'equPonScoringInfoService/terminateScoringInfo',

        //EQU_PON_批量导入Panel test 分数
        updatePanelTestScoreImport:webconfig.url.equotationServer + 'equPonScoringInfoService/updatePanelTestScoreImport',

        //EQU_PON_评分单据查询(流程)
        findScoringInfoForFlow:webconfig.url.equotationServer + 'equPonScoringInfoService/findScoringInfoForFlow',

        //EQU_PON_评分单据保存
        saveScoringInfo:webconfig.url.equotationServer + 'equPonScoringInfoService/saveScoringInfo',

        //EQU_PON_评分单据删除
        deleteScoringInfo:webconfig.url.equotationServer + 'equPonScoringInfoService/deleteScoringInfo',

        //EQU_PON_评分明细行查询
        findScoringDetail:webconfig.url.equotationServer + 'equPonScoringDetailService/findScoringDetail',

        //EQU_PON_校验报价单状态
        checkQuotationStatus:webconfig.url.equotationServer + 'equPonScoringInfoService/checkQuotationStatus',

        findPonInformationInfoLov:webconfig.url.equotationServer + 'equPonScoringInfoService/findPonInformationInfoLov',

        //EQU_PON_PanelTest评分模板下载
        btnExportTemplage:webconfig.url.equotationServer + 'equPonScoringInfoService/btnExportTemplage',

        //EQU_PON_查询供应商基础分数
        findSupplierBaseScore:webconfig.url.equotationServer + 'equPonScoringDetailService/findSupplierBaseScore',

        //EQU_PON_非价格计算
        saveNonPriceCalculate:webconfig.url.equotationServer + 'equPonScoringInfoService/saveNonPriceCalculate',

        //EQU_PON_报价总分计算
        saveQuotationScoreCalculate:webconfig.url.equotationServer + 'equPonScoringInfoService/saveQuotationScoreCalculate',

        //提交审批流
        bpmStart: webconfig.url.processServer + '/bpmProcessService/start',

        //HLH ---------------------------------------------------------------------------------------------------------------------
        //场景引入查询
        findEquPosSceneManageInfo: webconfig.url.equotationServer + 'equPosSceneManageService/findEquPosSceneManageInfo',
        //场景引入保存
        saveSceneManage: webconfig.url.equotationServer + 'equPosSceneManageService/saveSceneManage',
        //提交场景引入
        sumbitSceneManage: webconfig.url.equotationServer + 'equPosSceneManageService/sumbitSceneManage',
        //查询场景引入详情
        findSceneManageLine: webconfig.url.equotationServer + 'equPosSceneManageService/findSceneManageLine',
        //删除场景引入
        deleteSceneManage: webconfig.url.equotationServer + 'equPosSceneManageService/deleteSceneManage',


        //供应商暂停淘汰查询
        findEquPosSuspendInfo: webconfig.url.equotationServer + 'equPosSupplierSuspendService/findEquPosSuspendInfo',
        //查询详情
        findSupSuspendDatail:webconfig.url.equotationServer + 'equPosSupplierSuspendService/findSupSuspendDatail',
        //保存供应商暂停淘汰详情
        saveEquPosSuspend: webconfig.url.equotationServer + 'equPosSupplierSuspendService/saveEquPosSuspend',
        saveEquPosSuspendSubmit: webconfig.url.equotationServer + 'equPosSupplierSuspendService/saveEquPosSuspendSubmit',
        //查询供应商LOV
        findSupplierLov :webconfig.url.equotationServer + 'equPosSupplierSuspendService/findSupplierLov',
        findSupplierLovNotDept :webconfig.url.equotationServer + 'equPonQuotationApprovalService/findSupplierLovNotDept',

        //供应商恢复查询
        findEquPosRecoverInfo: webconfig.url.equotationServer + 'equPosSupplierRecoverService/findEquPosRecoverInfo',
        //查询详情
        findSupRecoverDatail:webconfig.url.equotationServer + 'equPosSupplierRecoverService/findSupRecoverDatail',
        //保存供应商恢复详情
        saveEquPosRecover: webconfig.url.equotationServer + 'equPosSupplierRecoverService/saveEquPosRecover',

        saveEquPosRecoverSumbit: webconfig.url.equotationServer + 'equPosSupplierRecoverService/saveEquPosRecoverSumbit',
        //供应商恢复删除
        deleteSupplierRecover: webconfig.url.equotationServer + 'equPosSupplierRecoverService/deleteSupplierRecover',
        // 附件上传
        saveEquFileUpload : webconfig.url.equotationServer  + "equPosSupplierRecoverService/saveEquFileUpload",


        //供应商黑名单查询
        findEquPosBlacklistInfo: webconfig.url.equotationServer + 'equPosSupplierBlacklistService/findEquPosBlacklistInfo',
        //供应商黑名单查询详情
        findSupBlacklistDatail:webconfig.url.equotationServer + 'equPosSupplierBlacklistService/findSupBlacklistDatail',
        //保存供应商黑名单详情
        saveEquPosBlacklist: webconfig.url.equotationServer + 'equPosSupplierBlacklistService/saveEquPosBlacklist',
        //供应商黑名单删除
        deleteSupplierBlacklist: webconfig.url.equotationServer + 'equPosSupplierBlacklistService/deleteSupplierBlacklist',
        saveEquPosBlack: webconfig.url.equotationServer + 'equPosSupplierBlacklistService/saveEquPosBlack',

        //供应商信用审核查询
        findEquPosCreditAuditInfo: webconfig.url.equotationServer + 'equPosSupplierCreditAuditService/findEquPosCreditAuditInfo',
        //供应商信用审核详情
        findSupCreditAuditDatail:webconfig.url.equotationServer + 'equPosSupplierCreditAuditService/findSupCreditAuditDatail',
        //保存供应商信用审核详情
        saveEquPosCreditAudit: webconfig.url.equotationServer + 'equPosSupplierCreditAuditService/saveEquPosCreditAudit',

        saveEquPosCreditAuditSubmit: webconfig.url.equotationServer + 'equPosSupplierCreditAuditService/saveEquPosCreditAuditSubmit',
        //供应商信用审核删除
        deleteSupplierCreditAudit: webconfig.url.equotationServer + 'equPosSupplierCreditAuditService/deleteSupplierCreditAudit',

        //供应商资质审核单号LOV
        findQualificationAuditLov: webconfig.url.equotationServer + 'equPosSupplierCreditAuditService/findQualificationLov',

        //供应商入库审核查询
        findEquPosWarehousingInfo: webconfig.url.equotationServer + 'equPosSupplierWarehousingService/findEquPosWarehousingInfo',
        //供应商入库审核查询详情
        findSupWarehousingDatail:webconfig.url.equotationServer + 'equPosSupplierWarehousingService/findSupWarehousingDatail',
        saveSupWarehousingDatailSumbit:webconfig.url.equotationServer + 'equPosSupplierWarehousingService/saveSupWarehousingDatailSumbit',
        //保存供应商入库审核详情
        saveEquPosWarehousing: webconfig.url.equotationServer + 'equPosSupplierWarehousingService/saveEquPosWarehousing',
        //供应商入库审核删除
        deleteSupplierWarehousing: webconfig.url.equotationServer + 'equPosSupplierWarehousingService/deleteSupplierWarehousing',

        getActivitiesHistoric: webconfig.url.equotationServer + 'equPosSupplierWarehousingService/getActivitiesHistoric',

        //供应商入库审核变更查询
        findEquPosCreditAuditChangeInfo: webconfig.url.equotationServer + 'equPosCreditAuditChangeHService/findEquPosCreditAuditChangeInfo',
        //供应商入库审核变更详情
        findSupCreditAuditChangeDatail: webconfig.url.equotationServer + 'equPosCreditAuditChangeHService/findSupCreditAuditChangeDatail',
        //导入保存
        saveImportChange: webconfig.url.equotationServer + 'equPosCreditAuditChangeHService/saveImportChange',
        //保存详细信息
        saveEquPosCreditAuditChange: webconfig.url.equotationServer + 'equPosCreditAuditChangeHService/saveEquPosCreditAuditChange',

        saveEquPosCreditAuditChangeSubmit: webconfig.url.equotationServer + 'equPosCreditAuditChangeHService/saveEquPosCreditAuditChangeSubmit',
        //查询行
        findEquPosCreditAuditChangeL : webconfig.url.equotationServer + 'equPosCreditAuditChangeHService/findEquPosCreditAuditChangeL',
        //保存导入信息
        saveImportChange : webconfig.url.equotationServer + 'equPosCreditAuditChangeHService/saveImportChange',
        //删除
        deleteCreditAuditChange : webconfig.url.equotationServer + 'equPosCreditAuditChangeHService/deleteCreditAuditChange',

        deleteCreditAuditLine : webconfig.url.equotationServer + 'equPosCreditAuditChangeHService/deleteCreditAuditLine',
        //删除暂停淘汰
        deleteSuspend:webconfig.url.equotationServer + 'equPosSupplierSuspendService/deleteSuspend',
        // EQU 供应商品分类
        findSupplierCategoryPagination: webconfig.url.equotationServer + 'equPosSupplierCategoryService/findSupplierCategoryPagination',
        saveSupplierCategoryList: webconfig.url.equotationServer + 'equPosSupplierCategoryService/saveSupplierCategoryList',

        // EQU 临时特批
        findTempSpecialPagination: webconfig.url.equotationServer + 'equPosTempSpecialService/findTempSpecialPagination',
        findTempSpecialDetail: webconfig.url.equotationServer + 'equPosTempSpecialService/findTempSpecialDetail',
        saveTempSpecialInfo: webconfig.url.equotationServer + 'equPosTempSpecialService/saveTempSpecialInfo',
        deleteTempSpecial: webconfig.url.equotationServer + 'equPosTempSpecialService/deleteTempSpecial',
        // 附件上传
        fileUploadForTempSpecial : webconfig.url.equotationServer  + "equPosTempSpecialService/fileUploadForTempSpecial",

        // EQU 年度质量审核导入更新
        findQualityUpdatePagination: webconfig.url.equotationServer + 'equPosQualityUpdateHeadService/findQualityUpdatePagination',
        findQualityUpdateHeadInfo: webconfig.url.equotationServer + 'equPosQualityUpdateHeadService/findQualityUpdateHeadInfo',
        findQualityUpdateLinePagination: webconfig.url.equotationServer + 'equPosQualityUpdateLineService/findQualityUpdateLinePagination',
        saveQualityUpdateHeadAndLine: webconfig.url.equotationServer + 'equPosQualityUpdateHeadService/saveQualityUpdateHeadAndLine',
        deleteQualityUpdateHead: webconfig.url.equotationServer + 'equPosQualityUpdateHeadService/deleteQualityUpdateHead',
        // 质量审核头附件上传
        fileUploadForQualityUpdateHead : webconfig.url.equotationServer  + 'equPosQualityUpdateHeadService/fileUploadForQualityUpdateHead',
        // 质量审核行附件上传
        fileUploadForQualityUpdateLine : webconfig.url.equotationServer  + "equPosQualityUpdateHeadService/fileUploadForQualityUpdateLine",
        // 质量审核行文件导入
        saveImportForQualityUpdate : webconfig.url.equotationServer  + "equPosQualityUpdateLineService/saveImportForQualityUpdate",

        // EQU 年度CSR审核导入更新
        findCsrUpdatePagination: webconfig.url.equotationServer + 'equPosCsrUpdateHeadService/findCsrUpdatePagination',
        findCsrUpdateHeadInfo: webconfig.url.equotationServer + 'equPosCsrUpdateHeadService/findCsrUpdateHeadInfo',
        findCsrUpdateLinePagination: webconfig.url.equotationServer + 'equPosCsrUpdateLineService/findCsrUpdateLinePagination',
        saveCsrUpdateHeadAndLine: webconfig.url.equotationServer + 'equPosCsrUpdateHeadService/saveCsrUpdateHeadAndLine',
        deleteCsrUpdateHead: webconfig.url.equotationServer + 'equPosCsrUpdateHeadService/deleteCsrUpdateHead',
        // CSR审核头附件上传
        fileUploadForCsrUpdateHead : webconfig.url.equotationServer  + "equPosCsrUpdateHeadService/fileUploadForCsrUpdateHead",
        // CSR审核行附件上传
        fileUploadForCsrUpdateLine : webconfig.url.equotationServer  + "equPosCsrUpdateHeadService/fileUploadForCsrUpdateLine",
        // CSR审核行文件导入
        saveImportForCsrUpdate : webconfig.url.equotationServer  + "equPosCsrUpdateLineService/saveImportForCsrUpdate",

        // EQU 年度评分审核导入更新
        findScoreUpdatePagination: webconfig.url.equotationServer + 'equPosScoreUpdateHeadService/findScoreUpdatePagination',
        findScoreUpdateLinePagination: webconfig.url.equotationServer + 'equPosScoreUpdateLineService/findScoreUpdateLinePagination',
        findScoreUpdateHeadInfo: webconfig.url.equotationServer + 'equPosScoreUpdateHeadService/findScoreUpdateHeadInfo',
        saveScoreUpdateHeadAndLine: webconfig.url.equotationServer + 'equPosScoreUpdateHeadService/saveScoreUpdateHeadAndLine',
        deleteScoreUpdateHead: webconfig.url.equotationServer + 'equPosScoreUpdateHeadService/deleteScoreUpdateHead',
        // Score审核头附件上传
        fileUploadForScoreUpdateHead : webconfig.url.equotationServer  + "equPosScoreUpdateHeadService/fileUploadForScoreUpdateHead",
        // Score审核行附件上传
        fileUploadForScoreUpdateLine : webconfig.url.equotationServer  + "equPosScoreUpdateHeadService/fileUploadForScoreUpdateLine",
        // Score审核行文件导入
        saveImportForScoreUpdate : webconfig.url.equotationServer  + "equPosScoreUpdateLineService/saveImportForScoreUpdate",

        // EQU 年度spend审核导入更新
        findSpendUpdatePagination: webconfig.url.equotationServer + 'equPosSpendUpdateHeadService/findSpendUpdatePagination',
        findSpendUpdateHeadInfo: webconfig.url.equotationServer + 'equPosSpendUpdateHeadService/findSpendUpdateHeadInfo',
        findSpendUpdateLinePagination: webconfig.url.equotationServer + 'equPosSpendUpdateLineService/findSpendUpdateLinePagination',
        saveSpendUpdateHeadAndLine: webconfig.url.equotationServer + 'equPosSpendUpdateHeadService/saveSpendUpdateHeadAndLine',
        deleteSpendUpdateHead: webconfig.url.equotationServer + 'equPosSpendUpdateHeadService/deleteSpendUpdateHead',
        // Spend审核头附件上传
        fileUploadForSpendUpdateHead : webconfig.url.equotationServer  + "equPosSpendUpdateHeadService/fileUploadForSpendUpdateHead",
        // Spend审核行附件上传
        fileUploadForSpendUpdateLine : webconfig.url.equotationServer  + "equPosSpendUpdateHeadService/fileUploadForSpendUpdateLine",
        // Spend审核行文件导入
        saveImportForSpendUpdate : webconfig.url.equotationServer  + "equPosSpendUpdateLineService/saveImportForSpendUpdate",

        // EQU 年度Contract审核导入更新
        findContractUpdatePagination: webconfig.url.equotationServer + 'equPosContractUpdateHeadService/findContractUpdatePagination',
        findContractUpdateHeadInfo: webconfig.url.equotationServer + 'equPosContractUpdateHeadService/findContractUpdateHeadInfo',
        findContractUpdateLinePagination: webconfig.url.equotationServer + 'equPosContractUpdateLineService/findContractUpdateLinePagination',
        saveContractUpdateHeadAndLine: webconfig.url.equotationServer + 'equPosContractUpdateHeadService/saveContractUpdateHeadAndLine',
        deleteContractUpdateHead: webconfig.url.equotationServer + 'equPosContractUpdateHeadService/deleteContractUpdateHead',
        // Contract审核头附件上传
        fileUploadForContractUpdateHead : webconfig.url.equotationServer  + "equPosContractUpdateHeadService/fileUploadForContractUpdateHead",
        // Contract审核行附件上传
        fileUploadForContractUpdateLine : webconfig.url.equotationServer  + "equPosContractUpdateHeadService/fileUploadForContractUpdateLine",
        // Contract审核行文件导入
        saveImportForContractUpdate : webconfig.url.equotationServer  + "equPosContractUpdateLineService/saveImportForContractUpdate",
        // Contract行删除
        deleteContractUpdateLine : webconfig.url.equotationServer  + "equPosContractUpdateLineService/deleteContractUpdateLine",
        // Csr行删除
        deleteCsrUpdateLine : webconfig.url.equotationServer  + "equPosCsrUpdateLineService/deleteCsrUpdateLine",
        // Spend行删除
        deleteSpendUpdateLine : webconfig.url.equotationServer  + "equPosSpendUpdateLineService/deleteSpendUpdateLine",
        // Score行删除
        deleteScoreUpdateLine : webconfig.url.equotationServer  + "equPosScoreUpdateLineService/deleteScoreUpdateLine",
        // Quality行删除
        deleteQualityUpdateLine : webconfig.url.equotationServer  + "equPosQualityUpdateLineService/deleteQualityUpdateLine",



        // 报价管理
        findQuotationInfoPagination : webconfig.url.equotationServer  + "equPonQuotationInfoService/findQuotationInfoPagination",
        findQuotationInfo : webconfig.url.equotationServer  + "equPonQuotationInfoService/findQuotationInfo",
        // 待报价分页查询
        waitQuotationInfoPagination : webconfig.url.equotationServer  + "equPonQuotationInfoService/waitQuotationInfoPagination",
        // 确认/拒绝参与报价
        confirmParticipation : webconfig.url.equotationServer  + "equPonQuotationInfoService/confirmParticipation",
        rejectParticipation : webconfig.url.equotationServer  + "equPonQuotationInfoService/rejectParticipation",
        // 查询价格和非价格
        findQuotationNopriceInfo : webconfig.url.equotationServer  + "equPonQuotationNopriceDocService/findQuotationNopriceInfo",
        findQuotationPriceInfo : webconfig.url.equotationServer  + "equPonQuotationPriceDocService/findQuotationPriceInfo",
        saveQuotationInfo : webconfig.url.equotationServer  + "equPonQuotationInfoService/saveQuotationInfo",
        uploadNonPriceFileCommon : webconfig.url.equotationServer  + "equPonQuotationNopriceDocService/uploadNonPriceFileCommon",
        uploadPriceFileCommon : webconfig.url.equotationServer  + "equPonQuotationPriceDocService/uploadPriceFileCommon",
        // 导入产品表
        saveImportForQuotationProduct : webconfig.url.equotationServer  + "equPonQuotationProductInfoService/saveImportForQuotationProduct",
        // 查询产品表信息
        findQuoProductInfo : webconfig.url.equotationServer  + "equPonQuotationProductInfoService/findQuoProductInfo",
        // 删除产品行
        deleteQuotationProductInfo : webconfig.url.equotationServer  + "equPonQuotationProductInfoService/deleteQuotationProductInfo",
        // 导出产品模版
        productTemplateExport : webconfig.url.equotationServer  + "equPonQuotationProductInfoService/productTemplateExport",
        findAuctionHeader: webconfig.url.equotationServer  + "equPonQuotationProductInfoService/findAuctionHeader",

        findBidSupplierList: webconfig.url.equotationServer  + "equPonQuotationInformationService/findBidSupplierList",
        //---------------------------------------------------------------------------------------------------------------------end
        //------------------------------------------pon------------------------------------------------------------------------

        // 评分标准查询
        findPonStandardsInfo : webconfig.url.equotationServer  + "equPonStandardsHService/findPonStandardsInfo",
        findPonStandardsDatail : webconfig.url.equotationServer  + "equPonStandardsHService/findPonStandardsDatail",
        deletePonStandardsLine: webconfig.url.equotationServer  + "equPonStandardsHService/deletePonStandardsLine",
        savePonStandards: webconfig.url.equotationServer  + "equPonStandardsHService/savePonStandards",
        savePonStandardsSubmit: webconfig.url.equotationServer  + "equPonStandardsHService/savePonStandardsSubmit",
        findCopyStandardsLov: webconfig.url.equotationServer  + "equPonStandardsHService/findCopyStandardsLov",

        deletePonStandards: webconfig.url.equotationServer  + "equPonStandardsHService/deletePonStandards",

        findPonInformationInfo: webconfig.url.equotationServer  + "equPonQuotationInformationService/findPonInformationInfo",

        findInformationIdDatail: webconfig.url.equotationServer  + "equPonQuotationInformationService/findInformationIdDatail",

        updateSupplierQuotation: webconfig.url.equotationServer  + "equPonQuotationInformationService/updateSupplierQuotation",

        savePonInvitation: webconfig.url.equotationServer  + "equPonQuotationInformationService/savePonInvitation",

        findMaxProjectInfo: webconfig.url.equotationServer  + "equPonQuotationInformationService/findMaxProjectInfo",

        saveWitnessTeamData: webconfig.url.equotationServer  + "equPonQuotationInformationService/saveWitnessTeamData",

        deleteInformation: webconfig.url.equotationServer  + "equPonQuotationInformationService/deleteInformation",

        findSupplierBidStatusReport: webconfig.url.equotationServer  + "equPonQuotationApprovalService/findSupplierBidStatusReport",

        findSupplierBidStatusReportDetal: webconfig.url.equotationServer  + "equPonQuotationApprovalService/findSupplierBidStatusReportDetal",

        findExportSupplierBid: webconfig.url.equotationServer  + "equPonQuotationApprovalService/findExportSupplierBid",

        findPonQuotationApproval: webconfig.url.equotationServer  + "equPonQuotationApprovalService/findPonQuotationApproval",

        findPonQuotationApprovalDatail: webconfig.url.equotationServer  + "equPonQuotationApprovalService/findPonQuotationApprovalDatail",

        findPonDoubleData: webconfig.url.equotationServer  + "equPonQuotationApprovalService/findPonDoubleData",

        findPonDouQouData: webconfig.url.equotationServer  + "equPonQuotationApprovalService/findPonDouQouData",

        findDoubleTotalAble: webconfig.url.equotationServer  + "equPonQuotationApprovalService/findDoubleTotalAble",

        saveDoubleTotalData: webconfig.url.equotationServer  + "equPonQuotationApprovalService/saveDoubleTotalData",

        saveEquPonQuoApprove: webconfig.url.equotationServer  + "equPonQuotationApprovalService/saveEquPonQuoApprove",
        findApprovalResults: webconfig.url.equotationServer  + "equPonQuotationApprovalService/findApprovalResults",
        deleteQuotationApproval: webconfig.url.equotationServer  + "equPonQuotationApprovalService/deleteQuotationApproval",

        findSecondResult: webconfig.url.equotationServer  + "equPonQuotationApprovalService/findSecondResult",

        // 报表
        findSupplierStatusReportForm: webconfig.url.equotationServer + 'equPosSupplierCategoryService/findSupplierStatusReportForm',
        findSupplierReportForm: webconfig.url.equotationServer + 'equPosSupplierCategoryService/findSupplierReportForm',
        supplierStatusReportFormExcelExport: webconfig.url.equotationServer + 'equPosSupplierCategoryService/supplierStatusReportFormExcelExport',
        //------------------------------------------pon------------------------------------------------------------------------end
        /***********************************************E-QUOTATION************************************************************/



        //PLM_OB_产品开发_产品资质文件汇总
        findPlmDevelopmentQaSummaryInfo: webconfig.url.plmServer + 'plmDevelopmentQaSummaryService/findPlmDevelopmentQaSummaryInfo',
        savePlmDevelopmentQaSummaryInfo: webconfig.url.plmServer + 'plmDevelopmentQaSummaryService/savePlmDevelopmentQaSummaryInfo',
        deletePlmDevelopmentQaSummaryInfo: webconfig.url.plmServer + 'plmDevelopmentQaSummaryService/deletePlmDevelopmentQaSummaryInfo',

        //PLM_product 商品新增接口
        saveProductInfo:webconfig.url.plmServer+'plmProductHeadService/saveProductInfo',  //新增/ 保存 接口
        findTemple:webconfig.url.plmServer+'plmProductHeadtempleService/findTemple', //查询我的模板
        saveTemple:webconfig.url.plmServer+'plmProductHeadtempleService/saveProductTemple', //保存我的模板信息
        FindProductList:webconfig.url.plmServer+'plmProductHeadService/FindProductList', //
        findProductById:webconfig.url.plmServer+ 'plmProductHeadService/findProductById', // 商品详情
        isupdateproduct:webconfig.url.plmServer+ 'plmProductHeadService/isupdateproduct',//是否正在审批中
        updateProval:webconfig.url.plmServer+ 'plmProductHeadService/updateProval', //审批之后的回调方法
        addobProduct:webconfig.url.plmServer+ 'plmProductHeadService/addobProduct', //添加ob商品接口
        updateProductInfo:webconfig.url.plmServer+ 'plmProductHeadService/updateProductInfo',//修改商品接口
        SaveProductByExcel:webconfig.url.plmServer+ 'plmProductHeadService/SaveProductByExcel', //excel导入

        /*文件上传服务*/
        getSaafFileUploadList: webconfig.url.baseServer  + 'saafFileUploadService/getSaafFileUploadList', // 查询文件上传记录
        saveSaafFileUpload: webconfig.url.baseServer  + 'saafFileUploadService/saveSaafFileUpload', //保存文件上传记录
        deleteSaafFileUpload: webconfig.url.baseServer  + 'saafFileUploadService/deleteSaafFileUpload', // 删除文件上传记录

        //PLM_OB_产品开发_产品资质文件明细
        findPlmDevelopmentQaDetailInfo: webconfig.url.plmServer + 'plmDevelopmentQaDetailService/findPlmDevelopmentQaDetailInfo',
        savePlmDevelopmentQaDetailInfo: webconfig.url.plmServer + 'plmDevelopmentQaDetailService/savePlmDevelopmentQaDetailInfo',
        deletePlmDevelopmentQaDetailInfo: webconfig.url.plmServer + 'plmDevelopmentQaDetailService/deletePlmDevelopmentQaDetailInfo',

        //PLM_OB_产品开发_产品成分表
        findPlmDevelopmentIngredientsInfo: webconfig.url.plmServer + 'plmDevelopmentIngredientsService/findPlmDevelopmentIngredientsInfo',
        savePlmDevelopmentIngredientsInfo: webconfig.url.plmServer + 'plmDevelopmentIngredientsService/savePlmDevelopmentIngredientsInfo',
        deletePlmDevelopmentIngredientsInfo: webconfig.url.plmServer + 'plmDevelopmentIngredientsService/deletePlmDevelopmentIngredientsInfo',

        //PLM_OB_产品开发_包装规格书
        findPlmPackageSpecificationInfo: webconfig.url.plmServer + 'plmPackageSpecificationService/findPlmPackageSpecificationInfo',
        savePlmPackageSpecificationInfo: webconfig.url.plmServer + 'plmPackageSpecificationService/savePlmPackageSpecificationInfo',
        deletePlmPackageSpecificationInfo: webconfig.url.plmServer + 'plmPackageSpecificationService/deletePlmPackageSpecificationInfo',

        //PLM_OB_产品开发_批次信息
        findPlmDevelopmentBatchInfoInfo: webconfig.url.plmServer + 'plmDevelopmentBatchInfoService/findPlmDevelopmentBatchInfoInfo',
        savePlmDevelopmentBatchInfoInfo: webconfig.url.plmServer + 'plmDevelopmentBatchInfoService/savePlmDevelopmentBatchInfoInfo',
        deletePlmDevelopmentBatchInfoInfo: webconfig.url.plmServer + 'plmDevelopmentBatchInfoService/deletePlmDevelopmentBatchInfoInfo',

        //PLM_OB_产品异常_详情头
        findPlmProductExceptionInfoInfo: webconfig.url.plmServer + 'plmProductExceptionInfoService/findPlmProductExceptionInfoInfo',
        savePlmProductExceptionInfoInfo: webconfig.url.plmServer + 'plmProductExceptionInfoService/savePlmProductExceptionInfoInfo',

        //PLM_OB_产品异常_明细
        findPlmProductExceptionDetailInfo: webconfig.url.plmServer + 'plmProductExceptionDetailService/findPlmProductExceptionDetailInfo',
        savePlmProductExceptionDetailInfo: webconfig.url.plmServer + 'plmProductExceptionDetailService/savePlmProductExceptionDetailInfo',
        deletePlmProductExceptionDetailInfo: webconfig.url.plmServer + 'plmProductExceptionDetailService/deletePlmProductExceptionDetailInfo',

        //PLM_BASE_特殊商品类型
        findPlmSpecialProductTypeInfo: webconfig.url.plmServer + 'plmSpecialProductTypeService/findPlmSpecialProductTypeInfo',
        savePlmSpecialProductTypeInfo: webconfig.url.plmServer + 'plmSpecialProductTypeService/savePlmSpecialProductTypeInfo',
        deletePlmSpecialProductTypeInfo: webconfig.url.plmServer + 'plmSpecialProductTypeService/deletePlmSpecialProductTypeInfo',

        //PLM_BASE_地点清单
        findPlmLocationListInfo: webconfig.url.plmServer + 'plmLocationListService/findPlmLocationListInfo',
        savePlmLocationListInfo: webconfig.url.plmServer + 'plmLocationListService/savePlmLocationListInfo',

        //PLM_BASE_供应商信息(RMS)
        findPlmRmsSupplierInfoInfo: webconfig.url.plmServer + 'plmRmsSupplierInfoService/findPlmRmsSupplierInfoInfo',
        savePlmRmsSupplierInfoInfo: webconfig.url.plmServer + 'plmRmsSupplierInfoService/savePlmRmsSupplierInfoInfo',

        //PLM_BASE_部门及分类清单
        findPlmDeptListInfo: webconfig.url.plmServer + 'plmDeptListService/findPlmDeptListInfo',
        savePlmDeptListInfo: webconfig.url.plmServer + 'plmDeptListService/savePlmDeptListInfo',

        //PLM_BASE_部门及分类清单-行
        findPlmDeptClassInfo: webconfig.url.plmServer + 'plmDeptClassService/findPlmDeptClassInfo',
        savePlmDeptClassInfo: webconfig.url.plmServer + 'plmDeptClassService/savePlmDeptClassInfo',
        deletePlmDeptClassInfo: webconfig.url.plmServer + 'plmDeptClassService/deletePlmDeptClassInfo',

        //PLM_BASE_线上渠道头
        findPlmOnlineRouteInfo: webconfig.url.plmServer + 'plmOnlineRouteService/findPlmOnlineRouteInfo',
        savePlmOnlineRouteInfo: webconfig.url.plmServer + 'plmOnlineRouteService/savePlmOnlineRouteInfo',

        //PLM_BASE_线上渠道行
        findPlmOnlineSubrouteInfo: webconfig.url.plmServer + 'plmOnlineSubrouteService/findPlmOnlineSubrouteInfo',
        savePlmOnlineSubrouteInfo: webconfig.url.plmServer + 'plmOnlineSubrouteService/savePlmOnlineSubrouteInfo',
        deletePlmOnlineSubrouteInfo: webconfig.url.plmServer + 'plmOnlineSubrouteService/deletePlmOnlineSubrouteInfo',

        //PLM_BASE_税收分类编码
        findPlmTaxTypeListInfo: webconfig.url.plmServer + 'plmTaxTypeListService/findPlmTaxTypeListInfo',
        savePlmTaxTypeListInfo: webconfig.url.plmServer + 'plmTaxTypeListService/savePlmTaxTypeListInfo',

        //PLM_BASE_售价区域
        findPlmSalesAreaInfo: webconfig.url.plmServer + 'plmSalesAreaService/findPlmSalesAreaInfo',
        savePlmSalesAreaInfo: webconfig.url.plmServer + 'plmSalesAreaService/savePlmSalesAreaInfo',

        //PLM_BASE_售价区域行
        findPlmSalesAreaRowInfo: webconfig.url.plmServer + 'plmSalesAreaRowService/findPlmSalesAreaRowInfo',
        savePlmSalesAreaRowInfo: webconfig.url.plmServer + 'plmSalesAreaRowService/savePlmSalesAreaRowInfo',
        deletePlmSalesAreaRowInfo: webconfig.url.plmServer + 'plmSalesAreaRowService/deletePlmSalesAreaRowInfo',

        //PLM_BASE_部门分类及子分类
        findPlmDeptSubclassInfo: webconfig.url.plmServer + 'plmDeptSubclassService/findPlmDeptSubclassInfo',
        savePlmDeptSubclassInfo: webconfig.url.plmServer + 'plmDeptSubclassService/savePlmDeptSubclassInfo',
        deletePlmDeptSubclassInfo: webconfig.url.plmServer + 'plmDeptSubclassService/deletePlmDeptSubclassInfo',

        //PLM_BASE_品牌
        findPlmBrandInfoInfo: webconfig.url.plmServer + 'plmBrandInfoService/findPlmBrandInfoInfo',
        savePlmBrandInfoInfo: webconfig.url.plmServer + 'plmBrandInfoService/savePlmBrandInfoInfo',

        //PLM_BASE_GROUP_BRAND
        findPlmGroupBrandInfo: webconfig.url.plmServer + 'plmGroupBrandService/findPlmGroupBrandInfo',
        savePlmGroupBrandInfo: webconfig.url.plmServer + 'plmGroupBrandService/savePlmGroupBrandInfo',

        //PLM_BASE_MOTHER_COMPANY
        findPlmMotherCompanyInfo: webconfig.url.plmServer + 'plmMotherCompanyService/findPlmMotherCompanyInfo',
        savePlmMotherCompanyInfo: webconfig.url.plmServer + 'plmMotherCompanyService/savePlmMotherCompanyInfo',

        //PLM_BASE_供应商资质管理详情头
        findPlmSupplierQaNonObInfoInfo: webconfig.url.plmServer + 'plmSupplierQaNonObInfoService/findPlmSupplierQaNonObInfoInfo',
        savePlmSupplierQaNonObInfoInfo: webconfig.url.plmServer + 'plmSupplierQaNonObInfoService/savePlmSupplierQaNonObInfoInfo',

        //PLM_BASE_供应商资质管理品牌行
        findPlmSupplierQaBrandInfo: webconfig.url.plmServer + 'plmSupplierQaBrandService/findPlmSupplierQaBrandInfo',
        savePlmSupplierQaBrandInfo: webconfig.url.plmServer + 'plmSupplierQaBrandService/savePlmSupplierQaBrandInfo',
        deletePlmSupplierQaBrandInfo: webconfig.url.plmServer + 'plmSupplierQaBrandService/deletePlmSupplierQaBrandInfo',


        //PLM_BASE_供应商资质管理经销商/生产商行
        findPlmSupplierQaDealerInfo: webconfig.url.plmServer + 'plmSupplierQaDealerService/findPlmSupplierQaDealerInfo',
        savePlmSupplierQaDealerInfo: webconfig.url.plmServer + 'plmSupplierQaDealerService/savePlmSupplierQaDealerInfo',
        deletePlmSupplierQaDealerInfo: webconfig.url.plmServer + 'plmSupplierQaDealerService/deletePlmSupplierQaDealerInfo',

        //PLM_BASE_原产国
        findPlmCountryOfOriginInfo: webconfig.url.plmServer + 'plmCountryOfOriginService/findPlmCountryOfOriginInfo',
        savePlmCountryOfOriginInfo: webconfig.url.plmServer + 'plmCountryOfOriginService/savePlmCountryOfOriginInfo',

        //PLM_BASE_字段帮助说明
        findPlmHelpStatementsInfo: webconfig.url.plmServer + 'plmHelpStatementsService/findPlmHelpStatementsInfo',
        savePlmHelpStatementsInfo: webconfig.url.plmServer + 'plmHelpStatementsService/savePlmHelpStatementsInfo',

        imgUpload:webconfig.url.baseServer+ 'fileUploadService/imgUpload',
        DeleteProductFileById:webconfig.url.plmServer+'plmProductFileService/DeleteProductFileById',
        DeleteProductById:webconfig.url.plmServer+'plmProductHeadService/DeleteProductById',
        DeleteDrugfileById:webconfig.url.plmServer+'plmProductDrugfileService/DeleteDrugfileById',
        DeleteMedicalfileById:webconfig.url.plmServer+'plmProductMedicalfileService/DeleteMedicalfileById',
        DeleteProductChannalById:webconfig.url.plmServer+'plmProductOnlineChannalService/DeleteProductChannalById',
        DeleteProductPackageById:webconfig.url.plmServer+'plmProductPackageService/DeleteProductPackageById',
        DeletePicFileById:webconfig.url.plmServer+'plmProductPicfileTableService/DeletePicFileById',
        DeleteProductPlaceById:webconfig.url.plmServer+'plmProductPlaceInfoService/DeleteProductPlaceById',
        DeleteProductPriceById:webconfig.url.plmServer+'plmProductPriceService/DeleteProductPriceById',
        DeleteProductQaFileById:webconfig.url.plmServer+'plmProductQaFileService/DeleteProductQaFileById',
        DeleteProductSalePropertiesById:webconfig.url.plmServer+'plmProductSalesPropertiesService/DeleteProductSalePropertiesById',
        DeleteProductSupplierById:webconfig.url.plmServer+'plmProductSupplierInfoService/DeleteProductSupplierById',
        DeleteBarcodeById:webconfig.url.plmServer+'plmProductBarcodeTableService/DeleteBarcodeById',
        FindProductUpdateList:webconfig.url.plmServer+'plmProductUpdatepropertisService/FindProductUpdateList',
        updateobProduct:webconfig.url.plmServer+'plmProductHeadService/updateobProduct',
        FindProductReportList:webconfig.url.plmServer+'plmProductHeadService/FindProductReportList',
        updateProductByExcel:webconfig.url.plmServer+'plmProductHeadService/updateProductByExcel',
        FindProductReportTList:webconfig.url.plmServer+'plmProductHeadService/FindProductReportTList',
        FindProductconditionReportList:webconfig.url.plmServer+'plmProductHeadService/FindProductconditionReportList',
        saveProductInstanceById:webconfig.url.plmServer+'plmProductHeadService/saveProductInstanceById',
        FindProductUpdatePackage:webconfig.url.plmServer+'plmProductHeadService/FindProductUpdatePackage',
        FindProductUpdateSupplier:webconfig.url.plmServer+'plmProductHeadService/FindProductUpdateSupplier',
        productSupplierReturn:webconfig.url.plmServer+'plmProductHeadService/productSupplierReturn',

        //plm补货
        plmSupplementHeadService_findPlmsInfo:webconfig.url.plmServer+'plmSupplementHeadService/findPlmsInfo',
        plmSupplementHeadService_findPlmsInfoDetail:webconfig.url.plmServer+'plmSupplementHeadService/findPlmsInfoDetail',
        savePlmSupplementInfo:webconfig.url.plmServer+'plmSupplementHeadService/savePlmSupplementInfo',
        findPlmSupItem:webconfig.url.plmServer+'plmSupplementHeadService/findPlmSupItem',
        findPlmSupWarehouseInfo:webconfig.url.plmServer+'plmSupWarehouseService/findPlmSupWarehouseInfo',
        importWarehouse:webconfig.url.plmServer+'plmSupWarehouseService/importWarehouse',

        FindProductProcessList:webconfig.url.plmServer+'plmProductBpmPersonService/FindProductProcessList',
        updateProcessList:webconfig.url.plmServer+'plmProductBpmPersonService/updateProcessList',

        removePlmLine:webconfig.url.plmServer+'plmSupplementLineService/removePlmLine',
        findPlmLineDetail:webconfig.url.plmServer+'plmSupplementLineService/findPlmLineDetail',
        saveFile:webconfig.url.plmServer+'plmSupplementLineFileService/saveFile',
        deleteProductFileById:webconfig.url.plmServer+'plmSupplementLineFileService/DeleteProductFileById',
        plmSupplementLineService_findMeter:webconfig.url.plmServer+'plmSupplementLineService/findMeter',
        updateShopsSupBefore:webconfig.url.plmServer+'plmSupplementHeadService/updateShopsSupBefore',
        FindProductList2:webconfig.url.plmServer+'plmProductHeadService/FindProductList2',
        importSupLines:webconfig.url.plmServer+'plmSupplementLineService/importSupLines',
        findLines:webconfig.url.plmServer+'plmSupplementLineService/findLines',
        importWarehouse2:webconfig.url.plmServer+'plmSupWarehouseService/importWarehouse2',

        productPicfileReturn:webconfig.url.plmServer+'plmProductHeadService/productPicfileReturn',
        productQafileReturn:webconfig.url.plmServer+'plmProductHeadService/productQafileReturn',
        FindUpdateByHeadid:webconfig.url.plmServer+'plmProductUpdatepropertisService/FindUpdateByHeadid',
        saveUpdateinfo:webconfig.url.plmServer+'plmProductUpdatepropertisService/saveUpdateinfo',

        //OI拆分字段查询
        findFieldPagination: webconfig.url.ttaServer + 'ttaOiFieldMappingService/findFieldPagination', //查询
        findResourceField: webconfig.url.ttaServer + 'ttaOiFieldMappingService/findResourceField',
        saveOrUpdateField: webconfig.url.ttaServer + 'ttaOiFieldMappingService/saveOrUpdateField',


        findPlmseriesInfo:webconfig.url.plmServer+'plmBaseSeriesService/findPlmseriesInfo',
        savePlmseriesInfo:webconfig.url.plmServer+'plmBaseSeriesService/savePlmseriesInfo',
        findPlmseriesInfoById:webconfig.url.plmServer+'plmBaseSeriesService/findPlmseriesInfoById',
        delPlmseriesInfoById:webconfig.url.plmServer+'plmBaseSeriesService/delPlmseriesInfoById',

        findUserRoleProfile:webconfig.url.baseServer+'baseUsersService/findUserRoleProfile',
        findUserRoleMenu:webconfig.url.baseServer+'baseUsersService/findUserRoleMenu',
        //TTA Proposal Summary
        findNodeList: webconfig.url.ttaServer + 'processSummaryService/findNodeList',//查询流程节点
        findPropsolProcessSummery: webconfig.url.ttaServer + 'processSummaryService/findPropsolProcessSummery',//查询流程结果数据

        //TTA contract summary
        findContractNodeList: webconfig.url.ttaServer + 'processSummaryService/findContractNodeList',//查询合同流程节点

        //电子盖章start
        updateEleSkipStatus: webconfig.url.ttaServer + 'ttaElecConHeaderService/updateSkipStatus',//是否审批
        findElecConHeaderPagination: webconfig.url.ttaServer + 'ttaElecConHeaderService/find',//查询电子合同头信息
        findContractAttrList: webconfig.url.ttaServer + 'ttaElecConHeaderService/findContractAttrList',//合同附件信息
        findElecConHeaderById: webconfig.url.ttaServer + 'ttaElecConHeaderService/findById',//查询电子签章头部信息
        saveElecConHeader: webconfig.url.ttaServer + 'ttaElecConHeaderService/saveElecConHeader',//保存电子盖章头信息
        findEeleContractHandleList :webconfig.url.ttaServer + 'ttaElecConHeaderService/findEeleContractHandleList',//电子签章返回结果信息
        findAddNotExistsAttList: webconfig.url.ttaServer + 'ttaElecConHeaderService/findAddNotExistsAttList',//查询需要添加的附件信息
        saveContractAttrList: webconfig.url.ttaServer + 'ttaElecConHeaderService/saveContractAttrList',//添加电子签章信息
        deleteContractDetail:webconfig.url.ttaServer + 'ttaElecConHeaderService/deleteContractDetail',//添加电子签章附件信息删除
        changeElecAll:webconfig.url.ttaServer + 'ttaElecConHeaderService/changeElecAll',//电子签章-变更
        updateStatus:webconfig.url.ttaServer + 'ttaElecConHeaderService/updateStatus',//更新审批状态
        findDicValues: webconfig.url.ttaServer + 'ttaElecConHeaderService/findDicValues',//变更类型查询
        findElecContractReport:webconfig.url.ttaServer + 'ttaProposalStatusService/findElecContractReport', //电子盖章流程报表
        findAttCheckList: webconfig.url.ttaServer + 'ttaElecConHeaderService/findAttCheckList',//查询选中的附件列表信息
        createContractAttr: webconfig.url.ttaServer + 'ttaElecConHeaderService/createContractAttr',//生成合同
        findSepcial: webconfig.url.ttaServer + 'ttaElecConHeaderService/findSepcial', //特批单号查询
        findReceive: webconfig.url.ttaServer + 'ttaElecConHeaderService/findReceive', //合同领用单号
        findAndSaveContractNo: webconfig.url.ttaServer + 'ttaElecConHeaderService/findAndSaveContractNo',

        //电子盖章end

        //OI费用管理start
        findReportAttGenRecord: webconfig.url.ttaServer + 'ttaReportAttGenRecordService/find',//查询报表生成记录
        deleteReportAttGenRecord: webconfig.url.ttaServer + 'ttaReportAttGenRecordService/deleteReportAttGenRecord',//删除报表生成记录
        findReportAttGenById:  webconfig.url.ttaServer + 'ttaReportAttGenRecordService/findById',//通过id查询
        //OI费用管理end
        findTtaVSActualAchieveRateReport:  webconfig.url.ttaServer + 'ttaOiReportFieldHeaderService/findTtaVSActualAchieveRateReport',//报表4
        /*guojiecheng start*/
        findUserGroupAssignsForUser:webconfig.url.baseServer+'baseUserGroupAssignServices/findUserGroupAssignsForUser',//根据用户id 查询用户群组
        saveUserGroupAssignsForUser:webconfig.url.baseServer+'baseUserGroupAssignServices/saveUserGroupAssignsForUser',//指定用户关联多个群组
        deleteUserGroupAssignsForGroup:webconfig.url.baseServer+'baseUserGroupAssignServices/deleteUserGroupAssignsForGroup',//删除用户群组中的用户
        findUserGroupAssignsForGroup:webconfig.url.baseServer+'baseUserGroupAssignServices/findUserGroupAssignsForGroup',//根据用户群组编码查询数据
        deleteUserGroupAssignsForUser:webconfig.url.baseServer+'baseUserGroupAssignServices/deleteUserGroupAssignsForUser',//删除用户中的群组
        saveUserGroupAssignsForGroup:webconfig.url.baseServer+'baseUserGroupAssignServices/saveUserGroupAssignsForGroup',//指定群组关联用户
        findNoAssignsForUser:webconfig.url.baseServer+'baseUserGroupAssignServices/findNoAssignsForUser',//查询未分配给指定用户的用户群组

        /*guojiecheng end*/
    }
});
