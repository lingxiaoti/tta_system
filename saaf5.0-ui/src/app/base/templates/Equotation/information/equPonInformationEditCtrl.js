/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('equPonInformationEditCtrl', function (wechatFind, $filter, Base64, deleteUserResp, findProfileSqlDatas, $window, iframeTabAction,
                                                          saveUserResp, userSave, $timeout, $scope, httpServer, URLAPI, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS) {
        // ------------------------------------------------初始化------------------------------------------------
        var informationId = $stateParams.informationId
        $scope.params = {}
        $scope.param = {}//lov查询参数
        var saafsuccessLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
        //当前登录人所属部门
        $scope.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;
        $scope.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userFullName;
        $scope.param.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.params.department = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.param.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;
        $scope.param.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.param.userNumber = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;
        $scope.param.employeeNumber = JSON.parse(sessionStorage[appName + '_successLoginInfo']).employeeNumber
        $scope.witnessTeamDataTable = [];
// ------------------------------------------------调整------------------------------------------------

        $scope.edit = function (standardsId) {
            if(standardsId){
                iframeTabAction('评分标准详情', 'equPonStandardsEdit/' + standardsId)
            }

        }


        /********************查询立项资料信息**********************/
        $scope.searchProjectFileInfo = function () {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.params.projectId,
                    fileType: "ProjectInfo"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.projectInfoDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询产品规格信息**********************/
        $scope.searchProductSpecsInfo = function () {
            httpServer.post(URLAPI.findProductSpecs, {
                params: JSON.stringify({
                    projectId: $scope.params.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.productSpecsDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询邀请供应商列表********************/
        $scope.searchSupplierInvitationInfo = function () {
            httpServer.post(URLAPI.findSupplierInvitation, {
                params: JSON.stringify({
                    projectId: $scope.params.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierInvitationDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询非价格文件信息********************/
        $scope.searchNonPriceFileInfo = function () {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.params.projectId,
                    fileType: "NonPriceFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.nonPriceFileDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };
        $scope.quitInvitation = function (item, index) {

            if (item.reason == null) {
                SIEJS.alert("请填写退出原因", "success", "确定");
            } else {
                SIEJS.confirm('退出', '是否退出该供应商,确定后将保存数据.', '确定', function () {
                    httpServer.post(URLAPI.saveProjectSupplierQuit, {
                        params: JSON.stringify({
                            invitationId: item.invitationId,
                            projectId: item.projectId,
                            supplierId: item.supplierId,
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            item.isQuit = 'Y';
                            item.isEdit = 'Y';

                        }
                    }, function (error) {
                        console.error(error)
                    })
                })
            }
        }

        /********************查询非价格文件(选择)信息********************/
        $scope.searchNonPriceSelectFileInfo = function () {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.params.projectId,
                    fileType: "NonPriceSelectFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.nonPriceSelectFileDataTable = res.data;
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询价格文件信息********************/
        $scope.searchPriceFileInfo = function () {
            httpServer.post(URLAPI.findProjectAttachment, {
                params: JSON.stringify({
                    projectId: $scope.params.projectId,
                    fileType: "PriceFile"
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.priceFileDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询评分小组信息********************/
        $scope.searchScoringTeamInfo = function () {
            httpServer.post(URLAPI.findScoringTeam, {
                params: JSON.stringify({
                    projectId: $scope.params.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.scoringTeamDataTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询见证小组信息********************/
        $scope.searchwitnessTeamInfo = function () {
            httpServer.post(URLAPI.findWitnessTeam, {
                params: JSON.stringify({
                    projectId: $scope.params.projectId,
                    searchType: 'information'
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.witnessTeamDataTable = res.data;
                    $scope.openFlag = false;
                    console.log($scope.param.userNumber)
                    //只有见证人才能开启
                    if($scope.params.informationStatus == '20'){
                        for(var i=0;i<$scope.witnessTeamDataTable.length;i++){
                            //开启权限
                            if(($scope.witnessTeamDataTable[i].attribute1==null || $scope.witnessTeamDataTable[i].attribute1===undefined||$scope.witnessTeamDataTable[i].attribute1!='Y')&&
                                ($scope.witnessTeamDataTable[i].defaultMember== $scope.param.employeeNumber|| $scope.witnessTeamDataTable[i].witnessMember== $scope.param.employeeNumber)){
                                $scope.openFlag = true;
                            }
                            //保存人员信息权限
                            if($scope.witnessTeamDataTable[i].defaultMember== $scope.param.employeeNumber ){
                                $scope.saveLFlag = true;
                            }
                        }
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        $scope.changeShowFlag = function () {
            $scope.saveFlag = false;
            $scope.savesFlag = false;
            $scope.overFlag = false;
            $scope.monitoringFlag = false;
            $scope.overFlag = false;
            $scope.saveLFlag = false;


            if ($scope.params.informationStatus != '20' && $scope.params.informationStatus != '30'&&$scope.params.createdBy==$scope.userId) {
                $scope.saveFlag = true;  //提交与审批状态不能保存
            }
            if ($scope.params.projectNumber != null && $scope.params.createdBy==$scope.userId) {
                $scope.monitoringFlag = true;  //监控报价
            }
            if ($scope.params.informationStatus == '20') {
                $scope.approveFlag = true;  //提交与审批状态不能保存
                $scope.rejectFlag = true;
            }
            //拟定和驳回状态,并且是本人操作

            if(($scope.params.informationStatus == '10'||$scope.params.informationStatus == '40')&&$scope.params.createdBy==$scope.userId){
                // $scope.saveLFlag = true;
            }

            //保存见证人员按钮控制

            if(($scope.params.informationStatus == '40')&&$scope.params.createdBy==$scope.userId){
                $scope.overFlag = true;
            }
        }

        //评分小组-选择成员
        $scope.getScoringTeamMember = function (value) {
            $('#selectScoringMemberLov').modal('toggle')
        };

        if (informationId) {
            $scope.params.informationId = informationId;
            httpServer.post(URLAPI.findInformationIdDatail, {
                params: JSON.stringify($scope.params)
            }, function (res) {
                if (res.status == "S") {
                    $scope.params = res.data;
                    $scope.param.userNumber = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;
                    //修改按键显示隐藏
                    //查询各个子信息
                    $scope.search();
                    $scope.changeShowFlag();
                }
            }, function (error) {
                console.error(error);
            });
        } else {
            $scope.params.createdByName = saafsuccessLoginInfo.userFullName;
            $scope.params.createdBy= saafsuccessLoginInfo.userId;
            $scope.params.creationDate = CurentTime();
            $scope.params.informationStatus = '10';
            $scope.param.userNumber = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;
            $scope.changeShowFlag();
        }


        //查询子表信息
        $scope.search = function () {
            $scope.searchProjectFileInfo();
            $scope.searchProductSpecsInfo();
            $scope.searchSupplierInvitationInfo();
            $scope.searchNonPriceFileInfo();
            $scope.searchNonPriceSelectFileInfo();
            $scope.searchPriceFileInfo();
            $scope.searchScoringTeamInfo();
            $scope.searchwitnessTeamInfo();
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
            httpServer.post(URLAPI.findSupplierInfo, {
                params: JSON.stringify({
                    supplierId: supplierId,
                    deptCode: $scope.params.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierParams = res.data[0];
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商关联工厂**********************/
        $scope.searchRelatedFactoryInfo = function () {
            httpServer.post(URLAPI.findAssociatedSupplier, {
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
            httpServer.post(URLAPI.findSupplierCategorysInfo, {
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
            httpServer.post(URLAPI.findSupplierCategorysInfo, {
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
            httpServer.post(URLAPI.findSupplierContactsInfo, {
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
            httpServer.post(URLAPI.findSupplierCredentialsInfo, {
                params: JSON.stringify({
                    supplierId: $scope.params.supplierId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierCredentialsParams = res.data[0];

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商生产资质信息**********************/
        $scope.searchProductQualificationsInfo = function () {
            httpServer.post(URLAPI.findCredentialsAttachmentInfo, {
                params: JSON.stringify({
                    supplierId: $scope.params.supplierId,
                    isProductFactory: 'Y'
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.productQualificationTable = res.data;

                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************查询供应商资质文件信息**********************/
        $scope.searchQualificationsFileInfo = function () {
            httpServer.post(URLAPI.findCredentialsAttachmentInfo, {
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
            httpServer.post(URLAPI.findSupplierBankInfo, {
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
            httpServer.post(URLAPI.findSupplierAddressInfo, {
                params: JSON.stringify({
                    supplierId: $scope.params.supplierId,
                    deptCode: $scope.params.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.oemAddressInfoDataTable = res.data;
                    $scope.oemAddressInfoDataTable.selectRow = 0;
                    var row = $scope.oemAddressInfoDataTable[$scope.oemAddressInfoDataTable.selectRow];
                    if ($scope.oemAddressInfoDataTable.length > 0) {
                        $scope.loadLinesData(row, 1);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        //选中地址行时，加载关联行数据
        $scope.loadLinesData = function (row, $index) {
            $scope.oemProductionInfoParams = row.oemProductionInfoParams;
            $scope.oemCapacityInfoDataTable = row.oemCapacityInfoParams;
        }

        /********************查询IT地址信息**********************/
        $scope.searchItAddressInfo = function () {
            httpServer.post(URLAPI.findSupplierAddressInfo, {
                params: JSON.stringify({
                    supplierId: $scope.params.supplierId,
                    deptCode: $scope.params.deptCode
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.itAddressInfoDataTable = res.data;
                    $scope.itAddressInfoDataTable.selectRow = 0;
                    var row = $scope.itAddressInfoDataTable[$scope.itAddressInfoDataTable.selectRow];
                    if ($scope.itAddressInfoDataTable.length > 0) {
                        $scope.loadITLinesData(row, 1);
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        //打开立项LOV
        $scope.showComplain = function(val){
            $scope.current.deptCode = $scope.deptCode
            $scope.current.deptName = $scope.deptName
            $scope.current.createdBy = $scope.userId
            $scope.current.userName = $scope.userName
            $scope.current.projectStatus = '30'
            $('#complainModal').modal('toggle');
        }

        //选择立项lov确认
        $scope.callback = function(){
            $scope.params.projectNumber = $scope.currentList.projectNumber;
            $scope.params.projectName = $scope.currentList.projectName;
            $scope.params.projectId = $scope.currentList.projectId;
            $scope.params.standardsId = $scope.currentList.standardsId;
            $scope.params.projectVersion=$scope.currentList.projectVersion;
            $scope.params.projectStatus = $scope.currentList.projectStatus;
            $scope.params.relevantCatetory = $scope.currentList.relevantCatetory;
            $scope.params.projectCategory = $scope.currentList.projectCategory;
            $scope.params.seriesName = $scope.currentList.seriesName;
            $scope.params.projectPurchaseAmount = $scope.currentList.projectPurchaseAmount;
            $scope.params.projectCycle = $scope.currentList.projectCycleFrom+"至"+$scope.currentList.projectCycleTo;
            $scope.params.projectCycleFrom = $scope.currentList.projectCycleFrom;
            $scope.params.projectCycleTo = $scope.currentList.projectCycleTo;
            $scope.params.quotationDeadline=$scope.currentList.quotationDeadline;
            $scope.params.standardsCode = $scope.currentList.scoringStandard;
            $scope.params.presentCooperationSupplier = $scope.currentList.presentCooperationSupplier;
            $scope.params.sensoryTestTypes = $scope.currentList.sensoryTestTypes;
            $scope.params.remark = $scope.currentList.remark;
            $scope.search();
        }

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

        //选择评分标准
        $scope.selectScoringStandard = function (value) {
            $scope.standard.standardsStatus = '30';
            $scope.standard.standardsStatusName = '生效';
            $scope.standard.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            $scope.standard.department = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            $('#selectScoringStandardLov').modal('toggle')
        }

        //选择评分标准-回调
        $scope.selectScoringStandardReturn = function () {
            $scope.params.standardsCode = $scope.currentList.standardsCode;
            $scope.params.standardsId = $scope.currentList.standardsId;
        }

        $scope.selectStandardRow = function (row,lovStandardTable) {
            for(var i = 0;i<lovStandardTable.length;i++){
                lovStandardTable[i].isSelect = 'N'
            }
            $scope.currentList = row
            $scope.isLovSelect = 'Y';
            $scope.params.radioValue1 = row.standardsCode;
            row.isSelect = 'Y';
        }

        $scope.selectSupplierLov = function (key, value, currentList) {
            $scope.params.supplierStatus = currentList[0].supplierStatus;
            $scope.params.supplierId = currentList[0].supplierId;
            $scope.params.supplierNumber = currentList[0].supplierNumber;
            $scope.params.supplierName = currentList[0].supplierName;
        };

        $scope.selectQualificationLov = function (key, value, currentList) {
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
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.params.fileId = response.data[0].fileId;
                $scope.params.fileName = response.data[0].fileName;
                $scope.params.filePath = response.data[0].filePath;
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        $scope.selectRow = function (row,lovDataTable) {
            if(row.projectStatus =='30'){
                for(var i = 0;i<lovDataTable.length;i++){
                    lovDataTable[i].isSelect = 'N'
                }
                $scope.currentList = row
                $scope.isLovSelect = 'Y';
                $scope.params.radioValue = row.projectNumber
                row.isSelect = 'Y';
            }
        }
        $scope.changeFile = function () {
            $scope.params.fileId = null;
            $scope.params.filePath = null;
            $scope.params.fileName = null;
        }
// ------------------------------------------------保存------------------------------------------------

        $scope.saveDatil = function () {
            $scope.params.supplierTable = []
            $scope.params.supplierInvitationDataTable =  []
            if($scope.supplierInvitationDataTable!=null){
                for( var i = 0; i< $scope.supplierInvitationDataTable.length;i++){
                    var supplierLine = $scope.supplierInvitationDataTable[i];
                    if(supplierLine.isEdit !=null && supplierLine.isEdit == 'Y'){
                        $scope.params.supplierTable.push($scope.supplierInvitationDataTable[i])
                    }
                   if(supplierLine.isQuit!='Y'){
                       $scope.params.supplierInvitationDataTable.push($scope.supplierInvitationDataTable[i])
                   }
                }
            }
            httpServer.post(URLAPI.savePonInvitation, {
                'params': JSON.stringify($scope.params),
            }, function (res) {
                if (res.status == "S") {
                    informationId= res.data.informationId;
                    $scope.params.informationId = res.data.informationId;
                    $scope.params.informationStatus = res.data.informationStatus;
                    $scope.params.supWarehousingCode = res.data.supWarehousingCode;
                    $scope.params.informationCode = res.data.informationCode;
                    $scope.params.informationStatus = res.data.informationStatus;
                    $scope.params.creationDate = res.data.creationDate;
                    if($scope.params.action=='save'){
                        $scope.saveLI()
                    }else{
                        $scope.search();
                    }
                    $scope.changeShowFlag();
                    SIEJS.alert("操作成功!", "success");
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
            });
        }

        $scope.saveLI = function () {
            $scope.params.witnessTeamDataTable = $scope.witnessTeamDataTable;
            $scope.params.scoringTeamDataTable = $scope.scoringTeamDataTable;

            httpServer.post(URLAPI.saveWitnessTeamData, {
                'params': JSON.stringify($scope.params),
            }, function (res) {
                if (res.status == "S") {
                    $scope.search();
                } else {
                    SIEJS.alert("保存见证人失败", 'error', '确定');
                }
            }, function (error) {
                console.error(error);
            });
        }

        $scope.saveL = function () {
            $scope.params.witnessTeamDataTable = $scope.witnessTeamDataTable;
            $scope.params.scoringTeamDataTable = null;
            httpServer.post(URLAPI.saveWitnessTeamData, {
                'params': JSON.stringify($scope.params),
            }, function (res) {
                if (res.status == "S") {
                    SIEJS.alert("保存见证人成功!", "success");
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
            });
        }

        $scope.monitoring = function () {

            // var witnessFlag = 'Y';
            // for(var i=0;i<$scope.witnessTeamDataTable.length;i++){
            //     if($scope.witnessTeamDataTable[i].isOpen!='开启'){
            //         witnessFlag = 'N';
            //     }
            // }

            var witnessFlag = 'N';
            if($scope.params.informationStatus=='30'){
                witnessFlag = 'Y';
            }
            iframeTabAction('监控报价', 'monitorPriceInfo/'+$scope.params.informationId+'/'+witnessFlag)
            // iframeTabAction('报价资料开启详情：', 'equPonInformationEdit/' + row.informationId)
        }

        //见证小组-选择见证人-回调
        $scope.selectWitnessMemberReturn = function (key, value, currentList) {
            var row = $scope.witnessTeamDataTable[$scope.witnessTeamDataTable.selectRow];
            row.witnessMember = currentList[0].employeeNumber;
            row.witnessMemberName = currentList[0].personName;
        }

        //见证小组-选择见证人
        $scope.getWitnessTeamMember = function (roleName) {
            // $scope.selectCategoryParams = {
            //     "deptCode" : $scope.program.deptCode
            // };
            // var row = $scope.witnessTeamDataTable[$scope.witnessTeamDataTable.selectRow];
            if (roleName == 'IA') {
                $('#selectWitnessIAMemberLov').modal('toggle')
            } else if (roleName == 'Security') {
                $('#selectWitnessSecurityMemberLov').modal('toggle')
            } else if (roleName == 'QA') {
                $('#selectWitnessQAMemberLov').modal('toggle')
            }
        };

        //见证小组-选择默认人员
        $scope.getWitnessTeamDefaultMember = function (roleName) {
            // $scope.selectCategoryParams = {
            //     "deptCode" : $scope.program.deptCode
            // };
            // var row = $scope.witnessTeamDataTable[$scope.witnessTeamDataTable.selectRow];
            $('#selectWitnessIADefaultMemberLov').modal('toggle')
            // if(roleName == 'IA') {
            //     $('#selectWitnessIADefaultMemberLov').modal('toggle')
            // }else if(roleName == 'Security'){
            //     $('#selectWitnessSecurityDefaultMemberLov').modal('toggle')
            // }else if(roleName == 'QA'){
            //     $('#selectWitnessQADefaultMemberLov').modal('toggle')
            // }
        };

        //见证小组-选择默认人员-回调
        $scope.selectWitnessDefaultMemberReturn = function (key, value, currentList) {
            var row = $scope.witnessTeamDataTable[$scope.witnessTeamDataTable.selectRow];
            row.defaultMember = currentList[0].employeeNumber;
            row.defaultMemberName = currentList[0].personName;
        }

        //评分小组-选择成员-回调
        $scope.selectScoringMemberReturn = function (key, value, currentList) {
            var row = $scope.scoringTeamDataTable[$scope.scoringTeamDataTable.selectRow];
            row.memberName = currentList[0].personName;
            row.memberNumber = currentList[0].employeeNumber;
        }

        $scope.saveSupplierInvitationRemark = function () {
            $scope.param.witnessTeamDataTable = $scope.witnessTeamDataTable;
            httpServer.post(URLAPI.saveSupplierInvitationRemark, {
                'params': JSON.stringify($scope.param),
            }, function (res) {
                if (res.status == 'S') {
                    SIEJS.alert("操作成功!", "success");
                }
            }, function (error) {
                console.error(error)
            })
        }

        $scope.showBidList = function (index) {
            console.log(index)
            console.log($scope.witnessTeamDataTable)
            console.log($scope.witnessTeamDataTable[index])
            // $scope.witnessTeamDataTable[index].isSelected = !$scope.monitorBidPriceData[index].isSelected;
            // console.log($scope.witnessTeamDataTable[index].isSelected)
        }
        $scope.save = function (action) {
            $scope.params.action = action;
            //验证相关人员信息必填
            if('submit'==action){
                SIEJS.confirm('确认', '是否确认立项资料', '确定', function () {
                    for(var i = 0;i<$scope.scoringTeamDataTable.length;i++){
                        if($scope.scoringTeamDataTable[i].memberName==null){
                            SIEJS.alert("请完善评分小组人员", "error");
                            return
                        }
                    }
                    for(var i = 0;i<$scope.witnessTeamDataTable.length;i++){
                        if($scope.witnessTeamDataTable[i].defaultMemberName==null&&$scope.witnessTeamDataTable[i].remarkFlag!='Y'){
                            SIEJS.alert("请完善见证小组人员", "error");
                            return
                        }
                    }
                    $scope.saveDatil();
                })
            }else if('reject'==action){
                if(!$scope.params.rejectReason){
                    SIEJS.alert("请填写驳回原因", "success");
                    return
                }
                SIEJS.confirm('驳回','确认驳回报价资料开启？','确定',function() {
                    $scope.saveDatil();
                })
            }else if('over'==action){
                SIEJS.confirm('终止','确认终止报价资料开启？','确定',function() {
                    $scope.saveDatil();
                })
            }else if('open'==action){
                SIEJS.confirm('开启','确认见证开启？','确定',function() {
                    $scope.saveDatil();
                })
            }else{
                $scope.saveDatil();
            }


        }

    });
});
