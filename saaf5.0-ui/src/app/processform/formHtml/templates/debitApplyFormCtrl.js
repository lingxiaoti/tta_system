/**
 * Created by Acer on 2018/7/19.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('debitApplyFormCtrl', function ($scope,httpServer, $location, $rootScope, $state, $stateParams, SIEJS, $timeout, $filter,numberToZh,URLAPI) {
        $scope.params = {};
        $scope.fileType = webconfig.fileFormat.testType;
        // 上传文件ID
        $scope.getFileIds = function(){
            $scope.formParams.fileIds = [];
            for(var i = 0; i<$scope.fileData.file.length;i++){
                $scope.formParams.fileIds.push($scope.fileData.file[i].fileId);
            }
        }
        //  附件上传组件 的状态。当前表单为 detail时不可以上传，其它状态可以上传
        $scope.fileViewOnly = $scope.action ==='detail' ? 0:1;

        //提交前回调
        $scope.submitEvent.cusValidator = function(){
            $scope.getFileIds()
            return true
        } 
         //保存前回调
        $scope.draftEvent.cusValidator = function(){
            console.log($scope.fileData);
            $scope.getFileIds()
            return true
        }
        $scope.debitCusBankPaginatioLov = {}
        $scope.employeeId = {};
        $scope.place = '';
        setTimeout(function () {
            $scope.formParams.moneyType = "CNY"
            $scope.formParams['borrowUserId'] = $scope.formParams.userId;
            $scope.formParams['requestUserId'] = $scope.formParams.userId;
            $scope.formParams['requestUserName'] = $scope.formParams.personName;
            $scope.personCusBankPaginatioParams = {
                personId: $scope.formParams.personId
            }
        }, 1000)
        //$scope.formParams.interfaceName=$filter('date')(new Date(), 'yyyy-MM-dd')
        $scope.tableCallBack = function (index) {
            $scope.tableEnditIndex = index
        }
        $scope.subTotal = function (index, item) {
            var reg = /^(0|[1-9]\d*)(\.\d+)?$/;


        };
        $scope.numberDx= function (n) {
            return numberToZh(n);
        }
        $scope.borrowMoneyStr = function () {
            if(!$scope.formParams.borrowMoney && $scope.formParams.borrowMoney !== undefined) {
                SIEJS.alert('最高金额99,999,999精确到分','error')
                return
            }else{
                $scope.formParams.borrowMoneyZh= $scope.numberDx(Number($scope.formParams.borrowMoney))
            };
            //$scope.formParams.borrowMoneyZh = $scope.numberDx(Number($scope.formParams.borrowMoney))
            //}

        }
        $scope.personCusBankCallback = function () {
            $scope.formParams.bankName = $scope.debitCusBankPaginatioLov.selectRow.bankName;

        }
        $scope.personCusBankCancelCallback = function () {
            $scope.formParams.bankName = '';
        }

        if($scope.action == 'new'){
            setTimeout(function () {
                $scope.employeeId = {
                    employeeId: $scope.formParams.personId,
                    orgId: $scope.orgId
                };
                $scope.acquirePlace();
                //监听借支人改变
                $scope.w2 = $scope.$watch('formParams.personId', function (nval, oval) {
                    if (nval !== oval){
                        $scope.employeeId = {
                            employeeId: $scope.formParams.personId,
                            orgId: $scope.orgId
                        };
                        $scope.acquirePlace();
                    }else{
                        return;
                    }
                });
                $scope.$on("$destroy", function () {
                    scope.w2();
                })

            },500)
            $scope.createRows('items', {
                'giftId': -1
            })
            $scope.fileData = []; // 上传文件
        }
        $scope.pageView.getBusinessFormCallback = function (res) {
            if (res.status === 'S') {
                var data = res.data;
                $scope.formParams.place = data.vendorSiteCode;
                $scope.borrowMoneyStr();
                $scope.positionByPersonParams.orgId = data.orgId;
                $scope.positionByPersonParams.personId = data.personId;
                $scope.positionByPersonParams.deptId = data.deptId;
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
            }
        }

        $scope.acquirePlace = function(){
            //获取供应商地点
            httpServer.post(URLAPI.findVendorSitesAllPagination, {
                params: JSON.stringify($scope.employeeId)
            },function(res) {
                if(res.status === 'S') {
                    if(res.data.length > 0){
                        $scope.formParams.place = res.data[0].vendorSiteCode;
                        $scope.formParams.vendorSiteId = res.data[0].vendorSiteId;
                    }else{
                        //SIEJS.alert("当前借支人没有供应商地点",'error','确定');
                        $scope.formParams.place = "";
                    }
                }else {
                    SIEJS.alert(res.msg,'error','确定');
                }
            },function(err) {
                SIEJS.alert('获取数据失败','error','确定');
            })
        }

        //获取当前时间
        var today = new Date();
        var yyyy = today.getFullYear();
        var mm = today.getMonth()+1;
        var dd = today.getDate();
        $scope.newDate = yyyy+"-"+mm+"-"+dd;
        // //处理
        // $scope.$on('businessLoad', function (event, msg) {
        //     if (msg) {
        //         $scope.borrowMoneyStr();
        //     }
        // })

        //获取当前职责orgId
        $scope.orgId = "";
        var passOrgId =[];
        passOrgId = JSON.parse(localStorage.getItem("OA_currentResp"));
        for(var i=0;i<passOrgId.length;i++){
             if(passOrgId[i].url == "/oa/debitManagement"){
                 $scope.orgId = passOrgId[i].resp.orgBean.orgId;
             }
         }

        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateBorrowApplyByApproval");
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