'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('editTempSpecialCtrl', function (wechatFind, $filter, Base64, deleteUserResp, findProfileSqlDatas, $window,
                                                    saveUserResp, userSave, $timeout, $scope, httpServer, URLAPI, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS, $controller) {
        $scope.editParams = {};
        $scope.params = {};
        $scope.lovParams = {};
        $scope.lovParams.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.qualificationLovParams = {};


        // 控制按钮的状态
        $scope.changeButtonShow = function () {
            $scope.saveFlag = false;
            $scope.submitFlag = false;
            $scope.passFlag = false;
            $scope.rejectFlag = false;
            $scope.cancelFlag = false;
            $scope.editFlag = false;
            // 不是待审核和已批准状态，则保存和提交能用
            console.log($scope.editParams.docStatus);
            // 拟定状态才能编辑
            if ($scope.editParams.docStatus == 'DRAFT') {
                $scope.editFlag = true;
                $scope.saveFlag = true;
                $scope.submitFlag = true;
            }
            // 待审核和审批时都没有按钮，都不能修改
            // 驳回时取消按钮才有效,本人可以取消
            if ($scope.editParams.docStatus == 'REJECT') {
                $scope.cancelFlag = true;
            }
        };

        var tempSpecialId = null;
        $controller('processBase', {$scope: $scope}); // 继承基础的流程控制器
        if ($stateParams.tempSpecialId) {
            tempSpecialId = $stateParams.tempSpecialId;
        } else if ($scope.urlParams.businessKey) {
            tempSpecialId = $scope.urlParams.businessKey;
        }

        if (tempSpecialId) {
            $scope.params.tempSpecialId = tempSpecialId;
            $scope.params.deptId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptId;
            httpServer.post(URLAPI.findTempSpecialDetail, {
                params: JSON.stringify($scope.params)
            }, function (res) {
                if (res.status == "S") {
                    $scope.editParams = res.data;
                    console.log($scope.editParams);
                    // 查询供应商资质信息
                    $scope.params.supplierId = $scope.editParams.supplierId;
                    $scope.searchAll();
                    $scope.changeButtonShow();

                    //查询各个子信息
                    $scope.historicParam = {}
                    $scope.historicParam.code = $scope.editParams.tempSpecialCode
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
                SIEJS.alert('根据id查询数据详情失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error");
            });
        } else {
            $scope.editParams = {};
            // 用户名
            $scope.editParams.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;
            $scope.editParams.creationDate = CurentTime();
            $scope.editParams.docStatus = 'DRAFT';
            $scope.editFlag = true;
            $scope.changeButtonShow();
        }

        // 供应商值列表查询
        //选择供应商
        $scope.selectSupplierInfo = function (value) {
            $('#selectSupplierLov').modal('toggle')
        };

        $scope.selectSupplierLov = function (key, value, currentList) {
            $scope.editParams.supplierNumber = currentList[0].supplierNumber;
            $scope.editParams.supplierName = currentList[0].supplierName;
            $scope.editParams.supplierId = currentList[0].supplierId;
            $scope.editParams.supplierStatus = currentList[0].supplierStatus;
        };
        // 资质审查值列表查询
        $scope.selectQualificationNumber = function (value) {
            $('#selectQualificationLov').modal('toggle');
            $scope.$watch("editParams.businessType", function () {
                // 给资质审查值列表参数赋值
                $scope.qualificationLovParams.sceneType = $scope.editParams.businessType;
                // $scope.qualificationLovParams.qualificationStatus = '50';
                $scope.qualificationLovParams.isLov = 'Y';
                $scope.qualificationLovParams.tempFlag = 'Y';
                $scope.qualificationLovParams.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;;
            });
        };

        $scope.selectQualificationLov = function (key, value, currentList) {
            $scope.editParams.supplierName = currentList[0].supplierName;
            $scope.editParams.supplierNumber = currentList[0].supplierNumber;
            $scope.editParams.sceneTypeMeaning = currentList[0].sceneTypeMeaning;
            $scope.editParams.creationDate = currentList[0].creationDate;
            // $scope.editParams.userName = currentList[0].userFullName;
            $scope.editParams.qualificationStatusMeaning = currentList[0].qualificationStatusMeaning;
            // $scope.editParams.qualificationStatus = currentList[0].qualificationStatus;
            $scope.editParams.qualificationNumber = currentList[0].qualificationNumber;
            $scope.editParams.qualificationId = currentList[0].qualificationId;
            console.log(currentList[0].supplierId);
            $scope.editParams.supplierId = currentList[0].supplierId;
            // 调用查询所有资质审查信息
            $scope.params.supplierId = $scope.editParams.supplierId;
            $scope.searchAll();
        };

        $scope.changeFile = function () {
            $scope.editParams.fileId = null;
            $scope.editParams.filePath = null;
            $scope.editParams.fileName = null;
        };
        //上传附件
        $scope.uploadFile = function () {

            var f = $scope.myFile;
            var fd = new FormData();
            var file = document.querySelector('input[type=file]').files[0];
            if (!file) {
                SIEJS.alert("请选择上传附件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.editParams.tempSpecialId;
            if (!id) {
                id = '0';
            }
            fd.append('businessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.fileUploadForTempSpecial, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.editParams.fileId = response.data[0].fileId;
                $scope.editParams.fileName = response.data[0].fileName;
                $scope.editParams.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        };

        $scope.btnSave = function (status) {
            //校验
            if (status =='save') {
                if($scope.editParams.specialReason == '' || $scope.editParams.specialReason == undefined){
                    SIEJS.alert('特批原因说明不能为空', 'error', '确定');
                    return;
                }
            }
            $scope.editParams.status = status;
            $scope.editParams.deptId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptId;
            $scope.editParams.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            httpServer.post(URLAPI.saveTempSpecialInfo, {
                'editParams': JSON.stringify($scope.editParams)
            }, function (res) {
                if (res.status == "S") {
                    $scope.editParams = res.data;
                    // 保存后刷新
                    $scope.search($scope.editParams.tempSpecialId,status);
                    $scope.submitFlag = true;
                    SIEJS.alert("操作成功!", "success");
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        };

        $scope.btnCancel = function (status) {
            $scope.editParams.status = status;
            $scope.editParams.deptId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptId;
            $scope.editParams.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            httpServer.post(URLAPI.saveTempSpecialInfo, {
                'editParams': JSON.stringify($scope.editParams)
            }, function (res) {
                if (res.status == "S") {
                    $scope.editParams = res.data;
                    // 保存后刷新
                    $scope.search($scope.editParams.tempSpecialId,status);
                    // $scope.changeButtonShow();
                    SIEJS.alert("取消成功!", "success");
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        };

        // 查询单条记录
        $scope.search = function (id,status) {
            httpServer.post(URLAPI.findTempSpecialDetail, {
                params: JSON.stringify({
                    tempSpecialId: id
                })
            }, function (res) {
                if (res.status == 'S') {
                    console.log(res.data);
                    $scope.editParams = res.data;
                    if (status == 'submit'){
                        $scope.editParams.docStatus = 'AUDITING';
                        $scope.editParams.docStatusMeaning = '待审核';
                    }
                    $scope.changeButtonShow();
                    $scope.processInstanceParams = {
                        processInstanceId: $scope.editParams.procInstId
                    };
                    $scope.taskTable.search();
                }
            }, function (error) {
                console.error(error)
            })
        };

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

        $scope.program = {};
        $scope.supplierProgram = {};
        // //当前登录人所属部门
        $scope.program.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.program.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        //
        // $scope.program.qualificationStatus = 'DRAFT';

        //当前登录人所属部门
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;

        // $scope.params.qualificationStatus = '10';
        // $scope.params.qualificationStatusMeaning = '拟定';
        // $scope.params.departmentName = $scope.params.deptName;
        //当前登录人类型
        // $scope.flags.userTpye = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;

        //联系人目录
        $scope.supplierContactDataTable = [];
        //可合作品类
        $scope.supplierCopCategoryDataTable = [];
        //服务范围
        $scope.supplierServiceScopeDataTable = [];
        //银行信息
        $scope.supplierBankInfoDataTable = [];
        //生产资质信息(OEM)·
        $scope.productQualificationTable = [];
        $scope.productQualificationTable1 = {
            "attachmentName": "生产许可证",
            "fixFlag": "Y",
            "fileName": "",
            "filePath": "",
            "deptmentCode": $scope.params.deptCode,
            "description": "",
            "fileId": "",
            "supplierId": "",
            "invalidDate": "",
            "isProductFactory": "Y",
            "index": "1"
        };
        $scope.productQualificationTable2 = {
            "attachmentName": "质量安全管理体系认证证明",
            "fixFlag": "Y",
            "fileName": "",
            "filePath": "",
            "deptmentCode": $scope.params.deptCode,
            "description": "",
            "fileId": "",
            "supplierId": "",
            "invalidDate": "",
            "isProductFactory": "Y",
            "index": "2"
        };
        $scope.productQualificationTable3 = {
            "attachmentName": "CSR报告",
            "fixFlag": "Y",
            "fileName": "",
            "filePath": "",
            "deptmentCode": $scope.params.deptCode,
            "description": "",
            "fileId": "",
            "supplierId": "",
            "invalidDate": "",
            "isProductFactory": "Y",
            "index": "3"
        };
        $scope.productQualificationTable4 = {
            "attachmentName": "消防验收证明",
            "fixFlag": "Y",
            "fileName": "",
            "filePath": "",
            "deptmentCode": $scope.params.deptCode,
            "description": "",
            "fileId": "",
            "supplierId": "",
            "invalidDate": "",
            "isProductFactory": "Y",
            "index": "4"
        };
        $scope.productQualificationTable5 = {
            "attachmentName": "建筑工程竣工验收报告",
            "fixFlag": "Y",
            "fileName": "",
            "filePath": "",
            "deptmentCode": $scope.params.deptCode,
            "description": "",
            "fileId": "",
            "supplierId": "",
            "invalidDate": "",
            "isProductFactory": "Y",
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
        $scope.itCapacityInfoParams = [];
        //地址信息(IT)
        $scope.itAddressInfoDataTable = [];
        //生产信息(IT)
        // $scope.itProductionInfoDataTable = [];
        //产能信息(IT)
        $scope.itCapacityInfoDataTable = [];
        //资质信息
        $scope.supplierCredentialsProgram = {"deptCode": $scope.params.deptCode};
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

        /********************查询供应商基本信息**********************/
        $scope.searchBaseInfo = function (supplierId) {
            console.log(supplierId)
            httpServer.post(URLAPI.findZzscSupplierInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    deptCode: $scope.params.deptCode
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
                        console.log(res);
                        $scope.productQualificationTable = res.data[0];
                        $scope.productQualificationTable1 = res.data[1];
                        $scope.productQualificationTable2 = res.data[2];
                        $scope.productQualificationTable3 = res.data[3];
                        $scope.productQualificationTable4 = res.data[4];
                        $scope.productQualificationTable5 = res.data[5];
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
                    console.log(res);
                    $scope.oemAddressInfoDataTable = res.data;
                    $scope.oemAddressInfoDataTable.selectRow = 0;
                    var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
                    console.log(row);
                    $scope.loadLinesData(row, 1);
                }
            }, function (error) {
                console.error(error)
            })
        };

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
        };


        // 查看与供应商相关的资质审查信息
        $scope.searchAll = function () {
            $scope.searchBaseInfo($scope.params.supplierId);
            $scope.searchRelatedFactoryInfo();
            $scope.searchCategoryInfo();
            $scope.searchServiceScope();
            $scope.searchContacterInfo();
            $scope.searchCredentialsInfo();
            $scope.searchProductQualificationsInfo();
            $scope.searchProductQualificationsInfo2();
            $scope.searchProductQualificationsInfo3();
            $scope.searchProductQualificationsInfo4();
            $scope.searchProductQualificationsInfo5();
            $scope.searchProductQualificationsInfo6();
            $scope.searchProductQualificationsInfo7();
            $scope.searchQualificationsFileInfo();
            $scope.searchBankInfo();
            $scope.searchOemAddressInfo();
            $scope.searchItAddressInfo();
        };


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
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.editParams.tempSpecialId;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'EQU_LSTP.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
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
                        businessKey: $scope.editParams.tempSpecialId
                    })
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.processImageParams.instanceId = res.data.processInstanceId;
                    }
                    callback && callback(res);
                });
        };

        /**********************************工作流配置**************************************/

        //提交审批
        $scope.btnSubmit = function (status) {

            if ($scope.editParams.docStatus != 'DRAFT' && $scope.editParams.docStatus != 'AUDITING') {
                SIEJS.alert('入库临时特批状态不是拟定或待审核，不允许提交审批', 'error', '确定');
                return;
            }
            $scope.extend = {
                "tasks_assignees": []
            };
            $scope.variables = []; //流程变量
            angular.forEach($scope.editParams, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });

            $scope.properties = {
                "menucode": "LSTP",
                "opinion": ""
            };
            $scope.paramsBpm = {
                "extend": $scope.extend,
                "variables": $scope.variables,
                "properties": $scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": "EQU_LSTP.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.editParams.tempSpecialId, //单据ID
                "title": "入库临时特批流程", //流程标题
                "positionId": 0,
                "orgId": 0,
                "userType": "30",
                "billNo": $scope.editParams.tempSpecialCode
            };
            httpServer.post(URLAPI.bpmStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.search($scope.editParams.tempSpecialId,status);
                    SIEJS.alert('提交成功');
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
