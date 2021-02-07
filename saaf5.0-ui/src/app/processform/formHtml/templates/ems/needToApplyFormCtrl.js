/**
 * Created by hmx on 2018/8/21.
 */
'use strict';
define(['app', 'angularFileUpload', '../../../../ems/service/http','webconfig'], function (app,angularFileUpload,http,webconfig) {
    app.useModule('angularFileUpload');//按需加载模块
    app.controller('needToApplyFormCtrl', ['$scope', '$location', 'URLAPI', 'httpServer', 'SIE.JS', '$timeout','pageResp','limitNumLength',
        function ($scope, $location, URLAPI, httpServer, SIEJS, $timeout,pageResp,limitNumLength) {
            //  附件上传组件 的状态。当前表单为 detail时不可以上传，其它状态可以上传
            $scope.fileViewOnly = $scope.action ==='detail' ? 0:1;

            $scope.urlParams = angular.copy($location.$$search)

            $scope.respId = $scope.urlParams.respId;
            $scope.orgId = $scope.urlParams.orgId;
            $scope.channelType = $scope.urlParams.channelType;
            $scope.userType = $scope.urlParams.userType;

            $scope.bcUploadUrl = 'uploadFile2JsonObject';

            $scope.bankParams = {orgId: $scope.urlParams.orgId};

            $scope.action = $location.$$search.action;

            //费用报销行的部门、报销项目默认值
            $scope.rowsDefaultValue = {dept: [],expense: []};

            $scope.fileType = webconfig.fileFormat.emsFileType;

            /**
             * 表单数据请求成功回调
             * @param res
             */
            $scope.pageView.getBusinessFormCallback = function (res) {
                //重新赋值一遍申请人信息
                $scope.init();
                //在ems新增、编辑或查看的时候sysFlat为ems，在流程审批时sysFlag为undefined
                if ($scope.urlParams.sysFlag != 'ems') {
                    $scope.flag = 'bpm';
                    $scope.orgId = $scope.formParams.orgId;
                    $scope.channelType = $scope.formParams.channelType;
                    // $scope.userType = $scope.urlParams.userType;
                }

                $scope.urlParams.applyType = $scope.formParams.bcEncumbranceHId ? 'Y' : 'N';

                angular.forEach($scope.formParams.files, function(item, index) {
                    item.fileName = item.sourceFileName;
                    item.fileSize = item.fileSize + 'MB';
                })

                $scope.fileData = $scope.formParams.files;
                $scope.findExpensePeriodList();
                $scope.getDeptList();
                $scope.getExpenseList();
            };

            /**
             *获取费用期间
             */
            $scope.findExpensePeriodList = function () {

                var param1 = '';
                if($scope.action == 'new' || $scope.action == 'draft' || $scope.action == 'refusal' ) {

                    param1 = JSON.stringify({orgId: $scope.orgId, periodType: 'P',isFilter:'Y'});
                }else{
                    param1 = JSON.stringify({orgId: $scope.orgId, periodType: 'P'});
                }

               debugger;
                httpServer.post(URLAPI.ems_findExpensePeriodByOrgList, {
                    params: param1,
                    pageIndex: 1,
                    pageRows: 10000
                }, function (res) {
                    if (res.status === 'S') {
                        $scope.expensePeriodList = res.data;
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                    }
                }, function (err) {
                    SIEJS.alert('查询渠道数据失败', 'error', '确定');
                })
            }


            /**
             * 确认选中费用申请
             */
            $scope.historyFlag = false;
            $scope.feeApplySelectFun = function () {
                console.log($scope.applyItem)
                // if($scope.formParams.expenseAccount != $scope.applyItem.expenseAccount) {
                //     $scope.formParams.reimburseLineData = [];
                // }

                $scope.formParams.orgId = $scope.applyItem.orgId;
                $scope.formParams.channelName = $scope.applyItem.channelName;
                $scope.formParams.channelType = $scope.applyItem.channelType;
                $scope.formParams.departmentName = $scope.applyItem.departmentName;
                $scope.formParams.departmentId = $scope.applyItem.departmentId;
                $scope.formParams.applyPersonName = $scope.applyItem.personName;
                $scope.formParams.applyPersonId = $scope.applyItem.personId;
                $scope.formParams.applyPositionId = $scope.applyItem.positionId;
                $scope.formParams.applyPositionName = $scope.applyItem.positionName;
                $scope.formParams.custAccountName = $scope.applyItem.custAccountName;
                $scope.formParams.custAccountId = $scope.applyItem.custAccountId;
                $scope.formParams.bcEncumbranceHId = $scope.applyItem.bcEncumbranceCode;
                $scope.formParams.expensePeriod = $scope.applyItem.expensePeriod;
                $scope.formParams.expenseAccountName = $scope.applyItem.expenseAccountName;
                $scope.formParams.expenseAccount = $scope.applyItem.expenseAccount;
                $scope.formParams.encumbranceAmount = $scope.applyItem.commitAmount;
                $scope.formParams.reimburseAmount = $scope.applyItem.commitAmount;
                $scope.formParams.encumbranceExpenseType = $scope.applyItem.expenseType;

                $scope.formParams.typeCode = $scope.applyItem.typeCode?$scope.applyItem.typeCode:'';

                if ($scope.applyItem.personId) {
                    //审批流程区别   人员
                    $scope.formParams.roleType = 'person';
                } else if ($scope.applyItem.custAccountId) {
                    //审批流程区别   经销商
                    $scope.formParams.roleType = 'customer';
                }

                if (!($scope.applyItem.expenseType == '10') && $scope.formParams.expenseType == '10') {
                    $scope.formParams.expenseType = '';
                }

                //重置报销行信息的人员、经销商、第三方、部门
                angular.forEach($scope.formParams.reimburseLineData, function (item, index) {
                    item.departmentName = '';
                    item.personId = '';
                    item.personName = '';
                    item.custAccountId = '';
                    item.accountName = '';
                    item.secondaryInventoryName = '';
                    item.thirdParty = '';
                })

                //查询审批历史
                if ($scope.formParams.bcEncumbranceHId) {
                    var scParams = {
                        processDefinitionKey: 'CUX_ENCUMBRANCE',
                        responsibilityId: $scope.ctrlRespId,
                        businessKey : $scope.formParams.bcEncumbranceHId
                    }
                    httpServer.post(URLAPI.processGetStartUrl, {
                        params: JSON.stringify(scParams)
                    }, function (data) {
                        if (data.status === 'S') {
                            httpServer.post(URLAPI.processFindHistoricActivities, {
                                params: JSON.stringify({processInstanceId : data.data.processInstanceId}),
                                pageIndex: 1,
                                pageRows: 10000
                            }, function (res) {
                                if (res.status === 'S') {
                                    $scope.processFindHistoricActivitiesList = res.data;
                                    $scope.newProcessFindHistoricActivitiesList = [];
                                    angular.forEach($scope.processFindHistoricActivitiesList, function (data, index) {
                                        if (data.his_detail_opinion) {
                                            $scope.newProcessFindHistoricActivitiesList.push(data);
                                        }
                                    })
                                } /*else {
                                    SIEJS.alert(res.msg, 'error', '确定');
                                }*/
                            }, function (err) {
                                SIEJS.alert('查询审批历史失败', 'error', '确定');
                            })
                        }
                    }, function (err) {
                        SIEJS.alert('查询审批历史失败', 'error', '确定');
                    })
                }

                $scope.historyFlag = true;
            }


            /**
             * 确定选中头人员
             */
            $scope.selUserHeaderFun = function () {
                if($scope.formParams.applyPersonId != $scope.userModel_h.personId) {
                    $scope.formParams.reimburseLineData = [];
                    $scope.createRow();
                }
                $scope.formParams.applyPersonName = $scope.userModel_h.personName;
                $scope.formParams.applyPersonId = $scope.userModel_h.personId;
                $scope.formParams.applyPositionId = $scope.userModel_h.positionId;
                $scope.formParams.applyPositionName = $scope.userModel_h.positionName;
                $scope.formParams.channelName = $scope.userModel_h.channelName;
                $scope.formParams.channelType = $scope.userModel_h.channelId;
                $scope.formParams.departmentName = $scope.userModel_h.departmentName;
                $scope.formParams.departmentId = $scope.userModel_h.departmentId;

                //审批流程区别   人员
                $scope.formParams.roleType = 'person';

                $scope.formParams.custAccountName = '';
                $scope.formParams.custAccountId = '';
            }


            /**
             * 确定选中头经销商
             */
            $scope.dealerHeaderSelectFun = function () {
                if($scope.formParams.custAccountId != $scope.dealerModel_h.custAccountId) {
                    $scope.formParams.reimburseLineData = [];
                    $scope.createRow();
                }

                $scope.formParams.custAccountName = $scope.dealerModel_h.customerName;
                $scope.formParams.custAccountId = $scope.dealerModel_h.customerId;
                $scope.formParams.channelName = $scope.dealerModel_h.channelName;
                $scope.formParams.channelType = $scope.dealerModel_h.channelId;
                $scope.formParams.departmentName = $scope.dealerModel_h.departmentName;
                $scope.formParams.departmentId = $scope.dealerModel_h.departmentId;

                //审批流程区别   经销商
                $scope.formParams.roleType = 'customer';

                $scope.formParams.applyPersonName = '';
                $scope.formParams.applyPersonId = '';
                $scope.formParams.applyPositionId = '';
                $scope.formParams.applyPositionName = '';

                //$scope.createRow();
            }


            /**
             * 确定选中头费用科目
             */
            $scope.expSelectFun = function () {
                if($scope.formParams.expenseAccount != $scope.expModel.expenseAccountId) {
                    //$scope.formParams.reimburseLineData = [];
                    angular.forEach($scope.formParams.reimburseLineData, function (item, index) {
                        var row = $scope.formParams.reimburseLineData[index];
                        row.expenseItem = '';
                        row.expenseItemDesc = '';
                    });


                }
                $scope.formParams.expenseAccountName = $scope.expModel.expenseAccountName;
                $scope.formParams.expenseAccount = $scope.expModel.expenseAccountId;
                $scope.formParams.typeCode = $scope.expModel.typeCode?$scope.expModel.typeCode:'';
            }

            /**
             * 清空选择的费用科目
             */
            $scope.expEmptyFun = function () {
                $scope.formParams.expenseAccountName = '';
                $scope.formParams.expenseAccount = '';
            }

            /**
             * 添加报销行
             */
            $scope.createRow = function () {
                var obj = {
                    departmentName: '',
                    departmentId: '',
                    expenseItemName: '',
                    expenseItemId: '',
                    personName: '',
                    personId: '',
                    accountName: '',
                    custAccountId: '',
                    thirdParty: '',
                    reimburseAmount: '',
                    startDate: '',
                    endDate: '',
                    bankName: '',
                    bankBranchName: '',
                    bankAccount: '',
                    bankBool: true
                }

                if($scope.rowsDefaultValue.dept.length == 1) {
                    obj.departmentName = $scope.rowsDefaultValue.dept[0].departmentName;
                    obj.departmentId = $scope.rowsDefaultValue.dept[0].departmentId;
                }
                if($scope.rowsDefaultValue.expense.length == 1) {
                    obj.expenseItemDesc = $scope.rowsDefaultValue.expense[0].expenseItemDesc;
                    obj.expenseItemId = $scope.rowsDefaultValue.expense[0].expenseItemId;
                }

                $scope.formParams.reimburseLineData.push(obj);
            }

            /**
             * 删除费用报销行
             * @param item
             * @param index
             */
            $scope.deleteRow = function (item, index) {
                SIEJS.confirm('删除报销行', '是否确定删除？', '确定', function () {
                    if (item.bcReimburseLId) {
                        httpServer.post(URLAPI.deleteReimburseL, {
                            params: JSON.stringify({bcReimburseLId: item.bcReimburseLId})
                        }, function (res) {
                            if (res.status === 'S') {
                                $scope.formParams.reimburseLineData.splice(index, 1);
                            }
                        }, function (err) {
                            SIEJS.alert('删除失败', 'error', '确定');
                        })
                    } else {
                        $scope.formParams.reimburseLineData.splice(index, 1);
                    }
                })
            }


            /**
             * change费用报销类型
             */
            $scope.changeExpenseTypeFun = function (key) {
                if (key == '10' && $scope.formParams.encumbranceExpenseType && $scope.formParams.encumbranceExpenseType != '10') {
                    $scope.formParams.expenseType = '';
                    SIEJS.alert('申请费用类型非现金，报销费用类型不能选择现金', 'warning', '确定');
                    return;
                }

                //重置报销行信息的人员、经销商、第三方
                // angular.forEach($scope.formParams.reimburseLineData, function (item, index) {
                //     item.personId = '';
                //     item.personName = '';
                //     item.custAccountId = '';
                //     item.accountName = '';
                //     item.secondaryInventoryName = '';
                //     item.thirdParty = '';
                // })
            }

            $scope.$watch('formParams.expenseType',function(nval,lval) {
                if(nval && lval && nval != lval ) {
                    $scope.changeExpenseTypeFun(nval);
                }
            })

            /**
             * 确定选中报销行部门
             */
            $scope.selDeptFun = function () {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.departmentId = $scope.deptModel.departmentId;
                row.departmentName = $scope.deptModel.departmentName;
            }

            /**
             * 清空选中报销行部门
             */
            $scope.empDeptFun = function() {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.departmentId = '';
                row.departmentName = '';
            }

            /**
             * 确定选中报销行人员
             */
            $scope.selUserFun = function () {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.personId = $scope.userModel.personId;
                row.personName = $scope.userModel.personName;

                row.custAccountId = '';
                row.accountName = '';
                row.secondaryInventoryName = '';
                row.thirdParty = '';
            }

            /**
             * 确定选中报销科目
             */
            $scope.expenseSelectFun = function () {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.expenseItem = $scope.expenseItem.expenseItem;
                row.expenseItemDesc = $scope.expenseItem.expenseItemDesc;
            }

            /**
             * 清空选中报销科目
             */
            $scope.expenseEmptyFun = function() {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.expenseItem = '';
                row.expenseItemDesc = '';
            }

            /**
             * 确定选中报销行经销商
             */
            $scope.dealerSelectFun = function () {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.custAccountId = $scope.dealerItem.customerId;
                row.accountName = $scope.dealerItem.customerName;
                row.secondaryInventoryName = $scope.dealerItem.subInvName;

                row.personId = '';
                row.personName = '';
                row.thirdParty = '';
            }

            /**
             * 填写第三方事件
             */
            $scope.thirdPartyFun = function () {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.personId = '';
                row.personName = '';

                row.custAccountId = '';
                row.accountName = '';
                row.secondaryInventoryName = '';
            }

            /**
             * 确定选中银行
             */
            $scope.bankSelectFun = function () {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.bankName = $scope.bankItem.bankName;
                row.bankBranchName = $scope.bankItem.bankBranchName;
                row.bankAccount = $scope.bankItem.bankAccount;
            }

            /**
             * 清空选中银行
             */
            $scope.bankEmptyFun = function() {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.bankName = '';
                row.bankBranchName = '';
                row.bankAccount = '';
            }

            /**
             * 导入报销行回调
             * @param res
             */
            $scope.upCallBack = function (res) {
                if (res.status === 'S') {
                    angular.forEach(res.data, function (item, index) {
                        $scope.formParams.reimburseLineData = $scope.formParams.reimburseLineData ? $scope.formParams.reimburseLineData : [];
                        $scope.formParams.reimburseLineData.push(item);
                    })
                }
            }


            /**
             * 获取报销行的部门的默认值，当只有一个部门的时候即可赋默认部门值
             */
            $scope.getDeptList = function() {
                httpServer.post(URLAPI.findBaseDeptInfoByParent,{
                    params: JSON.stringify({
                        respId: $scope.urlParams.respId,
                        orgId: $scope.orgId,
                        departmentId: $scope.formParams.departmentId,
                        sysFlag: $scope.flag
                    })
                },function(res) {
                    if(res.status === 'S' && res.data.length == 1) {
                        $scope.rowsDefaultValue.dept = res.data;
                    }
                },function(err) {

                })
            }
            /**
             * 获取报销行的报销项目的默认值，当只有一个报销项目的时候即可赋默认报销项目值
             */
            $scope.getExpenseList = function() {
                httpServer.post(URLAPI.findExpenseItemPagination,{
                    params: JSON.stringify({
                        orgId: $scope.orgId,
                        expenseAccountId: $scope.formParams.expenseAccount
                    })
                },function(res) {
                    console.log(res,'+++')
                    if(res.status === 'S' && res.data.length == 1) {
                        $scope.rowsDefaultValue.expense = res.data;
                    }
                },function(err) {

                })
            }

            /**
             * 解禁btn和input
             */
            $scope.pageView.form = function () {
                if ($scope.urlParams.action == 'approval') {
                    console.log($scope.formParams, '====');
                    $timeout(function () {
                        if (!$scope.formParams.applyPersonName || $scope.formParams.expenseType != '10') {
                            $('.personBtn').each(function () {
                                $(this).attr('disabled',true);
                            })
                        }
                        if (!$scope.formParams.applyPersonName || $scope.formParams.expenseType != '10') {
                            $('.thirdPartyInput').each(function () {
                                $(this).attr('disabled',true);
                            })
                        }
                        $('#createRow').removeAttr('disabled');
                    })
                }
            }


            /**
             * 新增 保存草稿: $scope.draftEvent.cusValidator
             * @returns {boolean}
             */
            $scope.draftEvent.cusValidator = function () {
                if(!$scope.formParams.custAccountName && !$scope.formParams.applyPersonName) {
                    SIEJS.alert('请选择经销商或人员', 'warning', '确定');
                    return false;
                }

                if ($scope.formParams.reimburseLineData.length < 1) {
                    SIEJS.alert('报销行不能为空', 'warning', '确定');
                    return false;
                }

                //检查人员、经销商、第三方是否三选一
                for (var i = 0; i < $scope.formParams.reimburseLineData.length; i++) {
                    var item = $scope.formParams.reimburseLineData[i];

                    if(item.personName && ($scope.formParams.expenseType!='10' || !$scope.formParams.applyPersonName)) {
                        SIEJS.alert('当报销头非人员和现金时，报销行禁止选择人员', 'warning', '确定');
                        return false;
                    }

                    if(item.thirdParty && ($scope.formParams.expenseType!='10' || !$scope.formParams.applyPersonName)) {
                        SIEJS.alert('当报销头非人员和现金时，报销行禁止输入第三方', 'warning', '确定');
                        return false;
                    }

                    if (!item.personId && !item.custAccountId && !item.thirdParty) {
                        SIEJS.alert('表单填写信息不完整,在报销行信息：第' + (i + 1) + '行中，人员、经销商、第三方信息必须三选一', 'warning', '确定');
                        return false;
                    }
                }

                $scope.sumAmount();

                $scope.formParams.respId = $location.$$search.respId;
                // $scope.formParams.commitPersonId = $scope.successLoginUser.personId;
                // $scope.formParams.commitPersonName = $scope.successLoginUser.userFullName;
                // $scope.formParams.commitPositionId = $scope.successLoginUser.position.positionId;
                // $scope.formParams.commitPositionName = $scope.successLoginUser.position.positionName;

                $scope.formParams.commitPersonId = $scope.formParams.personId;
                $scope.formParams.commitPersonName = $scope.formParams.personName;
                $scope.formParams.commitPositionId = $scope.formParams.positionId;
                $scope.formParams.commitPositionName = $scope.formParams.positionName;

                //添加附件对象
                $scope.formParams.files = $scope.fileData;
                $scope.formParams.fileIds = [];
                for(var i in $scope.fileData) {
                    $scope.formParams.fileIds.push($scope.fileData[i].fileId)
                }

                httpServer.post(URLAPI.saveCuxBcReimburse, {
                    params: JSON.stringify($scope.formParams)
                }, function (res) {
                    if (res.status === 'S') {
                        $scope.formParams.bcReimburseHId = res.bcReimburseHId;
                        $scope.formParams.bcReimburseCode = res.bcReimburseHId;
                        // $location.$$search.action = $scope.action = 'edit';
                        SIEJS.alert(res.msg);
                        if($scope.action == 'new') {
                            $scope.getDeptList();
                            $scope.getExpenseList();
                        }
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                    }
                }, function (err) {
                    SIEJS.alert('保存失败', 'error', '确定');
                })

                return false;
            }


            /**
             * 新增 提交：$scope.submitEvent.cusValidator
             */
            $scope.submitEvent.cusValidator = function () {
                if(!$scope.formParams.custAccountName && !$scope.formParams.applyPersonName) {
                    SIEJS.alert('请选择经销商或人员', 'warning', '确定');
                    return false;
                }

                if ($scope.formParams.reimburseLineData.length < 1) {
                    SIEJS.alert('报销行不能为空', 'warning', '确定');
                    return false;
                }

                //检查人员、经销商、第三方是否三选一
                for (var i = 0; i < $scope.formParams.reimburseLineData.length; i++) {
                    var item = $scope.formParams.reimburseLineData[i];
                    if (!item.personId && !item.custAccountId && !item.thirdParty) {
                        SIEJS.alert('表单填写信息不完整,在报销行信息：第' + (i + 1) + '行中，人员、经销商、第三方信息必须三选一', 'warning', '确定');
                        return false;
                    }
                }

                if ($scope.formParams.applyPersonId) {
                    $scope.params.applyPersonId = $scope.formParams.applyPersonId;
                    $scope.params.applyPositionId = $scope.formParams.applyPositionId;
                    $scope.params.roleType = 'person';
                } else {
                    $scope.params.custAccountId = $scope.formParams.custAccountId;
                    $scope.params.roleType = 'customer';
                }

                $scope.ctrlRespId

                $scope.sumAmount();

                $scope.formParams.respId = $location.$$search.respId;
                // $scope.formParams.commitPersonId = $scope.successLoginUser.personId;
                // $scope.formParams.commitPersonName = $scope.successLoginUser.userFullName;
                // $scope.formParams.commitPositionId = $scope.successLoginUser.position.positionId;
                // $scope.formParams.commitPositionName = $scope.successLoginUser.position.positionName;
                $scope.formParams.commitPersonId = $scope.formParams.personId;
                $scope.formParams.commitPersonName = $scope.formParams.personName;
                $scope.formParams.commitPositionId = $scope.formParams.positionId;
                $scope.formParams.commitPositionName = $scope.formParams.positionName;

                $scope.formParams.bcReimburseHId = $scope.formParams.billNo?$scope.formParams.billNo:$scope.formParams.bcReimburseHId;

                $scope.formParams.buttonStatus = 'commit';

                $scope.params.properties = {
                    opinion: $scope.formParams.opinion
                }

                $scope.formParams.commitUserId = $scope.formParams.commitPersonId;
                $scope.formParams.commitUserType = $scope.ctrlUserType;
                $scope.formParams.processCode = $location.$$search.processDefinitionKey;
                $scope.formParams.remark = $scope.formParams.eventDesc;
                $scope.formParams.bussiessType = $scope.formParams.typeCode;
                $scope.formParams.opinion = $scope.formParams.opinion;

                $scope.variables.push({name:'typeCode',type:'string'});
                $scope.variables.push({name:'reimburseAmount',type:'long'});
                $scope.variables.push({name:'applyPersonId',type:'int'});
                $scope.variables.push({name:'custAccountId',type:'int'});
                $scope.variables.push({name:'orgId',type:'int'});
                $scope.variables.push({name:'channelType',type:'string'});
                $scope.variables.push({name:'departmentId',type:'int'});
                $scope.variables.push({name:'commitUserId',type:'int'});
                $scope.variables.push({name:'commitUserType',type:'string'});
                $scope.variables.push({name:'processCode',type:'string'});
                $scope.variables.push({name:'remark',type:'string'});
                $scope.variables.push({name:'bussiessType',type:'string'});
                $scope.variables.push({name:'expenseType',type:'string'});
                $scope.variables.push({name:'opinion',type:'string'});
                $scope.variables.push({name:'expenseAccount',type:'int'});


                //添加附件对象
                $scope.formParams.files = $scope.fileData;
                $scope.formParams.fileIds = [];
                for(var i in $scope.fileData) {
                    $scope.formParams.fileIds.push($scope.fileData[i].fileId)
                }

                return true;
            }

            /**
             * 监听保存操作和提交的返回数据，并赋值给表单，避免表单重复提交
             */
            // $scope.$watch('businessFormParams',function() {
            //     if($scope.businessFormParams && $scope.businessFormParams.data.billNo) {
            //         $scope.formParams.bcReimburseHId = $scope.businessFormParams.data.billNo;
            //     }
            // },true)


            /**
             * 提交审批时校验表单数据
             */
            $scope.passEvent.beforFunction = function () {

                if ($scope.formParams.reimburseLineData.length < 1) {
                    SIEJS.alert('报销行不能为空', 'warning', '确定');
                    return false;
                }

                //检查人员、经销商、第三方是否三选一
                for (var i = 0; i < $scope.formParams.reimburseLineData.length; i++) {
                    var item = $scope.formParams.reimburseLineData[i];
                    if (!item.personId && !item.custAccountId && !item.thirdParty) {
                        SIEJS.alert('表单填写信息不完整,在报销行信息：第' + (i + 1) + '行中，人员、经销商、第三方信息必须三选一', 'warning', '确定');
                        return false;
                    }
                }

                $scope.sumAmount();

                if ($scope.formParams.applyPersonId) {
                    $scope.params.applyPersonId = $scope.formParams.applyPersonId;
                    $scope.params.applyPositionId = $scope.formParams.applyPositionId;
                    $scope.params.roleType = 'person';
                } else {
                    $scope.params.custAccountId = $scope.formParams.custAccountId;
                    $scope.params.roleType = 'customer';
                }

                // $scope.formParams.respId = $location.$$search.respId;
                $scope.formParams.sysFlag = $scope.flag
                $scope.formParams.commitPersonId = $scope.formParams.personId;
                $scope.formParams.commitPersonName = $scope.formParams.personName;
                $scope.formParams.commitPositionId = $scope.formParams.positionId;
                $scope.formParams.commitPositionName = $scope.formParams.positionName;

                console.log('提交审批时校验表单数据===>>', $scope.formParams, $scope.params);
              //  if ($scope.passEventActtion) return true;
                httpServer.post(URLAPI.checkAndUpdate, {
                    params: JSON.stringify($scope.formParams)
                }, function (res) {


                    if (res.status === 'S') {
                        // $scope.approvalEvent('Y', false, '审批');
                        $scope.showBtnApproval('Y', false, '审批');
                        $scope.passEventActtion = true;
                        //$("#approvalFormModal").modal('show');
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                        $scope.passEventActtion= false;
                    }
                }, function (err) {
                    SIEJS.alert('校验数据失败', 'error', '确定');
                })
                return false;
            }


            $scope.addParams = function() {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                console.log(row)
                $scope.bankParams.personId = row.personId;
                $scope.bankParams.custAccountId = row.custAccountId;
                $scope.bankParams.thirdParty = row.thirdParty;

                $timeout(function() {
                    $('#bankInfo').modal('show')
                },300)
                // $scope.bankParams = {orgId:urlParams.orgId}
            }

            /**
             * 判断银行按钮是否可点击
             */
            $scope.$watch('formParams.reimburseLineData',function(nval,lval) {
                if(nval && nval != lval) {
                    angular.forEach($scope.formParams.reimburseLineData,function(item,index) {
                        if(item.personName || item.accountName || item.thirdParty) {
                            item.bankBool = false;
                        }else {
                            item.bankBool = true;
                        }
                    })
                }
            },true)

            /**
             * 限制金额小数点长度
             * @param item
             * @param key
             * @param len
             */
            $scope.changeRowAmount = function(item,key,len) {
                limitNumLength(item,key,len)
            }

            /**
             * 汇总金额
             */
            $scope.sumAmount = function () {
                var sum = 0;
                angular.forEach($scope.formParams.reimburseLineData, function (item, index) {
                    sum += item.reimburseAmount;
                })
                $scope.formParams.commitAmount = sum;
                $scope.formParams.reimburseAmount = sum;
            }


            /**
             * 下载模版
             */
            $scope.downloadTmp = function (code) {
                var dParams={
                    type: code
                }
                httpServer.post(URLAPI.downloadReimburse, {
                    params: JSON.stringify(dParams)
                }, function (res) {
                    if ( res.status =='S') {
                        var _url=location.protocol+'//'+location.host+res.data;
                        window.open(_url);
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    SIEJS.alert('请求失败', 'error', '确定');
                })
            }

            /**
             * 打印
             */
            $scope.print = function() {
                window.open(URLAPI.ems_get_downReimbursePrint + '?bcReimburseHId=' + $scope.urlParams.businessKey)
            }


            if ($location.$$search.action === 'new') {
                //新增
                $scope.formParams.orgId = $scope.orgId;
                $scope.formParams.respId = $location.$$search.respId;
                $scope.formParams.reimburseStatus = 'create';
                $scope.formParams.isPostPaperReceipts = 'Y';
                $scope.formParams.transferApSum = 'N';
                $scope.formParams.transferCaSum = 'Y';
                $scope.formParams.reimburseLineData = [];

                $scope.findExpensePeriodList();

                $scope.createRow();
            } else if ($location.$$search.action === 'edit') {
                //编辑
            }

            /**
             * create hmx 2018年9月19日
             * @type {Array}
             */
            $scope.fileData = []; // 上传文件
            $scope.fileFilter = {
                code: 'UPLOAD_FILE_FILTER',
                key: 'imageType'
            }
        }])
})
