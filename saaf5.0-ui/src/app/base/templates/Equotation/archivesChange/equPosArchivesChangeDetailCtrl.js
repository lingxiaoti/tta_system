/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload',"GooFlow"], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('equPosArchivesChangeDetailCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'iframeTabAction','$http','SIEJS','$timeout','$controller', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, iframeTabAction,$http,SIEJS,$timeout,$controller) {
        $controller('processBase', {$scope:$scope}); // 继承基础的流程控制器
        if ($stateParams.changeId){
            var id = $stateParams.changeId;
        }else if ($scope.urlParams.businessKey){
            var id = $scope.urlParams.businessKey;
        }
        // var id = $stateParams.changeId;
        $scope.params = {};
        $scope.bgqParams = {};
        $scope.bghParams = {};
        $scope.program = {};
        $scope.showParams = {};
        $scope.rowsIndex = 5;
        $scope.disabledFlag = false;
        $scope.catetoryParams = {};

        $scope.supplierProgram = {};
        $scope.supplierBghProgram = {};
        $scope.relatedFactoryDataTable = [];
        $scope.bgqRelatedFactoryDataTable = [];
        $scope.bghRelatedFactoryDataTable = [];
        $scope.supplierCredentialsProgram = {};
        $scope.supplierCredentialsBghProgram = {};
        $scope.supplierCopCategoryDataTable = [];
        $scope.supplierBghCopCategoryDataTable = [];
        $scope.supplierServiceScopeDataTable = [];
        $scope.supplierBghServiceScopeDataTable = [];
        $scope.supplierContactDataTable = [];
        $scope.supplierBghContactDataTable = [];

        $scope.productQualificationTable = [];
        $scope.productQualificationTable1 = {};
        $scope.productQualificationTable2 = {};
        $scope.productQualificationTable3 = {};
        $scope.productQualificationTable4 = {};
        $scope.productQualificationTable5 = {};

        $scope.bghProductQualificationTable = [];
        $scope.bghProductQualificationTable1 = {};
        $scope.bghProductQualificationTable2 = {};
        $scope.bghProductQualificationTable3 = {};
        $scope.bghProductQualificationTable4 = {};
        $scope.bghProductQualificationTable5 = {};

        //生产许可证(变更前)
        $scope.productionPermitDataTable = [];
        //质量安全管理体系认证证明(变更前)
        $scope.qualitySafetyDataTable = [];
        //CSR报告(变更前)
        $scope.csrReportDataTable = [];
        //消防验收证明(变更前)
        $scope.fireAcceptanceDataTable = [];
        //建筑工程竣工验收报告(变更前)
        $scope.projectAcceptanceDataTable = [];
        //其他附件(变更前)
        $scope.otherDataTable = [];

        //生产许可证(变更后)
        $scope.bghProductionPermitDataTable = [];
        //质量安全管理体系认证证明(变更后)
        $scope.bghQualitySafetyDataTable = [];
        //CSR报告(变更后)
        $scope.bghCsrReportDataTable = [];
        //消防验收证明(变更后)
        $scope.bghFireAcceptanceDataTable = [];
        //建筑工程竣工验收报告(变更后)
        $scope.bghProjectAcceptanceDataTable = [];
        //其他附件(变更后)
        $scope.bghOtherDataTable = [];

        $scope.qualificationsFileDataTable = [];
        $scope.bghQualificationsFileDataTable = [];
        $scope.supplierBankInfoDataTable = [];
        $scope.supplierBghBankInfoDataTable = [];
        $scope.oemAddressInfoDataTable = [];
        $scope.oemAddressInfoBghDataTable = [];
        $scope.itAddressInfoDataTable = [];
        $scope.itAddressInfoBghDataTable = [];
        $scope.oemProductionInfoParams = {};
        $scope.oemProductionInfoBghParams = {};
        $scope.oemCapacityInfoDataTable = [];
        $scope.oemCapacityInfoBghDataTable = [];
        $scope.itOperationalInfoParams = {};
        $scope.itOperationalInfoBghParams = {};
        $scope.itCapacityInfoDataTable = [];
        $scope.itCapacityInfoBghDataTable = [];

        $scope.surEnvironmentDataTable = [];
        $scope.surEnvironmentBghDataTable = [];
        $scope.doorPlateDataTable = [];
        $scope.doorPlateBghDataTable = [];
        $scope.receptionDataTable = [];
        $scope.receptionBghDataTable = [];
        $scope.companyAreaDataTable = [];
        $scope.companyAreaBghDataTable = [];
        $scope.officeSpaceDataTable = [];
        $scope.officeSpaceBghDataTable = [];
        $scope.employeeProfileDataTable = [];
        $scope.employeeProfileBghDataTable = [];

        //当前登录人所属部门
        $scope.program.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.program.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;

        $scope.disabledFlag = 'N';

        $scope.program.status = '10';

        /********************新增时,根据选择的供应商查询供应商相关信息**********************/
        $scope.supplierInfoSearch = function () {
            $scope.searchBaseInfo();
            $scope.searchRelatedFactoryInfo()
            $scope.searchCategoryInfo();
            $scope.searchServiceScope();
            $scope.searchContacterInfo();
            $scope.searchCredentialsInfo();
            $scope.searchBankInfo();
            // $scope.searchProductQualificationsInfo();
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
        };

        /********************查询供应商档案变更单据信息**********************/
        $scope.search = function (id) {
            httpServer.post(URLAPI.findArchivesChangeOrder, {
                params: JSON.stringify({
                    changeId: id
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.program = res.data[0];

                    if($scope.program.status == '10' || $scope.program.status == '40'){
                        $scope.disabledFlag = 'N';
                    }else{
                        $scope.disabledFlag = 'Y';
                    }

                    $scope.searchBghBaseInfo();
                    // $scope.searchBghRelatedFactoryInfo()
                    $scope.searchBghCategoryInfo();
                    $scope.searchBghServiceScope();
                    $scope.searchBghContacterInfo();
                    $scope.searchBghCredentialsInfo();
                    $scope.searchBghBankInfo();
                    $scope.searchBghProductQualificationsInfo();
                    $scope.searchBghProductQualificationsInfo2();
                    $scope.searchBghProductQualificationsInfo3();
                    $scope.searchBghProductQualificationsInfo4();
                    $scope.searchBghProductQualificationsInfo5();
                    $scope.searchBghProductQualificationsInfo6();
                    $scope.searchBghProductQualificationsInfo7();
                    $scope.searchBghQualificationsFileInfo();
                    $scope.searchBghOemAddressInfo();
                    $scope.searchBghItAddressInfo();

                    $scope.searchBgqBaseInfo();
                    $scope.searchBgqRelatedFactoryInfo()
                    $scope.searchBgqCategoryInfo();
                    $scope.searchBgqServiceScope();
                    $scope.searchBgqContacterInfo();
                    $scope.searchBgqCredentialsInfo();
                    $scope.searchBgqBankInfo();
                    $scope.searchBgqProductQualificationsInfo();
                    $scope.searchBgqProductQualificationsInfo2();
                    $scope.searchBgqProductQualificationsInfo3();
                    $scope.searchBgqProductQualificationsInfo4();
                    $scope.searchBgqProductQualificationsInfo5();
                    $scope.searchBgqProductQualificationsInfo6();
                    $scope.searchBgqProductQualificationsInfo7();
                    $scope.searchBgqQualificationsFileInfo();
                    $scope.searchBgqOemAddressInfo();
                    $scope.searchBgqItAddressInfo();

                    //查询各个子信息
                    $scope.historicParam = {}
                    $scope.historicParam.code = $scope.program.changeNumber
                    httpServer.post(URLAPI.getActivitiesHistoric, {
                        params: JSON.stringify($scope.historicParam)
                    }, function (resa) {
                        if (resa.status == "S") {
                            $scope.processInstanceParams = {
                                processInstanceId: resa.data
                            };
                            $scope.taskTable.search();
                        }
                    }, function (error) {
                    });

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************判断是新增还是修改********************/
        if (id == "" || id == undefined) {

        } else {
            $scope.search(id);
        };

        /********************查询变更后供应商基本信息**********************/
        $scope.searchBghBaseInfo = function () {
            httpServer.post(URLAPI.findBgSupplierInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId,
                    deptCode:   $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierBghProgram = res.data[0];
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更后供应商关联工厂**********************/
        $scope.searchBghRelatedFactoryInfo = function () {
            httpServer.post(URLAPI.findBgAssociatedSupplier, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId,
                    deptCode:   $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.bghRelatedFactoryDataTable = res.data;
                    if(res.data.length <= $scope.relatedFactoryDataTable.length){
                        for(var i = 0; i < res.data.length; i++){
                            $scope.relatedFactoryDataTable[i].supplierBghName = res.data[i].supplierName;
                            $scope.relatedFactoryDataTable[i].associatedSupplierBghId = res.data[i].supplierId;
                            $scope.relatedFactoryDataTable[i].sourceId = res.data[i].sourceId;
                        }
                    }
                    else{
                        var count = $scope.relatedFactoryDataTable.length;
                        for(var i = 0; i < res.data.length; i++){
                            if(count < (i + 1)){
                                var supplierArray = {
                                    supplierName: '',
                                    associatedSupplierId: '',
                                    supplierBghName: res.data[i].supplierName,
                                    associatedSupplierBghId: res.data[i].supplierId,
                                    sourceId:res.data[i].sourceId,
                                    deptCode: $scope.program.deptCode
                                };
                                $scope.relatedFactoryDataTable.push(supplierArray);
                            }else{
                                $scope.relatedFactoryDataTable[i].supplierBghName = res.data[i].supplierName;
                                $scope.relatedFactoryDataTable[i].associatedSupplierBghId = res.data[i].supplierId;
                                $scope.relatedFactoryDataTable[i].sourceId = res.data[i].sourceId;
                            }
                        }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更后供应商品类信息**********************/
        $scope.searchBghCategoryInfo = function () {
            httpServer.post(URLAPI.findBgSupplierCategorysInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierBghCopCategoryDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更后供应商服务范围**********************/
        $scope.searchBghServiceScope = function () {
            httpServer.post(URLAPI.findBgSupplierCategorysInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierBghServiceScopeDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更后供应商联系人信息**********************/
        $scope.searchBghContacterInfo = function () {
            httpServer.post(URLAPI.findBgSupplierContactsInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierBghContactDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更后供应商资质信息**********************/
        $scope.searchBghCredentialsInfo = function () {
            httpServer.post(URLAPI.findBgSupplierCredentialsInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierCredentialsBghProgram = res.data[0];
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更后供应商生产资质信息**********************/
        // $scope.searchBghProductQualificationsInfo = function () {
        //     httpServer.post(URLAPI.findBgCredentialsAttachmentInfo, {
        //         params: JSON.stringify({
        //             changeId: $scope.program.changeId,
        //             isProductFactory :'Y'
        //         })
        //     }, function (res) {
        //         if (res.status == 'S') {
        //             if(res.data.length > 0){
        //                 $scope.bghProductQualificationTable = res.data;
        //             }
        //         }
        //     }, function (error) {
        //         console.error(error)
        //     })
        // };

        /********************查询变更后供应商资质文件信息**********************/
        $scope.searchBghQualificationsFileInfo = function () {
            httpServer.post(URLAPI.findBgCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId,
                    isProductFactory :'N'
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.bghQualificationsFileDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更后供应商生产资质信息**********************/
        $scope.searchBghProductQualificationsInfo = function () {
            httpServer.post(URLAPI.findBgCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'FIXED',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.bghProductQualificationTable1 = res.data[0];
                        $scope.bghProductQualificationTable2 = res.data[1];
                        $scope.bghProductQualificationTable3 = res.data[2];
                        $scope.bghProductQualificationTable4 = res.data[3];
                        $scope.bghProductQualificationTable5 = res.data[4];
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchBghProductQualificationsInfo2 = function () {
            httpServer.post(URLAPI.findBgCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'productionPermit',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.bghProductionPermitDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchBghProductQualificationsInfo3 = function () {
            httpServer.post(URLAPI.findBgCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'qualitySafety',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.bghQualitySafetyDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchBghProductQualificationsInfo4 = function () {
            httpServer.post(URLAPI.findBgCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'csrReport',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.bghCsrReportDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchBghProductQualificationsInfo5 = function () {
            httpServer.post(URLAPI.findBgCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'fireAcceptance',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.bghFireAcceptanceDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchBghProductQualificationsInfo6 = function () {
            httpServer.post(URLAPI.findBgCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'projectAcceptance',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.bghProjectAcceptanceDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchBghProductQualificationsInfo7 = function () {
            httpServer.post(URLAPI.findBgCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'other',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.bghOtherDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更后供应商银行信息**********************/
        $scope.searchBghBankInfo = function () {
            httpServer.post(URLAPI.findBgSupplierBankInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierBghBankInfoDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更后OEM地址信息**********************/
        $scope.searchBghOemAddressInfo = function () {
            httpServer.post(URLAPI.findBgSupplierAddressInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.oemAddressInfoBghDataTable = res.data;
                        $scope.oemAddressInfoBghDataTable.selectRow=0;
                        var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
                        $scope.loadLinesBghData(row,1);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更后IT地址信息**********************/
        $scope.searchBghItAddressInfo = function () {
            httpServer.post(URLAPI.findBgSupplierAddressInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.changeId,
                    deptCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.itAddressInfoBghDataTable = res.data;
                        $scope.itAddressInfoBghDataTable.selectRow=0;
                        var row = $scope.itAddressInfoBghDataTable[$scope.itAddressInfoBghDataTable.selectRow];
                        $scope.loadITLinesData(row,1);
                        $scope.loadITLinesBghData(row,1);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };


        /********************查询变更前供应商基本信息**********************/
        $scope.searchBgqBaseInfo = function () {
            httpServer.post(URLAPI.findBgqSupplierInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierProgram = res.data[0];
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更前供应商关联工厂**********************/
        $scope.searchBgqRelatedFactoryInfo = function () {
            httpServer.post(URLAPI.findBgqAssociatedSupplier, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId,
                    deptCode:   $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.bgqRelatedFactoryDataTable = res.data;
                    $scope.relatedFactoryDataTable = res.data;
                    $scope.searchBghRelatedFactoryInfo();
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更前供应商品类信息**********************/
        $scope.searchBgqCategoryInfo = function () {
            httpServer.post(URLAPI.findBgqSupplierCategorysInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierCopCategoryDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更前供应商服务范围**********************/
        $scope.searchBgqServiceScope = function () {
            debugger;
            httpServer.post(URLAPI.findBgqSupplierCategorysInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                debugger;
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierServiceScopeDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更前供应商联系人信息**********************/
        $scope.searchBgqContacterInfo = function () {
            httpServer.post(URLAPI.findBgqSupplierContactsInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierContactDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更前供应商资质信息**********************/
        $scope.searchBgqCredentialsInfo = function () {
            httpServer.post(URLAPI.findBgqSupplierCredentialsInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierCredentialsProgram = res.data[0];
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        // /********************查询变更前供应商生产资质信息**********************/
        // $scope.searchBgqProductQualificationsInfo = function () {
        //     httpServer.post(URLAPI.findBgqCredentialsAttachmentInfo, {
        //         params: JSON.stringify({
        //             changeId: $scope.program.changeId,
        //             isProductFactory :'Y'
        //         })
        //     }, function (res) {
        //         if (res.status == 'S') {
        //             if(res.data.length > 0){
        //                 $scope.productQualificationTable = res.data;
        //             }
        //         }
        //     }, function (error) {
        //         console.error(error)
        //     })
        // };

        // /********************查询变更前供应商资质文件信息**********************/
        $scope.searchBgqQualificationsFileInfo = function () {
            httpServer.post(URLAPI.findBgqCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId,
                    isProductFactory :'N'
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.qualificationsFileDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更前供应商生产资质信息**********************/
        $scope.searchBgqProductQualificationsInfo = function () {
            httpServer.post(URLAPI.findBgqCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'FIXED',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
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

        $scope.searchBgqProductQualificationsInfo2 = function () {
            httpServer.post(URLAPI.findBgqCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'productionPermit',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.productionPermitDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchBgqProductQualificationsInfo3 = function () {
            httpServer.post(URLAPI.findBgqCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'qualitySafety',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.qualitySafetyDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchBgqProductQualificationsInfo4 = function () {
            httpServer.post(URLAPI.findBgqCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'csrReport',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.csrReportDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchBgqProductQualificationsInfo5 = function () {
            httpServer.post(URLAPI.findBgqCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'fireAcceptance',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.fireAcceptanceDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchBgqProductQualificationsInfo6 = function () {
            httpServer.post(URLAPI.findBgqCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'projectAcceptance',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.projectAcceptanceDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchBgqProductQualificationsInfo7 = function () {
            httpServer.post(URLAPI.findBgqCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'other',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.otherDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更前供应商银行信息**********************/
        $scope.searchBgqBankInfo = function () {
            httpServer.post(URLAPI.findBgqSupplierBankInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierBankInfoDataTable = res.data;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更前OEM地址信息**********************/
        $scope.searchBgqOemAddressInfo = function () {
            httpServer.post(URLAPI.findBgqSupplierAddressInfo, {
                params: JSON.stringify({
                    changeId: $scope.program.changeId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.oemAddressInfoDataTable = res.data;
                        $scope.oemAddressInfoDataTable.selectRow=0;
                        var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
                        $scope.loadLinesData(row,1);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询变更前IT地址信息**********************/
        $scope.searchBgqItAddressInfo = function () {
            httpServer.post(URLAPI.findBgqSupplierAddressInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.changeId,
                    deptCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.itAddressInfoDataTable = angular.copy(res.data);
                        $scope.itAddressInfoDataTable.selectRow=0;
                        var row = $scope.itAddressInfoDataTable[$scope.itAddressInfoDataTable.selectRow];
                        $scope.loadITLinesData(row,1);
                        $scope.loadITLinesBghData(row,1);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };


        /********************查询供应商基本信息**********************/
        $scope.searchBaseInfo = function () {
            httpServer.post(URLAPI.findSupplierInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode:$scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierProgram = angular.copy(res.data[0]);
                    $scope.supplierProgram.sourceId = undefined;
                    $scope.supplierBghProgram = angular.copy(res.data[0]);
                    $scope.supplierBghProgram.sourceId = undefined;
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商关联工厂**********************/
        $scope.searchRelatedFactoryInfo = function () {
            httpServer.post(URLAPI.findAssociatedSupplier, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode:   $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.bgqRelatedFactoryDataTable = angular.copy(res.data);
                    $scope.bghRelatedFactoryDataTable = angular.copy(res.data);
                    $scope.relatedFactoryDataTable = angular.copy(res.data);
                    for(var i = 0; i < $scope.relatedFactoryDataTable.length; i++){
                        $scope.relatedFactoryDataTable[i].associatedSupplierBghId = $scope.relatedFactoryDataTable[i].supplierId;
                        $scope.relatedFactoryDataTable[i].supplierBghName = $scope.relatedFactoryDataTable[i].supplierName;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商品类信息**********************/
        $scope.searchCategoryInfo = function () {
            httpServer.post(URLAPI.findSupplierCategorysInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode:   $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierCopCategoryDataTable = angular.copy(res.data);
                        $scope.supplierBghCopCategoryDataTable = angular.copy(res.data);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商服务范围**********************/
        $scope.searchServiceScope = function () {
            httpServer.post(URLAPI.findSupplierCategorysInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode:   $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierServiceScopeDataTable = angular.copy(res.data);
                        $scope.supplierBghServiceScopeDataTable = angular.copy(res.data);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商联系人信息**********************/
        $scope.searchContacterInfo = function () {
            httpServer.post(URLAPI.findSupplierContactsInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierContactDataTable = angular.copy(res.data);
                        $scope.supplierBghContactDataTable = angular.copy(res.data);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商资质信息**********************/
        $scope.searchCredentialsInfo = function () {
            httpServer.post(URLAPI.findSupplierCredentialsInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierCredentialsProgram = angular.copy(res.data[0]);
                        $scope.supplierCredentialsBghProgram = angular.copy(res.data[0]);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商生产资质信息**********************/
        $scope.searchProductQualificationsInfo = function () {
            httpServer.post(URLAPI.findCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory :'Y'
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.productQualificationTable = angular.copy(res.data);
                        $scope.bghProductQualificationTable = angular.copy(res.data);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商生产资质信息**********************/
        $scope.searchProductQualificationsInfo = function () {
            httpServer.post(URLAPI.findCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'FIXED',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.productQualificationTable1 = angular.copy(res.data[0]);
                        $scope.productQualificationTable2 = angular.copy(res.data[1]);
                        $scope.productQualificationTable3 = angular.copy(res.data[2]);
                        $scope.productQualificationTable4 = angular.copy(res.data[3]);
                        $scope.productQualificationTable5 = angular.copy(res.data[4]);

                        $scope.bghProductQualificationTable1 = angular.copy(res.data[0]);
                        $scope.bghProductQualificationTable2 = angular.copy(res.data[1]);
                        $scope.bghProductQualificationTable3 = angular.copy(res.data[2]);
                        $scope.bghProductQualificationTable4 = angular.copy(res.data[3]);
                        $scope.bghProductQualificationTable5 = angular.copy(res.data[4]);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo2 = function () {
            httpServer.post(URLAPI.findCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'productionPermit',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.productionPermitDataTable = angular.copy(res.data);
                        $scope.bghProductionPermitDataTable = angular.copy(res.data);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo3 = function () {
            httpServer.post(URLAPI.findCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'qualitySafety',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.qualitySafetyDataTable = angular.copy(res.data);
                        $scope.bghQualitySafetyDataTable = angular.copy(res.data);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo4 = function () {
            httpServer.post(URLAPI.findCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'csrReport',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.csrReportDataTable = angular.copy(res.data);
                        $scope.bghCsrReportDataTable = angular.copy(res.data);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo5 = function () {
            httpServer.post(URLAPI.findCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'fireAcceptance',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.fireAcceptanceDataTable = angular.copy(res.data);
                        $scope.bghFireAcceptanceDataTable = angular.copy(res.data);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo6 = function () {
            httpServer.post(URLAPI.findCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'projectAcceptance',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.projectAcceptanceDataTable = angular.copy(res.data);
                        $scope.bghProjectAcceptanceDataTable = angular.copy(res.data);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo7 = function () {
            httpServer.post(URLAPI.findCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'other',
                    deptmentCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.otherDataTable = angular.copy(res.data);
                        $scope.bghOtherDataTable = angular.copy(res.data);
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
                    supplierId: $scope.program.supplierId,
                    isProductFactory :'N'
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.qualificationsFileDataTable = angular.copy(res.data);
                        $scope.bghQualificationsFileDataTable = angular.copy(res.data);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商银行信息**********************/
        $scope.searchBankInfo = function () {
            httpServer.post(URLAPI.findSupplierBankInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.supplierBankInfoDataTable = angular.copy(res.data);
                        $scope.supplierBghBankInfoDataTable = angular.copy(res.data);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询OEM地址信息**********************/
        $scope.searchOemAddressInfo = function () {
            httpServer.post(URLAPI.findSupplierAddressInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.oemAddressInfoDataTable = angular.copy(res.data);
                        $scope.oemAddressInfoBghDataTable = angular.copy(res.data);
                        $scope.oemAddressInfoDataTable.selectRow=0;
                        var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
                        $scope.loadLinesData(row,1);
                        $scope.oemAddressInfoBghDataTable.selectRow=0;
                        var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
                        $scope.loadLinesBghData(row,1);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询IT地址信息**********************/
        $scope.searchItAddressInfo = function () {
            httpServer.post(URLAPI.findSupplierAddressInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data.length > 0){
                        $scope.itAddressInfoDataTable = angular.copy(res.data);
                        $scope.itAddressInfoBghDataTable = angular.copy(res.data);
                        $scope.itAddressInfoDataTable.selectRow=0;
                        var row = $scope.itAddressInfoDataTable[$scope.itAddressInfoDataTable.selectRow];
                        $scope.loadITLinesData(row,1);
                        $scope.loadITLinesBghData(row,1);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        //选中地址行时，加载关联行数据
        $scope.loadITLinesData = function(row,$index){
            $scope.itOperationalInfoParams = row.itOperationalInfoParams;
            $scope.itCapacityInfoDataTable = row.itCapacityInfoDataTable;
            $scope.surEnvironmentDataTable = row.surEnvironmentDataTable;
            $scope.doorPlateDataTable = row.doorPlateDataTable;
            $scope.receptionDataTable = row.receptionDataTable;
            $scope.companyAreaDataTable = row.companyAreaDataTable;
            $scope.officeSpaceDataTable = row.officeSpaceDataTable;
            $scope.employeeProfileDataTable = row.employeeProfileDataTable;
        }

        //选中地址行时，加载关联行数据
        $scope.loadLinesData = function(row,$index){
            $scope.oemProductionInfoParams = row.oemProductionInfoParams;
            $scope.oemCapacityInfoDataTable = row.oemCapacityInfoParams;
        }

        //选中地址行时，加载关联行数据(变更后)
        $scope.loadITLinesBghData = function(row,$index){
            $scope.itOperationalInfoBghParams = row.itOperationalInfoParams;
            $scope.itCapacityInfoBghDataTable = row.itCapacityInfoDataTable;
            $scope.surEnvironmentBghDataTable = row.surEnvironmentDataTable;
            $scope.doorPlateBghDataTable = row.doorPlateDataTable;
            $scope.receptionBghDataTable = row.receptionDataTable;
            $scope.companyAreaBghDataTable = row.companyAreaDataTable;
            $scope.officeSpaceBghDataTable = row.officeSpaceDataTable;
            $scope.employeeProfileBghDataTable = row.employeeProfileDataTable;
        }

        //选中地址行时，加载关联行数据(变更后)
        $scope.loadLinesBghData = function(row,$index){
            $scope.oemProductionInfoBghParams = row.oemProductionInfoParams;
            $scope.oemCapacityInfoBghDataTable = row.oemCapacityInfoParams;
        }

        //增加周边环境附件
        $scope.addSurEnvironmentFile = function () {
            var SurEnvironmentFileArray = {
                deptmentCode: $scope.program.deptCode,
                fileType:"SurEnvironment",
                index:$scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.surEnvironmentBghDataTable.push(SurEnvironmentFileArray);
        }
        //增加大门门牌附件
        $scope.addDoorPlateFile = function () {
            var CapacityArray = {
                deptmentCode: $scope.program.deptCode,
                fileType:"DoorPlate",
                index:$scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.doorPlateBghDataTable.push(CapacityArray);
        }
        //增加前台附件
        $scope.addReceptionFile = function () {
            var CapacityArray = {
                deptmentCode: $scope.program.deptCode,
                fileType:"Reception",
                index:$scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.receptionBghDataTable.push(CapacityArray);
        }
        //增加公司面积附件
        $scope.addCompanyAreaFile = function () {
            var CapacityArray = {
                deptmentCode: $scope.program.deptCode,
                fileType:"CompanyArea",
                index:$scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.companyAreaBghDataTable.push(CapacityArray);
        }
        //增加办公场所附件
        $scope.addOfficeSpaceFile = function () {
            var CapacityArray = {
                deptmentCode: $scope.program.deptCode,
                fileType:"OfficeSpace",
                index:$scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.officeSpaceBghDataTable.push(CapacityArray);
        }
        //增加员工概况附件
        $scope.addEmployeeProfileFile = function () {
            var CapacityArray = {
                deptmentCode: $scope.program.deptCode,
                fileType:"EmployeeProfile",
                index:$scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.employeeProfileBghDataTable.push(CapacityArray);
        }

        $scope.showBidList = function (index) {
            $scope.showParams.copIsSelected = !$scope.showParams.copIsSelected;
        }

        $scope.showBidList2 = function (index) {
            $scope.showParams.serviceIsSelected = !$scope.showParams.serviceIsSelected;
        }

        $scope.showBidList3 = function (index) {
            $scope.showParams.contactIsSelected = !$scope.showParams.contactIsSelected;
        }

        $scope.showBidList4 = function (index) {
            $scope.showParams.sczzIsSelected = !$scope.showParams.sczzIsSelected;
        }

        $scope.showBidList5 = function (index) {
            $scope.showParams.zzwjIsSelected = !$scope.showParams.zzwjIsSelected;
        }

        $scope.showBidList6 = function (index) {
            $scope.showParams.yhxxIsSelected = !$scope.showParams.yhxxIsSelected;
        }

        $scope.showBidList7 = function (index) {
            $scope.showParams.itdzIsSelected = !$scope.showParams.itdzIsSelected;
        }

        $scope.showBidList8 = function (index) {
            $scope.showParams.cnxxIsSelected = !$scope.showParams.cnxxIsSelected;
        }

        $scope.showBidList9 = function (index) {
            $scope.showParams.jjzkIsSelected = !$scope.showParams.jjzkIsSelected;
        }

        /********************提交前校验********************/
        $scope.validate = function () {
            //校验国家是否为空
            if ($scope.supplierBghProgram.country == "" || $scope.supplierBghProgram.country == null || $scope.supplierBghProgram.country == undefined) {
                JS.alert('请选择国家', 'error', '确定');
                return true;
            }

            //校验供应商类型是否为空
            if ($scope.supplierBghProgram.supplierType == "" || $scope.supplierBghProgram.supplierType == null || $scope.supplierBghProgram.supplierType == undefined) {
                JS.alert('请选择供应商类型', 'error', '确定');
                return true;
            }

            //校验是否存在联系人行，信息是否填写完整
            if ($scope.supplierBghContactDataTable.length == 0) {
                JS.alert('请添加联系人信息', 'error', '确定');
                return true;
            } else {
                var selectCount = 0;
                for (var i = 0; i < $scope.supplierBghContactDataTable.length; i++) {
                    if ($scope.supplierBghContactDataTable[i].contactName == '' || $scope.supplierBghContactDataTable[i].contactName == undefined) {
                        JS.alert('联系人目录填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.supplierBghContactDataTable[i].mobilePhone == '' || $scope.supplierBghContactDataTable[i].mobilePhone == undefined) {
                        JS.alert('联系人目录填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.supplierBghContactDataTable[i].emailAddress == '' || $scope.supplierBghContactDataTable[i].emailAddress == undefined) {
                        JS.alert('联系人目录填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.supplierBghContactDataTable[i].sendEmailFlag == 'Y') {
                        selectCount = selectCount + 1;
                    }
                }
                if (selectCount == 0) {
                    JS.alert('请选择需要创建账号的联系人', 'error', '确定');
                    return true;
                }
            }

            // if ($scope.flags.userTpye == '60') {
                //校验【主要客户】控制必填
                if ($scope.supplierBghProgram.majorCustomer == "" || $scope.supplierBghProgram.majorCustomer == null || $scope.supplierBghProgram.majorCustomer == undefined) {
                    JS.alert('请选择主要客户信息', 'error', '确定');
                    return true;
                }

                //校验【营业执照号】控制必填
                if ($scope.supplierCredentialsBghProgram.licenseNum == "" || $scope.supplierCredentialsBghProgram.licenseNum == null || $scope.supplierCredentialsBghProgram.licenseNum == undefined) {
                    JS.alert('请填写营业执照号', 'error', '确定');
                    return true;
                }

                //当【长期】没有勾选时，【营业期限】控制必填
                if ($scope.supplierCredentialsBghProgram.longBusinessIndate != 'Y') {
                    if ($scope.supplierCredentialsBghProgram.licenseIndate == "" || $scope.supplierCredentialsBghProgram.licenseIndate == null || $scope.supplierCredentialsBghProgram.licenseIndate == undefined) {
                        JS.alert('请填写营业执照号', 'error', '确定');
                        return true;
                    }
                }

                //校验【营业执照复印件】控制必填
                if ($scope.supplierCredentialsBghProgram.licenseFileId == "" || $scope.supplierCredentialsBghProgram.licenseFileId == null || $scope.supplierCredentialsBghProgram.licenseFileId == undefined) {
                    JS.alert('请上传营业执照复印件', 'error', '确定');
                    return true;
                }

                if ($scope.supplierCredentialsBghProgram.isThreeInOne != 'Y') {
                    // //校验【组织机构代码号】控制必填
                    // if ($scope.supplierCredentialsBghProgram.tissueCode == "" || $scope.supplierCredentialsBghProgram.tissueCode == null || $scope.supplierCredentialsBghProgram.tissueCode == undefined) {
                    //     JS.alert('请填写组织机构代码号', 'error', '确定');
                    //     return true;
                    // }
                    // //校验【组织机构代码到期日】控制必填
                    // if ($scope.supplierCredentialsBghProgram.tissueIndate == "" || $scope.supplierCredentialsBghProgram.tissueIndate == null || $scope.supplierCredentialsBghProgram.tissueIndate == undefined) {
                    //     JS.alert('请填写组织机构代码到期日', 'error', '确定');
                    //     return true;
                    // }
                    // //校验【组织机构代码证】控制必填
                    // if ($scope.supplierCredentialsBghProgram.tissueFileId == "" || $scope.supplierCredentialsBghProgram.tissueFileId == null || $scope.supplierCredentialsBghProgram.tissueFileId == undefined) {
                    //     JS.alert('请上传组织机构代码证附件', 'error', '确定');
                    //     return true;
                    // }
                    // //校验【税务登记证号】控制必填
                    // if ($scope.supplierCredentialsBghProgram.taxCode == "" || $scope.supplierCredentialsBghProgram.taxCode == null || $scope.supplierCredentialsBghProgram.taxCode == undefined) {
                    //     JS.alert('请填写税务登记证号', 'error', '确定');
                    //     return true;
                    // }
                    // //校验【税务登记证】控制必填
                    // if ($scope.supplierCredentialsBghProgram.taxFileId == "" || $scope.supplierCredentialsBghProgram.taxFileId == null || $scope.supplierCredentialsBghProgram.taxFileId == undefined) {
                    //     JS.alert('请上传税务登记证附件', 'error', '确定');
                    //     return true;
                    // }
                }

                // //校验【银行开户许可证号】控制必填
                // if ($scope.supplierCredentialsBghProgram.bankPermitNumber == "" || $scope.supplierCredentialsBghProgram.bankPermitNumber == null || $scope.supplierCredentialsBghProgram.bankPermitNumber == undefined) {
                //     JS.alert('请填写银行开户许可证号', 'error', '确定');
                //     return true;
                // }
                //
                // //校验【银行开户许可证】控制必填
                // if ($scope.supplierCredentialsBghProgram.bankFileId == "" || $scope.supplierCredentialsBghProgram.bankFileId == null || $scope.supplierCredentialsBghProgram.bankFileId == undefined) {
                //     JS.alert('请上传银行开户许可证附件', 'error', '确定');
                //     return true;
                // }

                //校验【一般纳税人证明】控制必填
                if ($scope.supplierCredentialsBghProgram.taxpayerFileId == "" || $scope.supplierCredentialsBghProgram.taxpayerFileId == null || $scope.supplierCredentialsBghProgram.taxpayerFileId == undefined) {
                    JS.alert('请上传一般纳税人证明附件', 'error', '确定');
                    return true;
                }

                //校验【法人姓名】控制必填
                if ($scope.supplierCredentialsBghProgram.representativeName == "" || $scope.supplierCredentialsBghProgram.representativeName == null || $scope.supplierCredentialsBghProgram.representativeName == undefined) {
                    JS.alert('请填写法人姓名', 'error', '确定');
                    return true;
                }

                //校验【经营范围】控制必填
                if ($scope.supplierCredentialsBghProgram.businessScope == "" || $scope.supplierCredentialsBghProgram.businessScope == null || $scope.supplierCredentialsBghProgram.businessScope == undefined) {
                    JS.alert('请填写经营范围', 'error', '确定');
                    return true;
                }

                //校验【成立日期】控制必填
                // if ($scope.supplierCredentialsBghProgram.establishmentDate == "" || $scope.supplierCredentialsBghProgram.establishmentDate == null || $scope.supplierCredentialsBghProgram.establishmentDate == undefined) {
                //     JS.alert('请填写成立日期', 'error', '确定');
                //     return true;
                // }

                //校验【经营年限】控制必填
                // if ($scope.supplierCredentialsBghProgram.businessYears == "" || $scope.supplierCredentialsBghProgram.businessYears == null || $scope.supplierCredentialsBghProgram.businessYears == undefined) {
                //     JS.alert('请填写经营年限', 'error', '确定');
                //     return true;
                // }

                //校验【注册资本】控制必填
                if ($scope.supplierCredentialsBghProgram.enrollCapital == "" || $scope.supplierCredentialsBghProgram.enrollCapital == null || $scope.supplierCredentialsBghProgram.enrollCapital == undefined) {
                    JS.alert('请填写注册资本', 'error', '确定');
                    return true;
                }

                //校验【股东信息】控制必填
                if ($scope.supplierCredentialsBghProgram.shareholderInfo == "" || $scope.supplierCredentialsBghProgram.shareholderInfo == null || $scope.supplierCredentialsBghProgram.shareholderInfo == undefined) {
                    JS.alert('请填写股东信息', 'error', '确定');
                    return true;
                }

                //校验【关联方信息】控制必填
                if ($scope.supplierCredentialsBghProgram.relatedParty == "" || $scope.supplierCredentialsBghProgram.relatedParty == null || $scope.supplierCredentialsBghProgram.relatedParty == undefined) {
                    JS.alert('请填写关联方信息', 'error', '确定');
                    return true;
                }

            //部门：OEM 用户类型：供应商  ，校验产能信息
            // if ($scope.program.deptCode == 'OEM') {
            //     //当前用户类型为【供应商】时，校验产能信息
            //     if ($scope.oemAddressInfoBghDataTable.length == 0) {
            //         JS.alert('请添加地址信息', 'error', '确定');
            //         return true;
            //     } else {
            //         for (var i = 0; i < $scope.oemAddressInfoBghDataTable.length; i++) {
            //             if ($scope.oemAddressInfoBghDataTable[i].addressName == '' || $scope.oemAddressInfoBghDataTable[i].addressName == undefined) {
            //                 JS.alert('地址信息不完整', 'error', '确定');
            //                 return true;
            //             }
            //             if ($scope.oemAddressInfoBghDataTable[i].country == '' || $scope.oemAddressInfoBghDataTable[i].country == undefined) {
            //                 JS.alert('地址信息不完整', 'error', '确定');
            //                 return true;
            //             }
            //             if ($scope.oemAddressInfoBghDataTable[i].province == '' || $scope.oemAddressInfoBghDataTable[i].province == undefined) {
            //                 JS.alert('地址信息不完整', 'error', '确定');
            //                 return true;
            //             }
            //             if ($scope.oemAddressInfoBghDataTable[i].city == '' || $scope.oemAddressInfoBghDataTable[i].city == undefined) {
            //                 JS.alert('地址信息不完整', 'error', '确定');
            //                 return true;
            //             }
            //             if ($scope.oemAddressInfoBghDataTable[i].county == '' || $scope.oemAddressInfoBghDataTable[i].county == undefined) {
            //                 JS.alert('地址信息不完整', 'error', '确定');
            //                 return true;
            //             }
            //             if ($scope.oemAddressInfoBghDataTable[i].detailAddress == '' || $scope.oemAddressInfoBghDataTable[i].detailAddress == undefined) {
            //                 JS.alert('地址信息不完整', 'error', '确定');
            //                 return true;
            //             }
            //             if($scope.supplierBghProgram.supplierType == '20'){
            //                 //校验产能信息-生产信息-工厂开始生产时间 是否为空
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.productionStartDate == '' || $scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.productionStartDate == undefined) {
            //                     JS.alert('请填写工厂开始生产时间', 'error', '确定');
            //                     return true;
            //                 }
            //                 //校验产能信息-生产信息-生产区域面积 是否为空
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.productionArea == '' || $scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.productionArea == undefined) {
            //                     JS.alert('请填写生产区域面积', 'error', '确定');
            //                     return true;
            //                 }
            //                 //校验产能信息-生产信息-成品仓库面积 是否为空
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.finishedProductArea == '' || $scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.finishedProductArea == undefined) {
            //                     JS.alert('请填写成品仓库面积', 'error', '确定');
            //                     return true;
            //                 }
            //                 //校验产能信息-生产信息-原料仓库面积 是否为空
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.rawMaterialArea == '' || $scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.rawMaterialArea == undefined) {
            //                     JS.alert('请填写原料仓库面积', 'error', '确定');
            //                     return true;
            //                 }
            //                 //校验产能信息-生产信息-包装材料仓库面积 是否为空
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.packagingArea == '' || $scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.packagingArea == undefined) {
            //                     JS.alert('请填写包装材料仓库面积', 'error', '确定');
            //                     return true;
            //                 }
            //                 //校验产能信息-生产信息-实验室面积 是否为空
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.laboratoryArea == '' || $scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.laboratoryArea == undefined) {
            //                     JS.alert('请填写实验室面积', 'error', '确定');
            //                     return true;
            //                 }
            //                 //校验产能信息-生产信息-办公区占地面积 是否为空
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.officeArea == '' || $scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.officeArea == undefined) {
            //                     JS.alert('请填写办公区占地面积', 'error', '确定');
            //                     return true;
            //                 }
            //                 //校验产能信息-生产信息-质量人员 是否为空
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.qualityPersonnelAmount == '' || $scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.qualityPersonnelAmount == undefined) {
            //                     JS.alert('请填写质量人员', 'error', '确定');
            //                     return true;
            //                 }
            //                 //校验产能信息-生产信息-销售人员 是否为空
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.salesmanAmount == '' || $scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.salesmanAmount == undefined) {
            //                     JS.alert('请填写销售人员', 'error', '确定');
            //                     return true;
            //                 }
            //                 //校验产能信息-生产信息-生产人员	 是否为空
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.producersAmount == '' || $scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.producersAmount == undefined) {
            //                     JS.alert('请填写生产人员', 'error', '确定');
            //                     return true;
            //                 }
            //                 //校验产能信息-生产信息-设计与开发	 是否为空
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.designerAmount == '' || $scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.designerAmount == undefined) {
            //                     JS.alert('请填写设计与开发', 'error', '确定');
            //                     return true;
            //                 }
            //                 //校验产能信息-生产信息-行政人员	 是否为空
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.administrativeStaffAmount == '' || $scope.oemAddressInfoBghDataTable[i].oemProductionInfoParams.administrativeStaffAmount == undefined) {
            //                     JS.alert('请填写行政人员', 'error', '确定');
            //                     return true;
            //                 }
            //
            //                 //产能信息
            //                 if ($scope.oemAddressInfoBghDataTable[i].oemCapacityInfoParams.length == 0) {
            //                     JS.alert('请添加产能信息', 'error', '确定');
            //                     return true;
            //                 } else {
            //                     for (var j = 0; j < $scope.oemAddressInfoBghDataTable[i].oemCapacityInfoParams.length; j++) {
            //                         if ($scope.oemAddressInfoBghDataTable[i].oemCapacityInfoParams[j].productScope == '' || $scope.oemAddressInfoBghDataTable[i].oemCapacityInfoParams[j].productScope == undefined) {
            //                             JS.alert('产能信息填写不完整', 'error', '确定');
            //                             return true;
            //                         }
            //                         if ($scope.oemAddressInfoBghDataTable[i].oemCapacityInfoParams[j].mainRawMaterials == '' || $scope.oemAddressInfoBghDataTable[i].oemCapacityInfoParams[j].mainRawMaterials == undefined) {
            //                             JS.alert('产能信息填写不完整', 'error', '确定');
            //                             return true;
            //                         }
            //                         if ($scope.oemAddressInfoBghDataTable[i].oemCapacityInfoParams[j].productionEquipment == '' || $scope.oemAddressInfoBghDataTable[i].oemCapacityInfoParams[j].productionEquipment == undefined) {
            //                             JS.alert('产能信息填写不完整', 'error', '确定');
            //                             return true;
            //                         }
            //                         if ($scope.oemAddressInfoBghDataTable[i].oemCapacityInfoParams[j].productionCapacity == '' || $scope.oemAddressInfoBghDataTable[i].oemCapacityInfoParams[j].productionCapacity == undefined) {
            //                             JS.alert('产能信息填写不完整', 'error', '确定');
            //                             return true;
            //                         }
            //                         if ($scope.oemAddressInfoBghDataTable[i].oemCapacityInfoParams[j].productionCapacityFileId == '' || $scope.oemAddressInfoBghDataTable[i].oemCapacityInfoParams[j].productionCapacityFileId == undefined) {
            //                             JS.alert('产能信息填写不完整', 'error', '确定');
            //                             return true;
            //                         }
            //                     }
            //                 }
            //             }
            //         }
            //     }
            // }

            //OEM制造工厂
            if ($scope.program.deptCode == '0E' && $scope.supplierBghProgram.supplierType == '20') {
                if ($scope.supplierBghCopCategoryDataTable.length == 0) {
                    JS.alert('请添加可合作品类', 'error', '确定');
                    return true;
                } else {
                    for (var i = 0; i < $scope.supplierBghCopCategoryDataTable.length; i++) {
                        if ($scope.supplierBghCopCategoryDataTable[i].categoryName == '' || $scope.supplierBghCopCategoryDataTable[i].categoryName == undefined) {
                            JS.alert('可合作品类填写不完整', 'error', '确定');
                            return true;
                        }
                    }
                }
            }

            //IT
            if ($scope.program.deptCode == 'IT') {
                //校验银行信息是否填写完整
                if ($scope.supplierBghBankInfoDataTable.length == 0) {
                    JS.alert('请添加银行信息行', 'error', '确定');
                    return true;
                } else {
                    for (var i = 0; i < $scope.supplierBghBankInfoDataTable.length; i++) {
                        if ($scope.supplierBghBankInfoDataTable[i].bankUserName == '' || $scope.supplierBghBankInfoDataTable[i].bankUserName == undefined) {
                            JS.alert('银行信息填写不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.supplierBghBankInfoDataTable[i].bankAccountNumber == '' || $scope.supplierBghBankInfoDataTable[i].bankAccountNumber == undefined) {
                            JS.alert('银行信息填写不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.supplierBghBankInfoDataTable[i].bankName == '' || $scope.supplierBghBankInfoDataTable[i].bankName == undefined) {
                            JS.alert('银行信息填写不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.supplierBghBankInfoDataTable[i].bankCurrency == '' || $scope.supplierBghBankInfoDataTable[i].bankCurrency == undefined) {
                            JS.alert('银行信息填写不完整', 'error', '确定');
                            return true;
                        }
                    }
                }

                if ($scope.supplierBghServiceScopeDataTable.length == 0) {
                    JS.alert('请添加服务范围', 'error', '确定');
                    return true;
                } else {
                    for (var i = 0; i < $scope.supplierBghServiceScopeDataTable.length; i++) {
                        if ($scope.supplierBghServiceScopeDataTable[i].categoryName == '' || $scope.supplierBghServiceScopeDataTable[i].categoryName == undefined) {
                            JS.alert('服务范围填写不完整', 'error', '确定');
                            return true;
                        }
                    }
                }
            }

            //部门：IT 用户类型：供应商  ，校验具体经营状况及现场照片
            // if ($scope.program.deptCode == 'IT') {
            //     if ($scope.itAddressInfoBghDataTable.length == 0) {
            //         JS.alert('请添加地址信息', 'error', '确定');
            //         return true;
            //     } else {
            //         for (var i = 0; i < $scope.itAddressInfoBghDataTable.length; i++) {
            //             if ($scope.itAddressInfoBghDataTable[i].addressName == '' || $scope.itAddressInfoBghDataTable[i].addressName == undefined) {
            //                 JS.alert('地址信息不完整', 'error', '确定');
            //                 return true;
            //             }
            //             if ($scope.itAddressInfoBghDataTable[i].country == '' || $scope.itAddressInfoBghDataTable[i].country == undefined) {
            //                 JS.alert('地址信息不完整', 'error', '确定');
            //                 return true;
            //             }
            //             if ($scope.itAddressInfoBghDataTable[i].province == '' || $scope.itAddressInfoBghDataTable[i].province == undefined) {
            //                 JS.alert('地址信息不完整', 'error', '确定');
            //                 return true;
            //             }
            //             if ($scope.itAddressInfoBghDataTable[i].city == '' || $scope.itAddressInfoBghDataTable[i].city == undefined) {
            //                 JS.alert('地址信息不完整', 'error', '确定');
            //                 return true;
            //             }
            //             if ($scope.itAddressInfoBghDataTable[i].county == '' || $scope.itAddressInfoBghDataTable[i].county == undefined) {
            //                 JS.alert('地址信息不完整', 'error', '确定');
            //                 return true;
            //             }
            //             if ($scope.itAddressInfoBghDataTable[i].detailAddress == '' || $scope.itAddressInfoBghDataTable[i].detailAddress == undefined) {
            //                 JS.alert('地址信息不完整', 'error', '确定');
            //                 return true;
            //             }
            //             //校验具体经营状况及现场照片-经营状况-周边环境 是否为空
            //             if ($scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.surroundingEnvironment == '' || $scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.surroundingEnvironment == undefined) {
            //                 JS.alert('请填写周边环境内容', 'error', '确定');
            //                 return true;
            //             }
            //             //校验具体经营状况及现场照片-经营状况-周边环境附件 是否为空
            //             if ($scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.surEnvironmentFileId == '' || $scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.surEnvironmentFileId == undefined) {
            //                 JS.alert('请上传周边环境附件', 'error', '确定');
            //                 return true;
            //             }
            //             //校验具体经营状况及现场照片-经营状况-大门门牌 是否为空
            //             if ($scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.doorPlate == '' || $scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.doorPlate == undefined) {
            //                 JS.alert('请填写大门门牌内容', 'error', '确定');
            //                 return true;
            //             }
            //             //校验具体经营状况及现场照片-经营状况-大门门牌附件 是否为空
            //             if ($scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.doorPlateFileId == '' || $scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.doorPlateFileId == undefined) {
            //                 JS.alert('请上传大门门牌附件', 'error', '确定');
            //                 return true;
            //             }
            //             //校验具体经营状况及现场照片-经营状况-前台 是否为空
            //             if ($scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.reception == '' || $scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.reception == undefined) {
            //                 JS.alert('请填写前台内容', 'error', '确定');
            //                 return true;
            //             }
            //             //校验具体经营状况及现场照片-经营状况-前台附件 是否为空
            //             if ($scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.receptionFileId == '' || $scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.receptionFileId == undefined) {
            //                 JS.alert('请上传前台附件', 'error', '确定');
            //                 return true;
            //             }
            //             //校验具体经营状况及现场照片-经营状况-公司面积 是否为空
            //             if ($scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.companyArea == '' || $scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.companyArea == undefined) {
            //                 JS.alert('请填写公司面积内容', 'error', '确定');
            //                 return true;
            //             }
            //             //校验具体经营状况及现场照片-经营状况-公司面积附件 是否为空
            //             if ($scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.companyAreaFileId == '' || $scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.companyAreaFileId == undefined) {
            //                 JS.alert('请上传公司面积附件', 'error', '确定');
            //                 return true;
            //             }
            //             //校验具体经营状况及现场照片-经营状况-办公场所 是否为空
            //             if ($scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.officeSpace == '' || $scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.officeSpace == undefined) {
            //                 JS.alert('请填写办公场所内容', 'error', '确定');
            //                 return true;
            //             }
            //             //校验具体经营状况及现场照片-经营状况-办公场所附件 是否为空
            //             if ($scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.officeSpaceFileId == '' || $scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.officeSpaceFileId == undefined) {
            //                 JS.alert('请上传办公场所附件', 'error', '确定');
            //                 return true;
            //             }
            //             //校验具体经营状况及现场照片-经营状况-员工概况 是否为空
            //             if ($scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.employeeProfile == '' || $scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.employeeProfile == undefined) {
            //                 JS.alert('请填写员工概况内容', 'error', '确定');
            //                 return true;
            //             }
            //             //校验具体经营状况及现场照片-经营状况-员工概况附件 是否为空
            //             if ($scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.employeeProfileFileId == '' || $scope.itAddressInfoBghDataTable[i].itOperationalInfoParams.employeeProfileFileId == undefined) {
            //                 JS.alert('请上传员工概况附件', 'error', '确定');
            //                 return true;
            //             }
            //
            //             //产能信息
            //             if ($scope.supplierBghProgram.supplierType == '90' && $scope.itAddressInfoBghDataTable[i].itCapacityInfoParams.length == 0) {
            //                 JS.alert('请添加产能信息', 'error', '确定');
            //                 return true;
            //             } else {
            //                 for (var j = 0; j < $scope.itAddressInfoBghDataTable[i].itCapacityInfoParams.length; j++) {
            //                     if ($scope.itAddressInfoBghDataTable[i].itCapacityInfoParams[j].productScope == '' || $scope.itAddressInfoBghDataTable[i].itCapacityInfoParams[j].productScope == undefined) {
            //                         JS.alert('产能信息填写不完整', 'error', '确定');
            //                         return true;
            //                     }
            //                     if ($scope.itAddressInfoBghDataTable[i].itCapacityInfoParams[j].mainRawMaterials == '' || $scope.itAddressInfoBghDataTable[i].itCapacityInfoParams[j].mainRawMaterials == undefined) {
            //                         JS.alert('产能信息填写不完整', 'error', '确定');
            //                         return true;
            //                     }
            //                     if ($scope.itAddressInfoBghDataTable[i].itCapacityInfoParams[j].productionEquipment == '' || $scope.itAddressInfoBghDataTable[i].itCapacityInfoParams[j].productionEquipment == undefined) {
            //                         JS.alert('产能信息填写不完整', 'error', '确定');
            //                         return true;
            //                     }
            //                     if ($scope.itAddressInfoBghDataTable[i].itCapacityInfoParams[j].productionCapacity == '' || $scope.itAddressInfoBghDataTable[i].itCapacityInfoParams[j].productionCapacity == undefined) {
            //                         JS.alert('产能信息填写不完整', 'error', '确定');
            //                         return true;
            //                     }
            //                     if ($scope.itAddressInfoBghDataTable[i].itCapacityInfoParams[j].productionCapacityFileId == '' || $scope.itAddressInfoBghDataTable[i].itCapacityInfoParams[j].productionCapacityFileId == undefined) {
            //                         JS.alert('产能信息填写不完整', 'error', '确定');
            //                         return true;
            //                     }
            //                 }
            //             }
            //         }
            //     }
            // }
        }

        /********************保存供应商档案变更单据信息********************/
        $scope.btnSave = function () {
            $scope.bgqParams.supplierBaseInfo = $scope.supplierProgram; //供应商基础信息
            $scope.bgqParams.relatedFactoryInfo = $scope.bgqRelatedFactoryDataTable;//关联工厂
            $scope.bgqParams.supplierContactInfo = $scope.supplierContactDataTable; //联系人
            $scope.bgqParams.supplierCredentialsInfo = $scope.supplierCredentialsProgram; //资质信息
            $scope.bgqParams.supplierBankInfo = $scope.supplierBankInfoDataTable; //银行信息
            $scope.bgqParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
            if($scope.program.deptCode == '0E'){
                $scope.bgqParams.copCategoryInfo = $scope.supplierCopCategoryDataTable; //合作品类
                // $scope.bgqParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
                // $scope.bgqParams.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
                $scope.bgqParams.oemAddressInfo = $scope.oemAddressInfoDataTable; //地址信息OEM
            }//OEM贸易商
            if ($scope.program.deptCode == '0E' && $scope.supplierProgram.supplierType == '10') {
                $scope.bgqParams.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
            }
            //OEM制造工厂
            if ($scope.program.deptCode == '0E' && $scope.supplierProgram.supplierType == '20') {
                $scope.bgqParams.copCategoryInfo = $scope.supplierCopCategoryDataTable; //合作品类
                $scope.productQualificationTable[0] = $scope.productQualificationTable1;
                $scope.productQualificationTable[1] = $scope.productQualificationTable2;
                $scope.productQualificationTable[2] = $scope.productQualificationTable3;
                $scope.productQualificationTable[3] = $scope.productQualificationTable4;
                $scope.productQualificationTable[4] = $scope.productQualificationTable5;
                var count = 4;
                for (var i = 0; i < $scope.productionPermitDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.productionPermitDataTable[i];
                }
                for (var i = 0; i < $scope.qualitySafetyDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.qualitySafetyDataTable[i];
                }
                for (var i = 0; i < $scope.csrReportDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.csrReportDataTable[i];
                }
                for (var i = 0; i < $scope.fireAcceptanceDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.fireAcceptanceDataTable[i];
                }
                for (var i = 0; i < $scope.projectAcceptanceDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.projectAcceptanceDataTable[i];
                }
                for (var i = 0; i < $scope.otherDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.otherDataTable[i];
                }
                $scope.bgqParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
                $scope.bgqParams.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
            }
            if($scope.program.deptCode == 'IT'){
                debugger;
                $scope.bgqParams.serviceScopeInfo = $scope.supplierServiceScopeDataTable; //服务范围
                // $scope.bgqParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
                $scope.bgqParams.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
                $scope.bgqParams.itAddressInfo = $scope.itAddressInfoDataTable; //地址信息IT
            }

//变更后
            $scope.bghParams.supplierBaseInfo = $scope.supplierBghProgram; //供应商基础信息
            $scope.bghParams.relatedFactoryInfo = $scope.bghRelatedFactoryDataTable;//关联工厂
            $scope.bghParams.supplierContactInfo = $scope.supplierBghContactDataTable; //联系人
            $scope.bghParams.supplierCredentialsInfo = $scope.supplierCredentialsBghProgram; //资质信息
            $scope.bghParams.supplierBankInfo = $scope.supplierBghBankInfoDataTable; //银行信息
            // $scope.bghParams.productQualificationFileInfo = $scope.bghProductQualificationTable; //生产资质文件
            if($scope.program.deptCode == '0E'){
                $scope.bghParams.copCategoryInfo = $scope.supplierBghCopCategoryDataTable; //合作品类
                // $scope.bgqParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
                // $scope.bgqParams.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
                $scope.bghParams.oemAddressInfo = $scope.oemAddressInfoBghDataTable; //地址信息OEM
            }//OEM贸易商
            if ($scope.program.deptCode == '0E' && $scope.supplierBghProgram.supplierType == '10') {
                $scope.bghParams.qualificationsFileInfo = $scope.bghQualificationsFileDataTable; //资质文件
            }
            //OEM制造工厂
            if ($scope.program.deptCode == '0E' && $scope.supplierBghProgram.supplierType == '20') {
                $scope.bghParams.copCategoryInfo = $scope.supplierBghCopCategoryDataTable; //合作品类
                $scope.productQualificationTable[0] = $scope.bghProductQualificationTable1;
                $scope.productQualificationTable[1] = $scope.bghProductQualificationTable2;
                $scope.productQualificationTable[2] = $scope.bghProductQualificationTable3;
                $scope.productQualificationTable[3] = $scope.bghProductQualificationTable4;
                $scope.productQualificationTable[4] = $scope.bghProductQualificationTable5;
                var count = 4;
                for (var i = 0; i < $scope.bghProductionPermitDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.bghProductionPermitDataTable[i];
                }
                for (var i = 0; i < $scope.bghQualitySafetyDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.bghQualitySafetyDataTable[i];
                }
                for (var i = 0; i < $scope.bghCsrReportDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.bghCsrReportDataTable[i];
                }
                for (var i = 0; i < $scope.bghFireAcceptanceDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.bghFireAcceptanceDataTable[i];
                }
                for (var i = 0; i < $scope.bghProjectAcceptanceDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.bghProjectAcceptanceDataTable[i];
                }
                for (var i = 0; i < $scope.bghOtherDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.bghOtherDataTable[i];
                }
                // $scope.bghParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
                $scope.bghParams.productQualificationFileInfo = $scope.bghProductQualificationTable; //生产资质文件
                $scope.bghParams.qualificationsFileInfo = $scope.bghQualificationsFileDataTable; //资质文件
            }
            if($scope.program.deptCode == 'IT'){
                $scope.bghParams.serviceScopeInfo = $scope.supplierBghServiceScopeDataTable; //服务范围
                // $scope.bgqParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
                $scope.bghParams.qualificationsFileInfo = $scope.bghQualificationsFileDataTable; //资质文件
                $scope.bghParams.itAddressInfo = $scope.itAddressInfoBghDataTable; //地址信息IT
            }

            // $scope.bghParams.supplierBaseInfo = $scope.supplierBghProgram; //供应商基础信息
            // $scope.bghParams.relatedFactoryInfo = $scope.relatedFactoryDataTable;//关联工厂
            // $scope.bghParams.supplierContactInfo = $scope.supplierBghContactDataTable; //联系人
            // $scope.bghParams.supplierCredentialsInfo = $scope.supplierCredentialsBghProgram; //资质信息
            // $scope.bghParams.supplierBankInfo = $scope.supplierBghBankInfoDataTable; //银行信息
            // $scope.bghParams.productQualificationFileInfo = $scope.bghProductQualificationTable; //生产资质文件
            // $scope.bghParams.qualificationsFileInfo = $scope.bghQualificationsFileDataTable; //资质文件
            //
            // if($scope.program.deptCode == 'OEM'){
            //     $scope.bghParams.copCategoryInfo = $scope.supplierBghCopCategoryDataTable; //合作品类
            //     // $scope.bgqParams.productQualificationFileInfo = $scope.bghProductQualificationTable; //生产资质文件
            //     // $scope.bgqParams.qualificationsFileInfo = $scope.bghQualificationsFileDataTable; //资质文件
            //     $scope.bghParams.oemAddressInfo = $scope.oemAddressInfoBghDataTable; //地址信息OEM
            // }else if($scope.program.deptCode == 'IT'){
            //     $scope.bghParams.serviceScopeInfo = $scope.supplierBghServiceScopeDataTable; //服务范围
            //     // $scope.bgqParams.productQualificationFileInfo = $scope.bghProductQualificationTable; //生产资质文件
            //     // $scope.bgqParams.qualificationsFileInfo = $scope.bghQualificationsFileDataTable; //资质文件
            //     $scope.bghParams.itAddressInfo = $scope.itAddressInfoBghDataTable; //地址信息IT
            // }

            $scope.params.archivesChangeInfo = $scope.program;
            if($scope.program.changeId == '' || $scope.program.changeId == undefined){
                $scope.params.bgqParams = $scope.bgqParams;
                $scope.params.bghParams = $scope.bghParams;
            }else{
                $scope.params.bghParams = $scope.bghParams;
            }

            httpServer.post(URLAPI.saveArchivesChangeOrder, {
                'params': JSON.stringify($scope.params)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.program = res.data;
                    $scope.search($scope.program.changeId);
                    if($scope.program.status != '20'){
                        JS.alert('保存成功');
                    }
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('保存失败', 'error', '确定');
            })
        }


        //选择供应商
        $scope.selectSupplierInfo = function () {
            // var deptCode = '';
            // if($scope.program.deptCode == 'IT'){
            //     deptCode = "OEM";
            // }else if($scope.program.deptCode == 'OEM'){
            //     deptCode = "IT";
            // }
            $scope.selectSupplierParams = {
                "deptCode" : $scope.program.deptCode
            };
            $('#selectSupplierLov').modal('toggle')
        }

        //选择供应商-回调
        $scope.selectSupplierReturn = function (key, value, currentList) {
            $scope.program.supplierName = currentList[0].supplierName;
            $scope.program.supplierNumber = currentList[0].supplierNumber;
            $scope.program.supplierStatusMeaning = currentList[0].supplierStatusMeaning;
            $scope.program.supplierId = currentList[0].supplierId;
            $scope.supplierInfoSearch();
        }

        //新增可合作品类
        $scope.addCopCategoryInfo = function () {
            var copCategoryArray = {
                supplierId: "",
                categoryId: "",
                status: "APPROVING",
                statusMeaning: "准入中",
                deptCode: $scope.program.deptCode
            };
            $scope.supplierBghCopCategoryDataTable.push(copCategoryArray);
        };

        //新增服务范围
        $scope.addServiceScopeInfo = function () {
            var serviceScopeArray = {
                supplierId: "",
                categoryId: "",
                status: "APPROVING",
                statusMeaning: "准入中",
                deptCode: $scope.program.deptCode
            };
            $scope.supplierBghServiceScopeDataTable.push(serviceScopeArray);
        };

        //新增供应商联系人目录行
        $scope.addContactInfo = function () {
            var contactDetailArray = {
                contactName: "",
                mobilePhone: "",
                emailAddress: "",
                fixedPhone: "",
                positionName: "",
                respCategory: "",
                remark: "",
                deptCode: $scope.program.deptCode,
                sendEmailFlag: "N"
            };
            $scope.supplierBghContactDataTable.push(contactDetailArray);
        };

        //生产资质信息-新增其他资质文件
        $scope.addProductQualifications = function () {
            var qualificationsFileArray = {
                fileName: "",
                filePath: "",
                deptmentCode: $scope.program.deptCode,
                description: "",
                fileId: "",
                supplierId: "",
                invalidDate:"",
                attachmentName:"",
                isProductFactory:"Y",
                index:$scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.bghProductQualificationTable.push(qualificationsFileArray);
        }

        //生产资质信息-根据当前资质文件新增资质文件
        $scope.addProductQualificationsByRow = function (row) {
            var qualificationsFileArray = {
                fileName: "",
                filePath: "",
                deptmentCode: $scope.program.deptCode,
                description: row.description,
                fileId: "",
                supplierId: "",
                invalidDate:row.invalidDate,
                attachmentName:row.attachmentName,
                isProductFactory:"Y",
                index:$scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.bghProductQualificationTable.push(qualificationsFileArray);
        }

        //生产资质信息-资质文件说明
        $scope.showQualificationsDescription = function () {
            iframeTabAction("资质文件说明", "qualificationDescription");
        }

        //资质文件
        $scope.addqualificationsFile = function () {
            var qualificationsFileArray = {
                fileName: "",
                filePath: "",
                deptmentCode: $scope.program.deptCode,
                description: "",
                fileId: "",
                supplierId: "",
                invalidDate:"",
                attachmentName:"",
                isProductFactory:"N",
                index:$scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.bghQualificationsFileDataTable.push(qualificationsFileArray);
        }

        //银行信息
        $scope.addBankInfo = function () {
            var bankDetailArray = {
                bankUserName: "",
                bankAccountNumber: "",
                bankName: "",
                bankCurrency: "",
                swiftCode: "",
                ibanCode: ""
            };
            $scope.supplierBghBankInfoDataTable.push(bankDetailArray);
        };

        //地址信息(OEM)
        $scope.addOEMAddressInfo = function () {
            var addressArray = {
                addressName: "",
                country: "",
                province: "",
                city: "",
                county: "",
                detailAddress: "",
                deptCode:$scope.program.deptCode,
                oemProductionInfoParams:{},
                oemCapacityInfoDataTable:[]
            };
            $scope.oemAddressInfoBghDataTable.push(addressArray);
            $scope.updateDate();
            $scope.updateOemCapacityInfo();
        };

        //更新生产信息
        $scope.updateDate = function(){
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            row.oemProductionInfoBghParams = $scope.oemProductionInfoBghParams;

            // if($scope.oemAddressInfoBghDataTable.length == 0){
            //     JS.alert('请先维护地址行信息！', 'error', '确定');
            //     $scope.oemProductionInfoBghParams = {};
            //     return false;
            // }
            // var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            // if (row.oemProductionInfoBghParams == undefined) {
            //     JS.alert('请先选中地址行信息！', 'error', '确定');
            //     $scope.oemProductionInfoBghParams = {};
            //     return false;
            // }
            // row.oemProductionInfoBghParams = $scope.oemProductionInfoBghParams;
        }

        //更新产能信息
        $scope.updateOemCapacityInfo = function(){
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            row.oemCapacityInfoBghDataTable = $scope.oemCapacityInfoBghDataTable;

        }

        //选中地址行时，加载关联行数据
        // $scope.loadLinesData = function(row,$index){
        //     $scope.oemProductionInfoBghParams = row.oemProductionInfoBghParams;
        //     $scope.oemCapacityInfoBghDataTable = row.oemCapacityInfoBghDataTable;
        // }

        //生产信息(OEM)
        // $scope.addOEMProductionInfo = function () {
        //     var productionArray = {
        //         contactName: "",
        //         mobilePhone: "",
        //         emailAddress: "",
        //         fixedPhone: "",
        //         positionName: "",
        //         respCategory: "",
        //         remark: "",
        //         sendEmailFlag: "N"
        //     };
        //     $scope.oemProductionInfoDataTable.push(productionArray);
        // };

        //产能信息(OEM)
        $scope.addOEMCapacityInfo = function () {
            var CapacityArray = {
                productScope: "",
                mainRawMaterials: "",
                productionEquipment: "",
                productionCapacity: "",
                productionCapacityFileId:"",
                productionCapacityFilePath:"",
                productionCapacityFileName:"",
                remark: "",
                index:$scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.oemCapacityInfoBghDataTable.push(CapacityArray);
        };

        //地址信息(IT)
        $scope.addITAddressInfo = function () {
            var addressArray = {
                addressName: "",
                country: "",
                province: "",
                city: "",
                county: "",
                detailAddress: "",
                deptCode:$scope.program.deptCode,
                itOperationalInfoParams:{},
                itCapacityInfoDataTable:[],
                surEnvironmentDataTable:[],
                doorPlateDataTable:[],
                receptionDataTable:[],
                companyAreaDataTable:[],
                officeSpaceDataTable:[],
                employeeProfileDataTable:[],
            };
            $scope.itAddressInfoBghDataTable.push(addressArray);
            $scope.updateITDate();
            $scope.updateITCapacityInfo();
        };

        //更新经营状况
        $scope.updateITDate = function(){
            var row = $scope.itAddressInfoBghDataTable[$scope.itAddressInfoBghDataTable.selectRow];
            row.itOperationalInfoBghParams = $scope.itOperationalInfoBghParams;
        }

        //更新产能信息
        $scope.updateITCapacityInfo = function(){
            var row = $scope.itAddressInfoBghDataTable[$scope.itAddressInfoBghDataTable.selectRow];
            row.itCapacityInfoDataTable = $scope.itCapacityInfoDataTable;

        }

        //选中地址行时，加载关联行数据
        $scope.loadITLinesData = function(row,$index){
            $scope.itOperationalInfoParams = row.itOperationalInfoParams;
            $scope.itCapacityInfoDataTable = row.itCapacityInfoDataTable;
            $scope.surEnvironmentDataTable = row.surEnvironmentDataTable;
            $scope.doorPlateDataTable = row.doorPlateDataTable;
            $scope.receptionDataTable = row.receptionDataTable;
            $scope.companyAreaDataTable = row.companyAreaDataTable;
            $scope.officeSpaceDataTable = row.officeSpaceDataTable;
            $scope.employeeProfileDataTable = row.employeeProfileDataTable;
        }

        //产能信息(IT)
        $scope.addITCapacityInfo = function () {
            var CapacityArray = {
                productScope: "",
                mainRawMaterials: "",
                productionEquipment: "",
                productionCapacity: "",
                remark: "",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.itCapacityInfoDataTable.push(CapacityArray);
        };

        //删除关联工厂
        $scope.deleteAssociatedSupplier = function (row, index) {
            JS.confirm('删除', '确定删除？', '确定', function () {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.bghRelatedFactoryDataTable.splice(index, 1);             //新增列没有ID直接删除
                    row.supplierBghName = '';
                    // JS.alert("操作成功!", "已成功删除数据！", "确定");
                    JS.alert("删除成功!", "success", "确定");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteBgAssociatedSupplier, {
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.bghRelatedFactoryDataTable.splice(index, 1);             //新增列没有ID直接删除
                            row.supplierBghName = '';
                            row.associatedSupplierBghId = '';
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除合作类别
        $scope.deleteCategory = function(row,index){
            JS.confirm('删除','确定删除该品类？','确定',function() {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.supplierBghCopCategoryDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteBghSupplierCategorysInfo,{
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.supplierBghCopCategoryDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除联系人
        $scope.deleteContact = function(row,index){
            JS.confirm('删除','确定删除联系人信息？','确定',function() {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.supplierBghContactDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteBgSupplierContactsInfo,{
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.supplierBghContactDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除生产资质信息
        $scope.deleteProductQualifications = function (row,index) {
            JS.confirm('删除','确定删除生产资质信息？','确定',function() {
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.bghProductQualificationTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo,{
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.bghProductQualificationTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除资质文件
        $scope.deleteQualificationsFile = function (row,index) {
            JS.confirm('删除','确定删除资质文件？','确定',function() {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.bghQualificationsFileDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo,{
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.bghQualificationsFileDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除银行信息
        $scope.deleteBankInfo = function(row,index){
            JS.confirm('删除','确定删除银行信息？','确定',function() {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.supplierBghBankInfoDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteBgBankInfo,{
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.supplierBghBankInfoDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除OEM地址行
        $scope.deleteOemAddressInfo = function(row,index){
            JS.confirm('删除','确定删除地址信息？','确定',function() {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.oemAddressInfoDataTable.splice(index, 1);             //新增列没有ID直接删除
                    $scope.oemProductionInfoParams = {};
                    $scope.oemCapacityInfoDataTable = [];
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteSupplierAddressInfo,{
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.oemAddressInfoDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除OEM产能信息行
        $scope.deleteOemCapacityInfo = function(row,index){
            JS.confirm('删除','确定删除产能信息？','确定',function() {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.oemCapacityInfoDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCapacityInfo,{
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.oemCapacityInfoDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除IT地址行
        $scope.deleteItAddressInfo = function(row,index){
            JS.confirm('删除','确定删除地址信息？','确定',function() {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.itAddressInfoDataTable.splice(index, 1);             //新增列没有ID直接删除
                    $scope.oemProductionInfoParams = {};
                    $scope.itCapacityInfoDataTable = [];
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteSupplierAddressInfo,{
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.itAddressInfoDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除IT地址行
        $scope.deleteItCapacityInfo = function(row,index){
            JS.confirm('删除','确定删除产能信息？','确定',function() {
                if (row.capacityId == null || row.capacityId == "") {
                    $scope.itCapacityInfoDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCapacityInfo,{
                        'params': JSON.stringify({
                            "id": row.capacityId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.itCapacityInfoDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //可合作品类行-选择品类
        $scope.getCategory = function (value) {
            angular.forEach($scope.newDataTable.selectRowList,function(obj,rowindex){
                obj.checked=false;
            });
            $scope.selectCategoryParams = {
                "deptCode": $scope.program.deptCode
            };
            $('#goodsSearchId').modal('toggle')
        };

        $scope.findLovCategory = function (value) {
            $scope.catetoryParams.departmentId = $scope.deptId;
            httpServer.post(URLAPI.findSupplierCategoryPagination, {
                params: JSON.stringify($scope.catetoryParams)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.itemlistp = res.data;
                }
            }, function (error) {

            });
        }

        $scope.resetLovCategory = function () {
            $scope.catetoryParams = {};
            $scope.catetoryParams.deptmentId = $scope.program.deptId;
        }

        $scope.cancleLovCategory = function () {
            $('#selectCategoryLov').modal('toggle');
        }

        $scope.confrimLovCategory = function () {
            debugger;
            var rows = $scope.newDataTable.selectRowList;
            if (rows.length == 0) {
                JS.alert('请选择', 'error', '确定');
                return;
            }

            if ($scope.program.deptCode == '0E') {
                var row = $scope.supplierBghCopCategoryDataTable[$scope.supplierBghCopCategoryDataTable.selectRow];
                if (rows.length > 0) {
                    row.categoryId = rows[0].categoryMaintainId;
                    row.categoryName = rows[0].categoryGroup;

                }
                for (var i = 1; i < rows.length; i++) {
                    var copCategoryArray = {
                        supplierId: "",
                        categoryId: rows[i].categoryMaintainId,
                        categoryName: rows[i].categoryGroup,
                        status: "APPROVING",
                        statusMeaning: "准入中",
                        deptCode: $scope.program.deptCode
                    };
                    $scope.supplierBghCopCategoryDataTable.push(copCategoryArray);
                }
            }

            if ($scope.program.deptCode == 'IT') {
                var row = $scope.supplierBghServiceScopeDataTable[$scope.supplierBghServiceScopeDataTable.selectRow];
                if (rows.length > 0) {
                    row.categoryId = rows[0].categoryMaintainId;
                    row.categoryName = rows[0].categoryGroup;

                }
                for (var i = 1; i < rows.length; i++) {
                    var copCategoryArray = {
                        supplierId: "",
                        categoryId: rows[i].categoryMaintainId,
                        categoryName: rows[i].categoryGroup,
                        status: "APPROVING",
                        statusMeaning: "准入中",
                        deptCode: $scope.program.deptCode
                    };
                    $scope.supplierBghServiceScopeDataTable.push(copCategoryArray);
                }
            }
            $('#selectCategoryLov').modal('toggle');
        }

        //可合作品类查询-一级分类查询
        $scope.selectBigCategory = function (value) {
            $scope.selectBigCategoryParams = {
                "departmentId": $scope.program.deptId
            };
            $('#bigCategorySelectLov').modal('toggle')
        }

        //可合作品类查询-一级分类查询-回调
        $scope.selectBigCategoryReturn = function (key, value, currentList) {
            $scope.catetoryParams.categoryCodeFirst = currentList[0].categoryCodeFirst;
            $scope.catetoryParams.categoryLevelFirst = currentList[0].categoryLevelFirst;
        }

        //可合作品类查询-二级分类查询
        $scope.selectMiddleCategory = function (value) {
            $scope.selectMiddleCategoryParams = {
                "departmentId": $scope.program.deptId,
                "categoryCodeFirst": $scope.catetoryParams.categoryCodeFirst
            };
            $('#middleCategorySelectLov').modal('toggle')
        }

        //可合作品类查询-二级分类查询-回调
        $scope.selectMiddleCategoryReturn = function (key, value, currentList) {
            $scope.catetoryParams.categoryCodeSecond = currentList[0].categoryCodeSecond;
            $scope.catetoryParams.categoryLevelSecond = currentList[0].categoryLevelSecond;
        }

        //可合作品类查询-三级分类查询
        $scope.selectSmallCategory = function (value) {
            $scope.selectSmallCategoryParams = {
                "departmentId": $scope.program.deptId,
                "categoryCodeFirst": $scope.catetoryParams.categoryCodeFirst,
                "categoryCodeSecond": $scope.catetoryParams.categoryCodeSecond
            };
            $('#smallCategorySelectLov').modal('toggle')
        }

        //可合作品类查询-三级分类查询-回调
        $scope.selectSmallCategoryReturn = function (key, value, currentList) {
            $scope.catetoryParams.categoryCodeThird = currentList[0].categoryCodeThird;
            $scope.catetoryParams.categoryLevelThird = currentList[0].categoryLevelThird;
        }

        // //可合作品类行-选择品类
        // $scope.getCategory = function (value) {
        //     $scope.selectCategoryParams = {
        //         "deptCode" : $scope.program.deptCode
        //     };
        //     $('#selectCategoryLov').modal('toggle')
        // };
        //
        // //可合作品类行-选择品类-回调
        // $scope.selectCatetoryReturn = function (key, value, currentList) {
        //     var row = $scope.supplierBghCopCategoryDataTable[$scope.supplierBghCopCategoryDataTable.selectRow];
        //     row.categoryId = currentList[0].categoryMaintainId;
        //     row.categoryName = currentList[0].categoryName;
        // }

        //服务范围行-选择服务范围
        $scope.getServiceScope = function (value) {
            $scope.selectServiceScopeParams = {
                "deptCode" : $scope.program.deptCode
            };
            $('#selectServiceScopeLov').modal('toggle')
        };

        //服务范围行-选择服务范围-回调
        $scope.selectServiceScopeReturn = function (key, value, currentList) {
            var row = $scope.supplierBghServiceScopeDataTable[$scope.supplierBghServiceScopeDataTable.selectRow];
            row.categoryId = currentList[0].categoryMaintainId;
            row.categoryName = currentList[0].categoryName;
        }

        //删除服务范围
        $scope.deleteServiceScope = function(row,index){
            JS.confirm('删除','确定删除该品类？','确定',function() {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.supplierBghServiceScopeDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteBghSupplierCategorysInfo,{
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.supplierBghServiceScopeDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //上传供应商档案变更单据附件
        $scope.uploadFile = function () {

            // var f = $scope.myFile;
            var fd = new FormData();
            var file = document.querySelector('#changeFile').files[0];
            // var file = document.getElementById("file").files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.program.changeFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.program.changeFileId = response.data[0].fileId;
                $scope.program.changeFileName = response.data[0].fileName;
                $scope.program.changeFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传供应商档案变更单据附件
        $scope.changeFile = function () {
            $scope.program.changeFileId = null;
            $scope.program.changeFilePath = null;
            $scope.program.changeFileName = null;
        }

        //上传-供应商基础信息-附件
        $scope.uploadFile2 = function () {

            var f = $scope.myFile2;
            var fd = new FormData();
            var file = document.querySelector('#file2').files[0];
            // var file = document.getElementById("file2").files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.supplierBghProgram.supplierFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.supplierBghProgram.supplierFileId = response.data[0].fileId;
                $scope.supplierBghProgram.fileName = response.data[0].fileName;
                $scope.supplierBghProgram.filePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-供应商基础信息-附件
        $scope.changeFile2 = function () {
            $scope.supplierBghProgram.supplierFileId = null;
            $scope.supplierBghProgram.filePath = null;
            $scope.supplierBghProgram.fileName = null;
        }

        //上传-营业执照复印件-附件
        $scope.uploadLicenseFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#licenseFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.supplierCredentialsBghProgram.licenseFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.supplierCredentialsBghProgram.licenseFileId = response.data[0].fileId;
                $scope.supplierCredentialsBghProgram.licenseFileName = response.data[0].fileName;
                $scope.supplierCredentialsBghProgram.licenseFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-营业执照复印件-附件
        $scope.changeLicenseFile = function () {
            $scope.supplierCredentialsBghProgram.licenseFileId = null;
            $scope.supplierCredentialsBghProgram.licenseFilePath = null;
            $scope.supplierCredentialsBghProgram.licenseFileName = null;
        }

        //上传-组织机构代码证-附件
        $scope.uploadTissueFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#tissueFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.supplierCredentialsBghProgram.tissueFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.supplierCredentialsBghProgram.tissueFileId = response.data[0].fileId;
                $scope.supplierCredentialsBghProgram.tissueFileName = response.data[0].fileName;
                $scope.supplierCredentialsBghProgram.tissueFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-组织机构代码证-附件
        $scope.changeTissueFile = function () {
            $scope.supplierCredentialsBghProgram.tissueFileId = null;
            $scope.supplierCredentialsBghProgram.tissueFilePath = null;
            $scope.supplierCredentialsBghProgram.tissueFileName = null;
        }

        //上传-税务登记证-附件
        $scope.uploadTaxFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#taxFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.supplierCredentialsBghProgram.taxFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.supplierCredentialsBghProgram.taxFileId = response.data[0].fileId;
                $scope.supplierCredentialsBghProgram.taxFileName = response.data[0].fileName;
                $scope.supplierCredentialsBghProgram.taxFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-税务登记证-附件
        $scope.changeTaxFile = function () {
            $scope.supplierCredentialsBghProgram.taxFileId = null;
            $scope.supplierCredentialsBghProgram.taxFilePath = null;
            $scope.supplierCredentialsBghProgram.taxFileName = null;
        }

        //上传-银行开户许可证-附件
        $scope.uploadBankFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#bankFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.supplierCredentialsBghProgram.bankFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.supplierCredentialsBghProgram.bankFileId = response.data[0].fileId;
                $scope.supplierCredentialsBghProgram.bankFileName = response.data[0].fileName;
                $scope.supplierCredentialsBghProgram.bankFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-银行开户许可证-附件
        $scope.changeBankFile = function () {
            $scope.supplierCredentialsBghProgram.bankFileId = null;
            $scope.supplierCredentialsBghProgram.bankFilePath = null;
            $scope.supplierCredentialsBghProgram.bankFileName = null;
        }

        //上传-一般纳税人证明-附件
        $scope.uploadTaxpayerFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#taxpayerFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.supplierCredentialsBghProgram.taxpayerFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.supplierCredentialsBghProgram.taxpayerFileId = response.data[0].fileId;
                $scope.supplierCredentialsBghProgram.taxpayerFileName = response.data[0].fileName;
                $scope.supplierCredentialsBghProgram.taxpayerFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-一般纳税人证明-附件
        $scope.changeTaxpayerFile = function () {
            $scope.supplierCredentialsBghProgram.taxpayerFileId = null;
            $scope.supplierCredentialsBghProgram.taxpayerFilePath = null;
            $scope.supplierCredentialsBghProgram.taxpayerFileName = null;
        }

        //上传-行业内类似项目附件-附件
        $scope.uploadProjectExperienceFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#projectExperienceFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.supplierCredentialsBghProgram.projectExperienceFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.supplierCredentialsBghProgram.projectExperienceFileId = response.data[0].fileId;
                $scope.supplierCredentialsBghProgram.projectExperienceFileName = response.data[0].fileName;
                $scope.supplierCredentialsBghProgram.projectExperienceFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-行业内类似项目附件-附件
        $scope.changeProjectExperienceFile = function () {
            $scope.supplierCredentialsBghProgram.projectExperienceFileId = null;
            $scope.supplierCredentialsBghProgram.projectExperienceFilePath = null;
            $scope.supplierCredentialsBghProgram.projectExperienceFileName = null;
        }

        //上传-相关附件-附件
        $scope.uploadRelatedFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#relatedFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.supplierCredentialsBghProgram.relatedFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.supplierCredentialsBghProgram.relatedFileId = response.data[0].fileId;
                $scope.supplierCredentialsBghProgram.relatedFileName = response.data[0].fileName;
                $scope.supplierCredentialsBghProgram.relatedFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-相关附件-附件
        $scope.changeRelatedFile = function () {
            $scope.supplierCredentialsBghProgram.relatedFileId = null;
            $scope.supplierCredentialsBghProgram.relatedFilePath = null;
            $scope.supplierCredentialsBghProgram.relatedFileName = null;
        }

        //上传-OEM生产能力-附件
        $scope.uploadOEMProductionCapacityFile = function () {
            var row = $scope.oemCapacityInfoBghDataTable[$scope.oemCapacityInfoBghDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#oemProductionCapacityFile' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.productionCapacityFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.productionCapacityFileId = response.data[0].fileId;
                row.productionCapacityFileName = response.data[0].fileName;
                row.productionCapacityFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-OEM生产能力-附件
        $scope.changeOEMProductionCapacityFile = function (row) {
            row.productionCapacityFileId = null;
            row.productionCapacityFilePath = null;
            row.productionCapacityFileName = null;
        }

        //上传-IT生产能力-附件
        $scope.uploadITProductionCapacityFile = function () {
            var row = $scope.itCapacityInfoBghDataTable[$scope.itCapacityInfoBghDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#itProductionCapacityFile' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.productionCapacityFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.productionCapacityFileId = response.data[0].fileId;
                row.productionCapacityFileName = response.data[0].fileName;
                row.productionCapacityFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-IT生产能力-附件
        $scope.changeITProductionCapacityFile = function (row) {
            row.productionCapacityFileId = null;
            row.productionCapacityFilePath = null;
            row.productionCapacityFileName = null;
        }

        //上传-生产资质文件-附件
        $scope.uploadProductQualificationFile = function () {
            var row = $scope.bghProductQualificationTable[$scope.bghProductQualificationTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#productQualification' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.fileId = response.data[0].fileId;
                row.fileName = response.data[0].fileName;
                row.filePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-生产资质文件-附件
        $scope.changeProductQualificationFile = function (row) {
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        }

        //上传-资质文件-附件
        $scope.uploadQualificationsFile = function () {
            var row = $scope.bghQualificationsFileDataTable[$scope.bghQualificationsFileDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#qualificationsFile' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.fileId = response.data[0].fileId;
                row.fileName = response.data[0].fileName;
                row.filePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-资质文件-附件
        $scope.changeQualificationsFile = function (row) {
            // var row = $scope.qualificationsFileDataTable[$scope.qualificationsFileDataTable.selectRow];
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        }

        //上传-经营状况-周边环境-附件
        $scope.uploadSurEnvironmentFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#surEnvironmentFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.itOperationalInfoBghParams.surEnvironmentFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.itOperationalInfoBghParams.surEnvironmentFileId = response.data[0].fileId;
                $scope.itOperationalInfoBghParams.surEnvironmentFileName = response.data[0].fileName;
                $scope.itOperationalInfoBghParams.surEnvironmentFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-周边环境-附件
        $scope.changeSurEnvironmentFile = function () {
            $scope.itOperationalInfoBghParams.surEnvironmentFileId = null;
            $scope.itOperationalInfoBghParams.surEnvironmentFilePath = null;
            $scope.itOperationalInfoBghParams.surEnvironmentFileName = null;
        }

        //上传-经营状况-周边环境-增加附件
        $scope.uploadOtherSurEnvironmentFile = function () {
            var row = $scope.surEnvironmentBghDataTable[$scope.surEnvironmentBghDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#surEnvironmentFile' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.fileId = response.data[0].fileId;
                row.fileName = response.data[0].fileName;
                row.filePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-周边环境-增加附件
        $scope.changeOtherSurEnvironmentFile = function (row) {
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        }

        //上传-经营状况-大门门牌-附件
        $scope.uploadDoorPlateFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#doorPlateFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.itOperationalInfoBghParams.doorPlateFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.itOperationalInfoBghParams.doorPlateFileId = response.data[0].fileId;
                $scope.itOperationalInfoBghParams.doorPlateFileName = response.data[0].fileName;
                $scope.itOperationalInfoBghParams.doorPlateFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-大门门牌-附件
        $scope.changeDoorPlateFile = function () {
            $scope.itOperationalInfoBghParams.doorPlateFileId = null;
            $scope.itOperationalInfoBghParams.doorPlateFilePath = null;
            $scope.itOperationalInfoBghParams.doorPlateFileName = null;
        }

        //上传-经营状况-大门门牌-增加附件
        $scope.uploadOtherDoorPlateFile = function () {
            var row = $scope.doorPlateBghDataTable[$scope.doorPlateBghDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#doorPlateFile' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.fileId = response.data[0].fileId;
                row.fileName = response.data[0].fileName;
                row.filePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-大门门牌-增加附件
        $scope.changeOtherDoorPlateFile = function (row) {
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        }

        //上传-经营状况-前台-附件
        $scope.uploadReceptionFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#receptionFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.itOperationalInfoBghParams.receptionFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.itOperationalInfoBghParams.receptionFileId = response.data[0].fileId;
                $scope.itOperationalInfoBghParams.receptionFileName = response.data[0].fileName;
                $scope.itOperationalInfoBghParams.receptionFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-前台-附件
        $scope.changeReceptionFile = function () {
            $scope.itOperationalInfoBghParams.receptionFileId = null;
            $scope.itOperationalInfoBghParams.receptionFilePath = null;
            $scope.itOperationalInfoBghParams.receptionFileName = null;
        }

        //上传-经营状况-前台-增加附件
        $scope.uploadOtherReceptionFile = function () {
            var row = $scope.receptionBghDataTable[$scope.receptionBghDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#receptionFile' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.fileId = response.data[0].fileId;
                row.fileName = response.data[0].fileName;
                row.filePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-前台-增加附件
        $scope.changeOtherReceptionFile = function (row) {
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        }

        //上传-经营状况-公司面积-附件
        $scope.uploadCompanyAreaFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#companyAreaFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.itOperationalInfoBghParams.companyAreaFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.itOperationalInfoBghParams.companyAreaFileId = response.data[0].fileId;
                $scope.itOperationalInfoBghParams.companyAreaFileName = response.data[0].fileName;
                $scope.itOperationalInfoBghParams.companyAreaFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-公司面积-附件
        $scope.changeCompanyAreaFile = function () {
            $scope.itOperationalInfoBghParams.companyAreaFileId = null;
            $scope.itOperationalInfoBghParams.companyAreaFilePath = null;
            $scope.itOperationalInfoBghParams.companyAreaFileName = null;
        }

        //上传-经营状况-公司面积-增加附件
        $scope.uploadOtherCompanyAreaFile = function () {
            var row = $scope.companyAreaBghDataTable[$scope.companyAreaBghDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#companyAreaFile' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.fileId = response.data[0].fileId;
                row.fileName = response.data[0].fileName;
                row.filePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-公司面积-增加附件
        $scope.changeOtherCompanyAreaFile = function (row) {
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        }

        //上传-经营状况-办公场所-附件
        $scope.uploadOfficeSpaceFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#officeSpaceFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.itOperationalInfoBghParams.officeSpaceFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.itOperationalInfoBghParams.officeSpaceFileId = response.data[0].fileId;
                $scope.itOperationalInfoBghParams.officeSpaceFileName = response.data[0].fileName;
                $scope.itOperationalInfoBghParams.officeSpaceFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-办公场所-附件
        $scope.changeOfficeSpaceFile = function () {
            $scope.itOperationalInfoBghParams.officeSpaceFileId = null;
            $scope.itOperationalInfoBghParams.officeSpaceFilePath = null;
            $scope.itOperationalInfoBghParams.officeSpaceFileName = null;
        }

        //上传-经营状况-办公场所-增加附件
        $scope.uploadOtherOfficeSpaceFile = function () {
            var row = $scope.officeSpaceBghDataTable[$scope.officeSpaceBghDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#officeSpaceFile' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.fileId = response.data[0].fileId;
                row.fileName = response.data[0].fileName;
                row.filePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-办公场所-增加附件
        $scope.changeOtherOfficeSpaceFile = function (row) {
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        }

        //上传-经营状况-员工概况-附件
        $scope.uploadEmployeeProfileFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#employeeProfileFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.itOperationalInfoBghParams.employeeProfileFileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.itOperationalInfoBghParams.employeeProfileFileId = response.data[0].fileId;
                $scope.itOperationalInfoBghParams.employeeProfileFileName = response.data[0].fileName;
                $scope.itOperationalInfoBghParams.employeeProfileFilePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-员工概况-附件
        $scope.changeEmployeeProfileFile = function () {
            $scope.itOperationalInfoBghParams.employeeProfileFileId = null;
            $scope.itOperationalInfoBghParams.employeeProfileFilePath = null;
            $scope.itOperationalInfoBghParams.employeeProfileFileName = null;
        }

        //上传-经营状况-员工概况-增加附件
        $scope.uploadOtherEmployeeProfile = function () {
            var row = $scope.employeeProfileBghDataTable[$scope.employeeProfileBghDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#employeeProfile' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.fileId = response.data[0].fileId;
                row.fileName = response.data[0].fileName;
                row.filePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });
        }

        //重新上传-经营状况-员工概况-增加附件
        $scope.changeOtherEmployeeProfile = function (row) {
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        }

        //上传-生产资质文件-生产许可证-附件
        $scope.uploadProductQualificationFile1 = function () {
            var fd = new FormData();
            var file = document.querySelector('#productQualification1').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.bghProductQualificationTable1.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.bghProductQualificationTable1.fileId = response.data[0].fileId;
                $scope.bghProductQualificationTable1.fileName = response.data[0].fileName;
                $scope.bghProductQualificationTable1.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-生产资质文件-生产许可证-附件
        $scope.changeProductQualificationFile1 = function () {
            $scope.bghProductQualificationTable1.fileId = null;
            $scope.bghProductQualificationTable1.filePath = null;
            $scope.bghProductQualificationTable1.fileName = null;
        }

        //上传-生产资质文件-质量安全管理体系认证证明-附件
        $scope.uploadProductQualificationFile2 = function () {
            var fd = new FormData();
            var file = document.querySelector('#productQualification2').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.bghProductQualificationTable2.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.bghProductQualificationTable2.fileId = response.data[0].fileId;
                $scope.bghProductQualificationTable2.fileName = response.data[0].fileName;
                $scope.bghProductQualificationTable2.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-生产资质文件-质量安全管理体系认证证明-附件
        $scope.changeProductQualificationFile2 = function () {
            $scope.bghProductQualificationTable2.fileId = null;
            $scope.bghProductQualificationTable2.filePath = null;
            $scope.bghProductQualificationTable2.fileName = null;
        }

        //上传-生产资质文件-CSR报告-附件
        $scope.uploadProductQualificationFile3 = function () {
            var fd = new FormData();
            var file = document.querySelector('#productQualification3').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.bghProductQualificationTable3.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.bghProductQualificationTable3.fileId = response.data[0].fileId;
                $scope.bghProductQualificationTable3.fileName = response.data[0].fileName;
                $scope.bghProductQualificationTable3.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-生产资质文件-CSR报告-附件
        $scope.changeProductQualificationFile3 = function () {
            $scope.bghProductQualificationTable3.fileId = null;
            $scope.bghProductQualificationTable3.filePath = null;
            $scope.bghProductQualificationTable3.fileName = null;
        }

        //上传-生产资质文件-消防验收证明-附件
        $scope.uploadProductQualificationFile4 = function () {
            var fd = new FormData();
            var file = document.querySelector('#productQualification4').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.bghProductQualificationTable4.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.bghProductQualificationTable4.fileId = response.data[0].fileId;
                $scope.bghProductQualificationTable4.fileName = response.data[0].fileName;
                $scope.bghProductQualificationTable4.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-生产资质文件-消防验收证明-附件
        $scope.changeProductQualificationFile4 = function () {
            $scope.bghProductQualificationTable4.fileId = null;
            $scope.bghProductQualificationTable4.filePath = null;
            $scope.bghProductQualificationTable4.fileName = null;
        }

        //上传-生产资质文件-建筑工程竣工验收报告-附件
        $scope.uploadProductQualificationFile5 = function () {
            var fd = new FormData();
            var file = document.querySelector('#productQualification5').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.bghProductQualificationTable5.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.bghProductQualificationTable5.fileId = response.data[0].fileId;
                $scope.bghProductQualificationTable5.fileName = response.data[0].fileName;
                $scope.bghProductQualificationTable5.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-生产资质文件-建筑工程竣工验收报告-附件
        $scope.changeProductQualificationFile5 = function () {
            $scope.bghProductQualificationTable5.fileId = null;
            $scope.bghProductQualificationTable5.filePath = null;
            $scope.bghProductQualificationTable5.fileName = null;
        }

        //选择关联供应商
        $scope.selectFactoryInfo = function (value) {
            $('#selectFactoryLov').modal('toggle')

        }

        //选择关联供应商-回调
        $scope.selectFactoryReturn = function (key, value, currentList) {
            for (var i = 0; i < currentList.length; i++) {
                var supplierArray = {
                    supplierName: currentList[i].supplierName,
                    associatedSupplierId: currentList[i].supplierId,
                    deptCode: $scope.program.deptCode
                };
                $scope.bghRelatedFactoryDataTable.push(supplierArray);
                var flag = 'N';
                for(var j = 0; j < $scope.relatedFactoryDataTable.length; j++){
                    if($scope.relatedFactoryDataTable[j].supplierBghName == '' || $scope.relatedFactoryDataTable[j].supplierBghName == null || $scope.relatedFactoryDataTable[j].supplierBghName == undefined){
                        $scope.relatedFactoryDataTable[j].supplierBghName = currentList[i].supplierName;
                        $scope.relatedFactoryDataTable[j].associatedSupplierBghId = currentList[i].supplierId;
                        $scope.relatedFactoryDataTable[j].deptCode = $scope.program.deptCode;
                        $scope.relatedFactoryDataTable[j].sourceId = '';
                        flag = 'Y';
                        break;
                    }
                }

                if(flag == 'N'){
                    var supplierArray = {
                        supplierName: '',
                        associatedSupplierId: '',
                        supplierBghName: currentList[i].supplierName,
                        associatedSupplierBghId: currentList[i].supplierId,
                        sourceId: '',
                        deptCode: $scope.program.deptCode
                    };
                    $scope.relatedFactoryDataTable.push(supplierArray);
                }
            }
        }

        //生产资质信息-新增其他资质文件
        $scope.addProductQualifications = function () {
            var qualificationsFileArray = {
                fileName: "",
                filePath: "",
                deptmentCode: $scope.program.deptCode,
                description: "",
                fileId: "",
                supplierId: "",
                invalidDate: "",
                attachmentName: "",
                isProductFactory: "Y",
                fileType: "other",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.bghOtherDataTable.push(qualificationsFileArray);
        }

        //生产资质信息-生产许可证-根据当前资质文件新增资质文件
        $scope.addProductQualificationsByRow1 = function () {
            var qualificationsFileArray = {
                fileName: "",
                filePath: "",
                deptmentCode: $scope.program.deptCode,
                description: $scope.productQualificationTable1.description,
                fileId: "",
                supplierId: "",
                invalidDate: $scope.productQualificationTable1.invalidDate,
                attachmentName: $scope.productQualificationTable1.attachmentName,
                isProductFactory: "Y",
                fileType: "productionPermit",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.bghProductionPermitDataTable.push(qualificationsFileArray);
        }

        //生产资质信息-质量安全管理体系认证证明-根据当前资质文件新增资质文件
        $scope.addProductQualificationsByRow2 = function () {
            var qualificationsFileArray = {
                fileName: "",
                filePath: "",
                deptmentCode: $scope.program.deptCode,
                description: $scope.productQualificationTable2.description,
                fileId: "",
                supplierId: "",
                invalidDate: $scope.productQualificationTable2.invalidDate,
                attachmentName: $scope.productQualificationTable2.attachmentName,
                isProductFactory: "Y",
                fileType: "qualitySafety",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.bghQualitySafetyDataTable.push(qualificationsFileArray);
        }

        //生产资质信息-CSR报告-根据当前资质文件新增资质文件
        $scope.addProductQualificationsByRow3 = function () {
            var qualificationsFileArray = {
                fileName: "",
                filePath: "",
                deptmentCode: $scope.program.deptCode,
                description: $scope.productQualificationTable3.description,
                fileId: "",
                supplierId: "",
                invalidDate: $scope.productQualificationTable3.invalidDate,
                attachmentName: $scope.productQualificationTable3.attachmentName,
                isProductFactory: "Y",
                fileType: "csrReport",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.bghCsrReportDataTable.push(qualificationsFileArray);
        }

        //生产资质信息-消防验收证明-根据当前资质文件新增资质文件
        $scope.addProductQualificationsByRow4 = function () {
            var qualificationsFileArray = {
                fileName: "",
                filePath: "",
                deptmentCode: $scope.program.deptCode,
                description: $scope.productQualificationTable4.description,
                fileId: "",
                supplierId: "",
                invalidDate: $scope.productQualificationTable4.invalidDate,
                attachmentName: $scope.productQualificationTable4.attachmentName,
                isProductFactory: "Y",
                fileType: "fireAcceptance",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.bghFireAcceptanceDataTable.push(qualificationsFileArray);
        }

        //生产资质信息-建筑工程竣工验收报告-根据当前资质文件新增资质文件
        $scope.addProductQualificationsByRow5 = function () {
            var qualificationsFileArray = {
                fileName: "",
                filePath: "",
                deptmentCode: $scope.program.deptCode,
                description: $scope.productQualificationTable5.description,
                fileId: "",
                supplierId: "",
                invalidDate: $scope.productQualificationTable5.invalidDate,
                attachmentName: $scope.productQualificationTable5.attachmentName,
                isProductFactory: "Y",
                fileType: "projectAcceptance",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.bghProjectAcceptanceDataTable.push(qualificationsFileArray);
        }

        $scope.uploadProductionPermitFile = function () {
            var row = $scope.bghProductionPermitDataTable[$scope.bghProductionPermitDataTable.selectRow];
            var file = document.querySelector('#productionPermitFile' + row.index).files[0];
            $scope.uploadSczzFile(file, row);
        }

        $scope.uploadQualitySafetyFile = function () {
            var row = $scope.bghQualitySafetyDataTable[$scope.bghQualitySafetyDataTable.selectRow];
            var file = document.querySelector('#qualitySafetyFile' + row.index).files[0];
            $scope.uploadSczzFile(file, row);
        }

        $scope.uploadCsrReportFile = function () {
            var row = $scope.bghCsrReportDataTable[$scope.bghCsrReportDataTable.selectRow];
            var file = document.querySelector('#csrReportFile' + row.index).files[0];
            $scope.uploadSczzFile(file, row);
        }

        $scope.uploadFireAcceptanceFile = function () {
            var row = $scope.bghFireAcceptanceDataTable[$scope.bghFireAcceptanceDataTable.selectRow];
            var file = document.querySelector('#fireAcceptanceFile' + row.index).files[0];
            $scope.uploadSczzFile(file, row);
        }

        $scope.uploadProjectAcceptanceFile = function () {
            var row = $scope.bghProjectAcceptanceDataTable[$scope.bghProjectAcceptanceDataTable.selectRow];
            var file = document.querySelector('#projectAcceptanceFile' + row.index).files[0];
            $scope.uploadSczzFile(file, row);
        }

        $scope.uploadOtherFile = function () {
            var row = $scope.bghOtherDataTable[$scope.bghOtherDataTable.selectRow];
            var file = document.querySelector('#otherFile' + row.index).files[0];
            $scope.uploadSczzFile(file, row);
        }

        //生产资质信息-生产许可证-新增行附件上传
        $scope.uploadSczzFile = function (file, row) {
            var fd = new FormData();
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
            if (!id) {
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                row.fileId = response.data[0].fileId;
                row.fileName = response.data[0].fileName;
                row.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //生产资质信息-生产许可证-新增行附件上传-重新上传
        $scope.changeSczzFile = function (row) {
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        }

        //删除生产许可证附件行
        $scope.deleteProductionPermitFile = function (row, index) {
            JS.confirm('删除', '确定删除附件？', '确定', function () {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.bghProductionPermitDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteBgCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.bghProductionPermitDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除质量安全管理体系认证证明附件行
        $scope.deleteQualitySafetyFile = function (row, index) {
            JS.confirm('删除', '确定删除附件？', '确定', function () {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.bghQualitySafetyDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteBgCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.bghQualitySafetyDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除CSR报告附件行
        $scope.deleteCsrReportFile = function (row, index) {
            JS.confirm('删除', '确定删除附件？', '确定', function () {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.bghCsrReportDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteBgCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.bghCsrReportDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除消防验收证明附件行
        $scope.deleteFireAcceptanceFile = function (row, index) {
            JS.confirm('删除', '确定删除附件？', '确定', function () {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.bghFireAcceptanceDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteBgCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.bghFireAcceptanceDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除建筑工程竣工验收报告附件行
        $scope.deleteProjectAcceptanceFile = function (row, index) {
            JS.confirm('删除', '确定删除附件？', '确定', function () {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.bghProjectAcceptanceDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteBgCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.bghProjectAcceptanceDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除其他附件行
        $scope.deleteOtherFile = function (row, index) {
            JS.confirm('删除', '确定删除附件？', '确定', function () {
                if (row.sourceId == null || row.sourceId == "") {
                    $scope.bghOtherDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteBgCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.sourceId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.bghOtherDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }


        $scope.nullConvernZero = function (params, row) {
            if (row.oemProductionInfoBghParams.factoryArea == null || row.oemProductionInfoBghParams.factoryArea == undefined || row.oemProductionInfoBghParams.factoryArea == '') {
                row.oemProductionInfoBghParams.factoryArea = 0;
            }
            if (params != null && params != undefined && params != '') {
                row.oemProductionInfoBghParams.factoryArea = Math.round((row.oemProductionInfoBghParams.factoryArea + parseFloat(params))*100)/100;
            }
        }

        $scope.nullConvernZero2 = function (params, row) {
            if (row.oemProductionInfoBghParams.employeesAmount == null || row.oemProductionInfoBghParams.employeesAmount == undefined || row.oemProductionInfoBghParams.employeesAmount == '') {
                row.oemProductionInfoBghParams.employeesAmount = 0;
            }
            if (params != null && params != undefined && params != '') {
                row.oemProductionInfoBghParams.employeesAmount = row.oemProductionInfoBghParams.employeesAmount + parseFloat(params);
            }
        }
        //校验生产区域面积输入是否正数
        $scope.checkProductionAreaPositive = function() {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            var productionArea = row.oemProductionInfoBghParams.productionArea;
            // if (productionArea.length > 1) {
            //     productionArea = productionArea.replace(/^0+/,"");
            // }
            // row.oemProductionInfoParams.productionArea = productionArea.replace(/\D/g, '');

            productionArea = productionArea.replace(/[^\d.]/g, "");  //仅保留数字和"."
            productionArea = productionArea.replace(/\.{2,}/g, "."); //两个连续的"."仅保留第一个"."
            productionArea = productionArea.replace(".", "$#*").replace(/\./g,'').replace('$#*','.');//去除其他"."
            productionArea = productionArea.replace(/^(\d+)\.(\d\d).*$/, '$1.$2');;//限制只能输入两个小数
            if (productionArea.indexOf(".") < 0 && productionArea != "") { //首位是0的话去掉
                productionArea = parseFloat(productionArea);
            }
            row.oemProductionInfoBghParams.productionArea = productionArea;
        }
        //校验成品仓库面积输入是否正数
        $scope.checkFinishedProductAreaPositive = function() {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            var finishedProductArea = row.oemProductionInfoBghParams.finishedProductArea;
            // if (finishedProductArea.length > 1) {
            //     finishedProductArea = finishedProductArea.replace(/^0+/,"");
            // }
            // row.oemProductionInfoParams.finishedProductArea = finishedProductArea.replace(/\D/g, '');
            finishedProductArea = finishedProductArea.replace(/[^\d.]/g, "");  //仅保留数字和"."
            finishedProductArea = finishedProductArea.replace(/\.{2,}/g, "."); //两个连续的"."仅保留第一个"."
            finishedProductArea = finishedProductArea.replace(".", "$#*").replace(/\./g,'').replace('$#*','.');//去除其他"."
            finishedProductArea = finishedProductArea.replace(/^(\d+)\.(\d\d).*$/, '$1.$2');;//限制只能输入两个小数
            if (finishedProductArea.indexOf(".") < 0 && finishedProductArea != "") { //首位是0的话去掉
                finishedProductArea = parseFloat(finishedProductArea);
            }
            row.oemProductionInfoBghParams.finishedProductArea = finishedProductArea;
        }
        //校验原料仓库面积输入是否正数
        $scope.checkRawMaterialAreaPositive = function() {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            var rawMaterialArea = row.oemProductionInfoBghParams.rawMaterialArea;
            // if (rawMaterialArea.length > 1) {
            //     rawMaterialArea = rawMaterialArea.replace(/^0+/,"");
            // }
            // row.oemProductionInfoParams.rawMaterialArea = rawMaterialArea.replace(/\D/g, '');
            rawMaterialArea = rawMaterialArea.replace(/[^\d.]/g, "");  //仅保留数字和"."
            rawMaterialArea = rawMaterialArea.replace(/\.{2,}/g, "."); //两个连续的"."仅保留第一个"."
            rawMaterialArea = rawMaterialArea.replace(".", "$#*").replace(/\./g,'').replace('$#*','.');//去除其他"."
            rawMaterialArea = rawMaterialArea.replace(/^(\d+)\.(\d\d).*$/, '$1.$2');;//限制只能输入两个小数
            if (rawMaterialArea.indexOf(".") < 0 && rawMaterialArea != "") { //首位是0的话去掉
                rawMaterialArea = parseFloat(rawMaterialArea);
            }
            row.oemProductionInfoBghParams.rawMaterialArea = rawMaterialArea;
        }
        //校验包装材料仓库面积输入是否正数
        $scope.checkPackagingAreaPositive = function() {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            var packagingArea = row.oemProductionInfoBghParams.packagingArea;
            // if (packagingArea.length > 1) {
            //     packagingArea = packagingArea.replace(/^0+/,"");
            // }
            // row.oemProductionInfoParams.packagingArea = packagingArea.replace(/\D/g, '');
            packagingArea = packagingArea.replace(/[^\d.]/g, "");  //仅保留数字和"."
            packagingArea = packagingArea.replace(/\.{2,}/g, "."); //两个连续的"."仅保留第一个"."
            packagingArea = packagingArea.replace(".", "$#*").replace(/\./g,'').replace('$#*','.');//去除其他"."
            packagingArea = packagingArea.replace(/^(\d+)\.(\d\d).*$/, '$1.$2');;//限制只能输入两个小数
            if (packagingArea.indexOf(".") < 0 && packagingArea != "") { //首位是0的话去掉
                packagingArea = parseFloat(packagingArea);
            }
            row.oemProductionInfoBghParams.packagingArea = packagingArea;
        }
        //校验实验室面积输入是否正数
        $scope.checkLaboratoryAreaPositive = function() {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            var laboratoryArea = row.oemProductionInfoBghParams.laboratoryArea;
            // if (laboratoryArea.length > 1) {
            //     laboratoryArea = laboratoryArea.replace(/^0+/,"");
            // }
            // row.oemProductionInfoParams.laboratoryArea = laboratoryArea.replace(/\D/g, '');
            laboratoryArea = laboratoryArea.replace(/[^\d.]/g, "");  //仅保留数字和"."
            laboratoryArea = laboratoryArea.replace(/\.{2,}/g, "."); //两个连续的"."仅保留第一个"."
            laboratoryArea = laboratoryArea.replace(".", "$#*").replace(/\./g,'').replace('$#*','.');//去除其他"."
            laboratoryArea = laboratoryArea.replace(/^(\d+)\.(\d\d).*$/, '$1.$2');;//限制只能输入两个小数
            if (laboratoryArea.indexOf(".") < 0 && laboratoryArea != "") { //首位是0的话去掉
                laboratoryArea = parseFloat(laboratoryArea);
            }
            row.oemProductionInfoBghParams.laboratoryArea = laboratoryArea;
        }
        //校验办公区占地面积输入是否正数
        $scope.checkOfficeAreaPositive = function() {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            var officeArea = row.oemProductionInfoBghParams.officeArea;
            // if (officeArea.length > 1) {
            //     officeArea = officeArea.replace(/^0+/,"");
            // }
            // row.oemProductionInfoParams.officeArea = officeArea.replace(/\D/g, '');
            officeArea = officeArea.replace(/[^\d.]/g, "");  //仅保留数字和"."
            officeArea = officeArea.replace(/\.{2,}/g, "."); //两个连续的"."仅保留第一个"."
            officeArea = officeArea.replace(".", "$#*").replace(/\./g,'').replace('$#*','.');//去除其他"."
            officeArea = officeArea.replace(/^(\d+)\.(\d\d).*$/, '$1.$2');;//限制只能输入两个小数
            if (officeArea.indexOf(".") < 0 && laboratoryArea != "") { //首位是0的话去掉
                officeArea = parseFloat(officeArea);
            }
            row.oemProductionInfoBghParams.officeArea = officeArea;
        }

        //校验质量人员输入是否正数
        $scope.checkQualityPersonnelAmountPositive = function() {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            var qualityPersonnelAmount = row.oemProductionInfoBghParams.qualityPersonnelAmount;
            if (qualityPersonnelAmount.length > 1) {
                qualityPersonnelAmount = qualityPersonnelAmount.replace(/^0+/,"");
            }
            row.oemProductionInfoBghParams.qualityPersonnelAmount = qualityPersonnelAmount.replace(/\D/g, '');
        }
        //校验销售人员输入是否正数
        $scope.checkSalesmanAmountPositive = function() {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            var salesmanAmount = row.oemProductionInfoBghParams.salesmanAmount;
            if (salesmanAmount.length > 1) {
                salesmanAmount = salesmanAmount.replace(/^0+/,"");
            }
            row.oemProductionInfoBghParams.salesmanAmount = salesmanAmount.replace(/\D/g, '');
        }

        //校验生产人员输入是否正数
        $scope.checkProducersAmountPositive = function() {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            var producersAmount = row.oemProductionInfoBghParams.producersAmount;
            if (producersAmount.length > 1) {
                producersAmount = producersAmount.replace(/^0+/,"");
            }
            row.oemProductionInfoBghParams.producersAmount = producersAmount.replace(/\D/g, '');
        }
        //校验设计与开发输入是否正数
        $scope.checkDesignerAmountPositive = function() {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            var designerAmount = row.oemProductionInfoBghParams.designerAmount;
            if (designerAmount.length > 1) {
                designerAmount = designerAmount.replace(/^0+/,"");
            }
            row.oemProductionInfoBghParams.designerAmount = designerAmount.replace(/\D/g, '');
        }
        //校验行政人员输入是否正数
        $scope.checkAdministrativeStaffAmountPositive = function() {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            var administrativeStaffAmount = row.oemProductionInfoBghParams.administrativeStaffAmount;
            if (administrativeStaffAmount.length > 1) {
                administrativeStaffAmount = administrativeStaffAmount.replace(/^0+/,"");
            }
            row.oemProductionInfoBghParams.administrativeStaffAmount = administrativeStaffAmount.replace(/\D/g, '');
        }
        //校验行政人员输入是否正数
        $scope.checkOtherPositive = function() {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            var other = row.oemProductionInfoBghParams.other;
            if (other.length > 1) {
                other = other.replace(/^0+/,"");
            }
            row.oemProductionInfoBghParams.other = other.replace(/\D/g, '');
        }

        /********************计算工厂面积********************/
        $scope.calFactoryArea = function () {
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            row.oemProductionInfoBghParams.factoryArea = 0;
            $scope.nullConvernZero(row.oemProductionInfoBghParams.productionArea, row);
            $scope.nullConvernZero(row.oemProductionInfoBghParams.finishedProductArea, row);
            $scope.nullConvernZero(row.oemProductionInfoBghParams.rawMaterialArea, row);
            $scope.nullConvernZero(row.oemProductionInfoBghParams.packagingArea, row);
            $scope.nullConvernZero(row.oemProductionInfoBghParams.laboratoryArea, row);
            $scope.nullConvernZero(row.oemProductionInfoBghParams.officeArea, row);
        }

        /******************计算员工数量*******************/
        $scope.calEmployeesAmount = function () {
            debugger;
            var row = $scope.oemAddressInfoBghDataTable[$scope.oemAddressInfoBghDataTable.selectRow];
            row.oemProductionInfoBghParams.employeesAmount = 0;
            $scope.nullConvernZero2(row.oemProductionInfoBghParams.qualityPersonnelAmount, row);
            $scope.nullConvernZero2(row.oemProductionInfoBghParams.salesmanAmount, row);
            $scope.nullConvernZero2(row.oemProductionInfoBghParams.producersAmount, row);
            $scope.nullConvernZero2(row.oemProductionInfoBghParams.designerAmount, row);
            $scope.nullConvernZero2(row.oemProductionInfoBghParams.administrativeStaffAmount, row);
            $scope.nullConvernZero2(row.oemProductionInfoBghParams.other, row);
        }

        /**********************************工作流配置1**************************************/
        function urlToObj(url) {
            var index = url.lastIndexOf('?');
            var params = url.substring(index+1);
            var arr = params.split('&');
            var obj = {};
            arr.forEach(function (item) {
                obj[item.split('=')[0]] = item.split('=')[1];
            });
            return obj;
        }
        //获取url参数对象
        $scope.urlParams = urlToObj(location.hash);

        //获取单据ID
        function getId() {
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.program.changeId;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate')||localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'EQU_DABG_OEM.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
        };
        // tab 切换
        $scope.tabAction = 'requestFundsBusiness'; //根据页面配置
        $scope.tabChange = function (name) {
            $scope.tabAction = name;
            if ((!$scope.processImgLoad) && (name == 'opinion')) {
                $scope.getProcessInfo(function () {
                    var p = $scope.processImageParams;
                    $timeout(function () {
                        processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                        $scope.processImgLoad = true;
                    }, 100)
                });
            }
        };
        // 获取流程信息
        $scope.getProcessInfo = function (callback) {
            httpServer.post(URLAPI.processGet, {
                    'params': JSON.stringify({
                        processDefinitionKey: $scope.processImageParams.key,
                        businessKey:$scope.program.changeId
                    })
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.processImageParams.instanceId = res.data.processInstanceId;
                    }
                    callback && callback(res);
                });
        };
        //先判断是否业务form是否有id 再判断流程form的id
        // if ($stateParams.qualificationId){
        //     $scope.id = $stateParams.qualificationId;
        // }else if ($scope.urlParams.businessKey){
        //     $scope.id = $scope.urlParams.businessKey;
        // }

        /**********************************工作流配置**************************************/

        //提交审批
        $scope.btnSubmit = function(){
            //校验
            if ($scope.validate()) {
                return;
            }

            $scope.bgqParams.supplierBaseInfo = $scope.supplierProgram; //供应商基础信息
            $scope.bgqParams.relatedFactoryInfo = $scope.bgqRelatedFactoryDataTable;//关联工厂
            $scope.bgqParams.supplierContactInfo = $scope.supplierContactDataTable; //联系人
            $scope.bgqParams.supplierCredentialsInfo = $scope.supplierCredentialsProgram; //资质信息
            $scope.bgqParams.supplierBankInfo = $scope.supplierBankInfoDataTable; //银行信息
            $scope.bgqParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
            if($scope.program.deptCode == '0E'){
                $scope.bgqParams.copCategoryInfo = $scope.supplierCopCategoryDataTable; //合作品类
                // $scope.bgqParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
                // $scope.bgqParams.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
                $scope.bgqParams.oemAddressInfo = $scope.oemAddressInfoDataTable; //地址信息OEM
            }//OEM贸易商
            if ($scope.program.deptCode == '0E' && $scope.supplierProgram.supplierType == '10') {
                $scope.bgqParams.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
            }
            //OEM制造工厂
            if ($scope.program.deptCode == '0E' && $scope.supplierProgram.supplierType == '20') {
                $scope.bgqParams.copCategoryInfo = $scope.supplierCopCategoryDataTable; //合作品类
                $scope.productQualificationTable[0] = $scope.productQualificationTable1;
                $scope.productQualificationTable[1] = $scope.productQualificationTable2;
                $scope.productQualificationTable[2] = $scope.productQualificationTable3;
                $scope.productQualificationTable[3] = $scope.productQualificationTable4;
                $scope.productQualificationTable[4] = $scope.productQualificationTable5;
                var count = 4;
                for (var i = 0; i < $scope.productionPermitDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.productionPermitDataTable[i];
                }
                for (var i = 0; i < $scope.qualitySafetyDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.qualitySafetyDataTable[i];
                }
                for (var i = 0; i < $scope.csrReportDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.csrReportDataTable[i];
                }
                for (var i = 0; i < $scope.fireAcceptanceDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.fireAcceptanceDataTable[i];
                }
                for (var i = 0; i < $scope.projectAcceptanceDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.projectAcceptanceDataTable[i];
                }
                for (var i = 0; i < $scope.otherDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.otherDataTable[i];
                }
                $scope.bgqParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
                $scope.bgqParams.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
            }
            if($scope.program.deptCode == 'IT'){
                debugger;
                $scope.bgqParams.serviceScopeInfo = $scope.supplierServiceScopeDataTable; //服务范围
                // $scope.bgqParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
                $scope.bgqParams.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
                $scope.bgqParams.itAddressInfo = $scope.itAddressInfoDataTable; //地址信息IT
            }

//变更后
            $scope.bghParams.supplierBaseInfo = $scope.supplierBghProgram; //供应商基础信息
            $scope.bghParams.relatedFactoryInfo = $scope.bghRelatedFactoryDataTable;//关联工厂
            $scope.bghParams.supplierContactInfo = $scope.supplierBghContactDataTable; //联系人
            $scope.bghParams.supplierCredentialsInfo = $scope.supplierCredentialsBghProgram; //资质信息
            $scope.bghParams.supplierBankInfo = $scope.supplierBghBankInfoDataTable; //银行信息
            // $scope.bghParams.productQualificationFileInfo = $scope.bghProductQualificationTable; //生产资质文件
            if($scope.program.deptCode == '0E'){
                $scope.bghParams.copCategoryInfo = $scope.supplierBghCopCategoryDataTable; //合作品类
                // $scope.bgqParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
                // $scope.bgqParams.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
                $scope.bghParams.oemAddressInfo = $scope.oemAddressInfoBghDataTable; //地址信息OEM
            }//OEM贸易商
            if ($scope.program.deptCode == '0E' && $scope.supplierBghProgram.supplierType == '10') {
                $scope.bghParams.qualificationsFileInfo = $scope.bghQualificationsFileDataTable; //资质文件
            }
            //OEM制造工厂
            if ($scope.program.deptCode == '0E' && $scope.supplierBghProgram.supplierType == '20') {
                $scope.bghParams.copCategoryInfo = $scope.supplierBghCopCategoryDataTable; //合作品类
                $scope.productQualificationTable[0] = $scope.bghProductQualificationTable1;
                $scope.productQualificationTable[1] = $scope.bghProductQualificationTable2;
                $scope.productQualificationTable[2] = $scope.bghProductQualificationTable3;
                $scope.productQualificationTable[3] = $scope.bghProductQualificationTable4;
                $scope.productQualificationTable[4] = $scope.bghProductQualificationTable5;
                var count = 4;
                for (var i = 0; i < $scope.bghProductionPermitDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.bghProductionPermitDataTable[i];
                }
                for (var i = 0; i < $scope.bghQualitySafetyDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.bghQualitySafetyDataTable[i];
                }
                for (var i = 0; i < $scope.bghCsrReportDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.bghCsrReportDataTable[i];
                }
                for (var i = 0; i < $scope.bghFireAcceptanceDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.bghFireAcceptanceDataTable[i];
                }
                for (var i = 0; i < $scope.bghProjectAcceptanceDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.bghProjectAcceptanceDataTable[i];
                }
                for (var i = 0; i < $scope.bghOtherDataTable.length; i++) {
                    count = count + 1;
                    $scope.productQualificationTable[count] = $scope.bghOtherDataTable[i];
                }
                // $scope.bghParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
                $scope.bghParams.productQualificationFileInfo = $scope.bghProductQualificationTable; //生产资质文件
                $scope.bghParams.qualificationsFileInfo = $scope.bghQualificationsFileDataTable; //资质文件
            }
            if($scope.program.deptCode == 'IT'){
                $scope.bghParams.serviceScopeInfo = $scope.supplierBghServiceScopeDataTable; //服务范围
                // $scope.bgqParams.productQualificationFileInfo = $scope.productQualificationTable; //生产资质文件
                $scope.bghParams.qualificationsFileInfo = $scope.bghQualificationsFileDataTable; //资质文件
                $scope.bghParams.itAddressInfo = $scope.itAddressInfoBghDataTable; //地址信息IT
            }

            // $scope.bghParams.supplierBaseInfo = $scope.supplierBghProgram; //供应商基础信息
            // $scope.bghParams.relatedFactoryInfo = $scope.relatedFactoryDataTable;//关联工厂
            // $scope.bghParams.supplierContactInfo = $scope.supplierBghContactDataTable; //联系人
            // $scope.bghParams.supplierCredentialsInfo = $scope.supplierCredentialsBghProgram; //资质信息
            // $scope.bghParams.supplierBankInfo = $scope.supplierBghBankInfoDataTable; //银行信息
            // $scope.bghParams.productQualificationFileInfo = $scope.bghProductQualificationTable; //生产资质文件
            // $scope.bghParams.qualificationsFileInfo = $scope.bghQualificationsFileDataTable; //资质文件
            //
            // if($scope.program.deptCode == 'OEM'){
            //     $scope.bghParams.copCategoryInfo = $scope.supplierBghCopCategoryDataTable; //合作品类
            //     // $scope.bgqParams.productQualificationFileInfo = $scope.bghProductQualificationTable; //生产资质文件
            //     // $scope.bgqParams.qualificationsFileInfo = $scope.bghQualificationsFileDataTable; //资质文件
            //     $scope.bghParams.oemAddressInfo = $scope.oemAddressInfoBghDataTable; //地址信息OEM
            // }else if($scope.program.deptCode == 'IT'){
            //     $scope.bghParams.serviceScopeInfo = $scope.supplierBghServiceScopeDataTable; //服务范围
            //     // $scope.bgqParams.productQualificationFileInfo = $scope.bghProductQualificationTable; //生产资质文件
            //     // $scope.bgqParams.qualificationsFileInfo = $scope.bghQualificationsFileDataTable; //资质文件
            //     $scope.bghParams.itAddressInfo = $scope.itAddressInfoBghDataTable; //地址信息IT
            // }

            $scope.params.archivesChangeInfo = $scope.program;
            if($scope.program.changeId == '' || $scope.program.changeId == undefined){
                $scope.params.bgqParams = $scope.bgqParams;
                $scope.params.bghParams = $scope.bghParams;
            }else{
                $scope.params.bghParams = $scope.bghParams;
            }

            httpServer.post(URLAPI.saveArchivesChangeOrder, {
                'params': JSON.stringify($scope.params)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.program = res.data;
                    $scope.search($scope.program.changeId);

                    if($scope.program.status != '10' && $scope.program.status != '40'){
                        JS.alert('状态不是拟定或驳回，不允许提交审批', 'error', '确定');
                        return;
                    }
                    $scope.extend ={
                        "tasks_assignees":[]
                    }
                    $scope.variables = []; //流程变量
                    angular.forEach($scope.program, function (value, key) {
                        $scope.variables.push({
                            name: key,
                            type: 'string',
                            value: value
                        });
                    });

                    $scope.properties={
                        "menucode": "DABG",
                        "opinion": ""
                    };
                    $scope.paramsBpm={
                        "extend":$scope.extend,
                        "variables":$scope.variables,
                        "properties":$scope.properties,
                        "responsibilityId": "990021",
                        "respId": "990021",
                        "processDefinitionKey": "EQU_DABG_OEM.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                        "saveonly": false,
                        "businessKey": $scope.program.changeId, //单据ID
                        "title": "供应商档案变更流程", //流程标题
                        "positionId": 0,
                        "orgId": 0,
                        "userType": "30",
                        "billNo": $scope.program.changeNumber
                    }
                    httpServer.post(URLAPI.bpmStart, {
                        'params': JSON.stringify($scope.paramsBpm)
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.search($scope.program.changeId);
                            //修改单据状态为待审核
                            $scope.program.status = '20';
                            $scope.btnSave();
                            JS.alert('提交成功');
                        }
                        else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    }, function (err) {
                        JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    });

                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('保存失败', 'error', '确定');
            })
        }
    }]);
});
