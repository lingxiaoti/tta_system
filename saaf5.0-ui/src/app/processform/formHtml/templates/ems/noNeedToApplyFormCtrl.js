/**
 * Created by hmx on 2018/8/27.
 */
'use strict';
define(['app','angularFileUpload'], function(app) {
    app.useModule('angularFileUpload');//按需加载模块
    app.controller('noNeedToApplyFormCtrl',['$scope', '$location', 'URLAPI', 'httpServer', 'SIE.JS', '$timeout',
        function($scope, $location, URLAPI, httpServer, SIEJS, $timeout) {
            $scope.respId = $location.$$search.respId;
            $scope.orgId = $location.$$search.orgId;
            $scope.userType = $location.$$search.userType;
            $scope.channelType = $location.$$search.channelType;
            $scope.action = $location.$$search.action;

            console.log('$scope.action===',$scope.action)

            $scope.bcUploadUrl='uploadFile2JsonObject';

            /**
             *获取费用期间
             */
            $scope.findExpensePeriodList = function() {
                debugger;
                var param1 = '';
                if($scope.action == 'new' || $scope.action == 'draft' || $scope.action == 'refusal' ) {

                    param1 = JSON.stringify({respId: $scope.respId, periodType: 'P',isFilter:'Y'});
                }else{
                    param1 = JSON.stringify({respId: $scope.respId, periodType: 'P'});
                }

                httpServer.post(URLAPI.expensePeriodList, {
                    params: param1,
                    pageIndex: 1,
                    pageRows: 10000
                }, function(res) {
                    if(res.status === 'S') {
                        $scope.expensePeriodList = res.data;
                    }else {
                        SIEJS.alert(res.msg, 'error', '确定');
                    }
                }, function(err) {
                    SIEJS.alert('查询渠道数据失败','error', '确定');
                })
            }
            $scope.findExpensePeriodList();

            /**
             * 确定选中头人员
             */
            $scope.selUserHeaderFun = function() {
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
            $scope.dealerHeaderSelectFun = function() {
                console.log($scope.dealerModel_h);
                $scope.formParams.custAccountName = $scope.dealerModel_h.customerName;
                $scope.formParams.custAccountId = $scope.dealerModel_h.custAccountId;
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
            }

            /**
             * 确定选中头费用科目
             */
            $scope.expSelectFun = function() {
                console.log($scope.expModel)
                $scope.formParams.expenseAccountName = $scope.expModel.expenseAccountName;
                $scope.formParams.expenseAccount = $scope.expModel.expenseAccountId;
            }

             /**
             * 添加报销行
             */
            $scope.createRow = function() {
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
                    bankAccount: ''
                }

                $scope.formParams.reimburseLineData.push(obj);
            }


            /**
             * 删除费用报销行
             * @param item
             * @param index
             */
            $scope.deleteRow = function(item,index) {
                SIEJS.confirm('删除报销行', '是否确定删除？', '确定', function () {
                    if(item.bcReimburseLId) {
                        httpServer.post(URLAPI.deleteReimburseL,{
                            params: JSON.stringify({bcReimburseLId:item.bcReimburseLId})
                        },function(res) {
                            if(res.status === 'S') {
                                $scope.formParams.reimburseLineData.splice(index,1);
                            }
                        },function(err) {
                            SIEJS.alert('删除失败','error','确定');
                        })
                    }else {
                        $scope.formParams.reimburseLineData.splice(index,1);
                    }
                })
            }

            /**
             * change费用报销类型
             */
            $scope.changeExpenseTypeFun = function(key) {
                if(key == '10' && $scope.formParams.encumbranceExpenseType && $scope.formParams.encumbranceExpenseType != '10') {
                    $scope.formParams.expenseType = '';
                    SIEJS.alert('申请费用类型非现金，报销费用类型不能选择现金','warning','确定');
                    return;
                }

                //重置报销行信息的人员、经销商、第三方
                angular.forEach($scope.formParams.reimburseLineData, function(item, index) {
                    item.personId = '';
                    item.personName = '';
                    item.custAccountId = '';
                    item.accountName = '';
                    item.secondaryInventoryName = '';
                    item.thirdParty =  '';
                })
            }

            /**
             * 确定选中报销行部门
             */
            $scope.selDeptFun = function() {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.departmentId = $scope.deptModel.departmentId;
                row.departmentName = $scope.deptModel.departmentName;
            }

            /**
             * 确定选中报销科目
             */
            $scope.expenseSelectFun = function() {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.expenseItem = $scope.expenseItem.expenseItem;
                row.expenseItemDesc = $scope.expenseItem.expenseItemDesc;
            }

            /**
             * 确定选中报销行人员
             */
            $scope.selUserFun = function() {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.personId = $scope.userModel.personId;
                row.personName = $scope.userModel.personName;

                row.custAccountId = '';
                row.accountName = '';
                row.secondaryInventoryName = '';
                row.thirdParty = '';
            }

            /**
             * 确定选中报销行经销商
             */
            $scope.dealerSelectFun = function() {
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
            $scope.thirdPartyFun = function() {
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
            $scope.bankSelectFun = function() {
                var row = $scope.formParams.reimburseLineData[$scope.formParams.selectRowIndex];
                row.bankName = $scope.bankItem.bankName;
                row.bankBranchName = $scope.bankItem.bankBranchName;
                row.bankAccount = $scope.bankItem.bankAccount;
            }


            /**
             * 导入报销行回调
             * @param res
             */
            $scope.upCallBack = function(res) {
                if(res.status === 'S') {
                    angular.forEach(res.data, function(item,index) {
                        $scope.formParams.reimburseLineData = $scope.formParams.reimburseLineData?$scope.formParams.reimburseLineData:[];
                        $scope.formParams.reimburseLineData.push(item);
                    })
                }
            }


            $scope.$on("businessLoad", function(data,status) {
                if(status) {
                    //重新赋值一遍申请人信息
                    $scope.init();
                    console.log($scope.formParams)
                }
            })


            /**
             * 保存草稿
             * @returns {boolean}
             */
            $scope.draftEvent.cusValidator = function() {
                if($scope.formParams.bcReimburseHId && $scope.formParams.reimburseLineData.length < 1) {
                    SIEJS.alert('报销行不能为空','warning','确定');
                    return false;
                }

                //检查人员、经销商、第三方是否三选一
                for(var i = 0;i < $scope.formParams.reimburseLineData.length;i++) {
                    var item = $scope.formParams.reimburseLineData[i];
                    if(!item.personId && !item.custAccountId && !item.thirdParty) {
                        SIEJS.alert('表单填写信息不完整,在报销行信息：第'+(i+1)+'行中，人员、经销商、第三方信息必须三选一','warning','确定');
                        return false;
                    }
                }

                $scope.sumAmount();

                $scope.formParams.respId = $location.$$search.respId;
                $scope.formParams.commitPersonId = $scope.formParams.personId;
                $scope.formParams.commitPersonName = $scope.formParams.personName;
                $scope.formParams.commitPositionId = $scope.formParams.positionId;
                $scope.formParams.commitPositionName = $scope.formParams.positionName;
                httpServer.post(URLAPI.saveCuxBcReimburse, {
                    params: JSON.stringify($scope.formParams)
                }, function(res) {
                    console.log(res)
                    if(res.status === 'S') {
                        $scope.formParams.bcReimburseHId = res.bcReimburseHId;
                        SIEJS.alert(res.msg);
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                }, function(err) {
                    SIEJS.alert('保存失败','error','确定');
                })

                return false;
            }


            /**
             * 提交
             */
            $scope.submitEvent.cusValidator = function() {
                if($scope.formParams.reimburseLineData.length < 1) {
                    SIEJS.alert('报销行不能为空','warning','确定');
                    return false;
                }

                //检查人员、经销商、第三方是否三选一
                for(var i = 0;i < $scope.formParams.reimburseLineData.length;i++) {
                    var item = $scope.formParams.reimburseLineData[i];
                    if(!item.personId && !item.custAccountId && !item.thirdParty) {
                        SIEJS.alert('表单填写信息不完整,在报销行信息：第'+(i+1)+'行中，人员、经销商、第三方信息必须三选一','warning','确定');
                        return false;
                    }
                }

                $scope.sumAmount();


                if($scope.formParams.applyPersonId) {
                    $scope.params.applyPersonId = $scope.formParams.applyPersonId;
                    $scope.params.applyPositionId = $scope.formParams.applyPositionId;
                    $scope.params.roleType = 'person';
                }else {
                    $scope.params.custAccountId = $scope.formParams.custAccountId;
                    $scope.params.roleType = 'customer';
                }

                $scope.formParams.respId = $location.$$search.respId;
                $scope.formParams.commitPersonId = $scope.formParams.personId;
                $scope.formParams.commitPersonName = $scope.formParams.personName;
                $scope.formParams.commitPositionId = $scope.formParams.positionId;
                $scope.formParams.commitPositionName = $scope.formParams.positionName;
                $scope.formParams.buttonStatus = 'commit';
                return true;
            }

            /**
             * 提交审批时校验表单数据
             */
            $scope.passEvent.beforFunction = function() {
                if($scope.formParams.reimburseLineData.length < 1) {
                    SIEJS.alert('报销行不能为空','warning','确定');
                    return false;
                }

                //检查人员、经销商、第三方是否三选一
                for(var i = 0;i < $scope.formParams.reimburseLineData.length;i++) {
                    var item = $scope.formParams.reimburseLineData[i];
                    if(!item.personId && !item.custAccountId && !item.thirdParty) {
                        SIEJS.alert('表单填写信息不完整,在报销行信息：第'+(i+1)+'行中，人员、经销商、第三方信息必须三选一','warning','确定');
                        return false;
                    }
                }

                $scope.sumAmount();

                if($scope.formParams.applyPersonId) {
                    $scope.params.applyPersonId = $scope.formParams.applyPersonId;
                    $scope.params.applyPositionId = $scope.formParams.applyPositionId;
                    $scope.params.roleType = 'person';
                }else {
                    $scope.params.custAccountId = $scope.formParams.custAccountId;
                    $scope.params.roleType = 'customer';
                }

                $scope.formParams.respId = $location.$$search.respId;
                $scope.formParams.commitPersonId = $scope.formParams.personId;
                $scope.formParams.commitPersonName = $scope.formParams.personName;
                $scope.formParams.commitPositionId = $scope.formParams.positionId;
                $scope.formParams.commitPositionName = $scope.formParams.positionName;

                console.log('提交审批时校验表单数据===>>',$scope.formParams,$scope.params);

                // if ($scope.passEventActtion) return true;
                httpServer.post(URLAPI.checkAndUpdate,{
                    params: JSON.stringify($scope.formParams)
                },function(res) {

                    if (res.status === 'S') {
                        // $scope.approvalEvent('Y', false, '审批');
                        $scope.showBtnApproval('Y', false, '审批');
                        $scope.passEventActtion = true;

                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                        $scope.passEventActtion= false;
                    }
                },function(err) {
                    SIEJS.alert('校验数据失败','error','确定');
                })
                return false;
            }

            /**
             * 汇总金额
             */
            $scope.sumAmount = function() {
                var sum = 0;
                angular.forEach($scope.formParams.reimburseLineData,function(item, index) {
                    sum += item.reimburseAmount;
                })
                $scope.formParams.commitAmount = sum;
            }


            if($location.$$search.action === 'new') {
                //新增
                $scope.formParams.orgId = $scope.orgId;
                $scope.formParams.respId = $location.$$search.respId;
                $scope.formParams.reimburseStatus = 'create';
                $scope.formParams.isPostPaperReceipts = 'Y';
                $scope.formParams.transferApSum = 'N';
                $scope.formParams.transferCaSum = 'Y';
                $scope.formParams.reimburseLineData = [];
            }else if($location.$$search.action === 'edit') {
                //编辑
            }
    }])
})