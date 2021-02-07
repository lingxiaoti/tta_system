/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('equPosWarehousingEditCtrl', function (wechatFind, $filter, Base64, deleteUserResp, findProfileSqlDatas, $window, iframeTabAction,
                                                          saveUserResp, userSave, $timeout, $scope, httpServer, URLAPI, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS,$controller) {
        // ------------------------------------------------初始化------------------------------------------------
        $controller('processBase', {$scope:$scope}); // 继承基础的流程控制器
        var supWarehousingId = $stateParams.supWarehousingId

        if ($stateParams.supWarehousingId){
            supWarehousingId = $stateParams.supWarehousingId;
        }else if ($scope.urlParams.businessKey){
            supWarehousingId = $scope.urlParams.businessKey;
        }
        $scope.params = {}
        $scope.param = {}//lov查询参数
        $scope.flags = {};
        var saafsuccessLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
        //当前登录人所属部门
        $scope.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.param.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;

        $scope.param.type = 'warehousing';
        $scope.params.department = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.supplierProgram = {};

        //当前登录人所属部门
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;

        $scope.params.qualificationStatus = '10';
        $scope.params.qualificationStatusMeaning = '拟定';
        $scope.params.departmentName = $scope.params.deptName;
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
        $scope.productQualificationTable1 = {"attachmentName":"生产许可证","fixFlag":"Y","fileName":"","filePath":"","deptmentCode":$scope.params.deptCode,"description":"","fileId":"","supplierId":"","invalidDate":"","isProductFactory":"Y","index":"1"};
        $scope.productQualificationTable2 = {"attachmentName":"质量安全管理体系认证证明","fixFlag":"Y","fileName":"","filePath":"","deptmentCode":$scope.params.deptCode,"description":"","fileId":"","supplierId":"","invalidDate":"","isProductFactory":"Y","index":"2"};
        $scope.productQualificationTable3 = {"attachmentName":"CSR报告","fixFlag":"Y","fileName":"","filePath":"","deptmentCode":$scope.params.deptCode,"description":"","fileId":"","supplierId":"","invalidDate":"","isProductFactory":"Y","index":"3"};
        $scope.productQualificationTable4 = {"attachmentName":"消防验收证明","fixFlag":"Y","fileName":"","filePath":"","deptmentCode":$scope.params.deptCode,"description":"","fileId":"","supplierId":"","invalidDate":"","isProductFactory":"Y","index":"4"};
        $scope.productQualificationTable5 = {"attachmentName":"建筑工程竣工验收报告","fixFlag":"Y","fileName":"","filePath":"","deptmentCode":$scope.params.deptCode,"description":"","fileId":"","supplierId":"","invalidDate":"","isProductFactory":"Y","index":"5"};

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
        $scope.supplierCredentialsProgram = {"deptCode":$scope.params.deptCode};
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



// ------------------------------------------------跳转------------------------------------------------
        $scope.goToCreditAudit = function () {
            if($scope.params.supCreditAuditId){
                iframeTabAction('编辑供应商信用审批详情：', 'equPosCreditAuditEdit/' + $scope.params.supCreditAuditId)
            }

        }
        $scope.goToQualityAudit = function () {
            if($scope.params.supQualityAuditId){
                iframeTabAction('质量审核详情', 'equPosQualityAuditDetail/' + $scope.params.supQualityAuditId);
            }

        }
        $scope.goToCsrCredit = function () {
            if($scope.params.supCsrAuditId){
                iframeTabAction('CSR审核详情', 'equPosCsrAuditDetail/' + $scope.params.supCsrAuditId);
            }
        }


// ------------------------------------------------调整------------------------------------------------

        //选中地址行时，加载关联行数据
        $scope.loadLinesData = function (row, $index) {
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

        $scope.changeShowFlag = function () {
            $scope.saveFlag = false;
            $scope.submitFlag = false;
            $scope.approveFlag = false;
            $scope.rejectFlag = false;
            $scope.cancelFlag = false;
            $scope.editFlag = false;
            if ($scope.params.supWarehousingStatus != '20' && $scope.params.supWarehousingStatus != '30') {
                $scope.saveFlag = true;  //提交与审批状态不能保存
                $scope.submitFlag = true;
            }
            if ($scope.params.supWarehousingStatus == '20') {
                $scope.approveFlag = true;  //提交与审批状态不能保存
                $scope.rejectFlag = true;
            }
            if ($scope.params.supWarehousingStatus == '50' && saafsuccessLoginInfo.userId == $scope.params.createdBy) {
                $scope.cancelFlag = true;   //驳回状态本人可以取消
            }
            if ($scope.params.supWarehousingStatus == '10' || $scope.params.supWarehousingStatus == '50') {
                $scope.editFlag = true; //拟定与驳回状态才能编辑
            }
        }

        if (supWarehousingId) {
            // $scope.params.supWarehousingId = supWarehousingId;

            httpServer.post(URLAPI.findSupWarehousingDatail, {
                params: JSON.stringify({
                    supWarehousingId: supWarehousingId
                })
            }, function (res) {
                if (res.status == "S") {
                    $scope.params = res.data;
                    //修改按键显示隐藏
                    $scope.changeShowFlag();
                    $scope.params.deptCode = res.data.deptCode;
                    $scope.params.type = 'warehousing';
                    //查询各个子信息
                    $scope.search();

                    //查询各个子信息
                    $scope.historicParam = {}
                    $scope.historicParam.code = $scope.params.supWarehousingCode
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
                console.error(error);
            });
        } else {
            $scope.params = {};
            $scope.params.createdByName = saafsuccessLoginInfo.userFullName;
            $scope.params.creationDate = CurentTime();
            $scope.params.supWarehousingType = '10';
            $scope.params.supWarehousingStatus = '10';
            $scope.params.type = 'warehousing';
            $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            $scope.changeShowFlag();
        }

        //查询子表信息
        $scope.search = function () {
            $scope.searchBaseInfo($scope.params.supplierId);
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
        }
        //弹出供应商
        $scope.getSupplierCode = function () {
            $('#supplierLov').modal('toggle')
        };

        //弹出资质审核
        $scope.getQualificationCode = function () {
            if (!$scope.params.supplierName || !$scope.params.sceneType) {
                SIEJS.alert("供应商信息或者业务类型未填写", 'error', '确定');
            } else {
                $('#qualificationLov').modal('toggle')
            }
        };

        /********************查询供应商基本信息**********************/
        $scope.searchBaseInfo = function (supplierId) {
            console.log(supplierId)
            httpServer.post(URLAPI.findZzscSupplierInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    deptCode: $scope.params.deptCode,
                    searchType: 'WAREHOUSING'
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierProgram = res.data[0];
                    console.log($scope.supplierProgram)
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商关联工厂**********************/
        $scope.searchRelatedFactoryInfo = function () {
            httpServer.post(URLAPI.findZzscAssociatedSupplier, {
                params: JSON.stringify({
                    supplierId: $scope.params.supplierId,
                    deptCode: $scope.params.deptCode
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
                    supplierId: $scope.params.supplierId,
                    deptCode: $scope.params.deptCode
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
                    supplierId: $scope.params.supplierId,
                    deptCode: $scope.params.deptCode
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
                    supplierId: $scope.params.supplierId,
                    deptCode: $scope.params.deptCode
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
                    supplierId: $scope.params.supplierId,
                    deptCode: $scope.params.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data.length > 0) {
                        $scope.supplierCredentialsProgram = res.data[0];
                    } else {
                        $scope.supplierCredentialsProgram.deptCode = $scope.params.deptCode;
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
                    supplierId: $scope.params.supplierId,
                    isProductFactory: 'Y',
                    fileType: '',
                    deptCode: $scope.params.deptCode
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
                    supplierId: $scope.params.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'productionPermit',
                    deptCode: $scope.params.deptCode
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

        //查看工厂/贸易商详情
        $scope.viewSupplierDetail = function (row) {
            debugger;
            iframeTabAction('供应商档案详情', 'supplierFilesDetail/' + row.associatedSupplierId + '/' + row.supplierName);
        }

        $scope.searchProductQualificationsInfo3 = function () {
            httpServer.post(URLAPI.findZzscCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.params.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'qualitySafety',
                    deptCode: $scope.params.deptCode
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
                    supplierId: $scope.params.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'csrReport',
                    deptCode: $scope.params.deptCode
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
                    supplierId: $scope.params.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'fireAcceptance',
                    deptCode: $scope.params.deptCode
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
                    supplierId: $scope.params.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'projectAcceptance',
                    deptCode: $scope.params.deptCode
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
                    supplierId: $scope.params.supplierId,
                    isProductFactory: 'Y',
                    fileType: 'other',
                    deptCode: $scope.params.deptCode
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
                    supplierId: $scope.params.supplierId,
                    isProductFactory: 'N'
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
                    supplierId: $scope.params.supplierId
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
                    supplierId: $scope.params.supplierId,
                    deptCode: $scope.params.deptCode
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
                    supplierId: $scope.params.supplierId,
                    deptCode: $scope.params.deptCode
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

        //选中地址行时，加载关联行数据
        $scope.loadITLinesData = function (row, $index) {
            $scope.itOperationalInfoParams = row.itOperationalInfoParams;
            $scope.itCapacityInfoDataTable = row.itCapacityInfoParams;
            $scope.surEnvironmentDataTable = row.surEnvironmentDataTable;
            $scope.doorPlateDataTable = row.doorPlateDataTable;
            $scope.receptionDataTable = row.receptionDataTable;
            $scope.companyAreaDataTable = row.companyAreaDataTable;
            $scope.officeSpaceDataTable = row.officeSpaceDataTable;
            $scope.employeeProfileDataTable = row.employeeProfileDataTable;
        }

        $scope.selectSupplierLov = function (key, value, currentList) {
            $scope.params.supplierStatus = currentList[0].supplierStatus;
            $scope.params.supplierId = currentList[0].supplierId;
            $scope.params.supplierNumber = currentList[0].supplierNumber;
            $scope.params.supplierName = currentList[0].supplierName;
            $scope.search()
        };

        $scope.selectQualificationLov = function (key, value, currentList) {
            console.log(currentList[0].qualificationNumber)
            $scope.params.qualificationCode = currentList[0].qualificationNumber;
            $scope.params.qualificationId = currentList[0].qualificationId;
            $scope.params.qualificationStatus = currentList[0].qualificationStatus;
        }

        function CurentTime() {
            var now = new Date();
            var year = now.getFullYear();       //年
            var month = now.getMonth() + 1;     //月
            var day = now.getDate();            //日
            var hh = now.getHours();            //时
            var mm = now.getMinutes();          //分
            var ss = now.getSeconds();           //秒
            var clock = year + "-";
            if (month < 10)
                clock += "0";
            clock += month + "-";
            if (day < 10)
                clock += "0";
            clock += day + " ";
            if (hh < 10)
                clock += "0";
            clock += hh + ":";
            if (mm < 10) clock += '0';
            clock += mm + ":";
            if (ss < 10) clock += '0';
            clock += ss;
            return (clock);
        }

// ------------------------------------------------附件------------------------------------------------
        //上传附件
        $scope.uploadFile = function () {

            var f = $scope.myFile;
            var fd = new FormData();
            var file = document.querySelector('input[type=file]').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.params.supSuspendId;
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
                $scope.params.fileId = response.data[0].fileId;
                $scope.params.fileName = response.data[0].fileName;
                $scope.params.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        $scope.changeFile = function () {
            $scope.params.fileId = null;
            $scope.params.filePath = null;
            $scope.params.fileName = null;
        }
// ------------------------------------------------保存------------------------------------------------

        $scope.saveDatil = function (action) {
            if ($scope.params.supplierName == null || $scope.params.supplierName == "" ||$scope.params.sceneType == null || $scope.params.sceneType == "") {
                SIEJS.alert("请维护业务类型与供应商信息。", "error", "确定");
            }else{
                httpServer.post(URLAPI.saveEquPosWarehousing, {
                    'params': JSON.stringify($scope.params),
                }, function (res) {
                    if (res.status == "S") {
                        debugger
                        $scope.params.supWarehousingId = res.data.supWarehousingId;
                        if(!supWarehousingId){
                            httpServer.post(URLAPI.findSupWarehousingDatail, {
                                params: JSON.stringify($scope.params)
                            }, function (res) {
                                if (res.status == "S") {
                                    var type = $scope.params.type;
                                    $scope.params = res.data;
                                    $scope.params.type = type;
                                    supWarehousingId = res.data.supWarehousingId;
                                    $scope.search();
                                }
                            }, function (error) {
                                console.error(error);
                            });
                        }
                        $scope.params.versionNum = res.data.versionNum;
                        $scope.params.supWarehousingCode = res.data.supWarehousingCode;
                        $scope.params.supWarehousingStatus = res.data.supWarehousingStatus;
                        $scope.params.creationDate = res.data.creationDate;
                        $scope.changeShowFlag();
                        SIEJS.alert("操作成功!", "success");
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                });
            }

        }

        $scope.save = function (action) {
            $scope.params.action = action;
            if (!$scope.params.supWarehousingId) {
                SIEJS.confirm('保存', '保存后将无法再变更供应商信息与业务类型', '确认', function () {
                    $scope.saveDatil();
                })
            } else {
                if ($scope.params.supplierName == null || $scope.params.supplierName == "" ||$scope.params.sceneType == null || $scope.params.sceneType == ""
                    ||$scope.params.qualificationCode == null || $scope.params.qualificationCode == "") {
                    SIEJS.alert("请维护供应商信息,业务类型与资质审核单号。", "error", "确定");
                }
                else {
                    // 提交前验证数据
                    if(action==='submit'){
                        if($scope.params.sceneType == 10 || $scope.params.sceneType == 30){
                            if($scope.params.supCreditAuditCode == null || ($scope.params.creditAuditResule != 'Y'&&$scope.params.creditAuditResule != 'YN')){
                                SIEJS.alert("信用审核单不存在或者不是合格状态。", "error", "确定");
                                return;
                            }
                        }else{
                            if($scope.params.csrAuditNumber == null || $scope.params.csrAuditResult != '10' ){
                                SIEJS.alert("CSR审核单不存在或者不是合格状态。", "error", "确定");
                                return;
                            }
                            if($scope.params.supCreditAuditCode == null || ($scope.params.creditAuditResule != 'Y'&&$scope.params.creditAuditResule != 'YN')){
                                SIEJS.alert("信用审核单不存在或者不是合格状态。", "error", "确定");
                                return;
                            }
                            if($scope.params.qualityAuditNumber == null ||  $scope.params.qualityAuditResult != '10'){
                                SIEJS.alert("质量审核单不存在或者不是合格状态。", "error", "确定");
                                return;
                            }
                        }
                    }
                    $scope.saveDatil();

                }
            }
        }


        /**********************************工作流配置**************************************/
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
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.params.supWarehousingId;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate')||localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'EQU_RKSH.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
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
                        businessKey:$scope.params.supWarehousingId
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

        $scope.btnSubmit = function () {
            if($scope.params.sceneType == 10 || $scope.params.sceneType == 30){
                if($scope.params.supCreditAuditCode == null || ($scope.params.creditAuditResule != 'Y'&&$scope.params.creditAuditResule != 'YN')){
                    SIEJS.alert("信用审核单不存在或者不是合格状态。", "error", "确定");
                    return;
                }else{
                    $scope.params.action = 'submit';
                    httpServer.post(URLAPI.saveEquPosWarehousing, {
                        'params': JSON.stringify($scope.params),
                    }, function (res) {
                        if (res.status == "S") {
                            $scope.params.versionNum =res.data.versionNum;
                            $scope.params.createdBy =res.data.createdBy;
                            $scope.params.creationDate =res.data.creationDate;
                            $scope.btnSubmit2();
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                            console.error(res);
                        }
                    }, function (error) {
                        console.error(error);
                    });
                }
            }else{
                if($scope.params.csrAuditNumber == null || $scope.params.csrAuditResult != '10' ){
                    SIEJS.alert("CSR审核单不存在或者不是合格状态。", "error", "确定");
                    return;
                }else if($scope.params.supCreditAuditCode == null || ($scope.params.creditAuditResule != 'Y'&&$scope.params.creditAuditResule != 'YN')){
                    SIEJS.alert("信用审核单不存在或者不是合格状态。", "error", "确定");
                    return;
                }else if($scope.params.qualityAuditNumber == null ||  $scope.params.qualityAuditResult != '10'){
                    SIEJS.alert("质量审核单不存在或者不是合格状态。", "error", "确定");
                    return;
                }else{
                    $scope.params.action = 'submit';
                    httpServer.post(URLAPI.saveEquPosWarehousing, {
                        'params': JSON.stringify($scope.params),
                    }, function (res) {
                        if (res.status == "S") {
                            $scope.params.versionNum =res.data.versionNum;
                            $scope.params.createdBy =res.data.createdBy;
                            $scope.params.creationDate =res.data.creationDate;
                            $scope.btnSubmit2();
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                            console.error(res);
                        }
                    }, function (error) {
                        console.error(error);
                    });
                }
            }

        }

        /**********************************工作流配置**************************************/

        //提交审批
        $scope.btnSubmit2 = function(){
            debugger
            if($scope.params.supWarehousingStatus != '10' && $scope.params.supWarehousingStatus != '40'){
                SIEJS.alert('状态不是拟定，不允许提交审批', 'error', '确定');
                return;
            }
            $scope.extend ={
                "tasks_assignees":[]
            }
            $scope.variables = []; //流程变量
            angular.forEach($scope.params, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });

            $scope.properties={
                "menucode": "ZZSC",
                "opinion": ""
            };
            $scope.paramsBpm={
                "extend":$scope.extend,
                "variables":$scope.variables,
                "properties":$scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": "EQU_RKSH.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.params.supWarehousingId, //单据ID
                "title": "供应商入库", //流程标题
                "positionId": 0,
                "orgId": 0,
                "userType": "30",
                "billNo": $scope.params.supWarehousingCode
            }
            httpServer.post(URLAPI.bpmStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.params.supWarehousingStatus = '20'
                    $scope.search($scope.params.supWarehousingId);
                    $scope.changeShowFlag();
                    SIEJS.alert('提交成功');
                    // httpServer.post(URLAPI.saveSupWarehousingDatailSumbit, {
                    //     'params': JSON.stringify($scope.params)
                    // }, function (res) {
                    //     if (res.status == 'S') {
                    //         $scope.params.supWarehousingStatus = '20'
                    //         $scope.search($scope.params.supWarehousingId);
                    //         SIEJS.alert('提交成功');
                    //     }
                    //     else {
                    //         SIEJS.alert(res.msg, "error", "确定");
                    //     }
                    // }, function (err) {
                    //     SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    // });
                }
                else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        }

    });
});
