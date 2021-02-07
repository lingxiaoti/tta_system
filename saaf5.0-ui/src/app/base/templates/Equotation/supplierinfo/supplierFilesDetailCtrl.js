/**
 * Created by sie_panshibang on 2019/9/4.
 */
define(["app", "angularFileUpload", "pinyin"], function (app, angularFileUpload, pinyin) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('supplierFilesDetailCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'iframeTabAction','$http','SIEJS', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, iframeTabAction,$http,SIEJS) {
        var id = $stateParams.supplierId;
        var name = $stateParams.supplierName;
        $scope.params = {};
        $scope.program = {};
        $scope.supplierProgram = {};
        $scope.paramsInit = {};
        $scope.rowsIndex = 5;
        $scope.paramsInit.supplierName = name;

        //当前登录人所属部门
        $scope.program.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.program.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;

        //联系人目录
        $scope.supplierContactDataTable = [];
        //可合作品类
        $scope.supplierCopCategoryDataTable = [];
        //服务范围
        $scope.supplierServiceScopeDataTable = [];
        //银行信息
        $scope.supplierBankInfoDataTable = [];
        //生产资质信息(OEM)
        $scope.productQualificationTable = [];
        $scope.productQualificationTable1 = {};
        $scope.productQualificationTable2 = {};
        $scope.productQualificationTable3 = {};
        $scope.productQualificationTable4 = {};
        $scope.productQualificationTable5 = {};

        //资质文件(OEM/IT)
        $scope.qualificationsFileDataTable = [];
        //地址信息(OEM)
        $scope.oemAddressInfoDataTable = [];
        //生产信息(OEM)
        $scope.oemProductionInfoParams = {};
        //产能信息(OEM)
        $scope.oemCapacityInfoDataTable = [];
        //经营状况(IT)
        $scope.itOperationalInfoParams = {};
        //地址信息(IT)
        $scope.itAddressInfoDataTable = [];
        //生产信息(IT)
        // $scope.itProductionInfoDataTable = [];
        //产能信息(IT)
        $scope.itCapacityInfoDataTable = [];
        //资质信息
        $scope.supplierCredentialsProgram = {};
        //关联工厂
        $scope.relatedFactoryDataTable = [];

        //周边环境
        $scope.surEnvironmentDataTable = [];
        //大门门牌
        $scope.doorPlateDataTable = [];
        //前台
        $scope.receptionDataTable = [];
        //公司面积
        $scope.companyAreaDataTable = [];
        //办公场所
        $scope.officeSpaceDataTable = [];
        //员工概况
        $scope.employeeProfileDataTable = [];

        //生产许可证
        $scope.productionPermitDataTable = [];
        //质量安全管理体系认证证明
        $scope.qualitySafetyDataTable = [];
        //CSR报告
        $scope.csrReportDataTable = [];
        //消防验收证明
        $scope.fireAcceptanceDataTable = [];
        //建筑工程竣工验收报告
        $scope.projectAcceptanceDataTable = [];
        //其他附件
        $scope.otherDataTable = [];


        /********************查询供应商档案信息**********************/
        $scope.search = function () {
            $scope.searchBaseInfo();
            $scope.searchRelatedFactoryInfo()
            $scope.searchCategoryInfo();
            $scope.searchServiceScope();
            $scope.searchContacterInfo();
            $scope.searchCredentialsInfo();
            $scope.searchBankInfo();
            $scope.searchProductQualificationsInfo();
            $scope.searchProductQualificationsInfo2();
            $scope.searchProductQualificationsInfo3();
            $scope.searchProductQualificationsInfo4();
            $scope.searchProductQualificationsInfo5();
            $scope.searchProductQualificationsInfo6();
            $scope.searchProductQualificationsInfo7();
            $scope.searchQualificationsFileInfo();
            $scope.searchOemAddressInfo();
            $scope.searchItAddressInfo();
            $scope.supplierScoreDataTable()
        };

        /********************查询供应商基本信息**********************/
        $scope.searchBaseInfo = function () {
            httpServer.post(URLAPI.findSupplierInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    deptCode:$scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierProgram = res.data[0];

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商关联工厂**********************/
        $scope.searchRelatedFactoryInfo = function () {
            httpServer.post(URLAPI.findAssociatedSupplier, {
                params: JSON.stringify({
                    supplierId: id,
                    deptCode:   $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.relatedFactoryDataTable = res.data;
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商品类信息**********************/
        $scope.searchCategoryInfo = function () {
            httpServer.post(URLAPI.findSupplierCategorysInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    deptCode:   $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierCopCategoryDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商服务范围**********************/
        $scope.searchServiceScope = function () {
            httpServer.post(URLAPI.findSupplierCategorysInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    deptCode:   $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierServiceScopeDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商联系人信息**********************/
        $scope.searchContacterInfo = function () {
            httpServer.post(URLAPI.findSupplierContactsInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    deptCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierContactDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商资质信息**********************/
        $scope.searchCredentialsInfo = function () {
            httpServer.post(URLAPI.findSupplierCredentialsInfo, {
                params: JSON.stringify({
                    supplierId: id
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierCredentialsProgram = res.data[0];

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商生产资质信息**********************/
        $scope.searchProductQualificationsInfo = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    isProductFactory :'Y',
                    fileType: '',
                    deptCode:$scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.productQualificationTable1 = res.data[0];
                        $scope.productQualificationTable2 = res.data[1];
                        $scope.productQualificationTable3 = res.data[2];
                        $scope.productQualificationTable4 = res.data[3];
                        $scope.productQualificationTable5 = res.data[4];
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo2 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    isProductFactory :'Y',
                    fileType: 'productionPermit',
                    deptCode:$scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.productionPermitDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo3 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    isProductFactory :'Y',
                    fileType: 'qualitySafety',
                    deptCode:$scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.qualitySafetyDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo4 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    isProductFactory :'Y',
                    fileType: 'csrReport',
                    deptCode:$scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.csrReportDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo5 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    isProductFactory :'Y',
                    fileType: 'fireAcceptance',
                    deptCode:$scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.fireAcceptanceDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo6 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    isProductFactory :'Y',
                    fileType: 'projectAcceptance',
                    deptCode:$scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.projectAcceptanceDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo7 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    isProductFactory :'Y',
                    fileType: 'other',
                    deptCode:$scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.otherDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商资质文件信息**********************/
        $scope.searchQualificationsFileInfo = function () {
            httpServer.post(URLAPI.findCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    isProductFactory :'N'
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.qualificationsFileDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商银行信息**********************/
        $scope.searchBankInfo = function () {
            httpServer.post(URLAPI.findSupplierBankInfo, {
                params: JSON.stringify({
                    supplierId: id
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierBankInfoDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询OEM地址信息**********************/
        $scope.searchOemAddressInfo = function () {
            httpServer.post(URLAPI.findSupplierAddressInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    deptCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.oemAddressInfoDataTable = res.data;
                    $scope.oemAddressInfoDataTable.selectRow=0;
                    var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
                    $scope.loadLinesData(row,1);
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询IT地址信息**********************/
        $scope.searchItAddressInfo = function () {
            httpServer.post(URLAPI.findSupplierAddressInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    deptCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.itAddressInfoDataTable = res.data;
                    $scope.itAddressInfoDataTable.selectRow=0;
                    var row = $scope.itAddressInfoDataTable[$scope.itAddressInfoDataTable.selectRow];
                    $scope.loadITLinesData(row,1);
                }
            }, function (error) {
                console.error(error)
            })
        };
        /********************查询综合表现**********************/
        $scope.supplierScoreDataTable = function () {
            httpServer.post(URLAPI.findSupplierScoreInfo, {
                params: JSON.stringify({
                    supplierId: id,
                    deptCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierScoreDataTable = res.data;
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.showMore = function (row) {
            var type = row.atType
            switch (type) {
                case '信用审核':
                    iframeTabAction('信用审核更新查询', 'equPosCreditAuditChange');
                    // iframeTabAction('信用审核查询', 'equPosCreditAudit');

                    break;
                case '质量审核':
                    // iframeTabAction('质量审核查询', 'equPosQualityAudit');
                    iframeTabAction('质量审核更新查询', 'qualityUpdate');
                    break;
                case 'CSR审核':
                    // iframeTabAction('CSR审核查询', 'equPosCsrAudit');
                    iframeTabAction('CSR审核更新查询', 'csrUpdate');
                    break;
                case '年度评分':
                    iframeTabAction('年度评分查询', 'scoreUpdate');
                    break;
                case 'Commercial contract':
                    iframeTabAction('CommercialContract', 'contractUpdate');
                    break;
                case 'Supplier spend prfile':
                    iframeTabAction('SupplierSpendPrfile', 'spendUpdate');
                    break;
            }
        };



        //选中地址行时，加载关联行数据
        $scope.loadITLinesData = function(row,$index){
            $scope.itOperationalInfoParams = row.itOperationalInfoParams;
            $scope.itCapacityInfoDataTable = row.itCapacityInfoParams;
            $scope.surEnvironmentDataTable = row.surEnvironmentDataTable;
            $scope.doorPlateDataTable = row.doorPlateDataTable;
            $scope.receptionDataTable = row.receptionDataTable;
            $scope.companyAreaDataTable = row.companyAreaDataTable;
            $scope.officeSpaceDataTable = row.officeSpaceDataTable;
            $scope.employeeProfileDataTable = row.employeeProfileDataTable;
        };

        //选中地址行时，加载关联行数据
        $scope.loadLinesData = function(row,$index){
            $scope.oemProductionInfoParams = row.oemProductionInfoParams;
            $scope.oemCapacityInfoDataTable = row.oemCapacityInfoParams;
        };

        /********************判断是新增还是修改********************/
        if (id == "") {

        } else {
            $scope.search();
        }

        //查看工厂/贸易商详情
        $scope.viewSupplierDetail = function (row) {
            iframeTabAction('供应商档案详情', 'supplierFilesDetail/' + row.associatedSupplierId + '/' + row.supplierName);
        };

        /********************供应商基本信息保存*********************/
        $scope.saveForSupplierFilesDetail = function () {
            $scope.supplierProgram.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            httpServer.post(URLAPI.saveForSupplierFilesDetail, {
                'params': JSON.stringify(
                    $scope.supplierProgram
                )}, function (res) {
                if (res.status == 'S') {
                    $scope.searchBaseInfo();
                    SIEJS.alert("操作成功!", "success");
                }
            }, function (error) {
                SIEJS.alert(res.msg, 'error', '确定');
                console.error(error)
            })
        };
    }]);
})