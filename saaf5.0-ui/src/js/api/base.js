/**
 * Created by Administrator on 2018/3/22.
 */

define(['webconfig'], function (webconfig) {
    return {
        login: webconfig.url.baseServer + 'baseLoginService/login',  //登录
        logout: webconfig.url.baseServer + 'baseLoginService/logoff',// 退出
        findUserInfo: webconfig.url.baseServer + 'baseLoginService/getUserInfo', // 获取用户信息

        findSelectMenu: webconfig.url.baseServer + 'baseMenuService/findSelectMenu',// 获取可收藏的功能菜单列表
        findInCollection: webconfig.url.baseServer + 'baseFunctionCollectionService/findInCollection',// 获取已收藏的功能菜单列表
        addInCollection: webconfig.url.baseServer + 'baseFunctionCollectionService/addInCollection',// 添加收藏功能菜单
        deleteInCollection: webconfig.url.baseServer + 'baseFunctionCollectionService/deleteInCollection',// 删除已收藏功能菜单
        findMenuList: webconfig.url.baseServer + 'baseMenuService/findMenuList',// 删除已收藏功能菜单

        /*修改密码*/
        changePassword: webconfig.url.baseServer + 'baseUsersService/changePassword',

        /* 菜单管理 */
        menuSave: webconfig.url.baseServer + 'baseMenuService/save', // 保存,修改菜单
        menuListByRole: webconfig.url.baseServer + 'baseMenuService/findBaseMenuByRoleId', // 根据角色Id查询菜单
        menuListByReps: webconfig.url.baseServer + 'baseMenuService/findBaseMenuByRespId',// 根据现职查菜单;
        resourceByRespMenuId: webconfig.url.baseServer + 'baseResourceService/findBaseResourceByRespMenuId', //根据菜单和职责id获取资源 旧
        resourceByRespMenuCode: webconfig.url.baseServer + 'baseAccreditService/findResource', // 根据菜单编码、职责id 获取 资源
        getOprResp: webconfig.url.baseServer + 'baseAccreditService/getOprResp', //获取菜单的操作职责
        menuClickCount: webconfig.url.baseServer + 'baseMenuService/menuClickCount',// 菜单统计
        /*职责切换*/
        findMenuInfo: webconfig.url.baseServer + 'baseAccreditService/findMenuInfo', // 查询菜单
        findRespByMenuCode: webconfig.url.baseServer + 'baseAccreditService/findRespByMenuCode', // 查询菜单下职责
        changeResp: webconfig.url.baseServer + 'baseAccreditService/changeResp', // 切换职责

        queryLookupLineDic: webconfig.url.baseServer + 'baseLookupValuesService/findDic', // 字典使用查询
        exportData: webconfig.url.exportServer + 'dataExportService/export', // 导出功能
        exportDataStatus: webconfig.url.exportServer + 'dataExportService/getExportResult', // 导出状态
        /*固定资产列表*/
        findAssetMaterialPagination: webconfig.url.oaServer + 'oaAssetMaterialService/findAssetMaterialPagination',
        //固定资产列表新
        findAssetMaterialPaginationByOrgId: webconfig.url.oaServer + 'oaAssetMaterialService/findAssetMaterialPaginationByOrgId',
        /* 通用控件服务 */
        getDealerAccount: './json/getDealerAccount.json',  // 经销商
        getDealerSubAccount: './json/getDealerSubAccount.json',  // 经销商子库

        /* getCountryData: webconfig.url.baseServer + 'baseRegionService/getCountryData', // 	获取国家列表
         getProvinceDataByCountryId: webconfig.url.baseServer + 'baseRegionService/getProvinceDataByCountryId', //根据国家ID获取省
         getCityDataByProvinceId: webconfig.url.baseServer + 'baseRegionService/getCityDataByProvinceId', //根据省ID获取市的数据
         getCountryAreaDataByCityId: webconfig.url.baseServer + 'baseRegionService/getCountryAreaDataByCityId', //	 根据市的ID获取区县的数据*/

        getCountryData: webconfig.url.baseServer + 'baseAdminstrativeRegionService/findCountry', // 	获取国家列表
        getProvinceDataByCountryId: webconfig.url.baseServer + 'baseAdminstrativeRegionService/findProvinces', //根据国家ID获取省
        getCityDataByProvinceId: webconfig.url.baseServer + 'baseAdminstrativeRegionService/findByParent', //根据省ID获取市的数据
        getCountryAreaDataByCityId: webconfig.url.baseServer + 'baseAdminstrativeRegionService/findPagination', //	 根据市的ID获取区县的数据

        getCityDataByProvinceId2: webconfig.url.baseServer + 'baseAdminstrativeRegionService/findPagination', //根据省ID获取市的数据


        getAllCityData:webconfig.url.baseServer + 'baseAdminstrativeRegionService/findByParent', // 公共查下级城市数据

        edocUpload: webconfig.url.baseServer + 'fileUploadService/edocUpload', // 附件上传接口
        imgUpload: webconfig.url.baseServer + 'fileUploadService/imgUpload',// 图片上传
        base64ImgUpload: webconfig.url.baseServer + 'fileUploadService/base64ImgUpload',// 图片上传
        eDocPreview: webconfig.url.eDocServer + 'Preview.aspx',  // 文档预览接口
        eDocDownload: webconfig.url.eDocServer + 'Document/File_Download.aspx', // 	文档下载接口
        eDocToken: webconfig.url.baseServer + 'edocApiService/getEdocUserToken', //  获取eDoc 用户token
        eDocDelete: webconfig.url.baseServer + 'fileUploadService/edocDelete', //  eDoc 文件删除
        imgDelete: webconfig.url.baseServer + 'fileUploadService/fileDelete',  // 图片删除接口
        multiDownload: webconfig.url.baseServer + 'fileUploadService/batchDownload', // 批量下载
        imgDownload:webconfig.url.baseServer + 'fileUploadService/imgDownload', //图片下载
        getRespInfo:webconfig.url.baseServer + 'baseAccreditService/getRespInfo', // 根据用户ID与职责ID获取职责详情 params:{"userId":"1","respId":"720007"}

        base_ruleExpression: webconfig.url.baseServer + '/ruleExpressionService/findRuleExpression', // 查询规则业务表达式
        base_lookupVal: webconfig.url.baseServer + '/baseLookupValuesService/find', // 查询渠道

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
    }

})