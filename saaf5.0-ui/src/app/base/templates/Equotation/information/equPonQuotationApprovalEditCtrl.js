/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('equPonQuotationApprovalEditCtrl', function (wechatFind, $filter, Base64, deleteUserResp, iframeTabAction, findProfileSqlDatas, $window,
                                                                saveUserResp, userSave, $timeout, $scope, httpServer, URLAPI, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS,$controller) {
        // ------------------------------------------------初始化------------------------------------------------
        $controller('processBase', {$scope:$scope}); // 继承基础的流程控制器
        $scope.params = {}
        $scope.param = {}
        $scope.param.businessFlag = true;
        var approvalId = $stateParams.approvalId
        if ($stateParams.approvalId){
            approvalId = $stateParams.approvalId;
        }else if ($scope.urlParams.businessKey){
            approvalId = $scope.urlParams.businessKey;
            $scope.param.businessFlag = false;
        }

        $scope.doubleData = [];
        $scope.lovDataTable = {}
        $scope.nonPriceCalColumns = [];
        $scope.nonPriceCalChange = [];
        $scope.secondResultTable = [];
        $scope.nonPriceCalTable = [];
        $scope.columns = []
        $scope.quotationCalRankParamter = {};
        $scope.supplierInvitationListColumns = [];

        $scope.firstRountParamter = {};
        $scope.secondRountParamter = {};
        $scope.operationRemarkParamter = {};

        var saafsuccessLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
        $scope.params.userFullName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userFullName;

        $scope.param.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.param.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;
        $scope.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userFullName;
// ------------------------------------------------调整------------------------------------------------
        $scope.changeShowFlag = function () {
            $scope.saveFlag = false;
            $scope.submitFlag= false;
            $scope.approveFlag= false;
            $scope.rejectFlag= false;
            $scope.stopFlag= false;
            $scope.cancelFlag= false;
            $scope.editFlag= false;
            if($scope.params.approvalStatus=='50'){
                $scope.editFlag= true;//驳回才能终止
            }
            if($scope.params.approvalStatus=='40'){
                $scope.stopFlag= true;//驳回才能终止
            }
            if($scope.params.approvalStatus!='20'&&$scope.params.approvalStatus!='30'&&$scope.params.approvalStatus!='50'){
                $scope.saveFlag = true;  //提交与审批状态不能保存
            }
            if($scope.params.approvalStatus=='20'){
                $scope.approveFlag = true;  //提交与审批状态不能保存
                $scope.rejectFlag= true;
            }
            if($scope.params.approvalStatus=='50'&&saafsuccessLoginInfo.userId == $scope.params.createdBy){
                $scope.cancelFlag = true;   //驳回状态本人可以取消
            }
            if($scope.params.approvalStatus=='10'||$scope.params.approvalStatus=='50'){
                $scope.editFlag= true; //拟定与驳回状态才能编辑
            }
            if($scope.userId == $scope.params.createdBy){
                if($scope.params.orderType == '10'&&($scope.params.approvalStatus==10||$scope.params.approvalStatus==40)){
                    $scope.submitFlag= true;
                }
                if($scope.params.orderType == '20'&&$scope.params.approvalStatus==60){
                    $scope.submitFlag= true;
                }
            }
        }


        $scope.edit = function (standardsId) {
            iframeTabAction('评分标准', 'equPonStandardsEdit/' + standardsId)
        }
        $scope.sedit = function (row) {
            iframeTabAction('二次报价详情', 'completeQuotationInfo/complete/' + row.sQuotationId)
        }
        $scope.qedit = function (row) {
            iframeTabAction('报价详情', 'completeQuotationInfo/complete/' + row.quotationId)
        }

        //二次报价对比信息
        $scope.getDoubleData = function () {

            httpServer.post(URLAPI.findPonDoubleData, {
                'params': JSON.stringify($scope.params),
            }, function (res) {
                if (res.status == "S") {
                    $scope.colums = res.data.colums;
                    $scope.doubleData = res.data.rows;
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        }

        /**
         * 计算二次议价结果
         */
        $scope.getDoubleTotalData = function () {
            httpServer.post(URLAPI.saveDoubleTotalData, {
                'params': JSON.stringify($scope.params),
            }, function (res) {
                if (res.status == "S") {
                    $scope.getPonDouQouData();
                    SIEJS.alert("计算成功", "success", "确定");
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        }

        /**
         * 计算二次之前的验证
         */
        $scope.getDoubleTotal = function () {
            httpServer.post(URLAPI.findDoubleTotalAble, {
                'params': JSON.stringify($scope.params),
            }, function (res) {
                if (res.status == "S") {
                    SIEJS.confirm('确定', '确定计算二次报价总得分', '确定', function () {
                        $scope.getDoubleTotalData();
                    })
                } else if(res.status == "L") {
                    SIEJS.confirm('确定', '供应商'+res.data+"未提交二次报价,确定计算二次报价总得分", '确定', function () {
                        $scope.getDoubleTotalData();
                    })
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        }


        $scope.findScoringStandards = function () {
            httpServer.post(URLAPI.findPonStandardsDatail, {
                params: JSON.stringify({
                    standardsId: $scope.params.standardsId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.nonPriceCalTable = angular.copy(res.data.lineData);
                    var totalWeighting = 0;
                    for (var i = 0; i < $scope.nonPriceCalTable.length; i++) {
                        if ($scope.nonPriceCalTable[i].lineLv == 2 && $scope.nonPriceCalTable[i].weight != undefined && $scope.nonPriceCalTable[i].gradingDivision != 'Cost') {
                            if ($scope.params.sensoryTestTypes != '10' || $scope.nonPriceCalTable[i].gradingDivision != 'Panel test') {
                                totalWeighting = totalWeighting + $scope.nonPriceCalTable[i].weight;
                            }
                        }
                    }
                    $scope.nonPriceCalTotalParamter.weight = totalWeighting;
                }
            }, function (error) {
                console.error(error)
            })
        }

        //确认供应商列表
        $scope.supplierInvitationConfirm = function () {
            //修改supplierConfirmFlag字段为Y,失效邀请供应商列表操作按钮
            JS.confirm('确认', '确认后将不能再修改供应商列表，是否确认？', '确定', function () {
                $scope.params.supplierConfirmFlag = 'Y';
                SIEJS.alert("确认成功!", "success", "确定");
                //查询panel test 产品列表
                $scope.findScoringStandards();
                //显示页签上的供应商列
                for (var i = 0; i < $scope.supplierInvitationDataTable.length; i++) {
                    if ($scope.supplierInvitationDataTable[i].isQuit != 'Y') {
                        //Factory Audit
                        var obj1 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            column: $scope.supplierInvitationDataTable[i].supplierName,
                            defaultItem: ''
                        };
                        $scope.factoryAuditColumns.push(obj1);

                        //New connept from supplier
                        var obj2 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            column: $scope.supplierInvitationDataTable[i].supplierName,
                            defaultItem: '',
                            totalScore: ''
                        };
                        $scope.newConceptRromSupplierColumns.push(obj2);

                        //Component support
                        var obj3 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            column: $scope.supplierInvitationDataTable[i].supplierName,
                            innerPackage: '',
                            outerPackage: '',
                            totalScore: ''
                        };
                        $scope.componentSupportColumns.push(obj3);

                        //Effective & accurate feedback
                        var obj4 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            column: $scope.supplierInvitationDataTable[i].supplierName,
                            defaultItem: ''
                        };
                        $scope.effectiveAccurateFeedbackColumns.push(obj4);

                        //Payment terms
                        var obj5 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            column: $scope.supplierInvitationDataTable[i].supplierName,
                            paymentTerms: ''
                        };
                        $scope.paymentTermsColumns.push(obj5);

                        //Panel test
                        var obj9 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            supplierName: $scope.supplierInvitationDataTable[i].supplierName,
                            supplierNumber: $scope.supplierInvitationDataTable[i].supplierNumber
                        };
                        $scope.panelTestColumns.push(obj9);

                        //非价格计算
                        var obj10 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            supplierName: $scope.supplierInvitationDataTable[i].supplierName,
                            supplierNumber: $scope.supplierInvitationDataTable[i].supplierNumber
                        };
                        $scope.nonPriceCalColumns.push(obj10);

                        //Cost
                        var obj11 = {
                            supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                            supplierName: $scope.supplierInvitationDataTable[i].supplierName,
                            supplierNumber: $scope.supplierInvitationDataTable[i].supplierNumber
                        };
                        $scope.costColumns.push(obj11);

                        //查询供应商有效的基础分数（commercialContract，currentSupplierPerformance，supplierSpendProfile）
                        httpServer.post(URLAPI.findSupplierBaseScore, {
                            params: JSON.stringify({
                                supplierNumber: $scope.supplierInvitationDataTable[i].supplierNumber
                            })
                        }, function (res) {
                            if (res.status == 'S') {
                                var obj6 = {
                                    supplierId: res.data[0].supplierId,
                                    column: res.data[0].supplierName,
                                    defaultItem: res.data[0].commercialContractScore
                                };
                                $scope.commercialContractColumns.push(obj6);

                                var obj5 = {
                                    supplierId: res.data[0].supplierId,
                                    column: res.data[0].supplierName,
                                    defaultItem: res.data[0].currentPerformanceScore
                                };
                                $scope.currentSupplierPerformanceColumns.push(obj5);

                                var obj7 = {
                                    supplierId: res.data[0].supplierId,
                                    column: res.data[0].supplierName,
                                    defaultItem: res.data[0].spendProfileScore
                                };
                                $scope.supplierSpendProfileColumns.push(obj7);
                            }
                        }, function (error) {
                            console.error(error)
                        })
                    }
                }
            })
        }



        $scope.selectProjectRow = function (row, lovDataTable) {

            for(var i = 0;i<lovDataTable.length;i++){
                lovDataTable[i].isSelect = 'N'
            }
            $scope.currentList = row
            $scope.isLovSelect = 'Y';
            $scope.params.radioValue = row.scoringNumber
            row.isSelect = 'Y';

        }

        $scope.selectRow = function (row, lovDataTable) {
            if($scope.params.approvalStatus=='60'){
                if(row.isSelect!=null && row.isSelect == 'Y'){
                    row.isSelect = 'N'
                }else{
                    row.isSelect = 'Y'
                }
            }
        }

        //选择立项lov确认
        $scope.callback = function(){

            $scope.params.standardsId = $scope.currentList.standardsId;
            $scope.params.scoringNumber = $scope.currentList.scoringNumber;
            $scope.params.projectName = $scope.currentList.projectName;
            $scope.params.deptCode = $scope.currentList.deptCode;
            $scope.params.projectNumber = $scope.currentList.projectNumber;
            $scope.params.scoringId = $scope.currentList.scoringId;
            $scope.params.brandTeamUserId = $scope.currentList.brandTeamUserId;
            $scope.params.projectId = $scope.currentList.projectId;
            $scope.params.projectStatus = $scope.currentList.projectStatus;
            $scope.params.relevantCatetory = $scope.currentList.relevantCatetory;
            $scope.params.projectCategory = $scope.currentList.projectCategory;
            $scope.params.seriesName = $scope.currentList.seriesName;
            $scope.params.qaUserId = $scope.currentList.qaUserId;
            $scope.params.iaUserId = $scope.currentList.iaUserId;
            $scope.params.securityUserId = $scope.currentList.securityUserId;
            $scope.params.qaUserNumber = $scope.currentList.qaUserNumber;
            $scope.params.iaUserNumber = $scope.currentList.iaUserNumber;
            $scope.params.securityUserNumber = $scope.currentList.securityUserNumber;
            $scope.params.projectPurchaseAmount = $scope.currentList.projectPurchaseAmount;
            $scope.params.projectCycle = $scope.currentList.projectCycleFrom+"至"+$scope.currentList.projectCycleTo;
            $scope.params.projectCycleFrom = $scope.currentList.projectCycleFrom;
            $scope.params.projectCycleTo = $scope.currentList.projectCycleTo;
            $scope.params.quotationDeadline=$scope.currentList.quotationDeadline;
            $scope.params.standardsCode = $scope.currentList.scoringStandard;
            $scope.params.scoringStandard = $scope.currentList.scoringStandard;
            $scope.params.presentCooperationSupplier = $scope.currentList.presentCooperationSupplier;
            $scope.params.sensoryTestTypes = $scope.currentList.sensoryTestTypes;
            $scope.params.remark = $scope.currentList.remark;
            $scope.getLineData();
        }

        //二次议价结果
        $scope.getPonDouQouData = function () {
            httpServer.post(URLAPI.findPonDouQouData, {
                'params': JSON.stringify($scope.params),
            }, function (res) {
                if (res.status == "S") {
                    try {
                        $scope.secondResultTable.data = res.data.data;
                    }
                    catch(err){
                    }
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        }

        /********************查询邀请供应商列表**********************/
        $scope.searchSupplierInvitationInfo = function () {

            console.log($scope.params.projectId)
            httpServer.post(URLAPI.findSupplierInvitation, {
                params: JSON.stringify({
                    projectId: $scope.params.projectId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.supplierInvitationDataTable = res.data;
                    $scope.nonPriceCalColumns = [];
                    for (var i = 0; i < $scope.supplierInvitationDataTable.length; i++) {
                        //Panel test
                        if ($scope.supplierInvitationDataTable[i].isQuit != 'Y') {
                            var obj = {
                                supplierId: $scope.supplierInvitationDataTable[i].supplierId,
                                supplierName: $scope.supplierInvitationDataTable[i].supplierName,
                                supplierNumber: $scope.supplierInvitationDataTable[i].supplierNumber
                            };
                            $scope.nonPriceCalColumns.push(obj);
                        }
                    }
                    console.log('nonPriceCalColumns===')
                    console.log($scope.nonPriceCalColumns)
                    //如果供应商列表已确认，则查询每个页签的分数
                    if ($scope.params.supplierConfirmFlag == 'Y') {
                        $scope.searchScoringDetail();
                    }

                    //报价结果计算 排名行查询
                    $scope.quotationCalRankParamter = {};
                    httpServer.post(URLAPI.findScoringDetail, {
                        params: JSON.stringify({
                            scoringId: $scope.params.scoringId,
                            scoringItem: 'totalRanking',
                            scoringType: 'quotationTotalResult'
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            for (var i = 0; i < res.data.length; i++) {
                                $scope.quotationCalRankParamter.productName = res.data[i].scoringItem;
                                $scope.quotationCalRankParamter[res.data[i].supplierNumber] = res.data[i].scoringRanking;
                                $scope.quotationCalRankParamter[res.data[i].supplierId] = res.data[i];
                            }
                        }
                    }, function (error) {
                        console.error(error)
                    })

                    $scope.supplierInvitationListColumns = [];
                    for (var i = 0; i < $scope.supplierInvitationDataTable.length; i++) {
                        var supplierId = $scope.supplierInvitationDataTable[i].supplierId;
                        var supplierName = $scope.supplierInvitationDataTable[i].supplierName;
                        var supplierNumber = $scope.supplierInvitationDataTable[i].supplierNumber;
                        var isQuit = $scope.supplierInvitationDataTable[i].isQuit;
                        var reason = $scope.supplierInvitationDataTable[i].reason;
                        var obj = {
                            supplierId: supplierId,
                            supplierName: supplierName,
                            supplierNumber: supplierNumber
                        };
                        $scope.supplierInvitationListColumns.push(obj);

                        $scope.firstRountParamter[supplierNumber] = isQuit == 'Y' ? 'N' : 'Y';
                        $scope.secondRountParamter[supplierNumber] = isQuit == 'Y' ? 'N' : 'Y';
                        $scope.operationRemarkParamter[supplierNumber] = reason;
                        $scope.operationRemarkParamter[supplierId] = $scope.supplierInvitationDataTable[i];
                    }

                    //报价结果计算 汇总行查询
                    $scope.quotationCalTotalParamter = {};
                    httpServer.post(URLAPI.findScoringDetail, {
                        params: JSON.stringify({
                            scoringId: $scope.params.scoringId,
                            scoringItem: 'totalScore',
                            scoringType: 'quotationTotalResult'
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            for(var i = 0; i < res.data.length; i++){
                                $scope.quotationCalTotalParamter.productName = res.data[i].scoringItem;
                                $scope.quotationCalTotalParamter.weight = res.data[i].weighting;
                                $scope.quotationCalTotalParamter[res.data[i].supplierNumber] = res.data[i].score;
                                $scope.quotationCalTotalParamter[res.data[i].supplierId] = res.data[i];
                            }
                        }
                    }, function (error) {
                        console.error(error)
                    })
                    //非价格资料计算
                    $scope.nonPriceCalTable = [];
                    httpServer.post(URLAPI.findPonStandardsDatail, {
                        params: JSON.stringify({
                            standardsId: $scope.params.standardsId
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.standardsTemp = res.data.lineData;
                            httpServer.post(URLAPI.findScoringDetail, {
                                params: JSON.stringify({
                                    scoringId: $scope.params.scoringId,
                                    scoringType: 'nonPriceCal'
                                })
                            }, function (res) {
                                if (res.status == 'S') {
                                    for(var i = 0; i < $scope.standardsTemp.length; i++){
                                        var obj = {};
                                        for(var j = 0; j < res.data.length; j++){
                                            if($scope.standardsTemp[i].gradingDivision + $scope.standardsTemp[i].lineNumber == res.data[j].scoringItem + res.data[j].lineNumber){
                                                obj.gradingDivision = res.data[j].scoringItem;
                                                obj.lineNumber = res.data[j].lineNumber;
                                                obj.weight = res.data[j].weighting;
                                                obj.lineLv = res.data[j].lineLv;
                                                obj[res.data[j].supplierNumber] = res.data[j].score;
                                                obj[res.data[j].supplierId] = res.data[j];
                                            }
                                        }
                                        $scope.nonPriceCalTable.push(obj);
                                    }
                                }
                            }, function (error) {
                                console.error(error)
                            })
                        }
                    }, function (error) {
                        console.error(error)
                    })
                }
            }, function (error) {
                console.error(error)
            })
        };

        //所有行信息
        $scope.getLineData = function () {
            $scope.getDoubleData()
            $scope.getPonDouQouData()
            $scope.searchSupplierInvitationInfo();


        }

        if (approvalId) {
            $scope.params.approvalId = approvalId;
            httpServer.post(URLAPI.findPonQuotationApprovalDatail, {
                // params: JSON.stringify($scope.params)
                params: JSON.stringify({
                    approvalId: approvalId
                })
            }, function (res) {
                if (res.status == "S") {
                    $scope.params = res.data.data;
                    $scope.params.nonPriceCalChange = res.data.nonPriceCalChange;
                    $scope.nonPriceCalChange = [];
                    var obj = {
                        scoringType: 'commercialContract',
                    };
                    if ($scope.params.nonPriceCalChange) {
                        obj = $scope.params.nonPriceCalChange
                    }
                    $scope.nonPriceCalChange.push(obj);

                    $scope.params.userFullName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userFullName;
                    $scope.changeShowFlag();
                    $scope.getLineData();
                    // 查询审批信息
                    $scope.processInstanceParams = {
                        processInstanceId: $scope.params.processInstanceId
                    };
                    $scope.taskTable.search();


                    //查询各个子信息
                    $scope.historicParam = {}
                    $scope.historicParam.code = $scope.params.approvalNumber
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
            //当前登录人所属部门
            $scope.params.department = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            $scope.params.approvalStatus = '10';
            $scope.params.orderType = '10';
            $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            $scope.params.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;
            $scope.params.userFullName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userFullName;
            $scope.changeShowFlag();
            $scope.nonPriceCalChange = [];
            var obj = {
                scoringType: 'commercialContract',
            };

            $scope.nonPriceCalChange.push(obj);
        }



        $scope.selectSupplierLov = function (key, value, currentList) {
            $scope.params.supplierStatus = currentList[0].supplierStatus;
            $scope.params.supplierDeptStatusName = currentList[0].supplierDeptStatusName;
            $scope.params.supplierId = currentList[0].supplierId;
            $scope.params.supplierNumber = currentList[0].supplierNumber;
            $scope.params.supplierName = currentList[0].supplierName;
        };

        function CurentTime()
        {
            var now = new Date();
            var year = now.getFullYear();       //年
            var month = now.getMonth() + 1;     //月
            var day = now.getDate();            //日
            var hh = now.getHours();            //时
            var mm = now.getMinutes();          //分
            var ss = now.getSeconds();           //秒
            var clock = year + "-";
            if(month < 10)
                clock += "0";
            clock += month + "-";
            if(day < 10)
                clock += "0";
            clock += day + " ";
            if(hh < 10)
                clock += "0";
            clock += hh + ":";
            if (mm < 10) clock += '0';
            clock += mm + ":";
            if (ss < 10) clock += '0';
            clock += ss;
            return(clock);
        }

        //打开评分LOV
        $scope.showComplain = function(){
            $scope.current.deptCode = $scope.deptCode
            $scope.current.searchType  = 'quotationApproval'
            $scope.current.userName = $scope.userName
            $scope.current.scoringStatus = '30'
            $('#complainModal').modal('toggle');
        }

        //弹出供应商
        $scope.getSupplierLov = function () {
            $('#supplierLov').modal('toggle')
        };
// ------------------------------------------------保存------------------------------------------------


        $scope.sendResults = function (action) {
            httpServer.post(URLAPI.findApprovalResults, {
                'params': JSON.stringify($scope.params),
            }, function (res) {
                if (res.status == "S") {
                    SIEJS.alert("发送成功!", "success");
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        }
        $scope.save = function (action) {

            if($scope.params.orderType == '20'&&$scope.params.secondQuotationDeadline == null){
                SIEJS.alert("二次议价截止日期不能为空", "warning");
                return
            }

            if (action == 'submit'||'second'==action) {
                //整理供应商的报价
                var isNumOneSelect = 'N';
                var selectTotal = 0;
                var supplierScore = $scope.nonPriceCalColumns;
                for (var i = 0; i < supplierScore.length; i++) {
                    var supplierNumber = supplierScore[i].supplierNumber;
                    var supplierId = supplierScore[i].supplierId;
                    supplierScore[i].isSelect = $scope.nonPriceCalChange[0][supplierNumber];
                    try {
                        if (supplierScore[i].isSelect != null && supplierScore[i].isSelect == 'Y') {
                            selectTotal = selectTotal + 1;
                        }
                        supplierScore[i].scoringRanking = $scope.quotationCalRankParamter[supplierId]['scoringRanking']
                        if (supplierScore[i].isSelect != null && supplierScore[i].isSelect == 'Y' && supplierScore[i].scoringRanking == 1) {
                            isNumOneSelect = 'Y';
                        }
                    }
                    catch (err) {
                    }
                }

                if (selectTotal == 0) {
                    SIEJS.alert("未选择供应商,无法提交!", "warning");
                    return;
                }
                //正常特批规则(1、项目采购金额≤6.4M，有效供应商数量≥3，可选正常审批或特批，
                // 有效供应商数量＜3，只能选特批；
                //
                // 项目采购金额大于6.4M，有效供应商数量≥5，
                // 可选正常审批或特批，有效供应商数量＜5，只能选特批；
                // 2、只选择1st供应商，可选择正常，不选择1st或不止选择1st供应商，只能选特批；)
                if (supplierScore.length < 3 && $scope.params.projectPurchaseAmount < 6400000 && $scope.params.approvalType != 20) {
                    SIEJS.alert("此审批类型必须为特批!", "warning");
                    return;
                } else if (supplierScore.length < 5 && $scope.params.projectPurchaseAmount >= 6400000 && $scope.params.approvalType != 20) {
                    SIEJS.alert("此审批类型必须为特批!", "warning");
                    return;
                } else if (selectTotal > 1 && $scope.params.approvalType != 20) {
                    SIEJS.alert("多个供应商,请选择审批类型为特批!", "warning");
                    return;
                } else if (isNumOneSelect == 'N' && $scope.params.approvalType != 20) {
                    SIEJS.alert("未选择1th供应商,请将审批类型改为特批!", "warning");
                    return;
                } else {
                    SIEJS.confirm('确认', '是否确定发送二次报价邀请？', '确定', function () {
                        $scope.saveData(action)
                    })
                }
            } else if (action == 'stop') {
                SIEJS.confirm('终止', '是否确定终止？', '确定', function () {
                    $scope.saveData(action)
                })
            } else {
                $scope.saveData(action)
            }
        }

        $scope.saveData = function (action) {
            if ($scope.params.scoringNumber == null || $scope.params.scoringNumber == "" ||
                $scope.params.orderType == null || $scope.params.orderType == "" ||
                $scope.params.approvalType == null || $scope.params.approvalType == ""
            ) {
                SIEJS.alert("请维护必填项信息。", "error", "确定");
            } else {
                $scope.params.action = action;
                if (action === 'reject' && $scope.params.rejectReason == null) {
                    SIEJS.alert("请填写驳回原因。", "error", "确定");
                    return;
                }
                $scope.params.nonPriceCalChange = $scope.nonPriceCalChange;
                $scope.params.nonPriceCalColumns = $scope.nonPriceCalColumns;
                $scope.params.secondResultTable = $scope.secondResultTable.data;
                httpServer.post(URLAPI.saveEquPonQuoApprove, {
                    'params': JSON.stringify($scope.params),
                }, function (res) {
                    if (res.status == "S") {
                        $scope.params.approvalNumber = res.data.data.approvalNumber;
                        $scope.params.approvalId = res.data.data.approvalId;
                        approvalId = res.data.data.approvalId;
                        $scope.params.createdBy = res.data.data.createdBy;
                        $scope.params.creationDate = res.data.data.creationDate;
                        $scope.params.approvalStatus = res.data.data.approvalStatus;
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
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.params.approvalId;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate')||localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'EQU_BJJGSPOEM.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
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
                        businessKey:$scope.params.approvalId
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

        $scope.btnSubmit = function (action) {

            var isNumOneSelect = 'N';
            var selectTotal = 0;
            var supplierScore = $scope.nonPriceCalColumns;
            //非二次议价
            if($scope.params.orderType == '10'){
                for (var i = 0; i < supplierScore.length; i++) {
                    var supplierNumber = supplierScore[i].supplierNumber;
                    var supplierId = supplierScore[i].supplierId;
                    supplierScore[i].isSelect = $scope.nonPriceCalChange[0][supplierNumber];
                    try {
                        if (supplierScore[i].isSelect != null && supplierScore[i].isSelect == 'Y') {
                            selectTotal = selectTotal + 1;
                        }
                        supplierScore[i].scoringRanking = $scope.quotationCalRankParamter[supplierId]['scoringRanking']
                        if (supplierScore[i].isSelect != null && supplierScore[i].isSelect == 'Y' && supplierScore[i].scoringRanking == 1) {
                            isNumOneSelect = 'Y';
                        }
                    }
                    catch (err) {
                    }
                }
            }
            //二次议价
            else if($scope.params.orderType == '20'){
                supplierScore = $scope.secondResultTable.data;
                for (var i = 0; i < supplierScore.length; i++) {
                    var supplierNumber = supplierScore[i].supplierNumber;
                    try {
                        if (supplierScore[i].isSelect != null && supplierScore[i].isSelect == 'Y') {
                            selectTotal = selectTotal + 1;
                        }

                        // 判断是否是否是第一被选上
                        if (supplierScore[i].isSelect != null && supplierScore[i].isSelect == 'Y' && i == 0) {
                            isNumOneSelect = 'Y';
                        }
                    }
                    catch (err) {
                    }
                }
            }
            if (selectTotal == 0) {
                SIEJS.alert("未选择供应商,无法提交!", "warning");
                return;
            }
            //正常特批规则(1、项目采购金额≤6.4M，有效供应商数量≥3，可选正常审批或特批，
            // 有效供应商数量＜3，只能选特批；
            //
            // 项目采购金额大于6.4M，有效供应商数量≥5，
            // 可选正常审批或特批，有效供应商数量＜5，只能选特批；
            // 2、只选择1st供应商，可选择正常，不选择1st或不止选择1st供应商，只能选特批；)
            if (supplierScore.length < 3 && $scope.params.projectPurchaseAmount < 6400000 && $scope.params.approvalType != 20) {
                SIEJS.alert("此审批类型必须为特批!", "warning");
                return;
            } else if (supplierScore.length < 5 && $scope.params.projectPurchaseAmount >= 6400000 && $scope.params.approvalType != 20) {
                SIEJS.alert("此审批类型必须为特批!", "warning");
                return;
            } else if (selectTotal > 1 && $scope.params.approvalType != 20) {
                SIEJS.alert("多个供应商,请选择审批类型为特批!", "warning");
                return;
            } else if (isNumOneSelect == 'N' && $scope.params.approvalType != 20) {
                SIEJS.alert("未选择1th供应商,请将审批类型改为特批!", "warning");
                return;
            } else {
                var mes =  '是否确认';
                if(selectTotal > 1){
                    mes = '选择了'+ selectTotal +'个供应商,是否确认。'
                }
                SIEJS.confirm('确定',mes, '确定', function () {
                    $scope.params.action = action;
                    $scope.params.nonPriceCalChange = $scope.nonPriceCalChange;
                    $scope.params.nonPriceCalColumns = $scope.nonPriceCalColumns;
                    $scope.params.secondResultTable = $scope.secondResultTable.data;
                    httpServer.post(URLAPI.saveEquPonQuoApprove, {
                        'params': JSON.stringify($scope.params),
                    }, function (res) {
                        if (res.status == "S") {
                            $scope.params.approvalNumber = res.data.data.approvalNumber;
                            $scope.params.approvalId = res.data.data.approvalId;
                            approvalId = res.data.data.approvalId;
                            $scope.params.createdBy = res.data.data.createdBy;
                            $scope.params.creationDate = res.data.data.creationDate;
                            $scope.params.approvalStatus = res.data.data.approvalStatus;
                            $scope.btnSubmit2();
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                            console.error(res);
                        }
                    }, function (error) {
                        console.error(error);
                    });
                })

            }

        }

        /**********************************工作流配置**************************************/

        //提交审批
        $scope.btnSubmit2 = function(){
            if($scope.params.approvalStatus != '10' && $scope.params.approvalStatus != '40'&& $scope.params.approvalStatus != '60'){
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
                "menucode": "EQU_BJJGSPOEM",
                "opinion": ""
            };
            $scope.paramsBpm={
                "extend":$scope.extend,
                "variables":$scope.variables,
                "properties":$scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": "EQU_BJJGSPOEM.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.params.approvalId, //单据ID
                "title": "报价结果审批", //流程标题
                "positionId": 0,
                "orgId": 0,
                "userType": "30",
                "billNo": $scope.params.approvalNumber
            }
            httpServer.post(URLAPI.bpmStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    // $scope.search($scope.params.approvalId);
                    SIEJS.alert('提交成功');
                    $scope.params.approvalStatus='20'
                    $scope.changeShowFlag();
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
