/**
 * Created by Acer on 2018/7/19.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('refundApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS, $timeout, $filter,numberToZh,$http,httpServer,URLAPI) {
        $scope.params = {};
        $scope.bankFlag = false;
        $scope.automaticFlag = true;
        if ($scope.action == 'new') {
            $scope.createRows('items', {
                'giftId': -1
            });
            $scope.fileData = []; // 上传文件

        }
        $scope.fileType = webconfig.fileFormat.testType;
        // 上传文件ID
        $scope.getFileIds = function(){
            $scope.formParams.fileIds = [];
            for(var i = 0; i<$scope.fileData.file.length;i++){
                $scope.formParams.fileIds.push($scope.fileData.file[i].fileId);
            }
            // console.log($scope.fileData)
            // console.log($scope.formParams.fileIds)
        }
        //  附件上传组件 的状态。当前表单为 detail时不可以上传，其它状态可以上传
        $scope.fileViewOnly = $scope.action ==='detail' ? 0:1;

        //提交
        $scope.submitEvent.cusValidator = function(){
            $scope.getFileIds()
            return true
        } 
         //保存   
        $scope.draftEvent.cusValidator = function(){
            $scope.getFileIds()
            return true
        }
        //新增页面加载
        if($scope.action == "new"){
            console.log($scope.formParams);
            setTimeout(function(){
                $scope.personCusBankPaginatioParams = {
                    personId: $scope.formParams.personId
                }
                httpServer.post(URLAPI.findPersonCusBankPagination, {
                    params: JSON.stringify($scope.personCusBankPaginatioParams)
                },function(res) {
                    if(res.status === 'S') {
                        if(res.data.length != 0){
                            $scope.formParams.accountName = res.data[0].primaryAcctOwnerName;
                            $scope.formParams.accountBank = res.data[0].bankName;
                            $scope.formParams.accountNum = res.data[0].bankAccountNumElectronic;
                        }else{
                            $scope.formParams.accountName = "";
                            $scope.formParams.accountBank = "";
                            $scope.formParams.accountNum = "";
                        }
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('获取数据失败','error','确定');
                })
            },1000);
        }
        //修改
        $scope.pageView.getBusinessFormCallback=function(res){
            if (res.status === 'S') {
                console.log(res);
                $scope.formParams['borrowUserId'] = res.userId;
                $scope.formParams['requestUserId'] = res.userId;
                $scope.formParams['requestUserName'] = res.personName;
                $scope.getFileIds();
                //页面加载，设置收款人参数
                $scope.personCusBankPaginatioParams = {
                    personId: res.personId
                }
                //页面加载，查询默认收款人
                httpServer.post(URLAPI.findPersonCusBankPagination, {
                    params: JSON.stringify($scope.personCusBankPaginatioParams)
                },function(res) {
                    if(res.status === 'S') {
                        console.log(res.data);
                        if(res.data.length != 0 && !$scope.bankFlag){
                            $scope.formParams.accountName = res.data[0].primaryAcctOwnerName;
                            $scope.formParams.accountBank = res.data[0].bankName;
                            $scope.formParams.accountNum = res.data[0].bankAccountNumElectronic;
                        }else{
                            $scope.formParams.accountName = "";
                            $scope.formParams.accountBank = "";
                            $scope.formParams.accountNum = "";
                        }
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('获取数据失败','error','确定');
                })

            }
        };

        $scope.delarCodeParams = {
            responsibilityId: $scope.respId
        }
        //$scope.formParams.interfaceName=$filter('date')(new Date(), 'yyyy-MM-dd')
        $scope.tableCallBack = function (index) {
            $scope.tableEnditIndex = index
        }
        $scope.subTotal = function (index, item) {
            var reg = /^(0|[1-9]\d*)(\.\d+)?$/;


        };
        $scope.numberDx = function (n) {
            return numberToZh(n);
        }
        $scope.borrowMoneyStr = function () {
            var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
            if(!$scope.formParams.refundM && $scope.formParams.refundM !== undefined) {
                SIEJS.alert('最高金额99,999,999精确到分','error')
                return
            }else{
                $scope.formParams.borrowMoneyZh= $scope.numberDx(Number($scope.formParams.refundM))
            };

            //if (!reg.test(Number($scope.formParams.refundM))) return;
            //console.log($scope.formParams.refundM)
            //$scope.formParams.borrowMoneyZh = $scope.numberDx(Number($scope.formParams.refundM))
            //console.log($scope.numberDx($scope.formParams.refundM))

        }
        //收款人lov回调
        $scope.personCusBankCallback = function (key,value,item) {
            $scope.formParams.accountBank = item[0].bankName;
        }
        //收款人lov 清除选择回调
        $scope.personCusBankCancelCallback = function () {
            $scope.formParams.accountBank = '';
        }

        $scope.pageView.getBusinessFormCallback = function (res) {
            if (res.status === 'S') {
                var data = res.data;
                $scope.borrowMoneyStr();
                $scope.positionByPersonParams.orgId = data.orgId;
                $scope.positionByPersonParams.personId = data.personId;
                $scope.positionByPersonParams.deptId = data.deptId;
                $scope.fileData.file = data.fileList;//上传组件数据
            }
        };
        //  //处理
        // $scope.$on('businessLoad', function (event, msg) {
        //         if(msg){
        //             $scope.borrowMoneyStr();
        //         }
        //     })

        //退款申请人lov回调
        $scope.getCostUser = function(key,value,item){
            $scope.personCusBankPaginatioParams = {
                personId: item[0].personId
            }
            $scope.formParams.personId = item[0].personId;
            $scope.formParams.personName = item[0].personName;
            if($scope.automaticFlag){
                httpServer.post(URLAPI.findPersonCusBankPagination, {
                    params: JSON.stringify($scope.personCusBankPaginatioParams)
                },function(res) {
                    if(res.status === 'S') {
                        if(res.data.length != 0){
                            $scope.formParams.accountName = res.data[0].primaryAcctOwnerName;
                            $scope.formParams.accountBank = res.data[0].bankName;
                            $scope.formParams.accountNum = res.data[0].bankAccountNumElectronic;
                            console.log($scope.formParams);
                        }else{
                            $scope.formParams.accountName = "";
                            $scope.formParams.accountBank = "";
                            $scope.formParams.accountNum = "";
                        }
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('获取数据失败','error','确定');
                })
            }
        }
        //经销商编码lov回调
        $scope.setRowInput = function (key,value,item) {
            if(item.length > 0){
                $scope.automaticFlag = false;
                $scope.formParams.accountName = "";
                $scope.formParams.accountBank = "";
                $scope.formParams.accountNum = "";
            }
        }
        //经销商编码lov 清除选择回调
        $scope.dealersCodeCallback = function () {
            $scope.automaticFlag = true;
            $scope.personCusBankPaginatioParams = {
                personId: $scope.formParams.personId
            }
            httpServer.post(URLAPI.findPersonCusBankPagination, {
                params: JSON.stringify($scope.personCusBankPaginatioParams)
            },function(res) {
                if(res.status === 'S') {
                    if(res.data.length != 0){
                        $scope.formParams.accountName = res.data[0].primaryAcctOwnerName;
                        $scope.formParams.accountBank = res.data[0].bankName;
                        $scope.formParams.accountNum = res.data[0].bankAccountNumElectronic;
                    }else{
                        $scope.formParams.accountName = "";
                        $scope.formParams.accountBank = "";
                        $scope.formParams.accountNum = "";
                    }
                }else {
                    SIEJS.alert(res.msg,'error','确定');
                }
            },function(err) {
                SIEJS.alert('获取数据失败','error','确定');
            })
        }
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateApRefundByApproval");
            }
        });
        //流程审批，附件上传接口
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
        }

    })

});