/**
 * Created by Acer on 2018/7/19.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('sealApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,$timeout,$filter,httpServer,URLAPI) {
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
        //$scope.formParams.interfaceName=$filter('date')(new Date(), 'yyyy-MM-dd')
        $scope.tableCallBack=function(index){
            $scope.tableEnditIndex=index
        }
        $scope.setRowInput=function(){
            var data=$scope.findGif.selectRowList;
            var appleData=$scope.formParams.items
            for(var i=0;i<data.length;i++){
                //console.log(i==0&&!appleData.splPrice)

                if(i==0 && appleData[i].giftId==-1 && $scope.action=='new'){
                    $scope.formParams.items[i].giftNum=0;
                    $scope.formParams.items[i].remark='';
                    $scope.formParams.items[i].giftName=data[i].skuName;
                    $scope.formParams.items[i].giftId=data[i].admGiftId;
                }else{

                    var obj={
                        giftNum:0,
                        remark:'',
                        giftName:data[i].skuName,
                        giftId:data[i].admGiftId
                    }
                    $scope.formParams.items.shift(obj)
                }
            }
           /* angular.forEach($scope.findOfficeAdmGiftPagination.selectRowList,function(data,index){
                $scope.formParams.apple.push(data)
            })
            angular.forEach($scope.formParams.apple, function (data,index) {
                if(index==$scope.tableEnditIndex){

                }
            })*/

        }
        $scope.pageView.getBusinessFormCallback = function (res) {
            if (res.status === 'S') {
                var data = res.data;
                $scope.positionByPersonParams.orgId = data.orgId;
                $scope.positionByPersonParams.personId = data.personId;
                $scope.positionByPersonParams.deptId = data.deptId;
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
            }
        };


        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateSealApplyByApproval");
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
        // //提交验证
        // $scope.btnSubmit.cusValidator = function () {
        //     //盖章次数
        //     var a = false;
        //     var b = false;
        //     if ($scope.formParams.sealNum > 20) {
        //         SIEJS.alert('盖章次数最大为20次', 'warning');
        //         a = false;
        //     }else{
        //         a = true;
        //     }
        //     //申请说明
        //     if ($scope.formParams.applyDesc.toString().length > 500) {
        //         SIEJS.alert('申请说明字数限制为500字', 'warning');
        //         b = false;
        //     }else{
        //         b = true;
        //     }
        //     return (a && b);
        // }
        // //保存草稿验证
        // $scope.btnDraft.cusValidator = function () {
        //     //盖章次数
        //     var a = false;
        //     var b = false;
        //     if ($scope.formParams.sealNum > 20) {
        //         SIEJS.alert('盖章次数最大为20次', 'warning');
        //         a = false;
        //     }else{
        //         a = true;
        //     }
        //     //申请说明
        //     if ($scope.formParams.applyDesc.toString().length > 500) {
        //         SIEJS.alert('申请说明字数限制为500字', 'warning');
        //         b = false;
        //     }else{
        //         b = true;
        //     }
        //     return (a && b);
        // }
    });
});
