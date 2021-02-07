/**
 * Created by sie_panshibang on 2019/9/4.
 */
define(["app", "angularFileUpload", "pinyin", "GooFlow"], function (app, angularFileUpload, pinyin) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('qualificationDetailCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'iframeTabAction', '$http', 'SIEJS', '$timeout', '$controller', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, iframeTabAction, $http, SIEJS, $timeout, $controller) {
        $controller('processBase', {$scope: $scope}); // 继承基础的流程控制器
        if ($stateParams.qualificationId) {
            var id = $stateParams.qualificationId;
        } else if ($scope.urlParams.businessKey) {
            var id = $scope.urlParams.businessKey;
        }

        $scope.params = {};
        $scope.program = {};
        $scope.flags = {};
        $scope.supplierProgram = {};
        $scope.catetoryParams = {};
        $scope.rowsIndex = 5;
        $scope.readonlyFlag = 'N';
        $scope.btnSaveReadonlyFlag = 'N';
        $scope.btnSubmitReadonlyFlag = 'Y';
        $scope.btnRejectReadonlyFlag = 'Y';
        $scope.btnCancelReadonlyFlag = 'Y';

        //当前登录人所属部门
        $scope.program.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.program.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.deptId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptId;


        $scope.program.qualificationStatus = '10';
        $scope.program.qualificationStatusMeaning = '拟定';
        $scope.program.departmentName = $scope.program.deptName;
        //当前登录人类型
        $scope.flags.userTpye = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        // $scope.flags.userTpye = '60';

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
        $scope.productQualificationTable1 = {
            "attachmentName": "生产许可证",
            "fixFlag": "Y",
            "fileName": "",
            "filePath": "",
            "deptmentCode": $scope.program.deptCode,
            "description": "",
            "fileId": "",
            "supplierId": "",
            "invalidDate": "",
            "isProductFactory": "Y",
            "fileType": "FIXED",
            "index": "1"
        };
        $scope.productQualificationTable2 = {
            "attachmentName": "质量安全管理体系认证证明",
            "fixFlag": "Y",
            "fileName": "",
            "filePath": "",
            "deptmentCode": $scope.program.deptCode,
            "description": "",
            "fileId": "",
            "supplierId": "",
            "invalidDate": "",
            "isProductFactory": "Y",
            "fileType": "FIXED",
            "index": "2"
        };
        $scope.productQualificationTable3 = {
            "attachmentName": "CSR报告",
            "fixFlag": "Y",
            "fileName": "",
            "filePath": "",
            "deptmentCode": $scope.program.deptCode,
            "description": "",
            "fileId": "",
            "supplierId": "",
            "invalidDate": "",
            "isProductFactory": "Y",
            "fileType": "FIXED",
            "index": "3"
        };
        $scope.productQualificationTable4 = {
            "attachmentName": "消防验收证明",
            "fixFlag": "Y",
            "fileName": "",
            "filePath": "",
            "deptmentCode": $scope.program.deptCode,
            "description": "",
            "fileId": "",
            "supplierId": "",
            "invalidDate": "",
            "isProductFactory": "Y",
            "fileType": "FIXED",
            "index": "4"
        };
        $scope.productQualificationTable5 = {
            "attachmentName": "建筑工程竣工验收报告",
            "fixFlag": "Y",
            "fileName": "",
            "filePath": "",
            "deptmentCode": $scope.program.deptCode,
            "description": "",
            "fileId": "",
            "supplierId": "",
            "invalidDate": "",
            "isProductFactory": "Y",
            "fileType": "FIXED",
            "index": "5"
        };

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
        $scope.supplierCredentialsProgram = {"deptCode": $scope.program.deptCode};
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

        $scope.newDataTable = [];

        /********************查询资质审核单据信息**********************/
        $scope.search = function (id) {
            httpServer.post(URLAPI.findQualificationList, {
                params: JSON.stringify({
                    qualificationId: id
                })
            }, function (res) {
                if (res.status == 'S') {
                    debugger;
                    $scope.program = res.data[0];
                    $scope.program.departmentName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;

                    if ($scope.flags.userTpye == '60'){
                        if($scope.program.deptCode == '0E'){
                            $scope.program.departmentName = 'OEM and QA';
                        }else if($scope.program.deptCode == '03'){
                            $scope.program.departmentName = '电脑部';
                        }
                    }

                    //状态：拟定(10),已注册(30)，驳回(60),供应商不可编辑，内部人员可编辑
                    if ($scope.program.qualificationStatus == '10' || $scope.program.qualificationStatus == '30' || $scope.program.qualificationStatus == '60') {
                        if ($scope.flags.userTpye == '60') {
                            $scope.readonlyFlag = 'Y';
                        } else {
                            $scope.readonlyFlag = 'N';
                        }
                    }
                    //状态：注册中(20),供应商可编辑，内部人员不可编辑
                    if ($scope.program.qualificationStatus == '20') {
                        if ($scope.flags.userTpye == '60') {
                            $scope.readonlyFlag = 'N';
                        } else {
                            $scope.readonlyFlag = 'Y';
                        }
                    }

                    //状态：待审核(40),已审核(50)，取消(70),供应商不可编辑，内部人员不可编辑
                    if ($scope.program.qualificationStatus == '40' || $scope.program.qualificationStatus == '50' || $scope.program.qualificationStatus == '70') {
                        $scope.readonlyFlag = 'Y';
                    }

                    //保存/提交按钮控制
                    if ($scope.flags.userTpye == '60') {
                        if ($scope.program.qualificationStatus == '20') {
                            $scope.btnSaveReadonlyFlag = 'N';
                            $scope.btnSubmitReadonlyFlag = 'N';
                        } else {
                            $scope.btnSaveReadonlyFlag = 'Y';
                            $scope.btnSubmitReadonlyFlag = 'Y';
                        }
                    } else {
                        if ($scope.program.qualificationStatus == '10' || $scope.program.qualificationStatus == '30' || $scope.program.qualificationStatus == '60') {
                            $scope.btnSaveReadonlyFlag = 'N';
                            $scope.btnSubmitReadonlyFlag = 'N';
                        } else {
                            $scope.btnSaveReadonlyFlag = 'Y';
                            $scope.btnSubmitReadonlyFlag = 'Y';
                        }
                    }
                    //驳回
                    if ($scope.program.qualificationStatus == '30' || $scope.program.qualificationStatus == '60') {
                        $scope.btnRejectReadonlyFlag = 'N';
                    } else {
                        $scope.btnRejectReadonlyFlag = 'Y';
                    }
                    //取消
                    if ($scope.program.qualificationStatus == '60') {
                        $scope.btnCancelReadonlyFlag = 'N';
                    } else {
                        $scope.btnCancelReadonlyFlag = 'Y';
                    }


                    $scope.searchBaseInfo($scope.program.supplierId);
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
                    //查询各个子信息
                    $scope.historicParam = {}
                    $scope.historicParam.code = $scope.program.qualificationNumber
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

        /********************查询供应商基本信息**********************/
        $scope.searchBaseInfo = function (supplierId) {
            httpServer.post(URLAPI.findZzscSupplierInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    deptCode: $scope.program.deptCode
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
            httpServer.post(URLAPI.findZzscAssociatedSupplier, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode: $scope.program.deptCode
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
            httpServer.post(URLAPI.findZzscSupplierCategorysInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode: $scope.program.deptCode
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
            httpServer.post(URLAPI.findZzscSupplierCategorysInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode: $scope.program.deptCode
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
            httpServer.post(URLAPI.findZzscSupplierContactsInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
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
            httpServer.post(URLAPI.findZzscSupplierCredentialsInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.supplierCredentialsProgram = res.data[0];
                    } else {
                        $scope.supplierCredentialsProgram.deptCode = $scope.program.deptCode;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商生产资质信息**********************/
        $scope.searchProductQualificationsInfo = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
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

        $scope.searchProductQualificationsInfo2 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
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

        $scope.searchProductQualificationsInfo3 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
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

        $scope.searchProductQualificationsInfo4 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
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

        $scope.searchProductQualificationsInfo5 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
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

        $scope.searchProductQualificationsInfo6 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
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

        $scope.searchProductQualificationsInfo7 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
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

        /********************查询供应商资质文件信息**********************/
        $scope.searchQualificationsFileInfo = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    isProductFactory: 'N',
                    deptmentCode: $scope.program.deptCode
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
            httpServer.post(URLAPI.findZzscSupplierBankInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId
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
            httpServer.post(URLAPI.findZzscSupplierAddressInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.oemAddressInfoDataTable = res.data;
                    $scope.oemAddressInfoDataTable.selectRow = 0;
                    var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
                    $scope.loadLinesData(row, 1);
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询IT地址信息**********************/
        $scope.searchItAddressInfo = function () {
            httpServer.post(URLAPI.findZzscSupplierAddressInfo, {
                params: JSON.stringify({
                    supplierId: $scope.program.supplierId,
                    deptCode: $scope.program.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.itAddressInfoDataTable = res.data;
                    $scope.itAddressInfoDataTable.selectRow = 0;
                    var row = $scope.itAddressInfoDataTable[$scope.itAddressInfoDataTable.selectRow];
                    $scope.loadITLinesData(row, 1);
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************判断是新增还是修改********************/
        if (id == "" || id == undefined) {
        } else {
            $scope.search(id);
        }
        ;

        /********************资质信息-勾选【长期】触发时间********************/
        $scope.clearLicenseIndate = function () {
            $scope.supplierCredentialsProgram.licenseIndate = undefined;
        }

        $scope.nullConvernZero = function (params, row) {
            if (row.oemProductionInfoParams.factoryArea == null || row.oemProductionInfoParams.factoryArea == undefined || row.oemProductionInfoParams.factoryArea == '') {
                row.oemProductionInfoParams.factoryArea = 0;
            }
            if (params != null && params != undefined && params != '') {
                row.oemProductionInfoParams.factoryArea = Math.round((row.oemProductionInfoParams.factoryArea + parseFloat(params)) * 100) / 100;
            }
        }

        $scope.nullConvernZero2 = function (params, row) {
            if (row.oemProductionInfoParams.employeesAmount == null || row.oemProductionInfoParams.employeesAmount == undefined || row.oemProductionInfoParams.employeesAmount == '') {
                row.oemProductionInfoParams.employeesAmount = 0;
            }
            if (params != null && params != undefined && params != '') {
                row.oemProductionInfoParams.employeesAmount = row.oemProductionInfoParams.employeesAmount + parseFloat(params);
            }
        }
        //校验生产区域面积输入是否正数
        $scope.checkProductionAreaPositive = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            var productionArea = row.oemProductionInfoParams.productionArea;
            // if (productionArea.length > 1) {
            //     productionArea = productionArea.replace(/^0+/,"");
            // }
            // row.oemProductionInfoParams.productionArea = productionArea.replace(/\D/g, '');

            productionArea = productionArea.replace(/[^\d.]/g, "");  //仅保留数字和"."
            productionArea = productionArea.replace(/\.{2,}/g, "."); //两个连续的"."仅保留第一个"."
            productionArea = productionArea.replace(".", "$#*").replace(/\./g, '').replace('$#*', '.');//去除其他"."
            productionArea = productionArea.replace(/^(\d+)\.(\d\d).*$/, '$1.$2');
            ;//限制只能输入两个小数
            if (productionArea.indexOf(".") < 0 && productionArea != "") { //首位是0的话去掉
                productionArea = parseFloat(productionArea);
            }
            row.oemProductionInfoParams.productionArea = productionArea;
        }
        //校验成品仓库面积输入是否正数
        $scope.checkFinishedProductAreaPositive = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            var finishedProductArea = row.oemProductionInfoParams.finishedProductArea;
            // if (finishedProductArea.length > 1) {
            //     finishedProductArea = finishedProductArea.replace(/^0+/,"");
            // }
            // row.oemProductionInfoParams.finishedProductArea = finishedProductArea.replace(/\D/g, '');
            finishedProductArea = finishedProductArea.replace(/[^\d.]/g, "");  //仅保留数字和"."
            finishedProductArea = finishedProductArea.replace(/\.{2,}/g, "."); //两个连续的"."仅保留第一个"."
            finishedProductArea = finishedProductArea.replace(".", "$#*").replace(/\./g, '').replace('$#*', '.');//去除其他"."
            finishedProductArea = finishedProductArea.replace(/^(\d+)\.(\d\d).*$/, '$1.$2');
            ;//限制只能输入两个小数
            if (finishedProductArea.indexOf(".") < 0 && finishedProductArea != "") { //首位是0的话去掉
                finishedProductArea = parseFloat(finishedProductArea);
            }
            row.oemProductionInfoParams.finishedProductArea = finishedProductArea;
        }
        //校验原料仓库面积输入是否正数
        $scope.checkRawMaterialAreaPositive = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            var rawMaterialArea = row.oemProductionInfoParams.rawMaterialArea;
            // if (rawMaterialArea.length > 1) {
            //     rawMaterialArea = rawMaterialArea.replace(/^0+/,"");
            // }
            // row.oemProductionInfoParams.rawMaterialArea = rawMaterialArea.replace(/\D/g, '');
            rawMaterialArea = rawMaterialArea.replace(/[^\d.]/g, "");  //仅保留数字和"."
            rawMaterialArea = rawMaterialArea.replace(/\.{2,}/g, "."); //两个连续的"."仅保留第一个"."
            rawMaterialArea = rawMaterialArea.replace(".", "$#*").replace(/\./g, '').replace('$#*', '.');//去除其他"."
            rawMaterialArea = rawMaterialArea.replace(/^(\d+)\.(\d\d).*$/, '$1.$2');
            ;//限制只能输入两个小数
            if (rawMaterialArea.indexOf(".") < 0 && rawMaterialArea != "") { //首位是0的话去掉
                rawMaterialArea = parseFloat(rawMaterialArea);
            }
            row.oemProductionInfoParams.rawMaterialArea = rawMaterialArea;
        }
        //校验包装材料仓库面积输入是否正数
        $scope.checkPackagingAreaPositive = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            var packagingArea = row.oemProductionInfoParams.packagingArea;
            // if (packagingArea.length > 1) {
            //     packagingArea = packagingArea.replace(/^0+/,"");
            // }
            // row.oemProductionInfoParams.packagingArea = packagingArea.replace(/\D/g, '');
            packagingArea = packagingArea.replace(/[^\d.]/g, "");  //仅保留数字和"."
            packagingArea = packagingArea.replace(/\.{2,}/g, "."); //两个连续的"."仅保留第一个"."
            packagingArea = packagingArea.replace(".", "$#*").replace(/\./g, '').replace('$#*', '.');//去除其他"."
            packagingArea = packagingArea.replace(/^(\d+)\.(\d\d).*$/, '$1.$2');
            ;//限制只能输入两个小数
            if (packagingArea.indexOf(".") < 0 && packagingArea != "") { //首位是0的话去掉
                packagingArea = parseFloat(packagingArea);
            }
            row.oemProductionInfoParams.packagingArea = packagingArea;
        }
        //校验实验室面积输入是否正数
        $scope.checkLaboratoryAreaPositive = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            var laboratoryArea = row.oemProductionInfoParams.laboratoryArea;
            // if (laboratoryArea.length > 1) {
            //     laboratoryArea = laboratoryArea.replace(/^0+/,"");
            // }
            // row.oemProductionInfoParams.laboratoryArea = laboratoryArea.replace(/\D/g, '');
            laboratoryArea = laboratoryArea.replace(/[^\d.]/g, "");  //仅保留数字和"."
            laboratoryArea = laboratoryArea.replace(/\.{2,}/g, "."); //两个连续的"."仅保留第一个"."
            laboratoryArea = laboratoryArea.replace(".", "$#*").replace(/\./g, '').replace('$#*', '.');//去除其他"."
            laboratoryArea = laboratoryArea.replace(/^(\d+)\.(\d\d).*$/, '$1.$2');
            ;//限制只能输入两个小数
            if (laboratoryArea.indexOf(".") < 0 && laboratoryArea != "") { //首位是0的话去掉
                laboratoryArea = parseFloat(laboratoryArea);
            }
            row.oemProductionInfoParams.laboratoryArea = laboratoryArea;
        }
        //校验办公区占地面积输入是否正数
        $scope.checkOfficeAreaPositive = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            var officeArea = row.oemProductionInfoParams.officeArea;
            // if (officeArea.length > 1) {
            //     officeArea = officeArea.replace(/^0+/,"");
            // }
            // row.oemProductionInfoParams.officeArea = officeArea.replace(/\D/g, '');
            officeArea = officeArea.replace(/[^\d.]/g, "");  //仅保留数字和"."
            officeArea = officeArea.replace(/\.{2,}/g, "."); //两个连续的"."仅保留第一个"."
            officeArea = officeArea.replace(".", "$#*").replace(/\./g, '').replace('$#*', '.');//去除其他"."
            officeArea = officeArea.replace(/^(\d+)\.(\d\d).*$/, '$1.$2');
            ;//限制只能输入两个小数
            if (officeArea.indexOf(".") < 0 && laboratoryArea != "") { //首位是0的话去掉
                officeArea = parseFloat(officeArea);
            }
            row.oemProductionInfoParams.officeArea = officeArea;
        }

        //校验质量人员输入是否正数
        $scope.checkQualityPersonnelAmountPositive = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            var qualityPersonnelAmount = row.oemProductionInfoParams.qualityPersonnelAmount;
            if (qualityPersonnelAmount.length > 1) {
                qualityPersonnelAmount = qualityPersonnelAmount.replace(/^0+/, "");
            }
            row.oemProductionInfoParams.qualityPersonnelAmount = qualityPersonnelAmount.replace(/\D/g, '');
        }
        //校验销售人员输入是否正数
        $scope.checkSalesmanAmountPositive = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            var salesmanAmount = row.oemProductionInfoParams.salesmanAmount;
            if (salesmanAmount.length > 1) {
                salesmanAmount = salesmanAmount.replace(/^0+/, "");
            }
            row.oemProductionInfoParams.salesmanAmount = salesmanAmount.replace(/\D/g, '');
        }

        //校验生产人员输入是否正数
        $scope.checkProducersAmountPositive = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            var producersAmount = row.oemProductionInfoParams.producersAmount;
            if (producersAmount.length > 1) {
                producersAmount = producersAmount.replace(/^0+/, "");
            }
            row.oemProductionInfoParams.producersAmount = producersAmount.replace(/\D/g, '');
        }
        //校验设计与开发输入是否正数
        $scope.checkDesignerAmountPositive = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            var designerAmount = row.oemProductionInfoParams.designerAmount;
            if (designerAmount.length > 1) {
                designerAmount = designerAmount.replace(/^0+/, "");
            }
            row.oemProductionInfoParams.designerAmount = designerAmount.replace(/\D/g, '');
        }
        //校验行政人员输入是否正数
        $scope.checkAdministrativeStaffAmountPositive = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            var administrativeStaffAmount = row.oemProductionInfoParams.administrativeStaffAmount;
            if (administrativeStaffAmount.length > 1) {
                administrativeStaffAmount = administrativeStaffAmount.replace(/^0+/, "");
            }
            row.oemProductionInfoParams.administrativeStaffAmount = administrativeStaffAmount.replace(/\D/g, '');
        }
        //校验行政人员输入是否正数
        $scope.checkOtherPositive = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            var other = row.oemProductionInfoParams.other;
            if (other.length > 1) {
                other = other.replace(/^0+/, "");
            }
            row.oemProductionInfoParams.other = other.replace(/\D/g, '');
        }

        /********************计算工厂面积********************/
        $scope.calFactoryArea = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            row.oemProductionInfoParams.factoryArea = 0;
            $scope.nullConvernZero(row.oemProductionInfoParams.productionArea, row);
            $scope.nullConvernZero(row.oemProductionInfoParams.finishedProductArea, row);
            $scope.nullConvernZero(row.oemProductionInfoParams.rawMaterialArea, row);
            $scope.nullConvernZero(row.oemProductionInfoParams.packagingArea, row);
            $scope.nullConvernZero(row.oemProductionInfoParams.laboratoryArea, row);
            $scope.nullConvernZero(row.oemProductionInfoParams.officeArea, row);
        }

        /******************计算员工数量*******************/
        $scope.calEmployeesAmount = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            row.oemProductionInfoParams.employeesAmount = 0;
            $scope.nullConvernZero2(row.oemProductionInfoParams.qualityPersonnelAmount, row);
            $scope.nullConvernZero2(row.oemProductionInfoParams.salesmanAmount, row);
            $scope.nullConvernZero2(row.oemProductionInfoParams.producersAmount, row);
            $scope.nullConvernZero2(row.oemProductionInfoParams.designerAmount, row);
            $scope.nullConvernZero2(row.oemProductionInfoParams.administrativeStaffAmount, row);
            $scope.nullConvernZero2(row.oemProductionInfoParams.other, row);
        }

        /********************提交前校验********************/
        $scope.validate = function () {
            //校验业务类型是否为空
            if ($scope.program.sceneType == "" || $scope.program.sceneType == null || $scope.program.sceneType == undefined) {
                JS.alert('请选择业务类型', 'error', '确定');
                return true;
            }

            //当业务类型为【跨部门贸易供应商准入OEM】【跨部门制造工厂准入OEM】【部门内制造工厂新增品类准入OEM】【跨部门供应商准入IT】，校验资质审查单头供应商必填
            if ($scope.program.sceneType == "30" || $scope.program.sceneType == "40" || $scope.program.sceneType == "50" || $scope.program.sceneType == "70") {
                if ($scope.program.supplierId == "" || $scope.program.supplierId == null || $scope.program.supplierId == undefined) {
                    JS.alert('请选择供应商', 'error', '确定');
                    return true;
                }
            }

            //当业务类型为【全新贸易供应商准入OEM】【全新制造工厂准入OEM】【全新供应商准入IT】，校验基础信息供应商必填
            if ($scope.program.sceneType == "10" || $scope.program.sceneType == "20" || $scope.program.sceneType == "60") {
                if ($scope.supplierProgram.supplierName == "" || $scope.supplierProgram.supplierName == null || $scope.supplierProgram.supplierName == undefined) {
                    JS.alert('请填写供应商名称信息', 'error', '确定');
                    return true;
                }
            }

            //校验国家是否为空
            if ($scope.supplierProgram.country == "" || $scope.supplierProgram.country == null || $scope.supplierProgram.country == undefined) {
                JS.alert('请选择国家', 'error', '确定');
                return true;
            }

            //校验供应商类型是否为空
            if ($scope.supplierProgram.supplierType == "" || $scope.supplierProgram.supplierType == null || $scope.supplierProgram.supplierType == undefined) {
                JS.alert('请选择供应商类型', 'error', '确定');
                return true;
            }

            //校验是否存在联系人行，信息是否填写完整
            if ($scope.supplierContactDataTable.length == 0) {
                JS.alert('请添加联系人信息', 'error', '确定');
                return true;
            } else {
                var selectCount = 0;
                for (var i = 0; i < $scope.supplierContactDataTable.length; i++) {
                    if ($scope.supplierContactDataTable[i].contactName == '' || $scope.supplierContactDataTable[i].contactName == undefined) {
                        JS.alert('联系人目录填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.supplierContactDataTable[i].mobilePhone == '' || $scope.supplierContactDataTable[i].mobilePhone == undefined) {
                        JS.alert('联系人目录填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.supplierContactDataTable[i].emailAddress == '' || $scope.supplierContactDataTable[i].emailAddress == undefined) {
                        JS.alert('联系人目录填写不完整', 'error', '确定');
                        return true;
                    }
                    if ($scope.supplierContactDataTable[i].sendEmailFlag == 'Y') {
                        selectCount = selectCount + 1;
                    }
                }
                if (selectCount == 0) {
                    JS.alert('请选择需要创建账号的联系人', 'error', '确定');
                    return true;
                }
            }

            //当用户类型为60(供应商)
            if ($scope.flags.userTpye == '60') {
                //校验【主要客户】控制必填
                if ($scope.supplierProgram.majorCustomer == "" || $scope.supplierProgram.majorCustomer == null || $scope.supplierProgram.majorCustomer == undefined) {
                    JS.alert('请选择主要客户信息', 'error', '确定');
                    return true;
                }

                //校验【营业执照号】控制必填
                if ($scope.supplierCredentialsProgram.licenseNum == "" || $scope.supplierCredentialsProgram.licenseNum == null || $scope.supplierCredentialsProgram.licenseNum == undefined) {
                    JS.alert('请填写营业执照号', 'error', '确定');
                    return true;
                }

                //当【长期】没有勾选时，【营业期限】控制必填
                if ($scope.supplierCredentialsProgram.longBusinessIndate != 'Y') {
                    if ($scope.supplierCredentialsProgram.licenseIndate == "" || $scope.supplierCredentialsProgram.licenseIndate == null || $scope.supplierCredentialsProgram.licenseIndate == undefined) {
                        JS.alert('请填写营业执照号', 'error', '确定');
                        return true;
                    }
                }

                //校验【营业执照复印件】控制必填
                if ($scope.supplierCredentialsProgram.licenseFileId == "" || $scope.supplierCredentialsProgram.licenseFileId == null || $scope.supplierCredentialsProgram.licenseFileId == undefined) {
                    JS.alert('请上传营业执照复印件', 'error', '确定');
                    return true;
                }

                if ($scope.supplierCredentialsProgram.isThreeInOne != 'Y') {
                    //校验【组织机构代码号】控制必填
                    // if ($scope.supplierCredentialsProgram.tissueCode == "" || $scope.supplierCredentialsProgram.tissueCode == null || $scope.supplierCredentialsProgram.tissueCode == undefined) {
                    //     JS.alert('请填写组织机构代码号', 'error', '确定');
                    //     return true;
                    // }
                    //校验【组织机构代码到期日】控制必填
                    // if ($scope.supplierCredentialsProgram.tissueIndate == "" || $scope.supplierCredentialsProgram.tissueIndate == null || $scope.supplierCredentialsProgram.tissueIndate == undefined) {
                    //     JS.alert('请填写组织机构代码到期日', 'error', '确定');
                    //     return true;
                    // }
                    //校验【组织机构代码证】控制必填
                    // if ($scope.supplierCredentialsProgram.tissueFileId == "" || $scope.supplierCredentialsProgram.tissueFileId == null || $scope.supplierCredentialsProgram.tissueFileId == undefined) {
                    //     JS.alert('请上传组织机构代码证附件', 'error', '确定');
                    //     return true;
                    // }
                    //校验【税务登记证号】控制必填
                    // if ($scope.supplierCredentialsProgram.taxCode == "" || $scope.supplierCredentialsProgram.taxCode == null || $scope.supplierCredentialsProgram.taxCode == undefined) {
                    //     JS.alert('请填写税务登记证号', 'error', '确定');
                    //     return true;
                    // }
                    //校验【税务登记证】控制必填
                    // if ($scope.supplierCredentialsProgram.taxFileId == "" || $scope.supplierCredentialsProgram.taxFileId == null || $scope.supplierCredentialsProgram.taxFileId == undefined) {
                    //     JS.alert('请上传税务登记证附件', 'error', '确定');
                    //     return true;
                    // }
                }

                //校验【银行开户许可证号】控制必填
                if ($scope.supplierCredentialsProgram.bankPermitNumber == "" || $scope.supplierCredentialsProgram.bankPermitNumber == null || $scope.supplierCredentialsProgram.bankPermitNumber == undefined) {
                    JS.alert('请填写银行开户许可证号', 'error', '确定');
                    return true;
                }

                //校验【银行开户许可证】控制必填
                if ($scope.supplierCredentialsProgram.bankFileId == "" || $scope.supplierCredentialsProgram.bankFileId == null || $scope.supplierCredentialsProgram.bankFileId == undefined) {
                    JS.alert('请上传银行开户许可证附件', 'error', '确定');
                    return true;
                }

                //校验【一般纳税人证明】控制必填
                if ($scope.supplierCredentialsProgram.taxpayerFileId == "" || $scope.supplierCredentialsProgram.taxpayerFileId == null || $scope.supplierCredentialsProgram.taxpayerFileId == undefined) {
                    JS.alert('请上传一般纳税人证明附件', 'error', '确定');
                    return true;
                }

                //校验【法人姓名】控制必填
                if ($scope.supplierCredentialsProgram.representativeName == "" || $scope.supplierCredentialsProgram.representativeName == null || $scope.supplierCredentialsProgram.representativeName == undefined) {
                    JS.alert('请填写法人姓名', 'error', '确定');
                    return true;
                }

                //校验【经营范围】控制必填
                if ($scope.supplierCredentialsProgram.businessScope == "" || $scope.supplierCredentialsProgram.businessScope == null || $scope.supplierCredentialsProgram.businessScope == undefined) {
                    JS.alert('请填写经营范围', 'error', '确定');
                    return true;
                }

                //校验【成立日期】控制必填
                if ($scope.supplierCredentialsProgram.establishmentDate == "" || $scope.supplierCredentialsProgram.establishmentDate == null || $scope.supplierCredentialsProgram.establishmentDate == undefined) {
                    JS.alert('请填写成立日期', 'error', '确定');
                    return true;
                }

                //校验【经营年限】控制必填
                // if ($scope.supplierCredentialsProgram.businessYears == "" || $scope.supplierCredentialsProgram.businessYears == null || $scope.supplierCredentialsProgram.businessYears == undefined) {
                //     JS.alert('请填写经营年限', 'error', '确定');
                //     return true;
                // }

                //校验【注册资本】控制必填
                if ($scope.supplierCredentialsProgram.enrollCapital == "" || $scope.supplierCredentialsProgram.enrollCapital == null || $scope.supplierCredentialsProgram.enrollCapital == undefined) {
                    JS.alert('请填写注册资本', 'error', '确定');
                    return true;
                }

                //校验【股东信息】控制必填
                if ($scope.supplierCredentialsProgram.shareholderInfo == "" || $scope.supplierCredentialsProgram.shareholderInfo == null || $scope.supplierCredentialsProgram.shareholderInfo == undefined) {
                    JS.alert('请填写股东信息', 'error', '确定');
                    return true;
                }

                //校验【关联方信息】控制必填
                if ($scope.supplierCredentialsProgram.relatedParty == "" || $scope.supplierCredentialsProgram.relatedParty == null || $scope.supplierCredentialsProgram.relatedParty == undefined) {
                    JS.alert('请填写关联方信息', 'error', '确定');
                    return true;
                }
            }


            //公共
            // $scope.params.qualificationInfo = $scope.program; //资质单据
            // $scope.params.supplierBaseInfo = $scope.supplierProgram; //供应商基础信息
            // $scope.params.relatedFactoryInfo = $scope.relatedFactoryDataTable;//关联工厂
            // $scope.params.supplierContactInfo = $scope.supplierContactDataTable; //联系人
            // $scope.params.supplierBankInfo = $scope.supplierBankInfoDataTable; //银行信息
            // $scope.params.supplierCredentialsInfo = $scope.supplierCredentialsProgram; //资质信息

            //部门：OEM 用户类型：供应商  ，校验产能信息
            if ($scope.program.deptCode == '0E' && $scope.flags.userTpye == '60') {
                //当前用户类型为【供应商】时，校验产能信息
                if ($scope.oemAddressInfoDataTable.length == 0) {
                    JS.alert('请添加地址信息', 'error', '确定');
                    return true;
                } else {
                    for (var i = 0; i < $scope.oemAddressInfoDataTable.length; i++) {
                        // if ($scope.oemAddressInfoDataTable[i].addressName == '' || $scope.oemAddressInfoDataTable[i].addressName == undefined) {
                        //     JS.alert('地址信息不完整', 'error', '确定');
                        //     return true;
                        // }
                        if ($scope.oemAddressInfoDataTable[i].country == '' || $scope.oemAddressInfoDataTable[i].country == undefined) {
                            JS.alert('地址信息不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.oemAddressInfoDataTable[i].province == '' || $scope.oemAddressInfoDataTable[i].province == undefined) {
                            JS.alert('地址信息不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.oemAddressInfoDataTable[i].city == '' || $scope.oemAddressInfoDataTable[i].city == undefined) {
                            JS.alert('地址信息不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.oemAddressInfoDataTable[i].county == '' || $scope.oemAddressInfoDataTable[i].county == undefined) {
                            JS.alert('地址信息不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.oemAddressInfoDataTable[i].detailAddress == '' || $scope.oemAddressInfoDataTable[i].detailAddress == undefined) {
                            JS.alert('地址信息不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.supplierProgram.supplierType == '20') {
                            //校验产能信息-生产信息-工厂开始生产时间 是否为空
                            if ($scope.oemAddressInfoDataTable[i].oemProductionInfoParams.productionStartDate == '' || $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.productionStartDate == undefined) {
                                JS.alert('请填写工厂开始生产时间', 'error', '确定');
                                return true;
                            }
                            //校验产能信息-生产信息-生产区域面积 是否为空
                            if ($scope.oemAddressInfoDataTable[i].oemProductionInfoParams.productionArea == '' || $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.productionArea == undefined) {
                                JS.alert('请填写生产区域面积', 'error', '确定');
                                return true;
                            }
                            //校验产能信息-生产信息-成品仓库面积 是否为空
                            if ($scope.oemAddressInfoDataTable[i].oemProductionInfoParams.finishedProductArea == '' || $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.finishedProductArea == undefined) {
                                JS.alert('请填写成品仓库面积', 'error', '确定');
                                return true;
                            }
                            //校验产能信息-生产信息-原料仓库面积 是否为空
                            if ($scope.oemAddressInfoDataTable[i].oemProductionInfoParams.rawMaterialArea == '' || $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.rawMaterialArea == undefined) {
                                JS.alert('请填写原料仓库面积', 'error', '确定');
                                return true;
                            }
                            //校验产能信息-生产信息-包装材料仓库面积 是否为空
                            if ($scope.oemAddressInfoDataTable[i].oemProductionInfoParams.packagingArea == '' || $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.packagingArea == undefined) {
                                JS.alert('请填写包装材料仓库面积', 'error', '确定');
                                return true;
                            }
                            //校验产能信息-生产信息-实验室面积 是否为空
                            if ($scope.oemAddressInfoDataTable[i].oemProductionInfoParams.laboratoryArea == '' || $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.laboratoryArea == undefined) {
                                JS.alert('请填写实验室面积', 'error', '确定');
                                return true;
                            }
                            //校验产能信息-生产信息-办公区占地面积 是否为空
                            if ($scope.oemAddressInfoDataTable[i].oemProductionInfoParams.officeArea == '' || $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.officeArea == undefined) {
                                JS.alert('请填写办公区占地面积', 'error', '确定');
                                return true;
                            }
                            //校验产能信息-生产信息-质量人员 是否为空
                            if ($scope.oemAddressInfoDataTable[i].oemProductionInfoParams.qualityPersonnelAmount == '' || $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.qualityPersonnelAmount == undefined) {
                                JS.alert('请填写质量人员', 'error', '确定');
                                return true;
                            }
                            //校验产能信息-生产信息-销售人员 是否为空
                            if ($scope.oemAddressInfoDataTable[i].oemProductionInfoParams.salesmanAmount == '' || $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.salesmanAmount == undefined) {
                                JS.alert('请填写销售人员', 'error', '确定');
                                return true;
                            }
                            //校验产能信息-生产信息-生产人员	 是否为空
                            if ($scope.oemAddressInfoDataTable[i].oemProductionInfoParams.producersAmount == '' || $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.producersAmount == undefined) {
                                JS.alert('请填写生产人员', 'error', '确定');
                                return true;
                            }
                            //校验产能信息-生产信息-设计与开发	 是否为空
                            if ($scope.oemAddressInfoDataTable[i].oemProductionInfoParams.designerAmount == '' || $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.designerAmount == undefined) {
                                JS.alert('请填写设计与开发', 'error', '确定');
                                return true;
                            }
                            //校验产能信息-生产信息-行政人员	 是否为空
                            if ($scope.oemAddressInfoDataTable[i].oemProductionInfoParams.administrativeStaffAmount == '' || $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.administrativeStaffAmount == undefined) {
                                JS.alert('请填写行政人员', 'error', '确定');
                                return true;
                            }

                            //产能信息
                            if ($scope.oemAddressInfoDataTable[i].oemCapacityInfoParams.length == 0) {
                                JS.alert('请添加产能信息', 'error', '确定');
                                return true;
                            } else {
                                for (var j = 0; j < $scope.oemAddressInfoDataTable[i].oemCapacityInfoParams.length; j++) {
                                    if ($scope.oemAddressInfoDataTable[i].oemCapacityInfoParams[j].productScope == '' || $scope.oemAddressInfoDataTable[i].oemCapacityInfoParams[j].productScope == undefined) {
                                        JS.alert('产能信息填写不完整', 'error', '确定');
                                        return true;
                                    }
                                    if ($scope.oemAddressInfoDataTable[i].oemCapacityInfoParams[j].mainRawMaterials == '' || $scope.oemAddressInfoDataTable[i].oemCapacityInfoParams[j].mainRawMaterials == undefined) {
                                        JS.alert('产能信息填写不完整', 'error', '确定');
                                        return true;
                                    }
                                    if ($scope.oemAddressInfoDataTable[i].oemCapacityInfoParams[j].productionEquipment == '' || $scope.oemAddressInfoDataTable[i].oemCapacityInfoParams[j].productionEquipment == undefined) {
                                        JS.alert('产能信息填写不完整', 'error', '确定');
                                        return true;
                                    }
                                    // if ($scope.oemAddressInfoDataTable[i].oemCapacityInfoParams[j].productionCapacity == '' || $scope.oemAddressInfoDataTable[i].oemCapacityInfoParams[j].productionCapacity == undefined) {
                                    //     JS.alert('产能信息填写不完整', 'error', '确定');
                                    //     return true;
                                    // }
                                    // if ($scope.oemAddressInfoDataTable[i].oemCapacityInfoParams[j].productionCapacityFileId == '' || $scope.oemAddressInfoDataTable[i].oemCapacityInfoParams[j].productionCapacityFileId == undefined) {
                                    //     JS.alert('产能信息填写不完整', 'error', '确定');
                                    //     return true;
                                    // }
                                }
                            }
                        }
                    }
                }

                // $scope.params.oemAddressInfo = $scope.oemAddressInfoDataTable; //地址信息OEM
            }

            //OEM贸易商
            if ($scope.program.deptCode == '0E' && $scope.supplierProgram.supplierType == '10') {
                if ($scope.program.sceneType != '10' && $scope.program.sceneType != '30') {
                    JS.alert('业务类型与供应商类型不匹配，请修改!', 'error', '确定');
                    return true;
                }
            }

            //OEM制造工厂
            if ($scope.program.deptCode == '0E' && $scope.supplierProgram.supplierType == '20') {
                if ($scope.supplierCopCategoryDataTable.length == 0) {
                    JS.alert('请添加可合作品类', 'error', '确定');
                    return true;
                } else {
                    for (var i = 0; i < $scope.supplierCopCategoryDataTable.length; i++) {
                        if ($scope.supplierCopCategoryDataTable[i].categoryName == '' || $scope.supplierCopCategoryDataTable[i].categoryName == undefined) {
                            JS.alert('可合作品类填写不完整', 'error', '确定');
                            return true;
                        }
                    }
                }

                if ($scope.program.sceneType != '20' && $scope.program.sceneType != '40' && $scope.program.sceneType != '50') {
                    JS.alert('业务类型与供应商类型不匹配，请修改!', 'error', '确定');
                    return true;
                }

                // $scope.params.copCategoryInfo = $scope.supplierCopCategoryDataTable; //合作品类
                // $scope.params.productQualificationsInfo = $scope.productQualificationTable;//生产资质信息
                // $scope.params.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
            }

            //IT
            if ($scope.program.deptCode == 'IT') {
                //校验银行信息是否填写完整
                if ($scope.supplierBankInfoDataTable.length == 0) {
                    JS.alert('请添加银行信息行', 'error', '确定');
                    return true;
                } else {
                    for (var i = 0; i < $scope.supplierBankInfoDataTable.length; i++) {
                        if ($scope.supplierBankInfoDataTable[i].bankUserName == '' || $scope.supplierBankInfoDataTable[i].bankUserName == undefined) {
                            JS.alert('银行信息填写不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.supplierBankInfoDataTable[i].bankAccountNumber == '' || $scope.supplierBankInfoDataTable[i].bankAccountNumber == undefined) {
                            JS.alert('银行信息填写不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.supplierBankInfoDataTable[i].bankName == '' || $scope.supplierBankInfoDataTable[i].bankName == undefined) {
                            JS.alert('银行信息填写不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.supplierBankInfoDataTable[i].bankCurrency == '' || $scope.supplierBankInfoDataTable[i].bankCurrency == undefined) {
                            JS.alert('银行信息填写不完整', 'error', '确定');
                            return true;
                        }
                    }
                }

                if ($scope.supplierServiceScopeDataTable.length == 0) {
                    JS.alert('请添加服务范围', 'error', '确定');
                    return true;
                } else {
                    for (var i = 0; i < $scope.supplierServiceScopeDataTable.length; i++) {
                        if ($scope.supplierServiceScopeDataTable[i].categoryName == '' || $scope.supplierServiceScopeDataTable[i].categoryName == undefined) {
                            JS.alert('服务范围填写不完整', 'error', '确定');
                            return true;
                        }
                    }
                }
                // $scope.params.serviceScopeInfo = $scope.supplierServiceScopeDataTable; //服务范围
                // $scope.params.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
                // $scope.params.itAddressInfo = $scope.itAddressInfoDataTable; //地址信息IT
            }

            //部门：IT 用户类型：供应商  ，校验具体经营状况及现场照片
            if ($scope.program.deptCode == 'IT' && $scope.flags.userTpye == '60') {
                if ($scope.itAddressInfoDataTable.length == 0) {
                    JS.alert('请添加地址信息', 'error', '确定');
                    return true;
                } else {
                    for (var i = 0; i < $scope.itAddressInfoDataTable.length; i++) {
                        // if ($scope.itAddressInfoDataTable[i].addressName == '' || $scope.itAddressInfoDataTable[i].addressName == undefined) {
                        //     JS.alert('地址信息不完整', 'error', '确定');
                        //     return true;
                        // }
                        if ($scope.itAddressInfoDataTable[i].country == '' || $scope.itAddressInfoDataTable[i].country == undefined) {
                            JS.alert('地址信息不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.itAddressInfoDataTable[i].province == '' || $scope.itAddressInfoDataTable[i].province == undefined) {
                            JS.alert('地址信息不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.itAddressInfoDataTable[i].city == '' || $scope.itAddressInfoDataTable[i].city == undefined) {
                            JS.alert('地址信息不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.itAddressInfoDataTable[i].county == '' || $scope.itAddressInfoDataTable[i].county == undefined) {
                            JS.alert('地址信息不完整', 'error', '确定');
                            return true;
                        }
                        if ($scope.itAddressInfoDataTable[i].detailAddress == '' || $scope.itAddressInfoDataTable[i].detailAddress == undefined) {
                            JS.alert('地址信息不完整', 'error', '确定');
                            return true;
                        }
                        //校验具体经营状况及现场照片-经营状况-周边环境 是否为空
                        if ($scope.itAddressInfoDataTable[i].itOperationalInfoParams.surroundingEnvironment == '' || $scope.itAddressInfoDataTable[i].itOperationalInfoParams.surroundingEnvironment == undefined) {
                            JS.alert('请填写周边环境内容', 'error', '确定');
                            return true;
                        }
                        //校验具体经营状况及现场照片-经营状况-周边环境附件 是否为空
                        if ($scope.itAddressInfoDataTable[i].itOperationalInfoParams.surEnvironmentFileId == '' || $scope.itAddressInfoDataTable[i].itOperationalInfoParams.surEnvironmentFileId == undefined) {
                            JS.alert('请上传周边环境附件', 'error', '确定');
                            return true;
                        }
                        //校验具体经营状况及现场照片-经营状况-大门门牌 是否为空
                        if ($scope.itAddressInfoDataTable[i].itOperationalInfoParams.doorPlate == '' || $scope.itAddressInfoDataTable[i].itOperationalInfoParams.doorPlate == undefined) {
                            JS.alert('请填写大门门牌内容', 'error', '确定');
                            return true;
                        }
                        //校验具体经营状况及现场照片-经营状况-大门门牌附件 是否为空
                        if ($scope.itAddressInfoDataTable[i].itOperationalInfoParams.doorPlateFileId == '' || $scope.itAddressInfoDataTable[i].itOperationalInfoParams.doorPlateFileId == undefined) {
                            JS.alert('请上传大门门牌附件', 'error', '确定');
                            return true;
                        }
                        //校验具体经营状况及现场照片-经营状况-前台 是否为空
                        if ($scope.itAddressInfoDataTable[i].itOperationalInfoParams.reception == '' || $scope.itAddressInfoDataTable[i].itOperationalInfoParams.reception == undefined) {
                            JS.alert('请填写前台内容', 'error', '确定');
                            return true;
                        }
                        //校验具体经营状况及现场照片-经营状况-前台附件 是否为空
                        if ($scope.itAddressInfoDataTable[i].itOperationalInfoParams.receptionFileId == '' || $scope.itAddressInfoDataTable[i].itOperationalInfoParams.receptionFileId == undefined) {
                            JS.alert('请上传前台附件', 'error', '确定');
                            return true;
                        }
                        //校验具体经营状况及现场照片-经营状况-公司面积 是否为空
                        if ($scope.itAddressInfoDataTable[i].itOperationalInfoParams.companyArea == '' || $scope.itAddressInfoDataTable[i].itOperationalInfoParams.companyArea == undefined) {
                            JS.alert('请填写公司面积内容', 'error', '确定');
                            return true;
                        }
                        //校验具体经营状况及现场照片-经营状况-公司面积附件 是否为空
                        if ($scope.itAddressInfoDataTable[i].itOperationalInfoParams.companyAreaFileId == '' || $scope.itAddressInfoDataTable[i].itOperationalInfoParams.companyAreaFileId == undefined) {
                            JS.alert('请上传公司面积附件', 'error', '确定');
                            return true;
                        }
                        //校验具体经营状况及现场照片-经营状况-办公场所 是否为空
                        if ($scope.itAddressInfoDataTable[i].itOperationalInfoParams.officeSpace == '' || $scope.itAddressInfoDataTable[i].itOperationalInfoParams.officeSpace == undefined) {
                            JS.alert('请填写办公场所内容', 'error', '确定');
                            return true;
                        }
                        //校验具体经营状况及现场照片-经营状况-办公场所附件 是否为空
                        if ($scope.itAddressInfoDataTable[i].itOperationalInfoParams.officeSpaceFileId == '' || $scope.itAddressInfoDataTable[i].itOperationalInfoParams.officeSpaceFileId == undefined) {
                            JS.alert('请上传办公场所附件', 'error', '确定');
                            return true;
                        }
                        //校验具体经营状况及现场照片-经营状况-员工概况 是否为空
                        if ($scope.itAddressInfoDataTable[i].itOperationalInfoParams.employeeProfile == '' || $scope.itAddressInfoDataTable[i].itOperationalInfoParams.employeeProfile == undefined) {
                            JS.alert('请填写员工概况内容', 'error', '确定');
                            return true;
                        }
                        //校验具体经营状况及现场照片-经营状况-员工概况附件 是否为空
                        if ($scope.itAddressInfoDataTable[i].itOperationalInfoParams.employeeProfileFileId == '' || $scope.itAddressInfoDataTable[i].itOperationalInfoParams.employeeProfileFileId == undefined) {
                            JS.alert('请上传员工概况附件', 'error', '确定');
                            return true;
                        }

                        //产能信息
                        if ($scope.supplierProgram.supplierType == '90' && $scope.itAddressInfoDataTable[i].itCapacityInfoParams.length == 0) {
                            JS.alert('请添加产能信息', 'error', '确定');
                            return true;
                        } else {
                            for (var j = 0; j < $scope.itAddressInfoDataTable[i].itCapacityInfoParams.length; j++) {
                                if ($scope.itAddressInfoDataTable[i].itCapacityInfoParams[j].productScope == '' || $scope.itAddressInfoDataTable[i].itCapacityInfoParams[j].productScope == undefined) {
                                    JS.alert('产能信息填写不完整', 'error', '确定');
                                    return true;
                                }
                                if ($scope.itAddressInfoDataTable[i].itCapacityInfoParams[j].mainRawMaterials == '' || $scope.itAddressInfoDataTable[i].itCapacityInfoParams[j].mainRawMaterials == undefined) {
                                    JS.alert('产能信息填写不完整', 'error', '确定');
                                    return true;
                                }
                                if ($scope.itAddressInfoDataTable[i].itCapacityInfoParams[j].productionEquipment == '' || $scope.itAddressInfoDataTable[i].itCapacityInfoParams[j].productionEquipment == undefined) {
                                    JS.alert('产能信息填写不完整', 'error', '确定');
                                    return true;
                                }
                                // if ($scope.itAddressInfoDataTable[i].itCapacityInfoParams[j].productionCapacity == '' || $scope.itAddressInfoDataTable[i].itCapacityInfoParams[j].productionCapacity == undefined) {
                                //     JS.alert('产能信息填写不完整', 'error', '确定');
                                //     return true;
                                // }
                                // if ($scope.itAddressInfoDataTable[i].itCapacityInfoParams[j].productionCapacityFileId == '' || $scope.itAddressInfoDataTable[i].itCapacityInfoParams[j].productionCapacityFileId == undefined) {
                                //     JS.alert('产能信息填写不完整', 'error', '确定');
                                //     return true;
                                // }
                            }
                        }
                    }
                }
            }
        }

        /********************保存资质审查单据信息********************/
        $scope.btnSave = function () {
            //校验
            // if ($scope.validate()) {
            //     return;
            // }
            //公共
            $scope.params.qualificationInfo = $scope.program; //资质单据
            $scope.params.supplierBaseInfo = $scope.supplierProgram; //供应商基础信息
            $scope.params.relatedFactoryInfo = $scope.relatedFactoryDataTable;//关联工厂
            $scope.params.supplierContactInfo = $scope.supplierContactDataTable; //联系人
            $scope.params.supplierBankInfo = $scope.supplierBankInfoDataTable; //银行信息
            $scope.params.supplierCredentialsInfo = $scope.supplierCredentialsProgram; //资质信息

            //OEM
            if ($scope.program.deptCode == '0E') {
                $scope.params.oemAddressInfo = $scope.oemAddressInfoDataTable; //地址信息OEM
            }

            //OEM贸易商
            if ($scope.program.deptCode == '0E' && $scope.supplierProgram.supplierType == '10') {
                $scope.params.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
            }

            //OEM制造工厂
            if ($scope.program.deptCode == '0E' && $scope.supplierProgram.supplierType == '20') {
                $scope.params.copCategoryInfo = $scope.supplierCopCategoryDataTable; //合作品类
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
                $scope.params.productQualificationsInfo = $scope.productQualificationTable;//生产资质信息
            }

            //IT
            if ($scope.program.deptCode == 'IT') {
                $scope.params.serviceScopeInfo = $scope.supplierServiceScopeDataTable; //服务范围
                $scope.params.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
                $scope.params.itAddressInfo = $scope.itAddressInfoDataTable; //地址信息IT
            }

            httpServer.post(URLAPI.saveQualificationInfo, {
                'params': JSON.stringify($scope.params)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.program = res.data;
                    $scope.search($scope.program.qualificationId);
                    if($scope.program.qualificationStatus != '40'){
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

        $scope.reject = function () {
            $("#rejectionFormModal").modal('show');
            // $scope.btnSubmit1("reject");
        }

        $scope.rejectConfirm = function () {
            //校验驳回原因必填
            if ($scope.program.rejectionReason == '' || $scope.program.rejectionReason == undefined) {
                JS.alert('请填写驳回原因！', 'error', '确定');
                return false;
            }
            $("#rejectionFormModal").modal('hide');
            $scope.btnSubmit1("reject");
        }

        $scope.btnSubmit = function () {
            if ($scope.validate()) {
                return;
            }
            if ($scope.program.qualificationStatus == '10' || $scope.program.qualificationStatus == '20') {
                //提交供应商填资料
                $scope.btnSubmit1();
            } else if ($scope.program.qualificationStatus == '30' || $scope.program.qualificationStatus == '60') {
                //公共
                $scope.params.qualificationInfo = $scope.program; //资质单据
                $scope.params.supplierBaseInfo = $scope.supplierProgram; //供应商基础信息
                $scope.params.relatedFactoryInfo = $scope.relatedFactoryDataTable;//关联工厂
                $scope.params.supplierContactInfo = $scope.supplierContactDataTable; //联系人
                $scope.params.supplierBankInfo = $scope.supplierBankInfoDataTable; //银行信息
                $scope.params.supplierCredentialsInfo = $scope.supplierCredentialsProgram; //资质信息

                //OEM
                if ($scope.program.deptCode == '0E') {
                    $scope.params.oemAddressInfo = $scope.oemAddressInfoDataTable; //地址信息OEM
                }

                //OEM贸易商
                if ($scope.program.deptCode == '0E' && $scope.supplierProgram.supplierType == '10') {
                    $scope.params.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
                }

                //OEM制造工厂
                if ($scope.program.deptCode == '0E' && $scope.supplierProgram.supplierType == '20') {
                    $scope.params.copCategoryInfo = $scope.supplierCopCategoryDataTable; //合作品类
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
                    $scope.params.productQualificationsInfo = $scope.productQualificationTable;//生产资质信息
                }

                //IT
                if ($scope.program.deptCode == 'IT') {
                    $scope.params.serviceScopeInfo = $scope.supplierServiceScopeDataTable; //服务范围
                    $scope.params.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
                    $scope.params.itAddressInfo = $scope.itAddressInfoDataTable; //地址信息IT
                }

                httpServer.post(URLAPI.saveQualificationInfo, {
                    'params': JSON.stringify($scope.params)
                }, function (res) {
                    if ('S' == res.status) {
                        $scope.program = res.data;
                        $scope.search($scope.program.qualificationId);
                        //提交流程
                        $scope.btnSubmit2();
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    console.error(error);
                    JS.alert('保存失败', 'error', '确定');
                })
            }
        }

        /********************提交资质审核单据，创建供应商账号，修改单据状态为已提交********************/
        $scope.btnSubmit1 = function (value) {
            //公共
            $scope.params.qualificationInfo = $scope.program; //资质单据
            $scope.params.supplierBaseInfo = $scope.supplierProgram; //供应商基础信息
            $scope.params.relatedFactoryInfo = $scope.relatedFactoryDataTable;//关联工厂
            $scope.params.supplierContactInfo = $scope.supplierContactDataTable; //联系人
            $scope.params.supplierBankInfo = $scope.supplierBankInfoDataTable; //银行信息
            $scope.params.supplierCredentialsInfo = $scope.supplierCredentialsProgram; //资质信息

            //OEM
            if ($scope.program.deptCode == '0E') {
                $scope.params.oemAddressInfo = $scope.oemAddressInfoDataTable; //地址信息OEM
            }

            //OEM制造工厂
            if ($scope.program.deptCode == '0E' && $scope.supplierProgram.supplierType == '20') {

                $scope.params.copCategoryInfo = $scope.supplierCopCategoryDataTable; //合作品类
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
                $scope.params.productQualificationsInfo = $scope.productQualificationTable;//生产资质信息

                $scope.params.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
            }

            //IT
            if ($scope.program.deptCode == 'IT') {
                $scope.params.serviceScopeInfo = $scope.supplierServiceScopeDataTable; //服务范围
                $scope.params.qualificationsFileInfo = $scope.qualificationsFileDataTable; //资质文件
                $scope.params.itAddressInfo = $scope.itAddressInfoDataTable; //地址信息IT
            }

            httpServer.post(URLAPI.submitQualificationInfo, {
                'params': JSON.stringify($scope.params)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.program = res.data;
                    $scope.search($scope.program.qualificationId);
                    if (value == 'reject') {
                        JS.alert('驳回成功');
                    } else {
                        JS.alert('提交成功');
                    }
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                if (value == 'reject') {
                    JS.alert('驳回失败', 'error', '确定');
                } else {
                    JS.alert('提交失败', 'error', '确定');
                }
            })
        }

        /********************取消资质审核单据********************/
        $scope.btnCancel = function () {
            JS.confirm('取消', '确定取消资质审查？', '确定', function () {
                httpServer.post(URLAPI.cancelQualificationInfo, {
                    'params': JSON.stringify($scope.program)
                }, function (res) {
                    if ('S' == res.status) {
                        $scope.program = res.data;
                        $scope.search($scope.program.qualificationId);
                        JS.alert('取消成功');
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    console.error(error);
                    JS.alert('取消失败', 'error', '确定');
                })
            })
        }

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
            $scope.supplierContactDataTable.push(contactDetailArray);
        };

        //新增可合作品类
        $scope.addCopCategoryInfo = function () {
            var copCategoryArray = {
                supplierId: "",
                categoryId: "",
                status: "APPROVING",
                statusMeaning: "准入中",
                deptCode: $scope.program.deptCode
            };
            $scope.supplierCopCategoryDataTable.push(copCategoryArray);
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
            $scope.supplierServiceScopeDataTable.push(serviceScopeArray);
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
                invalidDate: "",
                attachmentName: "",
                isProductFactory: "Y",
                fileType: "other",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.otherDataTable.push(qualificationsFileArray);
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
            $scope.productionPermitDataTable.push(qualificationsFileArray);
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
            $scope.qualitySafetyDataTable.push(qualificationsFileArray);
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
            $scope.csrReportDataTable.push(qualificationsFileArray);
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
            $scope.fireAcceptanceDataTable.push(qualificationsFileArray);
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
            $scope.projectAcceptanceDataTable.push(qualificationsFileArray);
        }

        $scope.uploadProductionPermitFile = function () {
            var row = $scope.productionPermitDataTable[$scope.productionPermitDataTable.selectRow];
            var file = document.querySelector('#productionPermitFile' + row.index).files[0];
            $scope.uploadSczzFile(file, row);
        }

        $scope.uploadQualitySafetyFile = function () {
            var row = $scope.qualitySafetyDataTable[$scope.qualitySafetyDataTable.selectRow];
            var file = document.querySelector('#qualitySafetyFile' + row.index).files[0];
            $scope.uploadSczzFile(file, row);
        }

        $scope.uploadCsrReportFile = function () {
            var row = $scope.csrReportDataTable[$scope.csrReportDataTable.selectRow];
            var file = document.querySelector('#csrReportFile' + row.index).files[0];
            $scope.uploadSczzFile(file, row);
        }

        $scope.uploadFireAcceptanceFile = function () {
            var row = $scope.fireAcceptanceDataTable[$scope.fireAcceptanceDataTable.selectRow];
            var file = document.querySelector('#fireAcceptanceFile' + row.index).files[0];
            $scope.uploadSczzFile(file, row);
        }

        $scope.uploadProjectAcceptanceFile = function () {
            var row = $scope.projectAcceptanceDataTable[$scope.projectAcceptanceDataTable.selectRow];
            var file = document.querySelector('#projectAcceptanceFile' + row.index).files[0];
            $scope.uploadSczzFile(file, row);
        }

        $scope.uploadOtherFile = function () {
            var row = $scope.otherDataTable[$scope.otherDataTable.selectRow];
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
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.productionPermitDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.productionPermitDataTable.splice(index, 1);
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
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.qualitySafetyDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.qualitySafetyDataTable.splice(index, 1);
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
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.csrReportDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.csrReportDataTable.splice(index, 1);
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
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.fireAcceptanceDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.fireAcceptanceDataTable.splice(index, 1);
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
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.projectAcceptanceDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.projectAcceptanceDataTable.splice(index, 1);
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
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.otherDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.otherDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
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
                invalidDate: "",
                attachmentName: "",
                isProductFactory: "N",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.qualificationsFileDataTable.push(qualificationsFileArray);
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
            $scope.supplierBankInfoDataTable.push(bankDetailArray);
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
                deptCode: $scope.program.deptCode,
                oemProductionInfoParams: {},
                oemCapacityInfoParams: []
            };
            $scope.oemAddressInfoDataTable.push(addressArray);
            $scope.updateDate();
            $scope.updateOemCapacityInfo();
        };

        //更新生产信息
        $scope.updateDate = function () {
            if ($scope.oemAddressInfoDataTable.length == 0) {
                JS.alert('请先维护地址行信息！', 'error', '确定');
                $scope.oemProductionInfoParams = {};
                return false;
            }
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            if (row.oemProductionInfoParams == undefined) {
                JS.alert('请先选中地址行信息！', 'error', '确定');
                $scope.oemProductionInfoParams = {};
                return false;
            }
            row.oemProductionInfoParams = $scope.oemProductionInfoParams;
        }

        //更新产能信息
        $scope.updateOemCapacityInfo = function () {
            var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
            row.oemCapacityInfoParams = $scope.oemCapacityInfoDataTable;

        }

        //选中地址行时，加载关联行数据
        $scope.loadLinesData = function (row, $index) {
            if(row != null && row != undefined){
                if (row.oemProductionInfoParams != undefined) {
                    $scope.oemProductionInfoParams = row.oemProductionInfoParams;
                } else {
                    row.oemProductionInfoParams = {};
                }
                if (row.oemCapacityInfoParams != undefined) {
                    $scope.oemCapacityInfoDataTable = row.oemCapacityInfoParams;
                } else {
                    row.oemCapacityInfoParams = [];
                }
            }
        }

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
            if ($scope.oemAddressInfoDataTable.length == 0) {
                JS.alert('请先维护地址行信息！', 'error', '确定');
                $scope.oemProductionInfoParams = {};
                return false;
            }
            var CapacityArray = {
                productScope: "",
                mainRawMaterials: "",
                productionEquipment: "",
                productionCapacity: "",
                productionCapacityFileId: "",
                productionCapacityFilePath: "",
                productionCapacityFileName: "",
                remark: "",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.oemCapacityInfoDataTable.push(CapacityArray);
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
                deptCode: $scope.program.deptCode,
                itOperationalInfoParams: {},
                itCapacityInfoParams: [],
                surEnvironmentDataTable: [],
                doorPlateDataTable: [],
                receptionDataTable: [],
                companyAreaDataTable: [],
                officeSpaceDataTable: [],
                employeeProfileDataTable: [],
            };
            $scope.itAddressInfoDataTable.push(addressArray);
            $scope.updateITDate();
            $scope.updateITCapacityInfo();
        };

        //更新经营状况
        $scope.updateITDate = function () {
            if ($scope.itAddressInfoDataTable.length == 0) {
                JS.alert('请先维护地址行信息！', 'error', '确定');
                $scope.itOperationalInfoParams = {};
                return false;
            }
            var row = $scope.itAddressInfoDataTable[$scope.itAddressInfoDataTable.selectRow];
            row.itOperationalInfoParams = $scope.itOperationalInfoParams;
        }

        //更新产能信息
        $scope.updateITCapacityInfo = function () {
            var row = $scope.itAddressInfoDataTable[$scope.itAddressInfoDataTable.selectRow];
            row.itCapacityInfoParams = $scope.itCapacityInfoDataTable;

        }

        //选中地址行时，加载关联行数据
        $scope.loadITLinesData = function (row, $index) {
            if(row != null && row != undefined){
                $scope.itOperationalInfoParams = row.itOperationalInfoParams;
                $scope.itCapacityInfoDataTable = row.itCapacityInfoParams;
                $scope.surEnvironmentDataTable = row.surEnvironmentDataTable;
                $scope.doorPlateDataTable = row.doorPlateDataTable;
                $scope.receptionDataTable = row.receptionDataTable;
                $scope.companyAreaDataTable = row.companyAreaDataTable;
                $scope.officeSpaceDataTable = row.officeSpaceDataTable;
                $scope.employeeProfileDataTable = row.employeeProfileDataTable;
            }
        }

        //产能信息(IT)
        $scope.addITCapacityInfo = function () {
            if ($scope.itAddressInfoDataTable.length == 0) {
                JS.alert('请先维护地址行信息！', 'error', '确定');
                $scope.itOperationalInfoParams = {};
                return false;
            }
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

        //业务类型修改触发事件
        $scope.sceneTypeChangeEvent = function () {
            $scope.program.supplierName = undefined;
            $scope.program.supplierNumber = undefined;
            $scope.program.supplierId = undefined;
            $scope.supplierProgram = {};

            //根据当前业务类型，修改供应商类型
            if ($scope.program.sceneType == '10' || $scope.program.sceneType == '30') {
                $scope.supplierProgram.supplierType = '10';
            } else if ($scope.program.sceneType == '20' || $scope.program.sceneType == '40' || $scope.program.sceneType == '50') {
                $scope.supplierProgram.supplierType = '20';
            }
        }

        //可合作品类行-选择品类
        $scope.getCategory = function (value) {
            console.log($scope.newDataTable.selectRowList);
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
            console.log(rows);
            if (rows.length == 0) {
                JS.alert('请选择', 'error', '确定');
                return;
            }

            if ($scope.program.deptCode == '0E') {
                var row = $scope.supplierCopCategoryDataTable[$scope.supplierCopCategoryDataTable.selectRow];
                if (rows.length > 0) {
                    row.categoryId = rows[0].categoryMaintainId;
                    row.categoryName = rows[0].categoryGroup;

                }
                debugger;
                for (var i = 1; i < rows.length; i++) {
                    var copCategoryArray = {
                        supplierId: "",
                        categoryId: rows[i].categoryMaintainId,
                        categoryName: rows[i].categoryGroup,
                        status: "APPROVING",
                        statusMeaning: "准入中",
                        deptCode: $scope.program.deptCode
                    };
                    $scope.supplierCopCategoryDataTable.push(copCategoryArray);
                }
            }

            if ($scope.program.deptCode == 'IT') {
                var row = $scope.supplierServiceScopeDataTable[$scope.supplierServiceScopeDataTable.selectRow];
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
                    $scope.supplierServiceScopeDataTable.push(copCategoryArray);
                }
            }
            $('#selectCategoryLov').modal('toggle');
        }

        // //可合作品类行-选择品类-回调
        // $scope.selectCatetoryReturn = function (key, value, currentList) {
        //     var row = $scope.supplierCopCategoryDataTable[$scope.supplierCopCategoryDataTable.selectRow];
        //     row.categoryId = currentList[0].categoryMaintainId;
        //     row.categoryName = currentList[0].categoryName;
        // }

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

        //服务范围行-选择服务范围
        $scope.getServiceScope = function (value) {
            $scope.selectServiceScopeParams = {
                "deptCode": $scope.program.deptCode
            };
            $('#selectServiceScopeLov').modal('toggle')
        };

        //服务范围行-选择服务范围-回调
        $scope.selectServiceScopeReturn = function (key, value, currentList) {
            var row = $scope.supplierServiceScopeDataTable[$scope.supplierServiceScopeDataTable.selectRow];
            row.categoryId = currentList[0].categoryMaintainId;
            row.categoryName = currentList[0].categoryGroup;
        }

        //选择供应商
        $scope.selectSupplierInfo = function () {
            var deptCode = $scope.program.deptCode;
            $scope.selectSupplierParams = {};
            //业务类型为跨部门**，则不能查询本部门供应商，且需要排除已经通过跨部门引入的供应商
            if ($scope.program.sceneType == '30' || $scope.program.sceneType == '40' || $scope.program.sceneType == '70') {
                if ($scope.program.deptCode == 'IT') {
                    deptCode = "0E";
                    $scope.selectSupplierParams.excludeDeptCode = 'IT';
                } else if ($scope.program.deptCode == '0E') {
                    deptCode = "IT";
                    $scope.selectSupplierParams.excludeDeptCode = '0E';
                }
            }
            if ($scope.program.sceneType == '50') {
                $scope.selectSupplierParams.supplierType = '20';
            }
            $scope.selectSupplierParams.deptCode = deptCode;
            $('#selectSupplierLov').modal('toggle')
        }

        //选择供应商-回调
        $scope.selectSupplierReturn = function (key, value, currentList) {
            var deptCode = $scope.program.deptCode;
            if ($scope.program.sceneType == '30' || $scope.program.sceneType == '40' || $scope.program.sceneType == '70') {
                if ($scope.program.deptCode == 'IT') {
                    deptCode = "0E";
                } else if ($scope.program.deptCode == '0E') {
                    deptCode = "IT";
                }
            }
            $scope.program.supplierName = currentList[0].supplierName;
            $scope.program.supplierNumber = currentList[0].supplierNumber;
            $scope.program.supplierId = currentList[0].supplierId;
            // $scope.searchBaseInfo2($scope.program.supplierId);
            // $scope.searchCredentialsInfo2($scope.program.supplierId,deptCode);
            // $scope.searchBankInfo2($scope.program.supplierId,deptCode);
            //========================================================================================//

            if ($scope.program.sceneType == '50') {
                $scope.searchBaseInfo2($scope.program.supplierId);
                $scope.searchCredentialsInfo2($scope.program.supplierId, deptCode);
                $scope.searchBankInfo2($scope.program.supplierId, deptCode);
                $scope.searchRelatedFactoryInfo2($scope.program.supplierId, deptCode);
                $scope.searchCategoryInfo2($scope.program.supplierId, deptCode);
                // $scope.searchServiceScope2($scope.program.supplierId,deptCode);
                $scope.searchContacterInfo2($scope.program.supplierId, deptCode);
                $scope.searchProductQualificationsInfo11($scope.program.supplierId, deptCode);
                $scope.searchProductQualificationsInfo22($scope.program.supplierId, deptCode);
                $scope.searchProductQualificationsInfo33($scope.program.supplierId, deptCode);
                $scope.searchProductQualificationsInfo44($scope.program.supplierId, deptCode);
                $scope.searchProductQualificationsInfo55($scope.program.supplierId, deptCode);
                $scope.searchProductQualificationsInfo66($scope.program.supplierId, deptCode);
                $scope.searchProductQualificationsInfo77($scope.program.supplierId, deptCode);
                // $scope.searchQualificationsFileInfo2($scope.program.supplierId,deptCode);
                $scope.searchOemAddressInfo2($scope.program.supplierId, deptCode);
                // $scope.searchItAddressInfo2($scope.program.supplierId,deptCode);
            } else {
                $scope.searchBaseInfo2($scope.program.supplierId);
                $scope.searchCredentialsInfo2($scope.program.supplierId, deptCode);
                $scope.searchBankInfo2($scope.program.supplierId, deptCode);
            }
        }

        /********************查询供应商档案基础信息**********************/
        $scope.searchBaseInfo2 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findSupplierInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    deptCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierProgram = res.data[0];
                    //根据当前业务类型，修改供应商类型
                    if ($scope.program.sceneType == '10' || $scope.program.sceneType == '30') {
                        $scope.supplierProgram.supplierType = '10';
                    } else if ($scope.program.sceneType == '20' || $scope.program.sceneType == '40' || $scope.program.sceneType == '50') {
                        $scope.supplierProgram.supplierType = '20';
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商关联工厂**********************/
        $scope.searchRelatedFactoryInfo2 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findZzscAssociatedSupplier, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    deptCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.relatedFactoryDataTable = res.data;
                    // for(var i = 0; i < $scope.relatedFactoryDataTable.length; i++){
                    //     $scope.relatedFactoryDataTable[i].associatedId = undefined;
                    // }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商品类信息**********************/
        $scope.searchCategoryInfo2 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findZzscSupplierCategorysInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    deptCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierCopCategoryDataTable = res.data;
                    // for(var i = 0; i < $scope.supplierCopCategoryDataTable.length; i++){
                    //     $scope.supplierCopCategoryDataTable[i].supplierCategoryId = undefined;
                    // }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商服务范围**********************/
        // $scope.searchServiceScope2 = function (supplierId,deptCode) {
        //     httpServer.post(URLAPI.findZzscSupplierCategorysInfo, {
        //         params: JSON.stringify({
        //             supplierId: supplierId,
        //             deptCode: deptCode
        //         })
        //     }, function (res) {
        //         if (res.status == 'S') {
        //             $scope.supplierServiceScopeDataTable = res.data;
        //             for(var i = 0; i < $scope.supplierServiceScopeDataTable.length; i++){
        //                 $scope.supplierServiceScopeDataTable[i].supplierCategoryId = undefined;
        //             }
        //         }
        //     }, function (error) {
        //         console.error(error)
        //     })
        // };

        /********************查询供应商联系人信息**********************/
        $scope.searchContacterInfo2 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findZzscSupplierContactsInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    deptCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierContactDataTable = res.data;
                    // for(var i = 0; i < $scope.supplierContactDataTable.length; i++){
                    //     $scope.supplierContactDataTable[i].supplierContactId = undefined;
                    // }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商档案银行信息**********************/
        $scope.searchBankInfo2 = function (supplierId) {
            httpServer.post(URLAPI.findZzscSupplierBankInfo, {
                params: JSON.stringify({
                    supplierId: supplierId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierBankInfoDataTable = res.data;
                    // for(var i = 0; i < $scope.supplierBankInfoDataTable.length; i++){
                    //     $scope.supplierBankInfoDataTable[i].bankAccountId = undefined;
                    // }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商档案资质信息**********************/
        $scope.searchCredentialsInfo2 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findZzscSupplierCredentialsInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    deptCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierCredentialsProgram = res.data[0];
                    $scope.supplierCredentialsProgram.deptCode = $scope.program.deptCode;
                    if ($scope.program.sceneType != '50') {
                        $scope.supplierCredentialsProgram.credentialsId = undefined;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商生产资质信息**********************/
        $scope.searchProductQualificationsInfo11 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    isProductFactory: 'Y',
                    fileType: 'FIXED',
                    deptmentCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.productQualificationTable1 = res.data[0];
                        $scope.productQualificationTable2 = res.data[1];
                        $scope.productQualificationTable3 = res.data[2];
                        $scope.productQualificationTable4 = res.data[3];
                        $scope.productQualificationTable5 = res.data[4];

                        // $scope.productQualificationTable1.attachmentId = undefined;
                        // $scope.productQualificationTable2.attachmentId = undefined;
                        // $scope.productQualificationTable3.attachmentId = undefined;
                        // $scope.productQualificationTable4.attachmentId = undefined;
                        // $scope.productQualificationTable5.attachmentId = undefined;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo22 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    isProductFactory: 'Y',
                    fileType: 'productionPermit',
                    deptmentCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.productionPermitDataTable = res.data;
                        // for(var i = 0; i < $scope.productionPermitDataTable.length; i++){
                        //     $scope.productionPermitDataTable[i].attachmentId = undefined;
                        // }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo33 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    isProductFactory: 'Y',
                    fileType: 'qualitySafety',
                    deptmentCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.qualitySafetyDataTable = res.data;
                        // for(var i = 0; i < $scope.qualitySafetyDataTable.length; i++){
                        //     $scope.qualitySafetyDataTable[i].attachmentId = undefined;
                        // }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo44 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    isProductFactory: 'Y',
                    fileType: 'csrReport',
                    deptmentCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.csrReportDataTable = res.data;
                        // for(var i = 0; i < $scope.csrReportDataTable.length; i++){
                        //     $scope.csrReportDataTable[i].attachmentId = undefined;
                        // }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo55 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    isProductFactory: 'Y',
                    fileType: 'fireAcceptance',
                    deptmentCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.fireAcceptanceDataTable = res.data;
                        // for(var i = 0; i < $scope.fireAcceptanceDataTable.length; i++){
                        //     $scope.fireAcceptanceDataTable[i].attachmentId = undefined;
                        // }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo66 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    isProductFactory: 'Y',
                    fileType: 'projectAcceptance',
                    deptmentCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.projectAcceptanceDataTable = res.data;
                        // for(var i = 0; i < $scope.projectAcceptanceDataTable.length; i++){
                        //     $scope.projectAcceptanceDataTable[i].attachmentId = undefined;
                        // }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.searchProductQualificationsInfo77 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    isProductFactory: 'Y',
                    fileType: 'other',
                    deptmentCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.otherDataTable = res.data;
                        // for(var i = 0; i < $scope.otherDataTable.length; i++){
                        //     $scope.otherDataTable[i].attachmentId = undefined;
                        // }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商资质文件信息**********************/
        // $scope.searchQualificationsFileInfo2 = function (supplierId,deptCode) {
        //     httpServer.post(URLAPI.findCredentialsAttachmentInfo, {
        //         params: JSON.stringify({
        //             supplierId: supplierId,
        //             isProductFactory: 'N',
        //             deptmentCode: deptCode
        //         })
        //     }, function (res) {
        //         if (res.status == 'S') {
        //             $scope.qualificationsFileDataTable = res.data;
        //             for(var i = 0; i < $scope.qualificationsFileDataTable.length; i++){
        //                 $scope.qualificationsFileDataTable[i].attachmentId = undefined;
        //             }
        //         }
        //     }, function (error) {
        //         console.error(error)
        //     })
        // };

        /********************查询OEM地址信息**********************/
        $scope.searchOemAddressInfo2 = function (supplierId, deptCode) {
            httpServer.post(URLAPI.findZzscSupplierAddressInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    deptCode: deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.oemAddressInfoDataTable = res.data;
                    // for(var i = 0; i < $scope.oemAddressInfoDataTable.length; i++){
                    //     $scope.oemAddressInfoDataTable[i].supplierAddressId = undefined;
                    //     $scope.oemAddressInfoDataTable[i].oemProductionInfoParams.productionId = undefined;
                    //     for(var j = 0; j < $scope.oemAddressInfoDataTable[i].oemCapacityInfoParams.length; j++){
                    //         $scope.oemAddressInfoDataTable[i].oemCapacityInfoParams[j].capacityId = undefined;
                    //     }
                    // }
                    $scope.oemAddressInfoDataTable.selectRow = 0;
                    var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
                    $scope.loadLinesData(row, 1);
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询IT地址信息**********************/
        // $scope.searchItAddressInfo2 = function (supplierId,deptCode) {
        //     httpServer.post(URLAPI.findSupplierAddressInfo, {
        //         params: JSON.stringify({
        //             supplierId: supplierId,
        //             deptCode: deptCode
        //         })
        //     }, function (res) {
        //         if (res.status == 'S') {
        //             $scope.itAddressInfoDataTable = res.data;
        //             for(var i = 0; i < $scope.itAddressInfoDataTable.length; i++){
        //                 $scope.itAddressInfoDataTable[i].supplierAddressId = undefined;
        //                 $scope.itAddressInfoDataTable[i].itOperationalInfoParams.operationalId = undefined;
        //                 for(var j = 0; j < $scope.itAddressInfoDataTable[i].itCapacityInfoParams.length; j++){
        //                     $scope.itAddressInfoDataTable[i].itOperationalInfoParams[j].capacityId = undefined;
        //                 }
        //             }
        //             $scope.itAddressInfoDataTable.selectRow = 0;
        //             var row = $scope.itAddressInfoDataTable[$scope.itAddressInfoDataTable.selectRow];
        //             $scope.loadITLinesData(row, 1);
        //         }
        //     }, function (error) {
        //         console.error(error)
        //     })
        // };

        //选择关联工厂
        $scope.selectFactoryInfo = function (value) {
            $('#selectFactoryLov').modal('toggle')

        }

        //选择关联工厂-回调
        $scope.selectFactoryReturn = function (key, value, currentList) {
            for (var i = 0; i < currentList.length; i++) {
                var supplierArray = {
                    supplierName: currentList[i].supplierName,
                    associatedSupplierId: currentList[i].supplierId,
                    deptCode: $scope.program.deptCode
                };
                $scope.relatedFactoryDataTable.push(supplierArray);
            }
        }

        //选择关联贸易商
        $scope.selectTraderInfo = function (value) {
            $('#selectTraderLov').modal('toggle')
        }

        //选择关联贸易商-回调
        $scope.selectTraderReturn = function (key, value, currentList) {
            for (var i = 0; i < currentList.length; i++) {
                var supplierArray = {
                    supplierName: currentList[i].supplierName,
                    associatedSupplierId: currentList[i].supplierId,
                    deptCode: $scope.program.deptCode
                };
                $scope.relatedFactoryDataTable.push(supplierArray);
            }
        }

        //查看工厂/贸易商详情
        $scope.viewSupplierDetail = function (row) {
            iframeTabAction('供应商档案详情', 'supplierFilesDetail/' + row.associatedSupplierId + '/' + row.supplierName);
        }

        //删除关联工厂
        $scope.deleteAssociatedSupplier = function (row, index) {
            JS.confirm('删除', '确定删除？', '确定', function () {
                if (row.associatedId == null || row.associatedId == "") {
                    $scope.relatedFactoryDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteAssociatedSupplier, {
                        'params': JSON.stringify({
                            "id": row.associatedId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.relatedFactoryDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除合作类别
        $scope.deleteCategory = function (row, index) {
            JS.confirm('删除', '确定删除该品类？', '确定', function () {
                if (row.supplierCategoryId == null || row.supplierCategoryId == "") {
                    $scope.supplierCopCategoryDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteSupplierCategorysInfo, {
                        'params': JSON.stringify({
                            "id": row.supplierCategoryId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.supplierCopCategoryDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除服务范围
        $scope.deleteServiceScope = function (row, index) {
            JS.confirm('删除', '确定删除该品类？', '确定', function () {
                if (row.supplierCategoryId == null || row.supplierCategoryId == "") {
                    $scope.supplierServiceScopeDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteSupplierCategorysInfo, {
                        'params': JSON.stringify({
                            "id": row.supplierCategoryId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.supplierServiceScopeDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除联系人
        $scope.deleteContact = function (row, index) {
            JS.confirm('删除', '确定删除联系人信息？', '确定', function () {
                if (row.supplierContactId == null || row.supplierContactId == "") {
                    $scope.supplierContactDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteSupplierContactsInfo, {
                        'params': JSON.stringify({
                            "id": row.supplierContactId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.supplierContactDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除生产资质信息
        $scope.deleteProductQualifications = function (row, index) {
            JS.confirm('删除', '确定删除生产资质信息？', '确定', function () {
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.productQualificationTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.productQualificationTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除资质文件
        $scope.deleteQualificationsFile = function (row, index) {
            JS.confirm('删除', '确定删除资质文件？', '确定', function () {
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.qualificationsFileDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.qualificationsFileDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除银行信息
        $scope.deleteBankInfo = function (row, index) {
            JS.confirm('删除', '确定删除银行信息？', '确定', function () {
                if (row.bankAccountId == null || row.bankAccountId == "") {
                    $scope.supplierBankInfoDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteBankInfo, {
                        'params': JSON.stringify({
                            "id": row.bankAccountId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.supplierBankInfoDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除OEM地址行
        $scope.deleteOemAddressInfo = function (row, index) {
            JS.confirm('删除', '确定删除地址信息？', '确定', function () {
                if (row.supplierAddressId == null || row.supplierAddressId == "") {
                    $scope.oemAddressInfoDataTable.splice(index, 1);             //新增列没有ID直接删除
                    $scope.oemProductionInfoParams = {};
                    $scope.oemCapacityInfoDataTable = [];
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteSupplierAddressInfo, {
                        'params': JSON.stringify({
                            "id": row.supplierAddressId
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
        $scope.deleteOemCapacityInfo = function (row, index) {
            JS.confirm('删除', '确定删除产能信息？', '确定', function () {
                if (row.capacityId == null || row.capacityId == "") {
                    $scope.oemCapacityInfoDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCapacityInfo, {
                        'params': JSON.stringify({
                            "id": row.capacityId
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
        $scope.deleteItAddressInfo = function (row, index) {
            JS.confirm('删除', '确定删除地址信息？', '确定', function () {
                if (row.supplierAddressId == null || row.supplierAddressId == "") {
                    $scope.itAddressInfoDataTable.splice(index, 1);             //新增列没有ID直接删除
                    $scope.oemProductionInfoParams = {};
                    $scope.itCapacityInfoDataTable = [];
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteSupplierAddressInfo, {
                        'params': JSON.stringify({
                            "id": row.supplierAddressId
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
        $scope.deleteItCapacityInfo = function (row, index) {
            JS.confirm('删除', '确定删除产能信息？', '确定', function () {
                if (row.capacityId == null || row.capacityId == "") {
                    $scope.itCapacityInfoDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCapacityInfo, {
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

        //删除周边环境附件
        $scope.deleteSurEnvironmentFile = function (row, index) {
            JS.confirm('删除', '确定删除附件？', '确定', function () {
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.surEnvironmentDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.surEnvironmentDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除大门门牌附件
        $scope.deleteDoorPlateFile = function (row, index) {
            JS.confirm('删除', '确定删除附件？', '确定', function () {
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.doorPlateDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.doorPlateDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除前台附件
        $scope.deleteReceptionFile = function (row, index) {
            JS.confirm('删除', '确定删除附件？', '确定', function () {
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.receptionDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.receptionDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除公司面积附件
        $scope.deleteCompanyAreaFile = function (row, index) {
            JS.confirm('删除', '确定删除附件？', '确定', function () {
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.companyAreaDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.companyAreaDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除办公场所附件
        $scope.deleteOfficeSpaceFile = function (row, index) {
            JS.confirm('删除', '确定删除附件？', '确定', function () {
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.officeSpaceDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.officeSpaceDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //删除员工概况附件
        $scope.deleteEmployeeProfile = function (row, index) {
            JS.confirm('删除', '确定删除附件？', '确定', function () {
                if (row.attachmentId == null || row.attachmentId == "") {
                    $scope.employeeProfileDataTable.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!", "已成功删除数据！", "success");
                }
                else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteCredentialsAttachmentInfo, {
                        'params': JSON.stringify({
                            "id": row.attachmentId
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            JS.alert("删除成功!", "success", "确定");
                            $scope.employeeProfileDataTable.splice(index, 1);
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    });
                }
            })
        }

        //增加周边环境附件
        $scope.addSurEnvironmentFile = function () {
            var SurEnvironmentFileArray = {
                deptmentCode: $scope.program.deptCode,
                fileType: "SurEnvironment",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.surEnvironmentDataTable.push(SurEnvironmentFileArray);
        }
        //增加大门门牌附件
        $scope.addDoorPlateFile = function () {
            var CapacityArray = {
                deptmentCode: $scope.program.deptCode,
                fileType: "DoorPlate",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.doorPlateDataTable.push(CapacityArray);
        }
        //增加前台附件
        $scope.addReceptionFile = function () {
            var CapacityArray = {
                deptmentCode: $scope.program.deptCode,
                fileType: "Reception",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.receptionDataTable.push(CapacityArray);
        }
        //增加公司面积附件
        $scope.addCompanyAreaFile = function () {
            var CapacityArray = {
                deptmentCode: $scope.program.deptCode,
                fileType: "CompanyArea",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.companyAreaDataTable.push(CapacityArray);
        }
        //增加办公场所附件
        $scope.addOfficeSpaceFile = function () {
            var CapacityArray = {
                deptmentCode: $scope.program.deptCode,
                fileType: "OfficeSpace",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.officeSpaceDataTable.push(CapacityArray);
        }
        //增加员工概况附件
        $scope.addEmployeeProfileFile = function () {
            var CapacityArray = {
                deptmentCode: $scope.program.deptCode,
                fileType: "EmployeeProfile",
                index: $scope.rowsIndex + 1
            };
            $scope.rowsIndex = $scope.rowsIndex + 1;
            $scope.employeeProfileDataTable.push(CapacityArray);
        }

        //上传资质审核单据附件
        $scope.uploadFile = function () {

            // var f = $scope.myFile;
            var fd = new FormData();
            var file = document.querySelector('#file').files[0];
            // var file = document.getElementById("file").files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.program.qualificationFileId;
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
                $scope.program.qualificationFileId = response.data[0].fileId;
                $scope.program.fileName = response.data[0].fileName;
                $scope.program.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传资质审核单据附件
        $scope.changeFile = function () {
            $scope.program.qualificationFileId = null;
            $scope.program.filePath = null;
            $scope.program.fileName = null;
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
            var id = $scope.supplierProgram.supplierFileId;
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
                $scope.supplierProgram.supplierFileId = response.data[0].fileId;
                $scope.supplierProgram.fileName = response.data[0].fileName;
                $scope.supplierProgram.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-供应商基础信息-附件
        $scope.changeFile2 = function () {
            $scope.supplierProgram.supplierFileId = null;
            $scope.supplierProgram.filePath = null;
            $scope.supplierProgram.fileName = null;
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
            var id = $scope.supplierCredentialsProgram.licenseFileId;
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
                $scope.supplierCredentialsProgram.licenseFileId = response.data[0].fileId;
                $scope.supplierCredentialsProgram.licenseFileName = response.data[0].fileName;
                $scope.supplierCredentialsProgram.licenseFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-营业执照复印件-附件
        $scope.changeLicenseFile = function () {
            $scope.supplierCredentialsProgram.licenseFileId = null;
            $scope.supplierCredentialsProgram.licenseFilePath = null;
            $scope.supplierCredentialsProgram.licenseFileName = null;
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
            var id = $scope.supplierCredentialsProgram.tissueFileId;
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
                $scope.supplierCredentialsProgram.tissueFileId = response.data[0].fileId;
                $scope.supplierCredentialsProgram.tissueFileName = response.data[0].fileName;
                $scope.supplierCredentialsProgram.tissueFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-组织机构代码证-附件
        $scope.changeTissueFile = function () {
            $scope.supplierCredentialsProgram.tissueFileId = null;
            $scope.supplierCredentialsProgram.tissueFilePath = null;
            $scope.supplierCredentialsProgram.tissueFileName = null;
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
            var id = $scope.supplierCredentialsProgram.taxFileId;
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
                $scope.supplierCredentialsProgram.taxFileId = response.data[0].fileId;
                $scope.supplierCredentialsProgram.taxFileName = response.data[0].fileName;
                $scope.supplierCredentialsProgram.taxFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-税务登记证-附件
        $scope.changeTaxFile = function () {
            $scope.supplierCredentialsProgram.taxFileId = null;
            $scope.supplierCredentialsProgram.taxFilePath = null;
            $scope.supplierCredentialsProgram.taxFileName = null;
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
            var id = $scope.supplierCredentialsProgram.bankFileId;
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
                $scope.supplierCredentialsProgram.bankFileId = response.data[0].fileId;
                $scope.supplierCredentialsProgram.bankFileName = response.data[0].fileName;
                $scope.supplierCredentialsProgram.bankFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-银行开户许可证-附件
        $scope.changeBankFile = function () {
            $scope.supplierCredentialsProgram.bankFileId = null;
            $scope.supplierCredentialsProgram.bankFilePath = null;
            $scope.supplierCredentialsProgram.bankFileName = null;
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
            var id = $scope.supplierCredentialsProgram.taxpayerFileId;
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
                $scope.supplierCredentialsProgram.taxpayerFileId = response.data[0].fileId;
                $scope.supplierCredentialsProgram.taxpayerFileName = response.data[0].fileName;
                $scope.supplierCredentialsProgram.taxpayerFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-一般纳税人证明-附件
        $scope.changeTaxpayerFile = function () {
            $scope.supplierCredentialsProgram.taxpayerFileId = null;
            $scope.supplierCredentialsProgram.taxpayerFilePath = null;
            $scope.supplierCredentialsProgram.taxpayerFileName = null;
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
            var id = $scope.supplierCredentialsProgram.projectExperienceFileId;
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
                $scope.supplierCredentialsProgram.projectExperienceFileId = response.data[0].fileId;
                $scope.supplierCredentialsProgram.projectExperienceFileName = response.data[0].fileName;
                $scope.supplierCredentialsProgram.projectExperienceFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-行业内类似项目附件-附件
        $scope.changeProjectExperienceFile = function () {
            $scope.supplierCredentialsProgram.projectExperienceFileId = null;
            $scope.supplierCredentialsProgram.projectExperienceFilePath = null;
            $scope.supplierCredentialsProgram.projectExperienceFileName = null;
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
            var id = $scope.supplierCredentialsProgram.relatedFileId;
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
                $scope.supplierCredentialsProgram.relatedFileId = response.data[0].fileId;
                $scope.supplierCredentialsProgram.relatedFileName = response.data[0].fileName;
                $scope.supplierCredentialsProgram.relatedFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-相关附件-附件
        $scope.changeRelatedFile = function () {
            $scope.supplierCredentialsProgram.relatedFileId = null;
            $scope.supplierCredentialsProgram.relatedFilePath = null;
            $scope.supplierCredentialsProgram.relatedFileName = null;
        }

        //上传-OEM生产能力-附件
        $scope.uploadOEMProductionCapacityFile = function () {
            var row = $scope.oemCapacityInfoDataTable[$scope.oemCapacityInfoDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#oemProductionCapacityFile' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.productionCapacityFileId;
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
                row.productionCapacityFileId = response.data[0].fileId;
                row.productionCapacityFileName = response.data[0].fileName;
                row.productionCapacityFilePath = response.data[0].filePath;
            }).error(function (response) {
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
            var row = $scope.itCapacityInfoDataTable[$scope.itCapacityInfoDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#itProductionCapacityFile' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.productionCapacityFileId;
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
                row.productionCapacityFileId = response.data[0].fileId;
                row.productionCapacityFileName = response.data[0].fileName;
                row.productionCapacityFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-IT生产能力-附件
        $scope.changeITProductionCapacityFile = function (row) {
            row.productionCapacityFileId = null;
            row.productionCapacityFilePath = null;
            row.productionCapacityFileName = null;
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
            var id = $scope.productQualificationTable1.fileId;
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
                $scope.productQualificationTable1.fileId = response.data[0].fileId;
                $scope.productQualificationTable1.fileName = response.data[0].fileName;
                $scope.productQualificationTable1.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-生产资质文件-生产许可证-附件
        $scope.changeProductQualificationFile1 = function () {
            $scope.productQualificationTable1.fileId = null;
            $scope.productQualificationTable1.filePath = null;
            $scope.productQualificationTable1.fileName = null;
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
            var id = $scope.productQualificationTable2.fileId;
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
                $scope.productQualificationTable2.fileId = response.data[0].fileId;
                $scope.productQualificationTable2.fileName = response.data[0].fileName;
                $scope.productQualificationTable2.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-生产资质文件-质量安全管理体系认证证明-附件
        $scope.changeProductQualificationFile2 = function () {
            $scope.productQualificationTable2.fileId = null;
            $scope.productQualificationTable2.filePath = null;
            $scope.productQualificationTable2.fileName = null;
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
            var id = $scope.productQualificationTable3.fileId;
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
                $scope.productQualificationTable3.fileId = response.data[0].fileId;
                $scope.productQualificationTable3.fileName = response.data[0].fileName;
                $scope.productQualificationTable3.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-生产资质文件-CSR报告-附件
        $scope.changeProductQualificationFile3 = function () {
            $scope.productQualificationTable3.fileId = null;
            $scope.productQualificationTable3.filePath = null;
            $scope.productQualificationTable3.fileName = null;
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
            var id = $scope.productQualificationTable4.fileId;
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
                $scope.productQualificationTable4.fileId = response.data[0].fileId;
                $scope.productQualificationTable4.fileName = response.data[0].fileName;
                $scope.productQualificationTable4.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-生产资质文件-消防验收证明-附件
        $scope.changeProductQualificationFile4 = function () {
            $scope.productQualificationTable4.fileId = null;
            $scope.productQualificationTable4.filePath = null;
            $scope.productQualificationTable4.fileName = null;
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
            var id = $scope.productQualificationTable5.fileId;
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
                $scope.productQualificationTable5.fileId = response.data[0].fileId;
                $scope.productQualificationTable5.fileName = response.data[0].fileName;
                $scope.productQualificationTable5.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-生产资质文件-建筑工程竣工验收报告-附件
        $scope.changeProductQualificationFile5 = function () {
            $scope.productQualificationTable5.fileId = null;
            $scope.productQualificationTable5.filePath = null;
            $scope.productQualificationTable5.fileName = null;
        }

        //上传-资质文件-附件
        $scope.uploadQualificationsFile = function () {
            var row = $scope.qualificationsFileDataTable[$scope.qualificationsFileDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#qualificationsFile' + row.index).files[0];
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
            var id = $scope.itOperationalInfoParams.surEnvironmentFileId;
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
                $scope.itOperationalInfoParams.surEnvironmentFileId = response.data[0].fileId;
                $scope.itOperationalInfoParams.surEnvironmentFileName = response.data[0].fileName;
                $scope.itOperationalInfoParams.surEnvironmentFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-周边环境-附件
        $scope.changeSurEnvironmentFile = function () {
            $scope.itOperationalInfoParams.surEnvironmentFileId = null;
            $scope.itOperationalInfoParams.surEnvironmentFilePath = null;
            $scope.itOperationalInfoParams.surEnvironmentFileName = null;
        }

        //上传-经营状况-周边环境-增加附件
        $scope.uploadOtherSurEnvironmentFile = function () {
            var row = $scope.surEnvironmentDataTable[$scope.surEnvironmentDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#surEnvironmentFile' + row.index).files[0];
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
            var id = $scope.itOperationalInfoParams.doorPlateFileId;
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
                $scope.itOperationalInfoParams.doorPlateFileId = response.data[0].fileId;
                $scope.itOperationalInfoParams.doorPlateFileName = response.data[0].fileName;
                $scope.itOperationalInfoParams.doorPlateFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-大门门牌-附件
        $scope.changeDoorPlateFile = function () {
            $scope.itOperationalInfoParams.doorPlateFileId = null;
            $scope.itOperationalInfoParams.doorPlateFilePath = null;
            $scope.itOperationalInfoParams.doorPlateFileName = null;
        }

        //上传-经营状况-大门门牌-增加附件
        $scope.uploadOtherDoorPlateFile = function () {
            var row = $scope.doorPlateDataTable[$scope.doorPlateDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#doorPlateFile' + row.index).files[0];
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
            var id = $scope.itOperationalInfoParams.receptionFileId;
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
                $scope.itOperationalInfoParams.receptionFileId = response.data[0].fileId;
                $scope.itOperationalInfoParams.receptionFileName = response.data[0].fileName;
                $scope.itOperationalInfoParams.receptionFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-前台-附件
        $scope.changeReceptionFile = function () {
            $scope.itOperationalInfoParams.receptionFileId = null;
            $scope.itOperationalInfoParams.receptionFilePath = null;
            $scope.itOperationalInfoParams.receptionFileName = null;
        }

        //上传-经营状况-前台-增加附件
        $scope.uploadOtherReceptionFile = function () {
            var row = $scope.receptionDataTable[$scope.receptionDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#receptionFile' + row.index).files[0];
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
            var id = $scope.itOperationalInfoParams.companyAreaFileId;
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
                $scope.itOperationalInfoParams.companyAreaFileId = response.data[0].fileId;
                $scope.itOperationalInfoParams.companyAreaFileName = response.data[0].fileName;
                $scope.itOperationalInfoParams.companyAreaFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-公司面积-附件
        $scope.changeCompanyAreaFile = function () {
            $scope.itOperationalInfoParams.companyAreaFileId = null;
            $scope.itOperationalInfoParams.companyAreaFilePath = null;
            $scope.itOperationalInfoParams.companyAreaFileName = null;
        }

        //上传-经营状况-公司面积-增加附件
        $scope.uploadOtherCompanyAreaFile = function () {
            var row = $scope.companyAreaDataTable[$scope.companyAreaDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#companyAreaFile' + row.index).files[0];
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
            var id = $scope.itOperationalInfoParams.officeSpaceFileId;
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
                $scope.itOperationalInfoParams.officeSpaceFileId = response.data[0].fileId;
                $scope.itOperationalInfoParams.officeSpaceFileName = response.data[0].fileName;
                $scope.itOperationalInfoParams.officeSpaceFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-办公场所-附件
        $scope.changeOfficeSpaceFile = function () {
            $scope.itOperationalInfoParams.officeSpaceFileId = null;
            $scope.itOperationalInfoParams.officeSpaceFilePath = null;
            $scope.itOperationalInfoParams.officeSpaceFileName = null;
        }

        //上传-经营状况-办公场所-增加附件
        $scope.uploadOtherOfficeSpaceFile = function () {
            var row = $scope.officeSpaceDataTable[$scope.officeSpaceDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#officeSpaceFile' + row.index).files[0];
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
            var id = $scope.itOperationalInfoParams.employeeProfileFileId;
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
                $scope.itOperationalInfoParams.employeeProfileFileId = response.data[0].fileId;
                $scope.itOperationalInfoParams.employeeProfileFileName = response.data[0].fileName;
                $scope.itOperationalInfoParams.employeeProfileFilePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-经营状况-员工概况-附件
        $scope.changeEmployeeProfileFile = function () {
            $scope.itOperationalInfoParams.employeeProfileFileId = null;
            $scope.itOperationalInfoParams.employeeProfileFilePath = null;
            $scope.itOperationalInfoParams.employeeProfileFileName = null;
        }

        //上传-经营状况-员工概况-增加附件
        $scope.uploadOtherEmployeeProfile = function () {
            var row = $scope.employeeProfileDataTable[$scope.employeeProfileDataTable.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#employeeProfile' + row.index).files[0];
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

        //重新上传-经营状况-员工概况-增加附件
        $scope.changeOtherEmployeeProfile = function (row) {
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        }

        /**********************************工作流配置**************************************/
        function urlToObj(url) {
            var index = url.lastIndexOf('?');
            var params = url.substring(index + 1);
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
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.program.qualificationId;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'EQU_ZZSCOEM.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
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
                        businessKey: $scope.program.qualificationId
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
        $scope.btnSubmit2 = function () {
            if ($scope.program.qualificationStatus != '30' && $scope.program.qualificationStatus != '60') {
                JS.alert('状态不是已注册，不允许提交审批', 'error', '确定');
                return;
            }
            $scope.extend = {
                "tasks_assignees": []
            }
            $scope.variables = []; //流程变量
            angular.forEach($scope.program, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });

            $scope.properties = {
                "menucode": "ZZSC",
                "opinion": ""
            };
            $scope.paramsBpm = {
                "extend": $scope.extend,
                "variables": $scope.variables,
                "properties": $scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": "EQU_ZZSCOEM.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.program.qualificationId, //单据ID
                "title": "资质审查流程", //流程标题
                "positionId": 0,
                "orgId": 0,
                "userType": "30",
                "billNo": $scope.program.qualificationNumber
            }
            httpServer.post(URLAPI.bpmStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.search($scope.program.qualificationId);
                    //修改单据状态为待审核
                    $scope.program.qualificationStatus = '40';
                    $scope.btnSave();
                    JS.alert('提交成功');
                }
                else {
                    JS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        }
    }]);
})