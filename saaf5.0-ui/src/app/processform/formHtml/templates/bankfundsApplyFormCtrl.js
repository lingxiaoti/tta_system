/**
 * Created by Acer on 2018/7/19.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('bankfundsApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,$timeout,$filter,numberToZh,httpServer,URLAPI) {
        $scope.params = {};
        $scope.bankfundsModal={}
        $scope.bankfundsIAccountNumModal={}
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
        if($scope.action=='new'){
            $scope.createRows('items',{'giftId':-1});
            $scope.fileData = []; // 上传文件

        }

        setTimeout(function(){
            $scope.formParams['borrowUserId']=$scope.formParams.userId;
            $scope.formParams['requestUserId']=$scope.formParams.userId;
            $scope.formParams['requestUserName']=$scope.formParams.personName;
        },200)
        //$scope.formParams.interfaceName=$filter('date')(new Date(), 'yyyy-MM-dd')
        $scope.tableCallBack=function(index){
            $scope.tableEnditIndex=index
        }
        $scope.subTotal=function(index,item){
            var reg=/^(0|[1-9]\d*)(\.\d+)?$/;


        };
        $scope.numberDx= function (n) {
            return numberToZh(n);
        }
        $scope.borrowMoneyStr=function(){
            //var reg=/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;!scope.model[index][item] && scope.model[index][item] !== undefined
            console.log($scope.formParams.transferM)
            if(!$scope.formParams.transferM && $scope.formParams.transferM !== undefined) {
                SIEJS.alert('最高金额99,999,999精确到分','error')
                return
            }else{
                $scope.formParams.borrowMoneyZh= $scope.numberDx(Number($scope.formParams.transferM))
            };

        }
        //
        $scope.bankfundsModalCallback=function(){
            console.log('bankfundsModalCallback')
            if($scope.formParams.iAccountNum&&$scope.formParams.oAccountNum){
                if($scope.formParams.iAccountNum==$scope.formParams.oAccountNum){
                    $scope.bankfundsModal.cancel()
                    SIEJS.alert('转出帐号与转入帐号不能为同一帐号','error')
                }
            }
        }
        $scope.officeApplicantCallback=function(){
            console.log('officeApplicantCallback')
            if($scope.formParams.iAccountNum&&$scope.formParams.oAccountNum){
                if($scope.formParams.iAccountNum==$scope.formParams.oAccountNum){
                    $scope.bankfundsIAccountNumModal.cancel()
                    SIEJS.alert('转出帐号与转入帐号不能为同一帐号','error')
                }
            }
        };
        $scope.pageView.getBusinessFormCallback=function(res){
            if (res.status === 'S') {
                var data=res.data;
                $scope.positionByPersonParams.orgId=data.orgId;
                $scope.positionByPersonParams.personId= data.personId;      
                $scope.positionByPersonParams.deptId=data.deptId;
                $scope.borrowMoneyStr();
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
            } 
        };
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateApTransferByApproval");
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

    });
});
