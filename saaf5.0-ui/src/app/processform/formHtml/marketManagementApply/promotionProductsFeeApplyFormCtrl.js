/**
 * Created by lijiayao on 2018/8/23.
 */
'use strict';
define(['app','moment'], function (app,moment) {
    app.controller('promotionProductsFeeApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
        $scope.params = {};
        $scope.storeParams = {};
        $scope.customerParams = {};
        //费用期间参数+费用科目参数
        $scope.newParams = {};
        $scope.respCallback=function(respId,respList,curentResp){
            $scope.expensePeriodLovParams={
                "respId":$scope.ctrlRespId,
                "periodType":'P'
            }
            if (curentResp.proFileBeans.length != 0) {
                for (var i = 0; i < curentResp.proFileBeans.length; i++) {
                    if (curentResp.proFileBeans[i].profileCode == "BRC_OU") {
                        $scope.newParams.orgId = curentResp.proFileBeans[i].profileValue;
                        $scope.customerParams = {orgId:curentResp.proFileBeans[i].profileValue};
                    }
                }
            }
            //费用科目
            $scope.getRespParams = {
                orgId:$scope.newParams.orgId,
                sourceType:'OA_MARKET_MANAGE',
                sourceAtttribute1:'50',
                enabledFlag:'Y'
            }
        }
        //费用人员 展示弹窗、获取数据
        $scope.getCostUser = function(key,value,item){
            $scope.formParams.costUserId = item[0].personId;
            $scope.formParams.costUserName = item[0].personName;
            $scope.formParams.departmentCode = item[0].departmentId;
            $scope.formParams.departmentName = item[0].departmentName;
            $scope.formParams.channelCode = item[0].channelCode;
            $scope.formParams.channelType = item[0].channelName;
            $scope.storeParams = {
                orgId:$scope.$parent.$parent.orgId,
                personId:item[0].personId
            }
            // console.log($scope.storeParams)
        }
        //费用经销商 展示弹窗、获取数据
        $scope.getCustAccount = function(key,value,item){
            $scope.formParams.custAccountId = item[0].customerId;
            $scope.formParams.custAccountName = item[0].customerName;
            $scope.formParams.departmentName = item[0].departmentName;
            $scope.formParams.departmentCode = item[0].departmentId;
            $scope.formParams.channelCode = item[0].channelCode;
            $scope.formParams.channelType = item[0].channelName;
            $scope.storeParams = {
                orgId:$scope.$parent.$parent.orgId,
                custAccountId:item[0].customerId
            }
            // console.log($scope.storeParams)
        }
        //切换费用主体，清空费用人员或费用经销商信息
        $scope.yesOrNo = function(){
            if($scope.formParams.costMain == 'Y'){
                $scope.formParams.custAccountId = '';
                $scope.formParams.custAccountName = '';
                $scope.formParams.expenseType = '10';
                $("select[id$='expenseType']").prop({
                    disabled:true
                })
                
            }else{
                $scope.formParams.costUserId = '';
                $scope.formParams.costUserName = '';
                $("select[id$='expenseType']").prop({
                    disabled:false
                })
                
            }
            $scope.formParams.departmentName = '';
            $scope.formParams.departmentCode = '';
            $scope.formParams.channelCode = '';
            $scope.formParams.channelType = '';
        }
        // 经销商2信息
        // $scope.getCustAccount2 = function(key,value,item){
        //     $scope.formParams.itemSales[0].dealerName = item[0].customerName;
        //     $scope.formParams.itemSales[0].dealerId = item[0].customerId;
        // }
        //人员下属门店
        $scope.getStoreByPerson = function(key,value,item){
            $scope.formParams.itemSales[0].dealerName = item[0].storeName;
            $scope.formParams.itemSales[0].dealerCode = item[0].storeCode;
        }
        //经销商下属门店
        $scope.getStoreByCustAccount = function(key,value,item){
            $scope.formParams.itemSales[0].dealerName = item[0].storeName;
            $scope.formParams.itemSales[0].dealerCode = item[0].storeCode;
        }
        //费用期间
        $scope.getExpensePeriod = function(key,value,item){
            $scope.formParams.expensePeriod = item[0].expensePeriod;
        }
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth()+1;
        var today = year+"-"+month;
        if($scope.action == 'new'){
            setTimeout(function(){
                $scope.formParams.itemSales = [];
                $scope.formParams.itemSales[0] = {};
                $scope.formParams.costMain = 'Y';
                $scope.formParams.itemSales[0].payType = 1;
                $scope.yesOrNo();
                $scope.formParams.costUserId = $scope.formParams.personId;
                $scope.formParams.costUserName = $scope.formParams.personName;
                $scope.formParams.departmentCode = $scope.formParams.deptId;
                $scope.formParams.departmentName = $scope.formParams.deptName;
                $scope.formParams.orgId = $scope.$parent.$parent.orgId;
                $scope.formParams.expensePeriod = today;
                var p = {
                    params: JSON.stringify({
                        personId: $scope.formParams.personId
                    })
                };
                $scope.storeParams = {
                    orgId:$scope.$parent.$parent.orgId,
                    personId:$scope.formParams.personId
                }
                httpServer.post(URLAPI.findPaginationPersonInfo, p, function (res) {
                    if (res.status === 'S') {
                        $scope.formParams.channelCode = res.data[0].channelCode;
                        $scope.formParams.channelType = res.data[0].channelName;
                    } else {
                        SIEJS.alert(res.msg, 'error');
                    }
                })
            }, 500);
        }
        $scope.pageView.getBusinessFormCallback = function(res){
            if (res.status === 'S') {
                var data=res.data;
                $scope.positionByPersonParams.orgId = data.orgId;
                $scope.positionByPersonParams.personId = data.personId;      
                $scope.positionByPersonParams.deptId = data.deptId;
            } 
        };
        //提交前回调
        $scope.submitEvent.cusValidator = function(){
            //提交金额 = 本次付款金额
            $scope.formParams.encumbranceAmount = Number($scope.formParams.itemPromotionTotal[0].thisAmountTotal);
            return true
        }
        //保存前回调
        $scope.draftEvent.cusValidator = function(){
            //提交金额 = 本次付款金额
            $scope.formParams.encumbranceAmount = Number($scope.formParams.itemPromotionTotal[0].thisAmountTotal);
            return true
        }
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateOaMarketingCostSalesByApproval");
            }
        });
        /*//流程审批，附件上传接口
        $scope.saveFile = function () {
            $scope.getFileIds();
            var p = {
                oaBusinessId :$scope.formParams.requestNum,
                oaFunctionId :$scope.formParams.oaFunctionId,
                fileIds:$scope.formParams.fileIds
            }
            httpServer.post(URLAPI.uploadFile, {
                params: JSON.stringify(p)
            },function(res) {
                if(res.status === 'S') {

                }else {
                    SIEJS.alert(res.msg,'error','确定');
                }
            },function(err) {
                SIEJS.alert('获取数据失败','error','确定');
            })
        }

        //通过前回调
        $scope.passEvent.cusValidator = function(){
            $scope.saveFile();
            return true;
        }
        //驳回前回调
        $scope.refusalEvent.cusValidator = function(){
            $scope.saveFile();
            return true;
        }
        //驳回重审前回调
        $scope.refusaApprovallEvent.cusValidator = function(){
            $scope.saveFile();
            return true;
        }*/
        //获取费用期间
        setTimeout(function () {
            httpServer.post(URLAPI.ems_findExpensePeriodByOrgList, {
                params: JSON.stringify({orgId: $scope.newParams.orgId, periodType: 'P'}),
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
        },500);

    });
});
